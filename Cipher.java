import java.io.*;

public interface Cipher
{
/*
	Vincent Li
	March 12, 2017

	This interface includes CaesarCipher and RandomCipher

	Method:
		public String encrypt(String clearText)
			This method is abstract. It is the process of
			convertinga message into an encoded message.

		public String decrypt(String cipherText)
			This method is abstract. It is the process of
			converting a cipher text back in to clear
			message.

	Modifiacation History:
		March 12, 2017
			Original Versionl.

		April 27,2017
			changed the parameter of both abstract methods.
*/

	public void decrypt(Reader cipherText,OutputStream clearText);

	public void encrypt(InputStream clearText,Writer cipherText);

}