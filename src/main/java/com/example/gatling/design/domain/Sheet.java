package com.example.gatling.design.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"sheetKey"})
public class Sheet {
    private String sheetKey;
    private List<Element> elements;

    public Sheet(String sheetKey, List<Element> elements) {
        this.sheetKey = sheetKey;
        this.elements = elements;
    }
}
