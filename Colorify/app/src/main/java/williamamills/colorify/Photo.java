package williamamills.colorify;

import android.media.Image;

/**
 * Created by Alexander on 3/23/2016.
 */
public class Photo {
    private String location;
    private String tags;
    private String index;
    private String likes;
    private Image picture;
    public Photo(Image pic, String loc, String tag, String ind, String like){
        picture = pic;
        location = loc;
        tags = tag;
        index = ind;
        likes = like;
    }
    public String getLocation(){return location;}
    public String getTags(){return tags;}
    public String getLikes(){return likes;}
    public Image getPicture(){return picture;}
}
