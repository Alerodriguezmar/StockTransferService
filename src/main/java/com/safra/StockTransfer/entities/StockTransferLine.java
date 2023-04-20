package com.safra.StockTransfer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferLine {
    @JsonProperty("ItemCode")
    private String ItemCode;
    @JsonProperty("Quantity")
    private Double Quantity;
    @JsonProperty("BatchNumbers")
    private List<BatchNumber> BatchNumbers;
    @JsonProperty("WarehouseCode")
    private String WarehouseCode;

}
