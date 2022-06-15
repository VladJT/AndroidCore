package jt.projects.androidcore.notes.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private IDownloadListener downloadListener;

    public DownloadImageTask(IDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Ошибка передачи изображения", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        downloadListener.onDownloadComplete(result);
    }
}
