import java.io.*;

public class Annunciator implements Runnable
{
/*
	Vincent Li
	May 1,2017

	This class will provide the processing of the Annunciator

	Instance Variable:
		message
			a HelloGoodbye that contains the ServerInfo and multicastSocket

		sleepTime
			a long data that contains the time of thread sleep after sending the message.

	Constructor:
		Annunciator(long sleepTime,HelloGoodbye message)
			creates an Annunciator object sets to the values of the parameters.

	Methods:
		public void run()
			sending out the HelloGoodbye message.


*/
	private HelloGoodbye message;

	private long sleepTime;

	public Annunciator(long sleepTime,HelloGoodbye message)
	{
		if(message==null)
		{throw new IllegalArgumentException("The HelloGoodbye message is null.");}

		if(sleepTime<0)
		{throw new IllegalArgumentException("The sleepTime is not valid.");}

		this.message=message;
		this.sleepTime=sleepTime;
	}

	public void run()
	{
		while(Resources.getInstance().getKeepRunning())
		{
			try
			{

				this.message.send();
				Thread.sleep(sleepTime);
			}
			catch(IOException ioe)
			{throw new RuntimeException("Failed to send message.");}
			catch(InterruptedException ie)
			{throw new RuntimeException("Thread being interrupted.");}
		}

	}//run
}