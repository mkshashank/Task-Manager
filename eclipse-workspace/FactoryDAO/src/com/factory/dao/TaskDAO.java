package com.factory.dao;

public abstract interface TaskDAO 
{
	//Methods belonging to category
	public  abstract boolean checkCategoryExists(String categoryName);
	public abstract String createCategory(String categoryName);
	public abstract boolean deleteCategory(String categoryName);
	public abstract void listCategories();
	public abstract boolean searchCategory(String categoryName);
	public abstract String exportCategory(String categoryName);
	
	//Methods belonging to tasks
	public abstract boolean checkTaskExists(String taskName, String categoryName);
	public abstract String createTask(TaskBean bean, String categoryName);
	public abstract String updateTask(String oldTaskName, TaskBean bean, String categoryName);
	public abstract String deleteTask(String taskName, String categoryName);
	public abstract void listTasks(String categoryName);
	public abstract boolean searchTask(String taskName, String categoryName);
}
