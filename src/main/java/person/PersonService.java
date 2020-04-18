package person;

import com.github.javafaker.Faker;

import lombok.extern.log4j.Log4j2;
import person.model.Address;
import person.model.Person;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


@Log4j2
public class PersonService {

    private EntityManager em;
    private Faker faker = new Faker();

    public PersonService(EntityManager em){
        this.em=em;
    }

    public Person create(String name, LocalDate dob, Person.Gender gender, Address address,String email,String profession){

        Person person = Person.builder()
                        .name(name).dob(dob)
                        .gender(gender)
                        .address(address)
                        .email(email)
                        .profession(profession)
                        .build();

        em.persist(person);
        return person;
    }
    public Person create(Person person){
        em.persist(person);
        return person;
    }

    public Person randomPerson(){

        java.util.Date date = faker.date().birthday();
        java.time.LocalDate birthday = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Address address = Address.builder()
                        .country(faker.address().country())
                        .state(faker.address().state())
                        .city(faker.address().city())
                        .streetAddress(faker.address().streetAddress())
                        .zip(faker.address().zipCode())
                        .build();

        Person person = Person.builder()
                        .name(faker.name().fullName())
                        .dob(birthday)
                        .gender(faker.options().option(Person.Gender.class))
                        .address(address)
                        .email(faker.internet().emailAddress())
                        .profession(faker.company().profession())
                        .build();

        em.persist(person);
        return person;
    }

    public List<Person> findAll(){
        return em.createQuery("SELECT l FROM Person l ORDER BY l.id",Person.class).getResultList();
    }

}
