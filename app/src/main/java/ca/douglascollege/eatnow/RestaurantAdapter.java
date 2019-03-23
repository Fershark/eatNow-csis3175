package ca.douglascollege.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.douglascollege.eatnow.database.Restaurant;

class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context mContext;
    private int mResource;

    public RestaurantAdapter(Context context) {
        super(context, R.layout.adapter_view_layout);
        mContext = context;
        mResource = R.layout.adapter_view_layout;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        addAll(restaurants);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);

        int imageId = restaurant.getImageId();
        String name = restaurant.getName();
        String phone = restaurant.getPhone();
        String type = restaurant.getType();
        double lat = restaurant.getLatitude();
        double lng = restaurant.getLongitude();
        int distance = restaurant.getDistanceFromUser();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imageView1);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvType = convertView.findViewById(R.id.tvType);
        TextView tvDistance = convertView.findViewById(R.id.tvDistance);

        imageView.setImageResource(imageId);
        tvName.setText(name);
        tvType.setText(type);
        tvDistance.setText(mContext.getString(R.string.distanceFormat, Integer.toString(distance)));

        return convertView;
    }
}
