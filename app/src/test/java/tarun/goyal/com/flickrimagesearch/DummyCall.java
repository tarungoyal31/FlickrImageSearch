package tarun.goyal.com.flickrimagesearch;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarun.goyal.com.flickrimagesearch.data.FlickerPhotoContainer;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageMetadata;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageResponseDto;

/**
 * Dummy implementation of Retrofit call for testing purpose.
 */
public class DummyCall implements Call<FlickrImageResponseDto> {

    @Override
    public Response<FlickrImageResponseDto> execute() {
        return null;
    }

    @Override
    public void enqueue(@NonNull Callback<FlickrImageResponseDto> callback) {
        callback.onResponse(this, Response.success(flickrImageResponseDto()));
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<FlickrImageResponseDto> clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }

    private FlickrImageResponseDto flickrImageResponseDto() {
        FlickrImageMetadata flickrImageMetadata = new FlickrImageMetadata(10, "1", "djjds",
                "jdjkskj", "djsjjk");
        List<FlickrImageMetadata> photos = new ArrayList<>();
        photos.add(flickrImageMetadata);
        FlickerPhotoContainer photoContainer = new FlickerPhotoContainer(1, 100, 1000, 100000, photos);
        return new FlickrImageResponseDto(photoContainer, null);
    }
}
