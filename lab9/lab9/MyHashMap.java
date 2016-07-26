package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * Created by LouisCho on 7/16/16.
 */
public class MyHashMap<K, V> implements Map61B<K,V> {

	private static final int INIT_SIZE = 100;
	private static final double INIT_LOAD_FACTOR = 2.0;
	private int hashTableSize;
	private int pairNum;
	private double loadFactor;
	private LinkedList<Node>[] hashTable;
	private HashSet<K> keySet;

	private class Node {
		private K key;
		private V value;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K key() {
			return key;
		}

		public V value() {
			return value;
		}
	}

	public  MyHashMap() {
		this(INIT_SIZE, INIT_LOAD_FACTOR);
	}

	public MyHashMap(int initialSize) {
		this(initialSize, INIT_LOAD_FACTOR);
	}

	public MyHashMap(int initialSize, double loadFactor) {
		hashTableSize = initialSize;
		this.loadFactor = loadFactor;
		hashTable = new LinkedList[initialSize];
		pairNum = 0;
		keySet = new HashSet<K>();
	}

	private int hash(K key) {
		return ((key.hashCode() & 0x7fffffff) % hashTableSize);
	}

	private void resize(int newSize) {
		MyHashMap temp = new MyHashMap(newSize, loadFactor);

		for (int i = 0; i < hashTableSize; i += 1) {
			if (hashTable[i] != null) {
				for (Node x : hashTable[i]) {
					temp.put(x.key(), x.value());
				}
			}
		}

		// Only hash table and its size are changed during resizing, so we need to update only these two things.
		this.hashTableSize = temp.hashTableSize;
		this.hashTable = temp.hashTable;
	}

	/** Removes all of the mappings from this map. */
	@Override
	public void clear() {
		hashTable = new LinkedList[hashTableSize];
		pairNum = 0;
		keySet.clear();
	}

	/* Returns true if this map contains a mapping for the specified key. */
	@Override
	public boolean containsKey(K key) {
		if (key == null) {
			throw new NullPointerException("cannot accept null argument");
		}
		return (get(key) != null);
	}

	/* Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 */
	@Override
	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("cannot accept null argument");
		}

		// The position of the key in the hash table.
		int keyHashValue = hash(key);
		LinkedList<Node> bucket = hashTable[keyHashValue];

		// If the bucket is empty, the value to the key must not exist. Return null.
		if (bucket == null) {
			return null;
		}

		for (Node x : bucket) {
			// If the given key is in the bucket, return the value corresponding to the key.
			if (x.key() == key) {
				return x.value();
			}
		}
		// Else, the key is not in the bucket, return null.
		return null;
	}

	/* Returns the number of key-value mappings in this map. */
	@Override
	public int size() {
		return pairNum;
	}

	/* Associates the specified value with the specified key in this map. */
	@Override
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("cannot accept null argument");
		}

		if (!containsKey(key)) {
			int keyHashValue = hash(key);

			// If the bucket has not contained anything, create a new one.
			if (hashTable[keyHashValue] == null) {
				hashTable[keyHashValue] = new LinkedList();
			}
			hashTable[keyHashValue].addLast(new Node(key, value));
			pairNum += 1;
			keySet.add(key);
		} else {
			throw new IllegalArgumentException("The input key already exists in the map.");
		}

		if ((pairNum / hashTableSize) > loadFactor) {
			resize(hashTableSize * 2);
		}
	}

	/* Returns a Set view of the keys contained in this map. */
	@Override
	public Set<K> keySet() {
		return keySet;
	}

	/* Removes the mapping for the specified key from this map if present.
	 * Not required for Lab 8. If you don't implement this, throw an
	 * UnsupportedOperationException. */
	@Override
	public V remove(K key) {
		throw new UnsupportedOperationException("This method has not been created yet.");
	}

	/* Removes the entry for the specified key only if it is currently mapped to
	 * the specified value. Not required for Lab 8. If you don't implement this,
	 * throw an UnsupportedOperationException.*/
	@Override
	public V remove(K key, V value) {
		throw new UnsupportedOperationException("This method has not been created yet.");
	}


	@Override
	public Iterator<K> iterator() {
		return keySet.iterator();
	}

	public static void main(String[] args) {
		MyHashMap m = new MyHashMap(4, 2.0);

		for (int i = 0; i < 100; i += 1) {
			m.put("hi" + i, i);
		}

		for (Object key : m) {
			System.out.println(key);
		}

		System.out.println(m.keySet());
		System.out.println(m.size());
		System.out.println(null != m.get("hi1") && m.containsKey("hi0"));
	}

}
