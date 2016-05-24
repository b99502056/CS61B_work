/**
 * Created by LouisCho on 5/24/16.
 */
public class OffByN implements CharacterComparator {
	private int offset;

	public OffByN() {
		offset = 0;
	}

	public OffByN(int N) {
		offset = N;
	}
	@Override
	public boolean equalChars(char a, char b) {
		char c = Character.toLowerCase(a);
		char d = Character.toLowerCase(b);

		if (c - d == offset || c - d == -offset) {
			return true;
		}
		return false;
	}
}
