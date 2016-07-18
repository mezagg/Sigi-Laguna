package mx.gob.segob.nsjp.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import mx.gob.segob.nsjp.model.ssp.InformePolicialHomologado;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Funcionario entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Funcionario")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="valor")
@Inheritance(strategy = InheritanceType.JOINED)
public class Funcionario implements java.io.Serializable {

	// Fields

	private Long claveFuncionario;
	private Funcionario funcionarioJefe;
	private Usuario usuario;
	private String nombreFuncionario;
	private String apellidoPaternoFuncionario;
	private String apellidoMaternoFuncionario;
	private String sexo;
	private String rfc;
	private String curp;
	private ArchivoDigital archivoDigital;
	private java.util.Date fechaNacimiento;
	private java.util.Date fechaIngreso;
	private String email;
	private String cedula;
	private String numeroEmpleado;
	private Set<Involucrado> involucrados = new HashSet<Involucrado>(0);
	private Set<Funcionario> funcionarios = new HashSet<Funcionario>(0);
	private Set<Detencion> detencionsForFuncionarioDetiene = new HashSet<Detencion>(
			0);
	private Set<Detencion> detencionsForFuncionarioTraslada = new HashSet<Detencion>(
			0);
	private Set<Actividad> actividads = new HashSet<Actividad>(0);

	private Set<Solicitud> solicitudesAsignadas = new HashSet<Solicitud>(0);

	private JerarquiaOrganizacional area;
	private Valor especialidad;
	private Valor tipoEspecialidad;
	private Valor puesto;
	private Double cargaTrabajo = new Double(0);
	private Boolean esPar;
	private Set<MedioDeContacto> medioDeContactos = new HashSet<MedioDeContacto>(
			0);
	private Set<FuncionarioAudiencia> funcionarioAudiencias = new HashSet<FuncionarioAudiencia>(
			0);
	
	private List<Documento> documentos;
	private AgendaFuncionario agenda;

	private Set<Eslabon> eslabonesRecibidos = new HashSet<Eslabon>(0);
	private Set<Eslabon> eslabonesEntregados = new HashSet<Eslabon>(0);
	
	private Set<InformePolicialHomologado> iphsDestinados = new HashSet<InformePolicialHomologado>(
			0);

	private Set<InformePolicialHomologado> iphsElaborados = new HashSet<InformePolicialHomologado>(
			0);
	private Set<Medida> medidas = new HashSet<Medida>(0);

	private Set<Notificacion> notificacions = new HashSet<Notificacion>();
	private Set<PermisoExpediente> permisoExpedientes = new HashSet<PermisoExpediente>(
			0);
	
	private Set<Inspeccion> inspecciones = new HashSet<Inspeccion>(0);
	private Set<MultaSancion> multaSanciones = new HashSet<MultaSancion>(0);
	
	// AGENCIAS
	private Set<CatDiscriminante> funcionarioAgencias = new HashSet<CatDiscriminante>(
			0);
	private CatDiscriminante discriminante;
	// UIE
	private CatUIEspecializada unidadIEspecializada;

	private CatAreasNegocio catAreaNegocio;

	private Set<NotaExpediente> notas = new HashSet<NotaExpediente>(0);
	
	private Boolean buscarPorJerarquiasHijas;
	
	private Rol rol;
	
	//Permite cargar activar/desactivar la lista de rolesUsuario
	private Boolean esConsultarRolesDelUsuario = true;

	private EntidadFederativa entidadFederativa;
	private Region region;
	
	// Constructors

	/** default constructor */
	public Funcionario() {
	}
	public Funcionario(Long claveFuncionario,String nombreFuncionario, String apellidoPaternoFuncionario, String apellidoMaternoFuncionario) {
		this.claveFuncionario=claveFuncionario;

		this.nombreFuncionario=nombreFuncionario;
		this.apellidoPaternoFuncionario=apellidoPaternoFuncionario;
		this.apellidoMaternoFuncionario=apellidoMaternoFuncionario;
	}

	
	/**
	 * Constructor para solo regresar un rol en usuarios multirol
	 * @param funcionario
	 * @param rol
	 */
	public Funcionario(
			Funcionario funcionario, Rol rol
			) {
		super();
		this.claveFuncionario = funcionario.getClaveFuncionario();
		this.rol = rol;
		this.nombreFuncionario = funcionario.getNombreFuncionario();
		this.apellidoPaternoFuncionario = funcionario.getApellidoPaternoFuncionario();
		this.apellidoMaternoFuncionario = funcionario.getApellidoMaternoFuncionario();
		this.usuario = funcionario.getUsuario();
		this.area = funcionario.getArea();
	}



