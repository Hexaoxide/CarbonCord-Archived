package io.github.underscore11code.carbonchat.common.channels;

import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.config.DiscordChannelOptions;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.channels.ChatChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class CarbonDiscordChannel implements DiscordChannel {
  private DiscordChannelOptions channelOptions;

  public CarbonDiscordChannel(final DiscordChannelOptions options) throws UnknownChannelException {
    this.channelOptions = options;
  }

  @Override
  public TextChannel textChannel() {
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
  public ChatChannel chatChannel() {
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
  public DiscordChannelOptions channelOptions() {
    return this.channelOptions;
  }

  @Override
  public void channelOptions(final DiscordChannelOptions channelOptions) {
    this.channelOptions = channelOptions;
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
