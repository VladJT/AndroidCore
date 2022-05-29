package jt.projects.androidcore.notes;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.SharedPreferences;


public class NotesSharedPreferences {
    private static Context context;
    private static SharedPreferences sharedPref;

    public static void initSharedPreferences(Context c){
        context = c;
    }

    public static void saveUserAccountName(String name){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NotesConstants.ACCOUNT_USER_NAME_SHARED_PREFERENCES, name);
        editor.apply();
    }

    public static String getUserAccountName(){
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getString(NotesConstants.ACCOUNT_USER_NAME_SHARED_PREFERENCES, "user");
    }
}
