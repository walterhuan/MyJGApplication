package com.walter.sc.okhttp;

import java.util.List;

/**
 * Created by huangxl on 2016/3/31.
 */
public class FlightEntity {
    private String flightNo;
    private String planeCode;
    private String orgCityAirp;
    private String dstCityAirp;

    public String getOrgCityAirp() {
        return orgCityAirp;
    }

    public void setOrgCityAirp(String orgCityAirp) {
        this.orgCityAirp = orgCityAirp;
    }

    public String getDstCityAirp() {
        return dstCityAirp;
    }

    public void setDstCityAirp(String dstCityAirp) {
        this.dstCityAirp = dstCityAirp;
    }

    private List<PsgEntity> passengers;

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getPlaneCode() {
        return planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode;
    }

    public List<PsgEntity> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PsgEntity> passengers) {
        this.passengers = passengers;
    }
}
