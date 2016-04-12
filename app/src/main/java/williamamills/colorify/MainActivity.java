package williamamills.colorify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    Button mainButton;
    JSONArray j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = (Button) findViewById(R.id.main_enter);
        final JSONArray jArrayFacebookData = new JSONArray();
        final JSONObject jObjectType = new JSONObject();
        try {
            // put elements into the object as a key-value pair
            jObjectType.put("type", "instagram_json");

            jArrayFacebookData.put(jObjectType);

            // 2nd array for user information
            JSONObject jObjectData = new JSONObject();

            // Create Json Object using Facebook Data
            jObjectData.put("facebook_user_id", "id");
            jObjectData.put("first_name", "Alexander");
            jObjectData.put("last_name", "Mills");
            jObjectData.put("email", "wamills1@gmail.com");
            jObjectData.put("username", "wamills");
            jObjectData.put("birthday", "january");
            jObjectData.put("gender", "Male");
            jObjectData.put("location", "Austin");
            jObjectData.put("display_photo", "a");
            jArrayFacebookData.put(jObjectData);
        }catch(Exception e){

        }
        mainButton.setText("ImageListView Test");
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ItemsList.class);
                ArrayList<Bitmap> uris = new ArrayList<Bitmap>();
                Bundle extras = new Bundle();
                ArrayList<Photo> testPhotoList = new ArrayList<Photo>();
                for(Integer j = 0; j < 6; j++) {
                    Photo p = new Photo(j.toString(), "", "", "");
                    testPhotoList.add(p);
                }
                GetBitmap g = new GetBitmap(getApplicationContext(), testPhotoList);
                try {
                    g.execute("https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/12445772_589126311254658_1821092435_n.jpg", "http://assets9.pop-buzz.com/2015/50/neko-atsume-header-1450446679-responsive-large-0.jpg", "http://nerdist.com/wp-content/uploads/2015/12/Supernatural-Neko-Atsume-12302015.jpg","http://i.imgur.com/N7gJaR0.jpg","https://i.imgur.com/WJED9LY.png","http://i.imgur.com/H5j8qy6.jpg").get();
                    g.get(1000, TimeUnit.MILLISECONDS);
                }catch(Exception e){

                }
               // startActivity(i);
            }
        });
        Button b2 = (Button) findViewById(R.id.b2);
        b2.setText("Instagram Test");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstagramAPIHelper iHelper = new InstagramAPIHelper(MainActivity.this, getApplicationContext());
                iHelper.execute();
            }
        });

    }
    public void setJSON(String[] obj, ArrayList<Photo> photoList){
        GetBitmap g = new GetBitmap(getApplicationContext(), photoList);
        try {
            g.execute(obj);
            g.get(1000, TimeUnit.MILLISECONDS);
        }catch(Exception e){

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
