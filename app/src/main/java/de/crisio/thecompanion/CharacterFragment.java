package de.crisio.thecompanion;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Crisio on 25.08.2016.
 */
public class CharacterFragment extends android.support.v4.app.Fragment {

    public static CharacterFragment newInstance(){
        CharacterFragment fragment = new CharacterFragment();

        return fragment;
    }

    public CharacterFragment(){

    }

    Button go;
    EditText charname;
    ImageView charpic;
    String name;

    RadioButton rbeup;
    RadioButton rbeux;
    RadioButton rbusp;
    RadioButton rbusx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.character_fragment, container, false);



        go = (Button) rootView.findViewById(R.id.searchButton);
        charname = (EditText) rootView.findViewById(R.id.editText);
        charpic = (ImageView) rootView.findViewById(R.id.charpic);
        rbeup = (RadioButton) rootView.findViewById(R.id.eupcps);
        rbeux = (RadioButton) rootView.findViewById(R.id.euxbox);
        rbusp = (RadioButton) rootView.findViewById(R.id.uspcps);
        rbusx = (RadioButton) rootView.findViewById(R.id.usxbox);




        class CharPicAsync extends AsyncTask<String,Void,String> {
            String characterid =  "";
            String world = "";

            @Override
            protected String doInBackground(String... params) {

                try {
                    if(world == "eup"){
                        characterid = JSONReader.characterid(params[0]);
                    }else if(world == "eux"){
                        characterid = JSONReader.characteridx(params[0]);
                    }else if(world == "usp"){
                        characterid = JSONReader.characteridup(params[0]);
                    }else if(world == "usx"){
                        characterid = JSONReader.characteridux(params[0]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params[0];
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    if(result.length() <= 1){
                        Toast.makeText(getActivity(),"Enter Name", Toast.LENGTH_SHORT).show();
                    }else{
                        Picasso.with(getActivity()).load("http://census.daybreakgames.com/files/dcuo/images/character/paperdoll/"+ characterid +".png")
                                .tag(getContext())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(charpic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPreExecute() {
                if(rbeup.isChecked()){
                    world = "eup";
                }
                if(rbeux.isChecked()){
                    world = "eux";
                }
                if(rbusp.isChecked()){
                    world = "usp";
                }
                if(rbusx.isChecked()){
                    world = "usx";
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {}
        }

        go.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        go.getBackground().setColorFilter(0xff112564, PorterDuff.Mode.SRC_ATOP);
                        go.invalidate();
                        break;

                    }
                    case MotionEvent.ACTION_UP:{
                        name = charname.getText().toString();
                        new CharPicAsync().execute(check(name));
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        go.getBackground().clearColorFilter();
                        go.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        return rootView;
    }

    public static String check(String name){
        if(name.endsWith("%20")){
            name = name.substring(0, name.length()-3);
            check(name);
        }
        return name;
    }

    public static String worldid(String name){
        if(name.endsWith("&world_id=4") || name.endsWith("&world_id=2")){
            name = name.substring(0, name.length()-11);
            check(name);
        }else if(name.endsWith("&world_id=5001") || name.endsWith("&world_id=5002")){
            name = name.substring(0, name.length()-14);
            check(name);
        }
        return name;
    }
}
