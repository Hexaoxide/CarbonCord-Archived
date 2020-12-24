package io.github.underscore11code.carbonchat.common.channels;

import io.github.underscore11code.carbonchat.common.util.PlaceholderUtil;
import io.github.underscore11code.carbonchat.common.util.PrettyUtil;
import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.config.DiscordChannelOptions;
import io.github.underscore11code.carboncord.api.events.DiscordFormatEvent;
import io.github.underscore11code.carboncord.api.events.DiscordPostMessageEvent;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.channels.ChatChannel;
import net.draycia.carbon.api.users.PlayerUser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.luckperms.api.model.group.Group;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class CarbonDiscordChannel implements DiscordChannel {
  private DiscordChannelOptions channelOptions;

  public CarbonDiscordChannel(final DiscordChannelOptions options) throws UnknownChannelException {
    this.channelOptions = options;
  }

  @Override
  public @NotNull TextChannel textChannel() {
    try {
      final TextChannel textChannel = CarbonCordProvider.carbonCord().jda().getTextChannelById(this.channelOptions.discordChannelId());
      if (textChannel == null) {
        throw new UnknownDiscordChannelException(this.channelOptions.discordChannelId());
      }
      return textChannel;

    } catch (final Exception e) {
      throw new UnknownDiscordChannelException(this.channelOptions.discordChannelId(), e);
    }
  }

  @Override
  public @NotNull ChatChannel chatChannel() {
    try {
      final ChatChannel chatChannel = CarbonChatProvider.carbonChat().channelRegistry().get(this.channelOptions.carbonChannelKey());
      if (chatChannel == null) {
        throw new UnknownCarbonChannelException(this.channelOptions.carbonChannelKey());
      }
      return chatChannel;

    } catch (final Exception e) {
      throw new UnknownCarbonChannelException(this.channelOptions.carbonChannelKey(), e);
    }
  }

  @Override
  public void handleFromCarbon(final @NotNull PlayerUser user, final @NotNull String message) {
    // Fire off an event
    final DiscordFormatEvent discordFormatEvent = new DiscordFormatEvent(user, this, this.format(user), message);
    CarbonCordEvents.post(discordFormatEvent);
    if (discordFormatEvent.cancelled() || discordFormatEvent.message().equals("")) {
      CarbonCordProvider.carbonCord().logger().debug("Not handling message {} because the IngamePreFormatEvent was cancelled.", message);
      return;
    }

    final String finalMessage = PrettyUtil.stripColorCodes(CarbonCordProvider.carbonCord().setPlatformPlaceholders(
      PlaceholderUtil.setPlaceholders(discordFormatEvent.format(),
        "nickname", user.nickname(),
        "displayname", user.displayName(),
        "username", user.name(),
        "phase", Long.toString(System.currentTimeMillis() % 25),
        "message", discordFormatEvent.message()),
      user));

    this.textChannel().sendMessage(finalMessage).queue(discordMessage -> {
      CarbonCordEvents.post(new DiscordPostMessageEvent(user, discordMessage, this, finalMessage, discordFormatEvent.format()));
    });
  }

  @Override
  public @NotNull DiscordChannelOptions channelOptions() {
    return this.channelOptions;
  }

  @Override
  public void channelOptions(final @NotNull DiscordChannelOptions channelOptions) {
    this.channelOptions = channelOptions;
  }

  @Override
  public @NonNull String format(final @NonNull PlayerUser playerUser) {
    for (final Group group : playerUser.groups()) {
      final String groupFormat = this.channelOptions().formats().get(group.getFriendlyName());
      if (groupFormat != null) {
        return groupFormat;
      }
    }
    final String defaultFormat = this.channelOptions().formats().get(this.channelOptions().defaultFormatName());
    if (defaultFormat != null) {
      return defaultFormat;
    }
    throw new IllegalStateException("No format could be found for user");
  }

  @Override
  public @NotNull String format(final @NonNull Member member) {
    return /*todo*/"";
  }

  public static class UnknownChannelException extends IllegalArgumentException {
    private final String channelName;
    private final String channelType;

    @SuppressWarnings("assignment.type.incompatible")
    public UnknownChannelException(final String channelName, final String channelType) {
      this.channelName = channelName;
      this.channelType = channelType;
    }

    public UnknownChannelException(final String channelName, final String channelType, final Throwable cause) {
      this.channelName = channelName;
      this.channelType = channelType;
      this.initCause(cause);
    }

    @Override
    public String getMessage() {
      return "Unknown " + this.channelType + " channel: " + this.channelName;
    }

    public String channelName() {
      return this.channelName;
    }

    public String channelType() {
      return this.channelType;
    }
  }

  public static class UnknownDiscordChannelException extends UnknownChannelException {
    public UnknownDiscordChannelException(final String channelName) {
      super(channelName, "Discord");
    }

    public UnknownDiscordChannelException(final String channelName, final Throwable cause) {
      super(channelName, "Discord", cause);
    }
  }

  public static class UnknownCarbonChannelException extends UnknownChannelException {
    public UnknownCarbonChannelException(final String channelName) {
      super(channelName, "Carbon");
    }

    public UnknownCarbonChannelException(final String channelName, final Throwable cause) {
      super(channelName, "Carbon", cause);
    }
  }
}
