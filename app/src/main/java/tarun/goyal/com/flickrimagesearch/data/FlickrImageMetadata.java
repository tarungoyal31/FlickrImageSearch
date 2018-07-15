package tarun.goyal.com.flickrimagesearch.data;

/**
 * Image metadata fetched from Flickr's server.
 */
public class FlickrImageMetadata {

        private int farm;
        private String id;
        private String secret;
        private String server;
        private String title;

        public FlickrImageMetadata(int farm, String id, String secret, String server, String title) {
                this.farm = farm;
                this.id = id;
                this.secret = secret;
                this.server = server;
                this.title = title;
        }

        public String getId() {
                return id;
        }

        public String getTitle() {
                return title;
        }

        public String getImageUrl() {
            return String.format("http://farm%s.static.flickr.com/%s/%s_%s.jpg", farm, server, id, secret);
        }
}
