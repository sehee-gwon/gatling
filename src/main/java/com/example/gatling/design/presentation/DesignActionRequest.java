package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.Action;
import com.example.gatling.design.domain.Sheet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class DesignActionRequest {
    private Action action;
    private List<Sheet> sheet;

    public DesignActionRequest(Action action, List<Sheet> sheet) {
        this.action = action;
        this.sheet = sheet;
    }
}
