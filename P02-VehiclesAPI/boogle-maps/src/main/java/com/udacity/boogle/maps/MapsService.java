package com.udacity.boogle.maps;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapsService {

    private Map<String, Address> addressMap = new HashMap<String, Address>();

    public Address returnAddress(Double lat, Double lon){

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
