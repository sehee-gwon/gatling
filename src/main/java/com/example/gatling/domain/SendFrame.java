package com.example.gatling.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.util.StringUtils;

@NoArgsConstructor
public class SendFrame implements StompFrame {
    public static final StompCommand COMMAND = StompCommand.SEND;

    private String destination;
    private MediaType contentType;
    private Integer contentLength;
    private String body;

    @Builder
    public SendFrame(String destination, MediaType contentType, Integer contentLength, String body) {
        this.destination = destination;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.body = body;
    }

    @Override
    public String toFrame() {
        this.validator();

        StringBuilder sb = new StringBuilder();
        sb.append(COMMAND.name());
        sb.append("\n").append("destination:").append(this.destination);

        if (this.contentType != null) sb.append("\n").append("content-type:").append(this.contentType);
        if (this.contentLength != null) sb.append("\n").append("content-length:").append(this.contentLength);

        sb.append("\n\n");

        if (StringUtils.hasText(this.body)) sb.append(this.body);

        sb.append("\u0000");
        return sb.toString();
    }

    @Override
    public void validator() {
        if (!StringUtils.hasText(this.destination)) {
            throw new IllegalArgumentException(COMMAND.name() + ": destination value is required");
        }
    }
}
