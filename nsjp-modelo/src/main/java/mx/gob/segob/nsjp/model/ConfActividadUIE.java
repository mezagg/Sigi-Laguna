package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Pamela Lopez
 */
@Entity
@Table(name = "ConfActividadUIE")
public class ConfActividadUIE implements Serializable {

    private static final long serialVersionUID = 1L;
    private ConfActividadUIEId id;
    private CatUIEspecializada catUIEspecializada;
    private Valor tipoActividad;

    public ConfActividadUIE() {
    }

    public ConfActividadUIE(ConfActividadUIEId id) {
        this.id = id;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "catUIEId", column = @Column(name = "catUIE_id", nullable = false, precision = 18, scale = 0)),
        @AttributeOverride(name = "tipoActividadValor", column = @Column(name = "TipoActividad_val", nullable = false, precision = 18, scale = 0))})
    public ConfActividadUIEId getId() {
        return id;
    }

    public void setId(ConfActividadUIEId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catUIE_id", nullable = false, insertable = false, updatable = false)
    public CatUIEspecializada getCatUIEspecializada() {
        return catUIEspecializada;
    }

    public void setCatUIEspecializada(CatUIEspecializada catUIEspecializada) {
        this.catUIEspecializada = catUIEspecializada;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoActividad_val", nullable = false, insertable = false, updatable = false)
    public Valor getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(Valor tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

}
