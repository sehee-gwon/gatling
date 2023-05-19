package com.example.gatling.infrastructure.util;

import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSheetUtil {
    public static final long ID_MAX = 1000000L;

    public static final int SHEET_SIZE = 5;
    public static final int ELEMENT_SIZE = 10;

    public static final int ROTATE_MAX = 359;
    public static final int OPACITY_MAX = 255;
    public static final double POSITION_MAX = 10000.0;
    public static final int FILL_COLOR_MAX = 5;

    public static final String[] TAG_ARRAY = {"PHOTO", "SVG", "TEXT", "LineShapeItem", "FrameItem", "Chart"};

    public static List<Sheet> createSheets() throws ParserConfigurationException {
        List<Sheet> sheets = new ArrayList<>();
        for (int i=0; i<rand(1, SHEET_SIZE); i++) {
            sheets.add(new Sheet(UUID.randomUUID(), createElements()));
        }
        return sheets;
    }

    public static List<Element> createElements() throws ParserConfigurationException {
        List<Element> elements = new ArrayList<>();
        for (int i=0; i<rand(1, ELEMENT_SIZE); i++) {
            Long elementId = rand(1L, ID_MAX);
            elements.add(new Element(elementId, createXml(TAG_ARRAY[rand(0, TAG_ARRAY.length-1)], elementId)));
        }
        return elements;
    }

    public static String createXml(String tagName, Long elementId) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        org.w3c.dom.Element rootElement = doc.createElement(tagName);
        rootElement.setAttribute("ElementId", String.valueOf(elementId));
        rootElement.setAttribute("Rotate", rand(0, ROTATE_MAX));
        rootElement.setAttribute("Opacity", rand(0, OPACITY_MAX));
        rootElement.setAttribute("FlipH", rand(-1, 0));
        rootElement.setAttribute("FlipV", rand(-1, 0));
        rootElement.setAttribute("AddedBy", "false");
        rootElement.setAttribute("TbpeId", "");
        rootElement.setAttribute("GroupId", "");
        rootElement.setAttribute("copyKey", "");
        rootElement.setAttribute("ConnectedUniqueId", "");
        rootElement.setAttribute("LayerName", "false");
        rootElement.setAttribute("Priority", "");

        org.w3c.dom.Element positionElement = doc.createElement("Position");
        positionElement.setAttribute("Left", rand(0, POSITION_MAX));
        positionElement.setAttribute("Top", rand(0, POSITION_MAX));
        positionElement.setAttribute("Right", rand(0, POSITION_MAX));
        positionElement.setAttribute("Bottom", rand(0, POSITION_MAX));

        org.w3c.dom.Element hyperLinkElement = doc.createElement("HyperLink");
        hyperLinkElement.setAttribute("Active", "false");
        hyperLinkElement.setAttribute("Type", "URL");
        hyperLinkElement.setAttribute("Value", "");

        org.w3c.dom.Element logDataElement = doc.createElement("LogData");
        logDataElement.setAttribute("RefResourceKey", "");

        if ("SVG".equals(tagName)) {
            org.w3c.dom.Element fillColorMapElement = doc.createElement("fillColorMap");

            for (int i=0; i<FILL_COLOR_MAX; i++) {
                org.w3c.dom.Element fillColorElement = doc.createElement("fillColor");
                fillColorElement.setAttribute("originColor", randColorCode());
                fillColorElement.setAttribute("color", randColorCode());
                fillColorMapElement.appendChild(fillColorElement);
            }

            org.w3c.dom.Element shadowElement = doc.createElement("Shadow");
            shadowElement.setAttribute("IsShadow", "false");
            shadowElement.setAttribute("Color", "#000000");
            shadowElement.setAttribute("Angle", "");
            shadowElement.setAttribute("Alpha", "");
            shadowElement.setAttribute("Distance", "");
            shadowElement.setAttribute("Blur", "");

            org.w3c.dom.Element svgLayerElement = doc.createElement("SvgLayer");
            svgLayerElement.setAttribute("layerId", "TESTLAYERS");
            svgLayerElement.setAttribute("left", "");
            svgLayerElement.setAttribute("top", "");
            svgLayerElement.setAttribute("scaleX", "1");
            svgLayerElement.setAttribute("scaleY", "1");
            svgLayerElement.setAttribute("scaleCommon", "1");
            svgLayerElement.setAttribute("layerLeft", "0");
            svgLayerElement.setAttribute("layerTop", "0");
            svgLayerElement.setAttribute("layerWidth", "");
            svgLayerElement.setAttribute("layerHeight", "");
            svgLayerElement.setAttribute("moveOffsetX", "0");
            svgLayerElement.setAttribute("moveOffsetY", "0");
            svgLayerElement.setAttribute("extendOffsetX", "0");
            svgLayerElement.setAttribute("extendOffsetY", "0");
            svgLayerElement.setAttribute("type", "NO_PATTERN");
        }

        if ("TEXT".equals(tagName)) {
            org.w3c.dom.Element textDataElement = doc.createElement("TextData");
            textDataElement.setTextContent("[{\"a\":1,\"s\":1.4,\"il\":0,\"o\":\"NONE\",\"l\":0,\"c\":[{\"t\":\"↛\",\"c\":\"#000000\",\"z\":48.5514,\"g\":\"#00000000\",\"f\":0,\"dx\":100,\"ds\":0,\"oa\":false,\"ol\":255,\"oc\":\"#000000\",\"oz\":5,\"sa\":false,\"sl\":40,\"sg\":135,\"sc\":\"#000000\",\"sd\":10,\"sp\":\"\",\"sz\":1,\"sr\":0,\"yb\":false,\"yf\":\"Noto Sans CJK KR Regular\",\"yd\":467,\"yi\":false,\"yu\":false,\"yt\":false,\"sst\":\"NONE\"}]}]");

            org.w3c.dom.Element renderPosElement = doc.createElement("RenderPos");
            renderPosElement.setTextContent("{\"c\":[{\"x\":0,\"y\":53.82,\"w\":34.258,\"a\":56.32,\"d\":15.536,\"t\":\"↛\",\"s\":48.552,\"ds\":0,\"f\":\"rgb(0, 0, 0)\",\"yd\":467,\"r\":[0,-2.5,34.258,72.5]}],\"s\":[0,0,34.258,67.5],\"t\":[0,17.019,49.424,40.394]}");

            /*sb.append("<Text>").append(randAlphabet()).append("</Text>");

            sb.append("<Font");
            sb.append(" Color=\"").append(randColorCode()).append("\"");
            sb.append(" Family=\"Noto Sans CJK KR Regular\">");
            sb.append("<Style Bold=\"false\" Italic=\"false\" Strikeout=\"false\" Underline=\"false\"></Style>");
            sb.append("</Font>");

            sb.append("<Curve IsCurved=\"false\"></Curve>");
            sb.append("<Effect TextSpace=\"0\" ScaleX=\"1\"><Outline DoOutline=\"false\" Color=\"0000000\" Size=\"5\"/><Shadow DoShadow=\"false\" Color=\"28000000\" Distance=\"10\" Angle=\"315\" Spread=\"0\"/><Fill Type=\"COLOR\"/></Effect>");*/
        }

        //sb.append("</").append(nodeName).append(">");

        return "";
    }

    public static String rand(long min, long max) {
        return String.valueOf(ThreadLocalRandom.current().nextLong(min, max));
    }

    public static String rand(int min, int max) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(min, max));
    }

    public static String rand(double min, double max) {
        return String.valueOf(ThreadLocalRandom.current().nextDouble(min, max));
    }

    public static String randColorCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return String.format("#%06X", (0xFFFFFF & new Color(r, g, b).getRGB()));
    }

    public static String randAlphabet() {
        char ch = (char) ThreadLocalRandom.current().nextInt(65, 90);
        return Character.toString(ch);
    }
}
