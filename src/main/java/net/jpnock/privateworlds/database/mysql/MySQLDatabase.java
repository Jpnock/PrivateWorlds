package net.jpnock.privateworlds.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.database.DB_RETURN_CODE;
import net.jpnock.privateworlds.database.IDataHandler;

public class MySQLDatabase implements IDataHandler
{	
	public static String[] tableNames = {"PW_Users", "PW_Worlds", "PW_InvitedPlayers"};
	

	public static String dbConnectionString;
	public static String dbUsername;
	public static String dbPassword;
	public static String dbTablePrefix;
	
	private Connection connection;
	
	// When we reload and have a new dbPrefix we will create a new instance on MySQLDatabase and not use the previous one.
	public MySQLDatabase()
	{
		for(int i = 0; i < tableNames.length; i++)
			tableNames[i] = dbTablePrefix + tableNames[i];
		
		Queries.DB_USERS_CREATION_STATEMENT = String.format(QueriesRaw.DB_USERS_CREATION_STATEMENT, tableNames[0]);
		Queries.DB_WORLDS_CREATION_STATEMENT = String.format(QueriesRaw.DB_WORLDS_CREATION_STATEMENT, tableNames[1], tableNames[1], tableNames[1], tableNames[0]);
		Queries.DB_INVITED_PLAYERS_CREATION_STATEMENT = String.format(QueriesRaw.DB_INVITED_PLAYERS_CREATION_STATEMENT, tableNames[2], tableNames[2], tableNames[2], tableNames[2], tableNames[0], tableNames[2], tableNames[0]);
		
		Queries.DB_SELECT_PLAYER_OWNED_WORLDS = String.format(QueriesRaw.DB_SELECT_PLAYER_OWNED_WORLDS, tableNames[1]);
		Queries.DB_SELECT_PLAYER_INVITED_WORLDS = String.format(QueriesRaw.DB_SELECT_PLAYER_INVITED_WORLDS, tableNames[2]);
		
		Queries.DB_INSERT_PLAYER_WORLD = String.format(QueriesRaw.DB_INSERT_PLAYER_WORLD, tableNames[1]);
		Queries.DB_INSERT_PLAYER = String.format(QueriesRaw.DB_INSERT_PLAYER, tableNames[0]);
		
		Queries.DB_INSERT_PLAYER_INVITED_TO_WORLD = String.format(QueriesRaw.DB_INSERT_PLAYER_INVITED_TO_WORLD, tableNames[2]);
				
		Queries.DB_SELECT_USERID_FROM_UUID = String.format(QueriesRaw.DB_SELECT_USERID_FROM_UUID, tableNames[0]);
		Queries.DB_SELECT_USERNAME_FROM_USERID = String.format(QueriesRaw.DB_SELECT_USERNAME_FROM_USERID, tableNames[0]);
		Queries.DB_SELECT_UUID_FROM_USERID = String.format(QueriesRaw.DB_SELECT_UUID_FROM_USERID, tableNames[0]);
		Queries.DB_SELECT_USERNAME_FROM_UUID = String.format(QueriesRaw.DB_SELECT_USERNAME_FROM_UUID, tableNames[0]);
		
		Queries.DB_SELECT_ACTIVE_INVITED_WORLDS = String.format(QueriesRaw.DB_SELECT_ACTIVE_INVITED_WORLDS, tableNames[2]);
		
		Queries.DB_UPDATE_INVITE_END_TIME = String.format(QueriesRaw.DB_UPDATE_INVITE_END_TIME, tableNames[2]);
		
		Queries.DB_ALTER_1 = String.format(QueriesRaw.DB_ALTER_1, tableNames[0]);
		
		createDatabaseTables();
	}
	
