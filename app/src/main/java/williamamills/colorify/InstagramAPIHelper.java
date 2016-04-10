package williamamills.colorify;

/**
 * Created by Nishant on 4/9/16.
 */
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InstagramAPIHelper extends AsyncTask<Void, Void, String> {

    /* Popular Endpoint*/
    String API_URL = "https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";
    Context ctx;
    MainActivity activity;

    protected void onPreExecute() {
        /* initialization before network call in background,
        * potentially do add different endpoints/search criteria */

    }
    public InstagramAPIHelper(MainActivity act, Context context){
        activity = act;
        ctx = context;
    }

    protected String doInBackground(Void... urls) {

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
            Toast.makeText(ctx, "THERE WAS AN ERROR RETRIEVING JSON DATA", Toast.LENGTH_LONG).show();
            System.out.println("THERE WAS AN ERROR RETRIEVING JSON DATA");
        }
        else {
            try{
                JSONObject json = new JSONObject(response); //contains meta and data
                JSONArray data = json.getJSONArray("data"); //only get data, ignore meta

                for(int i =0;i<data.length();i++){
                    System.out.println(data.getJSONObject(i)); // print returned json objects
                }

                JSONObject test = data.getJSONObject(1); //photo at index 1
                JSONObject images = test.getJSONObject("images");
                JSONObject thumbnail = images.getJSONObject("thumbnail");
                activity.setJSON(test);
                String thumbnail_url = thumbnail.getString("url");

                /* Uncomment to see the returned color of the photo at index 1 */
                //new ColorHelper().execute(thumbnail_url);

                System.out.println(data.length()); //print number of objects returned (~24)
            } catch(JSONException e){
                System.out.println("JSONERROR: " + e.getMessage());
            }

        }

    }

}

