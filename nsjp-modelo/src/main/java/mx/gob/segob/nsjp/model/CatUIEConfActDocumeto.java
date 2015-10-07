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
@Table(name = "CatUIEConfActDocumeto")
public class CatUIEConfActDocumeto implements Serializable {

    private static final long serialVersionUID = 1L;
    private CatUIEConfActDocumetoId id;
    private CatUIEspecializada catUIEspecializada;
    private ConfActividadDocumento confActividadDocumento;

    public CatUIEConfActDocumeto() {
    }

    public CatUIEConfActDocumeto(CatUIEConfActDocumetoId id, CatUIEspecializada catUIEspecializada, ConfActividadDocumento confActividadDocumento) {
        this.id = id;
        this.catUIEspecializada = catUIEspecializada;
        this.confActividadDocumento = confActividadDocumento;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "catUIEId", column = @Column(name = "catUIE_id", nullable = false, precision = 18, scale = 0)),
        @AttributeOverride(name = "confActividadDocumentoId", column = @Column(name = "confActividadDocumento_id", nullable = false, precision = 18, scale = 0))})
    public CatUIEConfActDocumetoId getId() {
        return id;
    }

    public void setId(CatUIEConfActDocumetoId id) {
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
    @JoinColumn(name = "confActividadDocumento_id", nullable = false, insertable = false, updatable = false)
    public ConfActividadDocumento getConfActividadDocumento() {
        return confActividadDocumento;
    }

    public void setConfActividadDocumento(ConfActividadDocumento confActividadDocumento) {
        this.confActividadDocumento = confActividadDocumento;
    }

}
