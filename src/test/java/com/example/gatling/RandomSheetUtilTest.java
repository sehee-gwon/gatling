package com.example.gatling;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.infrastructure.util.SheetXmlUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomSheetUtilTest {

    final int sheetSize = 5;
    final int elementSize = 10;

    /*@Test
    void 시트_추가_테스트() {
        assertDoesNotThrow(() -> {
            List<Sheet> sheets = SheetXmlUtil.getInsertSheet(sheetSize, elementSize);
            assertEquals(sheetSize, sheets.size());
            for (Sheet sheet : sheets) {
                assertEquals(1, sheet.getElements().size());
                for (Element element : sheet.getElements()) {
                    assertNotNull(element.getData());
                }
            }
        });
    }

    @Test
    void 시트_삭제_테스트() {
        assertDoesNotThrow(() -> {
            List<Sheet> sheets = SheetXmlUtil.getDeleteSheets(sheetSize);
            assertEquals(sheetSize, sheets.size());
            for (Sheet sheet : sheets) {
                assertEquals(elementSize, sheet.getElements().size());
                for (Element element : sheet.getElements()) {
                    assertNull(element.getData());
                }
            }
        });
    }*/
}