	@Override
	public DB_RETURN_CODE createDatabaseTables() 
	{
		Connection conn;
		
		if(dbTablePrefix == null)
			dbTablePrefix = "";
		
		try 
		{
			conn = getConnection();
			Statement stmt = conn.createStatement();
			
			/*if(stmt.execute(MySQLDatabase.DB_USERS_CREATION_STATEMENT))
			{
				if(stmt.execute(MySQLDatabase.DB_WORLDS_CREATION_STATEMENT))
				{
					if(stmt.execute(MySQLDatabase.DB_INVITED_PLAYERS_CREATION_STATEMENT))
					{
						stmt.close();
					}
					else
					{
						stmt.close();
						return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
					}
				}
				else
				{
					stmt.close();
					return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
				}
			}
			else 
			{
				stmt.close();
				return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
			}*/
			
			stmt.executeUpdate(Queries.DB_USERS_CREATION_STATEMENT);
			stmt.executeUpdate(Queries.DB_WORLDS_CREATION_STATEMENT);
			stmt.executeUpdate(Queries.DB_INVITED_PLAYERS_CREATION_STATEMENT);
			stmt.close();
			
			return DB_RETURN_CODE.SUCCESSFUL;
		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
		}
	}
	
	@Override
	public ArrayList<String> getPlayerOwnedWorlds(UUID playerUUID) 
	{
		Connection conn;
		
		try 
		{
			int internalPlayerUserID = getInternalUIDFromUUID(playerUUID);
			
			if(internalPlayerUserID == 0)
				return null;
			
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_PLAYER_OWNED_WORLDS);
			stmt.setInt(1, internalPlayerUserID);
			
			ResultSet resSet = stmt.executeQuery();
			ArrayList<String> worldNameList = new ArrayList<String>();
			
			while(resSet.next())
			{
				worldNameList.add(resSet.getString("WorldName"));
			}
			
			return worldNameList;
		}
		catch(SQLException e)
		{
			return null;
		}
	}

	@Override
	public HashMap<String, Integer> getPlayerInvitedWorlds(UUID playerUUID)
	{
		Connection conn;
		
		try 
		{
			int internalPlayerUserID = getInternalUIDFromUUID(playerUUID);
			
			if(internalPlayerUserID == 0)
				return null;
		
			
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_PLAYER_INVITED_WORLDS);
			stmt.setInt(1, internalPlayerUserID);
			
			ResultSet resSet = stmt.executeQuery();
			HashMap<String, Integer> worldNameAndUIDMap = new HashMap<String, Integer>();
			
			// NOTE DUPLICATE ENTERIES DO NOT GET ADDED TO THE HASHMAP!!!
			while(resSet.next())
			{
				worldNameAndUIDMap.put(resSet.getString("WorldName"), resSet.getInt("UserIDWorldOwner"));
			}
			
			return worldNameAndUIDMap;
		}
		catch(SQLException e)
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			return null;
		}
	}
	
	@Override
	public DB_RETURN_CODE insertPlayerOwnedWorld(UUID playerUUID, String worldName) 
	{
		Connection conn;
		
		try 
		{
			int internalPlayerUserID = getInternalUIDFromUUID(playerUUID);
			
			if(internalPlayerUserID == 0)
			{
				return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
			}
			
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_INSERT_PLAYER_WORLD);
			stmt.setString(1, worldName);
			stmt.setInt(2, internalPlayerUserID);
			stmt.executeUpdate();
			stmt.close();
			
			return DB_RETURN_CODE.SUCCESSFUL;
		}
		catch(SQLException e)
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
		}
	}
	
	@Override
	public DB_RETURN_CODE insertPlayerInvitedWorld(UUID playerOwnerUUID, String worldName, UUID playerInvitedUUID, long msLengthOfTime) 
	{
		Connection conn;
		
		try 
		{			
			int internalPlayerUserIDOwner = getInternalUIDFromUUID(playerOwnerUUID);
			if(internalPlayerUserIDOwner == 0)
				return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;

			int internalPlayerUserIDInvited = getInternalUIDFromUUID(playerInvitedUUID);
			if(internalPlayerUserIDInvited == 0)
				return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
			
			conn = getConnection();
			
			Boolean worldExists = false;
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_PLAYER_OWNED_WORLDS);
			stmt.setInt(1, internalPlayerUserIDOwner);
			ResultSet resSet = stmt.executeQuery();
			while(resSet.next())
			{
				// Need to do this as we need to close the statement but that also closes the results set.
				String worldNameItt = resSet.getString("WorldName");
				
				if(worldNameItt != null && worldName.equals(worldNameItt))
				{
					worldExists = true;
					break;
				}
				
			}
			
			// If they don't have a world called that, make sure we don't let them invite someone to a world called it!
			if(!worldExists)
				return DB_RETURN_CODE.WORLD_DOES_NOT_EXIST;

				
			Date date = new Date();
			Timestamp sqlDate = new Timestamp(date.getTime());
						
			
			
			stmt = conn.prepareStatement(Queries.DB_INSERT_PLAYER_INVITED_TO_WORLD);
			stmt.setInt(1, internalPlayerUserIDOwner);
			stmt.setString(2, worldName);
			stmt.setInt(3, internalPlayerUserIDInvited);
			stmt.setTimestamp(4, sqlDate);
			
			sqlDate.setTime(date.getTime() + msLengthOfTime);
			stmt.setTimestamp(5, sqlDate);

			stmt.executeUpdate();
			stmt.close();

			return DB_RETURN_CODE.SUCCESSFUL;
		}
		catch(SQLException e)
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
		}
	}
	
	@Override
	public DB_RETURN_CODE insertOrUpdatePlayer(UUID playerUUID, String userName) 
	{
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_INSERT_PLAYER);
			stmt.setString(1, playerUUID.toString());
			stmt.setString(2, userName);
			stmt.setString(3, userName);
			stmt.executeUpdate();
			stmt.close();
			
			return DB_RETURN_CODE.SUCCESSFUL;
		}
		catch(SQLException e)
		{
			return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
		}
	}
	
	public int getInternalUIDFromUUID(UUID playerUUID)
	{
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_USERID_FROM_UUID);
			stmt.setString(1, playerUUID.toString());
			ResultSet resSet = stmt.executeQuery();
			if(resSet.next())
			{
				// Need to do this as we need to close the statement but that also closes the results set.
				int internalPlayerUID = resSet.getInt(1);
				stmt.close();
				
				return internalPlayerUID;
			}
			else
			{
				stmt.close();
				return 0;
			}
		} 
		catch (SQLException e) 
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			
			return 0;
		}
		
	}
	
	public String getUserNameFromUUID(UUID playerUUID)
	{
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_USERNAME_FROM_UUID);
			stmt.setString(1, playerUUID.toString());
			ResultSet resSet = stmt.executeQuery();
			if(resSet.next())
			{
				// Need to do this as we need to close the statement but that also closes the results set.
				String strUserName = resSet.getString(1);
				stmt.close();
				
				return strUserName;
			}
			else
			{
				stmt.close();
				return "";
			}
		} 
		catch (SQLException e) 
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			
			return "";
		}
		
	}
	
	public String getUserNameFromInternalUID(int internalUID)
	{
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_USERNAME_FROM_USERID);
			stmt.setInt(1, internalUID);
			ResultSet resSet = stmt.executeQuery();
			if(resSet.next())
			{
				// Need to do this as we need to close the statement but that also closes the results set.
				String playerDisplayName = resSet.getString(1);
				stmt.close();
				
				return playerDisplayName;
			}
			else
			{
				stmt.close();
				return null;
			}
		} 
		catch (SQLException e) 
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			
			return null;
		}

	}
	
	public UUID getUUIDFromInternalUID(int internalUID) 
	{
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_UUID_FROM_USERID);
			stmt.setInt(1, internalUID);
			ResultSet resSet = stmt.executeQuery();
			if(resSet.next())
			{
				// Need to do this as we need to close the statement but that also closes the results set.
				String strPlayerUUID = resSet.getString(1);
				stmt.close();
				
				UUID playerUUID;
				
				if(strPlayerUUID != null && !(strPlayerUUID.equals("")))
					playerUUID = UUID.fromString(strPlayerUUID);
				else
					return null;
				
				return playerUUID;
			}
			else
			{
				stmt.close();
				return null;
			}
		} 
		catch (SQLException e) 
		{
			PrivateWorlds.plugin.getLogger().info(e.getMessage());
			
			return null;
		}
	}
	
	public int getInternalUIDFromUUIDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA(UUID playerUUID)
	{
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_SELECT_UUID_FROM_USERID);
			stmt.setString(1, playerUUID.toString());
			ResultSet resSet = stmt.executeQuery();
			if(resSet.next())
			{
				// Need to do this as we need to close the statement but that also closes the results set.
				int pInternalUID = resSet.getInt(1);
				stmt.close();
				
				//if(pInternalUID != 0)
					return pInternalUID;
				//else
				//	return -1;
				
			}
			else
			{
				stmt.close();
				return -1;
			}
		} 
		catch (SQLException e) 
		{
			PrivateWorlds.plugin.getLogger().warning(e.getMessage());
			return -1;
		}
	}
	
	public DB_RETURN_CODE updateInvitedWorldEndTime(int ownerPlayerUID, int invitedPlayerUID, String worldName, Timestamp newTime)
	{
		// Assume that this means expire the InviteEndTime field in the DB.
		if(newTime == null)
			newTime = new Timestamp(new Date().getTime());
		
		Connection conn;
		
		try 
		{
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(Queries.DB_UPDATE_INVITE_END_TIME);
			stmt.setTimestamp(1, newTime);
			stmt.setInt(2, ownerPlayerUID);
			stmt.setInt(3, invitedPlayerUID);
			stmt.setString(4, worldName);
			
			int sret = stmt.executeUpdate();

			if(sret > 0)
			{
				stmt.close();
				return DB_RETURN_CODE.SUCCESSFUL;
			}
			else
			{
				stmt.close();
				return DB_RETURN_CODE.WORLD_DOES_NOT_EXIST_OR_NOT_INVITED;
			}
			

		}
		catch(SQLException e)
		{
			return DB_RETURN_CODE.INTERNAL_SERVER_ERROR;
		}
	}
	
	public DB_RETURN_CODE performDataOperations(int dbTableVersionNum)
	{
		try
		{
			Connection conn = getConnection();
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(Queries.DB_ALTER_1); // If we have no SQL Exceptions, assume this query was successful.
			return DB_RETURN_CODE.SUCCESSFUL;
		}
		catch(SQLException e)
		{
			if(e.getMessage().contains("UUID") && e.getMessage().contains("key") && !e.getMessage().contains("for"))
			{
				return DB_RETURN_CODE.SUCCESSFUL; // Fix already set!
			}
			else
			{
				PrivateWorlds.plugin.getLogger().warning("SQL Exception when altering the database: " + e.getMessage());
				return DB_RETURN_CODE.REPORT_SQL_EXCEPTION;
			}
		}
		
	}
	
    private Connection getConnection() throws SQLException
    {
        if (connection == null || connection.isClosed() || !connection.isValid(1)) 
        {
        	connection = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);
        }
        
        return connection;
    }
    
	public static class Queries
	{
		
		public static String DB_USERS_CREATION_STATEMENT;
		
		public static String DB_WORLDS_CREATION_STATEMENT;
		public static String DB_INVITED_PLAYERS_CREATION_STATEMENT;
		
		public static String DB_SELECT_PLAYER_OWNED_WORLDS;
		public static String DB_SELECT_PLAYER_INVITED_WORLDS;
		
		public static String DB_INSERT_PLAYER_WORLD;
		public static String DB_INSERT_PLAYER_INVITED_TO_WORLD;
		public static String DB_INSERT_PLAYER;
		
		public static String DB_SELECT_USERID_FROM_UUID;
		public static String DB_SELECT_USERNAME_FROM_USERID;
		public static String DB_SELECT_UUID_FROM_USERID;
		public static String DB_SELECT_USERNAME_FROM_UUID;
			
		public static String DB_SELECT_ACTIVE_INVITED_WORLDS;
		
		public static String DB_UPDATE_INVITE_END_TIME;
		
		public static String DB_ALTER_1;
	}
    
	public static class QueriesRaw
	{
		public static final String DB_USERS_CREATION_STATEMENT = "CREATE TABLE IF NOT EXISTS `%s` ( `UserID` int(11) unsigned NOT NULL AUTO_INCREMENT, `UUID` varchar(50) NOT NULL, `PlayerName` varchar(50) NOT NULL, `NumOfWorldsAllowed` int(11) DEFAULT NULL, PRIMARY KEY (`UserID`,`UUID`), UNIQUE KEY `UUID` (`UUID`) USING BTREE, KEY `UserID` (`UserID`));";
		public static final String DB_WORLDS_CREATION_STATEMENT = "CREATE TABLE IF NOT EXISTS `%s` ( `WorldID` int(11) unsigned NOT NULL AUTO_INCREMENT, `WorldName` varchar(50) NOT NULL, `UserIDWorldOwnedBy` int(11) unsigned NOT NULL, `WorldBorderRadius` int(11) DEFAULT NULL, PRIMARY KEY (`WorldID`), KEY `FK_%s_UserID` (`UserIDWorldOwnedBy`), CONSTRAINT `FK_%s_UserID` FOREIGN KEY (`UserIDWorldOwnedBy`) REFERENCES `%s` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE)";
		public static final String DB_INVITED_PLAYERS_CREATION_STATEMENT = "CREATE TABLE IF NOT EXISTS `%s` (`ID` int(11) unsigned NOT NULL AUTO_INCREMENT, `UserIDWorldOwner` int(11) unsigned NOT NULL, `WorldName` varchar(50) NOT NULL, `UserIDInvitedPlayer` int(11) unsigned NOT NULL, `InviteStartTime` datetime NOT NULL, `InviteEndTime` datetime NOT NULL, PRIMARY KEY (`ID`), KEY `FK_%s_InvitedUserID` (`UserIDInvitedPlayer`), KEY `FK_%s_OwnerUserID` (`UserIDWorldOwner`), CONSTRAINT `FK_%s_OwnerUserID` FOREIGN KEY (`UserIDWorldOwner`) REFERENCES `%s` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT `FK_%s_InvitedUserID` FOREIGN KEY (`UserIDInvitedPlayer`) REFERENCES `%s` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE )";
		
		public static final String DB_SELECT_PLAYER_OWNED_WORLDS = "SELECT * FROM `%s` WHERE `UserIDWorldOwnedBy` = ?;";
		public static final String DB_SELECT_PLAYER_INVITED_WORLDS = "SELECT * FROM `%s` WHERE UserIDInvitedPlayer = ? AND InviteEndTime > NOW()";
		
		public static final String DB_INSERT_PLAYER_WORLD = "INSERT INTO `%s` ( WorldName, UserIDWorldOwnedBy, WorldBorderRadius  ) VALUES ( ?, ?, NULL );";
		public static final String DB_INSERT_PLAYER_INVITED_TO_WORLD = "INSERT INTO `%s` ( UserIDWorldOwner, WorldName, UserIDInvitedPlayer, InviteStartTime, InviteEndTime ) VALUES ( ?, ?, ?, ?, ? );";
		public static final String DB_INSERT_PLAYER = "INSERT INTO `%s` ( UUID, PlayerName ) VALUES (?, ?) ON DUPLICATE KEY UPDATE `PlayerName` = ?;";
		
		public static final String DB_SELECT_USERID_FROM_UUID = "SELECT `UserID` FROM `%s` WHERE UUID = ?";
		public static final String DB_SELECT_USERNAME_FROM_USERID = "SELECT `PlayerName` FROM `%s` WHERE UserID = ?";
		public static final String DB_SELECT_UUID_FROM_USERID = "SELECT `UUID` FROM `%s` WHERE UserID = ?";
		public static final String DB_SELECT_USERNAME_FROM_UUID = "SELECT `PlayerName` FROM `%s` WHERE UUID = ?";
		
		public static final String DB_SELECT_ACTIVE_INVITED_WORLDS = "SELECT * FROM `%s` WHERE UserIDInvitedPlayer = ? AND InviteEndTime < NOW();";
		
		public static final String DB_UPDATE_INVITE_END_TIME = "UPDATE `%s` SET InviteEndTime = ? WHERE UserIDWorldOwner = ? AND UserIDInvitedPlayer = ? AND WorldName = ?";
		
		public static final String DB_ALTER_1 = "ALTER TABLE `%s` ADD UNIQUE INDEX `UUID` (`UUID`) USING BTREE;";
	}

}
