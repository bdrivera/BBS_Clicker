import java.awt.*;
import java.io.IOException;

public class Clicker {

    /**
     * Main execution method
     * @param args runtime arguments
     */
    public static void main(String[] args) throws AWTException, IOException {
        final int CLIENT_COUNT = 2;

        final int topX = 4;
        final int topY = 4;
        final int bottX = 636;
        final int bottY = 372;

        final int xOffset = 640;
        final int yOffset = 376;

        Thread[] client = new Thread[CLIENT_COUNT];
        Host host = new Host(topX, topY, bottX, bottY, (CLIENT_COUNT - 1));
        Mule[] mule = new Mule[CLIENT_COUNT - 1];

        client[0] = new Thread(host);
        for(int i = 1; i < client.length; i++) {
            if(i < 3) {
                mule[i - 1] = new Mule(
                                        (topX + (xOffset * (i))), topY,
                                         (bottX + (xOffset * (i))), bottY);
            } else {
                mule[i - 1] = new Mule(
                        (topX + (xOffset * (i - 2))), (topY + yOffset),
                        (bottX + (xOffset * (i - 2))), (bottY + yOffset));
            }
            client[i] = new Thread(mule[i-1]);
        }

        for (Thread i : client) {
            i.run();
        }
    }

}