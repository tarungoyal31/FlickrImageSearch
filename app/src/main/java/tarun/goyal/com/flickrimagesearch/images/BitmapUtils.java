package tarun.goyal.com.flickrimagesearch.images;

import android.graphics.Bitmap;

/**
 * Utility class for all the {@link Bitmap} related utilities.
 */
public class BitmapUtils {

    public static int calculateScaleRatio(Bitmap bitmap, int desiredWidth, int desiredHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        if (desiredHeight >= originalHeight || desiredWidth >= originalWidth) {
            return 1;
        }
        return Math.min(originalHeight/desiredHeight, originalWidth/desiredWidth);
    }
}
