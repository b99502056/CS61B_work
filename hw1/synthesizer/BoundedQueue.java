package synthesizer;
import java.util.Iterator;

/**
 * Created by LouisCho on 5/27/16.
 */
public interface BoundedQueue<T> extends Iterable<T> {
	int capacity(); // Return size of the buffer.
	int fillCount(); // Return number of items currently in the buffer.
	void enqueue(T x); // Add item x to the end
	T dequeue(); // Delete and return item from the front.
	T peek(); // Return(but not delete) the item from the front.

	// Needed abstract methods for iteration.
	Iterator<T> iterator();

	// is the buffer empty (fillCount equals to 0)?
	default boolean isEmpty() {
		if (fillCount() == 0) {
			return true;
		}
		return false;
	}
	// is the buffer full (fillCount is the same as capacity)?
	default boolean isFull() {
		if (fillCount() == capacity()) {
			return true;
		}
		return false;
	}
}
