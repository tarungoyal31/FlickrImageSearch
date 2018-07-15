package tarun.goyal.com.flickrimagesearch.images;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

/**
 * ImageCaching util class responsible for caching images. If same image is present with more than
 * one variant, then multiple entries of the same image might be present.
 *
 * This class is using in-memory LruCache.
 */
public class ImageCache {

    private LruCache<String, Bitmap> mImageCache;

    /**
     * Creates an in-memory cache of the required size.
     * @param maxSize maximum size of cache in bytes.
     */
    ImageCache(int maxSize) {
        this.mImageCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    /**
     * Used for getting {@link Bitmap} of specified size from the cache. Their
     *
     * @param path path of image for uniquely identifying the image
     * @param width desired width of the image
     * @param height desired height of the image
     * @return Bitmap from the cache if exists, otherwise null.
     */
    @Nullable
    public synchronized Bitmap get(@NonNull String path, int width, int height) {
        return mImageCache.get(getKey(path, width, height));
    }

    /**
     * @param path path of image for uniquely identifying the image
     * @param width width of the image
     * @param height height of the image
     * @param bitmap Bitmap to be put in the cache.
     */
    public synchronized void put(@NonNull String path, int width, int height, @NonNull Bitmap bitmap) {
        mImageCache.put(getKey(path, width, height), bitmap);
    }

    /**
     * Clears the complete imageCache. Use it if images are no longer required.
     */
    public synchronized void clear() {
        mImageCache.evictAll();
    }

    private String getKey(String imageUrl, int width, int height) {
        return imageUrl + ",width=" + width + ", height="+height;
    }
}
