import java.io.*;

public class IntegerList extends ObjectList<Integer> implements Serializable
{
/*
	Vincent Li
	April 25, 2017

	This class will provide general processing of dates.

	Instance Variable:
		serialVersionUID
			this variable allows this object to be serializable.

	Constructor:
		IntegerList()
			creates a new ObjectList that only contains integer.

*/
	private static final long serialVersionUID=1;

	public IntegerList()
	{
		ObjectList<Integer> integerList;
		integerList=new ObjectList<Integer>();
	}
}