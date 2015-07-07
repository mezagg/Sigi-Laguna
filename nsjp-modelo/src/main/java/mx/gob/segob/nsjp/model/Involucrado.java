package mx.gob.segob.nsjp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Involucrado entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Involucrado")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Involucrado_id")
public class Involucrado extends Persona {

    // Fields

    private Valor situacionJuridica;
    private Funcionario funcionario; // FIXME eliminar
    private String motivoComparecencia;
    private Boolean esDetenido;
    private Boolean esMismoDomicilio;
    private Boolean esProtegido;
    private Boolean esAutoridad;
    /**
     * Indica la relaci&oacute;n entre el denunciante y la victima:<br>
     * 0 = son elementos diferentes<br>
     * 1 = son el mismo<br>
     */
    private Short condicion;
    private String tipoPersona;
    private String desconocido;

    private Valor idioma;
    private Valor religion;
    private Valor estadoCivil;
    private Valor escolaridad;
    
    private Set<Detencion> detencions = new HashSet<Detencion>(0);
    private Set<AliasInvolucrado> aliasInvolucrados = new HashSet<AliasInvolucrado>(
            0);
    private Set<InvolucradoOcupacion> involucradoOcupacions = new HashSet<InvolucradoOcupacion>(
            0);
    private Set<InvolucradoNacionalidad> involucradoNacionalidads = new HashSet<InvolucradoNacionalidad>(
            0);

    private Set<DelitoPersona> victimaDeDelitos = new HashSet<DelitoPersona>(0);
    private Set<DelitoPersona> delitosCometidos = new HashSet<DelitoPersona>(0);

    private Set<InvolucradoAudiencia> invlucradoAudiencias = new HashSet<InvolucradoAudiencia>(
            0);
   
    
    private Set<MediaFiliacion> mediaFiliacions = new HashSet<MediaFiliacion>(0);
    /**
     * Relaci&oacute;n con la instituci&oacute;n que lo presenta a la audiencia.
     */
    private ConfInstitucion institucionPresenta;

    private DatosDefuncion datosDefuncion = new DatosDefuncion();
    
    /**
     * Relacion con Media <- MedidaCautelar y Medida <- MedidaAlterna
     */
    private Set<Medida> medidas = new HashSet<Medida>(0);
    
    private Boolean esPrincipal;
    
    
    private CentroDetencion centroDetencion;
	private Set<Sentencia> sentencias;
    /**
     * Relaci&oacute;n donde se representa como defendido.
     */
    private DefensaInvolucrado defensaInvolucrado;
    
    private Set<DefensaInvolucrado> defensaInvolucradosPg= new HashSet<DefensaInvolucrado>(
            0);

    
    private Set<Notificacion> notificacions = new HashSet<Notificacion>();
    
    private Short tipoEvento;
    private Short subtipoDeEvento;
    
	//Etapa del expediente para DEF
	private CatEtapa etapaInvolucrado;
    
    // Constructors

    /** default constructor */
    public Involucrado() {
    }

