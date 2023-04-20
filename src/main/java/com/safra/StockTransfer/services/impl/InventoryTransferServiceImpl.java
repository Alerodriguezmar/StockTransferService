package com.safra.StockTransfer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safra.StockTransfer.entities.BatchNumber;
import com.safra.StockTransfer.entities.OIBT;
import com.safra.StockTransfer.entities.StockTransferLine;
import com.safra.StockTransfer.entities.StockTransferRequest;
import com.safra.StockTransfer.exceptions.SQLConnectionException;
import com.safra.StockTransfer.services.InventoryTransferService;
import com.safra.StockTransfer.services.LoginService;
import com.safra.StockTransfer.services.OIBTService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.groupingBy;

@Service
public class InventoryTransferServiceImpl implements InventoryTransferService {

	@Autowired
	private RestTemplate restTemplate;


	@Autowired
	private OIBTService oibtService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Environment env;
	@Value("${inventory.table}")
	private String inventoryTableName;
	@Autowired
	private LoginService loginService;
	private static final String LOCATION_ATTRIBUTE_NAME_IN_TABLE = "U_HBT_Ubicacion";
	private final Logger log = LoggerFactory.getLogger(InventoryTransferServiceImpl.class);
	private static final int MAX_ATTEMPS = 3;
	private int requestAttemps;


	@Override
	public String RollTransferData(HttpHeaders cookies, StockTransferRequest stockTransferRequest) {
		requestAttemps++;
		try {

			URI url = null;


			ResponseEntity<String> response = null;
			try {
				url = new URI(env.getProperty("url.sap") + "StockTransfers");

				//System.out.println(url);
				//System.out.println(stockTransferRequest);
			} catch (URISyntaxException ex) {
				java.util.logging.Logger.getLogger(InventoryTransferServiceImpl.class.getName()).log(Level.SEVERE, null,
						ex);
			}
			RequestEntity<StockTransferRequest> request = new RequestEntity<>(stockTransferRequest, loginService.getAuthorizationHeader(),
					HttpMethod.POST, url);
			try {
				response = restTemplate.exchange(request, String.class);
			} catch (RestClientException e) {

			}
			// retry when unauthorized
			if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				// refresh authorization cookies
				request = new RequestEntity<>(stockTransferRequest, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
				response = restTemplate.exchange(request, String.class);
			}
//			if (shelfLocation != null) {
//				performUpdateOperationInSAP_OITB_Table(shelfLocation, Batch);
//			}
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			if (requestAttemps <= MAX_ATTEMPS) {
				log.info(String.format("Retring once again after %d attemps", requestAttemps));
				//this.RollTransfer(cookies, ItemCode, WhsCodeExit, WhsCodeEntry, Quantity, Batch, shelfLocation);
			}
		} finally {
			// clean requestAttemps class variable
			requestAttemps = 0;
		}
		throw new RestClientException("It was not posible connect to " + env.getProperty("url.sap") + "StockTransfers");
	}

	@Override
	public List<OIBT> transferStockMin(HttpHeaders cookies) throws JsonProcessingException {


		StockTransferRequest stockTransferRequest = new StockTransferRequest("10013-2","CTO900134273",null);


		List<OIBT> oibts = oibtService.findAll().stream().distinct().collect(Collectors.toList());
				//.subList(0,2);



		stockTransferRequest.setStockTransferLines(this.stockTransferLinesByOIBT(oibts));

		ObjectMapper mapper = new ObjectMapper();

		String jsonString = mapper.writeValueAsString(stockTransferRequest);
		System.out.println(jsonString);

		try {

			URI url = null;


			ResponseEntity<String> response = null;
			try {
				url = new URI(env.getProperty("url.sap") + "StockTransfers");

				System.out.println(stockTransferRequest);

			} catch (URISyntaxException ex) {
				java.util.logging.Logger.getLogger(InventoryTransferServiceImpl.class.getName()).log(Level.SEVERE, null,
						ex);
			}
			RequestEntity<StockTransferRequest> request = new RequestEntity<>(stockTransferRequest, loginService.getAuthorizationHeader(),
					HttpMethod.POST, url);
			try {
				response = restTemplate.exchange(request, String.class);
			} catch (RestClientException e) {

			}
			// retry when unauthorized
			if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				// refresh authorization cookies
				request = new RequestEntity<>(stockTransferRequest, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
				response = restTemplate.exchange(request, String.class);
			}

			System.out.println(response.toString());

			return oibts;
		} catch (Exception e) {
			e.printStackTrace();
			if (requestAttemps <= MAX_ATTEMPS) {
				log.info(String.format("Retring once again after %d attemps", requestAttemps));
				//this.RollTransfer(cookies, ItemCode, WhsCodeExit, WhsCodeEntry, Quantity, Batch, shelfLocation);
			}
		} finally {
			// clean requestAttemps class variable
			requestAttemps = 0;
		}
		throw new RestClientException("It was not posible connect to " + env.getProperty("url.sap") + "StockTransfers");
	}

	/**
	 * Helper method that directly updates U_HTB_Ubication column in OIBT table for
	 * a roll due to inability to set it in the REST request using SAP API.
	 *
	 * @param shelfName   matches the shelf name in the warehouse location.
	 * @param batchNumber matches the BatchNumber of the material.
	 */
	private void performUpdateOperationInSAP_OITB_Table(String shelfName, String batchNumber) {
		try {
			jdbcTemplate.update("UPDATE" + " " + inventoryTableName + " " + "SET" + " "
					+ LOCATION_ATTRIBUTE_NAME_IN_TABLE + " " + "=" + " " + "'" + shelfName + "'" + " " + "WHERE" + " "
					+ "BatchNum" + " " + "=" + " " + "'" + batchNumber + "'" + ";");
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			throw new SQLConnectionException(
					"Hubo un error actualizando el campo" + " " + LOCATION_ATTRIBUTE_NAME_IN_TABLE + " "
							+ "para el material" + " " + "con el BatchNum:" + " " + batchNumber);
		}
	}

	/**
	 * This method is intended to be invoked whenever is required to know the actual
	 * quantity attribute of a material or {@link Fabric} object. Since it queries
	 * in the database this attribute directly.
	 * 
	 * @param batchNum unique identifier of a {@link Fabric} object.
	 * @param itemCode attribute of a {@link Fabric} object.
	 * @return the quantity attribute in {@link Double} type.
	 */



	public List<StockTransferLine> stockTransferLinesByOIBT(List<OIBT> oibts){

		List<StockTransferLine> stockTransferLines = new ArrayList<>();

		var mapItemCode = oibts.stream().collect(groupingBy(OIBT::getItemCode));


		AtomicInteger songIndex = new AtomicInteger();
		mapItemCode.forEach((s, oibts1) -> {


			var a =oibts1.stream().map(OIBT::getQuantity).map(Double::valueOf);

			var sum =a.mapToDouble(Double::doubleValue).sum();

			var sumResult = String.format("%.3f%n", sum);


			StockTransferLine stockTransferLine = new StockTransferLine();
			stockTransferLine.setWarehouseCode("10013-3");
			stockTransferLine.setQuantity(Double.valueOf(sumResult));
			stockTransferLine.setItemCode(s);

				List<BatchNumber> batchNumbers = new ArrayList<>();
		for(OIBT oibt: oibts1){
				batchNumbers.add(new BatchNumber(oibt.getBatchNum(),oibt.getQuantity(),String.valueOf(songIndex.get())));
			};
			songIndex.getAndIncrement();
			stockTransferLine.setBatchNumbers(batchNumbers);
			stockTransferLines.add(stockTransferLine);

		});

		return  stockTransferLines;
	}

}
