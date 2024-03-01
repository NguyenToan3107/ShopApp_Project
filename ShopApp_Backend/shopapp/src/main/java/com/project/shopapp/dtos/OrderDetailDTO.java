package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order's ID must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 0")
    private Long productId;

    @Min(value = 0, message = "Product's ID must be >= 0")
    private Float price;

    @Min(value = 1, message = "Number Of Product must be >= 1")
    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @Min(value = 0, message = "Total Money must be >= 0")
    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;
}
