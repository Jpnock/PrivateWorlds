package net.jpnock.privateworlds.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import net.jpnock.privateworlds.language.Language;
import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.database.DB_RETURN_CODE;

public class CreateCommand extends PWCommandBase 
{
	public CreateCommand() 
	{
		super("create", "privateworlds", "privateworlds.world.create", Language.UserLang.PRIVATEWORLDS_CREATE_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_CREATE_CMD_DESC);
	}

	public void run(Player player, String userWorldName)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
		
		
		if(PrivateWorlds.dataHandler.insertPlayerOwnedWorld(player.getUniqueId(), userWorldName) == DB_RETURN_CODE.SUCCESSFUL)
		{
			for(Player pPlayer : Bukkit.getServer().getOnlinePlayers())
			{
				pPlayer.sendMessage(Language.UserLang.WORLD_CREATING);
			}
			
			PrivateWorlds.plugin.getServer().createWorld(new WorldCreator("PW_" + player.getUniqueId().toString() + "_" + userWorldName.toLowerCase()));
			player.sendMessage(Language.UserLang.WORLD_CREATED_MESSAGE_PLAYER);
		}
		else
		{
			player.sendMessage(Language.UserLang.ERROR_OCCURRED);
		}	
	}
}
