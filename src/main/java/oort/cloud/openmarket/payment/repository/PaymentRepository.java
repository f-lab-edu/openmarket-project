package oort.cloud.openmarket.payment.repository;

import oort.cloud.openmarket.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByExternalOrderId(String externalOrderId);
}
