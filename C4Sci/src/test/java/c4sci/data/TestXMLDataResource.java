package c4sci.data;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

import c4sci.data.basicDataParameters.PlaneVectorDataParameter;
import c4sci.data.dataParameters.GenericDataParameter;
import c4sci.data.dataParameters.basicModifiables.BooleanModifiable;
import c4sci.data.dataParameters.basicModifiables.FloatModifiable;
import c4sci.data.dataParameters.basicModifiables.IntegerModifiable;
import c4sci.data.dataResources.XMLDataResource;
import c4sci.data.exceptions.CannotInstantiateDataException;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.internationalization.InternationalizableTerm;


public class TestXMLDataResource {

	public class SubDataType1 extends HierarchicalData{

		private GenericDataParameter<IntegerModifiable>	intSub1Param;
		private GenericDataParameter<BooleanModifiable>	boolSub1Param;
		
		private static final String		INT_SUB_1_PARAM = "intSub1Param";
		private static final String		BOOL_SUB_1_PARAM = "boolSub1Param";
		
		public SubDataType1(String data_token,
				InternationalizableTerm data_name,
				InternationalizableTerm data_description) {
			super(data_token, data_name, data_description);
			intSub1Param 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), INT_SUB_1_PARAM, new InternationalizableTerm("intSub1 Parameter"), new InternationalizableTerm("int sub 1 parameter explanation"));
			boolSub1Param 	= new GenericDataParameter<BooleanModifiable>(new BooleanModifiable(), BOOL_SUB_1_PARAM, new InternationalizableTerm("boolSub1 Parameter"), new InternationalizableTerm("boolSub1 Parameter explanation"));
			
