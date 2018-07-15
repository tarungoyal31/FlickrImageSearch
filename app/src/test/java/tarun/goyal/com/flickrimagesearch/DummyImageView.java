package tarun.goyal.com.flickrimagesearch;

import java.util.ArrayList;
import java.util.List;

import tarun.goyal.com.flickrimagesearch.activities.FlickrImagePresenter;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageMetadata;

/**
 * Dummy implementation of FlickrImageView for testing presenter working.
 */
public class DummyImageView implements FlickrImagePresenter.FlickrImageView {

    private boolean isLoading;
    private List<FlickrImageMetadata> mImages;

    DummyImageView() {
        mImages = new ArrayList<>();
    }

    @Override
    public void setImages(List<FlickrImageMetadata> images) {
        mImages = images;
    }

    @Override
    public void addImages(List<FlickrImageMetadata> images) {
        mImages.addAll(images);
    }

    @Override
    public void showLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void showFailure() {

    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<FlickrImageMetadata> getmImages() {
        return mImages;
    }
}
