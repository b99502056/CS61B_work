package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by LouisCho on 7/16/16.
 */
public class MyHashMap<K extends Comparable<K>, V> implements Map61B<K,V> {

	/** Removes all of the mappings from this map. */
	@Override
	public void clear() {

	}

	/* Returns true if this map contains a mapping for the specified key. */
	@Override
	public boolean containsKey(K key) {
		return true;
	}

	/* Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 */
	@Override
	public V get(K key) {
		return null;
	}

	/* Returns the number of key-value mappings in this map. */
	@Override
	public int size() {
		return 0;
	}

	/* Associates the specified value with the specified key in this map. */
	@Override
	public void put(K key, V value) {

	}

	/* Returns a Set view of the keys contained in this map. */
	@Override
	public Set<K> keySet() {
		return null;
	}

	/* Removes the mapping for the specified key from this map if present.
	 * Not required for Lab 8. If you don't implement this, throw an
	 * UnsupportedOperationException. */
	@Override
	public V remove(K key) {
		return null;
	}

	/* Removes the entry for the specified key only if it is currently mapped to
	 * the specified value. Not required for Lab 8. If you don't implement this,
	 * throw an UnsupportedOperationException.*/
	@Override
	public V remove(K key, V value) {
		return null;
	}

	private class MapIterator implements Iterator<K> {


		public MapIterator() {

		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public K next() {
			return null;
		}
	}

	@Override
	public Iterator<K> iterator() {
		return new MyHashMap.MapIterator();
	}

}
