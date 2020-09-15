import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * @author bret
 *
 * Abstract Parent that defines a client that will have the basic structure to
 * read its portion of the screen(known as area of responsibility) and click
 * the mouse within that area.
 */

public abstract class Client implements Runnable {


    public Robot rob = new Robot(); //Robot instance used for

    public boolean running = false; // Flag for whether or not to keep looping the flag
    private Rectangle clientArea; // The area of responsibility for this client.

    // The offset values used to find actual coordinates
    private int xOffset;
    private int yOffset;

    BufferedImage clientAreaImage; // The images of the buttons to be checked for

    /**
     * Client is a class meant to be parent to Host and Mule classes
     * @param x1 Top left x coordinate of client window
     * @param y1 Top left y coordinate of client window
     * @param x2 Bottom right x coordinate of client window
     * @param y2 bottom right y coordinate of client window
     * @throws AWTException
     */
    public Client(int x1, int y1, int x2, int y2) throws AWTException {
        int width = x2 - x1;
        int height = y2 - y1;
        clientArea = new Rectangle(x1, y1, width, height);
        xOffset = x1;
        yOffset = y1;
    }

    /**
     * Used to get a screenshot of the area of responsibility of the client
     * @return screen shot of the area of responsibility
     */
    public BufferedImage getClientAreaImage() {
        return rob.createScreenCapture(clientArea);
    }

    /**
     * Used to get the x coordinate relative to entire screen
     * @param x x coordinate of area of responsibility
     * @return screen x coordinate
     */
    public int getRelativeX(int x) {
        return x + xOffset;
    }

    /**
     * Used to get the y coordinate relative to entire screen
     * @param y y coordinate of area of responsibility
     * @return screen y coordinate
     */
    public int getRelativeY(int y) {
        return y + yOffset;
    }

    /**
     * Uses class Robot instance to click on screen using Operating System mouse.
     * @param x horizontal coordinate of the screen
     * @param y vertical coordinate of the screen
     */
    private void leftClickOnLocation(int x, int y) {
        rob.mouseMove(x, y);
        rob.mousePress(InputEvent.BUTTON1_MASK);//come back to this, depreciated value
        rob.delay(200);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}