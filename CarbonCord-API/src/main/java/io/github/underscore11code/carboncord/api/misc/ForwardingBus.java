package io.github.underscore11code.carboncord.api.misc;

import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import net.draycia.carbon.api.events.misc.CarbonEvent;
import net.draycia.carbon.api.events.misc.CarbonEvents;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.kyori.event.EventBus;
import net.kyori.event.EventSubscriber;
import net.kyori.event.SimpleEventBus;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Simple event bus that forwards on events from another event bus. Intended for allowing
 * easy reloadability of classes with many instances
 *
 * @param <E> Superclass of Events that will be received
 */
public abstract class ForwardingBus<E> {
  private static final ForwardingBus<CarbonEvent> CARBON = new ForwardingBus<CarbonEvent>(CarbonEvent.class) {
    @SuppressWarnings("method.invocation.invalid")
    private final EventSubscriber<CarbonEvent> subscriber = event -> this.eventBus().post(event);

    @Override
    public void subscribeToSource() {
      CarbonEvents.register(carbon().clazz(), this.subscriber);
    }

    @Override
    public void unsubscribeFromSource() {
      CarbonEvents.unregister(this.subscriber);
    }
  };
  private static final ForwardingBus<CarbonCordEvent> CARBON_CORD = new ForwardingBus<CarbonCordEvent>(CarbonCordEvent.class) {
    @SuppressWarnings("method.invocation.invalid")
    private final EventSubscriber<CarbonCordEvent> subscriber = event -> this.eventBus().post(event);

    @Override
    public void subscribeToSource() {
      CarbonCordEvents.register(carbonCord().clazz(), this.subscriber);
    }

    @Override
    public void unsubscribeFromSource() {
      CarbonCordEvents.unregister(this.subscriber);
    }
  };
  private static final ForwardingBus<GenericEvent> JDA = new JDAForwardingBus();
  private final Class<E> clazz;
  private final EventBus<E> eventBus;
  private boolean registered = false;

  public ForwardingBus(final @NonNull Class<E> clazz) {
    this.clazz = clazz;
    this.eventBus = new SimpleEventBus<>(clazz);
  }

  /**
   * @return ForwardingBus for CarbonChat's API
   */
  public static @NonNull ForwardingBus<CarbonEvent> carbon() {
    return CARBON;
  }

  /**
   * @return ForwardingBus for CarbonCord's API
   */
  public static @NonNull ForwardingBus<CarbonCordEvent> carbonCord() {
    return CARBON_CORD;
  }

  public static ForwardingBus<GenericEvent> JDA() {
    return JDA;
  }

  public EventBus<E> eventBus() {
    return this.eventBus;
  }

  public Class<E> clazz() {
    return this.clazz;
  }

  public <C extends E> void register(final @NonNull Class<C> clazz, final int priority, final boolean consumeCancelled, final @NonNull Consumer<C> consumer) {
    eventBus.register(clazz, new EventSubscriber<C>() {
      @Override
      public int postOrder() {
        return priority;
      }

      @Override
      public boolean consumeCancelledEvents() {
        return consumeCancelled;
      }

      @Override
      public void invoke(final @NonNull C event) {
        consumer.accept(event);
      }
    });
  }

  public void safeSubscribeToSource() {
    if (!this.registered) {
      this.subscribeToSource();
      this.registered = true;
    }
  }

  public void safeUnsubscribeFromSource() {
    if (this.registered) {
      this.unsubscribeFromSource();
      this.registered = false;
    }
  }

  /**
   * Registers this ForwardingBus with the associated source event bus
   */
  public abstract void subscribeToSource();

  /**
   * Unregisters this ForwardingBus with the associated source event bus
   */
  public abstract void unsubscribeFromSource();

  private static final class JDAForwardingBus extends ForwardingBus<GenericEvent> implements EventListener {
    JDAForwardingBus() {
      super(GenericEvent.class);
    }

    @Override
    public void subscribeToSource() {
      CarbonCordProvider.carbonCord().jda().addEventListener(this);
    }

    @Override
    public void unsubscribeFromSource() {
      CarbonCordProvider.carbonCord().jda().removeEventListener(this);
    }

    @Override
    public void onEvent(final @NotNull GenericEvent event) {
      this.eventBus().post(event);
    }
  }
}
