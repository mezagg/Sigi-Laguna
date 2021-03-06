package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Parametros entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Parametro")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="valor")
public class Parametro implements java.io.Serializable {

    // Fields
    private Long parametroId;
    private String clave;
    private String valor;
    private String descripcion;
    private String tipoValor;

    // Constructors

    /** default constructor */
    public Parametro() {
    }

    public Parametro(Long parametroId, String clave, String valor, String descripcion, String tipoValor) {
        this.parametroId=parametroId;
        this.clave=clave;
        this.valor=valor;
        this.descripcion = descripcion;
        this.tipoValor=tipoValor;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Parametro_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getParametroId() {
        return this.parametroId;
    }

    public void setParametroId(Long ParametrosId) {
        this.parametroId = ParametrosId;
    }

    @Column(name = "cClave", length = 30)
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    @Column(name = "cValor", length = 80)
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    @Column(name = "cDescripcion", length = 150)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Column(name = "cTipoValor", length = 1)
    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }
    @Transient
    public Long getValorAsLong() {
        return Long.parseLong(valor);
    }

}
