
package de.crisio.thecompanion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Crisio on 29.08.2016.
 */
public class FriendsFragment extends android.support.v4.app.Fragment{

        public static FriendsFragment newInstance(){
            FriendsFragment fragment = new FriendsFragment();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            return fragment;
        }


        public FriendsFragment(){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        Button buttonAdd;
        EditText editFriend;



    ArrayList<String> friendsNames;

        String name;

    RadioButton rbeup;
    RadioButton rbeux;
    RadioButton rbusp;
    RadioButton rbusx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Set<String> startset = new HashSet<>();
        friendsNames = new ArrayList<>(getActivity().getSharedPreferences(getActivity().getPackageName(),Context.MODE_PRIVATE).getStringSet("friends", startset));
        for(String tmp: friendsNames){
            System.out.println(tmp);
        }
        final View rootView = inflater.inflate(R.layout.friends_fragment, container, false);
        buttonAdd = (Button) rootView.findViewById(R.id.addbutton);
        editFriend = (EditText) rootView.findViewById(R.id.editFriendText);

        rbeup = (RadioButton) rootView.findViewById(R.id.rbfriendep);
        rbeux = (RadioButton) rootView.findViewById(R.id.rbfriendex);
        rbusp = (RadioButton) rootView.findViewById(R.id.rbfriendup);
        rbusx = (RadioButton) rootView.findViewById(R.id.rbfriendux);



        class FragmentThings extends AsyncTask<Void, Void, Void>{

            String[] friendsArray;
            ListAdapter friendAdapter;

            @Override
            protected Void doInBackground(Void... params) {

                friendsArray = friendsNames.toArray(new String[friendsNames.size()]);
                friendAdapter = new FriendAdapter(getActivity(), friendsArray);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                ListView friendsListView = (ListView) rootView.findViewById(R.id.friendsList);

                friendsListView.setAdapter(friendAdapter);


            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onProgressUpdate(Void... values) {

            }
        }

            new FragmentThings().execute();

        buttonAdd.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        buttonAdd.getBackground().setColorFilter(0xff112564, PorterDuff.Mode.SRC_ATOP);
                        buttonAdd.invalidate();
                        break;

                    }
                    case MotionEvent.ACTION_UP:{
                        String world = "";

                        if(rbeup.isChecked()){
                            world = "&world_id=4";
                        }
                        if(rbeux.isChecked()){
                            world = "&world_id=5002";
                        }
                        if(rbusp.isChecked()){
                            world = "&world_id=2";
                        }
                        if(rbusx.isChecked()){
                            world = "&world_id=5001";
                        }

                        try {
                            if (friendsNames.contains(CharacterFragment.check(URLEncoder.encode(editFriend.getText().toString(), "UTF-8")) + world)) {
                                friendsNames.remove(CharacterFragment.check(URLEncoder.encode(editFriend.getText().toString(), "UTF-8")) + world);
                            } else {
                                friendsNames.add(CharacterFragment.check(URLEncoder.encode(editFriend.getText().toString(), "UTF-8")) + world);
                            }
                        }catch(Exception e){

                        }
                        new FragmentThings().execute();
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        buttonAdd.getBackground().clearColorFilter();
                        buttonAdd.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


            return rootView;

        }


    @Override
    public void onStop() {
        super.onStop();

        Set<String> set = new HashSet<>(friendsNames);
        getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE).edit().putStringSet("friends", set).commit();

    }

}


