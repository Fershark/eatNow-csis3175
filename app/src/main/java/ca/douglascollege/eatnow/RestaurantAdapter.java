package ca.douglascollege.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ca.douglascollege.eatnow.database.restaurant.Restaurant;

class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context mContext;
    private int mResource;
    DecimalFormat decimalFormat;

    public RestaurantAdapter(Context context) {
        super(context, R.layout.adapter_restaurant_layout);
        mContext = context;
        mResource = R.layout.adapter_restaurant_layout;
        decimalFormat = new DecimalFormat("#,###.##");
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        addAll(restaurants);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);

        String imageString = restaurant.getImage();
        String name = restaurant.getName();
        String phone = restaurant.getPhone();
        String type = restaurant.getType();
        double lat = restaurant.getLatitude();
        double lng = restaurant.getLongitude();
        float distance = restaurant.getDistanceFromUser();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgLogo);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtType = convertView.findViewById(R.id.txtType);
        TextView txtDistance = convertView.findViewById(R.id.txtDistance);

        imageView.setImageResource(mContext.getResources().getIdentifier(imageString, "drawable", mContext.getPackageName()));
        txtName.setText(name);
        txtType.setText(type);
        txtDistance.setText(mContext.getString(R.string.distanceFormat, decimalFormat.format(distance)));

        return convertView;
    }
}
