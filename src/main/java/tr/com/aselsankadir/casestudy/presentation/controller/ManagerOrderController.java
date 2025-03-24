package tr.com.aselsankadir.casestudy.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tr.com.aselsankadir.casestudy.application.service.AselOrderService;
import tr.com.aselsankadir.casestudy.application.service.AselOrderUpdateStatusUseCase;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderDetail;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.AselUser;
import tr.com.aselsankadir.casestudy.infrastructure.security.AuthenticatedUserUtil;
import tr.com.aselsankadir.casestudy.presentation.dto.AselOrderDTO;
import tr.com.aselsankadir.casestudy.presentation.dto.ResponseBody;
import tr.com.aselsankadir.casestudy.presentation.dto.UpdateOrderStatusRequest;
import tr.com.aselsankadir.casestudy.presentation.mapper.AselPrensentationMapper;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/manager/order")
@Tag(name = "Restorant Yöneticisi Sipariş İşlemleri", description = "Restorant siparişlerini görüntüleme ve yönetme.")
public class ManagerOrderController {

    private final AselOrderService orderService;
    private final AselOrderUpdateStatusUseCase orderUpdateStatusUseCase;
    private final AselPrensentationMapper mapper;


    @GetMapping("/{orderId}")
    @Operation(
            summary = "Sipariş Detayını Getir",
            description = "Belirli bir siparişe ait detay bilgileri döner."
    )
    public ResponseEntity<ResponseBody<AselOrderDTO>> getOrder(@PathVariable @NotNull(message = "Lütfen detayını görüntülemek istediğiniz siparişi seçin.") Long orderId) {

        AselUser manager = AuthenticatedUserUtil.getCurrentUser();

        RestaurantId restaurantId = manager.getRestaurantId();

        AselOrderDetail order = orderService.getOrderForRestaurant(restaurantId, new OrderId(orderId));

        AselOrderDTO dto = mapper.toDto(order);

        return ResponseEntity.ok(ResponseBody.success(dto));
    }

    @GetMapping
    @Operation(
            summary = "Tüm Siparişleri Listele",
            description = "Restoranta gelen tüm siparişleri listeler."
    )
    public ResponseEntity<ResponseBody<List<AselOrderDTO>>> getOrders() {
        AselUser manager = AuthenticatedUserUtil.getCurrentUser();

        RestaurantId restaurantId = manager.getRestaurantId();

        List<AselOrderDetail> orderList = orderService.getOrdersForRestaurant(restaurantId);

        return ResponseEntity.ok(ResponseBody.success(mapper.toDtoList(orderList)));
    }

    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            summary = "Sipariş durumu güncelleme.",
            description = "Bir siparişin durumunu günceller."
    )
    public ResponseEntity<ResponseBody<Void>> updateStatus(
            @PathVariable @NotNull(message = "Lütfen durumunu değiştirmek istediğiniz siparişi seçin.") Long orderId,
            @RequestBody @Valid UpdateOrderStatusRequest request) {

        AselUser user = AuthenticatedUserUtil.getCurrentUser();

        RestaurantId id = user.getRestaurantId();

        int updated = orderUpdateStatusUseCase.updateStatus(id, new OrderId(orderId), request.getStatus());

        if (updated > 0) {
            return ResponseEntity.ok(ResponseBody.success());
        }

        return ResponseEntity.badRequest().body(ResponseBody.error("Sipariş durumu güncellenemedi!"));
    }
}
