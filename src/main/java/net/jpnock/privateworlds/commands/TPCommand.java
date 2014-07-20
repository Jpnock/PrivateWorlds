package net.jpnock.privateworlds.commands;

import java.io.File;
import java.util.ArrayList;

import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.language.Language;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class TPCommand extends PWCommandBase
{
	
	public TPCommand() 
	{
		super("tp", "privateworlds", "privateworlds.world.tp", Language.UserLang.PRIVATEWORLDS_TP_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_TP_CMD_DESC );
	}

	public void run(Player player, String userWorldName)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
		
		ArrayList<String> userPrivateWorlds = PrivateWorlds.dataHandler.getPlayerOwnedWorlds(player.getUniqueId());
		
		if(userPrivateWorlds == null)
		{
			player.sendMessage(Language.UserLang.ERROR_OCCURRED);
			return;
		}
			
		if(!userPrivateWorlds.contains(userWorldName))
		{
			player.sendMessage(Language.UserLang.WORLD_DOES_NOT_EXIST);
			return;
		}
		
		Plugin pl = PrivateWorlds.plugin;

		final String userWorldNameFull = "PW_" + player.getUniqueId().toString() + "_" + userWorldName.toLowerCase();
			
		// If world is loaded
		if(Bukkit.getWorld(userWorldNameFull) != null)
		{
			World userWorld = Bukkit.getWorld(userWorldNameFull);
			player.teleport(userWorld.getSpawnLocation());
			player.sendMessage(String.format(Language.UserLang.TELEPORTED_TO_WORLD, userWorldName));
		}
		else
		{
			// World not loaded! Check if folder for the world actually exists.
			if(new File(pl.getServer().getWorldContainer(), userWorldNameFull).exists())
			{
				// Load the world (contrary to what you would believe createWorld does in this case)
				pl.getServer().createWorld(new WorldCreator(userWorldNameFull));
					
				World userWorld = Bukkit.getWorld(userWorldNameFull);
				player.teleport(userWorld.getSpawnLocation());
				
				player.sendMessage(String.format(Language.UserLang.TELEPORTED_TO_WORLD, userWorldName));
			}
			else
			{
				// World does not exist
				player.sendMessage(Language.UserLang.WORLD_DOES_NOT_EXIST);
			}
		}
	}
}
