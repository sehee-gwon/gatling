package com.example.gatling.design.domain;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Element {
    private String id;
    private String data;

    public Element(String id) {
        this.id = id;
    }

    public Element(String id, String data) {
        this.id = id;
        this.data = data;
    }
}
