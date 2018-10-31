package yich.base.preserver;


import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.util.UrlInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ShotFilePreserver extends TimeStampFilePreserver<BufferedImage> {
    final private static Logger logger = JUL.getLogger(ShotFilePreserver.class);
    private Set<String> formatSet;

    public ShotFilePreserver(String basePath) {
        setBasePath(basePath);
        setTag("Tag");
        setFormat("png");
    }

    private String checkImageType(String format) {
        if (formatSet == null) {
            formatSet = new HashSet<>();
            String[] formatNames = ImageIO.getWriterFormatNames();
            for (int i = 0; i < formatNames.length; i++) {
                formatSet.add(formatNames[i].toLowerCase());
            }
        }
        if (formatSet.contains(format))
            return format;
        return "png";
    }

    @Override
    public ShotFilePreserver setBasePath(String basePath) {
        super.setBasePath(basePath);
        return this;
    }

    @Override
    public ShotFilePreserver setTag(String... tags) {
        super.setTag(tags);
        return this;
    }

    @Override
    public ShotFilePreserver setFormat(String format) {
        Require.argumentNotNull(format);
        super.setFormat(checkImageType(format));
        return this;
    }

    public ShotFilePreserver setUrl(String url) {
        Require.argumentWCM(UrlInfo.of(url).isValid(), "URL Not Valid");
        // append first
        appendDir(UrlInfo.of(url).domainName());
        return this;
    }

    public ShotFilePreserver removeUrl(String url) {
        Require.argumentWCM(UrlInfo.of(url).isValid(), "URL Not Valid");
        removeAppendedDir(UrlInfo.of(url).domainName());
        return this;
    }

    private String save(BufferedImage image) {
        Require.stateNotNull(image, "BufferedImage image");

        File shotFile = getDestFile("_", 5);
        // Copy the element shot to disk
        try {
            ImageIO.write(image, getFormat(), shotFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("** Image Saved to : \"" + shotFile.getAbsolutePath() + "\"");
        return shotFile.getAbsolutePath();
    }

    @Override
    public void accept(BufferedImage image) {
        save(image);
    }

    @Override
    public String apply(BufferedImage image) {
        return save(image);
    }
}
