package editor;

import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollBar;
import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.scene.text.Text;

import java.awt.peer.CanvasPeer;
import java.util.LinkedList;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;


/**
 * Created by LouisCho on 6/5/16.
 */

/** Capable of calculating the position of each char on the window,
 *  according to its position in the list.
 *  Information of the font is set here.
 *  Also handles cursor rendering. */
public class RenderTextEngine {
	private static final int STARTING_TEXT_POSITION_X = 5;
	private static final int STARTING_TEXT_POSITION_Y = 0;
	private static final int STARTING_FONT_SIZE = 20;
	private String fontName = "Verdana";
	private Text newlineStandard;

	private int textPosX = STARTING_TEXT_POSITION_X;
	private int textPosY = STARTING_TEXT_POSITION_Y;
	private int textWidth;
	private int textHeight;
	private int windowWidth;
	private int windowHeight;
	// usableScreenWidth = windowWidth - scroll width.
	private int usableScreenWidth;
	// Used to indicate if the bottom of the last row of texts exceeds
	// the lower bound of the window.
	private int textBottom;
	private int scrollbarY;

	private int currentPos;

	private int cursorX;
	private int cursorY;


	private Group textGroup;
	private FastLinkedList<Character> charBuffer;
	// Creating a linked list of text can facilitate retrieving individual text information.
	private FastLinkedList<Text> textBuffer;
	private Line cursor;
	private ScrollBar scrollBar;

	public RenderTextEngine(Group tGroup, Line cs, ScrollBar scb, FastLinkedList<Character> cList,
	                        int wW, int wH) {
		// Set standard text height.
		newlineStandard = new Text(-1000, -1000, Character.toString('a'));
		newlineStandard.setTextOrigin(VPos.TOP);
		newlineStandard.setFont(Font.font(fontName, STARTING_FONT_SIZE));
		textHeight = (int) Math.round(newlineStandard.getLayoutBounds().getHeight());

		textGroup = tGroup;
		cursor = cs;
		scrollBar = scb;
		charBuffer = cList;
		windowWidth = wW;
		windowHeight = wH;
		textBuffer = new FastLinkedList<>();
		renewCurrentPosition(0);

		initCursor();
		initScrollbar();

	}

	private void renewTextGroup(char a) {
		Text nextText = new Text(textPosX, textPosY, Character.toString(a));
		/* Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
		 * that when the text is assigned a y-position, that position corresponds to the
		 * highest position across all letters (for example, the top of a letter like "I", as
		 * opposed to the top of a letter like "e"), which makes calculating positions much
		 * simpler! */
		nextText.setTextOrigin(VPos.TOP);
		nextText.setFont(Font.font(fontName, STARTING_FONT_SIZE));
		textWidth = (int) Math.round(nextText.getLayoutBounds().getWidth());
		textGroup.getChildren().add(nextText);
		textBuffer.addChar(nextText);
		textPosX += textWidth;
	}

	private void initParameters() {
		textPosX = STARTING_TEXT_POSITION_X;
		textPosY = scrollbarY;
	}

