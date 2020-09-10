import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author bret
 *
 * Uses ImageScanner class to check and handle if certain images are on screen
 */

public class ScreenTasker extends Thread {

    private Robot rob = new Robot();

    /**
     * the images of the buttons to be checked for
     */
    BufferedImage[] image = new image[5];

    public void run() {

    }

    private void leftClickOnLocation(int x, int y) {
        rob.mouseMove(x, y);
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.delay(200);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}