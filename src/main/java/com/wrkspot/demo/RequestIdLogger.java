package com.wrkspot.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RequestIdLogger {

    private static String logFilePath;

    @Value("${request.log.file.path}")
    public void setLogFilePath(String logFilePath) {
        RequestIdLogger.logFilePath = logFilePath;
    }

    public static void logRequestId(String requestId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(LocalDateTime.now() + " - Request ID: " + requestId);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}