package com.hfad.myresidentadmin.Model;

/**
 * Created by icefrog on 5/25/18.
 */

public class Common {

    public static final  String UPDATE= "Update";
    public static final  String DELETE= "Delete";


    public static String convertCodeToStatus(String code){
        if (code.equals("0"))
            return "Pending";
        else if (code.equals("1"))
            return "On My Way";
        else
            return "Settle";
    }

}
