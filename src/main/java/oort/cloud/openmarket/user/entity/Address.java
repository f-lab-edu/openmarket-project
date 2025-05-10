package oort.cloud.openmarket.user.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.common.entity.BaseTimeEntity;

import java.util.Objects;

@Entity
@Table(name = "address")
public class Address extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "postal_code")
    private String postalCode;

    protected Address(){}

    public static Address of(Users user, String address, String addressDetail, String postalCode){
        Address addressObj = new Address();
        addressObj.user = user;
        addressObj.address = address;
        addressObj.addressDetail = addressDetail;
        addressObj.postalCode = postalCode;
        return addressObj;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Users getUser() {
        return user;
    }

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
        return "Address{" +
                "addressId=" + addressId +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", createdAt='" + getCreatedAt() + '\'' +
                ", updatedAt='" + getUpdatedAt() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressId, address.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId);
    }
}
