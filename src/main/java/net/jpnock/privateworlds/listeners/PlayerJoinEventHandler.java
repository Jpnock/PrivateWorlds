package net.jpnock.privateworlds.listeners;

import net.jpnock.privateworlds.PrivateWorlds;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinEventHandler implements Listener
{
	@EventHandler(priority = EventPriority.LOW)
	public void playerLoginEvent(PlayerLoginEvent plLoginEvent) 
	{
		PrivateWorlds.dataHandler.insertOrUpdatePlayer(plLoginEvent.getPlayer().getUniqueId(), plLoginEvent.getPlayer().getName());
	}
}
