package com.hfad.myresidentadmin.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.hfad.myresidentadmin.Model.User;

/**
 * Created by icefrog on 2/19/18.
 */

public class Common {
    public static User currentUser;

    public static final  String UPDATE= "Update";
    public static final  String DELETE= "Delete";

    public static final int PICK_IMAGE_REQUEST =71;

    public static final String baseUrl = "https://maps.googleapis.com";

    public static String convertCodeToStatus1(String code){
        if (code.equals("0"))
            return "Pending";
        else if (code.equals("1"))
            return "On My Way";
        else
            return "Settle";
    }



    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight){

        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);
        float scaleX = newWidth/(float)bitmap.getWidth();
        float scaleY = newHeight/(float)bitmap.getHeight();
        float pivotX = 0,pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }

}
