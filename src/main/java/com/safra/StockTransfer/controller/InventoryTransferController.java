package com.safra.StockTransfer.controller;

import com.safra.StockTransfer.config.CertificateSSLConfig;
import com.safra.StockTransfer.entities.OIBT;
import com.safra.StockTransfer.entities.StockTransferRequest;
import com.safra.StockTransfer.services.InventoryTransferService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("InventoryTransfer")
public class InventoryTransferController {

	@Autowired
	private InventoryTransferService inventoryTransferService;


	@PostMapping("transfer")
	public String transferData(HttpServletRequest request, @RequestBody StockTransferRequest stockTransferRequest) {
		HttpSession session = request.getSession();
		new CertificateSSLConfig().trustSelfSignedSSL();
		try {
			System.out.println(stockTransferRequest.toString());
			return inventoryTransferService.RollTransferData((HttpHeaders) session.getAttribute("cookies"), stockTransferRequest);
		} catch (Exception e) {
			//pendingRollTransferingService.save(roll);
			//throw new RestClientException(ERPHttpConnectionErrorMessages.HTTP_ERP_CONECCTION_ERROR.getMessage());
		}
		return null;
	}


	@PostMapping("partialTransfer")
	public List<OIBT> partialTransfer(HttpServletRequest request) {
		HttpSession session = request.getSession();
		new CertificateSSLConfig().trustSelfSignedSSL();
		try {
			return inventoryTransferService.transferStockMin((HttpHeaders) session.getAttribute("cookies"));
		} catch (Exception e) {
			//pendingRollTransferingService.save(roll);
			//throw new RestClientException(ERPHttpConnectionErrorMessages.HTTP_ERP_CONECCTION_ERROR.getMessage());
		}
		return null;
	}



}
