import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import static java.lang.System.*;

/**
 * @author bret
 *
 * Abstract Parent that defines a client that will have the basic structure to
 * read its portion of the screen(known as area of responsibility) and click
 * the mouse within that area.
 */

public abstract class Client implements Runnable {


    public Robot rob = new Robot(); //Robot instance used for
    public ImageScanner scan = new ImageScanner();

    private static Hashtable<String, BufferedImage>
            hmRefImages = new Hashtable<String, BufferedImage>();

    private static int muleCount;
    private static int muleReport = 0;

    private static boolean running = false; // Flag for whether or not to keep looping the flag
    private static boolean raidFlag = false; // Flag for if a raid is in progress
    private static boolean inviteFlag = false; // Flag for if a raid invite has been sent
    private boolean isInError = false; // Flag for if this client is experiencing an error

    private Rectangle clientArea; // The area of responsibility for this client.

    // The offset values used to find actual coordinates
    private int xOffset;
    private int yOffset;

    BufferedImage clientAreaImage; // The images of the buttons to be checked for

    public static final int TICK_TIME = 1000; // Time in miliseconds for each tick iteration
    public static final int TICK_MULTI = 1000 / TICK_TIME; // Used to scale ticks to seconds

    /**
     * Client is a class meant to be parent to Host and Mule classes
     * @param x1 Top left x coordinate of client window
     * @param y1 Top left y coordinate of client window
     * @param x2 Bottom right x coordinate of client window
     * @param y2 bottom right y coordinate of client windows
     * @throws AWTException, IOException
     */
    public Client(int x1, int y1, int x2, int y2) throws AWTException, IOException {
        int width = x2 - x1;
        int height = y2 - y1;
        clientArea = new Rectangle(x1, y1, width, height);
        xOffset = x1;
        yOffset = y1;
        loadResourceHash();
        startRunning();
    }

    /**
     * Uses class Robot instance to click on screen using Operating System mouse.
     * @param x horizontal coordinate of the screen
     * @param y vertical coordinate of the screen
     */
    public synchronized void leftClickOnLocation(int x, int y) {
        rob.mouseMove(x, y);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.delay(200);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Uses ImageScanner to check if the reference image is in the Area of Responsibility
     * @param key key for desired reference image
     * @return if reference is on screen or not
     */
    public boolean isInAOR(String key) {
        return scan.isOnScreen(getClientAreaImage(), getReferenceImage(key));
    }

    /**
     * Adds reference images and text keys to hash
     */
    private synchronized void loadResourceHash() throws IOException {
        if(hmRefImages.isEmpty()) {
            out.println("Loading Resources...");
            hmRefImages.put("autoOff", ImageIO.read(new File("res/autoOff.png")));
            hmRefImages.put("autoOn", ImageIO.read(new File("res/autoOn.png")));
            hmRefImages.put("chatIcon", ImageIO.read(new File("res/chatIcon.png")));
            hmRefImages.put("close", ImageIO.read(new File("res/close.png")));
            hmRefImages.put("closeNews", ImageIO.read(new File("res/closeNews.png")));
            hmRefImages.put("createRoom", ImageIO.read(new File("res/createRoom.png")));
            hmRefImages.put("epicRaids", ImageIO.read(new File("res/epicRaids.png")));
            //hmRefImages.put("erQuestSelect", ImageIO.read(new File("res/erQuestSelect.png")));
            hmRefImages.put("erStartQuest", ImageIO.read(new File("res/erStartQuest.png")));
            hmRefImages.put("erTop", ImageIO.read(new File("res/erTop.png")));
            hmRefImages.put("playerInfoBTN", ImageIO.read(new File("res/playerInfoBTN.png")));
            hmRefImages.put("inviteIcon", ImageIO.read(new File("res/inviteIcon.png")));
            hmRefImages.put("inviteSent", ImageIO.read(new File("res/inviteSent.png")));
            hmRefImages.put("inviteText", ImageIO.read(new File("res/inviteText.png")));
            hmRefImages.put("ok", ImageIO.read(new File("res/ok.png")));
            hmRefImages.put("quests", ImageIO.read(new File("res/quests.png")));
            hmRefImages.put("retry", ImageIO.read(new File("res/retry.png")));
            hmRefImages.put("startRaid", ImageIO.read(new File("res/startRaid.png")));
            hmRefImages.put("ult200", ImageIO.read(new File("res/ult200.png")));
        }
    }

    /**
     * Accessor method for the hash containing the reference images
     * @param key for the desired hash value
     * @return image for the entered key
     */
    public BufferedImage getReferenceImage(String key) {
        return hmRefImages.get(key);
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
     * @param x x coordinate of relative to entire screen
     * @return screen x coordinate
     */
    public int getRelativeX(int x) {
        return x + xOffset;
    }

    /**
     * Used to get the y coordinate relative to entire screen
     * @param y y coordinate of relative to entire screen
     * @return screen y coordinate
     */
    public int getRelativeY(int y) {
        return y + yOffset;
    }

    /**
     * Used to get the x coordinate area of responsibility
     * @return screen x coordinate
     */
    public int getAORX() {
        return scan.getFoundX() + xOffset;
    }

    /**
     * Used to get the y coordinate area of responsibility
     * @return screen y coordinate
     */
    public int getAORY() {
        return scan.getFoundY() + yOffset;
    }

    /**
     * Returns the current condition of the raid flag
     * @return raid flag condition
     */
    public boolean isRaiding() {
        return raidFlag;
    }

    /**
     * Switches the condition of the raid flag to opposite value
     */
    public void switchRaidFlag() {
        raidFlag = !raidFlag;
        out.println("raidFlag Switch! [" + isRaiding() + "]");
    }

    /**
     * Returns the current condition of the invite flag
     * @return invite flag condition
     */
    public boolean isInvited() {
        return inviteFlag;
    }

    /**
     * Switches the condition of the invite flag to opposite value
     */
    public void switchInviteFlag() {
        inviteFlag = !inviteFlag;
        out.println("inviteFlag Switch! [" + isInvited() + "]");
    }

    /**
     * Returns the current condition of the error flag
     * @return error flag condition
     */
    public boolean isInError() {
        return isInError;
    }

    /**
     * Switches the condition of the error flag to opposite value
     */
    public void switchInError() {
        isInError = !isInError;
    }

    /**
     * Returns the current condition of running
     * @return running flag condition
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Changes running flag to true
     */
    public void startRunning() {
        running = true;
    }

    /**
     * Changes running flag to false
     */
    public void stopRunning() {
        running = false;
    }

    /**
     * Used to set the amount of mules being controlled
     * @param count number of mules to be controlled
     */
    public void setMuleCount(int count) {
        muleCount = count;
    }

    /**
     * Accessor method for muleCount
     * @return amount of mules running
     */
    public int getMuleCount() {
        return muleCount;
    }

    /**
     * Accessor method for muleReport
     * @return number of mules who have reported
     */
    public int getMuleReport() {
        return muleReport;
    }

    /**
     * Resets the mule report count to 0
     */
    public void resetMuleReport() {
        muleReport = 0;
    }

    /**
     * Adds 1 to the mule report count
     */
    public synchronized void reportMule() {
        muleReport++;
        out.println("mule report! [" + getMuleReport() + "]");
    }


}