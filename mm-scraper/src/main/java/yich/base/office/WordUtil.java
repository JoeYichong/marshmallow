package yich.base.office;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import yich.base.dbc.Require;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtil {

    private static void insertString(String str, XWPFRun run) throws IOException {
        if (str.contains("\n")) {
            String[] lines = str.split("\n");
            run.setText(lines[0]); // set first line into XWPFRun
//            System.out.println(lines[0]);
            for(int i = 1; i < lines.length; i++){
                // add break and insert new text
                run.addBreak();
                run.setText(lines[i]);
//                System.out.println(lines[i]);
            }
        } else {
            run.setText(str);
        }
        if (str.endsWith("\n"))
            run.addBreak();
    }

    public static void createDocx(Collection<String> txts, File dest_file) throws IOException {
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(dest_file);

        //create Paragraph
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        for (String str : txts) {
            insertString(str, run);
            run.addBreak();
        }

        document.write(out);

        //Close document
        out.close();

    }

    //
    public static void createDocx(String txt, File dest_file) throws IOException {
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(dest_file);

        //create Paragraph
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        insertString(txt, run);
//        if (txt.contains("\n")) {
//            String[] lines = txt.split("\n");
//            run.setText(lines[0], 0); // set first line into XWPFRun
//            for(int i = 1; i < lines.length; i++){
//                // add break and insert new text
//                run.addBreak();
//                run.setText(lines[i]);
//            }
//        } else {
//            run.setText(txt, 0);
//        }

//        run.setText(txt);
        document.write(out);

        //Close document
        out.close();
//        System.out.println("Create Word- \'"
//                + dest_file.getAbsolutePath()
//                + "\' successfully");
    }


    public static String readDocx(File src_file){
        Require.argumentNotNull(src_file, "File src_file");

        String text;
        try(FileInputStream fis = new FileInputStream(src_file)) {
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            text = extractor.getText();

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return text;
    }

    public static List<String> readDocx(File src_file, Pattern ptn) {
        Require.argumentNotNull(ptn, "Pattern ptn");
        String text = readDocx(src_file);
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
}
