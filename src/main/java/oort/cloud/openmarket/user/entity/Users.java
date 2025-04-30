package oort.cloud.openmarket.user.entity;


import jakarta.persistence.*;
import oort.cloud.openmarket.common.entity.BaseTimeEntity;
import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    protected Users() {}

    public static Users createUser(String email, String password, String userName, String phone, UserRole userRole){
        Users users = new Users();
        users.email = email;
        users.password = password;
        users.userName = userName;
        users.phone = phone;
        users.userRole = userRole;
        users.userStatus = UserStatus.ACTIVE;
        return users;
    }
    public void addAddress(Address address){
        this.address.add(address);
        address.setUser(this);
    }

    public Address findAddress(Long addressId){
        return this.address.stream()
                .filter(addressObj -> addressObj.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new NotFoundResourceException("저장된 주소가 없습니다."));
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }


    public String getPassword(){return password;};

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(userId, users.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", userRole=" + userRole +
                ", userStatus=" + userStatus +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
