import java.awt.AWTException;
import java.io.IOException;

import static java.lang.System.*;

/**
 * @author bret
 *
 * Host extends the abstract class Client
 * The host will run the "mod" and serves to invite "Mules" to the to the raid
 */
public class Mule extends Client implements Runnable {

    private boolean reported = false;
    private int functionCounter = 0;
    private int errorCounter = 0;

    /**
     * Mule is a child of Client, and is controlled by a Host
     * @param x1 Top left x coordinate of client window
     * @param y1 Top left y coordinate of client window
     * @param x2 Bottom right x coordinate of client window
     * @param y2 bottom right y coordinate of client windows
     * @throws AWTException, IOException
     */
    public Mule(int x1, int y1, int x2, int y2) throws AWTException, IOException {
        super(x1, y1, x2, y2);
    }

    @Override
    public void run() {
        out.println("Mule running!");
        while(isRunning()) { //getClientAreaImage();
            try {
                Thread.sleep(TICK_TIME); // sleeps a tick
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isInError()) {

            } else if(isInvited()) { // If you have sent the invite...
                if(isInAOR("chatIcon")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("inviteText")) {
                    leftClickOnLocation((getAORX() + 150), getAORY());
                } else if(!reported && isInAOR("guildLogo")) {
                    reportMule();
                    reported = true;
                }

            } else if(isRaiding()) { // If you are currently in a raid...
                reported = false;

                if(isInAOR("autoOff")) {
                    if(functionCounter < (functionCounter * TICK_MULTI)) {
                        functionCounter++;
                    } else {
                        leftClickOnLocation(getAORX(), getAORY());
                        functionCounter = 0;
                    }
                }

            } else { // If you are not special...
                if(isInAOR("erTop")) {
                    leftClickOnLocation(getAORX(), getAORY());
                }

            }
        }
    }

}
