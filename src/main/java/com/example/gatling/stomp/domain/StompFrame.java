package com.example.gatling.stomp.domain;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.util.StringUtils;

public abstract class StompFrame {
    protected StompCommand commend;
    protected String body;

    public String make() {
        this.validator();

        StringBuilder sb = new StringBuilder();
        sb.append(commend);
        this.setHeader(sb);

        sb.append("\n\n");
        if (StringUtils.hasText(this.body)) sb.append(this.body);

        sb.append("\u0000");
        return sb.toString();
    }

    protected abstract void setHeader(StringBuilder sb);
    protected abstract void validator();
}