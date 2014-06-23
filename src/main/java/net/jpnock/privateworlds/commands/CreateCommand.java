package net.jpnock.privateworlds.commands;

import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import net.jpnock.privateworlds.Language;
import net.jpnock.privateworlds.PrivateWorlds;

public class CreateCommand extends PWCommandBase 
{
	public CreateCommand() 
	{
		super("create", "privateworlds", "privateworlds.create", Language.PRIVATEWORLDS_CREATE_CMD_USAGE, Language.PRIVATEWORLDS_CREATE_CMD_DESC);
	}

	public void run(Player player, String userWorldName)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.NO_PERMISSION);
			return;
		}
		
		player.sendMessage(Language.WORLD_CREATING);
		PrivateWorlds.plugin.getServer().createWorld(new WorldCreator("PW_" + player.getUniqueId().toString() + "_" + userWorldName.toLowerCase()));
		player.sendMessage(Language.WORLD_CREATED_MESSAGE_PLAYER);
	}
}
