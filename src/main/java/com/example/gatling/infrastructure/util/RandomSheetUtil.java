package com.example.gatling.infrastructure.util;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.infrastructure.util.tags.SheetTag;
import com.example.gatling.infrastructure.util.tags.SvgTag;
import com.example.gatling.infrastructure.util.tags.SingleTag;
import com.example.gatling.infrastructure.util.tags.TextTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomSheetUtil {
    public static final String[] TAG_ARRAY = {"SVG", "PHOTO", "TEXT"};

    public static List<Sheet> createSheets(ActionType actionType, int sheetSize, int elementSize) {
        if (sheetSize > 999) sheetSize = 999;
        if (elementSize > 999) elementSize = 999;

        List<Sheet> sheets = new ArrayList<>();

        for (int i=0; i<sheetSize; i++) {
            int page = i+1;
            String key = String.format("SHEET_%03d", page);
            List<Element> elements = new ArrayList<>();

            for (int j=0; j<elementSize; j++) {
                elements.add(new Element(String.format("ELEMENT_%03d", (j+1)), createXml(actionType, key, page, TAG_ARRAY[j % TAG_ARRAY.length])));
            }

            sheets.add(new Sheet(key, elements));
        }

        return sheets;
    }

    public static String createXml(ActionType actionType, String key, int page, String tagName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            SingleTag tag = new SingleTag(doc, tagName);

            if ("SVG".equals(tagName)) {
                SvgTag svg = new SvgTag(doc);
                svg.addFillColorMap(doc);
                svg.addShadow(doc);
                svg.addSvgLayer(doc);
                tag = svg;
            }
            if ("TEXT".equals(tagName)) {
                TextTag text = new TextTag(doc);
                text.addTextData(doc);
                text.addRenderPos(doc);
                text.addText(doc);
                text.addFont(doc);
                text.addCurve(doc);
                text.addEffect(doc);
                tag = text;
            }

            if (actionType != ActionType.INSERT) {  // 최상단의 xml 태그 제외
                doc.appendChild(tag.getRootNode());
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            } else {
                org.w3c.dom.Element sheetNode = new SheetTag(doc, key, page).getRootNode();
                sheetNode.appendChild(tag.getRootNode());
                doc.appendChild(sheetNode);
            }

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("XML parser error");
        }
    }

    public static int rand(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double rand(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean rand() {
        int value = ThreadLocalRandom.current().nextInt(0, 2);
        return value == 0;
    }

    public static String randColorCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return String.format("#%06X", (0xFFFFFF & new Color(r, g, b).getRGB()));
    }

    public static String randAlphabet() {
        char ch = (char) ThreadLocalRandom.current().nextInt(65, 91);
        return Character.toString(ch);
    }
}
