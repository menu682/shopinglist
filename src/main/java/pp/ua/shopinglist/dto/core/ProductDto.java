package pp.ua.shopinglist.dto.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pp.ua.shopinglist.persistence.model.entity.Unit;

import java.math.BigDecimal;

public record ProductDto(

        Long id,

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Unit is required")
        Unit unit,

        BigDecimal lastPrice

) {
}
