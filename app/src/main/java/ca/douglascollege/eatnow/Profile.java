package ca.douglascollege.eatnow;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Profile extends Fragment {

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Profile.
     */
    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    String [] profileString = {"Edit Profile", "Log Out"};
    int [] imagesProfile = new int[] {R.drawable.icon_account, R.drawable.icon_power};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for(int x=0;x<imagesProfile.length;x++)
        {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("text", profileString[x]);
            hm.put("images", Integer.toString(imagesProfile[x]));
            aList.add(hm);
        }

        String [] from = {"images", "text"};
        int [] to = {R.id.imageView_profile, R.id.textView_profile};
        SimpleAdapter adapter =  new SimpleAdapter(Profile.this.getContext(), aList, R.layout.profile_layout, from, to);

        if(adapter==null){
            Toast.makeText(Profile.this.getContext(),"ADAPTER NULL", Toast.LENGTH_LONG).show();
        }
        ListView lv = (ListView) view.findViewById(R.id.listView_profile);
        if(lv==null){
            Toast.makeText(Profile.this.getContext(),"List NULL", Toast.LENGTH_LONG).show();
        }
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.douglascollege.ca/")));
                        break;

                    case 1:
                        SharedPreferences preferences = getActivity().getSharedPreferences("email", Context.MODE_PRIVATE);
                        preferences.edit().clear().commit();
                        startActivity(new Intent(getActivity(), Welcome.class));
                        break;
                }
            }
        });
        return view;
    }
}
