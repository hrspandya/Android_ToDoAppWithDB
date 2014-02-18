package com.hp.todoapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hp.todoapp.database.ToDoDBHelper;


public class MainActivity extends Activity {

	public ListView listView;
	protected ToDoDBHelper db;
	protected TaskAdapter adapt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_taskList);
        db = new ToDoDBHelper(this);
        List<Task> list = db.getAllTasks();
        
        adapt = new TaskAdapter(this, R.layout.inner_task_list, list);
		listView.setAdapter(adapt);        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void addTaskNow(View v) {
		EditText t = (EditText) findViewById(R.id.et_enterTask);
		String s = t.getText().toString();
		if (s.equalsIgnoreCase("")) {
			Toast.makeText(this, "enter the task description first!!",
					Toast.LENGTH_LONG);
		} else {
			Task task = new Task(s, 0);
			db.addTask(task);
			Log.d("tasker", "data added");
			t.setText("");
			adapt.add(task);
			adapt.notifyDataSetChanged();
		}

	}
    
    
    
    
    private class TaskAdapter extends ArrayAdapter<Task> {

		Context context;
		List<Task> taskList = new ArrayList<Task>();
		int layoutResourceId;

		public TaskAdapter(Context context, int layoutResourceId,
				List<Task> objects) {
			super(context, layoutResourceId, objects);
			this.layoutResourceId = layoutResourceId;
			this.taskList = objects;
			this.context = context;
		}

		/**
		 * This method will DEFINe what the view inside the list view will
		 * finally look like Here we are going to code that the checkbox state
		 * is the status of task and check box text is the task name
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CheckBox chk = null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.inner_task_list,
						parent, false);
				chk = (CheckBox) convertView.findViewById(R.id.cb_task);
				convertView.setTag(chk);

				chk.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Task changeTask = (Task) cb.getTag();
						changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
						db.updateTask(changeTask);
						Toast.makeText(
								getApplicationContext(),
								"Clicked on Checkbox: " + cb.getText() + " is "
										+ cb.isChecked(), Toast.LENGTH_LONG)
								.show();
					}

				});
			} else {
				chk = (CheckBox) convertView.getTag();
			}
			Task current = taskList.get(position);
			chk.setText(current.getTaskName());
			chk.setChecked(current.getStatus() == 1 ? true : false);
			chk.setTag(current);
			Log.d("listener", String.valueOf(current.getId()));
			return convertView;
		}

	}
    
    
}
