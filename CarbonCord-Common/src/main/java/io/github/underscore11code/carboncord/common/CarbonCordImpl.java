package io.github.underscore11code.carboncord.common;

import cloud.commandframework.CommandManager;
import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.DiscordChannelRegistry;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannelRegistry;
import io.github.underscore11code.carboncord.api.config.CarbonCordSettings;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import io.github.underscore11code.carboncord.api.misc.PlatformInfo;
import io.github.underscore11code.carboncord.common.channels.DiscordChannelManager;
import io.github.underscore11code.carboncord.common.channels.notifications.NotificationChannelManager;
import io.github.underscore11code.carboncord.common.commands.CommandRegistrar;
import io.github.underscore11code.carboncord.common.config.ConfigLoader;
import io.github.underscore11code.carboncord.common.events.ShutdownEvent;
import io.github.underscore11code.carboncord.common.events.StartupEvent;
import io.github.underscore11code.carboncord.common.listeners.ListenerRegistrar;
import net.draycia.carbon.api.users.CarbonUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;

public class CarbonCordImpl implements CarbonCord {

  private final CarbonCordBootstrap bootstrap;
  private final Logger logger = LoggerFactory.getLogger("CarbonCord");
  private CommandManager<CarbonUser> commandManager;
  private JDA jda;
  private CarbonCordSettings carbonCordSettings;
  private DiscordChannelManager discordChannelManager;
  private NotificationChannelManager notificationChannelManager;

  @SuppressWarnings({"initialization.fields.uninitialized", "argument.type.incompatible"})
  public CarbonCordImpl(final CarbonCordBootstrap bootstrap) {
    this.bootstrap = bootstrap;
  }

  public void enable() {
    this.loadConfigs();

    final String botToken = this.carbonCordSettings().botToken();
    if (botToken.equals("")) { // Default
      this.logger.error("FATAL: bot-token is not set! CarbonCord **CANNOT** work without a Bot Token!");
      this.logger.error("Not sure what a Bot Token is? See the installation instructions at https://github.com/Hexaoxide/CarbonCord");
      this.bootstrap.disable();
      return;
    }

    try {
      this.jda = JDABuilder.createDefault(botToken)
        .addEventListeners()
        .build().awaitReady();
    } catch (final LoginException e) {
      this.logger.error("FATAL: Could not log into Discord with the given Bot Token!");
      this.logger.error("Ensure the Bot Token is correct!");
      this.bootstrap.disable();
      return;
    } catch (final InterruptedException e) {
      this.logger.error("FATAL: JDA interrupted while logging in!");
      this.logger.error("This *shouldn't* be possible...");
      this.logger.error("", e);
      this.bootstrap.disable();
      return;
    }

    ForwardingBus.carbon().safeSubscribeToSource();
    ForwardingBus.carbonCord().safeSubscribeToSource();
    ForwardingBus.JDA().safeSubscribeToSource();

    this.discordChannelManager = new DiscordChannelManager(this);
    this.notificationChannelManager = new NotificationChannelManager(this);

    try {
      this.commandManager = this.bootstrap.commandManager();
    } catch (final Exception e) {
      this.logger.error("Could not init command manager!", e);
      this.bootstrap.disable();
      return;
    }
    CommandRegistrar.registerCommands(this);

    ListenerRegistrar.registerHandlers(this);

    CarbonCordEvents.post(new StartupEvent(this));

    this.logger.info("Done! Enjoy CarbonCord!");
  }

  @SuppressWarnings("assignment.type.incompatible")
  public void disable() {
    CarbonCordEvents.post(new ShutdownEvent());

    ForwardingBus.carbon().safeUnsubscribeFromSource();
    ForwardingBus.carbonCord().safeUnsubscribeFromSource();
    ForwardingBus.JDA().safeUnsubscribeFromSource();

    if (this.jda != null) {
      this.jda.shutdown();
    }

    this.jda = null;
    this.commandManager = null;
    this.carbonCordSettings = null;
    this.discordChannelManager = null;
    this.notificationChannelManager = null;
  }

  // --- Public API ---

  @Override
  public void reload() {
    this.loadConfigs();
  }

  // --- Private Util etc ---

  private void loadConfigs() {
    try {
      final ConfigLoader<YamlConfigurationLoader> loader = new ConfigLoader<>(YamlConfigurationLoader.class);

      this.carbonCordSettings = CarbonCordSettings.loadFrom(loader.loadConfig("config.yml"));
    } catch (final ConfigurateException ex) {
      ex.printStackTrace();
    }
  }

  // --- Getters | Setters ---

  @Override
  public @NonNull CarbonCordSettings carbonCordSettings() {
    return this.carbonCordSettings;
  }

  @Override
  public @NonNull Path dataFolder() {
    return this.bootstrap.dataFolder().toPath();
  }

  @Override
  public @NonNull JDA jda() {
    return this.jda;
  }

  @Override
  public @NonNull CommandManager<CarbonUser> commandManager() {
    return this.commandManager;
  }

  @Override
  public @NonNull Logger logger() {
    return this.logger;
  }

  @Override
  public @NonNull DiscordChannelRegistry discordChannelRegistry() {
    return this.discordChannelManager.registry();
  }

  @Override
  public @NonNull NotificationChannelRegistry notificationChannelRegistry() {
    return this.notificationChannelManager.registry();
  }

  @Override
  public @NonNull String version() {
    return this.bootstrap.version();
  }

  @Override
  public @NonNull PlatformInfo platformInfo() {
    return this.bootstrap.platformInfo();
  }
}
