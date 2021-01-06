package io.github.underscore11code.carbonchat.common.channels.notifications;

import io.github.underscore11code.carbonchat.common.events.AdvancementEvent;
import io.github.underscore11code.carbonchat.common.util.PlaceholderUtil;
import io.github.underscore11code.carbonchat.common.util.PrettyUtil;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannel;
import io.github.underscore11code.carboncord.api.config.NotificationChannelOptions;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import org.checkerframework.checker.nullness.qual.NonNull;

public class AdvancementNotificationChannel extends NotificationChannel {
  @SuppressWarnings("method.invocation.invalid")
  public AdvancementNotificationChannel(final @NonNull NotificationChannelOptions channelOptions) {
    super(channelOptions);

    ForwardingBus.carbonCord().eventBus().register(AdvancementEvent.class, e -> {
      if (e.advancementName().startsWith("recipes/")) return;

      this.textChannel().sendMessage(
        PlaceholderUtil.setPlaceholders(this.channelOptions().format(),
          "username", e.user().name(),
          "displayname", e.user().displayName(),
          "nickname", e.user().nickname(),
          "advancement", PrettyUtil.prettyEnum(e.advancementName().substring(e.advancementName().lastIndexOf("/") + 1))
        )
      ).queue();
    });
  }

  public static void init() {
    NotificationChannel.typeRegistry().register("ADVANCEMENT", AdvancementNotificationChannel.class);
  }
}
