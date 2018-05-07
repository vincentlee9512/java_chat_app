import java.io.*;
import java.util.*;

public class BealeCipherBytes implements Cipher,Serializable
{
	/*
		Vincent Li
		April 21

		This class will provide processing of Beale Cipher.
		This class implements Cipher and Serializable class.

		Instance Variable:
			codeBookContent
				a byteArray containing the content from the inputStream
				parameter in the constructor.

			locations
				an array containing IntegarLists which contains the position(s)
				of bytes in the codeBookContent.

			serialVersionUID
				this variable allows the object to be serialzable.

		Constructor:
			BealeCipherBytes(InputStream codeBook)
				creates a new BealeCipherBytes object set to the valu of
				the parameter.

		Methods:
			private byte[] getCodeBookContent()
				an accessor to the codeBookContent instance variable.

			private IntegerList[] getLocations()
				an accessor to the locations instance variable.

			public encrypt(String clearText,Writer cipherText)
				encrypts the string text, then enter into the Writer.

			public encrypt(InputStream clearText,Writer cipherText)
				encryts the content in the inputStream, then enter into
				the Writer.

			public decrypt(String cipherText,OutputStream clearText)
				decryts the string Text, then enter into the OutputStream.

			public decrypt(Reader cipherText,OutputStream clearText)
				decryts the content in the Reader, then enter into the
				OutputStream.
*/
	private static final long serialVersionUID=1;

	private byte[] codeBookContent;

	private IntegerList[] locations;

	public BealeCipherBytes(InputStream codeBook)
	{
		int bytesRead;
		byte[] data;
		int hold;
		int loc;
		int locInSet;
		ByteArrayOutputStream out;

		if(codeBook == null){throw new CipherException("The codeBook is null.");}

		this.locations = new IntegerList[256];

		for(int i=0;i<this.locations.length;i++){this.locations[i]=new IntegerList();}

		data = new byte[10000];
		loc = 0;
		out = new ByteArrayOutputStream();


		try
		{
			bytesRead = codeBook.read(data);

			while(bytesRead>0)
			{
				out.write(data);
				bytesRead = codeBook.read(data);
			}

			this.codeBookContent = out.toByteArray();
		}
		catch(IOException ioe)
		{throw new CipherException("Can not read the codeBook");}



		for(int i=0;i<this.codeBookContent.length;i++)
		{
			hold=this.codeBookContent[i];
			locInSet=(hold+256)%256;


			this.locations[locInSet].add(Position.LAST,loc);


			loc=loc+1;
		}

	}//constructor

	public byte[] getCodeBookContent(){return this.codeBookContent;}

	public IntegerList[] getLocations(){return this.locations;}

	public void encrypt(String clearText,Writer cipherText)
	{
		byte[] bytesOfString;
		int[] numsOfBytes;
		byte hold;
		String result;

		if(clearText==null){throw new EncryptException("The String clearText is null.");}

		if(clearText.length()==0){throw new EncryptException("The String clearText contains nothing.");}

		if(cipherText==null){throw new EncryptException("The Writer cipherText is null.");}

		bytesOfString=clearText.getBytes();

		numsOfBytes=new int[256];

		result="";

		for(int i=0;i<bytesOfString.length;i++)
		{
			hold=bytesOfString[i];

			numsOfBytes[(int)hold]=numsOfBytes[(int)hold]+1;

		}

		for(int i=0;i<numsOfBytes.length;i++)
		{
			if(getLocations()[i]!=null)
			{
				if(numsOfBytes[i]>getLocations()[i].size()){throw new EncryptException("The codeBook does not have enough elements to encrypt.");}
			}

			if((getLocations()[i]==null)&&(numsOfBytes[i]>0)){throw new EncryptException("The codeBook does not have certain elements of clearText.");}
		}

		for(int i=0;i<bytesOfString.length;i++){result=result+getLocations()[(int)bytesOfString[i]].remove(Position.RANDOM)+" ";}

		try
		{
			cipherText.write(result);
			cipherText.close();
		}
		catch(Exception e)
		{throw new EncryptException("Fail to process to the Writer cipherText.");}

	}//encrypt



