package com.example.controller;

import com.example.dao.Dao;
import com.example.service.FReader;
import com.vaadin.spring.annotation.SpringComponent;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@SpringComponent
@RestController
@RequestMapping("/test/insert")
public class PostController {
    @Autowired
    FReader fr;

    @Autowired
    Dao dao;


    @Value("${file.path}")
    String BASE_PATH;
    @Value("${file.name}")
    String FILE_NAME;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity res() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, SQLException {

        //"/Users/kamilai/Desktop/transactions.txt"
        List<String>  lines  = fr.getLines(  BASE_PATH + FILE_NAME  );

        List<List> groups = fr.groupLines(lines);

//      String json =  fr.getDataJson(groups.get(0));
//        System.out.println(json);
        JSONArray result = fr.createArrayOfJson(groups);
        System.out.println(result.toString());

        dao.addTransactionInDb(result);

        return new ResponseEntity<>(
               result,
                HttpStatus.OK);
    }
}
