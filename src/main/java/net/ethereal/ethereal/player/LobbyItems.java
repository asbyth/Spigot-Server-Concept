package net.ethereal.ethereal.player;

import net.ethereal.ethereal.Ethereal;
import net.ethereal.ethereal.player.message.IMessage;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import java.util.Collections;

import static org.bukkit.ChatColor.*;
import static org.bukkit.Material.*;

public class LobbyItems implements Listener, IMessage {

  private Ethereal ethereal;

  private Inventory navigatorServers = Bukkit.createInventory(null, 9, "Navigator");
  private Inventory playerStats = Bukkit.createInventory(null, 9, "Stats");
  private Inventory lobbies = Bukkit.createInventory(null, 9, "Lobbies");

  private String navigatorName = translateAlternateColorCodes('&', "&9Navigator");
  private String playerHidden = translateAlternateColorCodes('&', "&cHide Players");
  private String playerVisible = translateAlternateColorCodes('&', "&aShow Players");
  private String playerName = translateAlternateColorCodes('&', "&e");
  private String cosmeticsName = translateAlternateColorCodes('&', "&6Cosmetics");
  private String lobbynavName = translateAlternateColorCodes('&', "&9Lobbies");

  private String sgName = translateAlternateColorCodes('&', "&eSurvival Games");
  private String sgLore = translateAlternateColorCodes('&',
          "&7Fight against other players\n" +
                  "in a giant arena, surrounded\n" +
                  "with chest and dangers\n" +
                  "and try to be the\n" +
                  "last man standing.");

  private String dtName = translateAlternateColorCodes('&', "&cDeath Tag");
  private String dtLore = translateAlternateColorCodes('&',
          "&7Run away from a team\n" +
                  "of &cChasers &7and try to\n" +
                  "be the latest one standing\n" +
                  "against other players.");

  public LobbyItems(Ethereal ethereal) {
    this.ethereal = ethereal;
  }

  /**
   * once the player joins, give them all the items
   *
   * @param event invoked upon joining the server
   */
  @EventHandler
  public void join(PlayerJoinEvent event) {
    giveNavigator(event.getPlayer());
    givePlayerHider(event.getPlayer());
    givePlayerInfo(event.getPlayer());
    giveCosmetics(event.getPlayer());
    giveLobbyNavigator(event.getPlayer());
  }

  /**
   * gives the user a compass which can act as a minigame navigator
   *
   * @param player user the item is being given to
   */
  private void giveNavigator(Player player) {
    ItemStack navigator = new ItemStack(COMPASS);
    ItemMeta navigatorMeta = navigator.getItemMeta();
    navigatorMeta.setDisplayName(navigatorName);
    navigator.setItemMeta(navigatorMeta);
    setItem(player, 0, navigator);
  }

  /**
   * gives the user a light grey dye, allowing them to hide players
   *
   * @param player user the item is being given to
   */
  private void givePlayerHider(Player player) {
    Dye grey = new Dye();
    grey.setColor(DyeColor.GRAY);
    ItemStack hidePlayers = grey.toItemStack(1);
    ItemMeta hideMeta = hidePlayers.getItemMeta();
    hideMeta.setDisplayName(playerHidden);
    hidePlayers.setItemMeta(hideMeta);
    setItem(player, 1, hidePlayers);
  }

  /**
   * gives the user a lime green dye, allowing them to show players
   * this is given when they use the hider
   *
   * @param player user the item is being given to
   */
  private void givePlayerShower(Player player) {
    Dye lime = new Dye();
    lime.setColor(DyeColor.LIME);
    ItemStack showPlayers = lime.toItemStack(1);
    ItemMeta showMeta = showPlayers.getItemMeta();
    showMeta.setDisplayName(playerVisible);
    showPlayers.setItemMeta(showMeta);
    setItem(player, 1, showPlayers);
  }

