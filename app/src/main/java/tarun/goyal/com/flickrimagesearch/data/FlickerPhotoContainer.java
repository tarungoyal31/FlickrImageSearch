package tarun.goyal.com.flickrimagesearch.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Flickr image response object containing images and metadata of collection fetched from the server.
 */
public class FlickerPhotoContainer {
    private int page;
    private int pages;
    private int perpage;
    private int total;
    @SerializedName("photo")
    private List<FlickrImageMetadata> photos;

    public FlickerPhotoContainer(int page, int pages, int perpage, int total,
                                 List<FlickrImageMetadata> photos) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photos = photos;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public int getTotal() {
        return total;
    }

    public List<FlickrImageMetadata> getPhotos() {
        return photos;
    }
}
