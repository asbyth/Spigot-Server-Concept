package net.ethereal.ethereal.scoreboard;

import net.ethereal.ethereal.Ethereal;
import net.ethereal.ethereal.player.handler.EtherealPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import static org.bukkit.scoreboard.DisplaySlot.SIDEBAR;

public class EtherealTab implements Listener {

  private Ethereal ethereal;
  private EtherealPlayer etherealPlayer;

  public EtherealTab(Ethereal ethereal, EtherealPlayer etherealPlayer) {
    this.ethereal = ethereal;
    this.etherealPlayer = etherealPlayer;
  }

  @EventHandler
  public void join(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    etherealPlayer.setupPlayer(player);
    refreshRank();
  }

  /**
   * this dont even work im not gon explain it
   */

  private void refreshRank() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      ScoreboardManager manager = Bukkit.getScoreboardManager();
      Scoreboard board = manager.getNewScoreboard();
      Objective objective = board.registerNewObjective("test", "dummy");
      objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
      for (Player player1 : Bukkit.getOnlinePlayers()) {
        String prefix = etherealPlayer.getRankPrefixScoreboard(etherealPlayer.getRank(player1));
        Team team = board.registerNewTeam(player1.getName());
        team.setPrefix(prefix);
        team.addEntry(player1.getName());
      }

      player.setScoreboard(board);
    }
  }
}
