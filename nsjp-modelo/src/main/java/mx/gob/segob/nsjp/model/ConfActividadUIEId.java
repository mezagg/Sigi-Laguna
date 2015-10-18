package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConfActividadUIEId implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long catUIEId;
    private Long tipoActividadValor;

    public ConfActividadUIEId() {
    }

    public ConfActividadUIEId(Long catUIEId, Long tipoActividadValor) {
        this.catUIEId = catUIEId;
        this.tipoActividadValor = tipoActividadValor;
    }

    @Column(name = "catUIE_id", nullable = false, precision = 18, scale = 0)
    public Long getCatUIEId() {
        return catUIEId;
    }

    public void setCatUIEId(Long catUIEId) {
        this.catUIEId = catUIEId;
    }

    @Column(name = "TipoActividad_val", nullable = false, precision = 18, scale = 0)
    public Long getTipoActividadValor() {
        return tipoActividadValor;
    }

    public void setTipoActividadValor(Long confActividadDocumentoRolId) {
        this.tipoActividadValor = confActividadDocumentoRolId;
    }

}
