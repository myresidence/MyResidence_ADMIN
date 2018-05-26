package com.hfad.myresidentadmin.Common;

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





}
