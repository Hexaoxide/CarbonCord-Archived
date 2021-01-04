package io.github.underscore11code.carbonchat.common.listeners;

import io.github.underscore11code.carboncord.api.CarbonCord;

public final class ListenerRegistrar {
  private ListenerRegistrar() {

  }

  public static void registerHandlers(final CarbonCord carbonCord) {
    new CarbonMessageListener(carbonCord);
    new DiscordMessageListener(carbonCord);
  }
}
