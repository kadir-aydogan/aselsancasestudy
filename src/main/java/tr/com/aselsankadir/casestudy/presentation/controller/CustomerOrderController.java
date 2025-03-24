package tr.com.aselsankadir.casestudy.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tr.com.aselsankadir.casestudy.application.service.AselOrderService;
import tr.com.aselsankadir.casestudy.application.service.PlaceAselOrderUseCase;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderDetail;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.order.PlaceOrderCommand;
import tr.com.aselsankadir.casestudy.domain.orderitem.CreateOrderItemCommand;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.AselUser;
import tr.com.aselsankadir.casestudy.domain.user.UserId;
import tr.com.aselsankadir.casestudy.infrastructure.security.AuthenticatedUserUtil;
import tr.com.aselsankadir.casestudy.presentation.dto.AselOrderDTO;
import tr.com.aselsankadir.casestudy.presentation.dto.CreateAselOrderRequest;
import tr.com.aselsankadir.casestudy.presentation.dto.CreateMenuItemRequest;
import tr.com.aselsankadir.casestudy.presentation.dto.ResponseBody;
import tr.com.aselsankadir.casestudy.presentation.mapper.AselPrensentationMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/order")
@Validated
@Tag(name = "Müşteri Sipariş İşlemleri", description = "Müşteri sipariş oluşturup, onları görüntüleyebilir.")
public class CustomerOrderController {

    private final AselOrderService orderService;
    private final PlaceAselOrderUseCase placeOrderUseCase;
    private final AselPrensentationMapper mapper;

    @GetMapping("/{orderId}")
    @Operation(
            summary = "Sipariş Detayını Getir",
            description = "Belirli bir siparişe ait detay bilgileri döner."
    )
    public ResponseEntity<ResponseBody<AselOrderDTO>> getOrderDetails(@PathVariable @NotNull(message = "Lütfen sipariş seçiniz.") Long orderId) {

        AselUser user = AuthenticatedUserUtil.getCurrentUser();

        UserId customerId = user.getId();

        OrderId oid = new OrderId(orderId);

        AselOrderDetail orderDetails = orderService.getOrderForCustomer(customerId, oid);

        AselOrderDTO dto = mapper.toDto(orderDetails);

        return ResponseEntity.ok(ResponseBody.success(dto));
    }

    @GetMapping
    @Operation(
            summary = "Tüm Siparişleri Listele",
            description = "Müşterinin verdiği tüm siparişleri listeler."
    )
    public ResponseEntity<ResponseBody<List<AselOrderDTO>>> getOrders() {

        AselUser user = AuthenticatedUserUtil.getCurrentUser();

        List<AselOrderDetail> orders = orderService.getOrdersForCustomer(user.getId());

        List<AselOrderDTO> dtoList = mapper.toDtoList(orders);

        return ResponseEntity.ok(ResponseBody.success(dtoList));
    }

    @PostMapping
    @Operation(
            summary = "Sipariş Oluştur",
            description = "Müşteri sipariş oluşturabilir."
    )
    public ResponseEntity<ResponseBody<String>> placeOrder(@RequestBody @Valid CreateAselOrderRequest createAselOrder) {

        AselUser user = AuthenticatedUserUtil.getCurrentUser();

        List<CreateOrderItemCommand> itemCommands = new ArrayList<>();
        for (CreateMenuItemRequest dishId : createAselOrder.getDishIds()) {
            itemCommands.add(new CreateOrderItemCommand(new MenuItemId(dishId.getMenuItemId()), dishId.getQuantity()));
        }

        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(user.getId(), new RestaurantId(createAselOrder.getRestaurantId()), itemCommands);

        OrderId orderId = placeOrderUseCase.createOrder(placeOrderCommand);

        return ResponseEntity.ok(ResponseBody.success(orderId.getId().toString()));
    }
}
