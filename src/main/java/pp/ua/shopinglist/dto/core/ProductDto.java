package pp.ua.shopinglist.dto.core;

import jakarta.validation.constraints.NotBlank;

public record ProductDto(

        Long id,

        @NotBlank(message = "Product name is required")
        String name

) {
}
