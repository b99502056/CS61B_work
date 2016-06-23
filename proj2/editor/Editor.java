package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * A JavaFX application that displays the letter the user has typed most recently in the center of
 * the window. Pressing the up and down arrows causes the font size to increase and decrease,
 * respectively.
 */
public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    // Usable width = WINDOW_WIDTH - Scrollbar width. Initialized in initScrollBar().
    private double usableScreenWidth;
    private double usableScreenHeight;
    /* Current position of the cursor. 0 represents the very front of the file.
     * 1 is the right side of the first char and so on. */
    private int currentPosition;

    private static String filePath;
    private FastLinkedList<Character> charBuffer;

    /** Elements to display on the screen. */
    private ScrollBar scrollBar = new ScrollBar();
    // Group of Texts to display on the screen.
    private Group textGroup = new Group();
    private RenderTextEngine renderTextEngine;
    private Line cursor = new Line();

    public Editor() {
        currentPosition = 0;
        ReadFile myFile = new ReadFile(filePath);
        charBuffer = myFile.returnBuffer();
        renderTextEngine = new RenderTextEngine(textGroup, cursor, scrollBar, charBuffer,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        renderTextEngine.reRender();
    }

    private String bufferString() {
        String outputString;
        StringBuilder builder = new StringBuilder();

        for (char x : charBuffer) {
            builder.append(x);
        }

        outputString = builder.toString();
        return outputString;
    }

    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textOriginX;
        int textOriginY;

        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {

        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                // When enter is pressed, the input char is '\r'.
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8 && characterTyped.charAt(0) != '\r') {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
                    charBuffer.addChar(characterTyped.charAt(0));
                    renderTextEngine.reRender();
                    // displayText.setText(bufferString());
                    keyEvent.consume();
                }

            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.UP) {

                } else if (code == KeyCode.DOWN) {

                } else if (code == KeyCode.LEFT) {
                    if (currentPosition > 0) {
                        currentPosition -= 1;
                        charBuffer.changeCurrentPosTo(currentPosition);
                        renderTextEngine.renewCurrentPosition(currentPosition);
                        renderTextEngine.reRender();

                    }
                } else if (code == KeyCode.RIGHT) {
                    if (currentPosition < textGroup.getChildren().size()) {
                        currentPosition += 1;
                        charBuffer.changeCurrentPosTo(currentPosition);
                        renderTextEngine.renewCurrentPosition(currentPosition);
                        renderTextEngine.reRender();

                    }
                } else if (code == KeyCode.BACK_SPACE) {
                    // When Backspace is pressed.
                    if (!charBuffer.isEmpty() && currentPosition > 0) {
                        charBuffer.deleteChar();
                        currentPosition -= 1;
                        charBuffer.changeCurrentPosTo(currentPosition);
                        renderTextEngine.renewCurrentPosition(currentPosition);
                        renderTextEngine.reRender();

                    }

                } else if (code == KeyCode.ENTER) {
                    // When Enter is pressed.
                    charBuffer.addChar('\n');
                    renderTextEngine.reRender();
                }
            }
        }
    }

    /** An EventHandler to handle changing the color of the rectangle. */
    private class CursorBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors = {Color.BLACK, Color.WHITE};

        CursorBlinkEventHandler() {
            // Set the color to be the first color in the list.
            blink();
        }

        private void blink() {
            cursor.setStroke(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            blink();
        }
    }

    /** Makes the cursor blink periodically. */
    public void makeCursorBlink() {
        // Create a Timeline that will call the "handle" function of CursorBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The cursor should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        Editor.CursorBlinkEventHandler cursorChange = new Editor.CursorBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void addChildrenToRoot(Group r) {
        r.getChildren().add(textGroup);
        // Add the scroll bar to the scene graph, so that it appears on the screen.
        r.getChildren().add(scrollBar);
        r.getChildren().add(cursor);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();

        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        addChildrenToRoot(root);



        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);

        makeCursorBlink();

        // Register listeners that resize Allen when the window is re-sized.
        // We're using some new syntax here to create a ChangeListener with an overridden
        // changed() method; this is called instantiating an "anonymous class."  If you're curious
        // to learn more about this syntax, try Googling "Java anonymous class".  Beware that
        // IntelliJ sometimes collapses code blocks like this! If this happens, you can click on
        // the "+" icon that's to the left of the code (and to the right of the line numbers) to
        // expand the code again.
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenWidth,
                    Number newScreenWidth) {
                // Resize the window width.
                renderTextEngine.resizeWindowWidth(newScreenWidth.intValue());
                renderTextEngine.reRender();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenHeight,
                    Number newScreenHeight) {
                    renderTextEngine.resizeWindowHeight(newScreenHeight.intValue());
                    renderTextEngine.reRender();
            }
        });

        /** When the scroll bar changes position, change the display position of the text body. */
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                // newValue describes the value of the new position of the scroll bar. The numerical
                // value of the position is based on the position of the scroll bar, and on the min
                // and max we set above. For example, if the scroll bar is exactly in the middle of
                // the scroll area, the position will be:
                //      scroll minimum + (scroll maximum - scroll minimum) / 2
                // Here, we can directly use the value of the scroll bar to set the height of Josh,
                // because of how we set the minimum and maximum above.
                renderTextEngine.setScrollbarY(newValue.intValue());
                renderTextEngine.reRender();
            }
        });

        primaryStage.setTitle("Editor");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: Editor <source file name>");
            System.exit(1);
        }
        filePath = args[0];
        launch(args);
    }
}
