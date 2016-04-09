package williamamills.colorify;

/**
 * Created by Nishant on 4/9/16.
 */
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InstagramAPIHelper extends AsyncTask<Void, Void, String> {

    /* Popular Endpoint*/


    protected void onPreExecute() {
        /* initialization before network call in background,
        * potentially do add different endpoints/search criteria */

    }

    protected String doInBackground(Void... urls) {

        String API_URL = "https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";
        try {
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    protected void onPostExecute(String response) {
        /* what do with the network call response,
         * potentially to store into sql db */
        if(response == null) {
            System.out.println("THERE WAS AN ERROR RETRIEVING JSON DATA");
        }
        else {
            try{
                JSONObject json = new JSONObject(response); //contains meta and data
                JSONArray data = json.getJSONArray("data"); //only get data, ignore meta

                for(int i =0;i<data.length();i++){
                    System.out.println(data.getJSONObject(i)); // print returned json objects
                }
                System.out.println(data.length()); //print number of objects returned (24)
            } catch(JSONException e){
                System.out.println("JSONERROR: " + e.getMessage());
            }

        }

    }

}

