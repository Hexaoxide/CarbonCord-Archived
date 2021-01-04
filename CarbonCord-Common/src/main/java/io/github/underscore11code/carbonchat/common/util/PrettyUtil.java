package io.github.underscore11code.carbonchat.common.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.regex.Pattern;

public final class PrettyUtil {
  // Legacy &4-style codes
  private static final Pattern COLOR_CODE_REGEX =
    Pattern.compile("[§&][abcdef1234567890lmnokr]", Pattern.CASE_INSENSITIVE);
  // The pinnacle of modern design, the Bungeecord RGB syntax: &x&F&F&F&F&F&F
  private static final Pattern BUNGEE_RGB_COLOR_CODE_REGEX =
    Pattern.compile("([§&])x(?:\1[abcdef1234567890]){6}", Pattern.CASE_INSENSITIVE);
  // Sarcasm aside, the *actual* pinnacle of modern design, the Kyori &#FFFFFF
  private static final Pattern KYORI_RGB_COLOR_CODE_REGEX =
    Pattern.compile("[§&]#[abcdef1234567890lmnokr]{6}", Pattern.CASE_INSENSITIVE);

  private PrettyUtil() {

  }

  public static @NonNull String stripColorCodes(final @NonNull String original) {
    String temp = original;

    temp = COLOR_CODE_REGEX.matcher(temp).replaceAll("");
    temp = BUNGEE_RGB_COLOR_CODE_REGEX.matcher(temp).replaceAll("");
    temp = KYORI_RGB_COLOR_CODE_REGEX.matcher(temp).replaceAll("");

    return temp;
  }
}
