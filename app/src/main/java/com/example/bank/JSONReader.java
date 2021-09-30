package com.example.bank;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONReader {
        // Read the company.json file and convert it to a java object.
//        public static Places readPlaceJSONFile(Context context) throws IOException, JSONException {
//
//            // Read content of company.json
//            String jsonText = readText(context, R.raw.atm);
//
//            JSONObject jsonRoot = new JSONObject(jsonText);
//
//
//            int id= jsonRoot.getInt("id");
//            String address = jsonRoot.getString("address");
//            boolean available = jsonRoot.getBoolean("available");
//            String type = jsonRoot.getString("type");
//            String time = jsonRoot.getString("time");
//
////            JSONArray jsonArray = jsonRoot.getJSONArray("websites");
////            String[] websites = new String[jsonArray.length()];
//
////            for(int i=0;i < jsonArray.length();i++) {
////                websites[i] = jsonArray.getString(i);
////            }
//
////            JSONObject jsonAddress = jsonRoot.getJSONObject("ATM");
////            String addresss = jsonAddress.getString("address");
//
//            Places place = new Places();
//            place.setId(id);
//            place.setAddress(address);
//            place.setAvailable(available);
//            place.setType(type);
//            place.setTime(time);
//            return place;
//        }



//        private static String readText(Context context, int resId) throws IOException {
//            InputStream is = context.getResources().openRawResource(resId);
//            BufferedReader br= new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb= new StringBuilder();
//            String s = null;
//            while((  s = br.readLine()) != null) {
//                sb.append(s);
//                sb.append("\n");
//            }
//            return sb.toString();
//        }

    }