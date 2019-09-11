package yich.base.office;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class WordUtil {
//    public static void getText(Node element) {
//        for (Node n : element.childNodes()) {
//            if (n instanceof TextNode && !((TextNode) n).isBlank()) {
//                System.out.println(((TextNode) n).text());
//            } else {
//                getText(n);
//            }
//        }
//    }
//
//    public static void getText(Elements elements){
//        for (Node n : elements) {
//            if (n instanceof TextNode && !((TextNode) n).isBlank()) {
//                System.out.println(((TextNode) n).text());
//            } else {
//                getText(n);
//            }
//        }
//
//    }

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

//    public static Document getPage(String url, int timeout) {
//
//        System.out.println("**Start get Page: " + url);
//        Document doc = null;
//        for (;;) {
//            try {
//                doc = Jsoup.connect(url)
//                        //.userAgent("Mozilla")
//                        .cookie("auth", "token")
//                        .timeout(timeout)
//                        .get();
//                break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("**End get Page");
//        return doc;
//    }
//
//
//    public static String reduce(List<String> list){
//        String str =  list.stream()
//                .map(e -> {
//                    return "\"" + e + "\"";
//                })
//                .reduce((a, b) -> {
//                    System.out.println("Hi");
//                    return a + ", " + b;
//                }).get();
//        return "[" + str + "]";
//    }
//
//    public static String reduce2(List<String> list){
//        String str =  list.stream()
//                .reduce((a, b) -> {
//                    //System.out.println("Hi");
//                    return a + "\r\n" + b;
//                }).get();
//        return str;
//
//    }
//
//    public static void toContentFile(String txt, String file, boolean append)  {
//        if (txt != null) {
//            try(OutputStream os = new FileOutputStream(file, append)) {
//                os.write(txt.getBytes());
//                os.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
