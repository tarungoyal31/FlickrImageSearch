package tarun.goyal.com.flickrimagesearch.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tarun.goyal.com.flickrimagesearch.R;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageMetadata;
import tarun.goyal.com.flickrimagesearch.images.ImageLoader;

/**
 * Adapter class for {@link FlickrImageActivity#mImageRecyclerView}.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    @NonNull
    private List<FlickrImageMetadata> mImages;
    private ImageLoader mImageLoader;

    ImageAdapter() {
        mImages = new ArrayList<>();
        mImageLoader = ImageLoader.getInstance();
    }

    public void setImages(List<FlickrImageMetadata> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    public void addImages(List<FlickrImageMetadata> newImages) {
        mImages.addAll(newImages);
        notifyItemRangeInserted(mImages.size(), newImages.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(itemView, mImageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < getItemCount()) {
            holder.bindView(mImages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;
        private final ImageLoader mImageLoader;
        @Nullable
        private AsyncTask<Object, Void, Bitmap> mDownloadImageTask;
        @Nullable
        private FlickrImageMetadata mFlickrImageMetadata;

        ViewHolder(View itemView, ImageLoader imageLoader) {
            super(itemView);
            mImageLoader = imageLoader;
            mImageView = itemView.findViewById(R.id.image_view);
        }

        void bindView(@Nullable FlickrImageMetadata flickrImageMetadata) {

            mFlickrImageMetadata = flickrImageMetadata;

            if (mDownloadImageTask != null) {
                mDownloadImageTask.cancel(true);
            }

            loadImage();
        }

        private void loadImage() {
            int width = mImageView.getMeasuredWidth();
            if (mFlickrImageMetadata != null) {
                if (width > 0) {
                    mDownloadImageTask = mImageLoader.loadImage(mImageView, mFlickrImageMetadata.getImageUrl());
                } else {
                    mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int side = mImageView.getMeasuredHeight();
                            if (side > 0) {
                                mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                mDownloadImageTask = mImageLoader.loadImage(mImageView, mFlickrImageMetadata.getImageUrl());
                            }
                        }
                    });
                }
            }
        }
    }
}
