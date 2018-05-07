import java.io.*;
import java.net.*;

public class CipherRequest implements Serializable
{
/*
	Vincent Li
	May 4,2017

	Instance Variable:
		serialVersionUID
			this variable allows this object to be serializable

		serverInfoOfRequester
			a ServerInfo object that contains the information from the requester.

	Constructor:
		public CipherRequest(ServerInfo serverInfoOfRequester)
			creates a new CipherRequest object sets to the value of the parameter.

	Method:
		public void sendRequestTo(Hello helloMessage)
			this class will be called in the hello class. This call will take the helloMessage when it is called and send it to the helloGoodbyeMulticastSocket.

		public void sendRespone(Socket socket,CipherList cipherList)
			this class will be called in the CipherServer call. When the server receive the CipherRequest class, then send the cipher to the requester.

*/
	private static final long serialVersionUID=1;

	private ServerInfo serverInfoOfRequester;

	public CipherRequest(ServerInfo serverInfoOfRequester)
	{
		if(serverInfoOfRequester==null)
		{
			throw new IllegalArgumentException("The serverInfoOfRequester is null.");
		}

		this.serverInfoOfRequester=serverInfoOfRequester;

	}//constructor

	public void sendRequestTo(Hello helloMessage)//throws IOException,ClassNotFoundException
	{
		Cipher cipher;
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;

		try
		{
			if(helloMessage==null){throw new IllegalArgumentException("The helloMessage is null.");}

			socket=helloMessage.getServerInfo().getServerAddress().connect();


			out=new ObjectOutputStream(socket.getOutputStream());

			out.writeObject(this);



			in=new ObjectInputStream(socket.getInputStream());

			cipher=(Cipher)in.readObject();

			UserDirectory.getInstance().updateDecryptingCipher(helloMessage.getServerInfo().getUserName(),cipher);

		}
		catch(IOException ioe)
		{ioe.printStackTrace();}
		catch(ClassNotFoundException cnfe)
		{throw new RuntimeException(cnfe.getMessage());}


	}

	public void sendResponse(Socket socket,CipherList cipherList)//throws IOException
	{
		UserDirectory ud;
		UserName[] userNameSet;
		ObjectOutputStream out;
		Cipher cipher;

		if(socket==null){throw new IllegalArgumentException("The socket is null.");}

		//if(!socket.isConnected()){throw new IllegalArgumentException("The socket is not connected with any InetAddress or portNumber.");}

		if(cipherList==null){throw new IllegalArgumentException("The cipherList is null.");}

		try
		{
			ud=UserDirectory.getInstance();
			userNameSet=ud.getUserNames();
			out=new ObjectOutputStream(socket.getOutputStream());

			cipher=ud.getEncryptingCipher(this.serverInfoOfRequester.getUserName());

			if(cipher==null)
			{
				cipher=cipherList.remove(Position.RANDOM);
				ud.updateEncryptingCipher(this.serverInfoOfRequester.getUserName(),cipher);
				out.writeObject(cipher);
			}

			else
			{
				out.writeObject(cipher);
			}
		}
		catch(IOException ioe)
		{throw new RuntimeException("(CipherRequests)fail to write Cipher in sendResponse.");}


	}//sendRespond
}//class