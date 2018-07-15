package tarun.goyal.com.flickrimagesearch.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Utility class for all the {@link Bitmap} related utilities.
 */
public class BitmapUtils {

    /**
     * Calculates the sample size of bitmaps.
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int requiredWidth, int requiredHeight) {

        int inSampleSize = 1;
        int ratioH = options.outHeight / requiredHeight;
        int ratioW = options.outWidth / requiredWidth;

        if (ratioW > inSampleSize) {
            inSampleSize = ratioW;
        }

        if (ratioH > inSampleSize) {
            inSampleSize = ratioH;
        }

        return inSampleSize;
    }
}
