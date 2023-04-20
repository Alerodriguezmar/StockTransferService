package com.safra.StockTransfer.services.impl;

import com.safra.StockTransfer.entities.OIBT;
import com.safra.StockTransfer.repositories.OIBTRepository;
import com.safra.StockTransfer.services.OIBTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OIBTServiceImpl implements OIBTService {

    @Autowired
    private OIBTRepository oibtRepository;

    @Override
    public List<OIBT> findAll() {
        return oibtRepository.findAll();
    }
}
