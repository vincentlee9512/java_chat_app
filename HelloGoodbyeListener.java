import java.net.*;
import java.io.*;

public class HelloGoodbyeListener extends Listener
{
/*
	Vincent Li
	May 2,2017

	This class will provide the processing of the HelloGoodbyeListener. Extending the Listener class.

	Constructor:
		creates a new HelloGoodbye object by taking the helloGoodbyeMulticastSocket from the Resources class.

	Method:
		public void processPacket(DatagramPacket datagramPacket)
			this method will be call at the run() method. Getting helloGoodbye object from packet and call its run method.
*/

	public  HelloGoodbyeListener()
	{
		super(Resources.getInstance().getHelloGoodbyeMulticastSocket());
	}

	public void processPacket(DatagramPacket datagramPacket)
	{
		if(datagramPacket==null){throw new IllegalArgumentException("The packet is null.");}

		ByteArrayInputStream byteIn;
		ObjectInputStream objectIn;
		HelloGoodbye target;

		try
		{
			byteIn=new ByteArrayInputStream(datagramPacket.getData(),0,datagramPacket.getData().length);
			objectIn=new ObjectInputStream(byteIn);

			target=(HelloGoodbye)objectIn.readObject();

			target.run();

		}
		catch(IOException ioe)
		{ioe.printStackTrace();}
		catch(ClassNotFoundException cnfe)
		{cnfe.printStackTrace();}



	}

}