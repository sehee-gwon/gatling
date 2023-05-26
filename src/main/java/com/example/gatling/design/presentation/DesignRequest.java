package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.DesignMetaData;
import com.example.gatling.design.domain.enumeration.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.domain.enumeration.Target;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class DesignRequest {
    private String designIdx;
    private ActionType actionType;
    private Target target;
    private List<Sheet> sheets;
    private DesignMetaData designMetaData;

    @Builder
    public DesignRequest(String designIdx, Target target,
                         ActionType actionType, List<Sheet> sheets,
                         DesignMetaData designMetaData) {
        this.designIdx = designIdx;
        this.actionType = actionType;
        this.target = target;
        this.sheets = sheets;
        this.designMetaData = designMetaData;
    }
}
