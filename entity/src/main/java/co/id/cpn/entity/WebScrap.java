package co.id.cpn.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WebScrap implements Serializable {

    public String POST_DATE;
    public String POST_TITLE;
    public String POST_DESC;
    public String POST_IMAGE_URL;
    public double RAND_INDEX = Math.random() * (50 - 10 + 1) + 10;
    @PrimaryKey @NonNull
    public String POST_URL;
    
}
