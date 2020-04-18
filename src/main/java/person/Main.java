package person;

import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Log4j2
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        PersonService service =new PersonService(em);

        em.getTransaction().begin();

        for(int i=0;i<1000;i++)
           service.create(service.randomPerson());

        em.getTransaction().commit();

        log.info("All Persons in the database:");
        service.findAll().forEach(log::info);

        em.close();
        emf.close();
    }
}
