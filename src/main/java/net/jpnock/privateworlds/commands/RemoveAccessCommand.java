package net.jpnock.privateworlds.commands;

import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.database.DB_RETURN_CODE;
import net.jpnock.privateworlds.language.Language;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RemoveAccessCommand extends PWCommandBase
{
	public RemoveAccessCommand() 
	{
		super("removeaccess", "privateworlds", "privateworlds.world.removeaccess", Language.UserLang.PRIVATEWORLDS_REMOVEACCESS_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_REMOVEACCESS_CMD_DESC);
	}
	
	public void run(Player player, String userWorldName, String playerNameToRemoveAccessOf)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
			
		@SuppressWarnings({ "deprecation" }) // If they are in the game we should have no problem executing this even though it is deprecated.
		Player removeAccessPlayer = Bukkit.getServer().getPlayer(playerNameToRemoveAccessOf);
			
		if(removeAccessPlayer == null)
		{
			@SuppressWarnings("deprecation") // No other viable option here to do this.
			OfflinePlayer removeAccessOfflinePlayer = Bukkit.getServer().getOfflinePlayer(playerNameToRemoveAccessOf);
			
			if(removeAccessOfflinePlayer.hasPlayedBefore() == true)
			{	
				int oPlayerInternalUID = PrivateWorlds.dataHandler.getInternalUIDFromUUID(player.getUniqueId());
				int rPlayerInternalUID = PrivateWorlds.dataHandler.getInternalUIDFromUUID(removeAccessOfflinePlayer.getUniqueId());
				
				DB_RETURN_CODE updatedEndTime = PrivateWorlds.dataHandler.updateInvitedWorldEndTime(oPlayerInternalUID, rPlayerInternalUID, userWorldName, null);					
				
				if(updatedEndTime == DB_RETURN_CODE.SUCCESSFUL)
				{
					player.sendMessage(Language.UserLang.PLAYER_INVITED_ACCESS_REMOVED);
					return;
				}
				else if(updatedEndTime == DB_RETURN_CODE.WORLD_DOES_NOT_EXIST_OR_NOT_INVITED)
				{
					player.sendMessage(Language.UserLang.WORLD_DOES_NOT_EXIST_OR_NOT_INVITED);
					return;
				}
				else
				{
					player.sendMessage(Language.UserLang.ERROR_OCCURRED);
					return;
				}
				
			}
			else
			{
				player.sendMessage(Language.UserLang.PLAYER_DOES_NOT_EXIST);
				return;
			}
		}
		else
		{
			int oPlayerInternalUID = PrivateWorlds.dataHandler.getInternalUIDFromUUID(player.getUniqueId());
			int rPlayerInternalUID = PrivateWorlds.dataHandler.getInternalUIDFromUUID(removeAccessPlayer.getUniqueId());
				
			DB_RETURN_CODE updatedEndTime = PrivateWorlds.dataHandler.updateInvitedWorldEndTime(oPlayerInternalUID, rPlayerInternalUID, userWorldName, null);					
			
			if(updatedEndTime == DB_RETURN_CODE.SUCCESSFUL)
			{
				player.sendMessage(Language.UserLang.PLAYER_INVITED_ACCESS_REMOVED);
				return;
			}
			else if(updatedEndTime == DB_RETURN_CODE.WORLD_DOES_NOT_EXIST_OR_NOT_INVITED)
			{
				player.sendMessage(Language.UserLang.WORLD_DOES_NOT_EXIST_OR_NOT_INVITED);
				return;
			}
			else
			{
				player.sendMessage(Language.UserLang.ERROR_OCCURRED);
				return;
			}
		}	
	}
	
}
