package oort.cloud.openmarket.user.repository;

import oort.cloud.openmarket.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmail(String email);
    Users findByEmail(String email);
}
