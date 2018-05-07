import java.io.*;

public class Message implements Serializable
{
/*
	Vincent Li
	May 8, 2017

	This class will provide the processing of the Message. This class is a wrapper class for String.

	Instance Variable:
		serialVersionUID
			this variable allows this object to be serializable.

		clearText
			a String that contains the clear text.

	constructor:
		creates a new Message class sets to the value of the parameter.

	Method:
		public String getClearText()
			returns the value of the parameter in the constructor.

*/

	private static final long serialVersionUID=1;

	private String clearText;

	public Message(String clearText)
	{
		this.clearText=clearText;
	}

	public String getClearText(){return this.clearText;}

}//class