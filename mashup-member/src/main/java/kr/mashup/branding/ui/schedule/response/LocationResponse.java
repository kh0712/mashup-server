package kr.mashup.branding.ui.schedule.response;

import kr.mashup.branding.domain.schedule.Location;
import lombok.Getter;

@Getter
public class LocationResponse {

    private final Double latitude;
    private final Double longitude;
    private final String address;
    private final String placeName;

    public LocationResponse(Double latitude, Double longitude, String address, String placeName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.placeName = placeName;
    }

    public static LocationResponse from(Location location) {
        return new LocationResponse(
                location.getLatitude(),
                location.getLongitude(),
                location.getRoadAddress(),
                location.getDetailAddress()
        );
    }
}