    public Involucrado(Long involucradoId) {
        super();
        setElementoId(involucradoId);

    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SituacionJuridica_val", nullable = true)
    public Valor getSituacionJuridica() {
        return this.situacionJuridica;
    }

    public void setSituacionJuridica(Valor valor) {
        this.situacionJuridica = valor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iClaveFuncionario", nullable = true)
    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Column(name = "cMotivoComparecencia", length = 300)
    public String getMotivoComparecencia() {
        return this.motivoComparecencia;
    }

    public void setMotivoComparecencia(String motivoComparecencia) {
        this.motivoComparecencia = motivoComparecencia;
    }


    @Column(name = "bEsDetenido", precision = 1, scale = 0)
    public Boolean getEsDetenido() {
        return this.esDetenido;
    }

    public void setEsDetenido(Boolean esDetenido) {
        this.esDetenido = esDetenido;
    }

    @Column(name = "iCondicion", precision = 4, scale = 0)
    public Short getCondicion() {
        return this.condicion;
    }

    public void setCondicion(Short condicion) {
        this.condicion = condicion;
    }

    @Column(name = "cTipoPersona", length = 10)
    public String getTipoPersona() {
        return this.tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    @Column(name = "cDesconocido", length = 30)
    public String getDesconocido() {
        return this.desconocido;
    }

    public void setDesconocido(String desconocido) {
        this.desconocido = desconocido;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public Set<Detencion> getDetencions() {
        return this.detencions;
    }

    public void setDetencions(Set<Detencion> detencions) {
        this.detencions = detencions;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "involucrado")    
    public Set<AliasInvolucrado> getAliasInvolucrados() {
        return this.aliasInvolucrados;
    }

    public void setAliasInvolucrados(Set<AliasInvolucrado> aliasInvolucrados) {
        this.aliasInvolucrados = aliasInvolucrados;
    }

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public Set<InvolucradoOcupacion> getInvolucradoOcupacions() {
        return this.involucradoOcupacions;
    }

    public void setInvolucradoOcupacions(
            Set<InvolucradoOcupacion> involucradoOcupacions) {
        this.involucradoOcupacions = involucradoOcupacions;
    }

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public Set<InvolucradoNacionalidad> getInvolucradoNacionalidads() {
        return this.involucradoNacionalidads;
    }

    public void setInvolucradoNacionalidads(
            Set<InvolucradoNacionalidad> involucradoNacionalidads) {
        this.involucradoNacionalidads = involucradoNacionalidads;
    }

    /**
     * M&eacute;todo de acceso al campo idioma.
     * 
     * @return El valor del campo idioma
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Idioma_val")
    public Valor getIdioma() {
        return idioma;
    }

    /**
     * Asigna el valor al campo idioma.
     * 
     * @param idioma
     *            el valor idioma a asignar
     */
    public void setIdioma(Valor idioma) {
        this.idioma = idioma;
    }

    /**
     * M&eacute;todo de acceso al campo religion.
     * 
     * @return El valor del campo religion
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Religion_val")
    public Valor getReligion() {
        return religion;
    }

    /**
     * Asigna el valor al campo religion.
     * 
     * @param religion
     *            el valor religion a asignar
     */
    public void setReligion(Valor religion) {
        this.religion = religion;
    }

    /**
     * M&eacute;todo de acceso al campo estadoCivil.
     * 
     * @return El valor del campo estadoCivil
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EstadoCivil_val")
    public Valor getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Asigna el valor al campo estadoCivil.
     * 
     * @param estadoCivil
     *            el valor estadoCivil a asignar
     */
    public void setEstadoCivil(Valor estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * M&eacute;todo de acceso al campo escolaridad.
     * 
     * @return El valor del campo escolaridad
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Escolaridad_val")
    public Valor getEscolaridad() {
        return escolaridad;
    }

    /**
     * Asigna el valor al campo escolaridad.
     * 
     * @param escolaridad
     *            el valor escolaridad a asignar
     */
    public void setEscolaridad(Valor escolaridad) {
        this.escolaridad = escolaridad;
    }

    /**
     * M&eacute;todo de acceso al campo victimaDeDelitos.
     * 
     * @return El valor del campo victimaDeDelitos
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "victima")
    public Set<DelitoPersona> getVictimaDeDelitos() {
        return victimaDeDelitos;
    }

    /**
     * Asigna el valor al campo victimaDeDelitos.
     * 
     * @param victimaDeDelitos
     *            el valor victimaDeDelitos a asignar
     */
    public void setVictimaDeDelitos(Set<DelitoPersona> victimaDeDelitos) {
        this.victimaDeDelitos = victimaDeDelitos;
    }

    /**
     * M&eacute;todo de acceso al campo delitosCometidos.
     * 
     * @return El valor del campo delitosCometidos
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "probableResponsable")
    public Set<DelitoPersona> getDelitosCometidos() {
        return delitosCometidos;
    }

    /**
     * Asigna el valor al campo delitosCometidos.
     * 
     * @param delitosCometidos
     *            el valor delitosCometidos a asignar
     */
    public void setDelitosCometidos(Set<DelitoPersona> delitosCometidos) {
        this.delitosCometidos = delitosCometidos;
    }

    //INICIA MODULO DE PJ
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public Set<InvolucradoAudiencia> getInvlucradoAudiencias() {
        return invlucradoAudiencias;
    }

    public void setInvlucradoAudiencias(
            Set<InvolucradoAudiencia> invlucradoAudiencias) {
        this.invlucradoAudiencias = invlucradoAudiencias;
    }
	//FINALIZA MODULO DE PJ

    /**
     * M&eacute;todo de acceso al campo institucionPresenta.
     * 
     * @return El valor del campo institucionPresenta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ConfInstitucion_id")
    public ConfInstitucion getInstitucionPresenta() {
        return institucionPresenta;
    }

    /**
     * Asigna el valor al campo institucionPresenta.
     * 
     * @param institucionPresenta
     *            el valor institucionPresenta a asignar
     */
    public void setInstitucionPresenta(ConfInstitucion institucionPresenta) {
        this.institucionPresenta = institucionPresenta;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public Set<MediaFiliacion> getMediaFiliacions() {
        return this.mediaFiliacions;
    }

    public void setMediaFiliacions(Set<MediaFiliacion> mediaFiliacions) {
        this.mediaFiliacions = mediaFiliacions;
    }

   
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
	public DatosDefuncion getDatosDefuncion() {
		return datosDefuncion;
	}

	public void setDatosDefuncion(DatosDefuncion datosDefuncion) {
		this.datosDefuncion = datosDefuncion;
	}

	/**
	 * M&eacute;todo de acceso al campo medidas.
	 * @return El valor del campo medidas
	 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
	public Set<Medida> getMedidas() {
		return this.medidas;
	}

	/**
	 * Asigna el valor al campo medidas.
	 * @param medidas el valor medidas a asignar
	 */
	public void setMedidas(Set<Medida> medidas) {
		this.medidas = medidas;
	}
	
    @Column(name = "bEsPrincipal", precision = 1, scale = 0)
    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }	
    
    /**
     * M&eacute;todo de acceso al campo sentencia.
     * @return El valor del campo sentencia
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public Set<Sentencia> getSentencias() {
    	return sentencias;
    }
    
    /**
     * Asigna el valor al campo sentencia.
     * @param sentencia el valor sentencia a asignar
     */
    public void setSentencias(Set<Sentencia> sentencias) {
    	this.sentencias = sentencias;
    }

	//INICIA MODULO DE RS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CentroDetencion_id")
    public CentroDetencion getCentroDetencion() {
        return this.centroDetencion;
    }

    public void setCentroDetencion(CentroDetencion centroDetencion) {
        this.centroDetencion = centroDetencion;
    }
	//FINALIZA MODULO DE RS

    /**
     * M&eacute;todo de acceso al campo defensaInvolucrado.
     * @return El valor del campo defensaInvolucrado
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
    public DefensaInvolucrado getDefensaInvolucrado() {
        return defensaInvolucrado;
    }

    /**
     * Asigna el valor al campo defensaInvolucrado.
     * @param defensaInvolucrado el valor defensaInvolucrado a asignar
     */
    public void setDefensaInvolucrado(DefensaInvolucrado defensaInvolucrado) {
        this.defensaInvolucrado = defensaInvolucrado;
    }

	/**
	 * @return the notificacions
	 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucrado")
	public Set<Notificacion> getNotificacions() {
		return notificacions;
	}

	/**
	 * @param notificacions the notificacions to set
	 */
	public void setNotificacions(Set<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

    /**
     * M&eacute;todo de acceso al campo defensaInvolucradosPg.
     * @return El valor del campo defensaInvolucradosPg
     */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "involucradoPg")
    public Set<DefensaInvolucrado> getDefensaInvolucradosPg() {
        return defensaInvolucradosPg;
    }

    /**
     * Asigna el valor al campo defensaInvolucradosPg.
     * @param defensaInvolucradosPg el valor defensaInvolucradosPg a asignar
     */
    public void setDefensaInvolucradosPg(
            Set<DefensaInvolucrado> defensaInvolucradosPg) {
        this.defensaInvolucradosPg = defensaInvolucradosPg;
    }

	/**
	 * M&eacute;todo de acceso al campo esMismoDomicilio.
	 * Permite saber si el Domicilio de in Involucrado es el mismo al domicilio 
	 * de una notificacion
	 * @return El valor del campo esMismoDomicilio
	 */
    @Column(name = "bEsMismoDomicilio", precision = 1, scale = 0)
	public Boolean getEsMismoDomicilio() {
		return esMismoDomicilio;
	}

	/**
	 * Asigna el valor al campo esMismoDomicilio.
	 * @param esMismoDomicilio el valor esMismoDomicilio a asignar
	 */
	public void setEsMismoDomicilio(Boolean esMismoDomicilio) {
		this.esMismoDomicilio = esMismoDomicilio;
	}

	/**
	 * Permite indicar si se trata de un testigo protegido
	 * @return esProtegido
	 */
    @Column(name = "bEsProtegido", precision = 1, scale = 0)
	public Boolean getEsProtegido() {
		return esProtegido;
	}

	public void setEsProtegido(Boolean esProtegido) {
		this.esProtegido = esProtegido;
	}

	/**
	 * @return the tipoEvento
	 */
	@Column(name = "iTipoEvento", precision = 4, scale = 0)
	public Short getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(Short tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * @return the subtipoDeEvento
	 */
	@Column(name = "iSubtipoDeEvento", precision = 4, scale = 0)
	public Short getSubtipoDeEvento() {
		return subtipoDeEvento;
	}

	/**
	 * @param subtipoDeEvento the subtipoDeEvento to set
	 */
	public void setSubtipoDeEvento(Short subtipoDeEvento) {
		this.subtipoDeEvento = subtipoDeEvento;
	}

	public void setEsAutoridad(Boolean esAutoridad) {
		this.esAutoridad = esAutoridad;
	}
	@Column(name = "bEsAutoridad", precision = 1, scale = 0)
	public Boolean getEsAutoridad() {
		return esAutoridad;
	}

	/**
     * Etapa del expediente para DEF
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CatEtapa_id", nullable = true)
	public CatEtapa getEtapaInvolucrado() {
		return etapaInvolucrado;
	}

    /**
     * Etapa del expediente para DEF
     * 
	 * @param etapaInvolucrado
	 */
	public void setEtapaInvolucrado(CatEtapa etapaInvolucrado) {
		this.etapaInvolucrado = etapaInvolucrado;
	}    
}
