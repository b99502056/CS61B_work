package editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Editor extends Application {
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;

	private String contentToBeDisplayed = "";

	/** An EventHandler to handle keys that get pressed. */
	private class KeyEventHandler implements EventHandler<KeyEvent> {
		int textOriginX;
		int textOriginY;

		private static final int STARTING_FONT_SIZE = 20;
		private static final int STARTING_TEXT_POSITION_X = 250;
		private static final int STARTING_TEXT_POSITION_Y = 250;

		/** The Text to display on the screen. */
		private Text displayText = new Text(STARTING_TEXT_POSITION_X,
			STARTING_TEXT_POSITION_Y, "");
		private int fontSize = STARTING_FONT_SIZE;

		private String fontName = "Verdana";


		KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
			textOriginX = 15;
			textOriginY = 10;

			// Initialize some empty text and add it to root so that it will be displayed.
			displayText = new Text(textOriginX, textOriginY, "");

			/* Always set the text origin to be VPos.TOP! Setting the origin to be
			 * Vop.TOP means that when the text is assigned a y-position, that position
			 * corresponds to the highest position across all letters (for example, the
			 * top of a letter like "I" as opposed to the top of a letter like "e"),
			 * which makes calculating positions much simpler! */
			displayText.setTextOrigin(VPos.TOP);
			displayText.setFont(Font.font(fontName, fontSize));

			/* All new Nodes need to be added to the root in order to be displayed. */
			root.getChildren().add(displayText);

		}
		@Override
		public void handle(KeyEvent keyEvent) {
			if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
				/* Use the KEY_TYPED event rather than KEY_PRESSED for letter
				 * keys, because with the KEY_TYPED event, javafx handles the
				 * "Shift" key and associated capitalization. */
				 String characterTyped = keyEvent.getCharacter();
				 if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
					contentToBeDisplayed  += characterTyped;
					/* Ignore control keys, which have non-zero length,
					 * as well as the Backspace key, which is represented as a character
					 * of value = 8 on Windows. */
					displayText.setText(contentToBeDisplayed);

					// Terminates this event.
					keyEvent.consume();
				 }
				 // Put the text displayed to the center of the window.
				 alignText();
			} else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
				/* Arrow keys should be processed using the KEY_PRESSED event,
				 * because KEY_PRESSED events have a code that we can check
				 * (KEY_TYPED event does not have an associated KeyCode). */
				 KeyCode code = keyEvent.getCode();
				 if (code == KeyCode.UP) {
					fontSize += 5;
					displayText.setFont(Font.font(fontName, fontSize));
					alignText();
				 } else if (code == KeyCode.DOWN) {
					fontSize -= 5;
					displayText.setFont(Font.font(fontName, fontSize));
					alignText();
				 } else if (code == KeyCode.BACK_SPACE) {
					 /* When the Backspace is typed. */
					 if (contentToBeDisplayed != "") {
						 contentToBeDisplayed = contentToBeDisplayed.substring(0,
								 contentToBeDisplayed.length() - 1);
						 displayText.setText(contentToBeDisplayed);
					 }
				 } else if (code == KeyCode.ENTER) {
					 contentToBeDisplayed += "\n";
				 }
			}
		}

		private void alignText() {
			// Figure out the size of the current text.
			double textHeight = displayText.getLayoutBounds().getHeight();
			double textWidth = displayText.getLayoutBounds().getWidth();

			/* Calculate the position so that the text will be centered
			 * on the screen. */
			double textTop = textOriginY;
			double textLeft = textOriginX;

			// Reposition the text.
			displayText.setX(textLeft);
			displayText.setY(textTop);

			// Make sure the text appears in front of any other objects you might add.
			displayText.toFront();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		// Create a Node that will be the parent of all things displayed on the screen.
		Group root = new Group();

		/* The scene represents the window: its height and width will be the
		 * height and width of the window displayed. */
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

		/* To get information about what keys the user is pressing,
		 * create an EventHandler.
		 * EventHandler subclassed must override the "handle" method,
		 * which will be called by javafx. */
		EventHandler<KeyEvent> keyEventHandler =
				new KeyEventHandler(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		/* Register the event handler to be called for all KEY_PRESSED and
		 * KEY_TYPED events. */
		scene.setOnKeyTyped(keyEventHandler);
		scene.setOnKeyPressed(keyEventHandler);

		primaryStage.setTitle("Editor");

		// This is necessary to setup the window where things are displayed.
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
