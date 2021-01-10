package io.github.underscore11code.carboncord.common.listeners;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import net.draycia.carbon.api.events.PreChatFormatEvent;
import net.kyori.event.PostOrders;

import java.util.Set;

public class CarbonMessageListener {
  private final CarbonCord carbonCord;

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public CarbonMessageListener(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;

    ForwardingBus.carbon().register(PreChatFormatEvent.class, PostOrders.LAST, false, this::onCarbonMessage);
  }

  private void onCarbonMessage(final PreChatFormatEvent e) {
    this.carbonCord.logger().debug("Message {}", e.message());
    // Shadow muted check since the event still goes through
    if (e.user().shadowMuted()) {
      this.carbonCord.logger().debug("Not handling message {} because the user was shadow muted", e.message());
    }

    final Set<DiscordChannel> discordChannels = this.carbonCord.discordChannelRegistry().channelsFor(e.channel());

    for (final DiscordChannel discordChannel : discordChannels) {
      discordChannel.handleFromCarbon(e.user(), e.message());
    }
  }
}
