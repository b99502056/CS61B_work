public class PrintMax {
   public static int max(int[] m) {
       int l = m.length;
       int maxNum = m[0];

       for (int x : m) {
       		if (maxNum < x) {
       			maxNum = x;
       		}
       }
       return maxNum;
   }
   public static void main(String[] args) {
      int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};  
      int maxNum = max(numbers);
      System.out.println("The biggest number in the array is " + maxNum);

   }
}