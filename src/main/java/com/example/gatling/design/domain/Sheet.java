package com.example.gatling.design.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"key"})
public class Sheet {
    private UUID key;
    private List<Element> elementList;

    public Sheet(UUID key, List<Element> elementList) {
        this.key = key;
        this.elementList = elementList;
    }
}
