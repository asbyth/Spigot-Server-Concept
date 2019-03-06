package net.ethereal.ethereal.player.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class EtherealPlayer {

  public int MEMBER = 0;
  public int YOUTUBER = 95;
  public int BUILDER = 96;
  public int MOD = 97;
  public int ADMIN = 98;
  public int DEV = 99;
  public int OWNER = 100;

  private String memberColor = ChatColor.translateAlternateColorCodes('&', "&7&lMEMBER&f ");
  private String youtuberColor = ChatColor.translateAlternateColorCodes('&', "&c&lYOUTUBE&f ");
  private String builderColor = ChatColor.translateAlternateColorCodes('&', "&9&lBUILDER&f ");
  private String modColor = ChatColor.translateAlternateColorCodes('&', "&e&lMODERATOR&f ");
  private String adminColor = ChatColor.translateAlternateColorCodes('&', "&c&lADMIN&f ");
  private String devColor = ChatColor.translateAlternateColorCodes('&', "&c&lDEVELOPER&f ");
  private String ownerColor = ChatColor.translateAlternateColorCodes('&', "&4&lOWNER&f ");

  /**
   * setup the user's player data file, storing the username and rank
   * file name is saved as the players uuid
   * not efficient at all but i dont have a database and im not
   * bothering buying one for this
   *
   * @param player user
   */
  public void setupPlayer(Player player) {
    File userFile = new File("plugins/ethereal/userdata/" + player.getUniqueId() + ".yml");

    if (!userFile.exists()) {
      try {
        userFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    YamlConfiguration yml = YamlConfiguration.loadConfiguration(userFile);
    yml.addDefault("username", player.getName());
    yml.addDefault("rank", MEMBER);
    yml.options().copyDefaults(true);
    try {
      yml.save(userFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * get the users file and get the line "rank: x"
   *
   * @param player the user
   * @return the rank value
   */
  public int getRank(Player player) {
    File userFile = new File("plugins/ethereal/userdata/" + player.getUniqueId() + ".yml");
    YamlConfiguration yml = YamlConfiguration.loadConfiguration(userFile);
    return yml.getInt("rank");
  }

  /**
   * set the users rank inside the file, "rank: x"
   *
   * @param player the user
   * @param rank   the rank they're being set to
   * @return true if the rank is successfully set
   */
  public boolean setRank(Player player, int rank) {
    File userFile = new File("plugins/ethereal/userdata/" + player.getUniqueId() + ".yml");
    YamlConfiguration yml = YamlConfiguration.loadConfiguration(userFile);
    yml.set("rank", rank);
    try {
      yml.save(userFile);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * refresh the ranks everytime this is called so it updates ingame without the need
   * for a relog when changed
   */
  public void refreshRanks() {
    for (Player ranked : Bukkit.getOnlinePlayers()) {
      for (Player player : Bukkit.getOnlinePlayers()) {
        String prefix = getRankPrefixScoreboard(getRank(player));
      }
    }
  }

  /**
   * how the ranks appear on the scoreboard
   *
   * @param rank players rank
   * @return the formatting
   */
  public String getRankPrefixScoreboard(int rank) {
    if (rank == YOUTUBER) {
      return youtuberColor;
    } else if (rank == BUILDER) {
      return builderColor;
    } else if (rank == MOD) {
      return modColor;
    } else if (rank == ADMIN) {
      return adminColor;
    } else if (rank == DEV) {
      return devColor;
    } else if (rank == OWNER) {
      return ownerColor;
    }

    return memberColor;
  }

  /**
   * how the ranks appear in chat
   *
   * @param rank players rank
   * @return the formatting
   */
  public String getRankPrefixChat(int rank) {
    if (rank == YOUTUBER) {
      return youtuberColor;
    } else if (rank == BUILDER) {
      return builderColor;
    } else if (rank == MOD) {
      return modColor;
    } else if (rank == ADMIN) {
      return adminColor;
    } else if (rank == DEV) {
      return devColor;
    } else if (rank == OWNER) {
      return ownerColor;
    }

    return ChatColor.GRAY + "";
  }
}