	public void reRender() {
		/* Store the last word read to a LinkedList to make word wrap easier.
		 * If a space or newline is read, we know the word before it can fit in the window,
		 * and thus the word can be cleared.
		 */
		LinkedList<Character> lastWord = new LinkedList<>();
		// If the word starts from the left side of the window. If the width of the word exceeds
		// the width of the window, treat the part outside the window as the next word.
		boolean wordStartAtLeftSide = true;

		// Must clear whatever already inside textGroup for rerendering.
		textGroup.getChildren().clear();
		textBuffer.clear();
		// The text body starts at 1st position, so store null in the 0th position.
		textGroup.getChildren().add(0, newlineStandard);
		// Reset x, y positions of the starting point.
		initParameters();

		for (char x : charBuffer) {
			if (x == ' ') {
				lastWord.clear();
				wordStartAtLeftSide = false;
			} else if (x == '\n') {
				lastWord.clear();
				wordStartAtLeftSide = true;
				textPosX = STARTING_TEXT_POSITION_X;
				textPosY += textHeight;
			} else if (x == '\r') {
				lastWord.clear();
				wordStartAtLeftSide = true;
				textPosX = STARTING_TEXT_POSITION_X;
				textPosY += textHeight * 2;
			} else {
				lastWord.addLast(x);
			}

			renewTextGroup(x);
			// If the right side of the text exceeds the right border of the window,
			// delete the last word after the last space, and reset the word to the
			// start of the next line.
			if (textPosX > usableScreenWidth && !wordStartAtLeftSide) {
				for (int i = 0; i < lastWord.size(); i += 1) {
					textGroup.getChildren().remove(textGroup.getChildren().size() - 1);
					textBuffer.deleteChar();
				}
				textPosY += textHeight;
				textPosX = STARTING_TEXT_POSITION_X;
				for (char a : lastWord) {
					renewTextGroup(a);
				}
				wordStartAtLeftSide = true;
			}
			// If the width of the word exceeds the width of the window, treat the leaving part
			// as a new word.
			else if (textPosX > usableScreenWidth && wordStartAtLeftSide) {
				textGroup.getChildren().remove(textGroup.getChildren().size() - 1);
				textBuffer.deleteChar();
				textPosY += textHeight;
				textPosX = STARTING_TEXT_POSITION_X;
				renewTextGroup(x);
				lastWord.clear();
				lastWord.addLast(x);
			}
		}

		textBottom = textPosY + textHeight;
		renderScrollbar();
		renderCursor(currentPos);
	}

	public void resizeWindowWidth(int newWindowWidth) {
		windowWidth = newWindowWidth;
	}
	public void resizeWindowHeight(int newWindowHeight) {
		windowHeight = newWindowHeight;
	}
	public void setScrollbarY(int v) {
		scrollbarY = - v;
	}

	private void initScrollbar() {
		// Initialize scrollbar.
		// Make a vertical scroll bar on the right side of the screen.
		scrollBar.setOrientation(Orientation.VERTICAL);
		renderScrollbar();
	}

	private void renderScrollbar() {
		// Set the height of the scroll bar so that it fills the whole window.
		scrollBar.setPrefHeight(windowHeight);

		scrollBar.setVisibleAmount(scrollbarY / (textBottom - windowHeight));

		scrollBar.setMin(0);
		scrollBar.setMax(textBottom - windowHeight);


		// Set the scrollbar to the right side of the window.
		usableScreenWidth = (int) (windowWidth - scrollBar.getLayoutBounds().getWidth());
		scrollBar.setLayoutX(usableScreenWidth);
	}

	private void initCursor() {
		// Initialize cursor.
		cursor.setStartX(STARTING_TEXT_POSITION_X);
		cursor.setStartY(STARTING_TEXT_POSITION_Y);
		cursor.setEndX(STARTING_TEXT_POSITION_X);
		cursor.setEndY(STARTING_TEXT_POSITION_Y + textHeight);
		cursor.setStroke(Color.BLACK);
		cursor.setStrokeWidth(1.0);
	}
	public void renderCursor(int cPos) {
		if (cPos > 0) {
			Text currentText = (Text) textGroup.getChildren().get(currentPos);
			textWidth = (int) Math.round(currentText.getLayoutBounds().getWidth());
			cursorX = (int) Math.round(currentText.getX()) + textWidth;
			cursorY = (int) Math.round(currentText.getY());
			cursor.setStartX(cursorX);
			cursor.setStartY(cursorY);
			cursor.setEndX(cursorX);
			cursor.setEndY(cursorY + textHeight);
		} else if (cPos == 0) {
			initCursor();
		}
	}

	public void renewCurrentPosition(int cPos) {
		currentPos = cPos;
	}
}
