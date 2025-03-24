package tr.com.aselsankadir.casestudy.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tr.com.aselsankadir.casestudy.application.service.AselRestaurantMenuService;
import tr.com.aselsankadir.casestudy.domain.menu.AselRestaurantMenu;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.presentation.dto.ResponseBody;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Validated
@Tag(name = "Restorant Menüsü", description = "Restorant menüsü görüntüleme")
public class AselRestaurantMenuController {

    private final AselRestaurantMenuService restaurantMenuService;

    @GetMapping("/{restaurantId}/menu")
    @Operation(
            summary = "Menü Getir",
            description = "Tarih Formatı: yyyy-mm-dd"
    )
    public ResponseEntity<ResponseBody<AselRestaurantMenu>> getMenu(@PathVariable @NotNull(message = "Menüsünü görmek istediğiniz restorantı seçiniz.") Long restaurantId,
                                                                    @RequestParam(name = "date") @NotNull(message = "Lütfen menü tarihi seçiniz.") LocalDate date) {

        Optional<AselRestaurantMenu> menuOpt = restaurantMenuService.findByDate(new RestaurantId(restaurantId), date);

        if (menuOpt.isEmpty()) {
            return ResponseEntity.ok(ResponseBody.error("Bu güne ait menü bulunamadı."));
        }

        return ResponseEntity.ok(ResponseBody.success(menuOpt.get()));
    }
}
