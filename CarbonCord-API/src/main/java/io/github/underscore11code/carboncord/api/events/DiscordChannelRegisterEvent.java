package io.github.underscore11code.carboncord.api.events;

import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;

public class DiscordChannelRegisterEvent implements CarbonCordEvent {
  private final DiscordChannel discordChannel;

  public DiscordChannelRegisterEvent(final DiscordChannel discordChannel) {
    this.discordChannel = discordChannel;
  }

  public DiscordChannel discordChannel() {
    return this.discordChannel;
  }
}
