package org.apache.poi.xwpf.usermodel;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;

public class XWPFSDTRun extends XWPFAbstractSDT
        implements IRunBody, IRunElement, ISDTContentsRun {

    private CTSdtRun sdtRun;
    private XWPFSDTContentRun contentRun;
    private IRunBody parent;

    public XWPFSDTRun(CTSdtRun ctSdtRun, IRunBody parent) {
        super(ctSdtRun.getSdtPr());
        this.contentRun = new XWPFSDTContentRun(ctSdtRun.getSdtContent(), this);
        this.sdtRun = ctSdtRun;
        this.parent = parent;
    }

    @Override
    public XWPFSDTContentRun getContent() {
        return this.contentRun;
    }

    public XWPFSDTContentRun createSdtContent() {
        XWPFSDTContentRun xwpfsdtContentRun = new XWPFSDTContentRun(this.sdtRun.addNewSdtContent(), this);
        this.contentRun = xwpfsdtContentRun;
        return xwpfsdtContentRun;
    }

    @Override
    public XWPFSDTPr createSdtPr() {
        XWPFSDTPr xwpfsdtPr = new XWPFSDTPr(this.sdtRun.addNewSdtPr());
        this.sdtPr = xwpfsdtPr;
        return xwpfsdtPr;
    }

    @Override
    public XWPFDocument getDocument() {
        return parent.getDocument();
    }

    @Override
    public POIXMLDocumentPart getPart() {
        return parent.getPart();
    }
}
