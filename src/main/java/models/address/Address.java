package models.address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class Address {
    private String alias;
    private String firstName;
    private String lastName;
    private String company;
    private String vatNumber;
    private String address;
    private String addressComplement;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
}
