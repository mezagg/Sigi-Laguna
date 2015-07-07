package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Vehiculo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Vehiculo" )
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Vehiculo_id")
public class Vehiculo extends Objeto {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3045701205813680292L;
	private Valor valorByPaisOrigenVal;
	private Valor valorByColorVal;
	private Valor valorByMarcaVal;
	private Valor valorBySubmarcaVal;
	private Valor valorByTipoVehiculo;
	private Short modelo;
	private String placa;
	private String noSerie;
	private String noMotor;
	private String nrfv;
	private Boolean esBlindado;
	private Short noPuertas;
	private Short noCilindros;
	private String propietario;
	private Boolean EsNumMotorAlterado;
	private Boolean EsNumSerieAlterado;
	private Causa causa;
	private Causa causaRecuperado;
	private Causa causaRecuperadoOtros;
	private Date fechaRecuperado;
	private String lugarRecuperacion;
	private String autoridadRecupero;
	private Date fechaEntrega;



	// Constructors

	/** default constructor */
	public Vehiculo() {
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PaisOrigen_val")
	public Valor getValorByPaisOrigenVal() {
		return this.valorByPaisOrigenVal;
	}

	public void setValorByPaisOrigenVal(Valor valorByPaisOrigenVal) {
		this.valorByPaisOrigenVal = valorByPaisOrigenVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Color_val")
	public Valor getValorByColorVal() {
		return this.valorByColorVal;
	}

	public void setValorByColorVal(Valor valorByColorVal) {
		this.valorByColorVal = valorByColorVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Marca_val")
	public Valor getValorByMarcaVal() {
		return this.valorByMarcaVal;
	}

	public void setValorByMarcaVal(Valor valorByMarcaVal) {
		this.valorByMarcaVal = valorByMarcaVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Submarca_val")
	public Valor getValorBySubmarcaVal() {
		return this.valorBySubmarcaVal;
	}

	public void setValorBySubmarcaVal(Valor valorBySubmarcaVal) {
		this.valorBySubmarcaVal = valorBySubmarcaVal;
	}


	@Column(name = "iModelo", precision = 4, scale = 0)
	public Short getModelo() {
		return this.modelo;
	}

	public void setModelo(Short imodelo) {
		this.modelo = imodelo;
	}

	@Column(name = "cPlaca", length = 10)
	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String cplaca) {
		this.placa = cplaca;
	}

	@Column(name = "cNserie", length = 20)
	public String getNoSerie() {
		return this.noSerie;
	}

	public void setNoSerie(String cnserie) {
		this.noSerie = cnserie;
	}

	@Column(name = "cNmotor", length = 20)
	public String getNoMotor() {
		return this.noMotor;
	}

	public void setNoMotor(String cnmotor) {
		this.noMotor = cnmotor;
	}

	@Column(name = "cNRFV", length = 20)
	public String getNrfv() {
		return this.nrfv;
	}

	public void setNrfv(String cnrfv) {
		this.nrfv = cnrfv;
	}

	@Column(name = "bEsBlindado", precision = 1, scale = 0)
	public Boolean getEsBlindado() {
		return this.esBlindado;
	}

	public void setEsBlindado(Boolean besBlindado) {
		this.esBlindado = besBlindado;
	}

	@Column(name = "iNpuertas", precision = 4, scale = 0)
	public Short getNoPuertas() {
		return this.noPuertas;
	}

	public void setNoPuertas(Short inpuertas) {
		this.noPuertas = inpuertas;
	}

	@Column(name = "iNcilindros", precision = 4, scale = 0)
	public Short getNoCilindros() {
		return this.noCilindros;
	}

	public void setNoCilindros(Short incilindros) {
		this.noCilindros = incilindros;
	}

    /**
     * Método de acceso al campo valorByTipoVehiculo.
     * @return El valor del campo valorByTipoVehiculo
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoVehiculo_val")	
    public Valor getValorByTipoVehiculo() {
        return valorByTipoVehiculo;
    }

    /**
     * Asigna el valor al campo valorByTipoVehiculo.
     * @param valorByTipoVehiculo el valor valorByTipoVehiculo a asignar
     */
    public void setValorByTipoVehiculo(Valor valorByTipoVehiculo) {
        this.valorByTipoVehiculo = valorByTipoVehiculo;
    }

	/**
	 * Método de acceso al campo propietario.
	 * @return El valor del campo propietario
	 */
	@Column(name = "cPropietario", length = 100)
	public String getPropietario() {
		return propietario;
	}

	/**
	 * Asigna el valor al campo propietario.
	 * @param propietario el valor propietario a asignar
	 */	
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public void setEsNumMotorAlterado(Boolean esNumMotorAlterado) {
		EsNumMotorAlterado = esNumMotorAlterado;
	}


	@Column(name = "bNumMotorAlterado", precision = 1, scale = 0)
	public Boolean getEsNumMotorAlterado() {
		return EsNumMotorAlterado;
	}



	public void setEsNumSerieAlterado(Boolean esNumSerieAlterado) {
		EsNumSerieAlterado = esNumSerieAlterado;
	}


	@Column(name = "bNumSerieAlterado", precision = 1, scale = 0)
	public Boolean getEsNumSerieAlterado() {
		return EsNumSerieAlterado;
	}

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Causa_id")
	public Causa getCausa() {
		return causa;
	}

	public void setCausa(Causa causa) {
		this.causa = causa;
	}

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Causa_id_recuperado")
	public Causa getCausaRecuperado() {
		return causaRecuperado;
	}

	public void setCausaRecuperado(Causa causaRecuperado) {
		this.causaRecuperado = causaRecuperado;
	}

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Causa_id_recuperado_otros")
	public Causa getCausaRecuperadoOtros() {
		return causaRecuperadoOtros;
	}

	public void setCausaRecuperadoOtros(Causa causaRecuperadoOtros) {
		this.causaRecuperadoOtros = causaRecuperadoOtros;
	}
	
	@Column(name = "Fecha_recuperado", length = 23)
	public Date getFechaRecuperado() {
		return fechaRecuperado;
	}

	public void setFechaRecuperado(Date fechaRecuperado) {
		this.fechaRecuperado = fechaRecuperado;
	}

	@Column(name = "Lugar_recuperacion", length = 50)
	public String getLugarRecuperacion() {
		return lugarRecuperacion;
	}

	public void setLugarRecuperacion(String lugarRecuperacion) {
		this.lugarRecuperacion = lugarRecuperacion;
	}

	@Column(name = "Autoridad_recupero", length = 50)
	public String getAutoridadRecupero() {
		return autoridadRecupero;
	}

	public void setAutoridadRecupero(String autoridadRecupero) {
		this.autoridadRecupero = autoridadRecupero;
	}

	@Column(name = "Fecha_entrega", length = 23)
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	
}