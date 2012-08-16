package c4sci.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.internationalization.InternationalizableTerm;

public class HierarchicalDataMap<K extends HierarchicalData, V extends HierarchicalData> extends HierarchicalData implements Map<K, V> {

	private Map<DataIdentity, HDMapEntry>	keyEntryMap;
	
	public HierarchicalDataMap(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description) {
		super(data_token, data_name, data_description);

		keyEntryMap = new ConcurrentHashMap<DataIdentity, HDMapEntry>();
	}

	//CHECKSTYLE:OFF
	public int size() {
		//CHECKSTYLE:ON
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
	//CHECKSTYLE:OFF
	public V get(Object key_) {
		//CHECKSTYLE:ON
		try{
			return (V) HierarchicalData.getIdentifiedData(keyEntryMap.get(((HierarchicalData)key_).getDataIdentity()).getValueIdentity());
		}
		catch(ClassCastException _e){}
		return null;
	}
	//CHECKSTYLE:OFF
	public V put(K key_, V value_) {
		//CHECKSTYLE:ON
		DataIdentity _id_k = key_.getDataIdentity();
		if (keyEntryMap.containsKey(_id_k)){
			HDMapEntry _removed_entry = keyEntryMap.get(_id_k);
			removeSubData(_removed_entry);
		}
		addSubData(new HDMapEntry(_id_k, value_.getDataIdentity()));
		return value_;
	}
	//@SuppressWarnings("unchecked")
	public void addSubData(HierarchicalData child_data){
		try{
			if (child_data instanceof HDMapEntry){
				HDMapEntry _entry = (HDMapEntry) child_data;
				keyEntryMap.put(_entry.getKeyIdentity(), _entry);
			}
		}
		catch(ClassCastException _e){}
		super.addSubData(child_data);
	}

	@SuppressWarnings("unchecked")
	//CHECKSTYLE:OFF
	public V remove(Object key_) {
		//CHECKSTYLE:ON
		try{
			K _key = (K)key_;
			DataIdentity _id_key = _key.getDataIdentity();
			if (!keyEntryMap.containsKey(_id_key)){
				return null;
			}
			removeSubData(keyEntryMap.get(_id_key));
			return (V) HierarchicalData.getIdentifiedData(keyEntryMap.remove(_id_key).getValueIdentity());
		}
		catch(ClassCastException _e){}
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m_) {
		Set<? extends Map.Entry<? extends K,? extends V>> _key_set = m_.entrySet();
		Iterator<? extends Map.Entry<? extends K,? extends V>> _it = _key_set.iterator();
		while (_it.hasNext()){
			Entry<? extends K, ? extends V> _entry = _it.next();
			put(_entry.getKey(), _entry.getValue());
		}
	}

	//CHECKSTYLE:OFF
	public void clear() {
		//CHECKSTYLE:ON
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
	//CHECKSTYLE:OFF
	public Collection<V> values() {
		//CHECKSTYLE: ON
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
