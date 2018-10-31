package yich.base.selenium.common;

import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebDelayer {
    final private static Logger logger = JUL.getLogger(WebDelayer.class);
    //protected RemoteWebDriver driver;
    private long delayTimeX = 0;
    private int delayTimeY = 0;
    private Random rand = new Random();

    protected void delay() {
        if (delayTimeX > 0) {
            try {
                Thread.sleep(delayTimeX + (delayTimeY == 0 ? 0 : rand.nextInt(delayTimeY)));
                //logger.info("*** " + delayTimeX + " milliseconds delayed");
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "** Delay Interrupted", e);
                e.printStackTrace();
            }
        }
    }

    public long getDelayTimeX() {
        return delayTimeX;
    }

    public long getDelayTimeY() {
        return delayTimeY;
    }

    public WebDelayer setDelayTime(long delayTimeX) {
        Require.argument(delayTimeX > 0, delayTimeX, "delay > 0");
        this.delayTimeX = delayTimeX;
        return this;
    }

    public WebDelayer setDelayTime(long delayTimeX, int delayTimeY) {
        Require.argument(delayTimeX >= 0, delayTimeX, "delayTimeX >= 0");
        Require.argument(delayTimeY >= 0, delayTimeY, "delayTimeY >= 0");

        this.delayTimeX = delayTimeX;
        this.delayTimeY = delayTimeY;
        return this;
    }

}
