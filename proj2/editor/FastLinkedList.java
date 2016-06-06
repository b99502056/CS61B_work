package editor;

import java.util.Iterator;
/**
 * Created by LouisCho on 6/3/16.
 */
public class FastLinkedList<T> implements Iterable<T> {
	private Node sentinel;
	private Node currentNode;
	private int currentPos;
	private int size;

	private class Node {
		public Node prev;
		public Node next;
		public T item;

		public Node (Node p, T c, Node n) {
			prev = p;
			item = c;
			next = n;
		}
	}

	public FastLinkedList() {

		// \0 is for empty character.
		sentinel = new Node(sentinel, null, sentinel);

		// -1 represents the first position of the text. No deletion can take place at this position.
		currentPos = -1;
		currentNode = sentinel;
		size = 0;
	}
	// Each 'char' here should be a 'Text' object.
	public void addChar(T t) {
		Node oldCurrentNode = currentNode;
		Node newCurentNode = new Node(oldCurrentNode, t, oldCurrentNode.next);
		currentNode = newCurentNode;
		oldCurrentNode.next = currentNode;
		currentPos += 1;
		size += 1;
	}

	public void deleteChar() {
		// If at the very front of the text, do nothing.
		if (currentPos == -1) {
			return;
		}
		Node nodeToBeDeleted = currentNode;
		currentNode = currentNode.prev;
		currentNode.next = nodeToBeDeleted.next;
		currentPos -= 1;
		size -= 1;
	}

	public int currentPos() {
		return currentPos;
	}

	public void changeCurrentPos(Node n, int p) {
		currentNode = n;
		currentPos = p;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return !(size > 0);
	}

	// Code needed to support iteration.
	private class listIterator implements Iterator<T> {
		private int currentIndex;
		private Node n;

		public listIterator() {
			currentIndex = 0;
			n = sentinel.next;
		}
		@Override
		public boolean hasNext() {
			if (currentIndex < size) {
				return true;
			} else {
				return false;
			}
		}
		@Override
		public T next() {
			T currentThing = n.item;
			n = n.next;
			currentIndex += 1;
			return currentThing;
		}
	}
	@Override
	public Iterator<T> iterator() {
		return new listIterator();
	}
}
