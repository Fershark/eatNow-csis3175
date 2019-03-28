package ca.douglascollege.eatnow;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.order.OrderRepository;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetailRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.database.restaurant.RestaurantRepository;
import ca.douglascollege.eatnow.utilities.Helper;

public class History extends Fragment {
    private ArrayList<Order> orders;
    private HashMap<Integer, Restaurant> restaurantHashMap;
    private OrderAdapter orderAdapter;
    private int userId;

    public History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment History.
     */
    public static History newInstance() {
        History fragment = new History();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ListView listView = view.findViewById(R.id.lvOrders);

        //Adapter
        orderAdapter = new OrderAdapter(this.getActivity());
        listView.setAdapter(orderAdapter);

        userId = -1;
        restaurantHashMap = new HashMap<>();
        getOrders();
        return view;
    }

    public void getOrders() {
        if (userId == -1) {
            Helper helper = new Helper(this.getActivity());
            userId = helper.getLoggedUser().getId();
        }
        OrderRepository orderRepository = new OrderRepository(this.getActivity().getApplicationContext());
        orderRepository.findOrderByUser(userId).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                OrderDetailRepository orderDetailRepository = new OrderDetailRepository(History.this.getActivity().getApplicationContext());
                RestaurantRepository restaurantRepository = new RestaurantRepository(History.this.getActivity().getApplicationContext());
                Restaurant restaurant;
                for (Order order : orders) {
                    if (!restaurantHashMap.containsKey(order.getRestaurantId())) {
                        restaurant = restaurantRepository.getRestaurant(order.getRestaurantId());
                        restaurantHashMap.put(restaurant.getId(), restaurant);
                    }
                    order.setOrderDetailsCount(orderDetailRepository.countByOrder(order.getId()));
                }
                orderAdapter.setOrders(orders, restaurantHashMap);
            }
        });
    }
}
