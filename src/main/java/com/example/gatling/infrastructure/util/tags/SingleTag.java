package com.example.gatling.infrastructure.util.tags;

import com.example.gatling.infrastructure.util.RandomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SingleTag {
    protected Element element;

    public static final int ROTATE_MAX = 360;
    public static final int OPACITY_MAX = 256;
    public static final double POSITION_MAX = 10000.1;

    public SingleTag(Document doc, String tagName) {
        if (doc == null) {
            throw new IllegalStateException("document is required");
        }

        this.element = doc.createElement(tagName);
        this.element.setAttribute("Rotate", String.valueOf(RandomUtil.rand(0, ROTATE_MAX)));
        this.element.setAttribute("Opacity", String.valueOf(RandomUtil.rand(0, OPACITY_MAX)));
        this.element.setAttribute("FlipH", String.valueOf(RandomUtil.rand(-1, 1)));
        this.element.setAttribute("FlipV", String.valueOf(RandomUtil.rand(-1, 1)));
        this.element.setAttribute("AddedBy", String.valueOf(RandomUtil.rand()));
        this.element.setAttribute("TbpeId", "");
        this.element.setAttribute("GroupId", "");
        this.element.setAttribute("copyKey", "");
        this.element.setAttribute("ConnectedUniqueId", "");
        this.element.setAttribute("LayerName", String.valueOf(RandomUtil.rand()));
        this.element.setAttribute("Priority", "");

        addPosition(doc);
        addHyperLink(doc);
        addLogData(doc);
    }

    private void addPosition(Document doc) {
        Element position = doc.createElement("Position");
        position.setAttribute("Left", String.valueOf(RandomUtil.rand(0, POSITION_MAX)));
        position.setAttribute("Top", String.valueOf(RandomUtil.rand(0, POSITION_MAX)));
        position.setAttribute("Right", String.valueOf(RandomUtil.rand(0, POSITION_MAX)));
        position.setAttribute("Bottom", String.valueOf(RandomUtil.rand(0, POSITION_MAX)));
        this.element.appendChild(position);
    }

    private void addHyperLink(Document doc) {
        Element hyperLink = doc.createElement("HyperLink");
        hyperLink.setAttribute("Active", String.valueOf(RandomUtil.rand()));
        hyperLink.setAttribute("Type", "URL");
        hyperLink.setAttribute("Value", "");
        this.element.appendChild(hyperLink);
    }

    private void addLogData(Document doc) {
        Element logData = doc.createElement("LogData");
        logData.setAttribute("RefResourceKey", "");
        this.element.appendChild(logData);
    }

    public Element getElement() {
        return this.element;
    }
}
