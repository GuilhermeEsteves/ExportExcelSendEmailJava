
package DAO;

import Models.NotaFiscalitem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class NotaFiscalItemDAO {
     public List<NotaFiscalitem> Get() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("AtividadeHibernate");

        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT e FROM NotaFiscalitem e");
        List<NotaFiscalitem> notasFicaisItem = query.getResultList();

        return notasFicaisItem;
    }
}
