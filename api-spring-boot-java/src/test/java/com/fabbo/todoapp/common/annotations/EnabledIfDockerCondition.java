package com.fabbo.todoapp.common.annotations;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;

public class EnabledIfDockerCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (isDockerInstalled()) {
            return ConditionEvaluationResult.enabled("Docker installed");
        } else if (isDockerInstalledWsl()) {
            return ConditionEvaluationResult.enabled("Docker installed using wsl");
        }
        return ConditionEvaluationResult.disabled("Docker not installed or not in the PATH variable");
    }

    public static boolean isDockerInstalledWsl() {
        try {
            final Process process = Runtime
                    .getRuntime()
                    .exec(new String[]{"wsl", "docker", "--version"});
            final int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public static boolean isDockerInstalled() {
        try {
            final Process process = Runtime
                    .getRuntime()
                    .exec(new String[]{"docker", "--version"});
            final int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
