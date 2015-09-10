package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PermisoSolicitud")
public class PermisoSolicitud implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7873548746551218225L;
    private PermisoSolicitudId id;
    private Solicitud solicitud;
    private Funcionario funcionario;
    private Date fechaLimite;
    private Boolean esEscritura;
    private String creadoPor;
//    private Date fechaCreacion;
    private String modificadoPor;
    private Date fechaModificacion;

    public PermisoSolicitud() {
    }

    public PermisoSolicitud(PermisoSolicitudId id, Solicitud solicitud,
            Funcionario funcionario, Date fechaLimite, Boolean esEscritura, String creadoPor, String modificadoPor, Date fechaModificacion) {
        super();
        this.id = id;
        this.solicitud = solicitud;
        this.funcionario = funcionario;
        this.fechaLimite = fechaLimite;
        this.esEscritura = esEscritura;
        this.creadoPor = creadoPor;
//        this.fechaCreacion = fechaCreacion;
        this.modificadoPor = modificadoPor;
        this.fechaModificacion = fechaModificacion;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "solicitudId", column = @Column(name = "Solicitud_id", nullable = false, precision = 18, scale = 0)),
        @AttributeOverride(name = "iclaveFuncionario", column = @Column(name = "iClaveFuncionario", nullable = false, precision = 18, scale = 0))
    })
    public PermisoSolicitudId getId() {
        return id;
    }

    public void setId(PermisoSolicitudId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Solicitud_id", nullable = false, insertable = false, updatable = false)
    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iClaveFuncionario", nullable = false, insertable = false, updatable = false)
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Column(name = "dFechaLimite", nullable = false, length = 23)
    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @Column(name = "bEsEscritura", precision = 1, scale = 0)
    public Boolean getEsEscritura() {
        return esEscritura;
    }

    public void setEsEscritura(Boolean esEscritura) {
        this.esEscritura = esEscritura;
    }

    @Column(name = "creadoPor", length = 20)
    public String getCreadoPor() {
        return this.creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

//    @Column(name = "fechaCreacion", nullable = true, length = 23)
//    public Date getFechaCreacion() {
//        return fechaCreacion;
//    }
//
//    public void setFechaCreacion(Date fechaCreacion) {
//        this.fechaCreacion = fechaCreacion;
//    }

    @Column(name = "modificadoPor", length = 20)
    public String getModificadoPor() {
        return this.modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    @Column(name = "fechaModificacion", nullable = true, length = 23)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
