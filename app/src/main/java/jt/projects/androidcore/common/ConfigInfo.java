package jt.projects.androidcore.common;

import android.content.Context;
import android.content.res.Configuration;

public class ConfigInfo {
    private Context context;

    public ConfigInfo(Context context) {
        this.context = context;
    }

    public boolean isLandscape() {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
}
