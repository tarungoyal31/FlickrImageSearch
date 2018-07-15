package tarun.goyal.com.flickrimagesearch.images;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

/**
 * Singleton ImageLoader for loading the images from a remote url. This Loader maintains a cache of images.
 *
 * <p>Typical image loading cycle is as below.
 * i) Image is requested with imageUrl, desired width and desired height. At present, this class
 * also takes in an {@link ImageView}. This can be improved by returning a future of {@link Bitmap}
 * instead of putting image directly in {@link ImageView}.
 * ii) If {@link Bitmap} is present in the cache. It is loaded into the {@link ImageView}. Otherwise,
 * an {@link AsyncTask} is started to fetch image from server.
 * iii) After an image is fetched from the server, it is saved into the cache and loaded into the
 * {@link ImageView}.
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";

    /**
     * Cache size in MB.
     */
    private static final int CACHE_SIZE = 1000*1024*1024;

    private static ImageLoader sInstance = null;

    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                sInstance = new ImageLoader();
            }
        }
        return sInstance;
    }

    private final ImageCache mCache;

    private ImageLoader() {
        mCache = new ImageCache(CACHE_SIZE);
    }

    /**
     * Loads an image in the provided {@link ImageView}.
     * @param imageView ImageView in which image is to be loaded.
     * @param imageUrl Server url of image to be downloaded.
     * @return AsyncTask in which image is getting fetched. Can be cancelled using
     * {@link AsyncTask#cancel(boolean)} if image is no longer required.
     */
    @Nullable
    public AsyncTask<Object, Void, Bitmap> loadImage(@NonNull final ImageView imageView, String imageUrl) {

        int width = imageView.getMeasuredWidth();
        int height = imageView.getMeasuredHeight();

        DownloadImageTask.ImageDownloadCallback imageDownloadCallback = new DownloadImageTask.ImageDownloadCallback() {
            @Override
            public void imageDownloaded(@NonNull Bitmap bitmap) {
                try {
                    imageView.setImageBitmap(bitmap);
                } catch (OutOfMemoryError e) {
                    Log.e(TAG, "", e);
                }
            }
        };

        Bitmap imageBitmap = mCache.get(imageUrl, width, height);
        if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap);
            return null;
        }

        //Removes the Bitmap present in ImageView earlier.
        imageView.setImageBitmap(null);

        try {
            DownloadImageTask downloadImageTask =
                    new DownloadImageTask(imageDownloadCallback, imageUrl, mCache, width, height);

            downloadImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return downloadImageTask;
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return null;
        }
    }
}
