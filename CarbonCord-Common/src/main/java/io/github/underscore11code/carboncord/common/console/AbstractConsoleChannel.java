package io.github.underscore11code.carboncord.common.console;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.console.ConsoleChannel;
import io.github.underscore11code.carboncord.api.channels.console.ConsoleLine;
import io.github.underscore11code.carboncord.api.config.ConsoleChannelOptions;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.kyori.event.PostOrders;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractConsoleChannel extends Thread implements ConsoleChannel {
  private final CarbonCord carbonCord;
  private final BlockingQueue<ConsoleLine> lineQueue = new LinkedBlockingQueue<>();
  private boolean run = true;
  private static final long MAX_MESSAGE_LENGTH = 2000;

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public AbstractConsoleChannel(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;

    ForwardingBus.JDA().register(GuildMessageReceivedEvent.class, PostOrders.NORMAL, false, this::onMessage);
  }

  @SuppressWarnings("dereference.of.nullable")
  public void run() {
    long lastMessageMs = System.currentTimeMillis();
    while (this.run) {
      while (this.lineQueue.size() == 0 || System.currentTimeMillis() < lastMessageMs + 5000) {
        if (!this.run) return;
      }

      final StringBuilder sb = new StringBuilder();
      while (this.lineQueue.size() != 0 &&
        sb.length() < MAX_MESSAGE_LENGTH &&
        sb.length() + this.lineQueue.peek().beautify(this.options().format()).length() < MAX_MESSAGE_LENGTH) {
        sb.append(this.lineQueue.remove().beautify(this.options().format()));
      }

      final TextChannel channel = this.carbonCord.jda().getTextChannelById(this.options().channelId());
      if (channel != null) {
        channel.sendMessage(sb).queue();
        lastMessageMs = System.currentTimeMillis();
      }
    }
  }

  private void onMessage(final @NonNull GuildMessageReceivedEvent event) {
    if (!event.getChannel().getId().equals(this.options().channelId()) || event.getAuthor().isBot()) return;
    this.runConsoleCommand(event.getMessage().getContentRaw());
  }

  @Override
  public BlockingQueue<ConsoleLine> lineQueue() {
    return this.lineQueue;
  }

  @Override
  public void shutdown() {
    this.run = false;
  }

  public abstract void runConsoleCommand(final @NonNull String command);

  private ConsoleChannelOptions options() {
    return this.carbonCord.carbonCordSettings().consoleChannelOptions();
  }
}
