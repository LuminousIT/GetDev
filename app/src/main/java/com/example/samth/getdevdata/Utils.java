package com.example.samth.getdevdata;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

class Utils {

    static Context context;
    private static HttpURLConnection urlConnection = null;
    private static InputStream inputStream = null;

    private static String jsonResponse = "";
    private static ArrayList<GetDevData> devList = new ArrayList<>();

    private static final String link = "https://api.github.com/search/users?q=location:lagos+language:java";

    public static ArrayList<GetDevData> getDevDataList() {

        try {
            URL url = new URL(link);

            urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i("URL Connection", jsonResponse);
            }

            JSONObject mainJsonResponse = new JSONObject(jsonResponse);
            JSONArray itemsArray = mainJsonResponse.getJSONArray("items");

            for (int i=0; i<itemsArray.length(); i++) {
                JSONObject endpointObject = itemsArray.getJSONObject(i);

                String name = endpointObject.getString("login");
                String avatar = endpointObject.getString("avatar_url");
                String link = endpointObject.getString("html_url");

                Log.i("Data details", name);
                GetDevData devDetails = new GetDevData(name, link,avatar);
                devList.add(devDetails);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return devList;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        try {
//            String line = bufferedReader.readLine();
//
//            while (line != null) {
//                builder.append(line);
//                line = bufferedReader.readLine();
//            }

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }

        return builder.toString();
    }
}
