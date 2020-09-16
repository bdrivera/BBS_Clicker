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

    public Host(int x1, int y1, int x2, int y2) throws AWTException, IOException {
        super(x1, y1, x2, y2);
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            BufferedImage activeScreen = getClientAreaImage();
        }
    }
}
