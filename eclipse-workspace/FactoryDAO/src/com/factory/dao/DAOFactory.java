package com.factory.dao;

public class DAOFactory 
{
	public static TaskDAO getDAO()
	{
		switch(Constants.DATA_SOURCE)
		{
			case 1 : return new TaskFileDAO();
			
			case 2 : return new TaskDatabaseDAO();
		}
		return null;
	}
}
