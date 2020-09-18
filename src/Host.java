import java.awt.AWTException;
import java.io.IOException;

import static java.lang.System.*;

/**
 * @author bret
 *
 * Host extends the abstract class Client
 * The host will run the "mod" and serves to invite "Mules" to the to the raid
 */
public class Host extends Client implements Runnable {

    /**
     * Host is a child of the Client class and manages Mules
     * @param x1 Top left x coordinate of client window
     * @param y1 Top left y coordinate of client window
     * @param x2 Bottom right x coordinate of client window
     * @param y2 bottom right y coordinate of client windows
     * @throws AWTException, IOException
     */
    public Host(int x1, int y1, int x2, int y2, int mules) throws AWTException, IOException {
        super(x1, y1, x2, y2);
        setMuleCount(mules);
    }

    /**
     * Interface method from Runnable. Run method for thread.
     */
    @Override
    public void run() {
        while(isRunning()) { //getClientAreaImage();
            try {
                Thread.sleep(TICK_TIME); // sleeps a tick
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isInError()) {

            } else if(isInvited()) { // If you have sent the invite...
                if(isInAOR("close")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(getMuleReport() == getMuleCount()) {
                    if(isInAOR("erStartQuest")) {
                        leftClickOnLocation(getAORX(), getAORY());
                    } else if(isInAOR("startRaid")) {
                        leftClickOnLocation(getAORX(), getAORY());
                        switchInviteFlag();
                        switchRaidFlag();
                        resetMuleReport();
                    }
                }

            } else if(isRaiding()) { // If you are currently in a raid...

                if(isInAOR("autoOn")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("retry")) {
                    switchRaidFlag();
                    leftClickOnLocation(getAORX(), getAORY());
                }

            } else { // If you are not special...

                if(isInAOR("ult200")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("createRoom")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("inviteIcon")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("ok")) {
                    leftClickOnLocation(getAORX(), getAORY());
                    switchInviteFlag();
                }

            }
        }
    }
}