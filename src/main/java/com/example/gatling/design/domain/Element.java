package com.example.gatling.design.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"elementId"})
public class Element {
    private Long elementId;
    private String elementData;

    public Element(Long elementId, String elementData) {
        this.elementId = elementId;
        this.elementData = elementData;
    }
}
