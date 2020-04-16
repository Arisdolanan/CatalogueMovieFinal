package com.hokagelab.www.cataloguemovie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompanies {
    @Expose
    @SerializedName("origin_country")
    private String originCountry;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("logo_path")
    private String logoPath;
    @Expose
    @SerializedName("id")
    private int id;

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
