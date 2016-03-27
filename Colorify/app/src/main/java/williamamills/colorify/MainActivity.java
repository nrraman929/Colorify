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

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends Activity {
    Button mainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = (Button) findViewById(R.id.main_enter);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                startActivity(i);
            }
        });
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
