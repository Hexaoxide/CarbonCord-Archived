package io.github.underscore11code.carbonchat.common.channels.notifications;

import io.github.underscore11code.carbonchat.common.events.StartupEvent;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannel;
import io.github.underscore11code.carboncord.api.config.NotificationChannelOptions;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ServerStartNotificationChannel extends NotificationChannel {
  @SuppressWarnings("method.invocation.invalid")
  public ServerStartNotificationChannel(final @NonNull NotificationChannelOptions channelOptions) {
    super(channelOptions);

    CarbonCordEvents.register(StartupEvent.class, e -> {
      this.textChannel().sendMessage(this.channelOptions().format()).queue();
    });
  }

  public static void init() {
    NotificationChannel.typeRegistry().register("START", ServerStartNotificationChannel.class);
  }
}
