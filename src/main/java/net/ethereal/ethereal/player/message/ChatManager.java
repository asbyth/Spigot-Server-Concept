package net.ethereal.ethereal.player.message;

import net.ethereal.ethereal.Ethereal;
import net.ethereal.ethereal.player.handler.EtherealPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

  private Ethereal ethereal;
  private EtherealPlayer etherealPlayer;

  public ChatManager(Ethereal ethereal, EtherealPlayer etherealPlayer) {
    this.ethereal = ethereal;
    this.etherealPlayer = etherealPlayer;
  }

  /**
   * set the chat to include the users prefix
   *
   * @param event invoked upon chatting
   */
  @EventHandler
  public void chat(AsyncPlayerChatEvent event) {
    event.setCancelled(true);
    Player player = event.getPlayer();
    String name = player.getName();
    String prefix = etherealPlayer.getRankPrefixChat(etherealPlayer.getRank(player));
    String message = event.getMessage();
    Bukkit.getServer().broadcastMessage(prefix + name + " » " + message);
  }
}
