package de.crisio.thecompanion;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Crisio on 19.09.16.
 */
public class StatsFragment extends android.support.v4.app.Fragment {
    public static StatsFragment newInstance(){
        StatsFragment fragment = new StatsFragment();
        return fragment;
    }


    public StatsFragment(){

    }


    TextView health;
    TextView power;
    TextView defense;
    TextView toughness;
    TextView might;
    TextView precision;
    TextView restoration;
    TextView vitalization;
    TextView dominance;
    TextView skillpoints;

    Button goStats;
    EditText charnamestats;
    String name;

    RadioButton rbeup;

    RadioButton rbeux;

    RadioButton rbusp;

    RadioButton rbusx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.stats_fragment, container, false);

        goStats = (Button) rootView.findViewById(R.id.button);
        charnamestats = (EditText) rootView.findViewById(R.id.editFeatText);

        skillpoints = (TextView) rootView.findViewById(R.id.tv26);

        health = (TextView) rootView.findViewById(R.id.tv2);

        power = (TextView) rootView.findViewById(R.id.tv23);

        defense = (TextView) rootView.findViewById(R.id.tv20);

        toughness= (TextView) rootView.findViewById(R.id.tv17);

        might = (TextView) rootView.findViewById(R.id.tv14);

        precision = (TextView) rootView.findViewById(R.id.tv11);

        restoration = (TextView) rootView.findViewById(R.id.tv8);

        vitalization = (TextView) rootView.findViewById(R.id.tv5);

        dominance = (TextView) rootView.findViewById(R.id.tv32);

        rbeup = (RadioButton) rootView.findViewById(R.id.rbstatsep);
        rbeux = (RadioButton) rootView.findViewById(R.id.rbstatsex);
        rbusp = (RadioButton) rootView.findViewById(R.id.rbstatsup);
        rbusx = (RadioButton) rootView.findViewById(R.id.rbstatsux);


        goStats.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        goStats.getBackground().setColorFilter(0xff112564, PorterDuff.Mode.SRC_ATOP);
                        goStats.invalidate();
                        break;

                    }
                    case MotionEvent.ACTION_UP:{
                        name = CharacterFragment.check(charnamestats.getText().toString());
                        new StatsAsync().execute(name);
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        goStats.getBackground().clearColorFilter();
                        goStats.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        return rootView;
    }

    class StatsAsync extends AsyncTask<String,String,String[]> {
        String world = "";


        @Override
        protected String[] doInBackground(String... params) {
            try{
                String[] stats = new String[9];
                if(world == "eup"){
                    stats= JSONReader.stats(params[0]);
                }else if(world == "eux"){
                    stats= JSONReader.statsx(params[0]);
                }else if(world == "usp"){
                    stats= JSONReader.statsup(params[0]);
                }else if(world == "usx"){
                    stats= JSONReader.statsux(params[0]);
                }
                return stats;
            }catch (Exception e){
                String[] sh = new String[1];
                return sh;
            }

        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            try {
                health.setText(result[0]);
                power.setText(result[1]);
                defense.setText(result[2]);
                toughness.setText(result[3]);
                might.setText(result[4]);
                precision.setText(result[5]);
                restoration.setText(result[6]);
                vitalization.setText(result[7]);
                dominance.setText(result[8]);
                skillpoints.setText(result[9]);
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
        protected void onProgressUpdate(String... values) {
        }
    }

}
