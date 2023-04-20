package com.safra.StockTransfer;

import com.safra.StockTransfer.exceptions.NotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.*"})
public class StockTransferServiceServiceApplication {


	public static void main(String[] args) throws NotFoundException {
		SpringApplication.run(StockTransferServiceServiceApplication.class, args);
	}

}
