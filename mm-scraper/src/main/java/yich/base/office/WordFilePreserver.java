package yich.base.office;


import yich.base.logging.JUL;
import yich.base.preserver.TimeStampFilePreserver;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class WordFilePreserver extends TimeStampFilePreserver<Object> {
    final private static Logger logger = JUL.getLogger(WordFilePreserver.class);


    public WordFilePreserver() {
        setFormat("docx");
        setType(1);
    }


    @Override
    public void accept(Object txt) {
        apply(txt);
    }


    @Override
    public String apply(Object txt) {
        File file = getDestFile();
        int size;
        try {
            if (txt instanceof Collection) {
                WordUtil.createDocx((Collection<String>) txt, file);
                size = ((Collection) txt).size();
            } else if(txt instanceof String) {
                WordUtil.createDocx((String) txt, file);
                size = ((String) txt).length();
            } else {
                throw new RuntimeException("Type Not Compatible: " + txt.getClass().getTypeName());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("** File '" + file.getAbsolutePath() + "'(" + size + ") Saved.");
        return file.getAbsolutePath();
    }


}
