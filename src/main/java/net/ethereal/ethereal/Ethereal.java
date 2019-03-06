package net.ethereal.ethereal;

import net.ethereal.ethereal.command.CommandRank;
import net.ethereal.ethereal.config.FileHandler;
import net.ethereal.ethereal.player.LobbyItems;
import net.ethereal.ethereal.player.handler.EtherealPlayer;
import net.ethereal.ethereal.player.handler.permissions.PermissionManager;
import net.ethereal.ethereal.player.message.ChatManager;
import net.ethereal.ethereal.scoreboard.EtherealScoreboard;
import net.ethereal.ethereal.scoreboard.EtherealTab;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ethereal extends JavaPlugin {

  private FileHandler file = new FileHandler();
  private EtherealPlayer player = new EtherealPlayer();

  /**
   * method called upon starting the server
   *
   * handles registering;
   * the commands
   * the events
   * the config
   * the file setup
   */
  @Override
  public void onEnable() {
    registerCommands();
    registerEvents();
    getConfig().options().copyDefaults(true);
    saveConfig();
    file.setup();
  }

  /**
   * registers all the commands when invoked
   */
  private void registerCommands() {
    getCommand("rank").setExecutor(new CommandRank(this, player));
  }

  /**
   * registers events easily when invoked
   */
  private void registerEvents() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new EtherealScoreboard(this, player), this);
    pm.registerEvents(new EtherealTab(this, player), this);
    pm.registerEvents(new LobbyItems(this), this);
    pm.registerEvents(new PermissionManager(this, player), this);
    pm.registerEvents(new ChatManager(this, player), this);
  }

  /**
   * method called when closing the server
   * just saves the config
   */
  @Override
  public void onDisable() {
    saveConfig();
  }
}
