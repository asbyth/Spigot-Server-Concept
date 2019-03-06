package net.ethereal.ethereal.player.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface IMessage {

  /**
   * easily send a message to a player without the need for a
   * ChatColor.COLOR or ChatColor.translateAlternateColorCodes() call
   *
   * @param player  player the message is being sent to
   * @param message message the player is being sent
   */
  default void sendMessage(Player player, String message) {
    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Ethereal &7» " + message));
  }
}
