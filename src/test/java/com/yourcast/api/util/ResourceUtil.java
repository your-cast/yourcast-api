package com.yourcast.api.util;

import lombok.NoArgsConstructor;

import org.testcontainers.shaded.com.google.common.io.Resources;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@NoArgsConstructor(access = PRIVATE)
public final class ResourceUtil {

    public static final String AUTH_REGISTER_REQUEST = loadAsString("rest/request/auth-register.json");
    public static final String AUTH_REGISTER_RESPONSE = loadAsString("rest/response/success-register.json");
    public static final String AUTH_LOGIN_REQUEST = loadAsString("rest/request/auth-login.json");

    public static String loadAsString(String path) {
        try {
            return Files.lines(getResources(path))
                    .map(String::trim)
                    .collect(Collectors.joining(""));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getResources(String fileLocation) throws URISyntaxException {
        return Paths.get(Resources.getResource(fileLocation).toURI());
    }
}