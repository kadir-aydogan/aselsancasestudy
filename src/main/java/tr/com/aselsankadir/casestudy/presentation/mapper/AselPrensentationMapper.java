package tr.com.aselsankadir.casestudy.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import tr.com.aselsankadir.casestudy.domain.common.AselId;
import tr.com.aselsankadir.casestudy.domain.menu.AselRestaurantMenu;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderDetail;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderItemDetail;
import tr.com.aselsankadir.casestudy.presentation.dto.AselMenuItemDTO;
import tr.com.aselsankadir.casestudy.presentation.dto.AselOrderDTO;
import tr.com.aselsankadir.casestudy.presentation.dto.AselOrderItemDTO;
import tr.com.aselsankadir.casestudy.presentation.dto.AselRestaurantMenuDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AselPrensentationMapper {

    AselOrderDTO toDto(AselOrderDetail detail);

    List<AselOrderDTO> toDtoList(List<AselOrderDetail> details);

    AselOrderItemDTO toItemDto(AselOrderItemDetail detail);

    List<AselOrderItemDTO> toItemDtoList(List<AselOrderItemDetail> details);

    AselMenuItemDTO toMenuItemDto(AselMenuItem item);

    List<AselMenuItemDTO> toMenuItemDtoList(List<AselMenuItem> items);


    AselRestaurantMenuDTO toMenuDto(AselRestaurantMenu menu);

    List<AselRestaurantMenuDTO> toMenuDtoList(List<AselRestaurantMenu> menus);


    @Named("longToString")
    static String longToString(Long value) {
        return value != null ? value.toString() : null;
    }

    default String map(Long value) {
        return longToString(value);
    }

    default String map(AselId id) {
        return id != null ? String.valueOf(id.getValue()) : null;
    }
}
