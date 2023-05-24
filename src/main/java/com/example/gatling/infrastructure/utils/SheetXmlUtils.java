package com.example.gatling.infrastructure.utils;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.infrastructure.exception.SimulationException;
import com.example.gatling.infrastructure.utils.tags.SheetTag;
import com.example.gatling.infrastructure.utils.tags.SingleTag;
import com.example.gatling.infrastructure.utils.tags.SvgTag;
import com.example.gatling.infrastructure.utils.tags.TextTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
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
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetXmlUtils {
    public static final String TAG_NAME = "SVG";

    public static final String DESIGN_UUID_FORMAT = "aaaaaaaa-0000-0000-0000-%012d";
    public static final String SHEET_UUID_FORMAT = "bbbbbbbb-1111-1111-1111-%012d";
    public static final String ELEMENT_UUID_FORMAT = "cccccccc-2222-2222-2222-%012d";

    /**
     * 시트 추가/수정/삭제 테스트 데이터 생성<br/>
     * sheetId, sheetData
     * @param sheetIds
     * @param elementSize
     * @return
     */
    public static List<Sheet> createSheets(ActionType actionType, List<Integer> sheetIds, int elementSize) {
        List<Sheet> sheets = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        TransformerFactory tf = TransformerFactory.newInstance();

        try {
            int page = 1;
            for (Integer sheetId : sheetIds) {
                UUID sheetUUID = createUUID(SHEET_UUID_FORMAT, sheetId);

                if (actionType != ActionType.DELETE) {
                    Document doc = dbf.newDocumentBuilder().newDocument();
                    org.w3c.dom.Element sheet = new SheetTag(doc, sheetUUID, page).getElement();

                    for (int i=0; i<elementSize; i++) {
                        sheet.appendChild(createTag(doc, TAG_NAME, createUUID(DESIGN_UUID_FORMAT, i)));
                    }

                    doc.appendChild(sheet);
                    sheets.add(Sheet.save(sheetUUID, toXmlString(doc, tf, true)));
                } else {
                    sheets.add(Sheet.delete(sheetUUID));
                }

                page++;
            }
        } catch (ParserConfigurationException | TransformerException e) {
            throw new SimulationException("sheet XML processing error", e);
        }

        return sheets;
    }

    /**
     * 요소 추가/수정/삭제 테스트 데이터 생성<br/>
     * sheetId, elements
     * @param actionType
     * @param sheetId
     * @param elementIds
     * @return
     */
    public static List<Sheet> createElements(ActionType actionType, int sheetId, List<Integer> elementIds) {
        List<Sheet> sheets = new ArrayList<>();
        List<Element> elements = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        TransformerFactory tf = TransformerFactory.newInstance();

        try {
            for (Integer elementId : elementIds) {
                UUID elementUUID = createUUID(ELEMENT_UUID_FORMAT, elementId);

                if (actionType != ActionType.DELETE) {
                    Document doc = dbf.newDocumentBuilder().newDocument();
                    doc.appendChild(createTag(doc, TAG_NAME, elementUUID));
                    elements.add(new Element(elementUUID, toXmlString(doc, tf, false)));
                } else {
                    elements.add(new Element(elementUUID));
                }
            }

            sheets.add(Sheet.save(createUUID(SHEET_UUID_FORMAT, sheetId), elements));
        } catch (ParserConfigurationException | TransformerException e) {
            throw new SimulationException("element XML processing error", e);
        }

        return sheets;
    }

    /**
     * 요소 테스트 데이터 생성
     * @param doc
     * @param tagName
     * @param elementId
     * @return
     */
    public static org.w3c.dom.Element createTag(Document doc, String tagName, UUID elementId) {
        SingleTag tag = new SingleTag(doc, tagName, elementId);

        if ("SVG".equals(tagName)) {
            SvgTag svg = new SvgTag(doc, elementId);
            svg.addFillColorMap(doc);
            svg.addShadow(doc);
            svg.addSvgLayer(doc);
            tag = svg;
        }
        if ("TEXT".equals(tagName)) {
            TextTag text = new TextTag(doc, elementId);
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

    /**
     * UUID 테스트 데이터 생성
     * @param format
     * @param id
     * @return
     */
    public static UUID createUUID(String format, int id) {
        return UUID.fromString(String.format(format, id));
    }

    private static String toXmlString(Document doc, TransformerFactory tf, boolean hasDeclaration) throws TransformerException {
        StringWriter writer = new StringWriter();

        Transformer transformer = tf.newTransformer();
        if (!hasDeclaration) transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // 최상단 xml 태그 제외
        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        return Base64.encodeBase64String(writer.toString().getBytes());
    }
}
