public class Pyramid{
	public static void DrawTriangle(int SIZE){
		int col = 0;
		int row = 0;

		while (col < SIZE){
			col += 1;
			while (row < col){
				System.out.print("*");
				row += 1;
			}
			System.out.println();
			row = 0;
		}
	}

	public static void main(String[] arg){
		DrawTriangle(10);
		
	}
}