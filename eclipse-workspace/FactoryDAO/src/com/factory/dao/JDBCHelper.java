package com.factory.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCHelper 
{
	public static Connection getConnection()
	{
		try
		{
			Class.forName(Constants.DRIVER);
			Connection con = DriverManager.getConnection(Constants.URL,Constants.USER_NAME,Constants.PASSWORD);
			System.out.println("connection established " +con);
			return con;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(Connection con)
	{
		try
		{
			if(con != null)
				con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement stmt)
	{
		try
		{
			if(stmt != null)
				stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt)
	{
		try
		{
			if(stmt != null)
				stmt.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs)
	{
		try
		{
			if(rs != null)
				rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
