package oort.cloud.openmarket.user.repository;

import oort.cloud.openmarket.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = """
                    SELECT COUNT(*)
                    FROM users
                    WHERE email = :email
                    """,
    nativeQuery = true)
    int countByEmail(@Param("email") String email);

    @Query(value = """
                    SELECT user_id, email, password, user_name, phone, role, status, created_at, updated_at
                    FROM users
                    WHERE email = :email
                    """,
            nativeQuery = true)
    Users findByEmail(@Param("email") String email);
}
