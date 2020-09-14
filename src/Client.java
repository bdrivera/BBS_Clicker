import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * @author bret
 *
 * Uses ImageScanner class to check and handle if certain images are on screen
 */

public class Client implements Runnable {

    private Robot rob = new Robot();

    private boolean running = false;
    private Rectangle clientArea;


    /**
     * the images of the buttons to be checked for
     */
    BufferedImage[] image = new BufferedImage[5];

    public Client() throws AWTException {
    }

    public void run() {}

    /**
     * Uses class Robot instance to click on screen using Operating System mouse.
     * @param x horizontal coordinate of the screen
     * @param y vertical coordinate of the screen
     */
    private void leftClickOnLocation(int x, int y) {
        rob.mouseMove(x, y);
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.delay(200);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}