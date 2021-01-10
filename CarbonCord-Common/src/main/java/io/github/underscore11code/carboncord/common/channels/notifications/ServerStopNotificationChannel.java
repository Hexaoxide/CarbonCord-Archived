package io.github.underscore11code.carboncord.common.channels.notifications;

import io.github.underscore11code.carboncord.common.events.ShutdownEvent;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannel;
import io.github.underscore11code.carboncord.api.config.NotificationChannelOptions;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ServerStopNotificationChannel extends NotificationChannel {
  @SuppressWarnings("method.invocation.invalid")
  public ServerStopNotificationChannel(final @NonNull NotificationChannelOptions channelOptions) {
    super(channelOptions);

    CarbonCordEvents.register(ShutdownEvent.class, e -> {
      this.textChannel().sendMessage(this.channelOptions().format()).queue();
    });
  }

  public static void init() {
    NotificationChannel.typeRegistry().register("STOP", ServerStopNotificationChannel.class);
  }
}
