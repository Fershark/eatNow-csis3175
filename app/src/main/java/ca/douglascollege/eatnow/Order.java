package ca.douglascollege.eatnow;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class Order extends Fragment {

    EditText etAddress;

    public Order() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Order.
     */
    public static Order newInstance() {
        Order fragment = new Order();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
        etAddress = (EditText) v.findViewById(R.id.etAddress);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationAddress = etAddress.getText().toString();
                if (locationAddress.length() == 0) {
                    Toast.makeText(getActivity(),"Enter an Address",Toast.LENGTH_LONG).show();
                }
                else {
                    locationAddress += ", BC";
                    Geocoder geocoder = new Geocoder(Order.this.getContext(), Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
                        if (addressList != null && addressList.size() > 0) {
                            Address address = addressList.get(0);
                            Intent i = new Intent(getActivity(), Restaurants.class);
                            i.putExtra("latitude", address.getLatitude());
                            i.putExtra("longitude", address.getLongitude());
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getActivity(),"Enter a valid Address",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(),"Error Geocoder",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return v;
    }
}
