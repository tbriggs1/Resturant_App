package helloworld.mmu.ac.uk.resturantrating;

public class ResturantData {
    private String id;
    private String businessName;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String postcode;
    private String ratingValue;
    private String distanceKm;
    private String location;
    private String lat;
    private String lng;

    public ResturantData(String businessName, String addressLine1)
    {
        this.businessName = businessName;
        this.AddressLine1 = addressLine1;
    }

    public ResturantData(String businessName, String addressLine1, String addressLine2, String addressLine3, String postcode, String ratingValue, String distanceKm, String location, String lat, String lng) {
        this.businessName = businessName;
        AddressLine1 = addressLine1;
        AddressLine2 = addressLine2;
        AddressLine3 = addressLine3;
        this.postcode = postcode;
        this.ratingValue = ratingValue;
        this.distanceKm = distanceKm;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName; }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressLine1() {
        return AddressLine1; }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2; }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return AddressLine3; }

    public void setAddressLine3(String addressLine3) {
        AddressLine3 = addressLine3;
    }

    public String getPostcode() {
        return postcode; }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(String distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", businessName='" + businessName + '\n' +
                        ", AddressLine1='" + AddressLine1 + '\n' +
                        ", AddressLine2='" + AddressLine2 + '\n' +
                        ", AddressLine3='" + AddressLine3 + '\n' +
                        ", postcode='" + postcode + '\n' +
                        ", ratingValue='" + ratingValue + '\n' +
                        ", distanceKm=" + distanceKm +
                        ", location=" + location + '\n';

    }
}
