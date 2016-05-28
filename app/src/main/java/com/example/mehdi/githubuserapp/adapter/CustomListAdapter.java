package com.example.mehdi.githubuserapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mehdi.githubuserapp.NextActivity;
import com.example.mehdi.githubuserapp.R;
import com.example.mehdi.githubuserapp.model.openshiftUser;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mehdi on 3/17/16.
 */
public class CustomListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List results;

    public CustomListAdapter(NextActivity nextActivity, List users) {

        context = nextActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        results = (List) users;
    }
    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Holder {
        TextView firstname, lastname, email;
        ImageView userImage;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.list_item, parent, false);
        holder.firstname = (TextView) rowView.findViewById(R.id.first_name);
        holder.lastname = (TextView) rowView.findViewById(R.id.last_name);
        holder.email = (TextView) rowView.findViewById(R.id.email);
        holder.userImage = (ImageView) rowView.findViewById(R.id.left_image);

        openshiftUser result = (openshiftUser) results.get(position);
        try {
            holder.firstname.setText(result.getFirstName().toString());
            holder.lastname.setText(result.getLastName().toString());
            holder.email.setText(result.getEmail().toString());
            Log.i("INFO" , "Loading Data!!!");
            Picasso.with(holder.userImage.getContext())
                    .load("http://api.randomuser.me/portraits/men/" + position +".jpg")
                    .error(android.R.drawable.stat_notify_error)
                    .placeholder(android.R.drawable.gallery_thumb)
                    .into(holder.userImage);

        } catch (Exception e) {
            e.printStackTrace();
        }



        return rowView;



    }
}
