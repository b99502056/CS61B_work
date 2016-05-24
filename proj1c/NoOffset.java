/**
 * Created by LouisCho on 5/24/16.
 */
public class NoOffset implements CharacterComparator {
	@Override
	public boolean equalChars(char a, char b) {
		char c = Character.toLowerCase(a);
		char d = Character.toLowerCase(b);

		return c == d;
	}
}
