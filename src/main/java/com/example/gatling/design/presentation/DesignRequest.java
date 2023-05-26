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
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
public class DesignRequest {
    private UUID designIdx;
    private ActionType actionType;
    private Target target;
    private List<Sheet> sheets;
    private DesignMetaData designMetaData;

    @Builder
    public DesignRequest(UUID designIdx, Target target,
                         ActionType actionType, List<Sheet> sheets,
                         DesignMetaData designMetaData) {
        this.designIdx = designIdx;
        this.actionType = actionType;
        this.target = target;
        this.sheets = sheets;
        this.designMetaData = designMetaData;
    }
}
