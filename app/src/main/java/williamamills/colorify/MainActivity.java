package williamamills.colorify;

import android.app.Activity;
import android.content.Intent;
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
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent i = new Intent(getApplicationContext(), ItemsList.class);
                ArrayList<Uri> uris = new ArrayList<Uri>();
                // fill uris
                String s = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
                for(int k = 0; k < 10; k++) {
                    Uri img = Uri.fromFile(new File(s + "/Camera/IMG_20160327_164935.jpg"));
                    uris.add(img);
                    img = Uri.fromFile(new File(s + "/Camera/IMG_20160327_165018.jpg"));
                    uris.add(img);
                }
                Bundle extras = new Bundle();
                extras.putParcelableArrayList("uris", uris);
                i.putExtras(extras);
                startActivity(i);*/
                /*DBHelper mydb = DBHelper.getInstance(getApplicationContext());
                mydb.insertPhoto(jObjectType, "First");*/
                InstagramAPIHelper iHelper = new InstagramAPIHelper(MainActivity.this, getApplicationContext());
                iHelper.execute();
            }
        });
        Button b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper mydb = DBHelper.getInstance(getApplicationContext());
                JSONObject j = mydb.getPhoto(1);
                Integer i = 1;
                i++;
            }
        });

        /*Uncomment to execute network call */
        //new InstagramAPIHelper().execute();
    }
    public void setJSON(JSONObject obj){
        //Add your parsing logic here
        JSONObject a = new JSONObject();
        DBHelper mydb = DBHelper.getInstance(getApplicationContext());
        mydb.insertPhoto(a, "hey");
        int i = 0;
        i++;
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
