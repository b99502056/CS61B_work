package synthesizer;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Capacity of the buffer. */
    private int capacity;

    /* Create a guitar string of the given frequency. */
    public GuitarString(double frequency) {
        /* Create a buffer with capacity = SR / frequency. You'll need to
         * cast the result of this division operation into an int. For better
         * accuracy, use the Math.round() function before casting.
         * Your buffer should be initially filled with zeros. */
        capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer(capacity);

        for (int i = 0; i < capacity; i += 1) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        /* Dequeue everything in the buffer, and replace it with random numbers
         * between -0.5 and 0.5. You can get such a number by using:
         * double r = Math.random() - 0.5;
         *
         * Make sure that your random numbers are different from each other. */
        for (int i = 0; i < capacity; i += 1) {
            double r = Math.random() - 0.5;
            buffer.dequeue();
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        /* Dequeue the front sample and enqueue a new sample that is
         * the average of the two multiplied by the DECAY factor.
         * Do not call StdAudio.play(). */
        double firstItem  = buffer.dequeue();
        double secondItem = buffer.peek();
        double newSample = DECAY * (firstItem + secondItem) / 2.0;
        buffer.enqueue(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (buffer.isEmpty()) {
            return 0.0;
        }
        return buffer.peek();
    }
}
