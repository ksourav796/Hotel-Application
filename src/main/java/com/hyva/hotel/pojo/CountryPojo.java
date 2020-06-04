package com.hyva.hotel.pojo;

public class CountryPojo {

    private Long countryId;
    private String countryName;
    private Boolean flag;
    private String locationId;
    private String useraccount_id;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CountryPojo(String countryName, Boolean flag) {
        this.countryName = countryName;
        this.flag = flag;
    }

    public CountryPojo() {

    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUseraccount_id() {
        return useraccount_id;
    }

    public void setUseraccount_id(String useraccount_id) {
        this.useraccount_id = useraccount_id;
    }
}
