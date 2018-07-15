package tarun.goyal.com.flickrimagesearch.activities.imagedetailactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import tarun.goyal.com.flickrimagesearch.R;
import tarun.goyal.com.flickrimagesearch.images.ImageLoader;

/**
 * Activity showing details of image. This activity does not have presenter as it is fairly simple
 * and has task of showing Enlarged image only.
 */
public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "image_url";
    public static final String IMAGE_TITLE = "image_title";

    private ImageView mImageView;
    private AsyncTask<Object, Void, Bitmap> mDownloadImageTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_image_detail);

        initImageLayout();
    }

    private void initImageLayout() {
        Intent intent = getIntent();
        supportPostponeEnterTransition();
        if (intent.hasExtra(IMAGE_URL)) {
            String imageUrl = intent.getStringExtra(IMAGE_URL);
            mImageView = findViewById(R.id.image_view);
            loadImage(imageUrl);

            if (intent.hasExtra(IMAGE_TITLE)) {
                String title = intent.getStringExtra(IMAGE_TITLE);
                if (!TextUtils.isEmpty(title)) {
                    setTitle(title);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void loadImage(@NonNull final String imageUrl) {
        int width = mImageView.getMeasuredWidth();
        if (width > 0) {
            mDownloadImageTask = ImageLoader.getInstance().loadImage(mImageView, imageUrl);
        } else {
            mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int side = mImageView.getMeasuredHeight();
                    if (side > 0) {
                        mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        mDownloadImageTask = ImageLoader.getInstance().loadImage(mImageView, imageUrl);
                        supportStartPostponedEnterTransition();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownloadImageTask != null) {
            mDownloadImageTask.cancel(true);
        }
    }
}
