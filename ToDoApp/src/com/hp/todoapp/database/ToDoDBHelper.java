package com.hp.todoapp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hp.todoapp.R;
import com.hp.todoapp.Task;

public class ToDoDBHelper extends SQLiteOpenHelper {

	
	private static final int DB_VERSION = 1;

	// Database Name
	private static final String DB_NAME = "taskerManager";

	// Table Name
	private static final String TABLE_TASKS = "tasks";

	//Columns names inside tasks table
	private static final String ID = "id";
	private static final String TASKNAME = "taskName";
	private static final String STATUS = "status";
	
	
	public ToDoDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sqlQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
				+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASKNAME
				+ " TEXT, " + STATUS + " INTEGER)";
		db.execSQL(sqlQuery);
	
		
		
	}
	
	public List<Task> getAllTasks(){
		List<Task> taskList = new ArrayList<Task>();
		
		String query = "SELECT * FROM " + TABLE_TASKS;
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Task task = new Task();
				task.setId(cursor.getInt(0));
				task.setTaskName(cursor.getString(1));
				task.setStatus(cursor.getInt(2));
				// Adding contact to list
				taskList.add(task);
			} while (cursor.moveToNext());
		}

		// return task list
		return taskList;
	}

	
	// Adding new task
	public void addTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TASKNAME, task.getTaskName()); // task name
		// status of task- can be 0 for not done and 1 for done
		values.put(STATUS, task.getStatus());

		// Inserting Row
		db.insert(TABLE_TASKS, null, values);
		db.close(); // Closing database connection
	}
		
	
	public void updateTask(Task task) {
		// updating row
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TASKNAME, task.getTaskName());
		values.put(STATUS, task.getStatus());
		db.update(TABLE_TASKS, values, ID + " = ?",new String[] {String.valueOf(task.getId())});
		db.close();
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
