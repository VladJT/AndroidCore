package jt.projects.androidcore.notes;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import jt.projects.androidcore.R;
import jt.projects.androidcore.notes.constants.DATABASE;
import jt.projects.androidcore.notes.constants.NotesConstants;

// pattern ОДИНОЧКА
public class NotesSharedPreferences {
    final String USER_PHOTO = "photo.jpg";

    private Context context;
    private SharedPreferences sharedPref;
    private Bitmap cachedPhoto;

    private static volatile NotesSharedPreferences instance;

    public static NotesSharedPreferences getInstance() {
        NotesSharedPreferences localInstance = instance;
        if (localInstance == null) {
            // Synchronized - это ключевое слово, которое позволяет заблокировать доступ к методу или части кода, если его уже использует другой поток.
            synchronized (NotesSharedPreferences.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new NotesSharedPreferences();
                }
            }
        }
        return localInstance;
    }

    public void initSharedPreferences(Context c) {
        context = c;
    }

    public SharedPreferences getCustomSharedPreferences(String name) {
        return context.getSharedPreferences(name, MODE_PRIVATE);
    }

    public void saveAppTheme(int theme) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(NotesConstants.APP_THEME_SHARED_PREFERENCES, theme);
        editor.apply();
    }

    public int getAppTheme() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getInt(NotesConstants.APP_THEME_SHARED_PREFERENCES, R.style.Theme_NotesTheme);
    }

    public void saveDBSource(DATABASE database) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NotesConstants.DB_SOURCE_SHARED_PREFERENCES, database.toString());
        editor.apply();
    }

    public DATABASE getDBSource() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        String source = sharedPref.getString(NotesConstants.DB_SOURCE_SHARED_PREFERENCES, "");
        if (source.equals("SHARED_PREF")) return DATABASE.SHARED_PREF;
        if (source.equals("FIREBASE")) return DATABASE.FIREBASE;
        if (source.equals("DATASTORE")) return DATABASE.DATASTORE;
        return DATABASE.SHARED_PREF;
    }

    public void saveUserAccountName(String name) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NotesConstants.ACCOUNT_USER_NAME_SHARED_PREFERENCES, name);
        editor.apply();
    }

    public String getUserAccountName() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getString(NotesConstants.ACCOUNT_USER_NAME_SHARED_PREFERENCES, "user");
    }

    @Deprecated
    public void saveUserPhotoUriString_OLD(String uriString) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NotesConstants.ACCOUNT_PHOTO_SHARED_PREFERENCES, uriString);
        editor.apply();
        //cachedPhoto = decodeBase64(getUserPhotoUriString());
    }

    @Deprecated
    public String getUserPhotoUriString_OLD() {
        sharedPref = context.getSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES,
                MODE_PRIVATE);
        return sharedPref.getString(NotesConstants.ACCOUNT_PHOTO_SHARED_PREFERENCES, "");
    }

    // encode your bitmap into string base64
    public String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    // decode Bitmap
    public Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public Bitmap getEmptyPhoto() {
        return BitmapFactory.decodeResource(context.getResources(),
                R.drawable.no_avatar);
    }

    public void saveUserPhotoToAssets(Bitmap bitmap) {
        try (FileOutputStream out = new FileOutputStream(context.getFilesDir().getAbsolutePath() + "/" + USER_PHOTO)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            cachedPhoto = bitmap;
        } catch (IOException e) {
            Log.e("SHARED_PREF", e.getMessage());
        }
    }


    public Bitmap getBitmapPhoto() {
        try {
            if (cachedPhoto == null) {
                // получаем входной поток
                FileInputStream ims = new FileInputStream(context.getFilesDir().getAbsolutePath() + "/" + USER_PHOTO);
                if (ims == null) {
                    cachedPhoto = getEmptyPhoto();
                } else {
                    cachedPhoto = BitmapFactory.decodeStream(ims);
                }
            }
            return cachedPhoto;
        } catch (Exception e) {
            Log.e("SHARED_PREF", e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String getAllPreferences() {
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