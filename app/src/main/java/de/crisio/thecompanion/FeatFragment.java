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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Crisio on 29.08.2016.
 */
public class FeatFragment extends android.support.v4.app.Fragment{

    public static FeatFragment newInstance(){
        FeatFragment fragment = new FeatFragment();


        return fragment;
    }


    public FeatFragment(){

    }

    TextView activeFeat;
    Button buttonFeat;
    EditText editFeat;
    ImageView featIV;
    String name;

    RadioButton rbeup;
    RadioButton rbeux;
    RadioButton rbusp;
    RadioButton rbusx;

    ExpandableListView expview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.feat_fragment, container, false);

        buttonFeat = (Button) rootView.findViewById(R.id.buttonFeatGo);
        editFeat = (EditText) rootView.findViewById(R.id.editFeatText);
        activeFeat = (TextView) rootView.findViewById(R.id.activefeat);
        featIV = (ImageView) rootView.findViewById(R.id.FeatIv);

        rbeup = (RadioButton) rootView.findViewById(R.id.rbfeatep);
        rbeux = (RadioButton) rootView.findViewById(R.id.rbfeatex);
        rbusp = (RadioButton) rootView.findViewById(R.id.rbfeatup);
        rbusx = (RadioButton) rootView.findViewById(R.id.rbfeatux);

        expview = (ExpandableListView) rootView.findViewById(R.id.feattree);

        //FeatTreeAdapter listAdapter = new FeatTreeAdapter(this, listDataHeader, listDataChild);

        class FeatAsync extends AsyncTask<String,Void,String> {
            long characterid;
            String text;
            String img;
            String world = "";

            @Override
            protected String doInBackground(String... params) {
                try {
                    if(world == "eup"){
                        characterid = Long.parseLong(JSONReader.characterid(params[0]));
                    }else if(world == "eux"){
                        characterid = Long.parseLong(JSONReader.characteridx(params[0]));
                    }else if(world == "usp"){
                        characterid = Long.parseLong(JSONReader.characteridup(params[0]));
                    }else if(world == "usx"){
                        characterid = Long.parseLong(JSONReader.characteridux(params[0]));
                    }
                    String[] jsonfeat = JSONReader.activefeat(characterid);
                    text = jsonfeat[1] + "\n \n" + jsonfeat[2];
                    img = jsonfeat[0];
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
                        Toast.makeText(editFeat.getContext(),"Enter Name", Toast.LENGTH_SHORT).show();
                    }else{
                        activeFeat.setText(text);
                        Picasso.with(getActivity()).load("http://census.daybreakgames.com" + img).into(featIV);
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

        buttonFeat.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        buttonFeat.getBackground().setColorFilter(0xff112564, PorterDuff.Mode.SRC_ATOP);
                        buttonFeat.invalidate();
                        break;

                    }
                    case MotionEvent.ACTION_UP:{
                        name = editFeat.getText().toString();
                        new FeatAsync().execute(CharacterFragment.check(name));
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        buttonFeat.getBackground().clearColorFilter();
                        buttonFeat.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        return rootView;
    }
}
