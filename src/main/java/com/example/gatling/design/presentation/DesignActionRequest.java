package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.Action;
import com.example.gatling.design.domain.Sheet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DesignActionRequest {
    private Action action;
    private Sheet sheet;
}
