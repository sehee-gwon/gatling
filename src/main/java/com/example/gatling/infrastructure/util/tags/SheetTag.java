package com.example.gatling.infrastructure.util.tags;

import com.example.gatling.infrastructure.util.RandomSheetUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SheetTag {
    private Element root;
    private int width;
    private int height;

    public static final int SIZE_MIN = 30;
    public static final int SIZE_MAX = 10001;

    public SheetTag(Document doc, String key, int page) {
        if (doc == null) {
            throw new IllegalStateException("document is required");
        }

        this.root = doc.createElement("SHEET");
        this.root.setAttribute("appVersion", "1.10.53");
        this.root.setAttribute("Version", "1.5.0.4");
        this.root.setAttribute("RatioId", "");
        this.root.setAttribute("TemplateName", "");
        this.root.setAttribute("LayoutId", "");
        this.root.setAttribute("PageTitle", "제목없음");
        this.root.setAttribute("PageMemo", "");
        this.root.setAttribute("IsFreeStyle", "true");
        this.root.setAttribute("Page", String.valueOf(page));
        this.root.setAttribute("PageDuration", "5");
        this.root.setAttribute("PagePersistentKey", key);

        this.width = RandomSheetUtil.rand(SIZE_MIN, SIZE_MAX);
        this.height = RandomSheetUtil.rand(SIZE_MIN, SIZE_MAX);

        addSheetSize(doc);
        addTemplate(doc);
        addBackground(doc);
        addGuideLines(doc);
        addPageAnimations(doc);
    }

    private void addSheetSize(Document doc) {
        Element sheetSize = doc.createElement("SHEETSIZE");
        sheetSize.setAttribute("cx", String.valueOf(this.width));
        sheetSize.setAttribute("cy", String.valueOf(this.height));
        this.root.appendChild(sheetSize);
    }

    private void addTemplate(Document doc) {
        Element template = doc.createElement("TEMPLATE");
        template.setAttribute("Width", String.valueOf(this.width));
        template.setAttribute("Height", String.valueOf(this.height));
        this.root.appendChild(template);
    }

    private void addBackground(Document doc) {
        Element background = doc.createElement("TEMPLATE");
        background.setAttribute("Color", RandomSheetUtil.randColorCode());
        background.setAttribute("LayerName", "");

        Element pureSkinSize = doc.createElement("PureSkinSize");
        pureSkinSize.setAttribute("cx", "0");
        pureSkinSize.setAttribute("cy", "0");
        background.appendChild(pureSkinSize);

        Element cropRect = doc.createElement("CropRect");
        cropRect.setAttribute("Left", "0");
        cropRect.setAttribute("Top", "0");
        cropRect.setAttribute("Right", "0");
        cropRect.setAttribute("Bottom", "0");
        background.appendChild(cropRect);

        this.root.appendChild(background);
    }

    private void addGuideLines(Document doc) {
        Element guideLines = doc.createElement("GUIDELINES");

        int hPosition = RandomSheetUtil.rand(0, this.width+1);
        Element horizontal = doc.createElement("HORIZONTAL");
        horizontal.setAttribute("CurrentPosition", String.valueOf(hPosition));
        horizontal.setAttribute("Positive", "true");

        Element hGuideLine = doc.createElement("GUIDELINE");
        hGuideLine.setAttribute("InitPosition", "0");
        hGuideLine.setAttribute("LastPosition", String.valueOf(hPosition));

        horizontal.appendChild(hGuideLine);

        int vPosition = RandomSheetUtil.rand(0, this.height+1);
        Element vertical = doc.createElement("VERTICAL");
        vertical.setAttribute("CurrentPosition", String.valueOf(vPosition));
        vertical.setAttribute("Positive", "true");

        Element vGuideLine = doc.createElement("GUIDELINE");
        vGuideLine.setAttribute("InitPosition", "0");
        vGuideLine.setAttribute("LastPosition", String.valueOf(vPosition));

        vertical.appendChild(vGuideLine);

        guideLines.appendChild(horizontal);
        guideLines.appendChild(vertical);

        this.root.appendChild(guideLines);
    }

    private void addPageAnimations(Document doc) {
        Element pageAnimations = doc.createElement("PageAnimations");
        pageAnimations.setAttribute("introPageAnimationPreset", "NONE");
        pageAnimations.setAttribute("outroPageAnimationPreset", "NONE");
        this.root.appendChild(pageAnimations);
    }

    public Element getRootNode() {
        return this.root;
    }
}