package williamamills.colorify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
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
public class Photo implements Parcelable{
    private String location;
    private String tags;
    //private String index;
    private String likes;
    private String bitmapAddress;

    public Photo(String _location, String _tags, String _likes, String _bitmapAddress){
        location = _location;
        tags = _tags;
        likes = _likes;
        bitmapAddress = _bitmapAddress;
    }
    public String getLocation(){return location;}
    public String getTags(){return tags;}
    public String getLikes(){return likes;}
    public String getBitmapAddress(){return bitmapAddress;}
    public void setBitmapAddress(String _bitmapAddress){
        bitmapAddress = _bitmapAddress;
    }
    // Parcelling part
    public Photo(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this.location = data[0];
        this.tags = data[1];
        this.likes = data[2];
        this.bitmapAddress = data[3];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.location,
                this.tags,
                this.likes, this.bitmapAddress});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}

