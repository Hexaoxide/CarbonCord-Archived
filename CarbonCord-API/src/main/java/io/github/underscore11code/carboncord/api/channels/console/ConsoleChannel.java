package io.github.underscore11code.carboncord.api.channels.console;

import io.github.underscore11code.carboncord.api.CarbonCord;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleChannel extends Thread {
  private final CarbonCord carbonCord;
  private final BlockingQueue<ConsoleLine> lineQueue = new LinkedBlockingQueue<>();
  private boolean run = true;
  private static final long MAX_MESSAGE_LENGTH = 2000;

  public ConsoleChannel(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;
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

  public BlockingQueue<ConsoleLine> lineQueue() {
    return this.lineQueue;
  }
}
