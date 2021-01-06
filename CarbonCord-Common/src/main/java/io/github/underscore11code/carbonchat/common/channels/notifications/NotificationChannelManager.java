package io.github.underscore11code.carbonchat.common.channels.notifications;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannel;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannelRegistry;
import io.github.underscore11code.carboncord.api.config.NotificationChannelOptions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

public class NotificationChannelManager {
  private final CarbonCord carbonCord;
  private final NotificationChannelRegistry notificationChannelRegistry;

  @SuppressWarnings("method.invocation.invalid")
  public NotificationChannelManager(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;
    this.notificationChannelRegistry = new NotificationChannelRegistry();

    this.carbonCord.logger().info("Loading notification channels!");

    // Register provided notification channels
    PlayerJoinNotificationChannel.init();
    PlayerLeaveNotificationChannel.init();
    ServerStartNotificationChannel.init();
    ServerStopNotificationChannel.init();
    AdvancementNotificationChannel.init();

    this.loadChannels();
  }

  // --- API ---

  public NotificationChannelRegistry registry() {
    return this.notificationChannelRegistry;
  }

  @SuppressWarnings("argument.type.incompatible")
  public @Nullable NotificationChannel loadChannel(final @NonNull NotificationChannelOptions channelOptions) {
    NotificationChannel channel = null;

    try {
      channel = NotificationChannel.of(channelOptions);
    } catch (final Exception e) {
      this.carbonCord.logger().error("Error loading a notification channel: ", e);
    }
    return channel;
  }

  public void registerChannel(final @NonNull NotificationChannel notificationChannel) {
    this.registry().register(notificationChannel.channelOptions().key(), notificationChannel);

    // TODO event
  }

  public void reloadChannels() {
    for (final NotificationChannelOptions options : this.carbonCord.carbonCordSettings().notificationChannelSettings().values()) {
      final NotificationChannel notificationChannel = this.registry().get(options.key());
      if (notificationChannel != null) {
        notificationChannel.channelOptions(options);
      }
    }
  }

  // --- Private ---

  private void loadChannels() {
    for (final Map.Entry<String, NotificationChannelOptions> options :
      this.carbonCord.carbonCordSettings().notificationChannelSettings().entrySet()) {
      final NotificationChannel channel = this.loadChannel(options.getValue());

      if (channel != null) {
        this.registerChannel(channel);

        this.carbonCord.logger().info("Registering channel: " + channel.channelOptions().key());
      }
    }
  }
}
