package de.crisio.thecompanion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Crisio on 08.09.16.
 */
class FriendAdapter extends ArrayAdapter<String>{


    public FriendAdapter(Context context, String[] resource) {
        super(context, R.layout.friend, resource);
    }

    TextView friendsname;
    TextView friendsLL;
    ImageView friendspic;
    String singleFriend;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater friendInflater = LayoutInflater.from(getContext());
        View friendView = friendInflater.inflate(R.layout.friend,parent,false);

        String singleFriend = null;
        try {
            singleFriend = URLDecoder.decode(CharacterFragment.worldid(getItem(position)),"UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        final TextView friendsname = (TextView) friendView.findViewById(R.id.friendsName);
        final TextView friendsLL = (TextView) friendView.findViewById(R.id.friendsLastLocation);
        final ImageView friendspic = (ImageView) friendView.findViewById(R.id.friendspic);


        friendsname.setText(singleFriend);


        class AdapterThings extends AsyncTask<Void, Void, String>{
            @Override
            protected String doInBackground(Void... params) {
                String text="";
                try {
                    text = JSONReader.getRegion(CharacterFragment.check(getItem(position).replaceAll(" ","%20")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return text;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    friendsLL.setText(result);

                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPreExecute() {}

            @Override
            protected void onProgressUpdate(Void... values) {}
        }




        try {
            String name = getItem(position);
            if(name.endsWith("&world_id=4")){
                name = name.substring(0, name.length()-11);
                Picasso.with(getContext())
                        .load("http://census.daybreakgames.com/files/dcuo/images/character/paperdoll/"+ JSONReader.characterids(CharacterFragment.check(name)+ "&world_id=4")+".png")
                        .resize(125,200)
                        .onlyScaleDown()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .tag(getContext())
                        .into(friendspic);
            }else if(name.endsWith("&world_id=2")){
                name = name.substring(0, name.length()-11);
                URLDecoder.decode(name,"UTF-8");
                Picasso.with(getContext())
                        .load("http://census.daybreakgames.com/files/dcuo/images/character/paperdoll/"+ JSONReader.characterids(CharacterFragment.check(name)+ "&world_id=2")+".png")
                        .resize(125,200)
                        .onlyScaleDown()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .tag(getContext())
                        .into(friendspic);
            }else if(name.endsWith("&world_id=5001")){
                name = name.substring(0, name.length()-14);
                Picasso.with(getContext())
                        .load("http://census.daybreakgames.com/files/dcuo/images/character/paperdoll/"+ JSONReader.characterids(CharacterFragment.check(name)+ "&world_id=5001")+".png")
                        .resize(125,200)
                        .onlyScaleDown()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .tag(getContext())
                        .into(friendspic);
            }else if(name.endsWith("&world_id=5002")){
                name = name.substring(0, name.length()-14);
                Picasso.with(getContext())
                        .load("http://census.daybreakgames.com/files/dcuo/images/character/paperdoll/"+ JSONReader.characterids(CharacterFragment.check(name)+ "&world_id=5002")+".png")
                        .resize(125,200)
                        .onlyScaleDown()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .tag(getContext())
                        .into(friendspic);
            }

            } catch (Exception e) {
            e.printStackTrace();
        }

        new AdapterThings().execute();



        return friendView;

    }



}
