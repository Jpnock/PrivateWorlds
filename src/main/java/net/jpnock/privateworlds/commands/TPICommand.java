package net.jpnock.privateworlds.commands;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.language.Language;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class TPICommand extends PWCommandBase
{
	
	public TPICommand() 
	{
		super("tpi", "privateworlds", "privateworlds.world.tpi", Language.UserLang.PRIVATEWORLDS_TPI_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_TPI_CMD_DESC );
	}

	public void run(Player player, String userWorldName, String playerNameWorldOwner, UUID playerUUIDWorldOwner)
	{
		Plugin pl = PrivateWorlds.plugin;
		
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
		
		HashMap<String, Integer> userInvitedPrivateWorlds = PrivateWorlds.dataHandler.getPlayerInvitedWorlds(player.getUniqueId());
		
		if(userInvitedPrivateWorlds == null)
		{
			player.sendMessage("You are invited to 0 worlds!" /*INVITED TO NO WORLDS */ );
			return;
		}
			
		boolean bWorldExists = false;
		for(String worldName : userInvitedPrivateWorlds.keySet())
		{
			System.out.println("A: " + worldName + " B: " + userWorldName +  " C: " + userInvitedPrivateWorlds.get(userWorldName) + " D: ");
			if(worldName.toLowerCase().equals(userWorldName.toLowerCase()) && PrivateWorlds.dataHandler.getInternalUIDFromUUID(playerUUIDWorldOwner) == userInvitedPrivateWorlds.get(userWorldName))
			{
				bWorldExists = true;
			}
		}
		
		if(bWorldExists == false)
		{
			player.sendMessage(Language.UserLang.WORLD_DOES_NOT_EXIST);
			return;
		}

		UUID worldOwnerUUID = PrivateWorlds.dataHandler.getUUIDFromInternalUID(userInvitedPrivateWorlds.get(userWorldName));
		
		if(worldOwnerUUID == null)
		{
			player.sendMessage(Language.UserLang.ERROR_OCCURRED);
			return;
		}
		
		

		final String userWorldNameFull = "PW_" + worldOwnerUUID.toString() + "_" + userWorldName.toLowerCase();
			
		// If world is loaded
		if(Bukkit.getWorld(userWorldNameFull) != null)
		{
			World userWorld = Bukkit.getWorld(userWorldNameFull);
			player.teleport(userWorld.getSpawnLocation());
			player.sendMessage(String.format(Language.UserLang.TELEPORTED_TO_INVITED_WORLD, playerNameWorldOwner, userWorldName));
		}
		else
		{
			// World not loaded! Check if folder for the world actually exists.
			if(new File(pl.getServer().getWorldContainer(), userWorldNameFull).exists())
			{
				// Load the world (contrary to what you would believe createWorld does in this case)
				pl.getServer().createWorld(new WorldCreator(userWorldNameFull));
					
				World userWorld = Bukkit.getWorld(userWorldNameFull);
				player.teleport(userWorld.getSpawnLocation());//
				
				player.sendMessage(String.format(Language.UserLang.TELEPORTED_TO_WORLD, userWorldName));
			}
			else
			{
				// World does not exist
				player.sendMessage(Language.UserLang.WORLD_FOLDER_DOES_NOT_EXIST);
			}
		}
	}
}
