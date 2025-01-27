package com.nqlo.ch.mkt.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqlo.ch.mkt.service.dto.TimeResponseDTO;
import com.nqlo.ch.mkt.service.services.DateService;

@RestController
@RequestMapping("/api/date")
public class DateController {
    
    @Autowired
    private DateService dateService;

    private int counter = 0;
    private String lastDate = "N/A";


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> getDate(){
        System.out.println("gettingDate");
        TimeResponseDTO currentDate = dateService.getDate();
        counter++;
        if (currentDate == null){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service unavailable");
        }

        String msg = String.format(
            "Fecha Actual: %s %s %d, %d\n" +
            "Hora: %s\n" +
            "Numero de Invocaciones: %d\n" +
            "Last Date: %s",
            currentDate.getDayOfWeek(),
            currentDate.getMonth(),
            currentDate.getDay(),
            currentDate.getYear(),
            currentDate.getTime(),
            counter,
            lastDate
        );

        lastDate = String.format("%s %s %d, %d %s",
            currentDate.getDayOfWeek(),
            currentDate.getMonth(),
            currentDate.getDay(),
            currentDate.getYear(),
            currentDate.getTime()
        );

        return ResponseEntity.ok(msg);
    }

}
