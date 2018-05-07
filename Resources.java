import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.atomic.*;

public class Resources
{
/*
	Vincent Li
	April 26,2017

	This class is singleton, will provide the processing of the Rources.

	Instance Variable:
		PROPERTIES_FILE_PATH
			contains the path of the property file.

		codeBooks
			a File object that contains file(s) from the path in the property file.

		serverIPNumber
			an InetAddress that contains the IP number for the machine which we are using.

		serverPortNumber
			an int that contains the port number for the machine which we are using.

		helloGoodbyeGroup
			an InetAddress that contains the IP number for the helloGoodbye message multicastSocket.

		helloGoodbyePortNumber
			an int that contains the port number for the helloGoodbye message multicastSocket.

		helloGoodbyeMulticastSocket
			a MulticastSocket that created by helloGoodbyePortNumber and join the Group of helloGoodbyeGroup.

		cipherMessageGroup
			an InetAddress that contains the IP number for the cipher message multicastSocket.

		cipherMessagePortNumber
			an int that containsthat port number for the cipher message multicastSocket.

		cipherMessageMulticastSocket
			a MulticastSocket that creatsd by cipherMessagePortNumber and join the Group of CipherMessageGroup.

		userName
			an UserName object that contains the (nick)name of the user.

		keepRunning
			an AtomicBoolean object would control the whole system to start or stop.

		instance
			recreates the singleton object.

	Constructor:
		Resource()
			this constructor is private, it protects this object and initializes the instance variables.

	Method:
		public static Resources getInstance()
			returns the Resources object that contains the initial informations.

		public File getCodeBooks()
			the accessor to the codeBooks.

		public InetAddress getServerIPNumber()
			the accessor to the serverIPNumber.

		public int getServerPortNumber()
			the accessor to the serverPortNumber.

		public InetAddress getHelloGoodbyeGroup()
			the accessor to the helloGoodbyeGroup.

		public int getHelloGoodbyePortNumber()
			the accessor to the helloGoodbyePortNumber.

		public MulticastSocket getHelloGoodbyeMulticastSocket()
			the accessor to the helloGoodbyeMulticastSocket.

		public InetAddress getCipherMessageGroup()
			the accessor to the cipherMessageGroup.

		public int getCipherMessagePortNumber()
			the accessor to the cipherMessagePortNumber.

		public MulticastSocket getCipherMessageMulticastSocket()
			the accessor to the cipherMessageMulticastSocket.

		public UserName getUserName()
			the accessor to the userName.

		public boolean getKeepRunning()
			returns the value of the AtomicBoolean instance variable.

		public void setKeepRunning(boolean value)
			sets the value of the AtomicBoolean to the value in the parameter.
*/

	private static final String PROPERTIES_FILE_PATH="C:/Users/li000053/OneDrive/CISC 230/server/cipher.properties";

	private File codeBooks;

	private InetAddress serverIPNumber;

	private int serverPortNumber;

	private InetAddress helloGoodbyeGroup;

	private int helloGoodbyePortNumber;

	private MulticastSocket helloGoodbyeMulticastSocket;

	private InetAddress cipherMessageGroup;

	private int cipherMessagePortNumber;

	private MulticastSocket cipherMessageMulticastSocket;

	private UserName userName;

	private AtomicBoolean keepRunning;

	private static final Resources instance=new Resources();

