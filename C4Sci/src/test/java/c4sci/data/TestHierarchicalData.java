package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.exceptions.CannotInstantiateParameterException;
import c4sci.data.internationalization.InternationalizableTerm;

public class TestHierarchicalData {

	@Test
	public void testHierarchicalData() {
		assertTrue(new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class")) != null);
	}

	@Test
	public void testGetDataToken() {
		HierarchicalData _data = new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		assertTrue("test_data".compareTo(_data.getDataToken())==0);

	}

	@Test
	public void testGetDataName() {
		HierarchicalData _data = new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		assertTrue("testing data".compareTo(_data.getDataName().getDefaultValue())==0);
	}

	@Test
	public void testGetDataDescription() {
		HierarchicalData _data = new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		assertTrue("this data is used to test the HierarchicalData class".compareTo(_data.getDataDescription().getDefaultValue())==0);
	}

	class TestDataParameter  extends DataParameter{
		public TestDataParameter(String a_, InternationalizableTerm b_, InternationalizableTerm c_){
			super(a_,b_,c_);
			paramValue = 0;
		}
		private int	paramValue;
		public void setValue(String str_to_parse)
				throws DataValueParsingException {
			try{
				paramValue = Integer.parseInt(str_to_parse);
			}
			catch(NumberFormatException _e){
				throw new DataValueParsingException("integer", str_to_parse, "integer parsing error", _e);
			}
		}
		
		public String getValue() {
			return Integer.toString(paramValue);
		}

		@Override
		protected DataParameter getSameDataParameterInstance() {
			return new TestDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
		}

		@Override
		public String getRegExp() {
			return "\\w*|\\W*";
		}

	};
	@Test
	public void testAddDataParameter() {
		HierarchicalData _data = new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		DataParameter _param = new TestDataParameter("int", 
				new InternationalizableTerm("integer param"), 
				new InternationalizableTerm("testing int param"));

		_data.addDataParameter(_param);
		assertTrue(true);

	}

	@Test
	public void testSetGetParameterValue() {
		HierarchicalData _data = new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		DataParameter _param = new TestDataParameter("testParam", 
				new InternationalizableTerm("integer param"), 
				new InternationalizableTerm("testing int param"));

		_data.addDataParameter(_param);
		
		try {
			_data.setParameterValue("testParam", "10");
			assertTrue("10".compareTo(_data.getParameterValue("testParam"))==0);
		} catch (DataValueParsingException e) {
			fail();
		} catch (CannotInstantiateParameterException e) {
			fail();
		}
		
		try {
			_data.setParameterValue("error_param", "10");
			fail();
		} catch (DataValueParsingException e) {
			fail();
		} catch (CannotInstantiateParameterException e) {
			assertTrue(true);
		}
		
		try {
			_data.setParameterValue("testParam", "a10");
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		} catch (CannotInstantiateParameterException e) {
			fail();
		}
		
		try {
			String _str = _data.getParameterValue("err_str");
			fail(_str + " should not have been read");
		} catch (CannotInstantiateParameterException e) {
			assertTrue(true);
		}
	}


	@Test
	public void testAcceptVisitor() {
		HierarchicalData _data = new HierarchicalData("test_data", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		
		HierarchicalData _child_1 = new HierarchicalData("child_1", 
				new InternationalizableTerm("First child"), 
				new InternationalizableTerm("First child description"));
		
		HierarchicalData _child_2 = new HierarchicalData("child_2", 
				new InternationalizableTerm("Second child"), 
				new InternationalizableTerm("2nd child description"));
		
		
		DataParameter _param = new TestDataParameter("param1", 
				new InternationalizableTerm("testparam1"),
				new InternationalizableTerm("descr 1")) ;
		DataParameter _param_2 = new TestDataParameter("param2",
				new InternationalizableTerm("testparam2"),
				new InternationalizableTerm("descr 2"));

		_data.addDataParameter(_param);
		_data.addDataParameter(_param);
		_data.addDataParameter(_param_2);
		
		_data.addSubData(_child_1);
		_data.addSubData(_child_1);
		_data.addSubData(_child_2);
		
		_child_1.addDataParameter(_param);
		_child_2.addDataParameter(_param_2);
		
		class TwoInt{
			public int nbVisitedParam;
			public int nbVisitedNode;
			public TwoInt() {
				nbVisitedNode = 0;
				nbVisitedParam = 0;
			}
		};
		
		final TwoInt two_int = new TwoInt();
		
		
		HierarchicalDataVisitor _visitor = new HierarchicalDataVisitor() {
			
			public void performTreatmentOn(HierarchicalData current_node, DataParameter data_param) {
				two_int.nbVisitedParam ++;
			}
			
			public void performTreatmentOn(HierarchicalData data_node) {
				two_int.nbVisitedNode ++;
			}

			public void beginDataParametersSession(HierarchicalData current_node) {
			}

			public void endDataParametersSession(HierarchicalData current_node) {
			}

			public void endTreatmentOn(HierarchicalData data_node) {
			}

			public void beginSubDataSession(HierarchicalData current_node) {
			}

			public void endSubDataSession(HierarchicalData current_node) {
			}
		};
		
		_data.acceptVisitor(_visitor);
		assertTrue(two_int.nbVisitedNode == 3);
		assertTrue(two_int.nbVisitedParam == 4);
		
	}

	@Test
	public void testDataIdentity(){
		HierarchicalData _data_1 = new HierarchicalData("test_data_1", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		
		HierarchicalData _data_2 = new HierarchicalData("test_data_2", 
				new InternationalizableTerm("testing data"), 
				new InternationalizableTerm("this data is used to test the HierarchicalData class"));
		
		assertFalse(_data_1.getDataIdentity().equals(_data_2.getDataIdentity()));
		assertTrue(_data_1.getDataIdentity().equals(_data_1.getDataIdentity()));
		assertTrue(HierarchicalData.getIdentifiedData(_data_1.getDataIdentity()).getDataToken().compareTo(_data_1.getDataToken())==0);
		
		DataIdentity _id_1 = _data_1.getDataIdentity();
		_data_1.setDataIdentity(new DataIdentity());
		
		
		assertTrue(HierarchicalData.getIdentifiedData(_id_1) == null);
		_id_1 = _data_1.getDataIdentity();
		assertTrue(HierarchicalData.getIdentifiedData(_id_1).getDataToken().compareTo(_data_1.getDataToken())==0);
		
		_data_1.forgetIdentity();
		assertTrue(HierarchicalData.getIdentifiedData(_id_1) == null);
		
	}
	
}
