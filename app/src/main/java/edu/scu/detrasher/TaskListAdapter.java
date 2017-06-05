package edu.scu.detrasher;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Srivatsa on 02-06-2017.
 */

public class TaskListAdapter extends ArrayAdapter<Task> implements View.OnClickListener{

    private ArrayList<Task> dataSet;
    Context mContext;
    int userRole;

    // View lookup cache
    private static class ViewHolder {
        ImageView statusIcon;
        TextView trashID;
        TextView staffName;
    }

    public TaskListAdapter(ArrayList<Task> data, Context context, int userRole) {
        super(context, R.layout.activity_detrasher_task_view_rowitem, data);
        this.dataSet = data;
        this.mContext=context;
        this.userRole = userRole;
    }

    @Override
    public void onClick(View v) {
        if(this.userRole == 2) {
            int position = (Integer) v.getTag();
            Object object = getItem(position);
            Task taskObj = (Task) object;

            switch (v.getId()) {
                case R.id.icon_progress:
                    if(taskObj.get_task_completion_status() == 2) {
                        Snackbar.make(v, "This task is complete. No action required", Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
                    }
                    else
                    {
                        Intent taskManager = new Intent(mContext, DetrasherTaskManagerActivity.class);
                        taskManager.putExtra("task_id", taskObj.get_task_id());
                        taskManager.putExtra("userId", taskObj.get_task_user_id());
                        taskManager.putExtra("userRole", 2);
                        taskManager.putExtra("location_id", taskObj.get_task_location_id());
                        taskManager.putExtra("loc_descr", taskObj.get_task_location_descr());
                        taskManager.putExtra("trash_level", taskObj.get_task_trash_level());
                        taskManager.putExtra("assigned_to", taskObj.get_task_staff_name());
                        taskManager.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        taskManager.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(taskManager);
                    }
                    break;
            }
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task taskObj = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_detrasher_task_view_rowitem, parent, false);
            viewHolder.statusIcon = (ImageView)convertView.findViewById(R.id.icon_progress);
            viewHolder.staffName = (TextView) convertView.findViewById(R.id.staff_name);
            viewHolder.trashID = (TextView) convertView.findViewById(R.id.location_descr);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.staffName.setText(taskObj.get_task_staff_name());
        viewHolder.trashID.setText(taskObj.get_task_location_descr());
        if(taskObj.get_task_completion_status() == 2)
        {
            viewHolder.statusIcon.setImageResource(R.drawable.complete_task_icon);
        }
        else
        {
            viewHolder.statusIcon.setImageResource(R.drawable.inprogress_task_icon);
        }
        viewHolder.statusIcon.setTag(position);
        viewHolder.statusIcon.setOnClickListener(this);
        // Return the completed view to render on screen
        return convertView;
    }
}
