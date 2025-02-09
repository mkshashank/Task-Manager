package com.factory.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskModel 
{
	private TaskDAO dao;
	//instance initializer
	{
		dao = DAOFactory.getDAO();
	}
	
	public boolean checkCategoryExists(String categoryName)
	{
		
		return dao.checkCategoryExists(categoryName);
	}
	
	public String createCategory(String categoryName)
	{
		return dao.createCategory(categoryName);
	}
	
	public boolean deleteCategory(String categoryName)
	{
		return dao.deleteCategory(categoryName);
	}
	
	public void listCategories()
	{
		dao.listCategories();
	}
	
	public boolean searchCategory(String categoryName)
	{
		return dao.searchCategory(categoryName);
	}
	
	public String exportCategory(String categoryName)
	{
		return dao.exportCategory(categoryName);
	}
	
	//Methods belonging to tasks
	public boolean checkTaskExists(String taskName, String categoryName)
	{
		return dao.checkTaskExists(taskName, categoryName);
	}
	
	public String createTask(TaskBean bean, String categoryName)
	{
		return dao.createTask(bean, categoryName);
	}
	
	public String updateTask(String oldTaskName, TaskBean bean, String categoryName)
	{
		return dao.updateTask(oldTaskName, bean, categoryName);
	}
	
	public String deleteTask(String taskName, String categoryName)
	{
		return dao.deleteTask(taskName, categoryName);
	}
	
	public void listTasks(String categoryName)
	{
		dao.listTasks(categoryName);
	}
	
	public boolean searchTask(String taskName, String categoryName)
	{
		return dao.searchTask(taskName, categoryName);
	}
	
}
