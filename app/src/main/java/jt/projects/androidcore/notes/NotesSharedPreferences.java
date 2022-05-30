package jt.projects.androidcore.notes;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;

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
}