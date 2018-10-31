package yich.base.resource;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static java.lang.Thread.currentThread;

abstract public class Resource {

    public static String getStr(String path) {
        InputStream is = currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Error: Can't Find the Resource \"" + path + "\" Required.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public static InputStream getIS(String path) {
//        return Resource.class.getClassLoader().getResourceAsStream(path);
        InputStream is = currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Error: Can't Find the Resource \"" + path + "\" Required");
        }
        return is;
    }

    public static class Item {
        private String path;
        private String value;

        private Item(String path) {
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

        public Item trimStr() {
            str();
            if (value != null) {
                value = value.replaceAll("\\/\\/.*?(\\r?\\n)", "")
                            .replaceAll("(\\r|\\n)", "")
                            .replaceAll("\\s{2,}", " ");
            }
            return this;
        }

        public Item escapeStr() {
            str();
            if (value != null) {
                value = value.replaceAll("\\\\", "\\\\\\\\")
                        .replaceAll("\\b", "")
                        .replaceAll("\\f", "\\\\f")
                        .replaceAll("\\n", "\\\\n")
                        .replaceAll("\\r", "\\\\r")
                        .replaceAll("\\t", "\\\\t")
                        .replaceAll("\\\"", "\\\\\\\"");
            }
            return this;
        }
    }

    public static Item item(String path) {
        return new Item(path);
    }

}
