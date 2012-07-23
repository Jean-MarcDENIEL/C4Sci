package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.exceptions.DataValueParsingException;
import c4sci.data.exceptions.NoSuchParameterException;
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
		public void setParameterValue(String str_to_parse)
				throws DataValueParsingException {
			try{
				paramValue = Integer.parseInt(str_to_parse);
			}
			catch(NumberFormatException _e){
				throw new DataValueParsingException("integer", str_to_parse, "integer parsing error", _e);
			}
			
		}
		
		public String getParameterValue() {
			return Integer.toString(paramValue);
		}

		@Override
		protected DataParameter getSameDataParameterInstance() {
			return new TestDataParameter(getParameterToken(), getParameterName(), getParameterDescription());
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
		} catch (NoSuchParameterException e) {
			fail();
		}
		
		try {
			_data.setParameterValue("error_param", "10");
			fail();
		} catch (DataValueParsingException e) {
			fail();
		} catch (NoSuchParameterException e) {
			assertTrue(true);
		}
		
		try {
			_data.setParameterValue("testParam", "a10");
			fail();
		} catch (DataValueParsingException e) {
			assertTrue(true);
		} catch (NoSuchParameterException e) {
			fail();
		}
		
		try {
			String _str = _data.getParameterValue("err_str");
			fail(_str + " should not have been read");
		} catch (NoSuchParameterException e) {
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
		
		
		DataVisitor _visitor = new DataVisitor() {
			
			public void performTreatmentOn(DataParameter data_param) {
				two_int.nbVisitedParam ++;
			}
			
			public void performTreatmentOn(HierarchicalData data_node) {
				two_int.nbVisitedNode ++;
			}
		};
		
		_data.acceptVisitor(_visitor);
		assertTrue(two_int.nbVisitedNode == 3);
		assertTrue(two_int.nbVisitedParam == 4);
		
	}

}
