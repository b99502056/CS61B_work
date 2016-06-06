package editor;

import java.util.LinkedList;

import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * Created by LouisCho on 6/5/16.
 */

/** Handles all the changes to the text stream, such as font size, font style, add and delete.
 *  All the changes will be shown to the window by rerendering.
 */
public class RenderEngine {
	private static final int STARTING_TEXT_POSITION_X = 15;
	private static final int STARTING_TEXT_POSITION_Y = 10;
	private static final int STARTING_FONT_SIZE = 20;
	private String fontName = "Verdana";
	private int currentPos;


	public RenderEngine(FastLinkedList<Text> tList, final int WINDOW_WIDTH) {
		for (Text t : tList) {

		}
	}

}
