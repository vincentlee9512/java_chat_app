import java.io.*;

public class UserName implements Serializable,Comparable<UserName>
{
/*
	Vincent Li
	April 26,2017

	This class will provide the processing of the UserName.

	Instance Variable:
		userName
			a string that contains the user's name.

	Constructor:
		UserName(String userName)
			creates a new UserName object set to the value of the parameter.

	Method:
		public String getUserName()
			the accessor to the userName instance variable.

		public int compareTo(UserName other)
			returns the result of comparing this UserName object with another one.

		public int compareTo(String other)
			returns the result of comparing this UserName's instance variable with anther String.

		public int hashCode()
			returns the hashCode of the userName instance variable.

		public boolean equal(Object other)
			returns true if the Object other has the same hashCode with this UserName object.

		public String toString()
			returns the instance variable userName.


*/

	private static final long serialVersionUID=1;

	private String userName;

	public UserName(String userName)
	{
		if(userName==null){throw new IllegalArgumentException("The userName is null.");}

		this.userName=userName.toLowerCase();
	}

	public String getUserName(){return this.userName;}

	public int compareTo(UserName other)
	{
		if(other==null){throw new IllegalArgumentException("The UserName other is null.");}

		return this.userName.compareTo(other.getUserName());
	}

	public int compareTo(String other)
	{
		if(other==null){throw new IllegalArgumentException("The String other is null.");}

		return this.userName.compareTo(other);
	}

	public int hashCode(){return this.userName.hashCode();}

	public boolean equals(Object other)
	{
		if(other==null){throw new IllegalArgumentException("The Object other is null.");}

		return hashCode()==other.hashCode();
	}

	public String toString()
	{
		return this.userName;
	}

}//class