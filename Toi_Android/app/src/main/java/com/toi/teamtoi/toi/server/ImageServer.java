package com.toi.teamtoi.toi.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageServer {
    private Bitmap bitmap;
    private ImageView imageView;

    public ImageServer(ImageView imageView) {
        this.imageView = imageView;
    }

    public void getData(String url) {
        class GetImageData extends AsyncTask<String, Integer, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... urls) {
                try {
                    URL myFileUrl = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }
            protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }
        GetImageData server = new GetImageData();
        server.execute(url);
    }
}
