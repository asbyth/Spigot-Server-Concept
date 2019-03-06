package net.ethereal.ethereal.scoreboard;

import net.ethereal.ethereal.Ethereal;
import net.ethereal.ethereal.player.handler.EtherealPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.bukkit.scoreboard.DisplaySlot.SIDEBAR;

public class EtherealScoreboard implements Listener {

  private Ethereal ethereal;
  private EtherealPlayer etherealPlayer;

  private String servername = Bukkit.getServer().getClass().getPackage().getName();
  private String serverversion = servername.substring(servername.lastIndexOf(".") + 1);

  private String displayName = ChatColor.translateAlternateColorCodes('&', "  &5&lEthereal  ");
  private String line = ChatColor.translateAlternateColorCodes('&', "&7&m-------------------");
  private String onlineString = ChatColor.translateAlternateColorCodes('&', "&5Online:");
  private String onlineNumber = ChatColor.translateAlternateColorCodes('&', "&7");
  private String pingString = ChatColor.translateAlternateColorCodes('&', "&5Ping:");
  private String pingNumber = ChatColor.translateAlternateColorCodes('&', "&7");
  private String world = ChatColor.translateAlternateColorCodes('&', "&5Lobby:");
  private String worldName = ChatColor.translateAlternateColorCodes('&', "&7");
  private String info = ChatColor.translateAlternateColorCodes('&', "&5Rank:");
  private String line2 = ChatColor.translateAlternateColorCodes('&', "&7&m-------------------&r");

  public EtherealScoreboard(Ethereal ethereal, EtherealPlayer etherealPlayer) {
    this.ethereal = ethereal;
    this.etherealPlayer = etherealPlayer;
  }

  /**
   * this method is used to create a realtime updating scoreboard
   * it's given to the player when they join the server and updates
   * every few seconds (20 * 10)
   *
   * contains the following;
   * how many players are online
   * the users current ping
   * the lobby they're in (uses world name)
   * the users rank
   *
   * @param event invoked upon joining the server
   */
  @EventHandler
  public void join(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ethereal, () -> {
      ScoreboardManager manager = Bukkit.getScoreboardManager();
      Scoreboard board = manager.getNewScoreboard();
      Objective objective = board.registerNewObjective("test", "dummy");

      objective.setDisplaySlot(SIDEBAR);
      objective.setDisplayName(displayName);

      Score dash = objective.getScore(line);
      dash.setScore(12);

      Score online = objective.getScore(onlineString);
      online.setScore(11);
      Score count = objective.getScore(onlineNumber + getPlayerCount());
      count.setScore(10);

      Score blank = objective.getScore(" ");
      blank.setScore(9);

      Score ping = objective.getScore(pingString);
      ping.setScore(8);
      Score userPing = objective.getScore(pingNumber + getPing(player));
      userPing.setScore(7);

      Score blank2 = objective.getScore("  ");
      blank2.setScore(6);

      Score hub = objective.getScore(world);
      hub.setScore(5);
      Score hubName = objective.getScore(worldName + player.getWorld().getName());
      hubName.setScore(4);

      Score blank3 = objective.getScore("   ");
      blank3.setScore(3);

      Score playerinfo = objective.getScore(info);
      playerinfo.setScore(2);
      Score rank = objective.getScore(etherealPlayer.getRankPrefixScoreboard(etherealPlayer.getRank(player)));
      rank.setScore(1);

      Score bottomDash = objective.getScore(line2);
      bottomDash.setScore(0);

      player.setScoreboard(board);
      etherealPlayer.refreshRanks();
    }, 0, 20 * 10);
  }

  /**
   * method that grabs the users ping and puts it onto the scoreboard
   * uses reflection because otherwise you can't get it using
   * ((CraftPlayer) player).getHandle().ping;
   *
   * @param player the user
   * @return the ping amount; 0 if it's not working
   */
  private int getPing(Player player) {
    try {
      Class<?> CPClass = Class.forName("org.bukkit.craftbukkit." + serverversion + ".entity.CraftPlayer");
      Object CraftPlayer = CPClass.cast(player);

      Method getHandle = CraftPlayer.getClass().getMethod("getHandle");
      Object EntityPlayer = getHandle.invoke(CraftPlayer);

      Field ping = EntityPlayer.getClass().getDeclaredField("ping");

      return ping.getInt(EntityPlayer);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  /**
   * get the amount of players online and continue to count
   *
   * @return player count
   */
  private int getPlayerCount() {
    int players = 0;

    for (Player player : Bukkit.getOnlinePlayers()) {
      players++;
    }

    return players;
  }
}
