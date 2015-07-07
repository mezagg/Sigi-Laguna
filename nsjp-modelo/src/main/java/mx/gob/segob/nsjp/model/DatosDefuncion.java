package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Entidad DatosDefuncion
 * 
 */
@Entity
@Table(name = "DatosDefuncion")
@Inheritance(strategy = InheritanceType.JOINED)
public class DatosDefuncion implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2457527812682024878L;
	
	private Long datosDefuncionId;
	
	private Involucrado involucrado;
	
	private String folioDefuncion;
    private Date fechaAveriguacion;
    private Date fechaDefuncion;
    private Valor tipoDefuncion;
    private Valor certificadaPor;
    private Valor sitioDefuncion;
    private Valor sitioLesion;
    private Valor fueEnTrabajo;
    private String agenteExterno;
    private Valor tipoEventoDefuncion;
    private Valor tipoVictima;
    private Valor tipoArma;
  //Causas de la defuncion
  	private String causaA;
  	private String duracionA;
  	private String causaB;
  	private String duracionB;
  	private String causaC;
  	private String duracionC;
  	private String causaD;
  	private String duracionD;
  	private String edoPatologico;
  	private String duracionPatologico;
  	private Valor condicionEmbarazo;
  	private Valor periodoPosparto;
  	
  	private Long edadDefuncion;
  	private Valor edadUnidadDefuncion;
  	private Valor condicionActividad;
  	
	public DatosDefuncion() {
		super();
	}

	public DatosDefuncion(Long datosDefuncionId) {
		super();
		this.datosDefuncionId = datosDefuncionId;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DatosDefuncion_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getDatosDefuncionId() {
		return datosDefuncionId;
	}

	public void setDatosDefuncionId(Long datosDefuncionId) {
		this.datosDefuncionId = datosDefuncionId;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Involucrado_id", nullable = false)
    public Involucrado getInvolucrado() {
        return this.involucrado;
    }

    public void setInvolucrado(Involucrado involucrado) {
        this.involucrado = involucrado;
    }

	@Column(name = "cFolioDefuncion", length = 10)
	public String getFolioDefuncion() {
		return folioDefuncion;
	}

	public void setFolioDefuncion(String folioDefuncion) {
		this.folioDefuncion = folioDefuncion;
	}
	
	@Column(name = "dFechaAveriguacion", length = 23)
	public Date getFechaAveriguacion() {
		return fechaAveriguacion;
	}

	public void setFechaAveriguacion(Date fechaAveriguacion) {
		this.fechaAveriguacion = fechaAveriguacion;
	}

	@Column(name = "dFechaDefuncion", length = 23)
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}

	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoDefuncion_val")
	public Valor getTipoDefuncion() {
		return tipoDefuncion;
	}
	
	public void setTipoDefuncion(Valor tipoDefuncion) {
		this.tipoDefuncion = tipoDefuncion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CertificadaPor_val")
	public Valor getCertificadaPor() {
		return certificadaPor;
	}
	
	public void setCertificadaPor(Valor certificadaPor) {
		this.certificadaPor = certificadaPor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SitioDefuncion_val")
	public Valor getSitioDefuncion() {
		return sitioDefuncion;
	}

	public void setSitioDefuncion(Valor sitioDefuncion) {
		this.sitioDefuncion = sitioDefuncion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SitioLesion_val")
	public Valor getSitioLesion() {
		return sitioLesion;
	}

	public void setSitioLesion(Valor sitioLesion) {
		this.sitioLesion = sitioLesion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FueEnTrabajo_val")
	public Valor getFueEnTrabajo() {
		return fueEnTrabajo;
	}

	public void setFueEnTrabajo(Valor fueEnTrabajo) {
		this.fueEnTrabajo = fueEnTrabajo;
	}

	@Column(name = "cAgenteExterno", length = 200)
	public String getAgenteExterno() {
		return agenteExterno;
	}

	public void setAgenteExterno(String agenteExterno) {
		this.agenteExterno = agenteExterno;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoEventoDefuncion_val")
	public Valor getTipoEventoDefuncion() {
		return tipoEventoDefuncion;
	}
	
	public void setTipoEventoDefuncion(Valor tipoEventoDefuncion) {
		this.tipoEventoDefuncion = tipoEventoDefuncion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoVictima_val")
	public Valor getTipoVictima() {
		return tipoVictima;
	}

	public void setTipoVictima(Valor tipoVictima) {
		this.tipoVictima = tipoVictima;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoArma_val")
	public Valor getTipoArma() {
		return tipoArma;
	}

	public void setTipoArma(Valor tipoArma) {
		this.tipoArma = tipoArma;
	}

	@Column(name = "cCausaA", length = 225)
	public String getCausaA() {
		return causaA;
	}

	public void setCausaA(String causaA) {
		this.causaA = causaA;
	}

	@Column(name = "cDuracionA", length = 15)
	public String getDuracionA() {
		return duracionA;
	}

	public void setDuracionA(String duracionA) {
		this.duracionA = duracionA;
	}

	@Column(name = "cCausaB", length = 225)
	public String getCausaB() {
		return causaB;
	}

	public void setCausaB(String causaB) {
		this.causaB = causaB;
	}

	@Column(name = "cDuracionB", length = 15)
	public String getDuracionB() {
		return duracionB;
	}

	public void setDuracionB(String duracionB) {
		this.duracionB = duracionB;
	}

	@Column(name = "cCausaC", length = 225)
	public String getCausaC() {
		return causaC;
	}

	public void setCausaC(String causaC) {
		this.causaC = causaC;
	}

	@Column(name = "cDuracionC", length = 15)
	public String getDuracionC() {
		return duracionC;
	}

	public void setDuracionC(String duracionC) {
		this.duracionC = duracionC;
	}

	@Column(name = "cCausaD", length = 225)
	public String getCausaD() {
		return causaD;
	}

	public void setCausaD(String causaD) {
		this.causaD = causaD;
	}

	@Column(name = "cDuracionD", length = 15)
	public String getDuracionD() {
		return duracionD;
	}

	public void setDuracionD(String duracionD) {
		this.duracionD = duracionD;
	}

	@Column(name = "cEdoPatologico", length = 40)
	public String getEdoPatologico() {
		return edoPatologico;
	}

	public void setEdoPatologico(String edoPatologico) {
		this.edoPatologico = edoPatologico;
	}

	@Column(name = "cDuracionPatologico", length = 15)
	public String getDuracionPatologico() {
		return duracionPatologico;
	}

	public void setDuracionPatologico(String duracionPatologico) {
		this.duracionPatologico = duracionPatologico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CondicionEmbarazo_val")
	public Valor getCondicionEmbarazo() {
		return condicionEmbarazo;
	}

	public void setCondicionEmbarazo(Valor condicionEmbarazo) {
		this.condicionEmbarazo = condicionEmbarazo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PeriodoPosparto_val")
	public Valor getPeriodoPosparto() {
		return periodoPosparto;
	}

	public void setPeriodoPosparto(Valor periodoPosparto) {
		this.periodoPosparto = periodoPosparto;
	}

	@Column(name = "iEdadDefuncion", precision = 3, scale = 0)
	public Long getEdadDefuncion() {
		return edadDefuncion;
	}

	public void setEdadDefuncion(Long edadDefuncion) {
		this.edadDefuncion = edadDefuncion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EdadUnidadDefuncion_val")
	public Valor getEdadUnidadDefuncion() {
		return edadUnidadDefuncion;
	}

	public void setEdadUnidadDefuncion(Valor edadUnidadDefuncion) {
		this.edadUnidadDefuncion = edadUnidadDefuncion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CondicionActividad_val")
	public Valor getCondicionActividad() {
		return condicionActividad;
	}

	public void setCondicionActividad(Valor condicionActividad) {
		this.condicionActividad = condicionActividad;
	}
  	
  	
  	
}