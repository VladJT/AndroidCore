package jt.projects.androidcore.notes;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;

import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Set;

import jt.projects.androidcore.R;


public class NotesSharedPreferences {
    private static Context context;
    private static SharedPreferences sharedPref;
    private static Bitmap cachedPhoto = null;

    public static void initSharedPreferences(Context c) {
        context = c;
        if (!getUserPhotoUriString().equals("")) {
            cachedPhoto = decodeBase64(getUserPhotoUriString());
        }
    }

    public static void saveAppTheme(int theme) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(NotesConstants.APP_THEME_SHARED_PREFERENCES, theme);
        editor.apply();
    }

    public static int getAppTheme() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getInt(NotesConstants.APP_THEME_SHARED_PREFERENCES, R.style.Theme_NotesTheme);
    }

    public static void saveUserAccountName(String name) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NotesConstants.ACCOUNT_USER_NAME_SHARED_PREFERENCES, name);
        editor.apply();
    }

    public static String getUserAccountName() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getString(NotesConstants.ACCOUNT_USER_NAME_SHARED_PREFERENCES, "user");
    }

    public static void saveUserPhotoUriString(String uriString) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NotesConstants.ACCOUNT_PHOTO_SHARED_PREFERENCES, uriString);
        editor.apply();
        cachedPhoto = decodeBase64(getUserPhotoUriString());
    }

    public static String getUserPhotoUriString() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getString(NotesConstants.ACCOUNT_PHOTO_SHARED_PREFERENCES, "");
    }

    // encode your bitmap into string base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    // decode Bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap getEmptyPhoto() {
        return BitmapFactory.decodeResource(context.getResources(),
                R.drawable.no_avatar);
    }

    public static Bitmap getBitmapPhoto() {
        if (cachedPhoto == null) {
            return getEmptyPhoto();
        } else return cachedPhoto;
    }

    @SuppressWarnings("unchecked")
    public static String getAllPreferences() {
        StringBuilder result = new StringBuilder();
        SharedPreferences preference = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES, MODE_PRIVATE);
        Map<String, ?> prefs = preference.getAll();
        for (String key : prefs.keySet()) {
            if (key.equals(NotesConstants.ACCOUNT_PHOTO_SHARED_PREFERENCES)) continue;
            Object pref = prefs.get(key);
            String printVal = "";
            if (pref instanceof Boolean) {
                printVal = key + " : " + (Boolean) pref;
            }
            if (pref instanceof Float) {
                printVal = key + " : " + (Float) pref;
            }
            if (pref instanceof Integer) {
                printVal = key + " : " + (Integer) pref;
            }
            if (pref instanceof Long) {
                printVal = key + " : " + (Long) pref;
            }
            if (pref instanceof String) {
                printVal = key + " : " + (String) pref;
            }
            if (pref instanceof Set<?>) {
                printVal = key + " : " + (Set<String>) pref;
            }
            // Every new preference goes to a new line
            result.append(printVal + "\n");
        }
        return result.toString();
    }
}