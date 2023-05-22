package com.example.gatling.infrastructure.util.tags;

import com.example.gatling.infrastructure.util.RandomSheetUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SingleTag {
    protected Element root;

    public static final int ROTATE_MAX = 360;
    public static final int OPACITY_MAX = 256;
    public static final double POSITION_MAX = 10000.1;

    public SingleTag(Document doc, String tagName) {
        if (doc == null) {
            throw new IllegalStateException("document is required");
        }

        this.root = doc.createElement(tagName);
        this.root.setAttribute("Rotate", String.valueOf(RandomSheetUtil.rand(0, ROTATE_MAX)));
        this.root.setAttribute("Opacity", String.valueOf(RandomSheetUtil.rand(0, OPACITY_MAX)));
        this.root.setAttribute("FlipH", String.valueOf(RandomSheetUtil.rand(-1, 1)));
        this.root.setAttribute("FlipV", String.valueOf(RandomSheetUtil.rand(-1, 1)));
        this.root.setAttribute("AddedBy", String.valueOf(RandomSheetUtil.rand()));
        this.root.setAttribute("TbpeId", "");
        this.root.setAttribute("GroupId", "");
        this.root.setAttribute("copyKey", "");
        this.root.setAttribute("ConnectedUniqueId", "");
        this.root.setAttribute("LayerName", String.valueOf(RandomSheetUtil.rand()));
        this.root.setAttribute("Priority", "");

        addPosition(doc);
        addHyperLink(doc);
        addLogData(doc);
    }

    private void addPosition(Document doc) {
        Element position = doc.createElement("Position");
        position.setAttribute("Left", String.valueOf(RandomSheetUtil.rand(0, POSITION_MAX)));
        position.setAttribute("Top", String.valueOf(RandomSheetUtil.rand(0, POSITION_MAX)));
        position.setAttribute("Right", String.valueOf(RandomSheetUtil.rand(0, POSITION_MAX)));
        position.setAttribute("Bottom", String.valueOf(RandomSheetUtil.rand(0, POSITION_MAX)));
        this.root.appendChild(position);
    }

    private void addHyperLink(Document doc) {
        Element hyperLink = doc.createElement("HyperLink");
        hyperLink.setAttribute("Active", String.valueOf(RandomSheetUtil.rand()));
        hyperLink.setAttribute("Type", "URL");
        hyperLink.setAttribute("Value", "");
        this.root.appendChild(hyperLink);
    }

    private void addLogData(Document doc) {
        Element logData = doc.createElement("LogData");
        logData.setAttribute("RefResourceKey", "");
        this.root.appendChild(logData);
    }

    public Element getRootNode() {
        return this.root;
    }
}