  /**
   * gives the user their head, allowing them to see
   * their ingame stats for whatever minigames are
   * present
   *
   * @param player user the item is being given to
   */
  private void givePlayerInfo(Player player) {
    ItemStack skull = new ItemStack(SKULL_ITEM, 1, (short) 3);
    SkullMeta meta = (SkullMeta) skull.getItemMeta();
    meta.setOwner(player.getName());
    meta.setDisplayName(playerName + player.getName() + GRAY + " Stats");
    skull.setItemMeta(meta);
    setItem(player, 4, skull);
  }

  /**
   * gives the user a gold bar, allowing them to choose
   * whatever cosmetic they wish to spawn, if any
   *
   * @param player user the item is being given to
   */
  private void giveCosmetics(Player player) {
    ItemStack cosmetics = new ItemStack(GOLD_INGOT);
    ItemMeta cosmeticsMeta = cosmetics.getItemMeta();
    cosmeticsMeta.setDisplayName(cosmeticsName);
    cosmetics.setItemMeta(cosmeticsMeta);
    setItem(player, 7, cosmetics);
  }

  /**
   * gives the user a nether star, serving as another
   * navigator, but instead for lobbies
   *
   * @param player user the item is being given to
   */
  private void giveLobbyNavigator(Player player) {
    ItemStack lobbynav = new ItemStack(NETHER_STAR);
    ItemMeta lobbymeta = lobbynav.getItemMeta();
    lobbymeta.setDisplayName(lobbynavName);
    lobbynav.setItemMeta(lobbymeta);
    setItem(player, 8, lobbynav);
  }

  /**
   * easily set an item without the need for a lot of player.getInventory().setItem(inventory slot 0 - 8, itemstackVariable);
   *
   * @param player user the item is being given to
   * @param slot   the slot the item is going to be in (0 -8)
   * @param item   the item the person is being given
   */
  private void setItem(Player player, int slot, ItemStack item) {
    player.getInventory().setItem(slot, item);
  }

  /**
   * easily set items inside of an inventory, like the compass navigator, or
   * lobby navigator, without the need for a bunch of item stacks
   */
  private void addNavigatorItems() {
    createNavigatorItem(sgName, sgLore, IRON_SWORD, 3);
    createNavigatorSkull(dtName, dtLore, SKULL_ITEM, 5, 0);
  }

  /**
   * create a skull that allows the use of setting what specific skull they'd be given
   *
   * @param name      - name of the skull
   * @param lore      - lore of the skull
   * @param item      - not sure why i added this, its always a skull
   * @param slot      - the slot the skull is going to (0 - 53? i forget the max chest size)
   * @param skullType - 0 skeleton 1 wither skeleton 2 zombie 3 player 4 creeper
   * @return the item
   */
  private ItemStack createNavigatorSkull(String name, String lore, Material item, int slot, int skullType) {
    ItemStack skull = new ItemStack(item, 1, (short) skullType);
    ItemMeta meta = skull.getItemMeta();
    meta.setDisplayName(name);
    meta.setLore(Collections.singletonList(lore));
    skull.setItemMeta(meta);
    navigatorServers.setItem(slot, skull);
    return skull;
  }

  /**
   * create an item inside an Inventory
   *
   * @param name - name of the item
   * @param lore - lore of the item
   * @param item - the item
   * @param slot - the slot the item is going to (0 - 53? i forget the max chest size)
   * @return the item
   */
  private ItemStack createNavigatorItem(String name, String lore, Material item, int slot) {
    ItemStack i = new ItemStack(item, 1);
    ItemMeta meta = i.getItemMeta();
    meta.setDisplayName(name);
    meta.setLore(Collections.singletonList(lore));
    i.setItemMeta(meta);
    navigatorServers.setItem(slot, i);
    return i;
  }

  /**
   * method used to hide any player (including themselves on other players screens)
   */
  private void hidePlayers() {
    for (Player toHide : Bukkit.getServer().getOnlinePlayers()) {
      for (Player player : Bukkit.getServer().getOnlinePlayers()) {
        if (player != toHide) {
          player.hidePlayer(toHide);
        }
      }
    }
  }

