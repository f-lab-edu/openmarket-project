package oort.cloud.openmarket.shipment.repository;

import oort.cloud.openmarket.shipment.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
