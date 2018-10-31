package yich.base.resource;


import yich.base.dbc.Require;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;


abstract public class PropertyFiles {

    private static Properties getProperties(String path) {
        Properties properties = new Properties();
        try(InputStream is = new FileInputStream(path)) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

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
        private Properties properties = null;
        private String value;

        private Item(String path) {
            Require.argumentWCM(Files.exists(Paths.get(path)), "Properties File doesn't Exist.");
            this.path = path;
        }

        public String path() {
            return path;
        }

        public Properties properties() {
            if (properties == null) {
                properties = getProperties(path);
            }
            return properties;
        }

        public String getProperty(String key) {
            return properties().getProperty(key);
        }

        public String str() {
            return value == null ? (value = getStr(path)) : value;
        }

        public InputStream is() {
            return getIS(path);
        }

    }

    public static PropertyFiles.Item item(String path) {
        if (!Files.exists(Paths.get(path))) {
            path = System.getProperty("user.dir") + File.separator + path;
        }
        return new PropertyFiles.Item(path);
    }



}
