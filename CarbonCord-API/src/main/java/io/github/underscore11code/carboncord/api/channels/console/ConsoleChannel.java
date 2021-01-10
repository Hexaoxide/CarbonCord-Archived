package io.github.underscore11code.carboncord.api.channels.console;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.kyori.event.PostOrders;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleChannel extends Thread {
  private final CarbonCord carbonCord;
  private final BlockingQueue<ConsoleLine> lineQueue = new LinkedBlockingQueue<>();
  private boolean run = true;
  private static final long MAX_MESSAGE_LENGTH = 2000;

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public ConsoleChannel(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;

    ForwardingBus.JDA().register(GuildMessageReceivedEvent.class, PostOrders.NORMAL, false, this::onMessage);
  }

  @Override
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
        sb.length() + this.lineQueue.peek().beautify().length() < MAX_MESSAGE_LENGTH) {
        sb.append(this.lineQueue.remove().beautify());
      }

      final TextChannel channel = this.carbonCord.jda().getTextChannelById(796921636720869396L);
      if (channel != null) {
        channel.sendMessage(sb).queue();
        lastMessageMs = System.currentTimeMillis();
      }
    }
  }

  private void onMessage(final @NonNull GuildMessageReceivedEvent event) {
    if (event.getChannel().getIdLong() != 796921636720869396L || event.getAuthor().isBot()) return;
    this.carbonCord.runConsoleCommand(event.getMessage().getContentRaw());
  }

  public BlockingQueue<ConsoleLine> lineQueue() {
    return this.lineQueue;
  }
}
