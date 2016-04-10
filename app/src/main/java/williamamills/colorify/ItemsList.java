package williamamills.colorify;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Alexander on 3/27/2016.
 */
public class ItemsList extends ListActivity {

    private ItemsAdapter adapter;
    private ArrayList<Parcelable> uris;
    //private ArrayList<Parcelable> u;
    LruCache bitmapCache;
    ItemsList itemsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uris = new ArrayList<>();
        setContentView(R.layout.activity_items_list);
        Bundle extras = getIntent().getExtras();
        ArrayList<ClipData.Item> arrayList = new ArrayList<>();
        Integer numImages = extras.getInt("uris");
        int cacheSize = 4 * 1024 * 1024; // 4MiB
        bitmapCache = new LruCache(cacheSize) {
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();

            }};
        for(int k = 0; k < numImages; k++){
            try{
                uris.add(BitmapFactory.decodeStream(openFileInput("myImage" + k)));
                if(k < 3)
                bitmapCache.put(k, (Bitmap) uris.get(k));
            }catch(Exception e){

            }
        }
        this.adapter = new ItemsAdapter(this, R.layout.items_list_item, uris);
        setListAdapter(this.adapter);
        itemsList = this;
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
            TextView textView = (TextView) v.findViewById(R.id.list_item_title);
            Bitmap it = (Bitmap) bitmapCache.get(position);//items.get(position);
            Integer num = position;
            textView.setText(num.toString());
            ImageView iv = (ImageView) v.findViewById(R.id.list_item_image);
            if(it!=null){
                if (iv != null) {
                    iv.setImageBitmap(it);
                }
            }else{
                iv.setImageResource(R.mipmap.ic_launcher);
                BitmapWorkerTask task = new BitmapWorkerTask(iv, getApplicationContext(), itemsList);
                task.execute(position);
            }

            return v;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        this.adapter.getItem(position);//.click(this.getApplicationContext());
        Intent i = new Intent(getApplicationContext(), ImageViewActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("uri", position);
        i.putExtras(extras);
        startActivity(i);
    }
    public void addBitmapToMemoryCache(Integer key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            bitmapCache.put(key, bitmap);
        }
    }
    public Bitmap getBitmapFromMemCache(Integer key) {
        return (Bitmap)bitmapCache.get(key);
    }
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;
        private Context mCtx;
        private ItemsList itemsList;
        public BitmapWorkerTask(ImageView imageView, Context context, ItemsList list) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            mCtx = context;
            itemsList = list;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            final Bitmap b = getScaledImage("myImage" + data);
            itemsList.addBitmapToMemoryCache(data, b);
            return b;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
        private Bitmap getScaledImage(String imagePath){
            Bitmap bitmap = null;
            Uri imageUri = Uri.parse ("file://"+mCtx.getCacheDir()+"/"+imagePath);
            try{
                BitmapFactory.Options options = new BitmapFactory.Options();

                /**
                 * inSampleSize flag if set to a value > 1,
                 * requests the decoder to sub-sample the original image,
                 * returning a smaller image to save memory.
                 * This is a much faster operation as decoder just reads
                 * every n-th pixel from given image, and thus
                 * providing a smaller scaled image.
                 * 'n' is the value set in inSampleSize
                 * which would be a power of 2 which is downside
                 * of this technique.
                 */
                options.inSampleSize = 4;

                options.inScaled = true;

                //InputStream inputStream = mCtx.getContentResolver().openInputStream(imageUri);

                bitmap = BitmapFactory.decodeStream(mCtx.openFileInput("myImage"+data), null, options);
                //BitmapFactory.decodeStream(imagePath, null, options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
}
