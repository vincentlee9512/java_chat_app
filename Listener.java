import java.net.*;
import java.io.*;

abstract public class Listener implements Runnable
{
/*
	Vincent Li
	May 2, 2017

	This class will provide the processing of the Listener.

	Instance Variable:
		multicastSocket
			a multicastSocket that connected to the server.

	Constructor:
		Listener(MulticastSocket multicastSocket)
			creates a Listener object sets to the value of the parameter.

	Methods:
		public void run()
			an infinite loop that keeps receiving DatagramPacket from the socket and then callsthe abstract method processPacket.

		public abstract void(DatagramPacket packet)
*/

	private MulticastSocket multicastSocket;

	public Listener(MulticastSocket multicastSocket)
	{
		if(multicastSocket==null){throw new IllegalArgumentException("The multicastSocket is null.");}


		this.multicastSocket=multicastSocket;
	}


	public void run()
	{
		byte[] buffer;
		DatagramPacket packet;

		buffer=new byte[10000];
		packet=new DatagramPacket(buffer,buffer.length);


		while(Resources.getInstance().getKeepRunning())
		{


			try
			{
				this.multicastSocket.receive(packet);

				processPacket(packet);

			}
			catch(IOException ioe)
			{throw new RuntimeException("Failed to receive packet.");}


		}//loop
	}//run

	public abstract void processPacket(DatagramPacket packet);


}