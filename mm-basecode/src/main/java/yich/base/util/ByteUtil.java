package yich.base.util;

import yich.base.logging.JUL;

import java.util.List;
import java.util.logging.Logger;

public class ByteUtil {
    final private static Logger logger = JUL.getLogger(ByteUtil.class);

    public static byte[] merge(List<byte[]> data) {
        if (data.size() == 0) {
            return new byte[0];
        }
        if (data.size() == 1) {
            return data.get(0);
        }

        byte[] all;
        int total = 0;
        for (int i = 0; i < data.size(); i++){
            total += data.get(i).length;
        }
        all = new byte[total];

        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length; j++) {
                all[index + j] = data.get(i)[j];
            }
            index += data.get(i).length;
        }
        logger.info("**  The Merged File Size: " + all.length);
        return all;
    }

}
