package com.factory.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskDatabaseDAO implements TaskDAO
{
	Connection con;
	
	{
		con = JDBCHelper.getConnection();
	}
	
	@Override
	public boolean checkCategoryExists(String categoryName) 
	{
		try 
		{
			PreparedStatement ps_selCat = con.prepareStatement("SELECT categoryName FROM category;");
			ps_selCat.execute();
			ResultSet rs = ps_selCat.getResultSet();
			while(rs.next())
			{
				String category = rs.getString("categoryName");
				if(category.equals(categoryName))
					return true;
			}
			JDBCHelper.close(rs);
			JDBCHelper.close(con);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String createCategory(String categoryName) 
	{
		try 
		{
			PreparedStatement ps_insCat = con.prepareStatement("INSERT INTO category (categoryName) VALUES (?);");
			ps_insCat.setString(1, categoryName);
			ps_insCat.execute();
			return Constants.SUCCESS;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return "Category creation failed" + e.getMessage();
		}
	}

	@Override
	public boolean deleteCategory(String categoryName) 
	{
		try
		{
			PreparedStatement ps_delCat = con.prepareStatement("DELETE FROM category WHERE categoryName = ?;");
			ps_delCat.setString(1, categoryName);
			ps_delCat.execute();
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void listCategories() 
	{
		try
		{
			PreparedStatement ps_lisCat = con.prepareStatement("SELECT (categoryName) FROM category");
			ps_lisCat.execute();
			ResultSet rs = ps_lisCat.getResultSet();
			while(rs.next())
			{
				System.out.println(rs.getString("categoryName"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean searchCategory(String categoryName) 
	{
		try
		{
			PreparedStatement ps_lisCat = con.prepareStatement("SELECT (categoryName) FROM category");
			ps_lisCat.execute();
			ResultSet rs = ps_lisCat.getResultSet();
			while(rs.next())
			{
				if(rs.getString("categoryName").equals(categoryName))
					return true;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String exportCategory(String categoryName) 
	{
		return "Option not supported right now. Will be updated soon";
	}

	@Override
	public boolean checkTaskExists(String taskName, String categoryName) 
	{
		try
		{
			PreparedStatement ps_selCat = con.prepareStatement("SELECT slNo FROM category WHERE categoryName = ?;");
			ps_selCat.setString(1, categoryName);
			ps_selCat.execute();
			ResultSet rs = ps_selCat.getResultSet();

			int categorySl = -1;
			if(rs.next())
				categorySl = rs.getInt(1);
			if(categorySl == -1)
				return false;
			
			PreparedStatement ps_selTask = con.prepareStatement("SELECT * FROM task WHERE category_sl = ? AND taskName = ?");
			ps_selTask.setInt(1, categorySl);
			ps_selTask.setString(2, taskName);
			ps_selTask.execute();
			rs = ps_selTask.getResultSet();
			return rs.next();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String createTask(TaskBean bean, String categoryName) 
	{
		try
		{
			PreparedStatement ps_selCat = con.prepareStatement("SELECT slNo FROM category WHERE categoryName = ?;");
			ps_selCat.setString(1, categoryName);
			ps_selCat.execute();
			ResultSet rs = ps_selCat.getResultSet();

			int categorySl = -1;
			if(rs.next())
				categorySl = rs.getInt(1);
			if(categorySl == -1)
				return "Oops task insertion failed!";
			con.setAutoCommit(false);
			//Inserting into tasks table
			PreparedStatement ps_insTask = con.prepareStatement("INSERT INTO task (category_sl, taskName, description, endDate, priority, currentTime) VALUES (?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps_insTask.setInt(1, categorySl);
			ps_insTask.setString(2, bean.getTaskName());
			ps_insTask.setString(3, bean.getDescription());
			ps_insTask.setDate(4, new java.sql.Date(bean.getPlannedEndDate().getTime()));
			ps_insTask.setInt(5,bean.getPriority());
			ps_insTask.setString(6,"" + bean.getCurrentTime());
			ps_insTask.execute();
			ResultSet rs_insTask = ps_insTask.getGeneratedKeys();
			
			//Get generated taskSl
			int taskSl = -1;
			if(rs_insTask.next())
				taskSl = rs_insTask.getInt("slNo");
			if(taskSl == -1)
			{
				con.rollback();
				return "Oops task insertion failed!";
			}
			
			String[] tags = bean.getTags().split(",");
			//Inserting into tags table
			PreparedStatement ps_insTag = con.prepareStatement("INSERT INTO tag (task_sl,tagName) VALUES (?,?);");
			for(String tag : tags)
			{
				ps_insTag.setInt(1, taskSl);
				ps_insTag.setString(2, tag);
				ps_insTag.execute();
			}
			con.commit();
			return Constants.SUCCESS;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try 
			{
				con.rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			return e.getMessage();
		}
	}

	@Override
	public String updateTask(String oldTaskName, TaskBean bean, String categoryName) 
	{
	    try 
	    {
	        // Step 1: Get category_sl from category name
	        PreparedStatement ps_selCat = con.prepareStatement("SELECT slNo FROM category WHERE categoryName = ?;");
	        ps_selCat.setString(1, categoryName);
	        ps_selCat.execute();
	        ResultSet rs = ps_selCat.getResultSet();

	        int categorySl = -1;
	        if (rs.next()) 
	            categorySl = rs.getInt(1);
	        if (categorySl == -1)
	            return "Task update failed! Category not found.";

	        // Step 2: Get task_sl using oldTaskName and category_sl
	        PreparedStatement ps_selTask = con.prepareStatement("SELECT slNo FROM task WHERE category_sl = ? AND taskName = ?;");
	        ps_selTask.setInt(1, categorySl);
	        ps_selTask.setString(2, oldTaskName);
	        ps_selTask.execute();
	        ResultSet rs_task = ps_selTask.getResultSet();

	        int taskSl = -1;
	        if (rs_task.next()) 
	            taskSl = rs_task.getInt(1);
	        if (taskSl == -1) 
	            return "Task update failed! Task not found.";

	        con.setAutoCommit(false); // Start transaction

	        // Step 3: Update task details
	        PreparedStatement ps_updTask = con.prepareStatement(
	            "UPDATE task SET taskName = ?, description = ?, endDate = ?, priority = ?, currentTime = ? WHERE slNo = ?;"
	        );
	        ps_updTask.setString(1, bean.getTaskName());
	        ps_updTask.setString(2, bean.getDescription());
	        ps_updTask.setDate(3, new java.sql.Date(bean.getPlannedEndDate().getTime()));
	        ps_updTask.setInt(4, bean.getPriority());
	        ps_updTask.setString(5, "" + bean.getCurrentTime());
	        ps_updTask.setInt(6, taskSl);
	        ps_updTask.executeUpdate();

	        // Step 4: Delete old tags for this task
	        PreparedStatement ps_delTags = con.prepareStatement("DELETE FROM tag WHERE task_sl = ?;");
	        ps_delTags.setInt(1, taskSl);
	        ps_delTags.executeUpdate();

	        // Step 5: Insert new tags
	        PreparedStatement ps_insTag = con.prepareStatement("INSERT INTO tag (task_sl, tagName) VALUES (?,?);");
	        String[] tags = bean.getTags().split(",");
	        for (String tag : tags) 
	        {
	            ps_insTag.setInt(1, taskSl);
	            ps_insTag.setString(2, tag.trim());
	            ps_insTag.execute();
	        }

	        con.commit(); // Commit transaction
	        return Constants.SUCCESS;
	    } 
	    catch (SQLException e) 
	    {
	        e.printStackTrace();
	        try 
	        {
	            con.rollback(); // Rollback on failure
	        } 
	        catch (SQLException e1) 
	        {
	            e1.printStackTrace();
	        }
	        return "Task update failed: " + e.getMessage();
	    }
	}


	@Override
	public String deleteTask(String taskName, String categoryName) 
	{
		try
		{
			PreparedStatement ps_selCat = con.prepareStatement("SELECT slNo FROM category WHERE categoryName = ?;");
			ps_selCat.setString(1, categoryName);
			ps_selCat.execute();
			ResultSet rs = ps_selCat.getResultSet();
	
			int categorySl = -1;
			if(rs.next())
				categorySl = rs.getInt(1);
			if(categorySl == -1)
			{
				System.out.println("No tasks to delete. First add tasks");
				return "Task deletion failed. No tasks to delete.";
			}
			PreparedStatement ps_delTask = con.prepareStatement("DELETE FROM task WHERE category_sl = ? AND taskName = ?");
			ps_delTask.setInt(1, categorySl);
			ps_delTask.setString(2, taskName);
			ps_delTask.execute();
			return Constants.SUCCESS;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return "task deletion failed" + e.getMessage();
		}
	}

	@Override
	public void listTasks(String categoryName) 
	{
		try
		{
			PreparedStatement ps_selCat = con.prepareStatement("SELECT slNo FROM category WHERE categoryName = ?;");
			ps_selCat.setString(1, categoryName);
			ps_selCat.execute();
			ResultSet rs = ps_selCat.getResultSet();
	
			int categorySl = -1;
			if(rs.next())
				categorySl = rs.getInt(1);
			if(categorySl == -1)
			{
				System.out.println("No tasks to list. First add tasks");
				return;
			}
			
			PreparedStatement ps_selTask = con.prepareStatement("SELECT * FROM task WHERE category_sl = ?");
			ps_selTask.setInt(1, categorySl);
			ps_selTask.execute();
			rs = ps_selTask.getResultSet();
			while(rs.next())
			{
				int taskSl = rs.getInt("slNo");
				String taskName = rs.getString("taskName");
				String description = rs.getString("description");
				
				//tags
				PreparedStatement ps_selTag = con.prepareStatement("SELECT * FROM tag WHERE task_sl = ?");
				ps_selTag.setInt(1, taskSl);
				ps_selTag.execute();
				ResultSet rsTag = ps_selTag.getResultSet();
				StringBuilder sb = new StringBuilder();
				while(rsTag.next())
				{
					sb.append(rsTag.getString("tagName") + ",");
				}
				String tags = sb.toString().substring(0,sb.toString().length()-1);
				java.sql.Date endDate = rs.getDate("endDate");
				int priority = rs.getInt("priority");
				long currentTime = Long.parseLong(rs.getString("currentTime"));
				System.out.println(taskName + ":" + description + ":" + tags + ":" + endDate + ":" + priority + ":" + currentTime);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean searchTask(String taskName, String categoryName) 
	{
		return checkTaskExists(taskName, categoryName);
	}

}
