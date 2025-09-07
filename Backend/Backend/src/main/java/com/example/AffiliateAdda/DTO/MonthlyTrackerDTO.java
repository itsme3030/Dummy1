package com.example.AffiliateAdda.DTO;

import java.time.YearMonth;

public class MonthlyTrackerDTO {
    private Long monthlyTrackerId;
    private Long tId;
    private YearMonth month;  // This will store the month and year
    private Long count=0L;       // Click count for this particular month
    private Long buyCount=0L;   // Buy count for this particular month

    public Long getMonthlyTrackerId() {
        return monthlyTrackerId;
    }

    public void setMonthlyTrackerId(Long monthlyTrackerId) {
        this.monthlyTrackerId = monthlyTrackerId;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }
}
