package editor;

import org.apache.commons.collections.ArrayStack;

import java.util.Iterator;
import java.util.ArrayList;

/**
 * Created by LouisCho on 6/3/16.
 */
public class FastLinkedList<T> implements Iterable<T> {
	private Node sentinel;
	private Node currentNode;
	// For the first item, currentPos = 1, and so on.
	private int currentPos;
	// This array list stores nodes according to their corresponding positions
	// in the linked list.
	private ArrayList<Node> nodeArray;
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
		initialize();
	}
	// Each 'char' here should be a 'Text' object.
	public void addChar(T t) {
		Node oldCurrentNode = currentNode;
		Node newCurentNode = new Node(oldCurrentNode, t, oldCurrentNode.next);
		currentNode = newCurentNode;
		oldCurrentNode.next = currentNode;
		currentPos += 1;
		size += 1;
		nodeArray.add(currentPos, currentNode);
	}

	public void deleteChar() {
		// If at the very front of the text, do nothing.
		if (currentPos == 0) {
			return;
		}
		Node nodeToBeDeleted = currentNode;
		currentNode = currentNode.prev;
		currentNode.next = nodeToBeDeleted.next;
		nodeArray.remove(currentPos);
		currentPos -= 1;
		size -= 1;
	}

	private void initialize() {
		// \0 is for empty character.
		sentinel = new Node(sentinel, null, sentinel);

		// 0 represents the first position of the text. No deletion can take place at this position.
		currentPos = 0;
		currentNode = sentinel;
		nodeArray = new ArrayList<>();
		// nodeArray[1] is the first letter of the text file, so we
		// store null to the 0th position.
		nodeArray.add(0, null);
		size = 0;
	}

	public void clear() {
		initialize();
	}

	public T currentChar() {
		return currentNode.item;
	}

	public void changeCurrentPosTo(int p) {
		if (p == 0) {
			currentNode = null;
		} else {
			currentNode = nodeArray.get(p);
			currentPos = p;
		}
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
