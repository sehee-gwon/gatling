package com.example.gatling.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IDEPathHelper {

    public static final Path gradleSourcesDirectory;
    public static final Path gradleResourcesDirectory;
    public static final Path gradleBinariesDirectory;
    public static final Path resultsDirectory;
    public static final Path recorderConfigFile;

    static {
        try {
            Path projectRootDir = Paths.get(requireNonNull(IDEPathHelper.class.getResource("/gatling.conf"), "Couldn't locate gatling.conf").toURI()).getParent().getParent().getParent().getParent();
            Path gradleBuildDirectory = projectRootDir.resolve("build");
            Path gradleSrcDirectory = projectRootDir.resolve("src").resolve("main");

            gradleSourcesDirectory = gradleSrcDirectory.resolve("java");
            gradleResourcesDirectory = gradleSrcDirectory.resolve("resources");
            gradleBinariesDirectory = gradleBuildDirectory.resolve("classes").resolve("java").resolve("main");
            resultsDirectory = gradleBuildDirectory.resolve("reports").resolve("gatling");
            recorderConfigFile = gradleResourcesDirectory.resolve("recorder.conf");
        } catch (URISyntaxException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}