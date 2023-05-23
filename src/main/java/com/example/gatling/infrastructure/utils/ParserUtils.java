package com.example.gatling.infrastructure.utils;

import com.example.gatling.infrastructure.exception.SimulationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParserUtils {
    public static String toJsonString(Object object)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SimulationException("JSON parsing error (by objectMapper)", e);
        }
    }
}
