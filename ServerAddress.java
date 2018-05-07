import java.net.*;
import java.io.*;

public class ServerAddress implements Serializable
{
/*
	Vincent Li
	April 26,2017

	This class will provide the processing of the ServerAddress.

	Instance Variable:
		address
			an InetAddress that contains the serverIPNumber.

		portNumer
			an int that contains the serverPortNumber.

		serialVersionUID
			this variable allows this object to be serializable.

	Constructor:
		ServerAddress(InetAddress ipNumber,int portNumber)
			creates a new ServerAddress set to the values of the parameters.

	Methods:
		public InetAddress getAddress()
			the accessor to the address;

		public int getPortNumber()
			the accessor to the portNumber;

		public voidconnect()
			returns a socket that connects with this IPNumber and port number.

		public String toString()
			returns a string to describe this object.

*/

	private static final long serialVersionUID=1;

	private InetAddress address;

	private int portNumber;

	public ServerAddress(InetAddress ipNumber,int portNumber)
	{
		if(ipNumber==null){throw new IllegalArgumentException("The ipNumber is null.");}

		if((portNumber<0)&&(portNumber>65535)){throw new IllegalArgumentException("the portNumber should be in range of 0 to 65535.");}

		this.portNumber=portNumber;

		this.address=ipNumber;

	}//construtor


	public InetAddress getAddress(){return this.address;}

	public int getPortNumber(){return this.portNumber;}

	public Socket connect()
	{
		Socket socket;

		try
		{
			socket=new Socket(getAddress(),getPortNumber());

			return socket;
		}

		catch(IOException ioe)
		{throw new RuntimeException(ioe.getMessage());}


	}

	public String toString()
	{
		String result;

		result="InetAddress: "+getAddress()+"\n"
				+"PortNumber: "+getPortNumber()+"\n";
		return result;
	}



}//class