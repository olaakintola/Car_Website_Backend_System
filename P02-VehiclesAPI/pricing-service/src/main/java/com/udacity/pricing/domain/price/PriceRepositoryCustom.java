package com.udacity.pricing.domain.price;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@BasePathAwareController
public class PriceRepositoryCustom {

    private final PriceRepository priceRepository;

    public PriceRepositoryCustom(@Lazy PriceRepository priceRepository){
        this.priceRepository = priceRepository;
    }

    @RequestMapping(path = "/generatePrice/replacementPrice/{vehicleId}", method = RequestMethod.GET)
    @ResponseBody
    public Price newPriceForVehicileId(@PathVariable Long vehicleId) {

        Price price = null;
        List<Price> priceList = new ArrayList<>();
        Iterable<Price> allPrice = priceRepository.findAll();
        Iterator<Price> iterator = allPrice.iterator();

        while(iterator.hasNext()){
            Price nextPrice = iterator.next();
            System.out.println("PRICECLIENT "+ nextPrice.getPrice() + nextPrice.getVehicleId() );
            if( nextPrice.getVehicleId().equals(vehicleId)){
                price = new Price("USD", randomPrice(), vehicleId);

                System.out.println("IF STATEMENT PRICECLIENT "+ nextPrice.getPrice() + nextPrice.getVehicleId());
                System.out.println("IF STATEMENT new price "+ price.getPrice() + price.getVehicleId() );

                priceRepository.delete(nextPrice);
                priceList.add(price);
            }

            if(!nextPrice.getVehicleId().equals(vehicleId) ){
                priceList.add(nextPrice);
            }

        }

        priceRepository.saveAll(priceList);
        return price;
    }

    public static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
