package tarun.goyal.com.flickrimagesearch.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageMetadata;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageResponseDto;
import tarun.goyal.com.flickrimagesearch.networking.ApiService;

/**
 * Presenter for handling the logic for {@link FlickrImageActivity}. This is also responsible for
 * loading the data and give the respective callbacks.
 */
public class FlickrImagePresenter {

    private static final String TAG = "FlickrImagePresenter";

    @NonNull
    private final ApiService mApiService;
    @NonNull
    private final FlickrImageView mView;
    @Nullable
    private String mSearchText;
    private boolean isLoading = false;
    private int mPage;
    private boolean mActivityDestroyed = false;
    @NonNull
    private ArrayList<Call<FlickrImageResponseDto>> calls;

    FlickrImagePresenter(@NonNull ApiService apiService, @NonNull FlickrImageView view) {
        this.mApiService = apiService;
        this.mView = view;
        mSearchText = "";
        calls = new ArrayList<>();
    }

    /**
     * Start searching for given text.
     */
    public void search(@Nullable String text) {
        if (!TextUtils.isEmpty(text)) {
            mSearchText = text;
            refresh();
        }
    }

    /**
     * Handles refresh action and refreshes the data for {@link FlickrImageView}.
     */
    public void refresh() {
        if (!TextUtils.isEmpty(mSearchText)) {
            mView.showLoading(true);
            isLoading = true;
            mPage = 1;
            makeFlickrSearchApiCall();
        }
    }

    /**
     * Loads more items corresponding to the search query.
     */
    public void loadMoreItems() {

        if (isLoading) {
            return;
        }

        isLoading = true;
        makeFlickrSearchApiCall();
    }

    /**
     * Starts api call for fetching the data from Flickr's server.
     */
    private void makeFlickrSearchApiCall() {

        for (Call<FlickrImageResponseDto> call : calls) {
            call.cancel();
        }
        calls.clear();

        Call<FlickrImageResponseDto> imagesCall =  mApiService.getImages(mSearchText, mPage);
        imagesCall.enqueue(new Callback<FlickrImageResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<FlickrImageResponseDto> call, @NonNull Response<FlickrImageResponseDto> response) {
                FlickrImageResponseDto flickerImageResponseDto = response.body();
                if (!mActivityDestroyed && flickerImageResponseDto != null && !call.isCanceled()) {
                    if (mPage == 1) {
                        mView.setImages(flickerImageResponseDto.getPhotoContainer().getPhotos());
                    } else {
                        mView.addImages(flickerImageResponseDto.getPhotoContainer().getPhotos());
                    }
                }
                mPage++;
                isLoading = false;
                mView.showLoading(false);
            }

            @Override
            public void onFailure(@NonNull Call<FlickrImageResponseDto> call, @NonNull Throwable t) {
                Log.e(TAG, "Failed to load images", t);
                isLoading = false;
                mView.showLoading(false);
            }
        });

        calls.add(imagesCall);
    }

    /**
     * Must be called when activity/view is destroyed
     */
    public void finish() {
        mActivityDestroyed = true;
    }

    /**
     * Interface to be implemented by the Activity/Fragment/View using this Presenter.
     *
     * This interface contains various callbacks for the view from Presenter.
     */
    public interface FlickrImageView {
        /**
         * Sets new list of images in the view.
         */
        void setImages(List<FlickrImageMetadata> images);

        /**
         * Add images to the view.
         */
        void addImages(List<FlickrImageMetadata> images);

        /**
         * show the loading indicators in the view.
         */
        void showLoading(boolean isLoading);
    }
}
