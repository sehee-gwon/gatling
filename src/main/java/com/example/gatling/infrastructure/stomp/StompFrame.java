package com.example.gatling.infrastructure.stomp;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.util.StringUtils;

public abstract class StompFrame {
    protected StompCommand commend;
    protected String body;

    public String createPayload() {
        this.validator();

        StringBuilder sb = new StringBuilder();
        sb.append(this.commend);
        this.setHeader(sb);

        sb.append("\n\n");
        if (StringUtils.hasText(this.body)) sb.append(this.body);

        sb.append("\u0000");
        return sb.toString();
    }

    protected abstract void setHeader(StringBuilder sb);
    protected abstract void validator();
}