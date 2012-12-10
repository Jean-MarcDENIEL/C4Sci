package c4sci.data;

import static org.junit.Assert.*;

import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import c4sci.data.internationalization.InternationalizableTerm;

public class TestHierarchicalDataMap {

	class CountingVisitor implements HierarchicalDataVisitor{

		public int	counterValue;
		public CountingVisitor() {
			counterValue = 0;
		}
		public void performTreatmentOn(HierarchicalData data_node) {
			counterValue ++;
		}

		public void performTreatmentOn(HierarchicalData current_node, DataParameter data_param) {
		}
		public void beginDataParametersSession(HierarchicalData current_node) {}
		public void endDataParametersSession(HierarchicalData current_node) {}
		public void endTreatmentOn(HierarchicalData data_node) {}
		public void beginSubDataSession(HierarchicalData current_node) {
		}
		public void endSubDataSession(HierarchicalData current_node) {
		}
		
	}

	@Test
	public void testHierarchicalDataMap() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		assertTrue(_map != null);

	}

	@Test
	public void testSize() {
		

		
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		assertTrue(_map.size() == 0);
		_map.put(_data_1, _data_2);
		_map.put(_data_3,  _data_4);
		assertTrue(_map.size() == 2);
		
		CountingVisitor _count_1 = new CountingVisitor();
		_map.acceptVisitor(_count_1);
		assertTrue(_count_1.counterValue == 3);
		
		_map.put(_data_3,  _data_4);
		assertTrue(_map.size() == 2);
		
		_map.remove(_data_1);
		assertTrue(_map.size()==1);
		
		_map.put(null,  _data_3);
		assertTrue(_map.size() == 1);
		
		CountingVisitor _count_2 = new CountingVisitor();
		_map.acceptVisitor(_count_2);
		assertTrue(_count_2.counterValue == 2);
		
		_map.put(_data_4,  null);
		assertTrue(_map.size() == 1);
		_map.remove(null);
		assertTrue(_map.size() == 1);
	}

	@Test
	public void testIsEmpty() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		assertTrue(_map.isEmpty());
		_map.put(_data_1, _data_2);
		_map.put(_data_3,  _data_4);
		assertFalse(_map.isEmpty());
		_map.remove(_data_1);
		assertFalse(_map.isEmpty());
		_map.remove(_data_3);
		assertTrue(_map.isEmpty());
		
		CountingVisitor _count_1 = new CountingVisitor();
		_map.acceptVisitor(_count_1);
		assertTrue(_count_1.counterValue == 1);
	}

	@Test
	public void testContainsKey() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map.put(_data_1, _data_2);
		_map.put(_data_3,  _data_4);
		assertTrue(_map.containsKey(_data_1));
		assertFalse(_map.containsKey(_data_2));
		assertFalse(_map.containsKey(null));
	}

	@Test
	public void testContainsValue() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map.put(_data_1, _data_2);
		_map.put(_data_3,  _data_4);
		assertTrue(_map.containsValue(_data_2));
		assertFalse(_map.containsValue(null));
		assertFalse(_map.containsValue(_data_1));
	}

	@Test
	public void testGet() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map.put(_data_1, _data_2);
		_map.put(_data_3, _data_4);
		
		assertFalse(((HierarchicalData)_map.get(_data_1)).getDataIdentity().equals(_data_1.getDataIdentity()));
		assertTrue(((HierarchicalData)_map.get(_data_1)).getDataIdentity().equals(_data_2.getDataIdentity()));
		assertTrue(_map.get(_data_1).equals(_data_2));
		
	}

	@Test
	public void testPut() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map.put(_data_1, _data_2);
		_map.put(_data_3, _data_4);
		
	}

	@Test
	public void testRemove() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map.put(_data_1, _data_2);
		_map.put(_data_3, _data_4);
		
		_map.remove(_data_1);
		_map.remove(_data_1);
		assertTrue(_map.size() == 1);
		
		CountingVisitor _count = new CountingVisitor();
		_map.acceptVisitor(_count);
		assertTrue(_count.counterValue == 2);
	}

	@Test
	public void testPutAll() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_1 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_2 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map_1.put(_data_1, _data_2);
		_map_1.put(_data_3, _data_4);
		
		_map_2.put(_data_2, _data_3);
		_map_2.put(_data_4, _data_1);
		
		_map_1.putAll(_map_2);
		assertTrue(_map_1.size() == 4);
		
		CountingVisitor _count = new CountingVisitor();
		_map_1.acceptVisitor(_count);
		assertTrue(_count.counterValue == 5);
	}

	@Test
	public void testClear() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_1 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map_1.put(_data_1, _data_2);
		_map_1.put(_data_3, _data_4);
		
		_map_1.clear();
		assertTrue(_map_1.isEmpty());
		assertTrue(_map_1.size() == 0);
		
		CountingVisitor _count = new CountingVisitor();
		_map_1.acceptVisitor(_count);
		assertTrue(_count.counterValue == 1);
		
	}

	@Test
	public void testKeySet() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_1 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map_1.put(_data_1, _data_2);
		_map_1.put(_data_3, _data_4);
		
		assertTrue(_map_1.keySet().size() == 2);
		_map_1.clear();
		assertTrue(_map_1.keySet().size() == 0);
	}

	@Test
	public void testValues() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_1 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map_1.put(_data_1, _data_2);
		_map_1.put(_data_3, _data_4);
		
		assertTrue(_map_1.values().size() == 2);
		_map_1.clear();
		assertTrue(_map_1.values().size() == 0);
	}

	@Test
	public void testEntrySet() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_1 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_4 = new HierarchicalData("data_4",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		_map_1.put(_data_1, _data_2);
		_map_1.put(_data_3, _data_4);
		
		Set<Entry<HierarchicalData, HierarchicalData>> _entries = _map_1.entrySet();
		assertTrue(_entries.size() == 2);
	}

	@Test
	public void testAddSubData() {
		HierarchicalDataMap<HierarchicalData, HierarchicalData> _map_1 = 
				new HierarchicalDataMap<HierarchicalData, HierarchicalData>("tst_map", 
						new InternationalizableTerm(""), 
						new InternationalizableTerm(""));
		HierarchicalData _data_1 = new HierarchicalData("data_1",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_2 = new HierarchicalData("data_2",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		HierarchicalData _data_3 = new HierarchicalData("data_3",
				new InternationalizableTerm(""), 
				new InternationalizableTerm(""));
		final HDMapEntry _1_2_entry = new HDMapEntry(_data_1.getDataIdentity(), _data_2.getDataIdentity());
		_map_1.addSubData(_1_2_entry);
		assertTrue(_map_1.size() == 1);
		CountingVisitor _count_1 = new CountingVisitor();
		_map_1.acceptVisitor(_count_1);
		assertTrue("counter = "+_count_1.counterValue+" instead of 2", _count_1.counterValue == 2);
		
		_map_1.addSubData(new HDMapEntry(_data_1.getDataIdentity(), _data_3.getDataIdentity()));
		assertTrue(_map_1.size() == 1);
		CountingVisitor _count_2 = new CountingVisitor(){
			public void performTreatmentOn(HierarchicalData node_){
				if (node_.getDataIdentity().equals(_1_2_entry.getDataIdentity())){
					counterValue ++;
				}
			}
		};
		_map_1.acceptVisitor(_count_2);
		assertTrue(_count_2.counterValue == 0);
		assertTrue(_map_1.get(_data_1).equals(_data_3));
		
		
		_map_1.addSubData(new HierarchicalData("koko", new InternationalizableTerm(""), new InternationalizableTerm("")));
		assertTrue(_map_1.size() == 1);
		CountingVisitor _count_3 = new CountingVisitor();
		_map_1.acceptVisitor(_count_3);
		assertTrue(_count_3.counterValue == 3);
		
	}
	
	@Test
	public void testRemoveSubData(){
		
	}

}