	public void encrypt(InputStream clearText,Writer cipherText)
	{
		byte[] buffer;
		int bytesRead;
		byte[] content;
		ByteArrayOutputStream data;
		String text;

		if(clearText==null){throw new EncryptException("The InputStream clearText is null.");}

		if(cipherText==null){throw new EncryptException("The Writer cipherText is null.");}

		data=new ByteArrayOutputStream();
		buffer=new byte[10000];
		text="";

		try
		{
			bytesRead=clearText.read(buffer,0,buffer.length);

			while(bytesRead>0)
			{
				data.write(buffer,0,bytesRead);
				bytesRead=clearText.read(buffer,0,buffer.length);

				data.close();
			}//loop

			data.flush();

			content=data.toByteArray();
		}
		catch(Exception e)
		{throw new EncryptException("Failed to transfer the inputStream clearText to byteArray.");}

		if(content.length==0)
		{throw new EncryptException("The inputStream clearText contains nothing.");}

		for(int i=0;i<content.length;i++){text=text+(char)(int)content[i];}

		encrypt(text,cipherText);

		try
		{
			clearText.close();
			cipherText.close();
		}
		catch(Exception e)
		{throw new EncryptException("Can not close inputStream clearText or Writer cipherText.");}

	}


	public void decrypt(String cipherText,OutputStream clearText)
	{
		ArrayList<String> cipherArray;
		byte[] result;
		int i;
		int indexOfNextBlank;
		byte clearByte;

		if(cipherText==null){throw new DecryptException("The String cipehrText is null.");}

		if(clearText==null){throw new DecryptException("The OutputStream clearText is null.");}


		cipherArray=new ArrayList<String>();


		i=0;
		while(i<cipherText.length())
		{
			if(cipherText.substring(i,i+1).trim().length()==0){i=i+1;}

			else
			{
				try
				{
					indexOfNextBlank=cipherText.indexOf(" ",i);
					cipherArray.add(cipherText.substring(i,indexOfNextBlank));

					i=indexOfNextBlank;
				}
				catch(StringIndexOutOfBoundsException e)
				{
					cipherArray.add(cipherText.substring(i));
					i=cipherText.length();
				}//catch

			}//else
		}//loop


		result=new byte[cipherArray.size()];

		for(int j=0;j<result.length;j++)
		{
			if(Integer.parseInt(cipherArray.get(j))>getCodeBookContent().length)
			{throw new DecryptException("The code: "+cipherArray.get(j)+" is out of bounds or invalid.");}

			clearByte=getCodeBookContent()[Integer.parseInt(cipherArray.get(j))];
			result[j]=clearByte;
		}

		try
		{
			clearText.write(result);
			clearText.close();
		}
		catch(Exception e)
		{throw new DecryptException("Fail to process the OutputStream clearText.");}

	}//decrypt


		public void decrypt(Reader cipherText,OutputStream clearText)
		{
			String hold;
			String readerContent;
			int asciiNum;


			if(cipherText==null){throw new DecryptException("The Reader cipherText is null.");}

			if(clearText==null){throw new DecryptException("The OutputStream clearText is null.");}

			hold="";
			readerContent="";

			try
			{
				asciiNum=cipherText.read();

				while(asciiNum>0)
				{
					readerContent=readerContent+(char)asciiNum;
					asciiNum=cipherText.read();
				}

			}

			catch(Exception e)
			{throw new DecryptException("Can not read the reader cipehrText.");}

			if(readerContent.length()==0){throw new DecryptException("The Reader Containing nothing.");}

			decrypt(readerContent,clearText);

			try
			{
				cipherText.close();
				clearText.close();
			}
			catch(Exception e)
			{throw new DecryptException("Can not close the Reader cipherText or the OutputStream clearText.");}

		}//decrypt

}//class