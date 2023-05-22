package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DesignMessageController {
    @MessageMapping("/designId")
    public void action(DesignActionRequest request) {
        log.info("designIdx: {}, actionType: {}", request.getDesignIdx(), request.getActionType());
        for (Sheet sheet : request.getSheets()) {
            log.info("  ㄴ sheetKey: {}", sheet.getSheetKey());
            for (Element element : sheet.getElements()) {
                log.info("    ㄴ id: {}, data: {}", element.getId(), element.getData());
            }
        }
    }
}
