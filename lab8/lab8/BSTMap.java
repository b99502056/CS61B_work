package lab8;
import edu.princeton.cs.algs4.BST;

import java.util.Set;
import java.util.Iterator;
/**
 * Created by LouisCho on 6/23/16.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

	private K key;
	private V value;
	private BSTMap left;
	private BSTMap right;

	public BSTMap() {
		key = null;
		value = null;
		left = null;
		right = null;
	}

	public BSTMap(K k, V v) {
		key = k;
		value = v;
		left = null;
		right = null;
	}

	@Override
	public void clear(){
		key = null;
		value = null;
		left = null;
		right = null;
	}

	@Override
	public boolean containsKey(K key) {
		if (get(key) == null) {
			return false;
		} else {
			return true;
		}
	}


	@Override
	public V get(K k) {
		if (k == null) {
			return null;
		}
		if (key == null) {
			return null;
		}

		int cmp = k.compareTo(key);
		if (cmp == 0) {
			return value;
		} else if (cmp > 0) {
			if (right == null) {
				return null;
			}
			return (V) right.get(k);
		} else {
			if (left == null) {
				return null;
			}
			return (V) left.get(k);
		}
	}

	@Override
	public int size() {
		if (key == null) {
			return 0;
		}

		int leftSize = (left == null) ? 0 : left.size();
		int rightSize = (right == null) ? 0 : right.size();
		return leftSize + rightSize + 1;
	}

	@Override
	public void put(K k, V v) {

		if (key == null) {
			key = k;
			value = v;
			return;
		}

		int cmp = k.compareTo(key);

		if (cmp == 0) {
			value = v;
		} else if (cmp > 0) {
			if (right == null) {
				right = new BSTMap(k, v);
				return;
			}
			right.put(k, v);
		} else {
			if (left == null) {
				left = new BSTMap(k, v);
				return;
			}
			left.put(k, v);
		}
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException("This method is not supported at this time.");
	}

	@Override
	public V remove(K key) {
		throw new UnsupportedOperationException("This method is not supported at this time.");
	}

	@Override
	public V remove(K key, V value) {
		throw new UnsupportedOperationException("This method is not supported at this time.");
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
		return new MapIterator();
	}

}
