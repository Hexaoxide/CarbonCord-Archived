package io.github.underscore11code.carboncord.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class CarbonCordProvider {

  private CarbonCordProvider() {

  }

  private static @Nullable CarbonCord instance;

  public static void register(final @NonNull CarbonCord CarbonCord) {
    CarbonCordProvider.instance = CarbonCord;
  }

  public static @NonNull CarbonCord carbonCord() {
    if (CarbonCordProvider.instance == null) {
      throw new IllegalStateException("CarbonCord not initialized!"); // LuckPerms design go brrrr
    }

    return CarbonCordProvider.instance;
  }
  
}
