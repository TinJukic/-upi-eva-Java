package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Stores elements
 * 
 * @author Tin Jukić
 *
 * @param <K> key
 * @param <V> value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Class used for storing elements inside SimpleHashtable.
	 * 
	 * @author Tin Jukić
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next; // inside the same slot

		/**
		 * Creates an instance of the TableEntry for storing elements inside
		 * SimpleHashtable.
		 * 
		 * @param key
		 * @param value
		 * @throws NullPointerException if the key is null
		 */
		public TableEntry(K key, V value) throws NullPointerException {
			if (key == null)
				throw new NullPointerException();

			this.key = key;
			this.value = value;
			this.next = null;
		}

		/**
		 * 
		 * @return key
		 */
		K getKey() {
			return this.key;
		}

		/**
		 * 
		 * @return value
		 */
		V getValue() {
			return this.value;
		}

		/**
		 * Overwrites existing value with the new value.
		 * 
		 * @param value
		 */
		void setValue(V value) {
			this.value = value;
		}
	}

	/**
	 * Iterator implementation for SimpleHashtable class.
	 * @author Tin Jukić
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private int currentIndex;
		private int lastModificationCount;
		private TableEntry<K, V>[] array;
		
		/**
		 * Constructor for the iterator instance.
		 * @param lastModificationCount
		 */
		IteratorImpl(int lastModificationCount) {
			this.lastModificationCount = lastModificationCount;
			this.array = toArray();
			this.currentIndex = -1;
		}
		
		/**
		 * @return true if there is next element inside the table.
		 * @throws ConcurrentModificationException if the table has been changed in the meantime
		 */
		public boolean hasNext() throws ConcurrentModificationException {
			if(this.lastModificationCount != modificationCount) throw new ConcurrentModificationException();
			
			if(this.currentIndex + 1 >= this.array.length) return false;
			return true;
		}

		/**
		 * @return the next element
		 * @throws ConcurrentModificationException if the table has been changed in the meantime
		 */
		public SimpleHashtable.TableEntry next() throws ConcurrentModificationException {
			if(this.hasNext()) {
				this.currentIndex++;
				var element = this.array[this.currentIndex];
				return element;
			}
			
			return null;
		}

		/**
		 * Removes the current element in iterator.
		 * @throws ConcurrentModificationException if the table has been changed in the meantime
		 */
		public void remove() throws ConcurrentModificationException, IllegalStateException {
			if(this.lastModificationCount != modificationCount) throw new ConcurrentModificationException();
			if(array[this.currentIndex] == null) throw new IllegalStateException();
			
			SimpleHashtable.this.remove(this.array[this.currentIndex].getKey());
			array[this.currentIndex] = null;
			this.lastModificationCount++;
		}
	}

	private TableEntry<K, V>[] table; // an array of slots inside the table
	private int size; // number of pairs stored inside the table (number of keys)
	private int modificationCount;

	/**
	 * Default constructor which creates a table that is 16 slots in size.
	 */
	public SimpleHashtable() {
		// creates table with 16 slot size
		this.size = 0;
		this.table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, 16);
		this.modificationCount = 0;
	}

	/**
	 * Constructor which creates a table that has number of slots that is the first
	 * number that is power of 2 <i>equal or greater</i> than the desired capacity.
	 * 
	 * @param capacity
	 * @throws IllegalArgumentException if the capacity is less than 1
	 */
	public SimpleHashtable(int capacity) throws IllegalArgumentException {
		if (capacity < 1)
			throw new IllegalArgumentException();

		this.size = 0;
		int c = (int) Math.pow(2, (int) Math.sqrt(capacity));
		if (c < capacity)
			c = (int) Math.pow(2, (int) Math.sqrt(capacity) + 1);
		this.table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, c);
		this.modificationCount = 0;
	}

	/**
	 * Searches the table and decides where to put the given element.
	 * 
	 * @param key
	 * @param value
	 * @return null if the element is put inside or the old value if the element
	 *         with the given key already exists
	 * @throws NullPointerException if the key is null
	 */
	public V put(K key, V value) throws NullPointerException {
		if (key == null)
			throw new NullPointerException();

		// check whether the table is overfilled
		double fillLevel = (double) this.size / (double) this.table.length;
		if (fillLevel >= 0.75) {
			// create new instance of table with double the size
			var helperArray = this.toArray();
			this.table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, this.table.length * 2);
			this.size = 0;

			for (var element : helperArray)
				put(element.getKey(), element.getValue());
		}

		// getting hash value of the given key
		int slot = Math.abs(key.hashCode()) % this.table.length;
		if (this.table[slot] != null) {
			// check whether the key already exists
			TableEntry<K, V> nextEntry = this.table[slot];
			while (nextEntry != null) {
				if (nextEntry.getKey().equals(key)) {
					V oldValue = nextEntry.getValue();
					nextEntry.setValue(value);
					return oldValue;
				}
				if (nextEntry.next == null)
					break;
				nextEntry = nextEntry.next;
			}
			nextEntry.next = new TableEntry<>(key, value);
			this.size += 1;
			this.modificationCount += 1;
			return null;
		} else {
			this.table[slot] = new TableEntry<>(key, value);
			this.size += 1;
			this.modificationCount += 1;
			return null;
		}
	}

	/**
	 * Searches the table by key.
	 * 
	 * @param key
	 * @return null if the key does not exist or is null or the value of the element
	 *         paired with the given key
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		int slot = Math.abs(key.hashCode()) % this.table.length;
		if (this.table[slot] != null) {
			TableEntry<K, V> nextEntry = this.table[slot];

			while (nextEntry != null) {
				if (nextEntry.getKey().equals(key))
					return nextEntry.getValue();
				nextEntry = nextEntry.next;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return the number of elements stored inside the table
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Searches the table in order to find desired key.
	 * 
	 * @param key
	 * @return true if the key is found or false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		int slot = Math.abs(key.hashCode()) % this.table.length;
		if (this.table[slot] != null) {
			TableEntry<K, V> nextEntry = this.table[slot];

			while (nextEntry != null) {
				if (nextEntry.getKey().equals(key))
					return true;
				nextEntry = nextEntry.next;
			}
		}
		return false;
	}

	/**
	 * Searches the table in order to find desired value.
	 * 
	 * @param value (can be null)
	 * @return true if the value was found or false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> nextEntry = this.table[i];

			while (nextEntry != null) {
				if (nextEntry.getValue().equals(value))
					return true;
				nextEntry = nextEntry.next;
			}
		}
		return false;
	}

	/**
	 * Searches the table.
	 * 
	 * @param key
	 * @return the value of the deleted element or null if the element was not found
	 *         or key is null
	 */
	public V remove(Object key) {
		if (key == null)
			return null;

		int slot = Math.abs(key.hashCode()) % this.table.length;
		if (this.table[slot] != null) {
			TableEntry<K, V> nextEntry = this.table[slot];
			if (nextEntry.getKey().equals(key)) {
				nextEntry.key = null;
				V oldValue = nextEntry.getValue();
				nextEntry.setValue(null);
				this.table[slot] = nextEntry.next;
				nextEntry.next = null;

				this.size -= 1;
				this.modificationCount += 1;

				return oldValue;
			}
			var previous = nextEntry;
			if (nextEntry.next != null)
				nextEntry = nextEntry.next;

			while (nextEntry != null) {
				if (nextEntry.getKey().equals(key)) {
					if (nextEntry.next != null) {
						previous.next = nextEntry.next;
						nextEntry.key = null;
						V oldValue = nextEntry.getValue();
						nextEntry.setValue(null);
						nextEntry.next = null;

						this.size -= 1;
						this.modificationCount += 1;

						return oldValue;
					} else {
						previous.next = null;
						nextEntry.key = null;
						V oldValue = nextEntry.getValue();
						nextEntry.setValue(null);

						this.size -= 1;
						this.modificationCount += 1;

						return oldValue;
					}
				}
				nextEntry = nextEntry.next;
			}
		}
		return null;
	}

	/**
	 * Searches the table.
	 * 
	 * @return true if the table is empty
	 */
	public boolean isEmpty() {
		for (int i = 0; i < this.table.length; i++)
			if (this.table[i] != null)
				return false;

		return true;
	}

	/**
	 * @return a string representation of values stored inside the table
	 */
	public String toString() {
		String s = "[";
		int numberOfElements = 0;
		for (int i = 0; i < this.table.length; i++)
			if (this.table[i] != null) {
				var entry = this.table[i];
				while (entry != null) {
					numberOfElements++;
					if (entry.next == null && numberOfElements == this.size())
						s = s + entry.getKey() + "=" + entry.getValue();
					else
						s = s + entry.getKey() + "=" + entry.getValue() + ", ";
					entry = entry.next;
				}
			}

		s += "]";
		if (!s.equals("[]"))
			return s;
		return null;
	}

	/**
	 * 
	 * @return an array representation of the table
	 */
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] array = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, this.size);
		int k = 0;
		for (int i = 0; i < this.table.length; i++)
			if (this.table[i] != null) {
				var entry = this.table[i];
				while (entry != null) {
					array[k] = entry;
					k++;
					entry = entry.next;
				}
			}

		return array;
	}

	/**
	 * Deletes all elements from the table. Doesn't change the capacity of the
	 * table.
	 */
	public void clear() {
		for (int i = 0; i < this.table.length; i++)
			if (this.table[i] != null) {
				var entry = this.table[i];
				while (entry != null) {
					remove(entry.getKey());
					entry = entry.next;
				}
			}
		this.modificationCount += 1;
		this.size = 0;
	}

	/**
	 * @return new instance of iterator class
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(this.modificationCount);
	}
}
