package anu.edu.cecs.holistay.hoteldetails;

import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

/**
 * Hotel information
 *
 * @Authors: Srinivasa Sai Damarla (u7370240), Nakul Nambiar (u7433687), Aishwarya Sonavane (u7173560)
 */
public class Hotel implements Serializable {

    @CsvBindByName(required = true, column = "id")
    @PropertyName("id")
    public String listing_id="0"; //key

    @CsvBindByName(required = true, column = "name")
    private String name="Nan";

    @CsvBindByName(column = "description")
    private String description="Nan";

    @CsvBindByName(column = "price")
    private String price="0";

    @CsvBindByName(column = "review_scores_rating")
    @PropertyName("review_scores_rating")
    private String avgReview="0";

    @CsvBindByName(column = "latitude")
    private String latitude="0";

    @CsvBindByName(column = "longitude")
    private String longitude="0";

    @CsvBindByName(column = "accommodates")
    private String accommodates="0";

    @CsvBindByName(column = "bathrooms")
    private String bathrooms="0";

    @CsvBindByName(column = "bedrooms")
    private String bedrooms="0";

    @CsvBindByName(column = "beds")
    private String beds="0";

    @CsvBindByName(column = "property_type")
    private String property_type="Nan";

    @CsvBindByName(column = "number_of_reviews")
    private String number_of_reviews="0";

    @CsvBindByName(column = "picture_url")
    private String picture_url="https://upload.wikimedia.org/wikipedia/commons/6/6c/No_image_3x4.svg";

    @CsvBindByName(column = "amenities")
    private String amenities="Nan";

    @CsvBindByName(column = "host_identity_verified")
    private String host_identity_verified="Nan";

    @CsvBindByName(column = "host_name")
    private String host_name="Nan";

    @CsvBindByName(column = "neighbourhood")
    private String neighbourhood="Nan";

    @Exclude
    private boolean bookmarked;

    public Hotel(){
        this.listing_id = "0";
        this.avgReview = "0";
    }

    public Hotel(String listing_id, String name, String description, String price, String avgReview, String latitude, String longitude, String accommodates, String bathrooms, String bedrooms, String beds, String property_type, String number_of_reviews, String picture_url, String amenities, String host_identity_verified, String host_name, String neighbourhood, boolean bookmarked) {
        this.listing_id = listing_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.avgReview = avgReview;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accommodates = accommodates;
        this.bathrooms = bathrooms;
        this.bedrooms = bedrooms;
        this.beds = beds;
        this.property_type = property_type;
        this.number_of_reviews = number_of_reviews;
        this.picture_url = picture_url;
        this.amenities = amenities;
        this.host_identity_verified = host_identity_verified;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.bookmarked = bookmarked;
    }

    public String getNumber_of_reviews() {
        return number_of_reviews;
    }

    public void setNumber_of_reviews(String number_of_reviews) {
        this.number_of_reviews = number_of_reviews;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getListing_id() {
        return listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAccommodates() {
        return accommodates;
    }

    public void setAccommodates(String accommodates) {
        this.accommodates = accommodates;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getHost_identity_verified() {
        return host_identity_verified;
    }

    public void setHost_identity_verified(String host_identity_verified) {
        this.host_identity_verified = host_identity_verified;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getAvgReview() {
        return avgReview;
    }

    public void setAvgReview(String avgReview) {
        this.avgReview = avgReview;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
