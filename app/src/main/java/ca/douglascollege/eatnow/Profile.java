package ca.douglascollege.eatnow;

import android.app.Application;
import android.content.Intent;
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
    int [] imagesProfile = new int[] {R.drawable.edit_profile, R.drawable.log_out};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PROFILE", "....STARTING PROFILE....");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("PROFILE", "....POPULATING HASH MAP....");
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for(int x=0;x<imagesProfile.length;x++)
        {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt", profileString[x]);
            hm.put("images", Integer.toString(imagesProfile[x]));
            aList.add(hm);
        }

        Log.d("PROFILE", "....CREATING ADAPTER....");
        String [] from = {"images", "text"};
        int [] to = {R.id.profileImage, R.id.textProfile};
        SimpleAdapter adapter =  new SimpleAdapter(Profile.this.getContext(), aList, R.layout.profile_layout_view, from, to);

        if(adapter==null){
            Toast.makeText(Profile.this.getContext(),"ADAPTER NULL", Toast.LENGTH_LONG).show();
            Log.e("PROFILE", "ADAPTER NULL");
        }

        Log.d("PROFILE", "....GETTING LIST VIEW....");
        ListView listView = (ListView) view.findViewById(R.id.profile_layout);
        if(listView==null){
            Toast.makeText(Profile.this.getContext(),"List NULL", Toast.LENGTH_LONG).show();
            Log.e("PROFILE", "LIST VIEW profile_layout NULL");
        }
        listView.setAdapter(adapter);

        Log.d("PROFILE", "....ADDING ACTIONS....");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.douglascollege.ca/")));
                        break;

                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sfu.ca/")));
                        break;
                }
            }
        });
        Log.d("PROFILE", "....RETURNING VIEW....");
    }
}
