package tarun.goyal.com.flickrimagesearch.data;

import com.google.gson.annotations.SerializedName;

/**
 * This is a container class for holding Flickr image response from server.
 */
public class FlickrImageResponseDto {
    @SerializedName("photos")
    private FlickerPhotoContainer photoContainer;
    private String stat;

    public FlickrImageResponseDto(FlickerPhotoContainer photoContainer, String stat) {
        this.photoContainer = photoContainer;
        this.stat = stat;
    }

    public FlickerPhotoContainer getPhotoContainer() {
        return photoContainer;
    }

    public String getStat() {
        return stat;
    }
}
