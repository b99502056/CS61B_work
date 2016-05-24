public class ArrayDeque<Item> implements Deque<Item> {

    private Item[] items;
    private int size;
    private int RFACTOR = 2;
    private int arraySize;

    /* Two index numbers used addFront() and addLast(). */
    private int nextFirst;
    private int nextLast;

	/** Creates an empty array deque.
      * The initial size is set to be 8. */
	public ArrayDeque() {
        items = (Item[]) new Object[8];
        arraySize = 8;
        size = 0;
        nextFirst = arraySize / 2;
        nextLast = nextFirst + 1;
	}

    /** Resize the array when the number of items is equal to
      * the size. */
    private void resize (int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
        arraySize = capacity;
        /* Update nextFront and nextLast. */
        nextFirst = size * RFACTOR - 1;
        nextLast = size;
    }

    /** Adds an item to the front of the Deque. */
    @Override
    public void addFirst(Item i) {
        if (size == arraySize) {
            resize(size * RFACTOR);
        }
        items[nextFirst] = i;
        /* Update nextFirst. */
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst = arraySize - 1;
        }
        size += 1;
	}

    /** Adds an item to the back of the Deque. */
    @Override
    public void addLast(Item i) {
        if (size == arraySize) {
            resize(size * RFACTOR);
        }
        items[nextLast] = i;
        /* Update nextLast. */
        nextLast += 1;
        if (nextLast == arraySize) {
            nextLast = 0;
        }
        size += 1;
	}
	/** Return true if deque is empty, false otherwise. */
	@Override
	public boolean isEmpty() {
        if (nextFirst == arraySize - 1 && nextLast == 0) {
            return true;
        } else if (nextLast == nextFirst + 1) {
            return true;
        } else {
            return false;
        }
	}
	/** Returns the number of items in the deque. */
	@Override
	public int size() {
        return size;
	}

	/** Prints the items in the deque from first to last,
	  * separated by a space. */
	@Override
	public void printDeque() {
        /* Two cases:
         * 1. The front of the deque is BEFORE the back in the array.
         * 2. The front of the deque is AFTER the back in the array. */
        if (nextFirst < nextLast) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        } else {
            for (int i = nextFirst + 1; i < arraySize; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
	}

	/** Remove and return the item at the front of the deque.
	  * If no such item exists, return null. */
    /** Note: The usage factor of arrays of length 16 or more
     * should always be at least 25%. */
    @Override
    public Item removeFirst() {
        /* Beware of the case where the nextFirst goes to
         * the back of the array. */
        Item toBeReturn;
        if (nextFirst == arraySize) {
            toBeReturn = items[0];
            items[0] = null;
            /* Update nextFirst. */
            nextFirst = 0;
        } else {
            toBeReturn = items[nextFirst + 1];
            items[nextFirst + 1] = null;
            nextFirst += 1;
        }
        size -= 1;
        return toBeReturn;
	}

	/** Remove and return the item at the back of the deque.
	  * If no such item exists, return null. */
	@Override
	public Item removeLast() {
        /* Beware of the case where the nextLast goes to
         * the front of the array. */
        Item toBeReturn;
        if (nextLast == 0) {
            toBeReturn = items[arraySize - 1];
            items[arraySize - 1] = null;
            /* Update nextLast. */
            nextLast = arraySize - 1;
        } else {
            toBeReturn = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast -= 1;
        }

        size -= 1;
        return toBeReturn;
	}

	/** Gets the item at the given index, where 0 is the front,
	  * 1 is the next item, and so forth. If no such item exists,
	  * return null. Must not alter the deque! */
	@Override
	public Item get(int index) {
		if (index < 0) {
            return null;
        } else if (index >= size) {
            return null;
        }

        int actualIndex = index + nextFirst + 1;
        if (actualIndex > arraySize) {
            actualIndex -= arraySize;
        }
        return items[actualIndex];
	}
}