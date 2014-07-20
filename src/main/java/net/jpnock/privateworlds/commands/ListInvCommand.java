package net.jpnock.privateworlds.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.language.Language;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ListInvCommand extends PWCommandBase
{
	public ListInvCommand() 
	{
		super("listinv", "privateworlds", "privateworlds.listinv", Language.UserLang.PRIVATEWORLDS_LISTINV_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_LISTINV_CMD_DESC);
	}
	
	public void run(Player player)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
		
		HashMap<String, Integer> playerInvitedWorlds = PrivateWorlds.dataHandler.getPlayerInvitedWorlds(player.getUniqueId());
		
		
		String[] playerWorldNamesLinked = playerInvitedWorlds.keySet().toArray(new String[playerInvitedWorlds.size()]);
		Integer[] playerUIDsLinked = playerInvitedWorlds.values().toArray(new Integer[playerInvitedWorlds.size()]);
		List<String> playerWorldsInvitedToMessage = new ArrayList<String>();
		
		playerWorldsInvitedToMessage.add(Language.UserLang.INVITED_TO_WORLDS_LIST);
		
		for(int i = 0; i < playerInvitedWorlds.size(); i++)
		{
			playerWorldsInvitedToMessage.add(ChatColor.RED + "#" + i + ChatColor.WHITE +  " User: "  + ChatColor.YELLOW + PrivateWorlds.dataHandler.getUserNameFromInternalUID(playerUIDsLinked[i])
					+ ChatColor.WHITE + " | World: " + ChatColor.YELLOW + playerWorldNamesLinked[i]);
		}
		
		player.sendMessage(playerWorldsInvitedToMessage.toArray(new String[playerWorldsInvitedToMessage.size()]));
		
		
		
		return;
	}
}
