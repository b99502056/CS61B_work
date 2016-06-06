package editor;

import org.junit.Test;

/**
 * Created by LouisCho on 6/5/16.
 */
public class TestFastLinkedList {

	public static void main(String[] args) {
		FastLinkedList<Character> abc = new FastLinkedList<>();
		abc.addChar('a');
		abc.addChar('b');
		abc.addChar('c');

		for (char x : abc) {
			System.out.println(x);
		}
	}
}
