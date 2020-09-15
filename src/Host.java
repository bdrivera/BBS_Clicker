import java.awt.*;

/**
 * @author bret
 *
 * Host extends the abstract class Client
 */
public class Host extends Client implements Runnable {

    public Host(int x1, int y1, int x2, int y2) throws AWTException {
        super(x1, y1, x2, y2);
    }

    public void run() {
        running = true;
        while(running) {

        }
    }
}
