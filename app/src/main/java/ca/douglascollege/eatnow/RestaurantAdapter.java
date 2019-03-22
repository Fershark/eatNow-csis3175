package ca.douglascollege.eatnow;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.douglascollege.eatnow.database.Restaurant;

class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private static final String TAG = "RestaurantAdapter";
    private Context mContext;
    private  int mResource;
    private Location mUserLocation;

    public RestaurantAdapter(Context context, int resource, ArrayList<Restaurant> objects, Location userLocation) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mUserLocation = userLocation;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");

        int imageId = getItem(position).getImageId();
        String name = getItem(position).getName();
        String phone = getItem(position).getPhone();
        String type = getItem(position).getType();
        Double lat = getItem(position).getLatitude();
        Double lng = getItem(position).getLongitude();


        locationA.setLatitude(lat);
        locationA.setLongitude(lng);

        locationB.setLatitude(mUserLocation.getLatitude());
        locationB.setLongitude(mUserLocation.getLongitude());
        float distance = locationA.distanceTo(locationB);

        String strDistance = String.format("%.2f m",distance);


        Restaurant restaurant = new Restaurant(imageId,phone,name,type, lat, lng);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        ImageView imageView = convertView.findViewById(R.id.imageView1);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvType = convertView.findViewById(R.id.tvType);
        TextView tvDistance = convertView.findViewById(R.id.tvDistance);

        imageView.setImageResource(imageId);
        tvName.setText(name);
        tvType.setText(type);
        tvDistance.setText(strDistance);

        return convertView;
    }
}
