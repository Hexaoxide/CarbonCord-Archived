package io.github.underscore11code.carboncord.common.channels.notifications;

import io.github.underscore11code.carboncord.api.util.PlaceholderUtil;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannel;
import io.github.underscore11code.carboncord.api.config.NotificationChannelOptions;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import net.draycia.carbon.api.events.UserEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlayerJoinNotificationChannel extends NotificationChannel {
  @SuppressWarnings("method.invocation.invalid")
  public PlayerJoinNotificationChannel(final @NonNull NotificationChannelOptions channelOptions) {
    super(channelOptions);

    ForwardingBus.carbon().eventBus().register(UserEvent.Join.class, e -> {
      this.textChannel().sendMessage(
        PlaceholderUtil.setPlaceholders(this.channelOptions().format(),
          "username", e.user().name(),
          "displayname", e.user().displayName(),
          "nickname", e.user().nickname()
        )
      ).queue();
    });
  }

  protected static void init() {
    NotificationChannel.typeRegistry().register("JOIN", PlayerJoinNotificationChannel.class);
  }

}
