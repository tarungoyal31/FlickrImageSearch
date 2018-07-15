package tarun.goyal.com.flickrimagesearch;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import tarun.goyal.com.flickrimagesearch.activities.imagelistactivity.FlickrImagePresenter;
import tarun.goyal.com.flickrimagesearch.networking.ApiService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link FlickrImagePresenter}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class FlickrImagePresenterTest {

    @Mock
    ApiService mApiService;

    private FlickrImagePresenter.FlickrImageView dummyImageView;
    private FlickrImagePresenter flickrImagePresenter;

    @Before
    public void prepare() {

        mockTextUtils();

        ApiService apiService = initializeApiService();

        dummyImageView = new DummyImageView();
        flickrImagePresenter = new FlickrImagePresenter(apiService, dummyImageView);
    }

    /**
     * Mocks {@link TextUtils#isEmpty(CharSequence)}
     */
    private void mockTextUtils() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                return !(a != null && a.length() > 0);
            }
        });

    }

    /**
     * Initializes dummy {@link ApiService} for mocking network calls.
     */
    public ApiService initializeApiService() {
        when(mApiService.getImages(any(String.class), any(Integer.class))).thenReturn(new DummyCall());
        return mApiService;
    }

    @Test
    public void test_refresh() {
        flickrImagePresenter.search("cat");
        flickrImagePresenter.refresh();
        assertThat(((DummyImageView) dummyImageView).getmImages().size(), is(equalTo(1)));
    }

    @Test
    public void test_search() {
        flickrImagePresenter.search("cat");
        assertThat(((DummyImageView) dummyImageView).getmImages().size(), is(equalTo(1)));
    }

    @Test
    public void test_loadMore() {
        flickrImagePresenter.search("cat");
        flickrImagePresenter.loadMoreItems();
        flickrImagePresenter.loadMoreItems();
        assertThat(((DummyImageView) dummyImageView).getmImages().size(), is(equalTo(3)));
    }

    @Test
    public void test_searchChangeFlow() {
        flickrImagePresenter.search("cat");
        flickrImagePresenter.loadMoreItems();
        flickrImagePresenter.loadMoreItems();
        flickrImagePresenter.search("dog");
        assertThat(((DummyImageView) dummyImageView).getmImages().size(), is(equalTo(1)));
    }

    @Test
    public void test_activityDestroyedSearch() {
        flickrImagePresenter.search("w");
        flickrImagePresenter.finish();
        flickrImagePresenter.loadMoreItems();
        assertThat(((DummyImageView) dummyImageView).getmImages().size(), is(equalTo(1)));
    }
}
