package yich.base.selenium.shot;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.dbc.Require;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhantomShotTaker implements ShotTaker {
    private boolean isPageImgBuffered;
    private RemoteWebDriver currentDriver;
    private BufferedImage pageImg;

    public PhantomShotTaker() {
        this.isPageImgBuffered = false;
//        this.pageImg = null;
    }

    public boolean isPageImgBuffered() {
        return isPageImgBuffered;
    }

    public PhantomShotTaker setPageImgBuffered(boolean pageImgBuffered) {
        this.isPageImgBuffered = pageImgBuffered;
        if (!this.isPageImgBuffered) {
            this.pageImg = null;
            this.currentDriver = null;
        }
        return this;
    }

    public BufferedImage getPageImg(RemoteWebDriver driver) {
        Require.argumentNotNull(driver);
        if (!isPageImgBuffered) {
            return getPageImage0(driver);
        }
        if (this.currentDriver == driver) {
            return (pageImg = (pageImg == null) ? getPageImage0(driver) : pageImg);
        }
        this.currentDriver = driver;
        return (pageImg = getPageImage0(driver));

    }

    public BufferedImage getPageImage0(RemoteWebDriver driver) {
        try(InputStream is = new ByteArrayInputStream(driver.getScreenshotAs(OutputType.BYTES))) {
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BufferedImage getElementImage(RemoteWebDriver driver, RectArea rect) {
        return getPageImg(driver).getSubimage(rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public BufferedImage getElementImage(RemoteWebDriver driver, String cssLocator) {
        return getElementImage(driver, RectArea.getByCSS(driver, cssLocator));
    }

    @Override
    public BufferedImage getElementGroupImage(RemoteWebDriver driver, String... cssLocators) {
        //return getElementImage(currentDriver, RectArea.getVerticalElementsGroup(currentDriver, firstEleCss, lastEleCss));
        return null;
    }

    @Override
    public BufferedImage getElementGroupImage(RemoteWebDriver driver, List<String> cssLocators) {
        //return getElementImage(currentDriver, RectArea.getVerticalElementsGroup(currentDriver, firstEleCss, lastEleCss));
        return null;
    }


    @Override
    public BufferedImage getFullPageImage(RemoteWebDriver driver) {
        return getPageImg(driver);
        //return getElementImage(RectArea.getByCSS(currentDriver, "html"));
    }

    @Override
    public BufferedImage getWindowImage(RemoteWebDriver driver) {
        Dimension d = driver.manage().window().getSize();
        return getElementImage(driver, new RectArea(0, 0, d.width, d.height));
    }

}
