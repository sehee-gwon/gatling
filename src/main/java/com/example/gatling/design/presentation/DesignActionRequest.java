package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class DesignActionRequest {
    private String designIdx;
    private ActionType actionType;
    private List<Sheet> sheets;

    public DesignActionRequest(String designIdx, ActionType actionType, List<Sheet> sheets) {
        this.designIdx = designIdx;
        this.actionType = actionType;
        this.sheets = sheets;
    }
}
