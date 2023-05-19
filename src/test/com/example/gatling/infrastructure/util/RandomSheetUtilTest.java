package com.example.gatling.infrastructure.util;

import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomSheetUtilTest {

    @Test
    void createSheets() throws ParserConfigurationException, IOException, SAXException {
        List<Sheet> sheets = RandomSheetUtil.createSheets();
        for (Sheet sheet : sheets) {
            for (Element element : sheet.getElementList()) {
            }
        }
    }
}