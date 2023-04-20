package com.safra.StockTransfer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safra.StockTransfer.entities.OIBT;
import com.safra.StockTransfer.entities.StockTransferRequest;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface InventoryTransferService {


    String RollTransferData(HttpHeaders cookies, StockTransferRequest stockTransferRequest);


    List<OIBT> transferStockMin(HttpHeaders cookies) throws JsonProcessingException;

}
