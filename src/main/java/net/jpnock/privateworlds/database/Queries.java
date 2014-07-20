package net.jpnock.privateworlds.database;

public class Queries 
{
	public class MySQL
	{
		public static final String DB_USERS_CREATION_STATEMENT = "CREATE TABLE `PW_Users` ( `UUID` varchar(50) NOT NULL, `PlayerName` varchar(50) NOT NULL, `NumOfWorldsAllowed` int NULL, PRIMARY KEY (`UUID`) );";
		public static final String DB_WORLDS_CREATION_STATEMENT = "CREATE TABLE `PW_Worlds` ( `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, `WorldName` varchar(50) NOT NULL, `UUIDWorldOwnedBy` varchar(50) NOT NULL, `WorldBorderRadius` int(11) DEFAULT NULL,  PRIMARY KEY (`ID`), KEY `FK_UUID` (`UUIDWorldOwnedBy`), CONSTRAINT `FK_UUID` FOREIGN KEY (`UUIDWorldOwnedBy`) REFERENCES `PW_Users` (`UUID`) ON DELETE CASCADE ON UPDATE CASCADE);";
		// Add in another table for all the people who have been invited to worlds with ID WorldName and InvitedUUID
	}
}
