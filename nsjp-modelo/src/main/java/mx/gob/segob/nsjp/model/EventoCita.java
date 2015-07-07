package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
 * EventoCita entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "EventoCita")
public class EventoCita implements java.io.Serializable {

    // Fields

	private static final long serialVersionUID = -5637524031464908276L;
	private Long eventoCitaId;
    private AgendaFuncionario agendaFuncionario;
    private Valor estatus;
    private String nombreEvento;
    private Date fechaInicioEvento;
    private Date fechaFinEvento;
    private String descripcionEvento;
    private String direccion;
    private Date fechaNotificacion;
    private Tarea tarea;
    private Valor tipoEvento;
    
    private Boolean requiereReemplazo;
    
    private Boolean tieneAlarma;

    // Constructors

    /** default constructor */
    public EventoCita() {
    }

    /** minimal constructor */
    public EventoCita(Long eventoCitaId, AgendaFuncionario agendaFuncionario,
            String cnombreEvento, Date dfechaInicioEvento) {
        this.eventoCitaId = eventoCitaId;
        this.agendaFuncionario = agendaFuncionario;
        this.nombreEvento = cnombreEvento;
        this.fechaInicioEvento = dfechaInicioEvento;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventoCita_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getEventoCitaId() {
        return this.eventoCitaId;
    }

    public void setEventoCitaId(Long eventoCitaId) {
        this.eventoCitaId = eventoCitaId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AgendaFuncionario_id", nullable = false)
    public AgendaFuncionario getAgendaFuncionario() {
        return this.agendaFuncionario;
    }

    public void setAgendaFuncionario(AgendaFuncionario agendaFuncionario) {
        this.agendaFuncionario = agendaFuncionario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EstatusEventoCita_val")
    public Valor getEstatus() {
        return this.estatus;
    }

    public void setEstatus(Valor valor) {
        this.estatus = valor;
    }

    @Column(name = "cNombreEvento", nullable = false, length = 60)
    public String getNombreEvento() {
        return this.nombreEvento;
    }

    public void setNombreEvento(String cnombreEvento) {
        this.nombreEvento = cnombreEvento;
    }

    @Column(name = "dFechaInicioEvento", nullable = false, length = 23)
    public Date getFechaInicioEvento() {
        return this.fechaInicioEvento;
    }

    public void setFechaInicioEvento(Date dfechaInicioEvento) {
        this.fechaInicioEvento = dfechaInicioEvento;
    }

    @Column(name = "dFechaFinEvento", length = 23)
    public Date getFechaFinEvento() {
        return this.fechaFinEvento;
    }

    public void setFechaFinEvento(Date dfechaFinEvento) {
        this.fechaFinEvento = dfechaFinEvento;
    }

    @Column(name = "cDescripcionEvento", length = 150)
    public String getDescripcionEvento() {
        return this.descripcionEvento;
    }

    public void setDescripcionEvento(String cdescripcionEvento) {
        this.descripcionEvento = cdescripcionEvento;
    }

    @Column(name = "cDireccion", length = 300)
    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String cdireccion) {
        this.direccion = cdireccion;
    }

    @Column(name = "dFechaNotificacion", length = 23)
    public Date getFechaNotificacion() {
        return this.fechaNotificacion;
    }

    public void setFechaNotificacion(Date dfechaNotificacion) {
        this.fechaNotificacion = dfechaNotificacion;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eventoCita")
    public Tarea getTarea() {
        return this.tarea;
    }

    public void setTarea(Tarea tareas) {
        this.tarea = tareas;
    }

    /**
     * M�todo de acceso al campo tipoEvento.
     * @return El valor del campo tipoEvento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoEvento_val")
    public Valor getTipoEvento() {
        return tipoEvento;
    }

    /**
     * Asigna el valor al campo tipoEvento.
     * @param tipoEvento el valor tipoEvento a asignar
     */
    public void setTipoEvento(Valor tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    /**
     * M�todo de acceso al campo requiereReemplazo.
     * @return El valor del campo requiereReemplazo
     */
    @Column(name = "bRequiereReemplazo", precision = 1, scale = 0)
    public Boolean getRequiereReemplazo() {
        return requiereReemplazo;
    }

    /**
     * Asigna el valor al campo requiereReemplazo.
     * @param requiereReemplazo el valor requiereReemplazo a asignar
     */
    public void setRequiereReemplazo(Boolean requiereReemplazo) {
        this.requiereReemplazo = requiereReemplazo;
    }
    
    /**
     * M�todo de acceso al campo tieneAlarma.
     * @return El valor del campo tieneAlarma
     */
    @Column(name = "bTieneAlarma", precision = 1, scale = 0)
    public Boolean getTieneAlarma() {
        return tieneAlarma;
    }

    /**
     * Asigna el valor al campo tieneAlarma.
     * @param tieneAlarma el valor tieneAlarma a asignar
     */
    public void setTieneAlarma(Boolean tieneAlarma) {
        this.tieneAlarma = tieneAlarma;
    }

    public String toString(){
    	StringBuffer strB = new StringBuffer();
    	strB.append("[")
    	    .append(this.eventoCitaId+": ")
    		.append(this.nombreEvento+" ");
    	if(this.tipoEvento!=null)
    	strB.append(this.tipoEvento.getValor()+" ");
    	strB.append(this.fechaInicioEvento+" - ");
    	if(this.fechaFinEvento!=null)
    		strB.append(this.fechaFinEvento+"]");
    	
    	return strB.toString();
    }
    
}