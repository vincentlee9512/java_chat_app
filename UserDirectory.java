import java.util.*;


public class UserDirectory
{
/*
	Vincent Li
	May 1,2017

	This class is a singleton, will provide the processing of the UserDirectory

	Instance Variable:
		HashMap<UserName,UserInformation>
			a hash map that contains the userName and userInformation who are using the server.

	Constructor:
		UserDirectory()
			private constructor, creates a new hashmap when call new instance.

	Method:
		public static UserDirectory newInstance()
			calls the getInstance() method.

		public static UserDirectory getInstance()
			returns the UserDirectory when be called.

		public Hello getHelloMessage(UserName userName)
			returns the HelloMessage object in the UserInformation object based on the userName parameter.

		public Cipher getEncryptingCipher(UserName userName)
			returns the Cipher object object in the UserInformation object based on the userName parameter.

		public Cipher getDecryptingCipher(UserName userName)
			returns the Cipher object object in the UserInformation object based on the userName parameter.

		public UserName[] getUserName()
			returns the array of UserName objects from the directory instance variable.

		public boolean add(UserName userName)
			returns true if the method adds the userName as a key(no value) into the directory, false otherwise.

		public boolean add(Hello helloMessage)
			returns true if the method adds the helloMessage as a value into the directory, and using the userName(from the hellomessage) as a key.

		public remove(HelloGoodbye message)
			take an userName from message and uses it to remove the key and value using the userName.

		public updateEncryptingCipher(UserName userName,Cipher encryptingCipher)
			reset the encryptingCipher for a specific user.

		public updateEncryptingCipher(UserName userName,Cipher decryptingCipher)
			reset the decryptingCipher for a specific user.

	Inner class:
		UserInformation

			this class will provide the processing of the UserInformation

			Instance Variable:
				hellomessage
					a Hello object that contains the ServerInfo and MulticastSocket for the user.

				encryptingCipher
					a Cipher object that using to encrypt.

				decryptingCipher
					a Cipher object that using to decrypt.

			Constructor:
				UserInformation(Hello helloMessage)
					creates a new UserInformation sets to the value of the parameter.

			Method:
				public Hello getHelloMessage()
					the accessor to the helloMessage instance variable.

				public Cipher getEncryptingCipher()
					the accessor to the encryptingCipher instance variable.

				public Cipher getDecryptingCipher()
					the accessor to the decryptingCipher instance variable.

				public void setEncryptingCipher(Cipher encryptingCipher)
					set the encryptingCipher instance variable to the parameter.

				public void setEncryptingCipher(Cipehr decryptingCipher)
					set the decryptingCipher instance variable to the parameter.

*/

	private static final UserDirectory instance=new UserDirectory();

	private HashMap<UserName,UserInformation> directory;

	private UserDirectory()
	{
		directory=new HashMap<UserName,UserInformation>();

	}

	public static UserDirectory newInstance(){return UserDirectory.getInstance();}

	public static UserDirectory getInstance(){return UserDirectory.instance;}

	public Hello getHelloMessage(UserName userName)
	{
		if(userName==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}

		if(!this.directory.containsKey(userName))
		{
			throw new IllegalArgumentException("(getHelloMessage)"+userName.getUserName()+" is not in the directory.");
		}

		return directory.get(userName).getHelloMessage();
	}

	public Cipher getEncryptingCipher(UserName userName)
	{
		if(userName==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}

		if(!this.directory.containsKey(userName))
		{
			throw new IllegalArgumentException("(getEncryptingCipher)"+userName.getUserName()+" is not in the directory.");
		}

		return directory.get(userName).getEncryptingCipher();
	}

	public Cipher getDecryptingCipher(UserName userName)
	{
		if(userName==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}

		if(!this.directory.containsKey(userName))
		{
			throw new IllegalArgumentException("(getDecryptingCipher)"+userName.getUserName()+" is not in the directory.");
		}
		return directory.get(userName).getDecryptingCipher();
	}

	public void updateEncryptingCipher(UserName userName,Cipher encryptingCipher)
	{
		if(userName==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}

		if(!this.directory.containsKey(userName))
		{
			throw new IllegalArgumentException("(updateEncryptinCipher)"+userName.getUserName()+" is not in the directory.");
		}

		if(encryptingCipher==null)
		{
			throw new IllegalArgumentException("The encryptingCipher is null.");
		}

		directory.get(userName).setEncryptingCipher(encryptingCipher);
	}

	public void updateDecryptingCipher(UserName userName,Cipher decryptingCipher)
	{
		if(userName==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}

		if(!this.directory.containsKey(userName))
		{
			throw new IllegalArgumentException("(updateDecryptinCipher)"+userName.getUserName()+" is not in the directory.");
		}

		if(decryptingCipher==null)
		{
			throw new IllegalArgumentException("The decryptingCipher is null.");
		}

		directory.get(userName).setDecryptingCipher(decryptingCipher);
	}

	public UserName[] getUserNames()
	{
		UserName[] result;
		result=new UserName[directory.size()];

		directory.keySet().toArray(result);

		return result;
	}

	public boolean add(UserName userName)
	{
		boolean result;

		if(userName==null)
		{
			throw new IllegalArgumentException("The userName is null.");
		}



		result=false;

		if(!this.directory.containsKey(userName))
		{
			this.directory.put(userName,new UserInformation(null));
			result=true;
		}

		return result;
	}


	public boolean add(Hello helloMessage)
	{
		UserName key;
		boolean result;

		if(helloMessage==null)
		{
			throw new IllegalArgumentException("The helloMessage is null.");
		}

		key=helloMessage.getServerInfo().getUserName();
		result=false;

		if(!this.directory.containsKey(key))
		{
			this.directory.put(key,new UserInformation(helloMessage));
			result=true;
		}

		else
		{
			if((helloMessage.getTimeStamp()).after(this.directory.get(key).getHelloMessage().getTimeStamp()))
			{
				this.directory.put(key,new UserInformation(helloMessage));
				result=true;
			}
		}


		return result;
	}

	public void remove(HelloGoodbye message)
	{
		if(message==null)
		{
			throw new IllegalArgumentException("The message is null.");
		}

		UserName key;
		key=message.getServerInfo().getUserName();

		this.directory.remove(key);
	}









	private class UserInformation
	{
		private Hello helloMessage;

		private Cipher encryptingCipher;

		private Cipher decryptingCipher;

		public UserInformation(Hello helloMessage)
		{
			this.helloMessage=helloMessage;
		}

		public Hello getHelloMessage(){return this.helloMessage;}

		public Cipher getEncryptingCipher(){return this.encryptingCipher;}

		public Cipher getDecryptingCipher(){return this.decryptingCipher;}

		public void setEncryptingCipher(Cipher encryptingCipher)
		{
			if(encryptingCipher==null)
			{
				throw new IllegalArgumentException("The encryptingCipher is null.");
			}

			this.encryptingCipher=encryptingCipher;
		}

		public void setDecryptingCipher(Cipher decryptingCipher)
		{
			if(decryptingCipher==null)
			{
				throw new IllegalArgumentException("The decryptingCipher is null.");
			}

			this.decryptingCipher=decryptingCipher;
		}
	}

}