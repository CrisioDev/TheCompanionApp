package de.crisio.thecompanion;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Crisio on 25.08.2016.
 */




public  class JSONReader{


        /*


    THE JSON READER APRIL 2016


     */



    /*
    public static String[] Equipment(long characterid, int i) throws IOException, JSONException{ //prints out whole Loadout
        String equipid;
        String[] retu = new String[2];
            try{
                equipid=JsonCall("http://census.daybreakgames.com/s:crisio/get/dcuo/characters_item/?equipment_slot_id="+Integer.toString(i)+"&","character_id=E",Long.toString(characterid))[0];
                retu=(JsonCall("http://census.daybreakgames.com/s:crisio/get/dcuo/item/?","item_id=",equipid));
                return retu;

            }
            catch(JSONException e) {
                //
            }
        return retu;
    }

    public static String[] Feats(long characterid) throws IOException, JSONException{ //prints out active Feat with Description -- still Problems with API
        String featid;
        String[] retu = new String[3];
        try{
            featid = JsonCall("http://census.daybreakgames.com/s:crisio/get/dcuo/characters_active_feat/?","character_id=F",Long.toString(characterid))[0];//Feat_id ausgelesen
            System.out.println(featid);
            retu = JsonCall("http://census.daybreakgames.com/s:crisio/get/dcuo/feat/?","feat_id=",featid); //Gibt Feat Namen aus
        }catch (JSONException e){
            //
        }
        return retu;
    }




    public static String[] JsonCall(String url, String call, String id) throws IOException, JSONException{//besorgt die Dateien von der richtigen ID und ruft den Unloader auf
        if(call == "name="){
            JSONObject VerschachtelterCharacter = readJsonFromUrl(url +  call + id);
            String[] Charakter = new String[1];
            Charakter[0] = JsonUnload(VerschachtelterCharacter, "character_list",0);
            return Charakter;
        }

        if(call == "name=cr"){
            JSONObject VerschachtelterCharacter = readJsonFromUrl(url +  "name=" + id);
            String[] Charakter = new String[1];
            Charakter[0] = JsonUnload(VerschachtelterCharacter, "character_list",15);
            return Charakter;
        }

        if(call == "character_id=E"){
            JSONObject VerschachtelterCharacter = readJsonFromUrl(url +  "character_id=" + id);
            String[] Charakter = new String[1];
            Charakter[0] = JsonUnload(VerschachtelterCharacter, "characters_item_list", 3);
            return Charakter;
        }
        if(call == "character_id=F"){
            JSONObject VerschachtelterCharacter = readJsonFromUrl(url +  "character_id=" + id);
            String[] Charakter = new String[1];
            Charakter[0] = JsonUnload(VerschachtelterCharacter, "characters_active_feat_list", 1);
            return Charakter;
        }
        if(call == "item_id="){
            JSONObject VerschachtelterCharacter = readJsonFromUrl(url +  call + id);
            String[] Charakter = new String[2];
            Charakter[0] = JsonUnload(VerschachtelterCharacter, "item_list", 2);
            Charakter[1] = JsonUnload(VerschachtelterCharacter, "item_list", 4);
            return Charakter;
        }
        if(call == "feat_id="){
            JSONObject VerschachtelterCharacter = readJsonFromUrl(url +  call + id);
            String[] Charakter = new String[3];
            Charakter[0] = JsonUnload(VerschachtelterCharacter, "feat_list", 1);
            Charakter[1] = JsonUnload(VerschachtelterCharacter, "feat_list", 2);
            VerschachtelterCharacter = readJsonFromUrl(url +  call + id + "&c:show=image_path");
            Charakter[2] = JsonUnload(VerschachtelterCharacter, "feat_list", 0);

            return Charakter;
        }


        else{
            String wrong[] = new String[1];
            return wrong;
        }
    }

    public static String JsonUnload(JSONObject json, String liste, int i) throws JSONException{
        JSONObject Character = json.getJSONArray(liste).getJSONObject(0);  //Character ist der unverschachtelte Array
        try{
            //getNames
            //neuimplementiert weil android studio retarded
            int length = Character.length();
            if (length == 0) {
                return null;
            }
            Iterator iterator = Character.keys();
            String[] names = new String[length];
            int j = 0;
            while (iterator.hasNext()) {
                names[j] = (String)iterator.next();
                j += 1;
            }
            return (String) Character.get(names[i]);
        }
        catch(ClassCastException e){
            //getNames
            //neuimplementiert weil android studio retarded
            int length = Character.length();
            if (length == 0) {
                return null;
            }
            Iterator iterator = Character.keys();
            String[] names = new String[length];
            int j = 0;
            while (iterator.hasNext()) {
                names[j] = (String)iterator.next();
                j += 1;
            }


            JSONObject newjson = (JSONObject) (Character.get(names[i]));
            return (String) newjson.get("en");
        }
    }


    public static String[] JsonUnload(JSONObject json, String liste) throws JSONException{
        JSONObject Character = json.getJSONArray(liste).getJSONObject(0);  //Character ist der unverschachtelte Array
        String returnArray [] = new String[Character.length()-1];
        int i= 0;
        while(i < returnArray.length ){
            try{
                //getNames
                //neuimplementiert weil android studio retarded
                int length = Character.length();
                if (length == 0) {
                    return null;
                }
                Iterator iterator = Character.keys();
                String[] names = new String[length];
                int j = 0;
                while (iterator.hasNext()) {
                    names[j] = (String)iterator.next();
                    j += 1;
                }
                returnArray[i] = (String) Character.get(names[i]);
                i++;
            }
            catch(ClassCastException e){ //faengt die verschieden sprachigen Namen ab
                //getNames
                //neuimplementiert weil android studio retarded
                int length = Character.length();
                if (length == 0) {
                    return null;
                }
                Iterator iterator = Character.keys();
                String[] names = new String[length];
                int j = 0;
                while (iterator.hasNext()) {
                    names[j] = (String)iterator.next();
                    j += 1;
                }
                JSONObject newjson = (JSONObject) (Character.get(names[i]));
                returnArray[i] = (String) newjson.get("en");
                i++;
            }
        }
        return returnArray;
    }

    public static JSONObject readJsonFromUrl(String url)throws IOException, JSONException{
        InputStream is = new URL(url).openStream();
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String readAll(Reader rd) throws IOException{
        StringBuilder sb = new StringBuilder();
        int cp;
        while((cp = rd.read()) != -1){
            sb.append((char) cp);
        }
        return sb.toString();
    }
    */

