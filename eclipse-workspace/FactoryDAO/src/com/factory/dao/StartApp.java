package com.factory.dao;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class StartApp 
{

	public static void main(String[] args) 
	{
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		Logger logger = new Logger();
		TaskModel model = new TaskModel();
		String categoryName, taskName, description, tags, endDate, result, oldTaskName, newTaskName;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int ch1 = 0, ch2 = 0, priority;
		long currentTime;
		Date plannedEndDate;
		TaskBean bean;
		try
		{
			logger.log("Starting Task Manager");
			while(ch1 != 7)
			{
				System.out.println("Press 1) to create category");
				System.out.println("Press 2) to load category");
				System.out.println("Press 3) to delete category");
				System.out.println("Press 4) to list categories");
				System.out.println("Press 5) to search category");
				System.out.println("Press 6) to export category");
				System.out.println("Press 7) to Exit");
				ch1 = sc1.nextInt();
				switch(ch1)
				{
					case 1 : System.out.println("Enter category name");
						 	 categoryName = sc2.nextLine();
						 	 while(!TaskUtil.validateName(categoryName))
						 	 {
						 		 System.out.println("Enter a valid name");
						 		 categoryName = sc2.nextLine();
						 	 }
						 	 while(model.checkCategoryExists(categoryName))
						 	 {
						 		 System.out.println("Category with name " + categoryName + " already exists. Enter new name");
						 		 categoryName = sc2.nextLine();
						 		 while(!TaskUtil.validateName(categoryName))
							 	 {
							 		 System.out.println("Enter a valid name");
							 		 categoryName = sc2.nextLine();
							 	 } 
						 	 }
						 	 result = model.createCategory(categoryName);
						 	 if(result.equals(Constants.SUCCESS))
						 	 {
						 		ch2 = 0;
						 		System.out.println("Category " + categoryName + " created sucessfully");
						 		while(ch2 != 6)
						 		{
							 		 System.out.println("Press 1) to Create task");
							 		 System.out.println("Press 2) to Update task");
							 		 System.out.println("Press 3) to Delete task");
							 		 System.out.println("Press 4) to List tasks");
							 		 System.out.println("Press 5) to Search task");
							 		 System.out.println("Press 6) to go back");
							 		 ch2 = sc1.nextInt();
						 		 
							 		 switch(ch2)
							 		 {
							 		 	case 1 : System.out.println("Enter task name");
							 		 	 		 taskName = sc2.nextLine();
							 		 	 		 while(model.checkTaskExists(taskName, categoryName))
							 		 	 		 {
							 		 	 			 System.out.println("Task name " + taskName + " already exists. Enter new task name");
							 		 	 			 taskName = sc2.nextLine();
							 		 	 		 }
							 		 	 		 System.out.println("Enter task Description");
							 		 	 		 description = sc2.nextLine();
							 		 	 		 System.out.println("Enter tags(comma separated)");
							 		 	 		 tags = sc2.nextLine();
							 		 	 		 System.out.println("Enter planned end date(dd/MM/yyyy)");
							 		 	 		 endDate = sc2.nextLine();
							 		 	 		 plannedEndDate = sdf.parse(endDate);
							 		 	 		 System.out.println("Enter priority(1-10)");
							 		 	 		 priority = sc1.nextInt();
							 		 	 		 currentTime = new Date().getTime();
							 		 	 		 bean = new TaskBean(taskName,description,tags,plannedEndDate,priority,currentTime);
							 		 	 		 result = model.createTask(bean,categoryName);
							 		 	 		 if(result.equals(Constants.SUCCESS))
							 		 	 			 System.out.println("Task created successfully");
							 		 	 		 else
							 		 	 			 System.out.println(result);
							 		 			 break;
							 		 			 
							 		 	case 2 : System.out.println("Enter task name to update");
							 		 	 		 oldTaskName = sc2.nextLine();
							 		 	 		 while(!model.checkTaskExists(oldTaskName, categoryName))
							 		 	 		 {
							 		 	 			 System.out.println("Task name " + oldTaskName + " doesn't exist. Enter existing task name");
							 		 	 			 oldTaskName = sc2.nextLine();
							 		 	 		 }
							 		 	 		 System.out.println("Enter new task name");
							 		 	 		 newTaskName = sc2.nextLine();
							 		 	 		 System.out.println("Enter task Description");
							 		 	 		 description = sc2.nextLine();
							 		 	 		 System.out.println("Enter tags(comma separated)");
							 		 	 		 tags = sc2.nextLine();
							 		 	 		 System.out.println("Enter planned end date(dd/MM/yyyy)");
							 		 	 		 endDate = sc2.nextLine();
							 		 	 		 plannedEndDate = sdf.parse(endDate);
							 		 	 		 System.out.println("Enter priority(1-10)");
							 		 	 		 priority = sc1.nextInt();
							 		 	 		 currentTime = new Date().getTime();
							 		 	 		 bean = new TaskBean(newTaskName,description,tags,plannedEndDate,priority,currentTime);
							 		 	 		 result = model.updateTask(oldTaskName,bean,categoryName);
							 		 	 		 if(result.equals(Constants.SUCCESS))
							 		 	 			 System.out.println("Task updated successfully");
							 		 	 		 else
							 		 	 			 System.out.println(result);
							 		 			 break;
							 		 			 
							 		 	case 3 : System.out.println("Enter task name to be deleted");
							 		 			 taskName = sc2.nextLine();
							 		 			 while(!model.checkTaskExists(taskName, categoryName))
							 		 	 		 {
							 		 	 			 System.out.println("Task name " + taskName + " doesn't exist. Enter existing task name");
							 		 	 			 taskName = sc2.nextLine();
							 		 	 		 }
							 		 			 result = model.deleteTask(taskName,categoryName);
							 		 	 		 if(result.equals(Constants.SUCCESS))
							 		 	 			 System.out.println("Task deleted successfully");
							 		 	 		 else
							 		 	 			 System.out.println(result);
						 		 			 	 break;
						 		 			 	 
							 		 	case 4 : model.listTasks(categoryName);
							 		 			 break;
							 		 			 
								 		case 5 : System.out.println("Enter task name to search");
								 				 taskName = sc2.nextLine();
								 				 boolean res = model.searchTask(taskName, categoryName);
								 				 if(res)
								 					 System.out.println("Task '" + taskName + "' exists.");
								 				 else
								 					 System.out.println("Task '" + taskName + "' does not exist.");
							 		 			 break;
							 		 			 
							 		 	case 6 : break ;
							 		 			 
							 		 	default: System.out.println("Enter 1-6 only");
							 		 }
						 		 }
						 	 }
						 	 else
						 		 System.out.println(result);
							 break;
							 
					case 2 : System.out.println("Enter category name to be loaded");
						 	 categoryName = sc2.nextLine();
						 	 while(!TaskUtil.validateName(categoryName))
						 	 {
						 		 System.out.println("Enter a valid name");
						 		 categoryName = sc2.nextLine();
						 	 }
						 	 while(!model.checkCategoryExists(categoryName))
						 	 {
						 		 System.out.println("Category with name " + categoryName + " doesn't exists. Enter existing category");
						 		 categoryName = sc2.nextLine();
						 		 while(!TaskUtil.validateName(categoryName))
							 	 {
							 		 System.out.println("Enter a valid name");
							 		 categoryName = sc2.nextLine();
							 	 } 
						 	 }
						 	ch2 = 0;
					 		System.out.println("Category " + categoryName + " loaded sucessfully");
					 		while(ch2 != 6)
					 		{
						 		 System.out.println("Press 1) to Create task");
						 		 System.out.println("Press 2) to Update task");
						 		 System.out.println("Press 3) to Delete task");
						 		 System.out.println("Press 4) to List tasks");
						 		 System.out.println("Press 5) to Search task");
						 		 System.out.println("Press 6) to go back");
						 		 ch2 = sc1.nextInt();
					 		 
						 		 switch(ch2)
						 		 {
						 		 	case 1 : System.out.println("Enter task name");
						 		 	 		 taskName = sc2.nextLine();
						 		 	 		 while(model.checkTaskExists(taskName, categoryName))
						 		 	 		 {
						 		 	 			 System.out.println("Task name " + taskName + " already exists. Enter new task name");
						 		 	 			 taskName = sc2.nextLine();
						 		 	 		 }
						 		 	 		 System.out.println("Enter task Description");
						 		 	 		 description = sc2.nextLine();
						 		 	 		 System.out.println("Enter tags(comma separated)");
						 		 	 		 tags = sc2.nextLine();
						 		 	 		 System.out.println("Enter planned end date(dd/MM/yyyy)");
						 		 	 		 endDate = sc2.nextLine();
						 		 	 		 plannedEndDate = sdf.parse(endDate);
						 		 	 		 System.out.println("Enter priority(1-10)");
						 		 	 		 priority = sc1.nextInt();
						 		 	 		 currentTime = new Date().getTime();
						 		 	 		 bean = new TaskBean(taskName,description,tags,plannedEndDate,priority,currentTime);
						 		 	 		 result = model.createTask(bean,categoryName);
						 		 	 		 if(result.equals(Constants.SUCCESS))
						 		 	 			 System.out.println("Task created successfully");
						 		 	 		 else
						 		 	 			 System.out.println(result);
						 		 			 break;
						 		 			 
						 		 	case 2 : System.out.println("Enter task name to update");
						 		 	 		 oldTaskName = sc2.nextLine();
						 		 	 		 while(!model.checkTaskExists(oldTaskName, categoryName))
						 		 	 		 {
						 		 	 			 System.out.println("Task name " + oldTaskName + " doesn't exist. Enter existing task name");
						 		 	 			 oldTaskName = sc2.nextLine();
						 		 	 		 }
						 		 	 		 System.out.println("Enter new task name");
						 		 	 		 newTaskName = sc2.nextLine();
						 		 	 		 System.out.println("Enter task Description");
						 		 	 		 description = sc2.nextLine();
						 		 	 		 System.out.println("Enter tags(comma separated)");
						 		 	 		 tags = sc2.nextLine();
						 		 	 		 System.out.println("Enter planned end date(dd/MM/yyyy)");
						 		 	 		 endDate = sc2.nextLine();
						 		 	 		 plannedEndDate = sdf.parse(endDate);
						 		 	 		 System.out.println("Enter priority(1-10)");
						 		 	 		 priority = sc1.nextInt();
						 		 	 		 currentTime = new Date().getTime();
						 		 	 		 bean = new TaskBean(newTaskName,description,tags,plannedEndDate,priority,currentTime);
						 		 	 		 result = model.updateTask(oldTaskName,bean,categoryName);
						 		 	 		 if(result.equals(Constants.SUCCESS))
						 		 	 			 System.out.println("Task updated successfully");
						 		 	 		 else
						 		 	 			 System.out.println(result);
						 		 			 break;
						 		 			 
						 		 	case 3 : System.out.println("Enter task name to be deleted");
						 		 			 taskName = sc2.nextLine();
						 		 			 while(!model.checkTaskExists(taskName, categoryName))
						 		 	 		 {
						 		 	 			 System.out.println("Task name " + taskName + " doesn't exist. Enter existing task name");
						 		 	 			 taskName = sc2.nextLine();
						 		 	 		 }
						 		 			 result = model.deleteTask(taskName,categoryName);
						 		 	 		 if(result.equals(Constants.SUCCESS))
						 		 	 			 System.out.println("Task deleted successfully");
						 		 	 		 else
						 		 	 			 System.out.println(result);
					 		 			 	 break;
					 		 			 	 
						 		 	case 4 : model.listTasks(categoryName);
						 		 			 break;
						 		 			 
							 		case 5 : System.out.println("Enter task name to search");
							 				 taskName = sc2.nextLine();
							 				 boolean res = model.searchTask(taskName, categoryName);
							 				 if(res)
							 					 System.out.println("Task " + taskName + " exists.");
							 				 else
							 					 System.out.println("Task name does not exist.");
						 		 			 break;
						 		 			 
						 		 	case 6 : break ;
						 		 			 
						 		 	default: System.out.println("Enter 1-6 only");
						 		 }
					 		 }
						 	 break;
						 	 
					case 3 : System.out.println("Enter category name to be deleted");
						 	 categoryName = sc2.nextLine();
						 	 while(!TaskUtil.validateName(categoryName))
						 	 {
						 		 System.out.println("Enter a valid name");
						 		 categoryName = sc2.nextLine();
						 	 }
						 	 while(!model.checkCategoryExists(categoryName))
						 	 {
						 		 System.out.println("Category with name " + categoryName + " doesn't exists. Enter existing category");
						 		 categoryName = sc2.nextLine();
						 		 while(!TaskUtil.validateName(categoryName))
							 	 {
							 		 System.out.println("Enter a valid name");
							 		 categoryName = sc2.nextLine();
							 	 } 
						 	 }
						 	 boolean res = model.deleteCategory(categoryName);
						 	 if(res)
						 		 System.out.println("Category deleted successfully");
						 	 else
						 		 System.out.println("Oops something bad happende. Category deletion failed!");
						 	 break;
						 	 
					case 4 : model.listCategories();
						 	 break;
						 	 
					case 5 : System.out.println("Enter category name to search");
						 	 categoryName = sc2.nextLine();
						 	 while(!TaskUtil.validateName(categoryName))
						 	 {
						 		 System.out.println("Enter a valid name");
						 		 categoryName = sc2.nextLine();
						 	 }
						 	 res = model.searchCategory(categoryName);
						 	 if(res)
						 		 System.out.println("Category '" + categoryName + "' exists");
						 	 else
						 		 System.out.println("Category '" + categoryName + "' doesn't exist");
							 break;
							 
					case 6 : System.out.println("Enter category name to export");
						 	 categoryName = sc2.nextLine();
						 	 while(!TaskUtil.validateName(categoryName))
						 	 {
						 		 System.out.println("Enter a valid name");
						 		 categoryName = sc2.nextLine();
						 	 }
						 	 while(!model.checkCategoryExists(categoryName))
						 	 {
						 		 System.out.println("Category with name " + categoryName + " doesn't exists. Enter existing category");
						 		 categoryName = sc2.nextLine();
						 		 while(!TaskUtil.validateName(categoryName))
							 	 {
							 		 System.out.println("Enter a valid name");
							 		 categoryName = sc2.nextLine();
							 	 } 
						 	 }
						 	 result = model.exportCategory(categoryName);
						 	 if(result.equals(Constants.SUCCESS))
						 		 System.out.println("Category " + categoryName + " exported successfully");
						 	 else
						 		System.out.println("Category " + categoryName + " exporting failed " + result);
							 break;
							 
					case 7 : break;
					
					default : System.out.println("Enter 1-7 only");
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
