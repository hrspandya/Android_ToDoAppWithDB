package com.hp.todoapp;

public enum StatusEnum {
	
	COMPLETE("COMPLETE"), 
	PENDING("PENDING"), 
	DELETED("DELETED");
	
	
    private String statusName;
    
    private StatusEnum(String status) { 
        this.statusName = status; 
    } 
    
    @Override 
    public String toString(){ 
        return statusName; 
    } 
}