    /*

    END OF JSON READER

     */

    /*

    NEW GSON READER

    My hope to add performance.

     */



    private static String readUrl(String urlString) throws Exception {

        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    //cr eu pcps
    public static String combatrating(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=4");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("combat_rating").getAsString());
    }

    //cr eu xbox
    public static String combatratingx(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=5002");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("combat_rating").getAsString());
    }

    //cr us pspc
    public static String combatratingup(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=2");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("combat_rating").getAsString());
    }

    //cr us xbox
    public static String combatratingux(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=5001");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("combat_rating").getAsString());
    }

    public static String getRegion(String name)throws Exception{
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name );
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        try {
            return (getRegionName(clist.get("region_id").getAsString()));
        }catch(Exception e){
            return "";
        }
    }

    private static String getRegionName(String region_id) throws Exception{
        try {
            String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/region/?region_id=" + region_id);
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("region_list");
            JsonObject clist = (JsonObject) lis.get(0);
            JsonObject name = (JsonObject) (clist.get("name"));

            return (name.get("en").getAsString());
        }catch(Exception e){
            return "Hiding very good...";
        }
    }

    //charid eu pcps
    public static String characterid(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=4");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("character_id").getAsString());
    }

    //charid eu xbox
    public static String characteridx(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=5002");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("character_id").getAsString());
    }

