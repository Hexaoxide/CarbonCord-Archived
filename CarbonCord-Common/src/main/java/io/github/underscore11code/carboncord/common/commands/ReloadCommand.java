package io.github.underscore11code.carboncord.common.commands;

import cloud.commandframework.context.CommandContext;
import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.util.PrettyUtil;
import net.draycia.carbon.api.users.CarbonUser;
import net.kyori.adventure.text.Component;

public class ReloadCommand {
  private final CarbonCord carbonCord;

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public ReloadCommand(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;

    this.carbonCord.commandManager().command(this.carbonCord.baseCommand()
      .literal("reload")
      .permission("carboncord.reload")
      .handler(this::reload)
    );
  }

  private void reload(final CommandContext<CarbonUser> c) {
    c.getSender().sendMessage(PrettyUtil.prefixed(Component.text("Reloading...")));
    this.carbonCord.reload();
    c.getSender().sendMessage(PrettyUtil.prefixed(Component.text("Reload Complete!")));
  }
}
