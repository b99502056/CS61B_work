package synthesizer;
import synthesizer.AbstractBoundedQueue;
import java.util.Iterator;

// This ArrayRingBuffer is now an iterable.
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        /* Create new array with capacity elements.
         * first, last, and fillCount should all be set to 0.
         * this.capacity should be set appropriately. Note that the local variable
         * here shadows the field we inherit from AbstractBoundedQueue, so
         * you'll need to use this.capacity to set the capacity. */
        first = 0;
        last = 0;
        this.capacity = capacity;
        // Initialize the fillCount.
        fillCount = 0;
        rb = (T[]) new Object[capacity];

    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        if (last == capacity - 1) {
            last = 0;
        } else {
            last += 1;
        }
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and update fillCount.
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }

        T deletedItem = rb[first];
        rb[first] = null;
        if (first == capacity - 1) {
            first = 0;
        } else {
            first += 1;
        }
        fillCount -= 1;
        return deletedItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            return null;
        }
        return rb[first];
    }

    // Create a class that implements the interface Iterator, which is returned by the following "iterator()".
    private class bufferIterator implements Iterator<T> {

        private int currentIndex;

        public bufferIterator() {
            currentIndex = first;
        }

        @Override
        public boolean hasNext() {
            // Check if the head is in front of the tail in terms of array index, or otherwise.
            if (first < last) {
                return currentIndex < last;
            } else if(last < first) {
                if ((currentIndex >= first) && (currentIndex <= capacity)) {
                    return true;
                } else {
                    return currentIndex < last;
                }
            }
            // first == last, the list is empty.
            else {
                return false;
            }
        }

        @Override
        public T next() {
            T currentThing = rb[currentIndex];

            if (currentIndex == capacity) {
                currentIndex = 0;
            } else {
                currentIndex += 1;
            }
            return currentThing;
        }
    }
    // Methods needed to support the interface Iterable.
    @Override
    public Iterator<T> iterator() {
        return new bufferIterator();
    }



}
