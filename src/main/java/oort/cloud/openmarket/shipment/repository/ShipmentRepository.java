package oort.cloud.openmarket.shipment.repository;

import oort.cloud.openmarket.shipment.entity.Shipment;
import oort.cloud.openmarket.shipment.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Shipment s set s.status = :status where s.shipmentId = :shipmentId and s.status <> :status")
    int updateShipmentStatus(@Param("shipmentId") Long shipmentId, @Param("status") ShipmentStatus status);
}
