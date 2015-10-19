package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author pamela
 */
@Entity
@Table(name = "ConfActividadDocumentoRol")
public class ConfActividadDocumentoRol implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long confActividadDocumentoRolId;
    private String descripcion;
    private Valor tipoActividad;
    private Valor tipoDocumento;
    private Boolean muestraEnCombo;
    private Forma forma;
    private Boolean usaEditor;
    private Rol rol;
    private Boolean activo;

    public ConfActividadDocumentoRol() {

    }

    public ConfActividadDocumentoRol(Long confActividadDocumentoRolId) {
        this.confActividadDocumentoRolId = confActividadDocumentoRolId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfActividadDocumentoRol_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getConfActividadDocumentoRolId() {
        return confActividadDocumentoRolId;
    }

    public void setConfActividadDocumentoRolId(Long confActividadDocumentoRolId) {
        this.confActividadDocumentoRolId = confActividadDocumentoRolId;
    }

    @Column(name = "Descripcion", length = 100)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoActividad_val", nullable = false)
    public Valor getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(Valor tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoDocumento_val", nullable = true)
    public Valor getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Valor tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Column(name = "bUsaEditor", precision = 1, scale = 0)
    public Boolean getUsaEditor() {
        return usaEditor;
    }

    public void setUsaEditor(Boolean usaEditor) {
        this.usaEditor = usaEditor;
    }

    @Column(name = "bmuestracombo", precision = 1, scale = 0)
    public Boolean getMuestraEnCombo() {
        return muestraEnCombo;
    }

    public void setMuestraEnCombo(Boolean muestraEnCombo) {
        this.muestraEnCombo = muestraEnCombo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Forma_id", nullable = false)
    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Rol_id", nullable = false)
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Column(name = "bActivo", precision = 1, scale = 0)
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean bActivo) {
        this.activo = bActivo;
    }
}
