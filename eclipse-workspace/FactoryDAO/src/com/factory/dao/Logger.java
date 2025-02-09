package com.factory.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger 
{
	public static final boolean LOG_TO_MONITOR = true;
	
	public void log(String data)
	{
		new Thread
		(
			new Runnable() 
			{
				public void run() 
				{
					BufferedWriter bw = null;
					String str = data + " " + new Date();
					try
					{
						bw = new BufferedWriter(new FileWriter(Constants.LOG_FILE,true));
						bw.write(str);
						bw.newLine();
						if(LOG_TO_MONITOR)
							System.out.println(str);
					}
					catch(IOException e)
					{
						e.printStackTrace();
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
			}
		).start();
	}
	
}
