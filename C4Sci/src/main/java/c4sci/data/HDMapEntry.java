package c4sci.data;

import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.internationalization.InternationalizableTerm;

/**
 * This class represent a relationship of two HierarchicalData present in a Map :<br>
 * <ul>
 * <li>one as a key</li>
 * <li>one as a value</li>
 * </ul>
 * @author jeanmarc.deniel
 *
 */
public class HDMapEntry extends HierarchicalData{

	private DataIdentity	keyIdentity;
	private DataIdentity	valueIdentity;
	
	public HDMapEntry(DataIdentity key_id, DataIdentity value_id) {
		super("HDMapEntry", 
				new InternationalizableTerm("HDMapEntry"), 
				new InternationalizableTerm("HierarchicalData Map Entry"));
		keyIdentity 	= key_id;
		valueIdentity 	= value_id;
	}
	public DataIdentity getKeyIdentity(){
		return keyIdentity;
	}
	public DataIdentity getValueIdentity(){
		return valueIdentity;
	}
	@Override
	public PrototypeData newInstance() throws CannotInstantiateDataException {
		return new HDMapEntry(new DataIdentity(), new DataIdentity());
	}
	
}
