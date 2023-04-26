package com.udacity.vehicles.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    private MediaType contentType = new MediaType("application", "hal+json", Charset.forName("UTF-8"));

    private static String BASE_PATH = "http://localhost/cars";

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @Before
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        Location updatedLocation = getFullLocation(car.getLocation());
        given(mapsClient.getAddress(any())).willReturn(updatedLocation);
        car.setLocation(updatedLocation);
        String carPrice = "USD 17572.98";
        given(priceClient.getPrice(any())).willReturn(carPrice);
        car.setPrice(carPrice);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));
    }

    private Location getFullLocation(Location updatedLocation) {
        updatedLocation.setAddress("420 Koriko Boulevard");
        updatedLocation.setCity("Atlanta");
        updatedLocation.setState("Georgia");
        updatedLocation.setZip("21876");

        return updatedLocation;
    }

    /**
     * Tests for successful creation of new car in the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();

        final ResultActions result = mvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8));
        result.andExpect(status().isCreated());
        verifyJson(result, car);
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listCars() throws Exception {
        /**
         * TODO: Add a test to check that the `get` method works by calling
         *   the whole list of vehicles. This should utilize the car from `getCar()`
         *   below (the vehicle will be the first in the list).
         */

        Car car = getCar();
        mvc.perform(
                get(new URI("/cars")))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.carList[0].details.body").value(car.getDetails().getBody()))
                .andExpect(jsonPath("$._embedded.carList[0]._links.self.href").value(BASE_PATH + "/1"))
                .andExpect(jsonPath("$._embedded.carList[0]._links.cars.href").value(BASE_PATH))
                .andExpect(jsonPath("$._links.self.href").value(BASE_PATH));

    }

    /**
     * Tests the read operation for a single car by ID.
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
        /**
         * TODO: Add a test to check that the `get` method works by calling
         *   a vehicle by ID. This should utilize the car from `getCar()` below.
         */

        Car car = getCar();
        Location updatedLocation = getFullLocation(car.getLocation());
        car.setLocation(updatedLocation);
        String carPrice = "USD 17572.98";
        given(priceClient.getPrice(any())).willReturn(carPrice);
        car.setPrice(carPrice);

        Long carId = Long.valueOf(1);
        final ResultActions result = mvc.perform(get(new URI("/cars"+"/"+carId)));
        result.andExpect(status().isOk());

        result
                .andExpect(jsonPath("$.details.body").value( car.getDetails().getBody()))
                .andExpect(jsonPath("$.details.model").value( car.getDetails().getModel()))
                .andExpect(jsonPath("$.details.manufacturer.code").value( car.getDetails().getManufacturer().getCode()))
                .andExpect(jsonPath("$.details.manufacturer.name").value( car.getDetails().getManufacturer().getName()))
                .andExpect(jsonPath("$.details.numberOfDoors").value( car.getDetails().getNumberOfDoors()))
                .andExpect(jsonPath("$.details.fuelType").value( car.getDetails().getFuelType()))
                .andExpect(jsonPath("$.details.engine").value( car.getDetails().getEngine()))
                .andExpect(jsonPath("$.details.mileage").value( car.getDetails().getMileage()))
                .andExpect(jsonPath("$.details.modelYear").value( car.getDetails().getModelYear()))
                .andExpect(jsonPath("$.details.productionYear").value( car.getDetails().getProductionYear()))
                .andExpect(jsonPath("$.details.externalColor").value( car.getDetails().getExternalColor()))
                .andExpect(jsonPath("$.location.lat").value( car.getLocation().getLat()))
                .andExpect(jsonPath("$.location.lon").value( car.getLocation().getLon()))
                .andExpect(jsonPath("$.location.address").value( car.getLocation().getAddress()))
                .andExpect(jsonPath("$.location.city").value( car.getLocation().getCity()))
                .andExpect(jsonPath("$.location.state").value( car.getLocation().getState()))
                .andExpect(jsonPath("$.location.zip").value( car.getLocation().getZip()))
                .andExpect(jsonPath("$.price").value( car.getPrice()))
                .andExpect(jsonPath("$._links.self.href").value(BASE_PATH + "/1"))
                .andExpect(jsonPath("$._links.cars.href").value( BASE_PATH));

    }

    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {
        /**
         * TODO: Add a test to check whether a vehicle is appropriately deleted
         *   when the `delete` method is called from the Car Controller. This
         *   should utilize the car from `getCar()` below.
         */

        Long carId = Long.valueOf(1);
        mvc.perform(delete("/cars"+"/"+carId))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void updateCar() throws Exception{
        Car car = getCar();

        Long carId = Long.valueOf(1);
        final ResultActions result = mvc.perform(put(new URI("/cars"+"/"+carId))
                .content(json.write(car).getJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result.andExpect(status().isOk());
        verifyJson(result, car);

    }

    /**
     * Creates an example Car object for use in testing.
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }

    private void verifyJson(final ResultActions action, Car car) throws Exception {
        action
                .andExpect(jsonPath("$.details.body").value( car.getDetails().getBody()))
                .andExpect(jsonPath("$.details.model").value( car.getDetails().getModel()))
                .andExpect(jsonPath("$.details.manufacturer.code").value( car.getDetails().getManufacturer().getCode()))
                .andExpect(jsonPath("$.details.manufacturer.name").value( car.getDetails().getManufacturer().getName()))
                .andExpect(jsonPath("$.details.numberOfDoors").value( car.getDetails().getNumberOfDoors()))
                .andExpect(jsonPath("$.details.fuelType").value( car.getDetails().getFuelType()))
                .andExpect(jsonPath("$.details.engine").value( car.getDetails().getEngine()))
                .andExpect(jsonPath("$.details.mileage").value( car.getDetails().getMileage()))
                .andExpect(jsonPath("$.details.modelYear").value( car.getDetails().getModelYear()))
                .andExpect(jsonPath("$.details.productionYear").value( car.getDetails().getProductionYear()))
                .andExpect(jsonPath("$.details.externalColor").value( car.getDetails().getExternalColor()))
                .andExpect(jsonPath("$.location.lat").value( car.getLocation().getLat()))
                .andExpect(jsonPath("$.location.lon").value( car.getLocation().getLon()))
                .andExpect(jsonPath("$._links.self.href").value(BASE_PATH + "/1"))
                .andExpect(jsonPath("$._links.cars.href").value( BASE_PATH));
    }

}