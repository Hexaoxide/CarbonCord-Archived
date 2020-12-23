package io.github.underscore11code.carbonchat.common.channels;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.channels.DiscordChannelRegistry;
import io.github.underscore11code.carboncord.api.config.DiscordChannelOptions;
import io.github.underscore11code.carboncord.api.events.DiscordChannelRegisterEvent;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

public class ChannelManager {
  private final CarbonCord carbonCord;
  private final DiscordChannelRegistry discordChannelRegistry;

  @SuppressWarnings("method.invocation.invalid")
  public ChannelManager(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;
    this.discordChannelRegistry = new DiscordChannelRegistry();

    this.carbonCord.logger().info("Loading channels!");

    this.loadChannels();
  }

  // --- API ---

  public DiscordChannelRegistry registry() {
    return this.discordChannelRegistry;
  }

  @SuppressWarnings("argument.type.incompatible")
  public @Nullable DiscordChannel loadChannel(final @NonNull DiscordChannelOptions settings) {
    final CarbonDiscordChannel channel;
    try {
      channel = new CarbonDiscordChannel(settings);
    } catch (final CarbonDiscordChannel.UnknownChannelException e) {
      if (e.getCause() != null) {
        this.carbonCord.logger().error(e.getMessage(), e.getCause());
      } else {
        this.carbonCord.logger().error(e.getMessage());
      }
      return null;
    }
    return channel;
  }

  public void registerChannel(final @NonNull DiscordChannel discordChannel) {
    this.registry().register(discordChannel.channelOptions().key(), discordChannel);

    CarbonCordEvents.post(new DiscordChannelRegisterEvent(discordChannel));
  }

  public void reloadChannels() {
    for (final DiscordChannelOptions options : this.carbonCord.carbonCordSettings().discordChannelSettings().values()) {
      final DiscordChannel discordChannel = this.registry().get(options.key());
      if (discordChannel != null) {
        discordChannel.channelOptions(options);
      }
    }
  }

  // --- Private ---

  private void loadChannels() {
    for (final Map.Entry<String, DiscordChannelOptions> options :
      this.carbonCord.carbonCordSettings().discordChannelSettings().entrySet()) {
      final DiscordChannel channel = this.loadChannel(options.getValue());

      if (channel != null) {
        this.registerChannel(channel);

        this.carbonCord.logger().info("Registering channel: " + channel.channelOptions().key());
      }
    }
  }
}
