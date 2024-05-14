package hr.fer.oprpp1.custom.collections;

/**
 * Stores elements with corresponding key and value
 * @author Tin Jukić
 *
 * @param <K> key
 * @param <V> value
 */
public class Dictionary<K,V> {
	private LinkedListIndexedCollection<Element<K,V>> collection;
	
	/**
	 * Public constructor for class dictionary.
	 */
	public Dictionary() {
		this.collection = new LinkedListIndexedCollection<>();
	}
	
	/**
	 * 
	 * A class for every created element
	 * @author Tin Jukić
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	private class Element<K,V> {
		private K key;
		private V value;
		
		/**
		 * Constructor
		 * @param key (cannot be null)
		 * @param value
		 * @throws NullPointerException
		 */
		Element(K key, V value) throws NullPointerException {
			if(key == null) throw new NullPointerException();
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * 
		 * @return key for the desired element
		 */
		K getKey() {
			return this.key;
		}
		
		/**
		 * 
		 * @return value for the desired element
		 */
		V getValue() {
			return this.value;
		}
		
		/**
		 * Updates the value of the element.
		 * @param value
		 */
		void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * 
	 * @return true if the dictionary is empty, false otherwise
	 */
	boolean isEmpty() {
		return this.collection.isEmpty();
	}
	
	/**
	 * 
	 * @return the size of the dictionary
	 */
	int size() {
		return this.collection.size();
	}
	
	/**
	 * Deletes <i>all elements</i> from the dictionary.
	 */
	void clear() {
		this.collection.clear();
	}
	
	/**
	 * Inserts a new element inside the dictionary. If the element with the given key
	 * <i>already exists</i>, method deletes old value and places given value instead.
	 * @param key
	 * @param value
	 * @return the value of the inserted element
	 */
	V put(K key, V value) {
		Element<K,V> element = new Element<>(key, value);
		if(get(key) != null) {
			// if the element already exists
			V oldValue = get(key);
			
			var elements = this.collection.toArray();
			for(var e : elements) {
				if(((Element<K, V>) e).getKey().equals(key)) {
					((Dictionary<K, V>.Element<K, V>) e).setValue(value);
					break;
				}
			}
			
			return oldValue;
		} else {
			this.collection.add(element);
			return null;
		}
	}
	
	/**
	 * Searches the dictionary by key.
	 * @param key
	 * @return <b>found value</b> or <b>null</b> if the <i>key does not exist</i>
	 *         or if <i>value</i> is not defined
	 */
	V get(Object key) {
		if(this.collection.size() == 0) return null;
		
		var elements = this.collection.toArray();
		for(var element : elements) {
			if(((Element<K, V>) element).getKey().equals(key)) return (V) ((Element<K, V>) element).getValue();
		}
		
		return null;
	}
	
	/**
	 * Searches the dictionary by key and <b>removes value</b> for the <i>given key</i>.
	 * @param key
	 * @return <i>removed</i> value
	 */
	V remove(K key) {
		var elements = this.collection.toArray();
		for(var element : elements) {
			if(((Dictionary<K, V>.Element<K, V>) element).getKey().equals(key)) {
				V value = ((Dictionary<K, V>.Element<K, V>) element).getValue();
				this.collection.remove(element);
				return value;
			}
		}
		
		return null;
	}
}
