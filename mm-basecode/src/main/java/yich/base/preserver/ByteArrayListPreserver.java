
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

public class ByteArrayListPreserver extends TimeStampFilePreserver<List<byte[]>> {
    final private static Logger logger = JUL.getLogger(ByteArrayListPreserver.class);

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

    private String save(List<byte[]> data) throws IOException {
        Require.stateNotNull(data);

        byte[] all = merge(data);
        File saveFile = getDestFile("_", 5);

        ByteArrayInputStream is = new ByteArrayInputStream(all);
        Files.copy(is, Paths.get(saveFile.getAbsolutePath()));


        logger.info("** Data Saved to : \"" + saveFile.getAbsolutePath() + "\"");
        return saveFile.getAbsolutePath();
    }

    @Override
    public File getDestFile(String nameSep, int randStrLen) {
        return getDestFile(nameSep, randStrLen, 2);
    }

    @Override
    public String apply(List<byte[]> data) {
        try {
            return save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
