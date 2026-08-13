package org.example.springai.config;

import java.util.regex.Pattern;

public class PiiRedactor {
    private static final Pattern EMAIL = Pattern.compile("[\\w.+-]+@[\\w-]+\\.[\\w.-]+");

    private static final Pattern PHONE = Pattern.compile(
            "\\b0\\d{2}[-.\\s]?\\d{3}[-.\\s]?\\d{3,4}\\b|\\b\\+855\\d{8,9}\\b");

    public static String redact(String input) {
        String redacted = EMAIL.matcher(input).replaceAll("[REDACTED_EMAIL]");
        redacted = PHONE.matcher(redacted).replaceAll("[REDACTED_PHONE]");
        return redacted;
    }
}
