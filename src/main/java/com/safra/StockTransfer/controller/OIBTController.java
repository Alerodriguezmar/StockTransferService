package com.safra.StockTransfer.controller;

import com.safra.StockTransfer.entities.OIBT;
import com.safra.StockTransfer.services.OIBTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("OIBT")
public class OIBTController {



    @Autowired
    private OIBTService oibtService;


    @GetMapping("size")
    public ResponseEntity<Integer> sizeData() {
        return ResponseEntity.status(HttpStatus.OK).body(oibtService.findAll().size());
    }

    @GetMapping("items")
    public ResponseEntity<List<OIBT>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(oibtService.findAll().stream().distinct().collect(Collectors.toList()));
    }
}
