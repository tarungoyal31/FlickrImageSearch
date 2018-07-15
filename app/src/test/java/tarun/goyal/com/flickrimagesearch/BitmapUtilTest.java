package tarun.goyal.com.flickrimagesearch;

import android.graphics.Bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import tarun.goyal.com.flickrimagesearch.images.BitmapUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(PowerMockRunner.class)
public class BitmapUtilTest {

    @Mock
    Bitmap mBitmap;

    /**
     * Test for checking down whether scale down ratio is calculated properly
     */
    @Test
    public void scalingDown_isCorrect() {

        int originalWidth = 1024;
        int originalHeight = 2048;
        int requiredWidth = 512;
        int requiredHeight = 256;

        Bitmap bitmap = getDummyBitmap(originalWidth, originalHeight);

        int scaleRatio = BitmapUtils.calculateScaleRatio(bitmap, requiredWidth, requiredHeight);

        assertThat(scaleRatio, is(equalTo(2)));
    }

    /**
     * Test for checking down whether scale down ratio is calculated properly
     */
    @Test
    public void scalingDown_isCorrect2() {

        int originalWidth = 1024;
        int originalHeight = 2048;
        int requiredWidth = 512;
        int requiredHeight = 1024;

        Bitmap bitmap = getDummyBitmap(originalWidth, originalHeight);

        int scaleRatio = BitmapUtils.calculateScaleRatio(bitmap, requiredWidth, requiredHeight);

        assertThat(scaleRatio, is(equalTo(2)));
    }

    /**
     * Test for checking down whether scale ratio is correct when original image is smaller than
     * required image size.
     */
    @Test
    public void scalingUp_isCorrect() {

        int originalWidth = 512;
        int originalHeight = 256;
        int requiredWidth = 1024;
        int requiredHeight = 2048;

        Bitmap bitmap = getDummyBitmap(originalWidth, originalHeight);

        int scaleRatio = BitmapUtils.calculateScaleRatio(bitmap, requiredWidth, requiredHeight);

        assertThat(scaleRatio, is(equalTo(1)));
    }

    /**
     * Returns a dummy bitmap
     */
    private Bitmap getDummyBitmap(int width, int height) {

        when(mBitmap.getWidth()).thenReturn(width);
        when(mBitmap.getHeight()).thenReturn(height);

        return mBitmap;
    }

}