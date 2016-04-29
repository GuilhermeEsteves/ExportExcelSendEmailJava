package DAO;

import Models.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProdutoDAO {

    public List<Produto> Get() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("AtividadeHibernate");

        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery("SELECT e FROM Produto e");
        List<Produto> produtos = query.getResultList();

        return produtos;
    }
}
