package net.jpnock.privateworlds.commands;

import net.jpnock.privateworlds.language.Language;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrivateWorldsCommand extends PWCommandBase implements CommandExecutor {

	public PrivateWorldsCommand() 
	{
		super("privateworlds", "", Language.UserLang.PRIVATEWORLDS_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_CMD_DESC);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("create"))
				{
					if(args.length == 2)
					{
						Commands.createCommand.run(player, args[1]);
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_CREATE_CMD_USAGE);
					}
				}
				else if(args[0].equalsIgnoreCase("tp"))
				{
					if(args.length == 2)
					{
						Commands.tpCommand.run(player, args[1]);
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_TP_CMD_USAGE);
					}
				}
				else if(args[0].equalsIgnoreCase("tpi"))
				{

					if(args.length == 3)
					{
						@SuppressWarnings("deprecation") // Only method we can practically use here.
						OfflinePlayer offlineWorldOwner = Bukkit.getServer().getOfflinePlayer(args[2]);
						
						if(offlineWorldOwner != null && offlineWorldOwner.hasPlayedBefore())
						{
							Commands.tpiCommand.run(player, args[1], offlineWorldOwner.getName(), offlineWorldOwner.getUniqueId());
						}
						else
						{
							player.sendMessage(Language.UserLang.PLAYER_DOES_NOT_EXIST);
						}
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_TPI_CMD_USAGE);
					}
				}
				else if(args[0].equalsIgnoreCase("list"))
				{
					if(args.length == 1)
					{
						Commands.listCommand.run(player);
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_LIST_CMD_USAGE);
					}
				}
				else if(args[0].equalsIgnoreCase("listinv"))
				{
					if(args.length == 1)
					{
						Commands.listInvCommand.run(player);
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_LISTINV_CMD_USAGE);
					}
				}
				else if(args[0].equalsIgnoreCase("removeaccess") || args[0].equalsIgnoreCase("remaccess"))
				{
					if(args.length == 3)
					{
						Commands.remAccessCommand.run(player, args[2], args[1]);
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_REMOVEACCESS_CMD_USAGE);
					}
				}
				else if(args[0].equalsIgnoreCase("invite"))
				{
					if(args.length == 3 || args.length == 4)
					{
						@SuppressWarnings("deprecation") // Suppress warnings here as they have to be online to have that nickname used.
						Player invitedPlayer = Bukkit.getServer().getPlayer(args[1]);

						if(invitedPlayer != null)
						{
							if(!(invitedPlayer.getUniqueId().equals(player.getUniqueId())))
							{
								if(args.length == 3)
									Commands.inviteCommand.run(player, args[2], invitedPlayer, "");
								else if(args.length == 4)
									Commands.inviteCommand.run(player, args[2], invitedPlayer, args[3]);
							}
							else
							{
								player.sendMessage(Language.UserLang.CANNOT_INVITE_SELF);
							}
						}
						else
						{
							player.sendMessage(Language.UserLang.PLAYER_NOT_ONLINE);
							
						}
					}
					else
					{
						player.sendMessage(Language.UserLang.PRIVATEWORLDS_INVITE_CMD_USAGE);
					}
				}
			}
			else
			{
				sender.sendMessage(Language.UserLang.HELP_MENU);
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}

}