	private Resources()
	{
		Properties properties;

		properties=new Properties();

/////loading properties file
		try{properties.load(new FileReader(PROPERTIES_FILE_PATH));}
		catch(IOException ioe)
		{throw new RuntimeException("The userInfo properties file does not exist.");}


/////ipNumberOfMultiSocket
		try
		{
			if((properties.getProperty("ipNumberOfMultiSocket")==null)||(properties.getProperty("ipNumberOfMultiSocket").length()==0))
			{
				throw new IllegalArgumentException("The ipNumberOfMultiSocket in properties' file contains nothing.");
			}

			this.helloGoodbyeGroup=InetAddress.getByName(properties.getProperty("ipNumberOfMultiSocket"));
		}

		catch(UnknownHostException uhe)
		{throw new RuntimeException("The helloGoodbyeGroup ipNumber is not valid.");}


/////portNumberOfMultiSocket
		try
		{
			if((properties.getProperty("portNumberOfMultiSocket")==null)||(properties.getProperty("portNumberOfMultiSocket").length()==0))
			{
				throw new IllegalArgumentException("The portNumberOfMultiSocket in properties' file contains nothing.");
			}
			this.helloGoodbyePortNumber=Integer.parseInt(properties.getProperty("portNumberOfMultiSocket"));
		}

		catch(NumberFormatException nfe)
		{throw new RuntimeException("The helloGoodbyeGroup port number is not valid.");}


/////serverIPNumber
		try
		{
			if((properties.getProperty("ipNumber")==null)||(properties.getProperty("ipNumber").length()==0))
			{
				throw new IllegalArgumentException("The ipNumber in properties' file contains nothing.");
			}
			this.serverIPNumber=InetAddress.getByName(properties.getProperty("ipNumber"));
		}

		catch(UnknownHostException uhe)
		{throw new RuntimeException("The Server ipNumber is not valid.");}


/////serverPortNumber
		try
		{
			if((properties.getProperty("portNumber")==null)||(properties.getProperty("portNumber").length()==0))
			{
				throw new IllegalArgumentException("The portNumber in properties' file contains nothing.");
			}
			this.serverPortNumber=Integer.parseInt(properties.getProperty("portNumber"));
		}

		catch(NumberFormatException ufe)
		{throw new RuntimeException("The Server port number is not valid.");}


/////userName
		if(properties.getProperty("Name")==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}

		this.userName=new UserName(properties.getProperty("Name"));


/////creating multicastSocket for helloGoodbye
		try
		{
			this.helloGoodbyeMulticastSocket=new MulticastSocket(this.helloGoodbyePortNumber);
			this.helloGoodbyeMulticastSocket.joinGroup(this.helloGoodbyeGroup);
		}
		catch(IOException ioe)
		{throw new RuntimeException("Can not create the MulticastSocket for HelloGoodbyeMessage.");}


/////CipherMessageGroup
		try
		{
			if((properties.getProperty("ipNumberOfCipherMessage")==null)||(properties.getProperty("ipNumberOfCipherMessage").length()==0))
			{
				throw new IllegalArgumentException("The ipNumberOfCipherMessage in properties' file contains nothing.");
			}
			this.cipherMessageGroup=InetAddress.getByName(properties.getProperty("ipNumberOfCipherMessage"));
		}
		catch(UnknownHostException uhe)
		{throw new RuntimeException("The Cipher Message ipNumber is not valid.");}


/////CipherMessagePortNumber
		try
		{
			if((properties.getProperty("portNumberOfCipherMessage")==null)||(properties.getProperty("portNumberOfCipherMessage").length()==0))
			{
				throw new IllegalArgumentException("The portNumber for CipherMessage in properties' file contains nothing.");
			}
			this.cipherMessagePortNumber=Integer.parseInt(properties.getProperty("portNumberOfCipherMessage"));
		}

		catch(NumberFormatException ufe)
		{throw new RuntimeException("The Server port number is not valid.");}


/////CipherMessageMulticastSocket
		try
		{
			this.cipherMessageMulticastSocket=new MulticastSocket(this.cipherMessagePortNumber);
			this.cipherMessageMulticastSocket.joinGroup(this.cipherMessageGroup);
		}
		catch(IOException ioe)
		{throw new RuntimeException(ioe.getMessage());}

/////AtomicBoolean keepRunning
		this.keepRunning=new AtomicBoolean(true);

/////CodeBooks

		if((properties.getProperty("codeBookPath")==null)||(properties.getProperty("codeBookPath").length()==0))
		{throw new IllegalArgumentException("The codeBookPath in properties file contains nothing.");}

		this.codeBooks=new File(properties.getProperty("codeBookPath"));



	}//constructor

	public static Resources newInstance(){return Resources.getInstance();}

	public static Resources getInstance(){return Resources.instance;}

	public File getCodeBookPath(){return this.codeBooks;}

	public InetAddress getServerIPNumber(){return this.serverIPNumber;}

	public int getServerPortNumber(){return this.serverPortNumber;}

	public InetAddress getHelloGoodbyeGroup(){return this.helloGoodbyeGroup;}

	public int getHelloGoodbyePortNumber(){return this.helloGoodbyePortNumber;}

	public MulticastSocket getHelloGoodbyeMulticastSocket(){return this.helloGoodbyeMulticastSocket;}

	public InetAddress getCipherMessageGroup(){return this.cipherMessageGroup;}

	public int getCipherMessagePortNumber(){return this.cipherMessagePortNumber;}

	public MulticastSocket getCipherMessageMulticastSocket(){return this.cipherMessageMulticastSocket;}

	public UserName getUserName(){return this.userName;}

	public boolean getKeepRunning(){return this.keepRunning.get();}

	public void setKeepRunning(boolean value){this.keepRunning.set(value);}


}//class