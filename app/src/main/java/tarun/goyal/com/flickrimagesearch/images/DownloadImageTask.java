package tarun.goyal.com.flickrimagesearch.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * An {@link AsyncTask} for downloading images from server and sends a call to
 * {@link ImageDownloadCallback#imageDownloaded(Bitmap)}.
 *
 * This task runs on a {@link AsyncTask#THREAD_POOL_EXECUTOR} for fetching images in parallel.
 */
public class DownloadImageTask extends AsyncTask<Object, Void, Bitmap> {

    private static final String TAG = "DownloadImageTask";

    private final String url;
    private final ImageCache mCache;
    private final ImageDownloadCallback mImageDownloadCallback;

    private int width;
    private int height;

    DownloadImageTask(ImageDownloadCallback imageDownloadCallback, String url, ImageCache imageCache, int width,
                      int height) {
        this.url = url;
        this.mImageDownloadCallback = imageDownloadCallback;
        this.mCache = imageCache;
        this.width = width;
        this.height = height;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        if (isCancelled()) {
            return null;
        }
        if (isCancelled()) {
            return null;
        }

        Bitmap bitmap = mCache.get(url, width, height);

        if (bitmap == null) {
            try {
                //Set sample size for downsampling the image.
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());

                int scaleRatio = BitmapUtils.calculateScaleRatio(bitmap, width, height);
                if (scaleRatio > 1) {
                    bitmap = Bitmap.createScaledBitmap(bitmap,
                            bitmap.getWidth() / scaleRatio,
                            bitmap.getHeight() / scaleRatio,
                            false
                            );
                }

                if (bitmap != null) {
                    mCache.put(url, width, height, bitmap);
                }

                return bitmap;

            } catch (Exception e) {
                Log.e(TAG, "Exception while fetching image from the server.", e);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (isCancelled()) {
            bitmap = null;
        }

        mImageDownloadCallback.imageDownloaded(bitmap);
    }

    /**
     * Callback interface for handling the download of image.
     */
    public interface ImageDownloadCallback {
        void imageDownloaded(Bitmap bitmap);
    }
}
