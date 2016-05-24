/**
 * Created by LouisCho on 5/24/16.
 */
public class Palindrome {
	public static Deque<Character> wordToDeque(String word) {
		Deque<Character> deque = new LinkedListDeque<Character>();

		for (int i = 0; i < word.length(); i += 1) {
			Character c = word.charAt(i);
			deque.addLast(c);
		}
		return deque;
	}

	public static boolean isPalindrome(String word, CharacterComparator cc) {
		int length = word.length();

		if (length == 1 || length == 0) {
			return true;
		}

		boolean match = (cc.equalChars(word.charAt(0), word.charAt(length - 1)));

		return match && isPalindrome(word.substring(1, length - 1), cc);
	}

	public static void main(String[] args) {
		Deque<Character> deque = wordToDeque("HiIamJohn");
		deque.printDeque();
		System.out.println();

		if (isPalindrome("flake", new OffByN(2))) {
			System.out.println("This word is palindrome.");
		} else {
			System.out.println("This word is not palindrome.");
		}

	}
}
