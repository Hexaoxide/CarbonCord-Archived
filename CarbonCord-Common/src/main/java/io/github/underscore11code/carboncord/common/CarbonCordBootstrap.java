package io.github.underscore11code.carboncord.common;

import cloud.commandframework.CommandManager;
import io.github.underscore11code.carboncord.api.misc.PlatformInfo;
import net.draycia.carbon.api.users.CarbonUser;

import java.io.File;

public interface CarbonCordBootstrap {

  /**
   * Disable the plugin. Intended for fatal startup errors
   */
  void disable();

  void reload();

  CommandManager<CarbonUser> commandManager() throws Exception;

  File dataFolder();

  PlatformInfo platformInfo();

  String version();
}
