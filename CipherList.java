import java.io.*;

public class CipherList extends ObjectList<Cipher> implements Serializable
{
/*
	Vincent Li
	May 1,2017

	This class will provide the processing of the cipher arrayList.

	Constructor:
		public Cipherlist<Cipher>()
			creates a new ObjectList that contains cipher Object only.

	Method:
		public void loadCodeBooks(File codeBookDirectory) throws IOException
			take the file(s) from the codeBookDirectory and put them into
			the CipherList.

*/

	private static final long serialVersionUID=1;

	public CipherList()
	{
		ObjectList<Cipher> cipherList;
		cipherList=new ObjectList<Cipher>();
	}

	public void loadCodeBooks(File codeBookDirectory)throws IOException
	{
		if(codeBookDirectory==null)
		{
			throw new IllegalArgumentException("The codeBookDirectory is null.");
		}

		if((!codeBookDirectory.isFile())&&(!codeBookDirectory.isDirectory()))
		{
			throw new IllegalArgumentException("The codeBookDirectory is neither a file or a directory.");
		}

		if(codeBookDirectory.isFile())
		{
			this.add(Position.LAST,new BealeCipherBytes(new FileInputStream(codeBookDirectory)));
		}

		else
		{
			if(codeBookDirectory.isDirectory())
			{
				File[] fileArray;
				fileArray=codeBookDirectory.listFiles();

				for(int i=0;i<fileArray.length;i++)
				{
					//System.out.println(fileArray[i].getName());
					if(fileArray[i]!=null){this.add(Position.LAST,new BealeCipherBytes(new FileInputStream(fileArray[i])));}
				}
			}
		}
	}



}