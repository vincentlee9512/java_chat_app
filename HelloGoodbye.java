import java.io.*;
import java.net.*;
import java.util.*;

public abstract class HelloGoodbye implements Runnable,Serializable
{
/*
	Vincent Li
	April 25,2017

	This class will provide the processing of the HelloGoodbye.

	Instance Variable:
		datagramPacket
			a DatagramPacket object that contains the data for the whole object. Marked transient because we don't need this term in the other end.

		multicastSocket
			a MulticastSocket object connected to the a specific IP number and port number. Marked transient because it is impossible to serialize a multicastSocket.

		serverInfo
			a ServerInfo object contains the userName, user's IP number and user's portNumber of the user.

		timeStamp
			a Date object to help us to track time.

		serialVersionUID
			this variable allows this object to be serializable.

	Constructor:
		HelloGoodbye(ServerInfo serverInfo,MulticastSocket multicastSocket)throws IOException
			abstract constructor.

	Method:
		public ServerInfo getServerInfo()
			the accessor to the serverInfo.

		public Date getTimeStamp()
			the accessor to the timeStamp.

		public void run()
			prints the information from the serverInfo.

		public void send() throw IOException
			sending the datagramPacket by using the multicastSocket.

*/

	private static final long serialVersionUID=1;

	private transient DatagramPacket datagramPacket;

	private transient MulticastSocket multicastSocket;

	private ServerInfo serverInfo;

	private Date timeStamp;


	public HelloGoodbye(ServerInfo serverInfo,MulticastSocket multicastSocket)throws IOException
	{
		byte[] buffer;
		ByteArrayOutputStream x;
		ObjectOutputStream y;

		if(serverInfo==null){throw new IllegalArgumentException("The serverInfo is null.");}

		if(multicastSocket==null){throw new IllegalArgumentException("The multicastSocket is null.");}

		this.serverInfo=serverInfo;

		this.multicastSocket=multicastSocket;

		this.timeStamp=new Date();


		x = new ByteArrayOutputStream();
		y = new ObjectOutputStream(x);
		y.writeObject(this);
		buffer = x.toByteArray();

        this.datagramPacket = new DatagramPacket(buffer,buffer.length,Resources.getInstance().getHelloGoodbyeGroup(),Resources.getInstance().getHelloGoodbyePortNumber());

	}

	public ServerInfo getServerInfo(){return this.serverInfo;}

	public Date getTimeStamp(){return this.timeStamp;}

	public void run()
	{
		System.out.println(this.getClass());

		System.out.println("UserName: "+getServerInfo().getUserName());

		System.out.println("IPNumber: "+getServerInfo().getServerAddress().getAddress());

		System.out.println("Port Number: "+getServerInfo().getServerAddress().getPortNumber());

		System.out.println("Time: "+getTimeStamp());
	}

	public void send()throws IOException {this.multicastSocket.send(this.datagramPacket);}


}//class