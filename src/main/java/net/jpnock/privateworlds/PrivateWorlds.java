package net.jpnock.privateworlds;

import net.jpnock.privateworlds.commands.PrivateWorldsCommand;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PrivateWorlds extends JavaPlugin
{
	public static Plugin plugin;
	public static PluginDescriptionFile pdf;
	
	@Override
	public void onEnable()
	{
		getLogger().info("[PrivateWorlds] Enabled!");
		getCommand("privateworlds").setExecutor(new PrivateWorldsCommand());
		plugin = this;
		pdf = getDescription();
	}
	

	@Override
	public void onDisable()
	{
		getLogger().info("[PrivateWorlds] Disabled!");
		
	}
}
