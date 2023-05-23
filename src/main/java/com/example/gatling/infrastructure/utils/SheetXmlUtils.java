package com.example.gatling.infrastructure.utils;

import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.infrastructure.exception.SimulationException;
import com.example.gatling.infrastructure.utils.tags.SheetTag;
import com.example.gatling.infrastructure.utils.tags.SingleTag;
import com.example.gatling.infrastructure.utils.tags.SvgTag;
import com.example.gatling.infrastructure.utils.tags.TextTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetXmlUtils {
    public static final String TAG_NAME = "SVG";

    public static final String DESIGN_ID_FORMAT = "DESIGN_%03d";
    public static final String SHEET_KEY_FORMAT = "SHEET_%03d";
    public static final String ELEMENT_ID_FORMAT = "ELEMENT_%03d";

    /**
     * 시트 추가 테스트 데이터 생성<br/>
     * elements data: SHEET 부터 시작
     * @param sheetKeys
     * @param elementSize
     * @return
     */
    public static List<Sheet> getInsertSheet(List<Integer> sheetKeys, int elementSize) {
        List<Sheet> sheets = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        TransformerFactory tf = TransformerFactory.newInstance();

        int page = 1;

        try {
            for (Integer sheetKey : sheetKeys) {
                List<Element> elements = new ArrayList<>();

                Document doc = dbf.newDocumentBuilder().newDocument();
                org.w3c.dom.Element sheet = new SheetTag(doc, String.format(SHEET_KEY_FORMAT, sheetKey), page++).getElement();

                for (int i=0; i<elementSize; i++) {
                    sheet.appendChild(createTag(doc, TAG_NAME));
                }

                doc.appendChild(sheet);

                StringWriter writer = new StringWriter();
                tf.newTransformer().transform(new DOMSource(doc), new StreamResult(writer));

                elements.add(new Element(String.format(ELEMENT_ID_FORMAT, 1), writer.toString()));
                sheets.add(new Sheet(String.format(SHEET_KEY_FORMAT, sheetKey), elements));
            }
        } catch (ParserConfigurationException | TransformerException e) {
            throw new SimulationException("XML processing error", e);
        }

        return sheets;
    }

    /**
     * 시트 삭제 테스트 데이터 생성<br/>
     * elements: null
     * @param sheetKeys
     * @return
     */
    public static List<Sheet> getDeleteSheets(List<Integer> sheetKeys) {
        List<Sheet> sheets = new ArrayList<>();
        for (Integer sheetKey : sheetKeys) {
            sheets.add(new Sheet(String.format(SHEET_KEY_FORMAT, sheetKey)));
        }
        return sheets;
    }

    /**
     * 요소 추가/수정 테스트 데이터 생성<br/>
     * element data: 요소부터 시작
     * @param sheetKey
     * @param elementIds
     * @return
     */
    public static List<Sheet> getSaveElement(int sheetKey, List<Integer> elementIds) {
        List<Sheet> sheets = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        TransformerFactory tf = TransformerFactory.newInstance();

        try {
            List<Element> elements = new ArrayList<>();

            for (Integer elementId : elementIds) {
                Document doc = dbf.newDocumentBuilder().newDocument();

                doc.appendChild(createTag(doc, TAG_NAME));

                StringWriter writer = new StringWriter();

                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // 최상단 xml 태그 제외
                transformer.transform(new DOMSource(doc), new StreamResult(writer));

                elements.add(new Element(String.format(ELEMENT_ID_FORMAT, elementId), writer.toString()));
            }

            sheets.add(new Sheet(String.format(SHEET_KEY_FORMAT, sheetKey), elements));
        } catch (ParserConfigurationException | TransformerException e) {
            throw new SimulationException("XML processing error", e);
        }

        return sheets;
    }

    /**
     * 요소 삭제 테스트 데이터 생성<br/>
     * element data: null
     * @param sheetKey
     * @param elementIds
     * @return
     */
    public static List<Sheet> getDeleteElement(int sheetKey, List<Integer> elementIds) {
        List<Sheet> sheets = new ArrayList<>();
        List<Element> elements = new ArrayList<>();

        for (Integer elementId : elementIds) {
            elements.add(new Element(String.format(ELEMENT_ID_FORMAT, elementId)));
        }

        sheets.add(new Sheet(String.format(SHEET_KEY_FORMAT, sheetKey), elements));
        return sheets;
    }

    public static org.w3c.dom.Element createTag(Document doc, String tagName) {
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

        return tag.getElement();
    }
}
