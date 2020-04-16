package org.apache.poi.xwpf.usermodel.examples;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class SimpleDocumentSdt {

    private static String documentName = "";

    // for colored output in console
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    public static void sout(String str, String color) {
        System.out.println(color + str + RESET);
    }

    public static void main(String[] args) throws Exception {

        try (XWPFDocument doc = new XWPFDocument(
                new FileInputStream(documentName)
        )) {
            List<IBodyElement> list = doc.getBodyElements(); // get body elements

            for (IBodyElement elem : list) {

                if (elem instanceof XWPFParagraph) {

                    XWPFParagraph paragraph = (XWPFParagraph) elem;

                    for (IRunElement runElement : paragraph.getIRuns()) { // traverse paragraph contents

                        if (runElement instanceof XWPFSDT) {

                            XWPFSDT sdt = (XWPFSDT) runElement;

                            sout("SDT content: " + sdt.getContent().getText(), RED);

                            List <XWPFRun> runs = sdt.getContent().getContent();

                            for(int i = runs.size() - 1; i > 0; i--) {
                                paragraph.removeRun(i);
                            }

                            XWPFRun run = runs.get(0);
                            run.setText("New text for SdtRun . . .", 0); // modify text of SdtRun

                            List<CTString> tagList = sdt.getSdtPr().getTagList();
//                            for (CTString tag : tagList) {
//                                sout(tag.getVal(), RED);
//                            }
                            tagList.get(0).setVal(tagList.get(0).getVal() + "-modified-tag"); // modify Tag

                            List<CTLock> lockList = sdt.getSdtPr().getLockList();
//                            for (CTLock lock : lockList) {
//                                sout(lock.getVal().toString(), RED);
//                            }
                            lockList.get(0).setVal(STLock.Enum.forInt(STLock.INT_SDT_LOCKED)); // modify Lock
                        }
                    }
                }
            }

            sout(doc.getDocument().toString(), GREEN); // xml structure of document.xml
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void saveDocument(XWPFDocument doc) throws IOException {
        try (FileOutputStream out = new FileOutputStream(documentName)) {
            doc.write(out);
        }
    }
}
