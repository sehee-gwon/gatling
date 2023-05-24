package com.example.gatling.design.domain;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"sheetId"})
public class Sheet {
    private UUID sheetId;
    private String sheetData;
    private List<Element> elements;

    @Builder
    private Sheet(UUID sheetId, String sheetData, List<Element> elements) {
        this.sheetId = sheetId;
        this.sheetData = sheetData;
        this.elements = elements;
    }

    public static Sheet save(UUID sheetId, String sheetData) {
        return Sheet.builder()
                .sheetId(sheetId)
                .sheetData(sheetData)
                .build();
    }

    public static Sheet save(UUID sheetId, List<Element> elements) {
        return Sheet.builder()
                .sheetId(sheetId)
                .elements(elements)
                .build();
    }

    public static Sheet delete(UUID sheetId) {
        return Sheet.builder()
                .sheetId(sheetId)
                .build();
    }
}
