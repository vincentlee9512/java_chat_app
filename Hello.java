import java.net.*;
import java.io.*;

public class Hello extends HelloGoodbye
{
/*
	Vincent Li
	April 25,2017

	This class will provide the processing of the Hello.

	Instance Variable:
		serialVersionUID
			this variable allows this object to be serializable.

	Constructor:
		Hello(ServerInfo serverInfo,MulticastSocket multicastSocket)throws IOException
			creates a Hello object sets to the values of the parameter.

	Method:
		public void run()
			prints the information from the serverInfo and add the object into UserDirectory's instance.
			Then, it will check is there a cipher for the user who sent this hello message. If no, creates and sends a CipherRequest to that user.

*/
	private static final long serialVersionUID=1;

	public Hello(ServerInfo serverInfo,MulticastSocket multicastSocket)throws IOException
	{
		super(serverInfo,multicastSocket);

		if(serverInfo==null){throw new IllegalArgumentException("The serverInfo is null.");}

		if(multicastSocket==null){throw new IllegalArgumentException("The multicastSocket is null.");}



	}

	public void run()
	{
		super.run();

		CipherRequest request;
		Resources r;
		UserDirectory ud;
		UserName userName;
		ServerInfo myInfo;

		r=Resources.getInstance();
		userName=this.getServerInfo().getUserName();

		ud=UserDirectory.getInstance();
		ud.add(this);
		myInfo=new ServerInfo(r.getUserName(),new ServerAddress(r.getServerIPNumber(),r.getServerPortNumber()));

		if(ud.getDecryptingCipher(userName)==null)
		{
			request=new CipherRequest(myInfo);
			request.sendRequestTo(this);


		}

	}
}//class
