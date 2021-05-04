/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author REMS1DE
 */
public class Model {

    public ArrayList<String> covidDayInfo() throws MalformedURLException, ProtocolException, IOException, JSONException {
        JSONObject myResponse = getCovidToday("https://covid19.th-stat.com/api/open/today");
        ArrayList<String> daily = new ArrayList<String>();
        daily.add(String.valueOf(myResponse.getInt("Confirmed")));
        daily.add(String.valueOf(myResponse.getInt("Recovered")));
        daily.add(String.valueOf(myResponse.getInt("Hospitalized")));
        daily.add(String.valueOf(myResponse.getInt("Deaths")));
        daily.add(String.valueOf(myResponse.getInt("NewConfirmed")));
        daily.add(String.valueOf(myResponse.getInt("NewRecovered")));
        daily.add(String.valueOf(myResponse.getInt("NewHospitalized")));
        daily.add(String.valueOf(myResponse.getInt("NewDeaths")));
        daily.add(myResponse.getString("UpdateDate"));
        return daily;
    }

    public ArrayList<String> mostInfect() throws IOException, MalformedURLException, JSONException {
        JSONObject myResponse = getCovidToday("https://covid19.th-stat.com/api/open/timeline");
        JSONArray findmax = (JSONArray) myResponse.get("Data");
        JSONObject Obj = findmax.getJSONObject(0);
        int max = Obj.getInt("NewConfirmed");
        int row = 0;
        for (int i = 1; i < findmax.length(); i++) {
            Obj = findmax.getJSONObject(i);
            if (Obj.getInt("NewConfirmed")> max) {
                max = Obj.getInt("NewConfirmed");
                row = i;
            }
        }
        ArrayList<String> maxInfect = new ArrayList<String>();
        maxInfect.add(findmax.getJSONObject(row).getString("Date"));
        maxInfect.add(String.valueOf(max));
        return maxInfect;
    }
    
     public ArrayList<String> mostDeath() throws IOException, MalformedURLException, JSONException {
        JSONObject myResponse = getCovidToday("https://covid19.th-stat.com/api/open/timeline");
        JSONArray findmax = (JSONArray) myResponse.get("Data");
        JSONObject Obj = findmax.getJSONObject(0);
        int max = Obj.getInt("NewDeaths");
        int row = 0;
        for (int i = 1; i < findmax.length(); i++) {
            Obj = findmax.getJSONObject(i);
            if (Obj.getInt("NewDeaths")> max) {
                max = Obj.getInt("NewDeaths");
                row = i;
            }
        }
        ArrayList<String> maxInfect = new ArrayList<String>();
        maxInfect.add(findmax.getJSONObject(row).getString("Date"));
        maxInfect.add(String.valueOf(max));
        return maxInfect;
    }
     

    public JSONObject getCovidToday(String url) throws MalformedURLException, IOException, JSONException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse;
    }
    
    public ArrayList<Province> topInfect() throws IOException, JSONException{
        JSONObject myResponse = getCovidToday("https://covid19.th-stat.com/api/open/cases/sum");
        JSONObject findtop = (JSONObject) myResponse.get("Province");
        Iterator x = findtop.keys();
        JSONArray province = findtop.names();
        JSONArray value = new JSONArray();
        ArrayList<Province> list = new ArrayList<>();
        Province prov;
        while (x.hasNext()) {
            String key = (String) x.next();
            //value.put(findtop.get(key));
            prov = new Province(key,findtop.optInt(key));
            list.add(prov);
        }
        Collections.sort(list, new Comparator<Province>() {
            @Override
            public int compare(Province p1, Province p2) {
                return Integer.compare(p1.getPop(), p2.getPop());
            }
        });
        return list;
    }

}
