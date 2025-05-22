package oort.cloud.openmarket.shipment.controller;

import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.annotations.AccessToken;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.common.paging.offset.OffsetPageResponse;
import oort.cloud.openmarket.shipment.controller.request.ShipmentCreateRequest;
import oort.cloud.openmarket.shipment.controller.request.ShipmentSearchRequest;
import oort.cloud.openmarket.shipment.controller.response.ShipmentCreateResponse;
import oort.cloud.openmarket.shipment.controller.response.ShipmentResponse;
import oort.cloud.openmarket.shipment.enums.ShipmentStatus;
import oort.cloud.openmarket.shipment.service.ShipmentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/v1/seller/shipment")
    public ResponseEntity<ShipmentCreateResponse> createShipment(
            @AccessToken AccessTokenPayload user,
            @RequestBody @Valid ShipmentCreateRequest request
            ){
        return ResponseEntity.ok()
                .body(shipmentService.createShipment(user.getUserId(), request));
    }

    @GetMapping("/v1/seller/shipment")
    public ResponseEntity<OffsetPageResponse<ShipmentResponse>> getShipmentList(
            Pageable pageable,
            @AccessToken AccessTokenPayload user,
            @ModelAttribute ShipmentSearchRequest request
            ){
        return ResponseEntity.ok()
                .body(shipmentService.getShipmentListByUserId(user.getUserId(), pageable, request));
    }

    @PatchMapping("/v1/seller/shipment/{shipmentId}")
    public ResponseEntity<Void> modifyShipmentStatus(
            @PathVariable Long shipmentId,
            @RequestParam("status") ShipmentStatus status
            ){
        shipmentService.modifyStatus(shipmentId, status);
        return ResponseEntity.ok().build();
    }
}
