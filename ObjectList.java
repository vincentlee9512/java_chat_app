import java.util.ArrayList;
import java.io.*;

public class ObjectList<T> implements Serializable
{
/*
	Vincent Li
	April 1, 2017

	This class will provide the general processing of the object array list.

	Instance Variable:
		list
			an array list that could store different data type.

	Constructors:
		ObjectList()
			creates a new array list set to the array list the
			constructor was called.

	Methods:
		private ArrayList<T> getList()
			the accessor to the instance variable.

		public int size()
			returns the size of the array list.

		public boolean isEmpty()
			returns true if the array list is empty,
			otherwise false.

		public void add(Position position,T value)
			adds the value to the specific position.

		public T get(Position position)
			returns the element of this specific position.

		pbulic T remove(Position position)
			removes and returns the element in this specific
			position.

		public void set(Position position,T value)
			change the element of the array list in this specific
			position to the value.

*/
	private static final long serialVersionUID=1;

	private ArrayList<T> list;

	public ObjectList()
	{
		this.list=new ArrayList<T>();
	}

	private ArrayList<T> getList(){return this.list;}

	public int size(){return getList().size();}

	public boolean isEmpty(){return size()<1;}

	public void add(Position position,T value)
	{
		if((position.get(size())<-1)||(position.get(size())>size())){throw new IllegalArgumentException("The position is not valid.");}

		if(isEmpty()){getList().add(value);}

		else{getList().add(position.get(size())+1,value);}
	}//add

	public T get(Position position)
	{
		if((position.get(size())<0)||(position.get(size())>size())){throw new IllegalArgumentException("The position is not valid.");}

		if(getList().isEmpty()){throw new IllegalArgumentException("The list is empty at this time.");}

		return getList().get(position.get(size()));
	}//get

	public T remove(Position position)
	{
		if((position.get(size())<0)||(position.get(size())>size())){throw new IllegalArgumentException("The position is not valid.");}

		if(getList().isEmpty()){throw new IllegalArgumentException("The list is empty at this time.");}

		return getList().remove(position.get(size()));
	}//remove

	public void set(Position position,T value)
	{
		if((position.get(size())<0)||(position.get(size())>size())){throw new IllegalArgumentException("The position is not valid.");}

		if(getList().isEmpty()){throw new IllegalArgumentException("The list is empty at this time.");}

		getList().set(position.get(size()),value);
	}//set
}