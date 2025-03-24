package tr.com.aselsankadir.casestudy.domain.dish;

import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;

public class AselDishNotFoundException extends AselRuntimeException {

    private static final String message = "Bu restorantta böyle bir yemek bulunamadı!";

    public AselDishNotFoundException() {
        super(message);
    }
}
