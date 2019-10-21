package yich.base.preserver;

import yich.base.logging.JUL;
import yich.base.util.StrUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class TxtFilePreserver extends TimeStampFilePreserver<Object> {
    final private static Logger logger = JUL.getLogger(TxtFilePreserver.class);

    private int type;

    public TxtFilePreserver() {
        setFormat("txt");
        this.type = 1;
    }

    @Override
    public void accept(Object txt) {
        apply(txt);
    }


    @Override
    public String apply(Object txt) {
        File file = getDestFile("_", 3, type);
        int size;
        String str_txt;
        try(FileOutputStream os = new FileOutputStream(file)) {
            if (txt instanceof Collection) {
                str_txt = StrUtil.colToStr((Collection<String>) txt);
                size = ((Collection) txt).size();
            } else if(txt instanceof String) {
                str_txt = (String) txt;
                size = ((String) txt).length();
            } else {
                throw new RuntimeException("Type Not Compatible: " + txt.getClass().getTypeName());
            }

            os.write(str_txt.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("** File '" + file.getAbsolutePath() + "'(" + size + ") Saved.");
        return file.getAbsolutePath();
    }

    public int getType() {
        return type;
    }

    public TxtFilePreserver setType(int type) {
        this.type = type;
        return this;
    }
}
