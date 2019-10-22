package yich.base.util;

import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {
    final private static Logger logger = JUL.getLogger(FileUtil.class);

    public static class DirChecker {
        String beforeMsg = null;
        String afterMsg = null;

        public DirChecker beforeMake(String msg) {
            this.beforeMsg = "".equals(msg) ? null : msg;
            return this;
        }

        public DirChecker afterMake(String msg) {
            this.afterMsg = "".equals(msg) ? null : msg;
            return this;
        }

        public void check(String path) {
            File dirs = new File(path);
            if (!dirs.exists()) {
                if (beforeMsg != null) {
                    logger.info(beforeMsg);
                }
                dirs.mkdirs();
                if (afterMsg != null) {
                    logger.info(afterMsg);
                }
            }
        }
    }

    public static DirChecker dirChecker() {
        return new DirChecker();
    }

    // return all files and sub-directories paths in the current directory
    public static List<Path> list(Path dir, Predicate<Path> predicate){
        Require.argumentWCM(Files.isDirectory(dir), "The path is not a Directory");
        Require.argumentNotNull(predicate, "Predicate<Path> predicate");

        List<Path> result;
        try (Stream<Path> list = Files.list(dir)) {
            result = list.filter(predicate)
//                         .map(x -> x.toString())
                         .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static List<Path> list(String dir, Predicate<Path> predicate) {
        return list(Paths.get(dir), predicate);
    }

    // return all files and sub-directories paths in the current directory
    // as strings
    public static List<String> listAsStr(Path dir, Predicate<Path> predicate) {
        List<Path> list = list(dir, predicate);
        return list.stream()
                   .map(x -> x.toString())
                   .collect(Collectors.toList());
    }

    public static List<String> listAsStr(String dir, Predicate<Path> predicate) {
        return listAsStr(Paths.get(dir), predicate);
    }

    // read text & return a string
    public static String readText(Path path) {
        Require.argumentWCM(Files.isRegularFile(path), "The path is not a Regular File");

        String re;
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            re = br.lines()
                    .reduce((a, b) -> a + "\n" + b)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return re;
    }

    public static String readText(String path){
        return readText(Paths.get(path));
    }

    // read text and split it into a list of strings
    public static List<String> readText(Path path, Pattern ptn) {
        Require.argumentNotNull(ptn, "Pattern ptn");

        String text = readText(path);
        List<String> list = new ArrayList<>();

        Matcher matcher = ptn.matcher(text);
        int index = 0;
        String temp;
        while (matcher.find()){
            temp = matcher.group(2);
            list.add(index++, temp);
        }

        return list;
    }

    public static List<String> readText(String path, Pattern ptn) {
        return readText(Paths.get(path), ptn);
    }

}
