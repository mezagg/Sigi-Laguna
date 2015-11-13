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
@Table(name = "ConfUsuarioCatDiscriminante")
public class ConfUsuarioCatDiscriminante implements Serializable {

    private static final long serialVersionUID = 1L;

    private ConfUsuarioCatDiscriminanteId id;
    private Usuario usuario;
    private CatDiscriminante catDiscriminante;

    public ConfUsuarioCatDiscriminante() {
    }

    public ConfUsuarioCatDiscriminante(ConfUsuarioCatDiscriminanteId id, Usuario usuario, CatDiscriminante catDiscriminante) {
        this.id = id;
        this.usuario = usuario;
        this.catDiscriminante = catDiscriminante;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "usuarioId", column = @Column(name = "Usuario_id", nullable = false, precision = 18, scale = 0)),
        @AttributeOverride(name = "catDiscriminanteId", column = @Column(name = "catDiscriminante_id", nullable = false, precision = 18, scale = 0))})
    public ConfUsuarioCatDiscriminanteId getId() {
        return id;
    }

    public void setId(ConfUsuarioCatDiscriminanteId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuario_id", nullable = false, insertable = false, updatable = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catDiscriminante_id", nullable = false, insertable = false, updatable = false)
    public CatDiscriminante getCatDiscriminante() {
        return catDiscriminante;
    }

    public void setCatDiscriminante(CatDiscriminante catDiscriminante) {
        this.catDiscriminante = catDiscriminante;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ConfUsuarioCatDiscriminante other = (ConfUsuarioCatDiscriminante) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
