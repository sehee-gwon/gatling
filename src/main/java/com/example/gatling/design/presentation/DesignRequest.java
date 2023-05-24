package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.domain.Target;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
public class DesignRequest {
    private UUID designIdx;
    private ActionType actionType;
    private Target target;
    private List<Sheet> sheets;

    public DesignRequest(UUID designIdx, Target target, ActionType actionType, List<Sheet> sheets) {
        this.designIdx = designIdx;
        this.actionType = actionType;
        this.target = target;
        this.sheets = sheets;
    }
}
