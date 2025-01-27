package com.nqlo.ch.mkt.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nqlo.ch.mkt.service.dto.TimeResponseDTO;

@Service
public class DateService {

    @Autowired
    private RestTemplate rt;

    public TimeResponseDTO getDate() {
        try {
            final String URL = "https://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires";
            return rt.getForObject(URL, TimeResponseDTO.class);
        } catch (RestClientException e) {
            System.err.println("Error authenticating with external API " + e.getMessage());
            return null;
        }
    }
}