			addDataParameter(intSub1Param);
			addDataParameter(boolSub1Param);
		}
		
		public SubDataType1(){
			super();
			intSub1Param 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(), INT_SUB_1_PARAM, new InternationalizableTerm("intSub1 Parameter"), new InternationalizableTerm("int sub 1 parameter explanation"));
			boolSub1Param 	= new GenericDataParameter<BooleanModifiable>(new BooleanModifiable(),BOOL_SUB_1_PARAM, new InternationalizableTerm("boolSub1 Parameter"), new InternationalizableTerm("boolSub1 Parameter explanation"));

			addDataParameter(intSub1Param);
			addDataParameter(boolSub1Param);
		}
		
		public PrototypeData newInstance(){
			return new SubDataType1("", new InternationalizableTerm(""), new InternationalizableTerm(""));
		}
	}
	
	public class SubDataType2 extends HierarchicalData{
		
		private PlaneVectorDataParameter					planeVSub1Param;
		private GenericDataParameter<FloatModifiable>		floatSub1Param;
		
		private static final String		PLANE_V_SUB1_PARAM = "planeVSub1Param";
		private static final String		FLOAT_SUB_1_PARAM = "floatSub1Param";
		
		public SubDataType2(String data_token,
							InternationalizableTerm data_name,
							InternationalizableTerm data_description) {
			super(data_token, data_name, data_description);
			planeVSub1Param 	= new PlaneVectorDataParameter(PLANE_V_SUB1_PARAM, new InternationalizableTerm("plane vector sub param 1"), new InternationalizableTerm("plane vector sub 1 explanation"));
			floatSub1Param		= new GenericDataParameter<FloatModifiable>(new FloatModifiable(), FLOAT_SUB_1_PARAM, new InternationalizableTerm("floatSub1 param"), new InternationalizableTerm("float sub 1 param exlpanation"));
			
			addDataParameter(planeVSub1Param);
			addDataParameter(floatSub1Param);
		}
		
		public SubDataType2(){
			super();
			planeVSub1Param 	= new PlaneVectorDataParameter(PLANE_V_SUB1_PARAM, new InternationalizableTerm("plane vector sub param 1"), new InternationalizableTerm("plane vector sub 1 explanation"));
			floatSub1Param		= new GenericDataParameter<FloatModifiable>(new FloatModifiable(), FLOAT_SUB_1_PARAM, new InternationalizableTerm("floatSub1 param"), new InternationalizableTerm("float sub 1 param exlpanation"));

			addDataParameter(planeVSub1Param);
			addDataParameter(floatSub1Param);
		}

		@Override
		public PrototypeData newInstance()
				throws CannotInstantiateDataException {
			return new SubDataType2();
		}
		
	}
	
	public class DataType1 extends HierarchicalData{

		private GenericDataParameter<IntegerModifiable> 	intParam1;
		private GenericDataParameter<FloatModifiable>		floatParam1;
		
		private static final String INT_PARAM_1_STR = "intParam1";
		private static final String FLOAT_PARAM_1_STR = "floatParam1";
		
		public static final String SUB_DATA_1_STR = "subData1";
		public static final String SUB_DATA_2_STR = "subData2";
		
		public DataType1(String data_token, InternationalizableTerm data_name,
				InternationalizableTerm data_description) {
			super(data_token, data_name, data_description);
			intParam1 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(),INT_PARAM_1_STR, new InternationalizableTerm("Integer Param 1"), new InternationalizableTerm("Integer param 1 explanation"));
			floatParam1	= new GenericDataParameter<FloatModifiable>(new FloatModifiable() ,FLOAT_PARAM_1_STR, new InternationalizableTerm("Float Parameter 1"), new InternationalizableTerm("Flaot Parameter 1 explanation"));
			
			addDataParameter(intParam1);
			addDataParameter(floatParam1);
		}
		public DataType1(){
			super();
			intParam1 	= new GenericDataParameter<IntegerModifiable>(new IntegerModifiable(),INT_PARAM_1_STR, new InternationalizableTerm("Integer Param 1"), new InternationalizableTerm("Integer param 1 explanation"));
			floatParam1	= new GenericDataParameter<FloatModifiable>(new FloatModifiable(),FLOAT_PARAM_1_STR, new InternationalizableTerm("Float Parameter 1"), new InternationalizableTerm("Flaot Parameter 1 explanation"));

			addDataParameter(intParam1);
			addDataParameter(floatParam1);
		}
		@Override
		public HierarchialDataFactory getSubdataFactory(){
			HierarchialDataFactory _res = super.getSubdataFactory();
			
			_res.addFactoringAbility(new SubDataType1("type_1", new InternationalizableTerm(""), new InternationalizableTerm("")), SUB_DATA_1_STR);
			_res.addFactoringAbility(new SubDataType2("type_2", new InternationalizableTerm(""), new InternationalizableTerm("")), SUB_DATA_2_STR);
			
			return _res;
		}

		@Override
		public PrototypeData newInstance()
				throws CannotInstantiateDataException {
			return new DataType1();
		}
	}

	public class DataFactory1 extends HierarchialDataFactory{
		public DataFactory1(){
			addFactoringAbility(new DataType1("", new InternationalizableTerm(""), new InternationalizableTerm("")), "type1");
		}
	}
	
	@Test
	public void test() {
		DataType1 _written_data = new DataType1("type1", new InternationalizableTerm("type 1 name"), new InternationalizableTerm("type 1 description"));
		_written_data.floatParam1.accesValue().setFloatValue(20.0052f);
		_written_data.intParam1.accesValue().setIntegerValue(1025);
		
		_written_data.addSubData(new SubDataType1(DataType1.SUB_DATA_1_STR, new InternationalizableTerm("sub 1"), new InternationalizableTerm("sub 1 explanation")));
		_written_data.addSubData(new SubDataType2(DataType1.SUB_DATA_2_STR, new InternationalizableTerm("sub 2"), new InternationalizableTerm("sub 2 explanation")));
		
		XMLDataResource _resource = new XMLDataResource();
		HierarchicalData[] _written_data_tab = {_written_data};
		
		PipedOutputStream _out_stream = new PipedOutputStream();
		try {
			PipedInputStream  _input_stream = new PipedInputStream(_out_stream, 10000);
			_resource.storeData(_out_stream, _written_data_tab);
			_out_stream.close();
			HierarchicalData[] _read_data_tab = _resource.retrieveData(_input_stream, new DataFactory1());
			
			assertTrue("" + _read_data_tab.length + " instead of 1", _read_data_tab.length == 1);
			
			HierarchicalData _read_data = _read_data_tab[0];
			
			try {
				assertTrue(_written_data.floatParam1.getValue().equals(_read_data.getParameterValue(DataType1.FLOAT_PARAM_1_STR)));
				assertTrue(_written_data.intParam1.getValue().equals(_read_data.getParameterValue(DataType1.INT_PARAM_1_STR)));
				assertTrue(_written_data.getDataIdentity().equals(_read_data.getDataIdentity()));
			} catch (CannotInstantiateParameterException e) {
				fail("should not throw");
			}
			

		} catch (IOException e) {
			e.printStackTrace();
			fail("should not throw here");
		} catch (DataValueParsingException e) {
			e.printStackTrace();
			fail("should not throw here");
		}
		
		
	}

}
