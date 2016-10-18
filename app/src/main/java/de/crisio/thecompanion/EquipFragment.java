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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Crisio on 25.08.2016.
 */
public class EquipFragment extends android.support.v4.app.Fragment {

    public static EquipFragment newInstance(){
        EquipFragment fragment = new EquipFragment();

        return fragment;
    }


    public EquipFragment(){

    }


    TextView weaponcr;
    TextView weaponname;
    TextView headcr;
    TextView headname;
    TextView neckcr;
    TextView neckname;
    TextView backcr;
    TextView backname;
    TextView chestcr;
    TextView chestname;
    TextView legscr;
    TextView legsname;
    TextView trinketcr;
    TextView trinketname;
    TextView ring1cr;
    TextView ring1name;
    TextView ring2cr;
    TextView ring2name;
    TextView bootscr;
    TextView bootsname;
    TextView waistcr;
    TextView waistname;
    TextView handscr;
    TextView handsname;
    TextView shoulderscr;
    TextView shouldersname;
    TextView maskcr;
    TextView maskname;
    TextView totalcr;

    Button go2;
    EditText charname1;
    String name;

    RadioButton rbeup;

    RadioButton rbeux;

    RadioButton rbusp;

    RadioButton rbusx;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.equip_fragment, container, false);

        rbeup = (RadioButton) rootView.findViewById(R.id.rbequipeupcps);
        rbeux = (RadioButton) rootView.findViewById(R.id.rbequipeuxbox);
        rbusp = (RadioButton) rootView.findViewById(R.id.rbequipuspcps);
        rbusx = (RadioButton) rootView.findViewById(R.id.rbequipuscbox);



        go2 = (Button) rootView.findViewById(R.id.button);
        charname1 = (EditText) rootView.findViewById(R.id.editFeatText);

        totalcr = (TextView) rootView.findViewById(R.id.textView26);

        weaponcr = (TextView) rootView.findViewById(R.id.textView2);
        weaponname = (TextView) rootView.findViewById(R.id.textView3);

        headcr = (TextView) rootView.findViewById(R.id.textView23);
        headname = (TextView) rootView.findViewById(R.id.textView24);

        neckcr = (TextView) rootView.findViewById(R.id.textView20);
        neckname = (TextView) rootView.findViewById(R.id.textView21);

        backcr = (TextView) rootView.findViewById(R.id.textView17);
        backname = (TextView) rootView.findViewById(R.id.textView18);

        chestcr = (TextView) rootView.findViewById(R.id.textView14);
        chestname = (TextView) rootView.findViewById(R.id.textView15);

        legscr = (TextView) rootView.findViewById(R.id.textView11);
        legsname = (TextView) rootView.findViewById(R.id.textView12);

        trinketcr = (TextView) rootView.findViewById(R.id.textView8);
        trinketname = (TextView) rootView.findViewById(R.id.textView9);

        ring1cr = (TextView) rootView.findViewById(R.id.textView5);
        ring1name = (TextView) rootView.findViewById(R.id.textView6);

        ring2cr = (TextView) rootView.findViewById(R.id.textView32);
        ring2name = (TextView) rootView.findViewById(R.id.textView33);

        bootscr = (TextView) rootView.findViewById(R.id.textView38);
        bootsname = (TextView) rootView.findViewById(R.id.textView39);

        waistcr = (TextView) rootView.findViewById(R.id.textView44);
        waistname = (TextView) rootView.findViewById(R.id.textView45);

        handscr = (TextView) rootView.findViewById(R.id.textView47);
        handsname = (TextView) rootView.findViewById(R.id.textView48);

        shoulderscr = (TextView) rootView.findViewById(R.id.textView41);
        shouldersname = (TextView) rootView.findViewById(R.id.textView42);

        maskcr = (TextView) rootView.findViewById(R.id.textView35);
        maskname = (TextView) rootView.findViewById(R.id.textView36);

        go2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        go2.getBackground().setColorFilter(0xff112564, PorterDuff.Mode.SRC_ATOP);
                        go2.invalidate();
                        break;

                    }
                    case MotionEvent.ACTION_UP:{
                        name = charname1.getText().toString();
                        new EquipAsync().execute(CharacterFragment.check(name));
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        go2.getBackground().clearColorFilter();
                        go2.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        return rootView;
    }

    class EquipAsync extends AsyncTask<String,String,String[][]>{
        String world = "";

        @Override
        protected String[][] doInBackground(String... params) {
            try{
                String characterid = "";
                if(world == "eup"){
                    characterid = JSONReader.characterid(params[0]);
                    publishProgress(JSONReader.combatrating(params[0]).toString());
                }else if(world == "eux"){
                    characterid = JSONReader.characteridx(params[0]);
                    publishProgress(JSONReader.combatratingx(params[0]).toString());
                }else if(world == "usp"){
                    characterid = JSONReader.characteridup(params[0]);
                    publishProgress(JSONReader.combatratingup(params[0]).toString());
                }else if(world == "usx"){
                    characterid = JSONReader.characteridux(params[0]);
                    publishProgress(JSONReader.combatratingux(params[0]).toString());

                }
                String[][] equipment= JSONReader.equipment2(characterid);
                return equipment;
            }catch (Exception e){
                String[][] sh = new String[1][1];
                return sh;
            }

        }

        @Override
        protected void onPostExecute(String[][] result) {
            super.onPostExecute(result);
            try {

                weaponname.setText(result[17][0]);
                weaponcr.setText(result[17][1]);
            }catch(Exception e) {
            }
            try {
                headname.setText(result[0][0]);
                headcr.setText(result[0][1]);
            }catch(Exception e) {
            }
            try {
                neckname.setText(result[1][0]);
                neckcr.setText(result[1][1]);
            }catch(Exception e){}
            try {
                backname.setText(result[4][0]);
                backcr.setText(result[4][1]);
            }catch(Exception e){}
            try {
                chestname.setText(result[10][0]);
                chestcr.setText(result[10][1]);
            }catch(Exception e){}
            try {
                legsname.setText(result[11][0]);
                legscr.setText(result[11][1]);
            }catch(Exception e){}
            try {
                trinketname.setText(result[15][0]);
                trinketcr.setText(result[15][1]);
            }catch(Exception e){}
            try {
                ring1name.setText(result[12][0]);
                ring1cr.setText(result[12][1]);
            }catch (Exception e){}
            try {
                ring2name.setText(result[13][0]);
                ring2cr.setText(result[13][1]);
            }catch(Exception e){}
            try {
                bootsname.setText(result[7][0]);
                bootscr.setText(result[7][1]);
            }catch(Exception e){}
            try{
                waistname.setText(result[6][0]);
                waistcr.setText(result[6][1]);
            }catch (Exception e){}
            try{
                handsname.setText(result[5][0]);
                handscr.setText(result[5][1]);
            }catch(Exception e){}
            try{
                shouldersname.setText(result[3][0]);
                shoulderscr.setText(result[3][1]);
            }catch(Exception e){}
            try{
                maskname.setText(result[9][0]);
                maskcr.setText(result[9][1]);
            }catch(Exception e){
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
            try {
                totalcr.setText(values[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}