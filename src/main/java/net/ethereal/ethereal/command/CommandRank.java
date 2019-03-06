package net.ethereal.ethereal.command;

import net.ethereal.ethereal.Ethereal;
import net.ethereal.ethereal.player.handler.EtherealPlayer;
import net.ethereal.ethereal.player.message.IMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRank implements CommandExecutor, IMessage {

  private Ethereal ethereal;
  private EtherealPlayer etherealPlayer;

  public CommandRank(Ethereal ethereal, EtherealPlayer etherealPlayer) {
    this.ethereal = ethereal;
    this.etherealPlayer = etherealPlayer;
  }

  /**
   * command allows for setting a users rank ingame without having to change their rank through
   * their uuid file
   *
   * @param sender  player sending the command
   * @param command the command
   * @param label   never used this, dont know
   * @param args    arguments after /rank
   * @return true once finished if successful
   */
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player) sender;

    int rank = etherealPlayer.getRank(player);

    if (rank >= etherealPlayer.ADMIN) {
      if (args.length == 2) {
        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (!(target == null)) {
          int rankValue;
          String rankname = args[1].toLowerCase();

          switch (rankname) {
            case "owner":
              rankValue = etherealPlayer.OWNER;
              break;

            case "developer":
              rankValue = etherealPlayer.DEV;
              break;

            case "admin":
              rankValue = etherealPlayer.ADMIN;
              break;

            case "moderator":
              rankValue = etherealPlayer.MOD;
              break;

            case "builder":
              rankValue = etherealPlayer.BUILDER;
              break;

            case "youtube":
              rankValue = etherealPlayer.YOUTUBER;
              break;

            case "member":
              rankValue = etherealPlayer.MEMBER;
              break;

            default:
              rankValue = -1;
              break;
          }

          if (rankValue >= 0) {
            if (rankValue < rank) {
              if (etherealPlayer.getRank(target) < rank) {
                if (etherealPlayer.setRank(target, rankValue)) {
                  sendMessage(player, "&aSet " + target.getName() + "'s rank to " + rankname);
                  sendMessage(target, "&aCongratulations, your rank has been set to " + rankname);
                  etherealPlayer.refreshRanks();
                } else {
                  sendMessage(player, "&cUnable to set " + target.getName() + "'s rank to " + rankname);
                }
              } else {
                sendMessage(player, "&cUnable to set the rank due to &7" + target.getName() + "'s &crank being higher or equal to yours.");
              }
            } else {
              sendMessage(player, "&cUnable to set the rank due to it being larger or equal to yours.");
            }
          } else {
            sendMessage(player, "&cInvalid rank found, found &7" + rankname);
          }

        } else {
          sendMessage(player, "&c" + targetName + " not found. Do they exist?");
        }

      } else {
        sendMessage(player, "&cUsage: /rank <player> <rank>");
      }
      return true;
    } else {
      sendMessage(player, "&cYou do not have permission to set someones rank.");
    }

    return true;
  }
}
