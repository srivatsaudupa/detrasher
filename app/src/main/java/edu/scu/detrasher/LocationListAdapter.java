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

public class LocationListAdapter extends ArrayAdapter<Location> implements View.OnClickListener{

    private ArrayList<Location> dataSet;
    Context mContext;
    int userRole;

    // View lookup cache
    private static class ViewHolder {
        ImageView statusIcon;
        TextView locationId;
        TextView trashLevel;
    }

    public LocationListAdapter(ArrayList<Location> data, Context context, int userRole) {
        super(context, R.layout.activity_detrasher_task_view_rowitem, data);
        this.dataSet = data;
        this.mContext=context;
        this.userRole = userRole;
    }

    @Override
    public void onClick(View v) {
        if(this.userRole == 1) {
            int position = (Integer) v.getTag();
            Object object = getItem(position);
            Task taskObj = (Task) object;

            switch (v.getId()) {
                case R.id.icon_progress:
                    Snackbar.make(v, "Release date " + taskObj.get_task_completion_status(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                    break;
            }
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Location locObj = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_detrasher_task_view_rowitem, parent, false);
            viewHolder.statusIcon = (ImageView)convertView.findViewById(R.id.icon_progress);
            viewHolder.trashLevel = (TextView) convertView.findViewById(R.id.staff_name);
            viewHolder.locationId = (TextView) convertView.findViewById(R.id.location_descr);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.trashLevel.setText("Current Trash Level " + locObj.get_location_trash_level());
        viewHolder.locationId.setText(locObj.get_location_name()+" L"+locObj.get_location_floor()+ " Trash No. "+locObj.get_location_trash_id());
        int trashLevel = locObj.get_location_trash_level();
        if(trashLevel <=  10)
        {
            viewHolder.statusIcon.setImageResource(R.drawable.trash_level_v_high);
        }
        else if(trashLevel > 10 && trashLevel <= 20)
        {
            viewHolder.statusIcon.setImageResource(R.drawable.trash_level_medium);
        }
        else
        {
            viewHolder.statusIcon.setImageResource(R.drawable.trash_level_low);
        }
        viewHolder.statusIcon.setOnClickListener(this);
        // Return the completed view to render on screen
        return convertView;
    }
}
