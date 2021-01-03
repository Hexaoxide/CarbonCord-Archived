package io.github.underscore11code.carbonchat.common.util;

import org.checkerframework.checker.nullness.qual.NonNull;

@SuppressWarnings("MethodName") // not sure what this is on about, stfu
public final class PlaceholderUtil {
  private PlaceholderUtil() {

  }

  public static String setPlaceholders(final @NonNull String message, final @NonNull String... placeholders) {
    if (placeholders.length % 2 != 0) { // Odd number of arguments
      throw new IllegalArgumentException("Odd number of arguments for \"placeholders\"");
    }
    String temp = message;
    for (int i = 0; i < placeholders.length; i += 2) {
      temp = temp.replace("<" + placeholders[i] + ">", placeholders[i + 1]);
    }

    return temp;
  }
}
