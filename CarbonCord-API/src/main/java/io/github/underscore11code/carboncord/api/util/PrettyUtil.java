package io.github.underscore11code.carboncord.api.util;

import com.google.common.collect.ImmutableMap;
import io.github.underscore11code.carboncord.api.CarbonCord;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.util.RGBLike;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;

import java.awt.Color;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
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

  private static final Map<TextDecoration, TextDecoration.State> blankDecoration;

  static {
    final HashMap<TextDecoration, TextDecoration.State> decorations = new HashMap<>();
    for (final TextDecoration td : EnumSet.allOf(TextDecoration.class)) {
      decorations.put(td, TextDecoration.State.FALSE);
    }
    blankDecoration = ImmutableMap.copyOf(decorations);
  }

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

  public static RGBLike colorToRgbLike(final Color color) {
    return new RGBLike() {
      @Override
      public @IntRange(from = 0L, to = 255L) int red() {
        return color.getRed();
      }

      @Override
      public @IntRange(from = 0L, to = 255L) int green() {
        return color.getGreen();
      }

      @Override
      public @IntRange(from = 0L, to = 255L) int blue() {
        return color.getBlue();
      }
    };
  }

  public static Component prefixed(final Component component) {
    return appendBlankFormatting(CarbonCord.prefix(), component);
  }

  public static Component appendBlankFormatting(final Component c1, final Component c2) {
    return c1.append(Component.empty().color(NamedTextColor.WHITE).decorations(blankDecoration).append(c2));
  }
}
