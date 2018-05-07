import java.net.*;
import java.io.*;

public class CipherMessageListener extends Listener
{
/*
	Vincent Li
	May 8, 2017

	This class will provide the processing of the CipherMessageListener. Extending the Listener class.

	Constructor:
		creates a new CipherMessageListener object. Using the cipherMessageMulticastSocket in the Resources class.

	Method:
		public void processPacket(DatagramPacket datagramPacket)
			Overriding the processPacket method from the super class. Receiving the cipherMessge and decrypting.
*/

	public CipherMessageListener()
	{
		super(Resources.getInstance().getCipherMessageMulticastSocket());
	}

	public void processPacket(DatagramPacket datagramPacket)
	{
		ByteArrayInputStream clearTextInput;
		ByteArrayOutputStream clearTextOutput;
		BealeCipherBytes decryptingCipher;
		ObjectInputStream resultObjectIn;
		String cipherText;
		UserName[] userNamesInDirectory;
		Message result;

		userNamesInDirectory=UserDirectory.getInstance().getUserNames();

		cipherText=new String(datagramPacket.getData(),0,datagramPacket.getLength()).trim();



		for(int i=0;i<userNamesInDirectory.length;i++)
		{
			clearTextOutput=new ByteArrayOutputStream();

			decryptingCipher=(BealeCipherBytes)UserDirectory.getInstance().getDecryptingCipher(userNamesInDirectory[i]);

			if(decryptingCipher!=null)
			{


				decryptingCipher.decrypt(cipherText,clearTextOutput);

				clearTextInput=new ByteArrayInputStream(clearTextOutput.toByteArray());
				try
				{
					resultObjectIn=new ObjectInputStream(clearTextInput);

					result=(Message)resultObjectIn.readObject();

					System.out.println(userNamesInDirectory[i]+": "+result.getClearText());
				}//try
				catch(Exception e){}

				}//if
			}//loop

	}//processpacket
}