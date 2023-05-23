package com.example.gatling;

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