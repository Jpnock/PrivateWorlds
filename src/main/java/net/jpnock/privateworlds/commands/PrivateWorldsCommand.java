package net.jpnock.privateworlds.commands;

import net.jpnock.privateworlds.Language;
import net.jpnock.privateworlds.PrivateWorlds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PrivateWorldsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Plugin plugin = PrivateWorlds.plugin;
			Player player = (Player)sender;
			
			if(args.length > 0)
			{
				if(args[0].equals("create"))
				{
					if(args.length == 2)
					{
						CreateCommand.run(player, args[1]);
					}
					else
					{
						return false;
					}
				}
			}
			else
			{
				sender.sendMessage(Language.HELP_MENU);
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}

}
