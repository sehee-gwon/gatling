package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.infrastructure.utils.SheetXmlUtils;
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

    public DesignActionRequest(int designIdx, ActionType actionType, List<Sheet> sheets) {
        this.designIdx = String.format(SheetXmlUtils.DESIGN_ID_FORMAT, designIdx);
        this.actionType = actionType;
        this.sheets = sheets;
    }
}
