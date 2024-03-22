package adegas.fago.models;

public class LastLocationModel {
    private double lat;
    private double lng;
    private long ts;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public long getTs() {
        return ts;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "LastLocationModel{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", ts=" + ts +
                '}';
    }
}
