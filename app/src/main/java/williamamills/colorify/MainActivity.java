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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    Button mainButton;
    JSONArray j;
    MainActivity activity = this;
    Spinner choiceSpinner;
    EditText editText;
    Spinner colorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = (Button) findViewById(R.id.main_enter);
        choiceSpinner = (Spinner) findViewById(R.id.main_activity_choice_spinner);
        colorSpinner = (Spinner) findViewById(R.id.main_activity_color_spinner);
        editText = (EditText) findViewById(R.id.main_activity_edit_text);

        choiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        switch (position) {
                                                            case 0://popular
                                                                editText.setVisibility(View.INVISIBLE);
                                                                colorSpinner.setVisibility(View.INVISIBLE);
                                                                break;
                                                            case 1://location
                                                                editText.setVisibility(View.VISIBLE);
                                                                colorSpinner.setVisibility(View.INVISIBLE);
                                                                break;
                                                            case 2://color
                                                                editText.setVisibility(View.INVISIBLE);
                                                                colorSpinner.setVisibility(View.VISIBLE);
                                                                break;
                                                        }
                                                    }
                                                    @Override
                                                    public void onNothingSelected(AdapterView adapterView){

                                                    }
        });
        mainButton.setText("Search");
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = choiceSpinner.getSelectedItemPosition();
                switch (pos) {
                    case 0://popular
                        InstagramAPIHelper popularHelper = new InstagramAPIHelper(activity, getApplicationContext());
                        popularHelper.execute();
                        break;
                    case 1:
                        if(editText.getText().toString().matches("")){break;}
                        InstagramLocationAPIHelper locationHelper = new InstagramLocationAPIHelper(activity, getApplicationContext(), editText.getText().toString().trim());
                        locationHelper.execute();
                        break;
                    case 2:
                        popularHelper = new InstagramAPIHelper(activity, getApplicationContext());
                        popularHelper.execute();
                        break;
                }
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

    @Override
    public void onBackPressed(){

    }
    /*
  Integer pos = choiceSpinner.getSelectedItemPosition();
                switch (pos) {
                    case 0://popular
                        InstagramAPIHelper popularHelper = new InstagramAPIHelper(activity, getApplicationContext());
                        popularHelper.execute();
                        break;
                    case 1:
                        if(editText.getText().toString().matches("")){break;}
                        InstagramLocationAPIHelper locationHelper = new InstagramLocationAPIHelper(activity, getApplicationContext(), editText.getText().toString().trim());
                        locationHelper.execute();
                        break;
                    case 2:
                        popularHelper = new InstagramAPIHelper(activity, getApplicationContext());
                        popularHelper.execute();
                        break;
                }
     */
}
