package c4sci.data.dataResources;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import c4sci.data.DataIdentity;
import c4sci.data.DataParameter;
import c4sci.data.HierarchialDataFactory;
import c4sci.data.HierarchicalData;
import c4sci.data.HierarchicalDataResource;
import c4sci.data.HierarchicalDataVisitor;
import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
/**
 * Stores and retrieves {@link HierarchicalData} through the use of XML structures by the DOM API.<br>
 * <br>
 * The tree structure is as follows :<br>
 * (root)
 * <ul>
 * <li>HierarchicalData :
 * 		<ol>
 * 			<li>token</li>
 * 			<li>id</li>
 * 			<li>parameter session :
 * 				<ul>
 * 				<li> parameter token and string value</li>
 * 				<li> other parameters tokens and string values</li>
 * 				</ul>
 * 			</li>
 * 			<li> sub data session :
 * 				<ul>
 * 				<li> hierarchical data</li>
 * 				<li> other hierarchical data</li>
 * 				</ul>
 * 			</li>
 * 		</ol>
 * </li>
 * <li>other Hierarchical Data...</li>
 * </ul>
 * @author jeanmarc.deniel
 *
 */
public class XMLDataResource implements HierarchicalDataResource {


	private static final String ID_VALUE_TOKEN				= "dataIdentity";
	private static final String SUB_DATA_SESSION_TOKEN 		= "subdata";
	private static final String PARAMETER_SESSION_TOKEN 	= "parameters";
	private static final String PARAMETER_VALUE_TOKEN		= "value"; 

	public void storeData(OutputStream output_stream,
			HierarchicalData[] data_tab) throws DataValueParsingException{

		DocumentBuilder _doc_builder;
		try {
			_doc_builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document _doc = _doc_builder.newDocument();
			Element _root_element = _doc.getDocumentElement();
			if (_root_element == null){
				_root_element = _doc.createElement("root");
				_doc.appendChild(_root_element);
			}
			StoreVisitor _visitor = new StoreVisitor(_doc);

			for (HierarchicalData _data : data_tab){
				_visitor.currentNode = _root_element;
				_visitor.currentDataParametersNode = null;
				_visitor.currentSubDataNode = null;
				_data.acceptVisitor(_visitor);
			}

			_doc.normalize();


			Transformer _transf = TransformerFactory.newInstance().newTransformer();
			_transf.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource _source = new DOMSource(_doc);
			StreamResult _result = new StreamResult(output_stream);
			_transf.transform(_source, _result);

		} catch (ParserConfigurationException _e) {
			throw new DataValueParsingException("A DOM Document", "", "cannot create Document", _e);
		} catch (TransformerConfigurationException _e) {
			throw new DataValueParsingException("A DOM Document", "", "cannot transform Document", _e);
		} catch (TransformerFactoryConfigurationError _e) {
			throw new DataValueParsingException("A DOM Document", "", "cannot transform Document", _e);
		} catch (TransformerException _e) {
			throw new DataValueParsingException("A DOM Document", "", "cannot transform Document", _e);
		}
	}


