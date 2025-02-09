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

public class TaskFileDAO implements TaskDAO
{

	@Override
	public boolean checkCategoryExists(String categoryName) 
	{
		File f = new File(categoryName + ".todo");
		return f.exists();
	}

	@Override
	public String createCategory(String categoryName) 
	{
		File f = new File(categoryName + ".todo");
		
		try 
		{
			f.createNewFile();
			return Constants.SUCCESS;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return "Category creation failed";
		}
	}

	@Override
	public boolean deleteCategory(String categoryName) 
	{
		File f = new File(categoryName + ".todo");
		return f.delete();
	}

	@Override
	public void listCategories() 
	{
		File f = new File(Constants.PROJECT_PATH);
		File[] arr = f.listFiles();
		ArrayList<String> categories = new ArrayList<String>();
		for(File fi : arr)
		{
			if(fi.isFile() && fi.getName().endsWith(".todo"))
				categories.add(fi.getName());
		}
		if(categories.size() == 0)
			System.out.println("No categories exist. First add categories");
		else
		{
			for(String categoryName : categories)
				System.out.println(categoryName);
		}
	}

	@Override
	public boolean searchCategory(String categoryName) 
	{
		File f = new File(Constants.PROJECT_PATH);
		File[] arr = f.listFiles();
		for(File fi : arr)
		{
			if(fi.isFile() && fi.getName().endsWith(".todo") && fi.getName().equals(categoryName + ".todo"))
				return true;
		}
		return false;
	}

	@Override
	public String exportCategory(String categoryName) 
	{
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		try
		{
			br = new BufferedReader(new FileReader(categoryName+".todo"));
			while((line = br.readLine()) != null)
			{
				String[] arr = line.split(":");
				
				String taskName = arr[0];
				String description = arr[1];
				String tags = arr[2];
				Date plannedEndDate = sdf.parse(arr[3]);
				int priority = Integer.parseInt(arr[4]);
				long currentTime = Long.parseLong(arr[5].substring(0, arr[5].lastIndexOf("ms")));
				TaskBean temp = new TaskBean(taskName,description,tags,plannedEndDate,priority,currentTime);
				tasks.add(temp);
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(categoryName+"todo.csv"));
			for(TaskBean task : tasks)
			{
				bw.write(task.getTaskName() + ":" + task.getDescription() + ":" + task.getTags() + ":" + sdf.format(task.getPlannedEndDate()) + ":" + task.getPriority() + ":" + task.getCurrentTime() + "ms" );
				bw.newLine();
			}
			bw.flush();
			return Constants.SUCCESS;
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
			return "Task updation failed " + e.getMessage();
		}
		finally
		{
			if(bw != null)
			{
				try
				{
					bw.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean checkTaskExists(String taskName, String categoryName) 
	{
		BufferedReader br = null;
		
		try
		{
			br = new BufferedReader(new FileReader(categoryName + ".todo"));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] arr = line.split(":");
				if(arr[0].equals(taskName))
					return true;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try
				{
					br.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public String createTask(TaskBean bean, String categoryName) 
	{
		BufferedWriter bw = null;
		
		try
		{
			bw = new BufferedWriter(new FileWriter(categoryName + ".todo", true));
			String taskName = bean.getTaskName();
			String description = bean.getDescription();
			String tags = bean.getTags();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String plannedEndDate = sdf.format(bean.getPlannedEndDate());
			int priority = bean.getPriority();
			long currentTime = bean.getCurrentTime();
			bw.write(taskName + ":" + description + ":" + tags + ":" + plannedEndDate + ":" + priority + ":" + currentTime + "ms" );
			bw.newLine();
			bw.flush();
			return Constants.SUCCESS;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return "Task creation failed " + e.getMessage();
		}
		finally
		{
			if(bw != null)
			{
				try
				{
					bw.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String updateTask(String oldTaskName, TaskBean bean, String categoryName) 
	{
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		try
		{
			br = new BufferedReader(new FileReader(categoryName+".todo"));
			while((line = br.readLine()) != null)
			{
				String[] arr = line.split(":");
				if(arr[0].equals(oldTaskName))
				{
					tasks.add(bean);
				}
				else
				{
					String taskName = arr[0];
					String description = arr[1];
					String tags = arr[2];
					Date plannedEndDate = sdf.parse(arr[3]);
					int priority = Integer.parseInt(arr[4]);
					long currentTime = Long.parseLong(arr[5].substring(0, arr[5].lastIndexOf("ms")));
					TaskBean temp = new TaskBean(taskName,description,tags,plannedEndDate,priority,currentTime);
					tasks.add(temp);
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(categoryName+".todo"));
			for(TaskBean task : tasks)
			{
				bw.write(task.getTaskName() + ":" + task.getDescription() + ":" + task.getTags() + ":" + sdf.format(task.getPlannedEndDate()) + ":" + task.getPriority() + ":" + task.getCurrentTime() + "ms" );
				bw.newLine();
			}
			bw.flush();
			return Constants.SUCCESS;
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
			return "Task updation failed " + e.getMessage();
		}
		finally
		{
			if(bw != null)
			{
				try
				{
					bw.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String deleteTask(String taskName, String categoryName) 
	{
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		try
		{
			br = new BufferedReader(new FileReader(categoryName+".todo"));
			while((line = br.readLine()) != null)
			{
				String[] arr = line.split(":");
				if(arr[0].equals(taskName))
				{
					continue;
				}
				else
				{
					String taskName1 = arr[0];
					String description = arr[1];
					String tags = arr[2];
					Date plannedEndDate = sdf.parse(arr[3]);
					int priority = Integer.parseInt(arr[4]);
					long currentTime = Long.parseLong(arr[5].substring(0, arr[5].lastIndexOf("ms")));
					TaskBean temp = new TaskBean(taskName1,description,tags,plannedEndDate,priority,currentTime);
					tasks.add(temp);
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(categoryName+".todo"));
			for(TaskBean task : tasks)
			{
				bw.write(task.getTaskName() + ":" + task.getDescription() + ":" + task.getTags() + ":" + sdf.format(task.getPlannedEndDate()) + ":" + task.getPriority() + ":" + task.getCurrentTime() + "ms" );
				bw.newLine();
			}
			bw.flush();
			return Constants.SUCCESS;
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
			return "Task updation failed " + e.getMessage();
		}
		finally
		{
			if(bw != null)
			{
				try
				{
					bw.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void listTasks(String categoryName) 
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(categoryName + ".todo"));
			ArrayList<String> list = new ArrayList<String>();
			String line;
			while((line = br.readLine()) != null)
			{
				list.add(line);
			}
			if(list.size() == 0)
				System.out.println("No tasks to list. Add tasks first!");
			else
			{
				for(String str : list)
				{
					System.out.println(str);
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try
				{
					br.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public boolean searchTask(String taskName, String categoryName) 
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(categoryName + ".todo"));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] arr = line.split(":");
				if(arr[0].equals(taskName))
					return true;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try
				{
					br.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
