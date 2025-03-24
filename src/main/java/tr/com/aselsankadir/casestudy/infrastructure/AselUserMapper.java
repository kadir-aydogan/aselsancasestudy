package tr.com.aselsankadir.casestudy.infrastructure;

import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.AselUser;
import tr.com.aselsankadir.casestudy.domain.user.UserId;
import tr.com.aselsankadir.casestudy.infrastructure.jpa.AselUserEntity;

public class AselUserMapper {

    public static AselUser toDomain(AselUserEntity entity) {
        return AselUser.restore(UserId.restore(entity.getId()), new Email(entity.getEmail()), entity.getPassword(), entity.getRole(), new RestaurantId(entity.getRestaurantId()));
    }

    public static AselUserEntity toEntity(AselUser user) {
        AselUserEntity entity = new AselUserEntity();
        entity.setId(user.getId() != null ? user.getId().getId() : null);
        entity.setEmail(user.getEmail().email());
        entity.setPassword(user.getHashedPassword());
        entity.setRole(user.getRole());
        entity.setRestaurantId(user.getRestaurantId() != null ? user.getRestaurantId().getValue() : null);
        return entity;
    }
}
