package edu.scu.detrasher;

import android.content.Context;
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

public class UserListAdapter extends ArrayAdapter<User> implements View.OnClickListener{

    private ArrayList<User> dataSet;
    Context mContext;
    int userRole;
    int loggedUserId;

    // View lookup cache
    private static class ViewHolder {
        ImageView statusIcon;
        TextView userName;
        TextView userData;
    }

    public UserListAdapter(ArrayList<User> data, Context context, int userRole, int loggedUserId) {
        super(context, R.layout.activity_detrasher_task_view_rowitem, data);
        this.dataSet = data;
        this.mContext=context;
        this.userRole = userRole;
        this.loggedUserId = loggedUserId;

    }

    @Override
    public void onClick(View v) {
        if(this.userRole == 1) {
            int position = (Integer) v.getTag();
            Object object = getItem(position);
            User userObj = (User) object;

            Snackbar.make(v, "User Name: "+userObj.get_user_name(), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User userObj = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_detrasher_task_view_rowitem, parent, false);
            viewHolder.statusIcon = (ImageView)convertView.findViewById(R.id.icon_progress);
            viewHolder.userData = (TextView) convertView.findViewById(R.id.staff_name);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.location_descr);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.userName.setText(userObj.get_user_full_name());
        viewHolder.userData.setText("Staff ID: "+userObj.get_user_id()+" Role: "+userObj.get_user_role_descr());
        viewHolder.statusIcon.setImageResource(R.drawable.user_profile);

        viewHolder.statusIcon.setTag(position);
        viewHolder.statusIcon.setOnClickListener(this);
        // Return the completed view to render on screen
        return convertView;
    }
}
