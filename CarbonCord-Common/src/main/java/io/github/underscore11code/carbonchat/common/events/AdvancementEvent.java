package io.github.underscore11code.carbonchat.common.events;

import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;
import net.draycia.carbon.api.users.PlayerUser;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Only intended for use for NotificationChannels
 */
public class AdvancementEvent implements CarbonCordEvent {
  private final PlayerUser user;
  private final String advancementName;

  public AdvancementEvent(final @NonNull PlayerUser user, final @NonNull String advancementName) {
    this.user = user;
    this.advancementName = advancementName;
  }

  public @NonNull PlayerUser user() {
    return this.user;
  }

  public @NonNull String advancementName() {
    return this.advancementName;
  }
}