  /**
   * method used to show any player
   */
  private void showPlayers() {
    for (Player toShow : Bukkit.getServer().getOnlinePlayers()) {
      for (Player player : Bukkit.getServer().getOnlinePlayers()) {
        if (player != toShow) {
          player.showPlayer(toShow);
        }
      }
    }
  }

  /**
   * stop things from being picked up if they dont have permission
   *
   * @param event invoked upon trying to pickup an item
   */
  @EventHandler
  public void pickup(PlayerPickupItemEvent event) {
    event.setCancelled(true);
  }

  /**
   * stop things from being dropped if they dont have permission
   *
   * @param event invoked upon trying to drop an item
   */
  @EventHandler
  public void drop(PlayerDropItemEvent event) {
    event.setCancelled(true);
  }

  /**
   * stop players from clicking on things inside their inventory
   *
   * @param event invoked upon trying to click inside an inventory
   */
  @EventHandler
  public void clickInventory(InventoryInteractEvent event) {
    if (event.getInventory() == event.getWhoClicked().getInventory()) {
      event.setCancelled(true);
    }
  }

  /**
   * called when opening the game navigator
   * adds all the items
   *
   * @param event invoked upon opening an inventory
   */
  @EventHandler
  public void openNavigator(InventoryOpenEvent event) {
    if (event.getInventory().getName().equals(navigatorServers.getName())) {
      addNavigatorItems();
    }
  }

  /**
   * used to activate something when the player interacts with a specific item
   *
   * @param event invoked upon clicking / right clicking something
   */
  @EventHandler
  public void itemClick(PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      ItemStack currentItem = event.getPlayer().getItemInHand();

      if (currentItem != null && currentItem.getType() != AIR) {
        if (currentItem.getItemMeta().getDisplayName().equals(navigatorName)) {
          event.getPlayer().openInventory(navigatorServers);
        }

        if (currentItem.getItemMeta().getDisplayName().equals(playerHidden)) {
          hidePlayers();
          givePlayerShower(event.getPlayer());
          sendMessage(event.getPlayer(), "&cPlayers are now hidden");
          event.setCancelled(true);
        }

        if (currentItem.getItemMeta().getDisplayName().equals(playerVisible)) {
          showPlayers();
          givePlayerHider(event.getPlayer());
          sendMessage(event.getPlayer(), "&aPlayers are now visible");
          event.setCancelled(true);
        }

        if (currentItem.getItemMeta().getDisplayName().contains(stripColor(event.getPlayer().getName()))) {
          sendMessage(event.getPlayer(), "&cPlayer stats currently aren't ready, check back later!");
        }

        if (currentItem.getItemMeta().getDisplayName().equals(cosmeticsName)) {
          sendMessage(event.getPlayer(), "&cCosmetics currently aren't ready, check back later!");
        }

        if (currentItem.getItemMeta().getDisplayName().equals(lobbynavName)) {
          sendMessage(event.getPlayer(), "&cLobby navigator currently isn't ready, check back later!");
        }
      }
    }
  }

  /**
   * handle doing what is meant to be done when the user clicks something
   * inside the game navigator
   *
   * @param event invoked upon clicking inside an inventory
   */
  @EventHandler
  public void serversClick(InventoryClickEvent event) {
    String name = event.getInventory().getName();

    if (!name.equals(navigatorServers.getName())) {
      return;
    }

    if (event.getClick().equals(ClickType.NUMBER_KEY)) {
      event.setCancelled(true);
    }

    event.setCancelled(true);

    Player player = (Player) event.getWhoClicked();
    ItemStack clicked = event.getCurrentItem();

    if (clicked == null || clicked.getType().equals(AIR)) return;

    if (clicked.getType().equals(IRON_SWORD)) {
      sendMessage(player, "&eJoining queue for a game, please wait.");
    }

    if (clicked.getType().equals(SKULL_ITEM)) {
      sendMessage(player, "&eJoining queue for a game, please wait.");
    }
  }
}
