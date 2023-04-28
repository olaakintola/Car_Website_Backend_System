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

    private Map<String, Address> addressMap = new HashMap<String, Address>();
    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {

        String latitude  = String.valueOf(lat);
        String longitude = String.valueOf(lon);
        String coordinates = latitude + "-" + longitude ;
        if(addressMap.containsKey(coordinates) ){
            return addressMap.get(coordinates);
        }else {
            Address newAddress = generateNewAddress();
            addressMap.put(coordinates, newAddress);
            return newAddress;
        }
    }


    private Address generateNewAddress(){
        return MockAddressRepository.getRandom();
    }


}