	/**
	 * @param claveFuncionario
	 */
	public Funcionario(Long claveFuncionario) {
		super();
		this.claveFuncionario = claveFuncionario;
	}

	/**
	 * Concatena los atributos de nombre completo para regresar una sola cadena.
	 * 
	 * @return nombreFuncionario + apellidoPaternoFuncionario +
	 *         apellidoMaternoFuncionario
	 */
	@Transient
	public String getNombreCompleto() {
		final StringBuffer nom = new StringBuffer();

		if (getNombreFuncionario() != null) {
			nom.append(getNombreFuncionario());
		}

		if (getApellidoPaternoFuncionario() != null) {
			nom.append(" ");
			nom.append(getApellidoPaternoFuncionario());
		}
		if (getApellidoMaternoFuncionario() != null) {
			nom.append(" ");
			nom.append(getApellidoMaternoFuncionario());
		}

		return nom.toString();
	}

	/** minimal constructor */
	public Funcionario(Long claveFuncionario, Funcionario funcionarioJefe) {
		this.claveFuncionario = claveFuncionario;
		this.funcionarioJefe = funcionarioJefe;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iClaveFuncionario", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getClaveFuncionario() {
		return this.claveFuncionario;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ArchivoDigital_id", nullable = true)
	public ArchivoDigital getArchivoDigital() {
		return this.archivoDigital;
	}

	public void setArchivoDigital(ArchivoDigital archivoDigital) {
		this.archivoDigital = archivoDigital;
	}

	public void setClaveFuncionario(Long icaveFuncionario) {
		this.claveFuncionario = icaveFuncionario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iClaveFuncionarioJefe", nullable = true)
	public Funcionario getFuncionarioJefe() {
		return this.funcionarioJefe;
	}

	public void setFuncionarioJefe(Funcionario funcionarioJefe) {
		this.funcionarioJefe = funcionarioJefe;
	}

	@Column(name = "cNombreFuncionario", length = 50)
	public String getNombreFuncionario() {
		return this.nombreFuncionario;
	}

	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}

	@Column(name = "cApellidoPaternoFuncionario", length = 50)
	public String getApellidoPaternoFuncionario() {
		return this.apellidoPaternoFuncionario;
	}

	public void setApellidoPaternoFuncionario(String apellidoPaternoFuncionario) {
		this.apellidoPaternoFuncionario = apellidoPaternoFuncionario;
	}

	@Column(name = "cApellidoMaternoFuncionario", length = 50)
	public String getApellidoMaternoFuncionario() {
		return this.apellidoMaternoFuncionario;
	}

	public void setApellidoMaternoFuncionario(String apellidoMaternoFuncionario) {
		this.apellidoMaternoFuncionario = apellidoMaternoFuncionario;
	}

	@Column(name = "cSexo", length = 1)
	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Column(name = "cRFC", length = 13)
	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@Column(name = "cCURP", length = 18)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Column(name = "dFechaNacimiento", length = 23)
	public java.util.Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(java.util.Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Column(name = "dFechaIngreso", length = 23)
	public java.util.Date getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(java.util.Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@Column(name = "cEmail", length = 60)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "cCedula", length = 50)
	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<Involucrado> getInvolucrados() {
		return this.involucrados;
	}

	public void setInvolucrados(Set<Involucrado> involucrados) {
		this.involucrados = involucrados;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioJefe")
	public Set<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioByFuncionarioDetiene")
	public Set<Detencion> getDetencionsForFuncionarioDetiene() {
		return this.detencionsForFuncionarioDetiene;
	}

	public void setDetencionsForFuncionarioDetiene(
			Set<Detencion> detencionsForFuncionarioDetiene) {
		this.detencionsForFuncionarioDetiene = detencionsForFuncionarioDetiene;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioByFuncionarioTraslada")
	public Set<Detencion> getDetencionsForFuncionarioTraslada() {
		return this.detencionsForFuncionarioTraslada;
	}

	public void setDetencionsForFuncionarioTraslada(
			Set<Detencion> detencionsForFuncionarioTraslada) {
		this.detencionsForFuncionarioTraslada = detencionsForFuncionarioTraslada;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<Actividad> getActividads() {
		return this.actividads;
	}

	public void setActividads(Set<Actividad> actividads) {
		this.actividads = actividads;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "destinatario")
	public Set<Solicitud> getSolicitudesAsignadas() {
		return this.solicitudesAsignadas;
	}

	public void setSolicitudesAsignadas(Set<Solicitud> solicitudes) {
		this.solicitudesAsignadas = solicitudes;
	}

	/**
	 * M�todo de acceso al campo usuario.
	 * 
	 * @return El valor del campo usuario
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Asigna el valor al campo usuario.
	 * 
	 * @param usuario
	 *            el valor usuario a asignar
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Especialidad_val", nullable = true)
	public Valor getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Valor especialidad) {
		this.especialidad = especialidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Puesto_val", nullable = true)
	public Valor getPuesto() {
		return puesto;
	}

	public void setPuesto(Valor puesto) {
		this.puesto = puesto;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JerarquiaOrganizacional_id", nullable = true)
	public JerarquiaOrganizacional getArea() {
		return area;
	}
	
	public void setArea(JerarquiaOrganizacional area) {
		this.area = area;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<MedioDeContacto> getMedioDeContactos() {
		return this.medioDeContactos;
	}

	public void setMedioDeContactos(Set<MedioDeContacto> medioDeContactos) {
		this.medioDeContactos = medioDeContactos;
	}
	
	//INICIA MODULO DE PJ
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<FuncionarioAudiencia> getFuncionarioAudiencias() {
		return this.funcionarioAudiencias;
	}

	public void setFuncionarioAudiencias(
			Set<FuncionarioAudiencia> funcionarioAudiencias) {
		this.funcionarioAudiencias = funcionarioAudiencias;
	}
	//FINALIZA MODULO DE PJ

	/**
	 * M�todo de acceso al campo cargaTrabajo.
	 * 
	 * @return El valor del campo cargaTrabajo
	 */
	@Column(name = "dcCargaTrabajo", precision = 5)
	public Double getCargaTrabajo() {
		return cargaTrabajo;
	}

	/**
	 * Asigna el valor al campo cargaTrabajo.
	 * 
	 * @param cargaTrabajo
	 *            el valor cargaTrabajo a asignar
	 */
	public void setCargaTrabajo(Double cargaTrabajo) {
		this.cargaTrabajo = cargaTrabajo;
	}

	@Transient
	public JerarquiaOrganizacional getInstitucion() {

		JerarquiaOrganizacional institucion = area;
		while (institucion != null
				&& institucion.getJerarquiaOrgResponsable() != null) {
			institucion = institucion.getJerarquiaOrgResponsable();
		}

		return institucion;

	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoEspecialidad_val", nullable = true)
	public Valor getTipoEspecialidad() {
		return tipoEspecialidad;
	}

	public void setTipoEspecialidad(Valor tipoEspecialidad) {
		this.tipoEspecialidad = tipoEspecialidad;
	}

	@OneToMany(mappedBy = "responsableDocumento", fetch = FetchType.LAZY)
	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * M�todo de acceso al campo esPar.
	 * 
	 * @return El valor del campo esPar
	 */
	@Column(name = "bEsPar", precision = 1, scale = 0)
	public Boolean getEsPar() {
		return esPar;
	}

	/**
	 * Asigna el valor al campo esPar.
	 * 
	 * @param esPar
	 *            el valor esPar a asignar
	 */
	public void setEsPar(Boolean esPar) {
		this.esPar = esPar;
	}

	/**
	 * M�todo de acceso al campo agenda.
	 * 
	 * @return El valor del campo agenda
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public AgendaFuncionario getAgenda() {
		return agenda;
	}

	/**
	 * Asigna el valor al campo agenda.
	 * 
	 * @param agenda
	 *            el valor agenda a asignar
	 */
	public void setAgenda(AgendaFuncionario agenda) {
		this.agenda = agenda;
	}

	/**
	 * M�todo de acceso al campo eslabonesRecibidos.
	 * 
	 * @return El valor del campo eslabonesRecibidos
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioRecibe")
	public Set<Eslabon> getEslabonesRecibidos() {
		return eslabonesRecibidos;
	}

	/**
	 * Asigna el valor al campo eslabonesRecibidos.
	 * 
	 * @param eslabonesRecibidos
	 *            el valor eslabonesRecibidos a asignar
	 */
	public void setEslabonesRecibidos(Set<Eslabon> eslabonesRecibidos) {
		this.eslabonesRecibidos = eslabonesRecibidos;
	}

	/**
	 * M�todo de acceso al campo eslabonesEntregados.
	 * 
	 * @return El valor del campo eslabonesEntregados
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioEntrega")
	public Set<Eslabon> getEslabonesEntregados() {
		return eslabonesEntregados;
	}

	/**
	 * Asigna el valor al campo eslabonesEntregados.
	 * 
	 * @param eslabonesEntregados
	 *            el valor eslabonesEntregados a asignar
	 */
	public void setEslabonesEntregados(Set<Eslabon> eslabonesEntregados) {
		this.eslabonesEntregados = eslabonesEntregados;
	}

	/**
	 * M�todo de acceso al campo numeroEmpleado.
	 * 
	 * @return El valor del campo numeroEmpleado
	 */
	@Column(name = "cNumeroEmpleado", length = 20, nullable = true)
	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	/**
	 * Asigna el valor al campo numeroEmpleado.
	 * 
	 * @param numeroEmpleado
	 *            el valor numeroEmpleado a asignar
	 */
	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}
	//INICIA MODULO DE SSP
	/**
	 * M�todo de acceso al campo iphsDestinados.
	 * 
	 * @return El valor del campo iphsDestinados
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioDestinatario")
	public Set<InformePolicialHomologado> getIphsDestinados() {
		return iphsDestinados;
	}

	/**
	 * Asigna el valor al campo iphsDestinados.
	 * 
	 * @param iphsDestinados
	 *            el valor iphsDestinados a asignar
	 */
	public void setIphsDestinados(Set<InformePolicialHomologado> iphsDestinados) {
		this.iphsDestinados = iphsDestinados;
	}

	/**
	 * M�todo de acceso al campo iphsElaborados.
	 * 
	 * @return El valor del campo iphsElaborados
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioElabora")
	public Set<InformePolicialHomologado> getIphsElaborados() {
		return iphsElaborados;
	}

	/**
	 * Asigna el valor al campo iphsElaborados.
	 * 
	 * @param iphsElaborados
	 *            el valor iphsElaborados a asignar
	 */
	public void setIphsElaborados(Set<InformePolicialHomologado> iphsElaborados) {
		this.iphsElaborados = iphsElaborados;
	}
	//FINALIZA MODULO DE SSP
	/**
	 * M�todo de acceso al campo medidas.
	 * 
	 * @return El valor del campo medidas
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<Medida> getMedidas() {
		return this.medidas;
	}

	/**
	 * Asigna el valor al campo medidas.
	 * 
	 * @param medidas
	 *            el valor medidas a asignar
	 */
	public void setMedidas(Set<Medida> medidas) {
		this.medidas = medidas;
	}

	/**
	 * @return the notificacions
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<Notificacion> getNotificacions() {
		return notificacions;
	}

	/**
	 * @param notificacions
	 *            the notificacions to set
	 */
	public void setNotificacions(Set<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
	public Set<PermisoExpediente> getPermisoExpedientes() {
		return this.permisoExpedientes;
	}

	public void setPermisoExpedientes(Set<PermisoExpediente> permisoExpedientes) {
		this.permisoExpedientes = permisoExpedientes;
	}

	//INICIA MODULO DE DEF
	/**
	 * M�todo de acceso al campo inspecciones.
	 * 
	 * @return El valor del campo inspecciones
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioInspeccionado")
	public Set<Inspeccion> getInspecciones() {
		return inspecciones;
	}
	
	/**
	 * Asigna el valor al campo inspecciones.
	 * 
	 * @param inspecciones
	 *            el valor inspecciones a asignar
	 */
	public void setInspecciones(Set<Inspeccion> inspecciones) {
		this.inspecciones = inspecciones;
	}
	
	/**
	 * M�todo de acceso al campo multaSanciones.
	 * 
	 * @return El valor del campo multaSanciones
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionarioMultado")
	public Set<MultaSancion> getMultaSanciones() {
		return multaSanciones;
	}
	
	/**
	 * Asigna el valor al campo multaSanciones.
	 * 
	 * @param multaSanciones
	 *            el valor multaSanciones a asignar
	 */
	public void setMultaSanciones(Set<MultaSancion> multaSanciones) {
		this.multaSanciones = multaSanciones;
	}
	//FINALIZA MODULO DE DEF

	/**
	 * M�todo de acceso al campo funcionarioAgencias.
	 *
	 * @return El valor del campo funcionarioAgencias.
	 */
	@ManyToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
	@JoinTable(name = "FuncionarioAgencias",
			joinColumns = @JoinColumn(name = "iClaveFuncionario", referencedColumnName = "iClaveFuncionario"),
			inverseJoinColumns = @JoinColumn(name = "catDiscriminante_id", referencedColumnName="catDiscriminante_id"))
	public Set<CatDiscriminante> getFuncionarioAgencias() {
		return funcionarioAgencias;
	}
	/**
	 * Asigna el valor al campo funcionarioAgencias.
	 *
	 * @param funcionarioAgencias
	 *            el valor multaSanciones a asignar
	 */
	public void setFuncionarioAgencias(Set<CatDiscriminante> funcionarioAgencias) {
		this.funcionarioAgencias = funcionarioAgencias;
	}
	//FINALIZA MODULO DE DEF

	/**
	 * @return the discriminante
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catDiscriminante_id")
	public CatDiscriminante getDiscriminante() {
		return discriminante;
	}

	/**
	 * @param discriminante
	 *            the discriminante to set
	 */
	public void setDiscriminante(CatDiscriminante discriminante) {
		this.discriminante = discriminante;
	}

	/**
	 * Asigna el valor al campo unidadIEspecializada.
	 * 
	 * @param unidadIEspecializada
	 *            el valor unidadIEspecializada a asignar
	 */
	public void setUnidadIEspecializada(CatUIEspecializada unidadIEspecializada) {
		this.unidadIEspecializada = unidadIEspecializada;
	}

	/**
	 * M�todo de acceso al campo unidadIEspecializada.
	 * 
	 * @return El valor del campo unidadIEspecializada
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catUIE_id")
	public CatUIEspecializada getUnidadIEspecializada() {
		return unidadIEspecializada;
	}

	/**
	 * @return the catAreaNegocio
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CatAreasNegocio_id", nullable = false)
	public CatAreasNegocio getCatAreaNegocio() {
		return catAreaNegocio;
	}

	/**
	 * @param catAreaNegocio
	 *            the catAreaNegocio to set
	 */
	public void setCatAreaNegocio(CatAreasNegocio catAreaNegocio) {
		this.catAreaNegocio = catAreaNegocio;
	}

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "funcionario")
    public Set<NotaExpediente> getNotas() {
        return notas;
    }

    public void setNotas(Set<NotaExpediente> notas) {
        this.notas = notas;
    }

	/**
	 * M�todo de acceso al campo buscarPorJerarquiasHijas.
	 * @return El valor del campo buscarPorJerarquiasHijas
	 */
    @Transient
	public Boolean getBuscarPorJerarquiasHijas() {
		return buscarPorJerarquiasHijas;
	}

	/**
	 * Asigna el valor al campo buscarPorJerarquiasHijas.
	 * @param buscarPorJerarquiasHijas el valor buscarPorJerarquiasHijas a asignar
	 */
	public void setBuscarPorJerarquiasHijas(Boolean buscarPorJerarquiasHijas) {
		this.buscarPorJerarquiasHijas = buscarPorJerarquiasHijas;
	}



	/**
	 * M�todo de acceso al campo rol.
	 * @return El valor del campo rol
	 */
	@Transient
	public Rol getRol() {
		return rol;
	}



	/**
	 * Asigna el valor al campo rol.
	 * @param rol el valor rol a asignar
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}


	/**
	 * @return the esConsultarRolesDelUsuario
	 */
	@Transient
	public Boolean getEsConsultarRolesDelUsuario() {
		return esConsultarRolesDelUsuario;
	}



	/**
	 * @param esConsultarRolesDelUsuario the esConsultarRolesDelUsuario to set
	 */
	public void setEsConsultarRolesDelUsuario(Boolean esConsultarRolesDelUsuario) {
		this.esConsultarRolesDelUsuario = esConsultarRolesDelUsuario;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidadFederativa_id")
	public EntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String toString(){
		return "Funcionario:"+this.getNombreCompleto();
	}
}
