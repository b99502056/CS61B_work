package editor;

import edu.princeton.cs.algs4.ST;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.LinkedList;

/**
 * Created by LouisCho on 6/5/16.
 */

/** Convert a Char FastLinkedList to a Text FastLinkedList.
 *  Capable of calculating the position of each char on the window
 *  according to its position in the list. */
public class CharToText {
	private static final int STARTING_TEXT_POSITION_X = 15;
	private static final int STARTING_TEXT_POSITION_Y = 10;
	private static final int STARTING_FONT_SIZE = 20;
	private String fontName = "Verdana";
	private FastLinkedList<Text> tList = new FastLinkedList<>();

	private int textPosX = STARTING_TEXT_POSITION_X;
	private int textPosY = STARTING_TEXT_POSITION_Y;
	private int textWidth;
	private int textHeight;
	public CharToText(LinkedList<Character> cList, final int WINDOW_WIDTH) {



		/* Store the last word read to a LinkedList to make word wrap easier.
		 * If a space or newline is read, we know the word before it can fit in the window,
		 * and thus the word can be cleared.
		 */
		LinkedList<Character> lastWord = new LinkedList<>();
		// If the word starts from the left side of the window. If the width of the word exceeds
		// the width of the window, treat the part outside the window as the next word.
		boolean wordStartAtLeftSide = true;

		for (char x : cList) {
			if ((x == ' ') || (x == '\n')) {
				lastWord.clear();
				wordStartAtLeftSide = false;
			}
			else {
				lastWord.addLast(x);
			}

			renewTextList(x);
			// If the right side of the text exceeds the right border of the window,
			// delete the last word after the last space, and reset the word to the
			// start of the next line.
			if (textPosX > WINDOW_WIDTH && !wordStartAtLeftSide) {
				for (int i = 0; i < lastWord.size(); i += 1) {
					tList.deleteChar();
				}
				textPosY += textHeight;
				textPosX = STARTING_TEXT_POSITION_X;
				for (int i = 0; i < lastWord.size(); i += 1) {
					renewTextList(x);
				}
				wordStartAtLeftSide = true;
			}
			// If the width of the word exceeds the width of the window, treat the leaving part
			// as a new word.
			else if (textPosX > WINDOW_WIDTH && wordStartAtLeftSide) {
				tList.deleteChar();
				textPosY += textHeight;
				textPosX = STARTING_TEXT_POSITION_X;
				renewTextList(x);
				lastWord.clear();
				lastWord.addLast(x);
			}
		}
	}

	private void renewTextList(char a) {
		Text nextText = new Text(textPosX, textPosY, Character.toString(a));
		nextText.setFont(Font.font(fontName, STARTING_FONT_SIZE));
		textWidth = (int) nextText.getLayoutBounds().getWidth();
		textHeight = (int) nextText.getLayoutBounds().getHeight();
		tList.addChar(nextText);
		textPosX += textWidth;
	}

	public FastLinkedList<Text> textList() {
		return tList;
	}
}
