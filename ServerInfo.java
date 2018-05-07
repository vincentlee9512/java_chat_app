import java.io.*;

public class ServerInfo implements Serializable
{
/*
	Vincent Li
	April 26,2017

	This class will provide the processing of the ServerInfo.

	Instance Variable:
		userName
			the UserName that contains the user's name.

		serverAddress
			the ServerAddress that contains the serverIPNumber and serverPortNumber.

	Constructor:
		ServerInfo(UserName userName,ServerAddress serverAddress)
			creates a ServerInfo object set to the values of the parameters.

	Methods:
		public UserName getUserName()
			the accessor to the userName.

		public ServerAddress getServerAddress()
			the accessor to the serverAddress.

		public String toString()
			returns the description of the object.
*/

	private static final long serialVersionUID=1;

	private UserName userName;

	private ServerAddress serverAddress;

	public ServerInfo(UserName userName,ServerAddress serverAddress)
	{
		if(userName==null){throw new IllegalArgumentException("The serverName is null.");}

		if(serverAddress==null){throw new IllegalArgumentException("The serverAddress is null");}

		this.userName=userName;
		this.serverAddress=serverAddress;
	}

	public UserName getUserName(){return this.userName;}

	public ServerAddress getServerAddress(){return this.serverAddress;}

	public String toString()
	{
		return "UserName: "+this.userName.getUserName()+"\n"+
				"ServerAddress: "+this.serverAddress.toString();
	}
}//class