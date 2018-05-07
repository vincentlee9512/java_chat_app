import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.*;

public abstract class Server implements Runnable
{
/*
	Vincent Li
	April 26, 2017

	Instance variable:
		serverIPNumber
			an InetAddress that contains the IP number for the server.

		serverSocket:
			a serverSocket that creates by the serverIPNumber and portNumber in parameter.

		keepRunning:
			a boolean that refers to the value of atomicBoolean in the Resources class.

	Constructor:
		public Server(InetAddress ipNumber, int portNumber)
			creates a new server objcet sets to the value of the parameters.

	Methods:
		public void run()
			uses serverSocket to creates a socket. Then uses the inner class RequestHandler to start to processRequest.

		public void stopServer()
			stops the server and set the atomicBoolean in the Resources class to false.



		private class RequestHandler()
			Instance Variable:
				client
					a Socket that create by serverSocket in the main class.

			Constructor:
				creates a new RequestHandler sets to the value of the parameter.

			Methods:
				public void run()
					call the abstract processRequest method.

*/
	private InetAddress serverIPNumber;

	private ServerSocket serverSocket;

	private boolean keepRunning;

	public Server(InetAddress ipNumber,int portNumber)
	{
		Resources r;
		r=Resources.getInstance();

		if(ipNumber==null){throw new RuntimeException("The ipNumber is null.");}

		if((portNumber>=0)&&(portNumber<65535))
		{
			this.serverIPNumber=ipNumber;

			try{this.serverSocket=new ServerSocket(portNumber, 1000, ipNumber);}

			catch(Exception e)
			{throw new RuntimeException("Can not create the serverSocket.");}

		}

		this.serverIPNumber=serverIPNumber;

		this.keepRunning=r.getKeepRunning();

		r.setKeepRunning(true);



	}//constructor

	public void run()//can't adverts
	{
		Socket client;

		while(Resources.getInstance().getKeepRunning())
		{
			try
			{
				client=this.serverSocket.accept();
				//processRequest(client);
				if(Resources.getInstance().getKeepRunning()){new Thread(new RequestHandler(client)).start();}
			}

			catch(IOException ioe){throw new RuntimeException(ioe.getMessage());}
		}

	}//run

	abstract public void processRequest(Socket socket);


	public void stopServer()
	{
		Socket client;

		Resources.getInstance().setKeepRunning(false);

		//make socket connection

		try{client=this.serverSocket.accept();}

		catch(IOException ioe){throw new RuntimeException(ioe.getMessage());}

	}




	private class RequestHandler implements Runnable
	{
		private Socket client;

		public RequestHandler(Socket socket)
		{
			this.client=socket;
		}//constructor

		public void run()
		{
			try{Server.this.processRequest(this.client);}

			catch(Exception e){System.out.println(e.getMessage());}

			finally
			{
				try{this.client.close();}
				catch(Exception e2){System.out.println("Can not close the socket.");}
			}//finally
		}//run
	}//classRequestHandler


}//class