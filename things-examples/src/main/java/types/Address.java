package types;

import org.hibernate.annotations.GenericGenerator;
import things.model.types.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 10/05/14
 * Time: 6:59 PM
 */
@Value(typeName = "address")
@Entity
public class Address {

    private String city;
    private String country;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private int nr;
    private String street;

    public Address() {

    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getNr() {
        return nr;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", nr=" + nr +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
