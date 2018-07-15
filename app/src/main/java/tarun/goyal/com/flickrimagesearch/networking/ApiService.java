package tarun.goyal.com.flickrimagesearch.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageResponseDto;

/**
 * Created by tarun on 7/14/18.
 */
public interface ApiService {
    @GET("?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&%20format=json&nojsoncallback=1&safe_search=1")
    Call<FlickrImageResponseDto> getImages(@Query("text") String text, @Query("page") int page);
}
