package com.safra.StockTransfer.repositories;

import com.safra.StockTransfer.entities.OIBT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OIBTRepository extends JpaRepository<OIBT,String> {


    @Query(nativeQuery = true, value = "select distinct\n" +
            " --TOP(100)\n" +
            "\n" +
            "      bt.ItemCode,\n" +
            "      bt.ItemName,\n" +
            "      bt.BatchNum, \n" +
            "      bt.Quantity ,\n" +
            "      tm.validFor\n" +
            "\n" +
            "\n" +
            "from OIBT AS bt\n" +
            "INNER JOIN OITM as tm ON bt.ItemCode = tm.ItemCode\n" +
            "\n" +
            "where  U_GrupoProd = '06'-- Telas\n" +
            "AND tm.ItemCode Like 'I_%%'\n" +
            "AND bt.WhsCode = '10013-2'\n" +
            "AND bt.quantity <> 0\n" +
            "AND  bt.Quantity <= 1.2\n" +
            "AND tm.validFor = 'Y'")
    List<OIBT> findAll();

}
