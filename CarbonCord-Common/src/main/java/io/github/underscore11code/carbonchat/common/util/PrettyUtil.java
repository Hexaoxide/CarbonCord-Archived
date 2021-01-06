package io.github.underscore11code.carbonchat.common.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

  /**
   * Converts from "THIS_FORMAT" to "This Format"
   * @param original Enum-style name
   * @return Pretty "human-friendly" name
   */
  // https://github.com/DiscordSRV/DiscordSRV/blob/8276d7905ab0cbca41b049b1863002d7d4a35c38/src/main/java/github/scarsz/discordsrv/util/PrettyUtil.java#L84-L90
  public static @NonNull String prettyEnum(final @NonNull String original) {
    return Arrays.stream(original.toLowerCase().split("_"))
      .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
      .collect(Collectors.joining(" "));
  }
}
