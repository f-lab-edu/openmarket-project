package oort.cloud.openmarket.user.controller.request;

import jakarta.validation.constraints.NotEmpty;

public class AddressCreateRequest {
    @NotEmpty
    private String address;
    @NotEmpty
    private String addressDetail;
    @NotEmpty
    private String postalCode;

    public String getAddress() {
        return address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "AddressCreateRequest{" +
                "address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
