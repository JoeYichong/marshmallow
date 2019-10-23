package yich.base.preserver;


import yich.base.dbc.Require;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class AbsFilePreserver<T> implements FilePreserver<T> {
    private String basePath;
    private List<String> tags;
    private String format;
    private List<String> appendedPath;
    private String nameSep;
    private int randStrLen;

    public AbsFilePreserver() {
        basePath = System.getProperty("user.dir")
                + File.separator + "_docs_" + File.separator;
        this.nameSep = "_";
        this.randStrLen = 0;
    }

    @Override
    public String getBasePath() {
        return basePath;
    }

    @Override
    public AbsFilePreserver setBasePath(String basePath) {
        Require.argumentNotNullAndNotEmpty(basePath, "String basePath");
        Require.argumentWCM(Files.isDirectory(Paths.get(basePath)), "\"" + basePath + "\" isn't Directory.");
        this.basePath = basePath;
        return this;
    }

    @Override
    public String tag() {
        if (this.tags == null)
            return "";

        return String.join("_", tags);
    }

    @Override
    public AbsFilePreserver setTag(String... tags) {
        if (tags != null && tags.length > 0){
            this.tags = Arrays.asList(tags);
        }

        return this;
    }

    @Override
    public FilePreserver appendTag(String... tags){
        if (this.tags == null){
            this.tags = new ArrayList<>();
        }
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    @Override
    public FilePreserver removeTag(String... tags) {
        if (this.tags != null) {
            this.tags.removeAll(Arrays.asList(tags));
        }
        return this;
    }

    @Override
    public String format() {
        return format;
    }

    @Override
    public AbsFilePreserver setFormat(String format) {
        Require.argumentNotNull(format);
        this.format = format;
        return this;
    }

    @Override
    public FilePreserver appendDir(String dirName) {
        Require.argumentNotNullAndNotEmpty(dirName, "String dirName");
        if (appendedPath == null) {
            this.appendedPath = new ArrayList<>();
        }
        if (!appendedPath.contains(dirName)) {
            appendedPath.add(dirName);
        }
        return this;
    }

    @Override
    public FilePreserver removeAppendedDir(String dirName) {
        Require.argumentNotNullAndNotEmpty(dirName, "String dirName");
        if (appendedPath != null) {
            appendedPath.remove(dirName);
        }
        return this;
    }

    @Override
    public String getAppendedPath() {
        if (appendedPath == null || appendedPath.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String name : appendedPath) {
            if (name != null && name.length() > 0) {
                sb.append(name).append(File.separator);
            }
        }
        return sb.length() != 0 ? sb.toString() : "";
    }

    public String nameSep() {
        return nameSep;
    }

    public AbsFilePreserver setNameSep(String nameSep) {
        Require.argumentNotNull(nameSep);

        this.nameSep = nameSep;
        return this;
    }

    public int randStrLen() {
        return randStrLen;
    }

    public AbsFilePreserver setRandStrLen(int randStrLen) {
        Require.argument(randStrLen >= 0, randStrLen, "randStrLen >= 0");

        this.randStrLen = randStrLen;
        return this;
    }

    @Override
    public void accept(T t) {
        apply(t);
    }

}
