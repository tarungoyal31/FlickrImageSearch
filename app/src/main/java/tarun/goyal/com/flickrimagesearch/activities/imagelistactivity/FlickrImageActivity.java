package tarun.goyal.com.flickrimagesearch.activities.imagelistactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import tarun.goyal.com.flickrimagesearch.App;
import tarun.goyal.com.flickrimagesearch.R;
import tarun.goyal.com.flickrimagesearch.activities.imagedetailactivity.ImageDetailActivity;
import tarun.goyal.com.flickrimagesearch.data.FlickrImageMetadata;

public class FlickrImageActivity extends AppCompatActivity implements FlickrImagePresenter.FlickrImageView, ImageAdapter.ImageClickedCallback {

    public static final int COLUMN_COUNT = 3;

    private ImageAdapter mImageAdapter;
    private FlickrImagePresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mImageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_image);
        App app = (App) getApplication();
        mPresenter = new FlickrImagePresenter(app.apiService(), this);
        initLayout();
    }

    private void initLayout() {

        initSearchLayout();

        initRecyclerView();

        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    private void initRecyclerView() {
        mImageRecyclerView = findViewById(R.id.images_rv);

        final GridLayoutManager layoutManager =
                new GridLayoutManager(this, COLUMN_COUNT, LinearLayoutManager.VERTICAL, false);

        mImageAdapter = new ImageAdapter(this);
        mImageRecyclerView.setLayoutManager(layoutManager);
        mImageRecyclerView.setAdapter(mImageAdapter);

        mImageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (layoutManager.findLastCompletelyVisibleItemPosition() + 6 > mImageAdapter.getItemCount()) {
                    mPresenter.loadMoreItems();
                }
            }
        });
    }

    private void initSearchLayout() {
        SearchView mImageSearchView = findViewById(R.id.images_search_view);

        mImageSearchView.setSubmitButtonEnabled(true);

        mImageSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void setImages(List<FlickrImageMetadata> images) {
        mImageRecyclerView.scrollToPosition(0);
        mImageAdapter.setImages(images);
    }

    @Override
    public void addImages(List<FlickrImageMetadata> images) {
        mImageAdapter.addImages(images);
    }

    @Override
    public void showLoading(boolean isLoading) {
        mSwipeRefreshLayout.setRefreshing(isLoading);
    }

    @Override
    public void showFailure() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.finish();
    }

    @Override
    public void imageClicked(FlickrImageMetadata image, View imageView) {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra(ImageDetailActivity.IMAGE_TITLE, image.getTitle());
        intent.putExtra(ImageDetailActivity.IMAGE_URL, image.getImageUrl());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, imageView, "sharedImage");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