	public HierarchicalData[] retrieveData(	InputStream input_stream,
			HierarchialDataFactory root_data_factory)
					throws DataValueParsingException {

			// first parse the input source
			//
			Document _doc;
			try {
				_doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input_stream);
			}catch (Exception _e) {
				throw new DataValueParsingException("", "", "DOM parsing error", _e);
			}
			// then creates the list of top level HierarchialData
			//
			Element _root_elt = _doc.getDocumentElement();
			if (_root_elt == null){
				throw new DataValueParsingException("an Element", "getDocuementElement()", "DOM error", null);
			}
			NodeList _root_list = _root_elt.getChildNodes();
			List<HierarchicalData> _res_list = new ArrayList<HierarchicalData>();
			for (int _i=0; _i<_root_list.getLength(); _i++){
				Node _node = _root_list.item(_i);
				if (Element.class.isInstance(_node)){
					try{
						_res_list.add(createNewData(null, (Element)_node, root_data_factory));
					}
					catch(CannotInstantiateDataException _e){
						throw new DataValueParsingException("an instance", "newInstance", "retrieve data", _e);
					}
					catch(DataValueParsingException _e){
						throw new DataValueParsingException(_e.getExpectedValueInformation(), _e.getStringToParse(), "creating a data", _e);
					}
					catch(Exception _e){
						throw new DataValueParsingException("???", "???", "An exception has raised in retrieveData", _e);
					}
				}
			}
			return (HierarchicalData[]) _res_list.toArray(new HierarchicalData[1]); // 1 to avoid "empty array warning"
	}

	private HierarchicalData createNewData(HierarchicalData parent_data, Element element_node, HierarchialDataFactory parent_subdata_factory) 
			throws DataValueParsingException, CannotInstantiateDataException, CannotInstantiateParameterException{
		
		HierarchicalData _res = createDataInstance(element_node, parent_data, parent_subdata_factory);
		NodeList _elt_node_children = element_node.getChildNodes();
		for (int _child_index = 0; _child_index < _elt_node_children.getLength(); _child_index ++){
			if (Element.class.isInstance(_elt_node_children.item(_child_index))){
				String _node_name = _elt_node_children.item(_child_index).getNodeName();
				if (PARAMETER_SESSION_TOKEN.compareTo(_node_name) == 0){
					setDataParameters(_res, _elt_node_children.item(_child_index).getChildNodes());
				}
				if (SUB_DATA_SESSION_TOKEN.compareTo(_node_name) == 0){
					insertSubdata(_res, _elt_node_children.item(_child_index).getChildNodes());
				}
			}
		}
		return _res;	
	}

	private void insertSubdata(HierarchicalData current_data, NodeList subdata_node_list)
			throws CannotInstantiateDataException{
		HierarchialDataFactory _factory = current_data.getSubdataFactory();
		for (int _subdata_index = 0; _subdata_index < subdata_node_list.getLength(); _subdata_index++){
			if (Element.class.isInstance(subdata_node_list.item(_subdata_index))){
				Element _subdata_elt = (Element) subdata_node_list.item(_subdata_index);
				try{
					current_data.addSubData(createNewData(current_data, _subdata_elt, _factory));
				}
				catch(Exception _e){
					throw new CannotInstantiateDataException(current_data, _subdata_elt.getNodeName(), "creating sub data raised exception", _e);
				}
			}
		}
	}

	private HierarchicalData createDataInstance(Element element_node, HierarchicalData parent_data, HierarchialDataFactory parent_subdata_factory)
			throws DataValueParsingException, CannotInstantiateDataException, CannotInstantiateParameterException{
		HierarchicalData _res = parent_subdata_factory.produceData(parent_data, element_node.getNodeName());
		DataIdentity _res_id = new DataIdentity();
		_res_id.parseFromString(element_node.getAttribute(ID_VALUE_TOKEN));
		_res.setDataIdentity(_res_id);
		return _res;
	}

	private void setDataParameters(HierarchicalData current_data, NodeList param_nodes_list)
			throws CannotInstantiateParameterException, DataValueParsingException{
		for (int _param_index = 0; _param_index<param_nodes_list.getLength(); _param_index ++){
			if (Element.class.isInstance(param_nodes_list.item(_param_index))){
				Element _param_elt = (Element) param_nodes_list.item(_param_index);
				try{
					current_data.setParameterValue(_param_elt.getNodeName(), _param_elt.getAttribute(PARAMETER_VALUE_TOKEN));
				}
				catch(CannotInstantiateParameterException _e){
					throw new CannotInstantiateParameterException(_param_elt.getNodeName(), "reading xml source", _e);
				}
				catch(DataValueParsingException _e){
					throw new DataValueParsingException(_param_elt.getNodeName(), _param_elt.getAttribute(PARAMETER_VALUE_TOKEN), "parsing data value", _e);
				}
			}
		}
	}

	private static class StoreVisitor implements HierarchicalDataVisitor{

		private Document				currentDocument;				// the DOM Document on which we're working on
		private Element 				currentNode;					// the node under which one we are working		
		private Element					currentDataParametersNode;		// its parameters node
		private Element					currentSubDataNode;				// the node under which new nodes must be constructed

		public StoreVisitor(Document current_document){
			currentDocument				= current_document;
			currentDataParametersNode	= null;
			currentNode					= null;
			currentSubDataNode			= null;
		}

		public Element findParentElement(Element current_elt){
			Node _current_node = current_elt.getParentNode();
			while ((_current_node != null)&& !Element.class.isInstance(_current_node)){
				_current_node = _current_node.getParentNode();
			}
			return _current_node == null ? null : (Element) _current_node;
		}
	

		public void performTreatmentOn(HierarchicalData current_data) {
			// begins the treatment of a data that should be a child of currentNode
			//
			
			// first finds the parent node
			//
			Element _parent_node = (currentSubDataNode == null)? currentDocument.getDocumentElement() :  currentSubDataNode;
			
			// creates a new Element corresponding to he current data and 
			//

			Element _new_node = currentDocument.createElement(current_data.getDataToken());
			_new_node.setAttribute(ID_VALUE_TOKEN, current_data.getDataIdentity().toString());
			
			// appends the newly created Element as child of _parent_node
			//
			_parent_node.appendChild(_new_node);
			
			// this newly created Element becomes the element under which one we're working from now
			//
			currentNode = _new_node;

			// performs initializations
			currentDataParametersNode = null;
			currentSubDataNode = null;
		}

		public void beginDataParametersSession(HierarchicalData current_data) {
			// creates the data node
			//
			currentDataParametersNode = currentDocument.createElement(PARAMETER_SESSION_TOKEN);
			currentNode.appendChild(currentDataParametersNode);
		}

		public void performTreatmentOn(HierarchicalData current_data, DataParameter data_param) {
			// appends a newly created parameter Element under the current data parameters node
			//
			Element _data_element = currentDocument.createElement(data_param.getParameterToken());
			_data_element.setAttribute(PARAMETER_VALUE_TOKEN, data_param.getValue());
			currentDataParametersNode.appendChild(_data_element);
		}

		public void endDataParametersSession(HierarchicalData current_data) {
			// nothing to do
		}

		public void beginSubDataSession(HierarchicalData current_data) {
			// creates a sub data node under the current node
			//
			currentSubDataNode = currentDocument.createElement(SUB_DATA_SESSION_TOKEN);
			currentNode.appendChild(currentSubDataNode);
		}

		public void endSubDataSession(HierarchicalData current_data) {
			// nothing to do
		}

		public void endTreatmentOn(HierarchicalData current_data) {
			// comes back up from the current Element to its parent Element that should be a sub data element
			//
			Element _current_node = null;

			if (currentNode != null){
				_current_node = findParentElement(currentNode);
			}

			if (_current_node != null){
				// then comes back from the sub data element to a data element
				//
				_current_node = findParentElement(_current_node);
			}
			currentNode = _current_node;
			// now we must find the current sub data node
			//
			currentSubDataNode = currentNode == null ? null : getSubDataNode(currentNode);
		}
		
		private Element getSubDataNode(Element current_node){
			NodeList _child_list = current_node.getChildNodes();
			int _child_index = 0;
			while ((_child_index<_child_list.getLength()) &&
					(		(!Element.class.isInstance(_child_list.item(_child_index))) ||
							(!_child_list.item(_child_index).getNodeName().equals(SUB_DATA_SESSION_TOKEN))
							)){
				_child_index ++;
			}
			return (Element)_child_list.item(_child_index);
		}

	}


}
