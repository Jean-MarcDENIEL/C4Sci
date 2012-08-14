package c4sci.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.text.Keymap;

import c4sci.data.internationalization.InternationalizableTerm;

public class HierarchicalDataMap<K extends HierarchicalData, V extends HierarchicalData> extends HierarchicalData implements Map<K, V> {

	public class HDMapEntry extends HierarchicalData{

		DataIdentity	keyIdentity;
		DataIdentity	valueIdentity;
		
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
		
	};
	
	private Map<DataIdentity, HDMapEntry>	keyEntryMap;
	
	public HierarchicalDataMap(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description) {
		super(data_token, data_name, data_description);

		keyEntryMap = new ConcurrentHashMap<DataIdentity, HDMapEntry>();
	}

	public int size() {
		return keyEntryMap.size();
	}

	public boolean isEmpty() {
		return keyEntryMap.isEmpty();
	}

	public boolean containsKey(Object key_) {
		try{
			return keyEntryMap.containsKey(((HierarchicalData)key_).getDataIdentity());
		}
		catch(ClassCastException _e){}
		return false;
	}

	public boolean containsValue(Object value_) {
		try{
			DataIdentity _value_id = ((HierarchicalData)value_).getDataIdentity();
			Iterator<HDMapEntry> _it = keyEntryMap.values().iterator();
			while (_it.hasNext()){
				if (_it.next().getValueIdentity().equals(_value_id)){
					return true;
				}
			}
		}
		catch(ClassCastException _e){}
		return false;
	}

	
	@SuppressWarnings("unchecked")
	public V get(Object key_) {
		try{
			return (V) HierarchicalData.getIdentifiedData(keyEntryMap.get(((HierarchicalData)key_).getDataIdentity()).getValueIdentity());
		}
		catch(ClassCastException _e){}
		return null;
	}

	public V put(K key_, V value_) {

		DataIdentity id_k = key_.getDataIdentity();
		if (keyEntryMap.containsKey(key_)){
			HDMapEntry _removed_entry = keyEntryMap.get(id_k);
			removeSubData(_removed_entry);
		}
		addSubData(new HDMapEntry(id_k, value_.getDataIdentity()));
		return value_;
	}
	
	public void addSubData(HierarchicalData child_data){
		try{
			@SuppressWarnings("unchecked")
			HDMapEntry _entry = (HDMapEntry) child_data;
			keyEntryMap.put(_entry.getKeyIdentity(), _entry);
		}
		catch(ClassCastException _e){}
		super.addSubData(child_data);
	}

	@SuppressWarnings("unchecked")
	public V remove(Object key_) {
		try{
			K _key = (K)key_;
			if (!keyEntryMap.containsKey(_key)){
				return null;
			}
			removeSubData(keyEntryMap.get(_key));
			return (V) HierarchicalData.getIdentifiedData(keyEntryMap.remove(key_).getValueIdentity());
		}
		catch(ClassCastException _e){}
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		Set<? extends Map.Entry<? extends K,? extends V>> _key_set = m.entrySet();
		Iterator<? extends Map.Entry<? extends K,? extends V>> _it = _key_set.iterator();
		while (_it.hasNext()){
			Entry<? extends K, ? extends V> _entry = _it.next();
			put(_entry.getKey(), _entry.getValue());
		}
	}

	public void clear() {
		while (!isEmpty()){
			HierarchicalData _value = HierarchicalData.getIdentifiedData(keyEntryMap.keySet().iterator().next());
			removeSubData(_value);
		}
	}

	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		Set<K> _res = new HashSet<K>();
		Iterator<DataIdentity> _id_set = keyEntryMap.keySet().iterator();
		while (_id_set.hasNext()){
			try{
				_res.add((K) HierarchicalData.getIdentifiedData(_id_set.next()));
			}
			catch(ClassCastException _e){}
		}
		return _res;
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		Collection<V> _res = new ArrayList<V>();
		Iterator<HDMapEntry> _it = keyEntryMap.values().iterator();
		while (_it.hasNext()){
			try{
				_res.add((V) HierarchicalData.getIdentifiedData(_it.next().getValueIdentity()));
			}
			catch(ClassCastException _e){}
		}
		return _res;
	}

	@SuppressWarnings("unchecked")
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		
		class LocalEntry implements Map.Entry<K, V>{

			private K keyObject;
			private V valueObject;
			public LocalEntry(K key_, V value_){
				keyObject 	= key_;
				valueObject = value_; 
			}
			
			public K getKey() {
				return keyObject;
			}

			public V getValue() {
				return valueObject;
			}

			public V setValue(V value_) {
				valueObject = value_;
				return value_;
			}
			
		}
		
		Set<Map.Entry<K,V>> _res = new HashSet<Map.Entry<K,V>>();
		Iterator<DataIdentity> _key_it = keyEntryMap.keySet().iterator();
		while (_key_it.hasNext()){
			DataIdentity _key = _key_it.next();
			HDMapEntry _entry = keyEntryMap.get(_key);
			try{
				_res.add(new LocalEntry((K)HierarchicalData.getIdentifiedData(_key), (V)HierarchicalData.getIdentifiedData(_entry.getValueIdentity())));
			}
			catch(ClassCastException _e){}
		}
		
		return _res;
	}

}
