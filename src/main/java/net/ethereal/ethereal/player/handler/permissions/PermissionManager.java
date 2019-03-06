package net.ethereal.ethereal.player.handler.permissions;

import net.ethereal.ethereal.Ethereal;
import net.ethereal.ethereal.player.handler.EtherealPlayer;
import net.ethereal.ethereal.player.message.IMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PermissionManager implements Listener, IMessage {

  private Ethereal ethereal;
  private EtherealPlayer etherealPlayer;
  private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

  public PermissionManager(Ethereal ethereal, EtherealPlayer etherealPlayer) {
    this.ethereal = ethereal;
    this.etherealPlayer = etherealPlayer;
  }

  /**
   * setup permissions when a user joins the server
   * pperms.setPermission("permission", true); will add the permission
   * from there i got lost and didnt bother doing the rest because its a pain in the ass
   *
   * @param event join event, invoked upon joining the server
   */
  @EventHandler
  public void join(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    etherealPlayer.setupPlayer(player);

    PermissionAttachment attachment = player.addAttachment(ethereal);
    perms.put(player.getUniqueId(), attachment);

    PermissionAttachment pperms = perms.get(player.getUniqueId());
//    pperms.setPermission("e", true);
  }
}
