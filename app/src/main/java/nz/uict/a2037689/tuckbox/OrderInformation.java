package nz.uict.a2037689.tuckbox;



//Order form item information class

import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;


//Order information details to be used globally.
public class OrderInformation {



    public String name;
    public String description;


    @Exclude
    public Drawable image;

    public OrderInformation(){
    }

    //Item constructor for holding each item ordered
    public OrderInformation(String name, String description,Drawable image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

}
