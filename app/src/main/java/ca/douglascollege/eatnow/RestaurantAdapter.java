package ca.douglascollege.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private static final String TAG = "RestaurantAdapter";
    private Context mContext;
    private  int mResource;

    public RestaurantAdapter(Context context, int resource, ArrayList<Restaurant> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int imageId = getItem(position).getImageId();
        String name = getItem(position).getName();
        String type = getItem(position).getType();
        String distance = getItem(position).getDistance();

        Restaurant restaurant = new Restaurant(imageId,name,type, distance);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        ImageView imageView = convertView.findViewById(R.id.imageView1);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvType = convertView.findViewById(R.id.tvType);
        TextView tvDistance = convertView.findViewById(R.id.tvDistance);

        imageView.setImageResource(imageId);
        tvName.setText(name);
        tvType.setText(type);
        tvDistance.setText(distance);

        return convertView;
    }
}
