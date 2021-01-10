package io.github.underscore11code.carboncord.api.channels.console;

import java.util.concurrent.BlockingQueue;

public interface ConsoleChannel {
  void start();

  void shutdown();

  BlockingQueue<ConsoleLine> lineQueue();
}
