import java.net.*;
import java.io.*;

public class CipherServer extends Server
{
/*
	Vincent Li
	May 3,2017

	This class will provide the processing of the CipherServer. Extending the Server class.

	Instance Variable:
		cipherList
			a CipherList object that contains Cipher(codeBooks) ready to send to other users.

		serverInfo
			a ServerInfo object that contiains user's own information.

	Constructor:
		public CipherServer(InetAddress ipNumber, int portNumber, CipherList cipherList)
			creates a new CipherServer oabject sets to the value of the parameter.

	Method:
		public void processRequest(Socket socket)
			Overriding the processRequest Method from the super class. Receiving the CipherRequest Object and call its sendResponse method.

		public void run()
			Overriding the processRequest Method from the super class. Use thread to start the annunciator to send helloMessage and calls the run method from the super class.


*/

	private CipherList cipherList;

	private ServerInfo serverInfo;

	public CipherServer(InetAddress ipNumber, int portNumber, CipherList cipherList)
	{
		super(ipNumber,portNumber);

		if(cipherList==null){throw new IllegalArgumentException("The cipherList is null.");}

		this.cipherList=cipherList;

		this.serverInfo=new ServerInfo(Resources.getInstance().getUserName(),new ServerAddress(Resources.getInstance().getServerIPNumber(),Resources.getInstance().getServerPortNumber()));

	}//constructor

	@Override
	public void processRequest(Socket socket)
	{
		CipherRequest request;
		ObjectInputStream in;

		try
		{
			in=new ObjectInputStream(socket.getInputStream());

			request=(CipherRequest)in.readObject();

			request.sendResponse(socket,this.cipherList);
		}//try
		catch(IOException ioe)
		{System.out.println("socket is " + socket);
			ioe.printStackTrace();}
		catch(ClassNotFoundException cnfe)
		{System.out.println("socket is " + socket);
			cnfe.printStackTrace();}

	}//processRequest

	public void run()
	{
		Annunciator sender;
		Hello helloMessage;
		Goodbye goodbyeMessage;

		Thread thread;

		try
		{
			helloMessage=new Hello(this.serverInfo,Resources.getInstance().getHelloGoodbyeMulticastSocket());
			sender=new Annunciator(5000,helloMessage);
			thread=new Thread(sender);

			//helloMessage.run();

			thread.start();

			super.run();

			goodbyeMessage=new Goodbye(this.serverInfo,Resources.getInstance().getHelloGoodbyeMulticastSocket());

			goodbyeMessage.send();
		}//try

		catch(IOException ioe)
		{ioe.printStackTrace();}


	}//run
}//class