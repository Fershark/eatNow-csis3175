package ca.douglascollege.eatnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.douglascollege.eatnow.utilities.Helper;

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
        SimpleAdapter adapter =  new SimpleAdapter(Profile.this.getContext(), aList, R.layout.adapter_profile_layout, from, to);

        ListView lv = (ListView) view.findViewById(R.id.listView_profile);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                switch (position)
                {
                    case 0:
                        i = new Intent(getActivity(), Register.class);
                        i.putExtra("isEdit", true);
                        startActivity(i);
                        break;
                    case 1:
                        Helper helper = new Helper(getActivity());
                        helper.removeLoggedUser();
                        i = new Intent(getActivity(), Welcome.class);
                        startActivity(i);
                        getActivity().finish();
                        break;
                }
            }
        });

        return view;
    }
}
