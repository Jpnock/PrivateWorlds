package net.jpnock.privateworlds.commands;

import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import net.jpnock.privateworlds.Language;
import net.jpnock.privateworlds.PrivateWorlds;

public class CreateCommand 
{
	public static void run(Player player, String userWorldName)
	{
		PrivateWorlds.plugin.getServer().createWorld(new WorldCreator("PW_" + player.getUniqueId().toString() + "_" + userWorldName));
		player.sendMessage(Language.WORLD_CREATED_PLAYER_MESSAGE);
	}
}
