package net.jpnock.privateworlds;

import java.io.IOException;

import net.jpnock.privateworlds.commands.Commands;
import net.jpnock.privateworlds.commands.PrivateWorldsCommand;
import net.jpnock.privateworlds.database.DB_RETURN_CODE;
import net.jpnock.privateworlds.database.IDataHandler;
import net.jpnock.privateworlds.database.mysql.MySQLDatabase;
import net.jpnock.privateworlds.language.Language;
import net.jpnock.privateworlds.listeners.PlayerJoinEventHandler;
import net.jpnock.privateworlds.metrics.MetricsLite;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PrivateWorlds extends JavaPlugin
{
	public static Plugin plugin;
	public static PluginDescriptionFile pdf;
	public static Commands cmds;
	public static IDataHandler dataHandler;
	
	private static boolean usingMySQL = true; // Implemented but not necessary.
	
	@Override
	public void onEnable()
	{
		
		plugin = this;
		pdf = getDescription();
		cmds = new Commands();
		
		getCommand("privateworlds").setExecutor(new PrivateWorldsCommand());
		
		try 
		{
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} 
		catch (IOException e) 
		{
			getLogger().info("A problem has occurred within plugin metrics!");
		}
		catch(Exception e)
		{
			getLogger().info("A problem has occurred within plugin metrics!");
		}
		

		setupAndLoadConfigs();
		setupDBConnectionString();
		
		if(usingMySQL == true)
			dataHandler = new MySQLDatabase();
		else
			dataHandler = null;
	
		if(!performDataOperations())
		{
			return;
		}
		
		getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
		
		
		getLogger().info("Plugin Enabled!");
	}

	
	@Override
	public void onDisable()
	{
		PrivateWorlds.plugin = null;
		PrivateWorlds.pdf = null;
		PrivateWorlds.cmds = null;
		PrivateWorlds.dataHandler = null;
		
		getLogger().info("Plugin Disabled!");
		
	}
	
	public void setupAndLoadConfigs()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void setupDBConnectionString()
	{
		if(usingMySQL == true)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("jdbc:mysql://");
			sb.append(getConfig().getString("DatabaseInfo.hostname") + ":");
			sb.append(getConfig().getString("DatabaseInfo.port") + "/");
			sb.append(getConfig().getString("DatabaseInfo.dbname"));
			
			MySQLDatabase.dbConnectionString = sb.toString();
			MySQLDatabase.dbUsername = getConfig().getString("DatabaseInfo.username");
			MySQLDatabase.dbPassword = getConfig().getString("DatabaseInfo.password");
			MySQLDatabase.dbTablePrefix = getConfig().getString("DatabaseInfo.table_prefix");
		}
	}
	
	public boolean performDataOperations()
	{
		if(getConfig().getInt("ConfigFileVersion") == 1)
		{
			if(dataHandler.performDataOperations(1) == DB_RETURN_CODE.SUCCESSFUL)
			{
				getConfig().set("ConfigFileVersion", 2);
				getConfig().set("DatabaseVersion", 2);
				saveConfig();
				return true;
			}
			else
			{
				getLogger().warning(Language.ConsoleLang.INTERNAL_ERROR_OCCURRED);
				Bukkit.getPluginManager().disablePlugin(this);
				return false;
			}			
		}
		
		return true;
	}
}
