package firstapp.myapplication;

/**
 * Created by User on 23/10/2016.
 */
 public  class Movie {

    private String posterPath;
    private String adult;
    private String overview;
    private String release_date;
    private String genre_ids ;
    private String id;
    private String originalTitle;
    private String original_language;
    private String title;
    private String backdrop_path;
    private String popularity;
    private String vote_count;
    private String video;
    private String vote_average;

    //constructor1


//    public Movie(JSONObject object) throws JSONException {
//
//
//            posterPath=object.getString("poster_path");
//            adult=object.getString("adult");
   // }
    //constructor2

   // public Movie(String posterPath) {
        //this.posterPath = posterPath;
   // }

    //constructor
    /*public Movie(String adult, String backdrop_path, String genre_ids, String id, String original_language,
                 String originalTitle, String overview, String popularity,
                 String posterPath, String release_date, String title, String video, String vote_average, String vote_count) {


        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_language = original_language;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.release_date = release_date;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }
*/
    //getters



    public String getAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public String getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getVideo() {
        return video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }



    //setters
    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setGenre_ids(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

}
