import java.io.*;
import java.net.*;

public class CipherSystemController
{
/*
	Vincent Li
	May 3,2017

	This class will provide the processing of the CipherSystemController.

	Instance Variable:
		cipherServer
			a CipherServer Object that using to run the system.

		helloGoodbyeListener
			a helloGoodbyeListener object that using for receiving the helloGoodbyeMessage.

	Constructor:
		Creates a new CipherSystemController sets to the value in the Resources class.

	Method:
		public void sendMessage(UserName[] to,String message)
			takes userNames and String message, sending encrypting message to every users in the userName array.

		public void quit()
			calls the stopServer method in the cipherServer class and set the atomicBoolean to false. At the end, send a goodbyeMessage to local server to activate the cipherMessageListener.

		public void run()
			starts the CipherServer, CipherMessageListener and HelloGoodbyeListener to run the whole system. Then call the GUI's show method to display the server.

		public static void main()
			activates the system.
*/

	private CipherServer cipherServer;

	HelloGoodbyeListener helloGoodbyeListener;

	public CipherSystemController()
	{
		CipherList cipherList;

		cipherList=new CipherList();

		try{cipherList.loadCodeBooks(Resources.getInstance().getCodeBookPath());}
		catch(IOException ioe)
		{ioe.printStackTrace();}

		this.cipherServer=new CipherServer(Resources.getInstance().getServerIPNumber(),Resources.getInstance().getServerPortNumber(),cipherList);

		this.helloGoodbyeListener=new HelloGoodbyeListener();
	}

	public void sendMessage(UserName[] to,String message)
	{
		if((to==null)||(to.length==0)){throw new IllegalArgumentException("The UserName array is null or contains nothing.");}

		for(int i=0;i<to.length;i++)
		{
			if((to[i]==null)||(to[i].getUserName().length()==0))
			{throw new IllegalArgumentException("(sendMessage)Some userName is not valid.");}
		}

		if(message==null){throw new IllegalArgumentException("The message is null.");}

		byte[] cipherBytes;
		byte[] messageBytes;
		ByteArrayInputStream byteIn;
		ByteArrayOutputStream byteOut;
		BealeCipherBytes encryptingCipher;
		DatagramPacket packet;
		int cipherMessagePortNumber;
		InetAddress cipherMessageAddress;
		Message messageObject;
		MulticastSocket cipherMessageSocket;
		StringWriter writer;
		String cipherText;
		ObjectOutputStream objectOut;
		UserDirectory ud;

		messageObject=new Message(message);

		ud=UserDirectory.getInstance();

		try
		{
			byteOut=new ByteArrayOutputStream();
			objectOut=new ObjectOutputStream(byteOut);

			objectOut.writeObject(messageObject);
			messageBytes=byteOut.toByteArray();
		}
		catch(IOException ioe)
		{throw new RuntimeException("Can not convert message to byte array.");}


		byteIn=new ByteArrayInputStream(messageBytes);


		cipherMessageSocket=Resources.getInstance().getCipherMessageMulticastSocket();


		for(int i=0;i<to.length;i++)
		{

			byteIn=new ByteArrayInputStream(messageBytes);

			writer=new StringWriter();

			encryptingCipher=(BealeCipherBytes)ud.getEncryptingCipher(to[i]);

			if(encryptingCipher!=null)
			{
				encryptingCipher.encrypt(byteIn,writer);

				cipherText=writer.toString();

				cipherBytes=cipherText.getBytes();

				cipherMessageAddress=Resources.getInstance().getCipherMessageGroup();

				cipherMessagePortNumber=Resources.getInstance().getCipherMessagePortNumber();

				packet=new DatagramPacket(cipherBytes,cipherBytes.length,cipherMessageAddress,cipherMessagePortNumber);


				try{cipherMessageSocket.send(packet);}
				catch(IOException ioe)
				{throw new RuntimeException("Can not send the cipherBytes.");}
			}

		}//loop



	}//send




	public void quit()
	{
		UserName[] myself;

		myself=new UserName[1];

		myself[0]=Resources.getInstance().getUserName();

		this.cipherServer.stopServer();

		Resources.getInstance().setKeepRunning(false);

		sendMessage(myself,"bye");




		//call teh stopServer from Server class and sleep(5000)
	}

	public void run()
	{
		Thread threadServer;
		Thread threadHGListener;
		Thread threadCipherListener;
		GUIForCipherSystem gcs;
		CipherMessageListener messageListener;

		messageListener=new CipherMessageListener();

		threadServer=new Thread(cipherServer);
		threadHGListener=new Thread(helloGoodbyeListener);
		threadCipherListener= new Thread(messageListener);


		threadServer.start();

		threadHGListener.start();

		threadCipherListener.start();



		gcs=new GUIForCipherSystem(this);

		gcs.show();

	}

	public static void main(String[] args)
	{
		CipherSystemController csc;

		csc=new CipherSystemController();

		csc.run();

	}
}