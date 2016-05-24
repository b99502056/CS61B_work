/**
 * Created by LouisCho on 5/24/16.
 */
public class OffByOne implements CharacterComparator {
	@Override
	public boolean equalChars(char a, char b) {
		// Turn input characters into lowercase.
		char c = Character.toLowerCase(a);
		char d = Character.toLowerCase(b);

		if (c - d == 1 || c - d == -1){
			return true;
		}
		return false;
	}
}
