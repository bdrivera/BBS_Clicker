import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author bret
 *
 * Host extends the abstract class Client
 * The host will run the "mod" and serves to invite "Mules" to the to the raid
 */
public class Host extends Client implements Runnable {

    public Host(int x1, int y1, int x2, int y2, int mules) throws AWTException, IOException {
        super(x1, y1, x2, y2, mules);
    }

    @Override
    public void run() {
        while(isRunning()) { //getClientAreaImage();
            try {
                Thread.sleep(TICK_TIME); // sleeps a tick
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isInError()) {

                continue;
            } else if(isInvited()) { // If you have sent the invite...
                if(getMuleReport() == getMuleCount()) {
                    if(isInAOR("erStartQuest")) {
                        leftClickOnLocation(getAORX(), getAORY());
                    } else if(isInAOR("startRaid")) {
                        leftClickOnLocation(getAORX(), getAORY());
                        switchInviteFlag();
                        switchRaidFlag();
                    }
                }
                continue;
            } else if(isRaiding()) { // If you are currently in a raid...

                if(isInAOR("autoOn")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("retry")) {
                    switchRaidFlag();
                    leftClickOnLocation(getAORX(), getAORY());
                }
                continue;

            } else { // If you are not special...

                if(isInAOR("createRoom")) {
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