package io.github.underscore11code.carboncord.api.events.misc;

import net.kyori.event.EventSubscriber;
import net.kyori.event.PostResult;
import net.kyori.event.SimpleEventBus;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Consumer;

public final class CarbonCordEvents {
  private CarbonCordEvents() {
  }

  private static final SimpleEventBus<CarbonCordEvent> EVENT_BUS = new SimpleEventBus<>(CarbonCordEvent.class);

  public static <T extends CarbonCordEvent> void register(final @NonNull Class<T> clazz, final @NonNull EventSubscriber<T> subscriber) {
    EVENT_BUS.register(clazz, subscriber);
  }

  public static <T extends CarbonCordEvent> void register(final @NonNull Class<T> clazz, final int priority, final boolean consumeCancelled, final @NonNull Consumer<T> consumer) {
    EVENT_BUS.register(clazz, new EventSubscriber<T>() {
      @Override
      public int postOrder() {
        return priority;
      }

      @Override
      public boolean consumeCancelledEvents() {
        return consumeCancelled;
      }

      @Override
      public void invoke(final @NonNull T event) {
        consumer.accept(event);
      }
    });
  }

  public static void unregister(final @NonNull EventSubscriber<? super CarbonCordEvent> subscriber) {
    EVENT_BUS.unregister(subscriber);
  }

  public static PostResult post(final @NonNull CarbonCordEvent event) {
    return EVENT_BUS.post(event);
  }

}
