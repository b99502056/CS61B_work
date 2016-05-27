package synthesizer;

/**
 * Created by LouisCho on 5/27/16.
 */
public interface BoundedQueue<T> {
	int capacity(); // Return size of the buffer.
	int fillCount(); // Return number of items currently in the buffer.
	void enque(T x); // Add item x to the end
	T dequeue(); // Delete and return item from the front.
	T peek(); // Return(but not delete) the item from the front.

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
