package com.factory.dao;

public class TaskUtil 
{
	public static boolean validateName(String name)
	{
		if(name == null || name.isBlank())
			return false;
		
		if(!Character.isLetter(name.charAt(0)))
			return false;
		
		if(name.split(" ").length > 1)
			return false;
		
		for(int i = 0; i < name.length(); i++)
		{
			if(!Character.isLetterOrDigit(name.charAt(i)))
				return false;
		}
		
		return true;
	}
}
