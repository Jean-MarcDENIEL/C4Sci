package c4sci.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import c4sci.data.internationalization.InternationalizableTerm;
/**
 * This map stores mapping relationship between HierarchicalData, based on the use of HierarchicalData's methods related to DataIdentity.<br>
 * <br>
 * To achieve persistence these relationships are stored internally and as child data at the same time.<br>
 * Modifying methods (add ...) impact on the two.
 * @author jeanmarc.deniel
 *
 * @param <K>
 * @param <V>
 */
public class HierarchicalDataMap<K extends HierarchicalData, V extends HierarchicalData> extends HierarchicalData implements Map<K, V> {

	private Map<DataIdentity, HDMapEntry>	keyEntryMap;
	
	public HierarchicalDataMap(String data_token,
			InternationalizableTerm data_name,
			InternationalizableTerm data_description) {
		super(data_token, data_name, data_description);
		keyEntryMap = new ConcurrentHashMap<DataIdentity, HDMapEntry>();
	}

	//CHECKSTYLE:OFF
	/**
	 * @returns the number of mapping elements.
	 */
	public int size() {
		//CHECKSTYLE:ON
		return keyEntryMap.size();
	}

	public boolean isEmpty() {
		return keyEntryMap.isEmpty();
	}

	public boolean containsKey(Object key_) {
		if (key_ == null){
			return false;
		}
		try{
			return keyEntryMap.containsKey(((HierarchicalData)key_).getDataIdentity());
		}
		catch(ClassCastException _e){}
		return false;
	}

	public boolean containsValue(Object value_) {
		if (value_ == null)
			return false;
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
		if (key_ == null){
			return null;
		}
		//CHECKSTYLE:ON
		try{
			return (V) HierarchicalData.getIdentifiedData(keyEntryMap.get(((HierarchicalData)key_).getDataIdentity()).getValueIdentity());
		}
		catch(ClassCastException _e){}
		return null;
	}

	/**
	 * Inserts a new (K,V) mapping and adds this relationship as a child data.<br>
	 * An existing (K,?) relationship will be replaced by the new one, as mapping and as child data.<br>
	 * @return null if key_ == null otherwise returns value_
	 */
	//CHECKSTYLE:OFF
	public V put(K key_, V value_) {
		if ((key_ == null)||(value_ == null)){
			return null;
		}
		//CHECKSTYLE:ON
		DataIdentity _id_k = key_.getDataIdentity();
		if (keyEntryMap.containsKey(_id_k)){
			HDMapEntry _removed_entry = keyEntryMap.get(_id_k);
			removeSubData(_removed_entry);
		}
		addSubData(new HDMapEntry(_id_k, value_.getDataIdentity()));
		return value_;
	}
	/**
	 * @param child_data If child_data is an instance of HDMapEntry, then it will added to the mapping relationships. <br>
	 * Only one HDMapEntry data will be inserted for a given HDMapEntry key: one mapping entry = one child data.
	 */
	public void addSubData(HierarchicalData child_data){
		if (child_data == null){
			return;
		}
		try{
			if (child_data instanceof HDMapEntry){
				HDMapEntry _entry = (HDMapEntry) child_data;
				boolean _key_contained = keyEntryMap.containsKey(_entry.getKeyIdentity());
				
				if (_key_contained){
					removeSubData(keyEntryMap.get(_entry.getKeyIdentity()));
				}
				keyEntryMap.put(_entry.getKeyIdentity(), _entry);
				super.addSubData(child_data);
			}
			else{
				super.addSubData(child_data);
			}
		}
		catch(ClassCastException _e){
			super.addSubData(child_data);
		}

	}
	/**
	 * @param if child_data is an instance of HDMapEntry, then it is removed as a mapping relationship and as a child data.
	 */
	public void removeSubData(HierarchicalData child_data){
		if (child_data == null){
			return;
		}
		if (child_data instanceof HDMapEntry){
			HDMapEntry _entry = (HDMapEntry) child_data;
			keyEntryMap.remove(_entry.getKeyIdentity());
		}
		super.removeSubData(child_data);
	}

	@SuppressWarnings("unchecked")
	//CHECKSTYLE:OFF
	/**
	 * Removes a relationship from the mapping and the corresponding HDMapEntry child data.
	 * @return null if key_ is not a valid mapping key.
	 */
	public V remove(Object key_) {
		if (key_ == null){
			return null;
		}
		//CHECKSTYLE:ON
		try{
			K _key = (K)key_;
			DataIdentity _id_key = _key.getDataIdentity();
			if (!keyEntryMap.containsKey(_id_key)){
				return null;
			}
			V _ret = (V) HierarchicalData.getIdentifiedData(keyEntryMap.get(_id_key).getValueIdentity());
			removeSubData(keyEntryMap.get(_id_key));
			return _ret;
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
	/**
	 * All mapping relationships are removed as well as mappings and child data
	 */
	public void clear() {
		//CHECKSTYLE:ON
		while (!isEmpty()){
			HierarchicalData _removed_entry = keyEntryMap.values().iterator().next();
			removeSubData(_removed_entry);
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
