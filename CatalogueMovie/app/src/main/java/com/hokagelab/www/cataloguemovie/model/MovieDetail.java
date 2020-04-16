package com.hokagelab.www.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDetail implements Parcelable {

    @Expose
    @SerializedName("vote_count")
    private int voteCount;
    @Expose
    @SerializedName("vote_average")
    private double voteAverage;
    @Expose
    @SerializedName("video")
    private boolean video;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("tagline")
    private String tagline;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("spoken_languages")
    private List<SpokenLanguages> spokenLanguages;
    @Expose
    @SerializedName("runtime")
    private int runtime;
    @Expose
    @SerializedName("revenue")
    private int revenue;
    @Expose
    @SerializedName("release_date")
    private String releaseDate;
    @Expose
    @SerializedName("production_countries")
    private List<ProductionCountries> productionCountries;
    @Expose
    @SerializedName("production_companies")
    private List<ProductionCompanies> productionCompanies;
    @Expose
    @SerializedName("poster_path")
    private String posterPath;
    @Expose
    @SerializedName("popularity")
    private double popularity;
    @Expose
    @SerializedName("overview")
    private String overview;
    @Expose
    @SerializedName("original_title")
    private String originalTitle;
    @Expose
    @SerializedName("original_language")
    private String originalLanguage;
    @Expose
    @SerializedName("imdb_id")
    private String imdbId;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("homepage")
    private String homepage;
    @Expose
    @SerializedName("genres")
    private List<Genres> genres;
    @Expose
    @SerializedName("budget")
    private int budget;
    @Expose
    @SerializedName("backdrop_path")
    private String backdropPath;
    @Expose
    @SerializedName("adult")
    private boolean adult;

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean getVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SpokenLanguages> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguages> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<ProductionCountries> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountries> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public List<ProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.tagline);
        dest.writeString(this.status);
        dest.writeList(this.spokenLanguages);
        dest.writeInt(this.runtime);
        dest.writeInt(this.revenue);
        dest.writeString(this.releaseDate);
        dest.writeList(this.productionCountries);
        dest.writeList(this.productionCompanies);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.popularity);
        dest.writeString(this.overview);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.imdbId);
        dest.writeInt(this.id);
        dest.writeString(this.homepage);
        dest.writeTypedList(this.genres);
        dest.writeInt(this.budget);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
    }

    public MovieDetail() {
    }

    protected MovieDetail(Parcel in) {
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
        this.video = in.readByte() != 0;
        this.title = in.readString();
        this.tagline = in.readString();
        this.status = in.readString();
        this.spokenLanguages = new ArrayList<SpokenLanguages>();
        in.readList(this.spokenLanguages, SpokenLanguages.class.getClassLoader());
        this.runtime = in.readInt();
        this.revenue = in.readInt();
        this.releaseDate = in.readString();
        this.productionCountries = new ArrayList<ProductionCountries>();
        in.readList(this.productionCountries, ProductionCountries.class.getClassLoader());
        this.productionCompanies = new ArrayList<ProductionCompanies>();
        in.readList(this.productionCompanies, ProductionCompanies.class.getClassLoader());
        this.posterPath = in.readString();
        this.popularity = in.readDouble();
        this.overview = in.readString();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.imdbId = in.readString();
        this.id = in.readInt();
        this.homepage = in.readString();
        this.genres = in.createTypedArrayList(Genres.CREATOR);
        this.budget = in.readInt();
        this.backdropPath = in.readString();
        this.adult = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MovieDetail> CREATOR = new Parcelable.Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel source) {
            return new MovieDetail(source);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };
}
