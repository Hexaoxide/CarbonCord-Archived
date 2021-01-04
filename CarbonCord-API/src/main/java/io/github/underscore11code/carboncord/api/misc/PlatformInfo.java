package io.github.underscore11code.carboncord.api.misc;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

public interface PlatformInfo {
  @NonNull String serverVersion();

  @NonNull String serverBrand();

  @NonNull String minecraftVersion();

  @NonNull Platform platform();

  @NonNull Map<String, String> otherInfo();

  enum Platform {
    BUKKIT,
  }
}
