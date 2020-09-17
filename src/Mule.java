import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author bret
 *
 * Host extends the abstract class Client
 * The host will run the "mod" and serves to invite "Mules" to the to the raid
 */
public class Mule extends Client implements Runnable {

    public Mule(int x1, int y1, int x2, int y2, int mules) throws AWTException, IOException {
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
                if(isInAOR("chatIcon")) {
                    leftClickOnLocation(getAORX(), getAORY());
                } else if(isInAOR("inviteText")) {
                    leftClickOnLocation((getAORX() + 150), getAORY());
                }

                continue;
            } else if(isRaiding()) { // If you are currently in a raid...

                continue;

            } else { // If you are not special...


            }
        }
    }

}
