package net.jpnock.privateworlds.commands;

import org.bukkit.entity.Player;

import net.jpnock.privateworlds.language.Language;
import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.database.DB_RETURN_CODE;

public class DeleteCommand extends PWCommandBase 
{
	public DeleteCommand() 
	{
		super("delete", "privateworlds", "privateworlds.world.delete", Language.UserLang.PRIVATEWORLDS_DELETE_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_DELETE_CMD_DESC);
	}

	public void run(Player player, String userWorldName)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
		
		
		if(PrivateWorlds.dataHandler.deletePlayerOwnedWorld(player.getUniqueId(), userWorldName) == DB_RETURN_CODE.SUCCESSFUL)
		{
			player.sendMessage(Language.UserLang.WORLD_CREATED_MESSAGE_PLAYER);
		}
		else
		{
			player.sendMessage(Language.UserLang.ERROR_OCCURRED);
		}	
	}
}
