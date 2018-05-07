import java.net.*;
import java.io.*;

public class Goodbye extends HelloGoodbye
{
/*
	Vincent Li
	April 25,2017

	This class will provide the processing of the Goodbye.

	Instance Variable:
		serialVersionUID
			this variable allows this object to be serializable.

	Constructor:
		Goodbye(ServerInfo serverInfo,MulticastSocket multicastSocket)throws IOException
			creates a Goodbye object sets to the values of the parameter.

	Method:
		public void run()
			prints the information from the serverInfo and remove the object from UserDirectory's instance.

*/

	private static final long serialVersionUID=1;

	public Goodbye(ServerInfo serverInfo,MulticastSocket multicastSocket)throws IOException
	{
		super(serverInfo,multicastSocket);

		if(serverInfo==null)
		{throw new IllegalArgumentException("The serverInfo is null.");}

		if(multicastSocket==null)
		{throw new IllegalArgumentException("The multicastSocket is null.");}

	}//constructor

	public void run()
	{
		super.run();

		UserDirectory ud;
		ud=UserDirectory.getInstance();
		ud.remove(this);
	}

}//class