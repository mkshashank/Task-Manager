package com.factory.dao;

import java.util.Date;
import java.util.Objects;

public class TaskBean 
{
	private String taskName;
	private String description;
	private String tags;
	private Date plannedEndDate;
	private int priority;
	private long currentTime;
	
	public TaskBean()
	{
		
	}
	
	public TaskBean(String taskName, String description, String tags, Date plannedEndDate, int priority, long currentTime) 
	{
		super();
		this.taskName = taskName;
		this.description = description;
		this.tags = tags;
		this.plannedEndDate = plannedEndDate;
		this.priority = priority;
		this.currentTime = currentTime;
	}

	@Override
	public String toString() 
	{
		return "TaskBean [taskName=" + taskName + ", description=" + description + ", tags=" + tags
				+ ", plannedEndDate=" + plannedEndDate + ", priority=" + priority + ", currentTime=" + currentTime
				+ "]";
	}
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(currentTime, description, plannedEndDate, priority, tags, taskName);
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskBean other = (TaskBean) obj;
		return currentTime == other.currentTime && Objects.equals(description, other.description)
				&& Objects.equals(plannedEndDate, other.plannedEndDate) && priority == other.priority
				&& Objects.equals(tags, other.tags) && Objects.equals(taskName, other.taskName);
	}
	
	public String getTaskName() 
	{
		return taskName;
	}
	
	public void setTaskName(String taskName) 
	{
		this.taskName = taskName;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public String getTags() 
	{
		return tags;
	}
	
	public void setTags(String tags) 
	{
		this.tags = tags;
	}
	
	public Date getPlannedEndDate() 
	{
		return plannedEndDate;
	}
	
	public void setPlannedEndDate(Date plannedEndDate) 
	{
		this.plannedEndDate = plannedEndDate;
	}
	
	public int getPriority() 
	{
		return priority;
	}
	
	public void setPriority(int priority) 
	{
		this.priority = priority;
	}
	
	public long getCurrentTime() 
	{
		return currentTime;
	}
	
	public void setCurrentTime(long currentTime) 
	{
		this.currentTime = currentTime;
	}
	
}
