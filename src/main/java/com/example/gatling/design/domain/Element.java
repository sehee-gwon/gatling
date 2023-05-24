package com.example.gatling.design.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Element {
    private UUID elementId;
    private String elementData;

    public Element(UUID elementId) {
        this.elementId = elementId;
    }

    public Element(UUID elementId, String elementData) {
        this.elementId = elementId;
        this.elementData = elementData;
    }
}
