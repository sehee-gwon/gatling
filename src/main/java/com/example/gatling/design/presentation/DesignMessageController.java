package com.example.gatling.design.presentation;

import com.example.gatling.design.domain.Element;
import com.example.gatling.design.domain.Sheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DesignMessageController {
    @MessageMapping("/designId")
    public void action(DesignRequest request) {
        log.info("---------------------------------------------------------------------------");
        log.info("designIdx: {}, actionType: {}", request.getDesignIdx(), request.getActionType());

        log.info("  Sheets:");
        for (Sheet sheet : request.getSheets()) {
            log.info("  ㄴ sheetKey: {}", sheet.getSheetKey());

            if (!CollectionUtils.isEmpty(sheet.getElements())) {
                log.info("    Elements");

                for (Element element : sheet.getElements()) {
                    if (StringUtils.hasText(element.getData())) {
                        log.info("    ㄴ id: {}, data: {}", element.getId(), new String(Base64.decodeBase64(element.getData())));
                    } else {
                        log.info("    ㄴ id: {}", element.getId());
                    }
                }
            }
        }
    }
}
