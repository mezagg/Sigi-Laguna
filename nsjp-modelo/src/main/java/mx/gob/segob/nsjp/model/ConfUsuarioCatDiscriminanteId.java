package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Pamela Lopez
 */
@Embeddable
public class ConfUsuarioCatDiscriminanteId implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long usuarioId;
    private Long catDiscriminanteId;

    public ConfUsuarioCatDiscriminanteId() {
    }

    public ConfUsuarioCatDiscriminanteId(Long usuarioId, Long catDiscriminanteId) {
        this.usuarioId = usuarioId;
        this.catDiscriminanteId = catDiscriminanteId;
    }

    @Column(name = "Usuario_id", nullable = false, precision = 18, scale = 0)
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Column(name = "catDiscriminante_id", nullable = false, precision = 18, scale = 0)
    public Long getCatDiscriminanteId() {
        return catDiscriminanteId;
    }

    public void setCatDiscriminanteId(Long catDiscriminanteId) {
        this.catDiscriminanteId = catDiscriminanteId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.usuarioId != null ? this.usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfUsuarioCatDiscriminanteId other = (ConfUsuarioCatDiscriminanteId) obj;
        if (this.usuarioId != other.usuarioId && (this.usuarioId == null || !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

}
