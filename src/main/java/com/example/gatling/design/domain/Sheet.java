package com.example.gatling.design.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Sheet {
    private String key;
    private List<Element> elementList;
}
