package net.jpnock.privateworlds;

import java.io.IOException;

import net.jpnock.privateworlds.commands.Commands;
import net.jpnock.privateworlds.commands.PrivateWorldsCommand;
import net.jpnock.privateworlds.metrics.MetricsLite;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PrivateWorlds extends JavaPlugin
{
	public static Plugin plugin;
	public static PluginDescriptionFile pdf;
	public static Commands cmds = new Commands();
	
	@Override
	public void onEnable()
	{
		getCommand("privateworlds").setExecutor(new PrivateWorldsCommand());
		plugin = this;
		pdf = getDescription();
		
		try 
		{
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} 
		catch (IOException e) 
		{
			getLogger().info(Language.PLUGIN_NAMETAG + " A problem happened within plugin metrics!");
		}
		catch(Exception e)
		{
			getLogger().info(Language.PLUGIN_NAMETAG + " A problem happened within plugin metrics!");
		}
		
		getLogger().info(Language.PLUGIN_NAMETAG +" Enabled!");
	}

	
	@Override
	public void onDisable()
	{
		getLogger().info("[PrivateWorlds] Disabled!");
		
	}
}
