package hw3.hash;

import java.util.HashSet;
import java.util.Set;


public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        double scale = 1;
        int N = 5;
        int M = 10;

        HashTableDrawingUtility.setScale(scale);
        Set<Oomage> oomies = new HashSet<Oomage>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(oomies, M, scale);
    }

    public static void visualize(Set<Oomage> set, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);
        HashTableDrawingUtility.setScale(scale);

        /* Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */

        /* When done with visualizer, be sure to try 
           scale = 0.5, N = 2000, M = 100. */

        // Storing the number of items in each bucket.
        int[] hashTable = new int[M];

        for (Oomage o : set) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            int bucketPos = hashTable[bucketNum];
            double xCoord = HashTableDrawingUtility.xCoord(bucketPos);
            double yCoord = HashTableDrawingUtility.yCoord(bucketNum, M);
            o.draw(xCoord, yCoord, scale);

            // Update number of items in the bucket.
            hashTable[bucketNum] = bucketPos + 1;
        }
    }
} 
