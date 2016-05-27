package synthesizer;

/**
 * Created by LouisCho on 5/27/16.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
	protected int fillCount;
	protected int capacity;
	public int capacity() {
		return capacity;
	}
	public int fillCount() {
		return fillCount;
	}
	@Override
	public boolean isEmpty() {
		if (fillCount() == 0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isFull() {
		if (fillCount() == capacity()) {
			return true;
		}
		return false;
	}
	public abstract T peek();
	public abstract T dequeue();
	public abstract void enque(T x);
}
