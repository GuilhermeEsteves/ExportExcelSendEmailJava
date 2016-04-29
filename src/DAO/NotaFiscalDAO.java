package DAO;

import Models.NotaFiscal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class NotaFiscalDAO {

    public List<NotaFiscal> Get() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("AtividadeHibernate");

        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT e FROM NotaFiscal e");
        List<NotaFiscal> notasFicais = query.getResultList();

        return notasFicais;
    }
}
