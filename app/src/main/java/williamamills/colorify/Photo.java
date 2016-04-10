package williamamills.colorify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander on 3/23/2016.
 */
public class Photo {
    private String location;
    private String tags;
    private String index;
    private String likes;
    private Image picture;
    public Bitmap bitmap;
    Context mCtx;
    public Photo(JSONObject json, Context ctx){
       /* picture = pic;
        location = loc;
        tags = tag;
        index = ind;
        likes = like;*/
        mCtx = ctx;

       // bitmap = getBitmapFromURL("https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/12445772_589126311254658_1821092435_n.jpg");

    }
     Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
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
    private void getImg(){
        try
        {
            URL url = new URL("Enter the URL to be downloaded");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
            String filename="downloadedFile.png";
            Log.i("Local filename:", "" + filename);
            File file = new File(SDCardRoot,filename);
            if(file.createNewFile())
            {
                file.createNewFile();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ( (bufferLength = inputStream.read(buffer)) > 0 )
            {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
            }
            fileOutput.close();
            //if(downloadedSize==totalSize) filepath=file.getPath();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            //filepath=null;
            e.printStackTrace();
        }
        //Log.i("filepath:"," "+filepath) ;
        //return filepath;
    }
    public String getLocation(){return location;}
    public String getTags(){return tags;}
    public String getLikes(){return likes;}
    public Image getPicture(){return picture;}
    public void setBitmap(Bitmap b){
        bitmap = b;
    }
}
