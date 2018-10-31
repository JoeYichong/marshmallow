package yich.base.preserver;


import yich.base.dbc.Require;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

abstract public class AbsFilePreserver<T> implements FilePreserver<T> {
    private String basePath;
    private String tag;
    private String format;
    private List<String> appendedPath;

//    {
//        this.appendedPath = new ArrayList<>();
//    }

    public AbsFilePreserver() {
        basePath = System.getProperty("user.dir")
                + File.separator + "_word_docs_" + File.separator;
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
    public String getTag() {
        return tag;
    }

    private String reduceTags(String... tags) {
        String reduced = null;
        if (tags != null && tags.length > 0) {
            reduced = "";
            for (String tag : tags) {
                reduced += tag;
            }
        }
        return reduced;
    }

    @Override
    public AbsFilePreserver setTag(String... tags) {
        this.tag = reduceTags(tags);
        return this;
    }

    @Override
    public FilePreserver appendTag(String... tags){
        this.tag = this.tag == null ? reduceTags(tags) : this.tag + reduceTags(tags);
        return this;
    }

    @Override
    public String getFormat() {
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

}
