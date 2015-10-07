package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CatUIEConfActDocumetoId implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long catUIEId;
    private Long confActividadDocumentoId;

    public CatUIEConfActDocumetoId() {
    }

    public CatUIEConfActDocumetoId(Long catUIEId, Long confActividadDocumentoId) {
        this.catUIEId = catUIEId;
        this.confActividadDocumentoId = confActividadDocumentoId;
    }

    @Column(name = "catUIE_id", nullable = false, precision = 18, scale = 0)
    public Long getCatUIEId() {
        return catUIEId;
    }

    public void setCatUIEId(Long catUIEId) {
        this.catUIEId = catUIEId;
    }

    @Column(name = "confActividadDocumento_id", nullable = false, precision = 18, scale = 0)
    public Long getConfActividadDocumentoId() {
        return confActividadDocumentoId;
    }

    public void setConfActividadDocumentoId(Long confActividadDocumentoId) {
        this.confActividadDocumentoId = confActividadDocumentoId;
    }

}
