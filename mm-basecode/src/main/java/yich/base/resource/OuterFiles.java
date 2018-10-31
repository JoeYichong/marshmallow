package yich.base.resource;

import yich.base.dbc.Require;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class OuterFiles {

    public static String getStr(String path) {
        InputStream is = getIS(path);
        if (is == null) {
            throw new RuntimeException("Error: Can't Find the Resource \"" + path + "\" Required.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public static InputStream getIS(String path) {
        InputStream is;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error: File Not Found \"" + path + "\" Required");
        }
        return is;
    }

    public static class Item {
        private String path;
        private String value;

        private Item(String path) {
            Require.argumentWCM(Files.exists(Paths.get(path)), "Outer File \'" + path + "\' doesn't Exist.");
            this.path = path;
        }

        public String path() {
            return path;
        }

        public String str() {
            return value == null ? (value = getStr(path)) : value;
        }

        public InputStream is() {
            return getIS(path);
        }

    }

    public static OuterFiles.Item item(String path) {
        if (!Files.exists(Paths.get(path))) {
            path = System.getProperty("user.dir") + File.separator + path;
        }
        return new OuterFiles.Item(path);
    }


}
