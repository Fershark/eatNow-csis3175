package ca.douglascollege.eatnow;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ca.douglascollege.eatnow.utilities.Validation;

public class Order extends Fragment {
    TextInputLayout tiAddress;
    EditText etAddress;
    Validation validation;

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
        Button btnSearch = v.findViewById(R.id.btnSearch);
        etAddress = v.findViewById(R.id.etAddress);
        tiAddress = v.findViewById(R.id.tiAddress);

        validation = new Validation(v.getContext());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = validation.validAddress(tiAddress, etAddress);

                if (address != null) {
                    Intent i = new Intent(getActivity(), Restaurants.class);
                    i.putExtra("latitude", address.getLatitude());
                    i.putExtra("longitude", address.getLongitude());
                    startActivity(i);
                }
            }
        });
        return v;
    }
}
