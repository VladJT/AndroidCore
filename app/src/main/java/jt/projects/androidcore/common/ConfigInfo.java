package jt.projects.androidcore.common;

import android.content.Context;
import android.content.res.Configuration;

public class ConfigInfo {
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
}
