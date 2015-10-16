package mx.gob.segob.nsjp.dto;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;

/**
 *
 * @author pamela
 */
public class ConfActividadDocumentoRolDTO extends GenericDTO {

    private static final long serialVersionUID = 1L;
    private Long confActividadDocumentoRolId;
    private String descripcion;
    private Long tipoActividadId;
    private Long tipoDocumentoId;
    private Boolean muestraEnCombo;
    private FormaDTO forma;
    private Boolean usaEditor;
    private Long rolId;
    private Boolean activo;
    private String nombreActividad;
    private String nombreDocumento;

    public ConfActividadDocumentoRolDTO() {
    }

    public ConfActividadDocumentoRolDTO(Long confActividadDocumentoRolId) {
        this.confActividadDocumentoRolId = confActividadDocumentoRolId;
    }

    public Long getConfActividadDocumentoRolId() {
        return confActividadDocumentoRolId;
    }

    public void setConfActividadDocumentoRolId(Long confActividadDocumentoRolId) {
        this.confActividadDocumentoRolId = confActividadDocumentoRolId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTipoActividadId() {
        return tipoActividadId;
    }

    public void setTipoActividadId(Long tipoActividadId) {
        this.tipoActividadId = tipoActividadId;
    }

    public Long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Long tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public Boolean getMuestraEnCombo() {
        return muestraEnCombo;
    }

    public void setMuestraEnCombo(Boolean muestraEnCombo) {
        this.muestraEnCombo = muestraEnCombo;
    }

    public FormaDTO getForma() {
        return forma;
    }

    public void setForma(FormaDTO forma) {
        this.forma = forma;
    }

    public Boolean getUsaEditor() {
        return usaEditor;
    }

    public void setUsaEditor(Boolean usaEditor) {
        this.usaEditor = usaEditor;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }
}
