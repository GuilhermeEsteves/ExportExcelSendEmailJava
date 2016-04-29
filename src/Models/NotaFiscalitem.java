package Models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class NotaFiscalitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private float Valor;
    
    @ManyToOne
    @JoinColumn(name="IdProduto")
    private Produto Produto;

    public Produto getProduto() {
        return Produto;
    }

    public void setProduto(Produto Produto) {
        this.Produto = Produto;
    }

    public NotaFiscal getNotaFical() {
        return NotaFical;
    }

    public void setNotaFical(NotaFiscal NotaFical) {
        this.NotaFical = NotaFical;
    }

    public float getValor() {
        return Valor;
    }

    public void setValor(float Valor) {
        this.Valor = Valor;
    }
    
    @ManyToOne
    @JoinColumn(name="IdNotaFiscal")
    private NotaFiscal NotaFical;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaFiscalitem)) {
            return false;
        }
        NotaFiscalitem other = (NotaFiscalitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.NotaFiscalitem[ id=" + id + " ]";
    }
    
}
