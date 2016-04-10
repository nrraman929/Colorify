package williamamills.colorify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Alexander on 4/9/2016.
 */
public class GetBitmap extends AsyncTask<String, Void, Bitmap> {
    protected void onPreExecute() {
        /* initialization before network call in background,
        * potentially do add different endpoints/search criteria */

    }
    Context ctx;
    Photo photo;
    public GetBitmap(Context context, Photo p){
        ctx = context;
        photo = p;
    }

    protected Bitmap doInBackground(String... urls) {

        try {
            //URL url = new URL(urls[0]);
           java.net.URL url = new java.net.URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }




    }

    protected void onPostExecute(Bitmap response) {
        /* what do with the network call response,
         * potentially to store into sql db */
        if(response == null) {
            Toast.makeText(ctx, "THERE WAS AN ERROR RETRIEVING JSON DATA", Toast.LENGTH_LONG).show();
            System.out.println("THERE WAS AN ERROR RETRIEVING JSON DATA");
        }
        else {
            photo.setBitmap(response);
            Intent i = new Intent(ctx, ItemsList.class);
            ArrayList<Bitmap> uris = new ArrayList<Bitmap>();
            uris.add(photo.bitmap);
            Bundle extras = new Bundle();
            extras.putParcelableArrayList("uris", uris);
            i.putExtras(extras);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);

        }

    }
}
