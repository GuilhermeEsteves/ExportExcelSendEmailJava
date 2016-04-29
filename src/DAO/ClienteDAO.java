package DAO;

import Models.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class ClienteDAO {
    EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("AtividadeHibernate");

    public List<Cliente> Get() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("AtividadeHibernate");

        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT e FROM Cliente e");
        List<Cliente> clientes = query.getResultList();

        return clientes;
    }
}
