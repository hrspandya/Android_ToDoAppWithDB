package com.hp.todoapp;

public class Task {
	public String taskName;	
	public int id;
	public int status;
	
	public Task(){
		this.taskName=null;
		this.status=0;
	}
	public Task(String taskName, int status) {
		super();
		this.taskName = taskName;
		this.status = status;
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
