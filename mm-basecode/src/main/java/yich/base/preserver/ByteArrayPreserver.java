
package yich.base.preserver;

import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class ByteArrayPreserver extends TimeStampFilePreserver<byte[]> {
    final private static Logger logger = JUL.getLogger(ByteArrayPreserver.class);

    public ByteArrayPreserver() {
        setNameSep("_");
        setRandStrLen(5);
        setType(2);
    }

//    public static byte[] merge(List<byte[]> data) {
//        if (data.size() == 0) {
//            return new byte[0];
//        }
//        if (data.size() == 1) {
//            return data.get(0);
//        }
//
//        byte[] all;
//        int total = 0;
//        for (int i = 0; i < data.size(); i++){
//            total += data.get(i).length;
//        }
//        all = new byte[total];
//
//        int index = 0;
//        for (int i = 0; i < data.size(); i++) {
//            for (int j = 0; j < data.get(i).length; j++) {
//                all[index + j] = data.get(i)[j];
//            }
//            index += data.get(i).length;
//        }
//        logger.info("**  The Merged File Size: " + all.length);
//        return all;
//    }

    private String save(byte[] data) throws IOException {
        Require.stateNotNull(data);

//        byte[] all = merge(data);
        File saveFile = getDestFile();

        ByteArrayInputStream is = new ByteArrayInputStream(data);
        Files.copy(is, Paths.get(saveFile.getAbsolutePath()));


        logger.info("** Data Saved to : \"" + saveFile.getAbsolutePath() + "\"");
        return saveFile.getAbsolutePath();
    }


    @Override
    public String apply(byte[] data) {
        try {
            return save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
