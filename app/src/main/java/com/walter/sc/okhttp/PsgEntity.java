package com.walter.sc.okhttp;

import java.util.List;

/**
 * Created by huangxl on 2016/3/31.
 */
public class PsgEntity {
    private String paxName;
    private String pnrRef;

    public List<PsgFlightHistoryEntity> getFlightHistory() {
        return flightHistory;
    }

    public void setFlightHistory(List<PsgFlightHistoryEntity> flightHistory) {
        this.flightHistory = flightHistory;
    }

    private List<PsgFlightHistoryEntity> flightHistory;

    public String getPaxName() {
        return paxName;
    }

    public void setPaxName(String paxName) {
        this.paxName = paxName;
    }

    public String getPnrRef() {
        return pnrRef;
    }

    public void setPnrRef(String pnrRef) {
        this.pnrRef = pnrRef;
    }
}
