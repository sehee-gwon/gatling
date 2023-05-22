package com.example.gatling.infrastructure.util;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomSheetUtilTest {

    @Test
    void createSheets() {
        assertDoesNotThrow(() -> {
            List<Sheet> sheets = RandomSheetUtil.createSheets(ActionType.INSERT, 5, 10);
            for (Sheet sheet : sheets) {
                System.out.println(sheet.getSheetKey());
                for (Element element : sheet.getElements()) {
                    assertNotNull(element.getData());
                    System.out.println(element.getData());
                }
                System.out.println();
            }
        });
    }
}