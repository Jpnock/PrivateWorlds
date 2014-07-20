package net.jpnock.privateworlds.commands;

import java.util.ArrayList;

import net.jpnock.privateworlds.language.Language;
import net.jpnock.privateworlds.PrivateWorlds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class ListCommand extends PWCommandBase
{
	
	public ListCommand() 
	{
		super("list", "privateworlds", "privateworlds.list", Language.UserLang.PRIVATEWORLDS_LIST_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_LIST_CMD_DESC );
	}

	public void run(Player player)
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
			
		if(userPrivateWorlds.size() == 0)
		{
			player.sendMessage(Language.UserLang.DOES_NOT_OWN_ANY_WORLDS_PLAYER);
			return;
		}
		
		ArrayList<String> worldsListTemp = new ArrayList<String>();
		worldsListTemp.add(Language.UserLang.LIST_OWNED_WORLDS_PLAYER);
		for(String worldName : userPrivateWorlds)
		{
			worldsListTemp.add("# " + ChatColor.YELLOW + worldName);
		}
		
		player.sendMessage(worldsListTemp.toArray(new String[worldsListTemp.size()]));
		
		
	}
}
