package com.crypto.cryptosim.models;

public class Exchange {

    private int id;
    private String logo;
    private String name;
    private String url;
    private int volDay;
    private int volWeek;
    private int volMonth;
    private int volLiquidity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVolDay() {
        return volDay;
    }

    public void setVolDay(int volDay) {
        this.volDay = volDay;
    }

    public int getVolWeek() {
        return volWeek;
    }

    public void setVolWeek(int volWeek) {
        this.volWeek = volWeek;
    }

    public int getVolMonth() {
        return volMonth;
    }

    public void setVolMonth(int volMonth) {
        this.volMonth = volMonth;
    }

    public int getVolLiquidity() {
        return volLiquidity;
    }

    public void setVolLiquidity(int volLiquidity) {
        this.volLiquidity = volLiquidity;
    }

    public Exchange(){}

    public Exchange(String logo, String name, String url, int volDay, int volWeek, int volMonth, int volLiquidity){
        this.logo = logo;
        this.name = name;
        this.url = url;
        this.volDay = volDay;
        this.volWeek = volWeek;
        this.volMonth = volMonth;
        this.volLiquidity = volLiquidity;
    }
}
