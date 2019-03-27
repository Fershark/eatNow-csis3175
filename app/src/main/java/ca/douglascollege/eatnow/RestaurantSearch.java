package ca.douglascollege.eatnow;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.recommendation.Recommendation;
import ca.douglascollege.eatnow.database.recommendation.RecommendationRepository;
import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.utilities.Helper;
import ca.douglascollege.eatnow.utilities.Validation;

public class RestaurantSearch extends Fragment {
    TextInputLayout tiAddress;
    EditText etAddress;
    Validation validation;

    public RestaurantSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RestaurantSearch.
     */
    public static RestaurantSearch newInstance() {
        RestaurantSearch fragment = new RestaurantSearch();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_search, container, false);
        Button btnSearch = v.findViewById(R.id.btnSearch);
        etAddress = v.findViewById(R.id.etAddress);
        tiAddress = v.findViewById(R.id.tiAddress);

        validation = new Validation(v.getContext());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = validation.validAddress(tiAddress, etAddress);

                if (address != null) {
                    //Get discount from user and initialize the order
                    Helper helper = new Helper(RestaurantSearch.this.getActivity());
                    User user = helper.getLoggedUser();
                    Order order = new Order(etAddress.getText().toString(), user.getId());
                    RecommendationRepository recommendationRepository = new RecommendationRepository(RestaurantSearch.this.getActivity().getApplicationContext());
                    Recommendation recommendation = recommendationRepository.findUnsedRecommendationByUser(user.getId());
                    if (recommendation != null)
                        order.setDiscount(Helper.RECOMMENDATION_DISCOUNT);

                    Intent i = new Intent(getActivity(), Restaurants.class);
                    i.putExtra("latitude", address.getLatitude());
                    i.putExtra("longitude", address.getLongitude());
                    i.putExtra("order", order);
                    startActivity(i);
                }
            }
        });
        return v;
    }
}
