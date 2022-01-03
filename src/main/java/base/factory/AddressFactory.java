package base.factory;

import com.github.javafaker.Faker;
import models.address.Address;

public class AddressFactory {
    Faker faker = new Faker();

    public Address getRandomizedRequiredAddressFields(){
        return Address.builder()
                .address(faker.address().streetAddress())
                .city(faker.address().city())
                .postalCode(faker.number().digits(2)+"-"+ faker.number().digits(3))
                .build();
    }
}
