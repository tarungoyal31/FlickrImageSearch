package tarun.goyal.com.flickrimagesearch;

import android.app.Application;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tarun.goyal.com.flickrimagesearch.networking.ApiService;

/**
 * Created by tarun on 7/14/18.
 */
public class App extends Application{

    public static final int NETWORK_TIMEOUT = 10;
    private ApiService mApiService;

    private static final String FLICKR_HOST = "https://api.flickr.com/services/rest/";

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FLICKR_HOST)
                .client(new OkHttpClient().newBuilder()
                        .readTimeout(NETWORK_TIMEOUT, TimeUnit.MINUTES)
                        .writeTimeout(NETWORK_TIMEOUT, TimeUnit.MINUTES)
                        .addInterceptor(loggingInterceptor)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService =  retrofit.create(ApiService.class);
    }

    @NonNull
    public ApiService apiService() {
        return mApiService;
    }
}
