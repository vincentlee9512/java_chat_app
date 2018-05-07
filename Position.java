public enum Position
{
/*
	Vincent Li
	April 1, 2017

	Enum:

		FIRST
			returns the first index of the array.

		LAST
			returns the last index of the array.

		RANDOM
			returns the random index of the array.

	Instance Variable:
		length
			the length of the array.

	Method:
		abstract public int get(int length)
			make the enums be able to motify the length of the array.

*/
	FIRST{public int get(int length)
			{
				if(length<-1){throw new IllegalArgumentException("The length is less than zero.");}
				return 0;
			}
		},

	LAST{public int get(int length)
			{
				if(length<-1){throw new IllegalArgumentException("The length is less than zero.");}
				return length-1;
			}
		},

	RANDOM
		{public int get(int length)
			{
				if(length<-1){throw new IllegalArgumentException("The length is less than zero.");}
				return (int)(Math.random()*length);
			}
		};

	private int length;

	abstract public int get(int length);

}