package com.udacity.boogle.maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/maps")
public class MapsController {

    private final MapsService mapsService;
    public MapsController(MapsService mapsService){
        this.mapsService = mapsService;
    }
    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {
        return mapsService.returnAddress(lat, lon);
    }

}
