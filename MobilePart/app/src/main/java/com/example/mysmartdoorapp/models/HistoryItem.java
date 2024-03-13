package com.example.mysmartdoorapp.models;

public class HistoryItem {
    String historydate, historystatus;

    public HistoryItem(String historydate, String historystatus) {
        this.historydate = historydate;
        this.historystatus = historystatus;
    }

    public String getHistorydate() {
        return historydate;
    }

    public String getHistorystatus() {
        return historystatus;
    }
}
