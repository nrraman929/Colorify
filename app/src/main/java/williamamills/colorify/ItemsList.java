package williamamills.colorify;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Alexander on 3/27/2016.
 */
public class ItemsList extends ListActivity {

    private ItemsAdapter adapter;
    private ArrayList<Parcelable> uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_items_list);
        Bundle extras = getIntent().getExtras();
        ArrayList<ClipData.Item> arrayList = new ArrayList<>();
        uris = extras.getParcelableArrayList("uris");
        this.adapter = new ItemsAdapter(this, R.layout.items_list_item, uris);
        setListAdapter(this.adapter);
    }

    private class ItemsAdapter extends ArrayAdapter<Parcelable> {

        private ArrayList<Parcelable> items;

        public ItemsAdapter(Context context, int textViewResourceId, ArrayList<Parcelable> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.items_list_item, null);
            }

            Uri it = (Uri) items.get(position);
            if (it != null) {
                ImageView iv = (ImageView) v.findViewById(R.id.list_item_image);
                if (iv != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(it);
                        Drawable yourDrawable = Drawable.createFromStream(inputStream, it.toString());
                        iv.setImageDrawable(yourDrawable);
                    } catch (FileNotFoundException e) {
                        //yourDrawable = getResources().getDrawable(R.drawable.default_image);
                        int c = 3;

                    }
                    //iv.setImageDrawable(it.getUri());
                }
            }

            return v;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        this.adapter.getItem(position);//.click(this.getApplicationContext());
        Intent i = new Intent(getApplicationContext(), ImageViewActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable("uri", uris.get(position));
        i.putExtras(extras);
        startActivity(i);
    }
}