    //charid us pcps
    public static String characteridup(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=2");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("character_id").getAsString());
    }

//http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name=%C2%BBSky+Girl%C2%AB&world_id=4
    public static String characterids(String name)throws Exception{
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name);
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("character_id").getAsString());
    }

    //charid us xbox
    public static String characteridux(String name)throws Exception{
        name = URLEncoder.encode(name, "UTF-8");
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=5001");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("character_list");
        JsonObject clist = (JsonObject) lis.get(0);
        return(clist.get("character_id").getAsString());
    }

    public static String[] activefeat(long charid) throws Exception{
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo:v1/characters_active_feat/?character_id=" + Long.toString(charid));
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("characters_active_feat_list");
        JsonObject clist = (JsonObject) lis.get(0);
        String featid = (clist.get("feat_id").getAsString());
        return featname(featid);
    }

    private static String[] featname(String featid) throws Exception{
        String[] feat = new String[3];
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo:v1/feat/?feat_id=" + featid);
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("feat_list");
        JsonObject clist = (JsonObject) lis.get(0);
        feat[0] = (clist.get("image_path").getAsString());
        JsonObject name = (JsonObject)clist.get("name");
        feat[1] = (name.get("en").getAsString());
        JsonObject description = (JsonObject)clist.get("description");
        feat[2] = (description.get("en").getAsString());
        return feat;

    }

    public static String[][] equip(String charid){
        String[][] result = new String[22][3];
        for(int i = 0; i < 21; i++){
            try{
                String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/characters_item/?character_id=" +charid + "&equipment_slot_id=" + Integer.toString(i));
                Gson gson = new Gson();
                JsonElement element = gson.fromJson (json, JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();
                JsonArray lis = (JsonArray) jsonObj.get("characters_item_list");
                JsonObject clist = (JsonObject) lis.get(0);
                String equipid = (clist.get("item_id").getAsString());
                result[i] = equipname(equipid);
            }catch(Exception e){
                //Do Nothing
            }

        }
        return result;
    }

    // http://census.daybreakgames.com/get/dcuo/characters_item/?character_id=1125912796598500&c:limit=30&c:tree=equipment_slot_id&c:join=item
    public static String[][] equipment2(String charid){
        String json = null;
        String[][] result = new String[30][3];
        try {
            json = readUrl("http://census.daybreakgames.com/get/dcuo/characters_item/?character_id=" +charid + "&c:limit=30&c:tree=equipment_slot_id&c:join=item");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("characters_item_list");
            JsonObject clist = (JsonObject) lis.get(0);
            for(int i = 0; i < 30; i++){
                try{
                    JsonObject equiparr = (JsonObject)(clist.get(Integer.toString(i)));
                    JsonObject joinarr = (JsonObject) equiparr.get("item_id_join_item");
                    JsonObject name = (JsonObject)joinarr.get("name");
                    result[i][0]=(name.get("en").getAsString());
                    result[i][1]=(joinarr.get("item_level")).getAsString();
                }catch(Exception e){
                    result[i][0] = "not found!";
                    result[i][1] = "not found!";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }        return result;
    }

    //laedt kompletten FeatTree
    public static String[][] feattree(String charid){
        String json = null;
        String[][] result = new String[30][3];
        try {
            json = readUrl("http://census.daybreakgames.com/get/dcuo/feat?c:limit=5000&c:tree=feat_id&c:join=feat_category");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("feat_list");
            JsonObject clist = (JsonObject) lis.get(0);
            Set<Map.Entry<String, JsonElement>> entrySet = clist.entrySet();
            for(Map.Entry<String, JsonElement> entry : entrySet) {
                try{
                    JsonObject feats = (JsonObject) clist.get(entry.getKey());
                    JsonObject featname = (JsonObject) feats.get("name");
                    System.out.println(featname.get("en").getAsString());
                    JsonObject description = (JsonObject) feats.get("description");
                    System.out.println(description.get("en").getAsString());
                    JsonObject fcij = (JsonObject) feats.get("feat_category_id_join_feat_category");
                    JsonObject categoryname = (JsonObject) fcij.get("category_name");
                    System.out.println(categoryname.get("en").getAsString());
                }catch(Exception e){
                    //not found
                }
            }
        }catch(Exception e){
            System.out.println("failed!");
        }
        return null;
    }

    //abgeschlossene Feats
    public static String[][] completedfeattree(String charid){
        String json = null;
        String[][] result = new String[30][3];
        try {
            json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo:v1/characters_completed_feat/?character_id=" +charid +"&c:limit=1000&c:tree=feat_id&c:join=feat");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("characters_completed_feat_list");
            JsonObject clist = (JsonObject) lis.get(0);
            Set<Map.Entry<String, JsonElement>> entrySet = clist.entrySet();
            for(Map.Entry<String, JsonElement> entry : entrySet) {
                try{
                    JsonObject feats = (JsonObject) clist.get(entry.getKey());
                    JsonObject featjoin = (JsonObject) feats.get("feat_id_join_feat");
                    JsonObject featname = (JsonObject) featjoin.get("name");
                    System.out.println(featname.get("en").getAsString());
                }catch(Exception e){
                    //not found
                }
            }
        }catch(Exception e){
            System.out.println("failed!");
        }
        return null;
    }

    //eu pcps stats
    public static String[] stats(String name){
        String json = null;
        try {
            name = URLEncoder.encode(name, "UTF-8");
            json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=4");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("character_list");
            JsonObject clist = (JsonObject) lis.get(0);
            String[] retString = new String[10];
            retString[0] = clist.get("max_health").getAsString();
            retString[1] = clist.get("max_power").getAsString();
            retString[2] = clist.get("defense").getAsString();
            retString[3] = clist.get("toughness").getAsString();
            retString[4] = clist.get("might").getAsString();
            retString[5] = clist.get("precision").getAsString();
            retString[6] = clist.get("restoration").getAsString();
            retString[7] = clist.get("vitalization").getAsString();
            retString[8] = clist.get("dominance").getAsString();
            retString[9] = clist.get("skill_points").getAsString();
            return(retString);
        } catch (Exception e) {
            String[] retString = new String[10];
            return(retString);
        }
    }

    //eu xbox stats
    public static String[] statsx(String name){
        String json = null;
        try {
            name = URLEncoder.encode(name, "UTF-8");
            json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=5002");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("character_list");
            JsonObject clist = (JsonObject) lis.get(0);
            String[] retString = new String[10];
            retString[0] = clist.get("max_health").getAsString();
            retString[1] = clist.get("max_power").getAsString();
            retString[2] = clist.get("defense").getAsString();
            retString[3] = clist.get("toughness").getAsString();
            retString[4] = clist.get("might").getAsString();
            retString[5] = clist.get("precision").getAsString();
            retString[6] = clist.get("restoration").getAsString();
            retString[7] = clist.get("vitalization").getAsString();
            retString[8] = clist.get("dominance").getAsString();
            retString[9] = clist.get("skill_points").getAsString();
            return(retString);
        } catch (Exception e) {
            String[] retString = new String[10];
            return(retString);
        }
    }
    //US pcps stats
    public static String[] statsup(String name){
        String json = null;
        try {
            name = URLEncoder.encode(name, "UTF-8");
            json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=2");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("character_list");
            JsonObject clist = (JsonObject) lis.get(0);
            String[] retString = new String[10];
            retString[0] = clist.get("max_health").getAsString();
            retString[1] = clist.get("max_power").getAsString();
            retString[2] = clist.get("defense").getAsString();
            retString[3] = clist.get("toughness").getAsString();
            retString[4] = clist.get("might").getAsString();
            retString[5] = clist.get("precision").getAsString();
            retString[6] = clist.get("restoration").getAsString();
            retString[7] = clist.get("vitalization").getAsString();
            retString[8] = clist.get("dominance").getAsString();
            retString[9] = clist.get("skill_points").getAsString();
            return(retString);
        } catch (Exception e) {
            String[] retString = new String[10];
            return(retString);
        }
    }
    //US xbox stats
    public static String[] statsux(String name){
        String json = null;
        try {
            name = URLEncoder.encode(name, "UTF-8");
            json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/character/?name="+ name +"&world_id=5001");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (json, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            JsonArray lis = (JsonArray) jsonObj.get("character_list");
            JsonObject clist = (JsonObject) lis.get(0);
            String[] retString = new String[10];
            retString[0] = clist.get("max_health").getAsString();
            retString[1] = clist.get("max_power").getAsString();
            retString[2] = clist.get("defense").getAsString();
            retString[3] = clist.get("toughness").getAsString();
            retString[4] = clist.get("might").getAsString();
            retString[5] = clist.get("precision").getAsString();
            retString[6] = clist.get("restoration").getAsString();
            retString[7] = clist.get("vitalization").getAsString();
            retString[8] = clist.get("dominance").getAsString();
            retString[9] = clist.get("skill_points").getAsString();
            return(retString);
        } catch (Exception e) {
            String[] retString = new String[10];
            return(retString);
        }
    }

    private static String[] equipname(String equipid) throws Exception {
        String[] result = new String[3];
        String json = readUrl("http://census.daybreakgames.com/s:crisio/get/dcuo/item/?item_id=" + equipid);
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (json, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        JsonArray lis = (JsonArray) jsonObj.get("item_list");
        JsonObject clist = (JsonObject) lis.get(0);
        String itemlevel = (clist.get("item_level").getAsString());
        String itempath = (clist.get("image_path").getAsString());
        JsonObject name = (JsonObject)(clist.get("name"));
        String itemname = (name.get("en").getAsString());
        result[0] = itemname;
        result[1] = itemlevel;
        result[2] = itempath;
        return result;
    }



    /*

    END OF GSON READER

     */
}