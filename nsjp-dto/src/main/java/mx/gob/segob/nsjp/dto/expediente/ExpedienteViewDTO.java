package mx.gob.segob.nsjp.dto.expediente;

import mx.gob.segob.nsjp.dto.base.GenericDTO;


public class ExpedienteViewDTO extends GenericDTO {

	private static final long serialVersionUID = -1415524474274685233L;
	
    private String casoId;
    private String numeroGeneralCaso;
    private String expedienteId;
    private String numeroExpedienteId;
    private String numeroExpediente;
    private String nombreInvolucrado;
	private String nombreCalidadInvolucrado;
	private String delito;
	private String esPrincipal;
	private String claveFuncionario;
	private String nombreFuncionario;
	private String areaFuncionario;
	private String edificio;
	private String estatus;
	private String esConsulta;
	private String totalRegistros;
	private String fechaApertura;
	private String idArea;
	private String denunciate;
	private String tipoDenuncia;
	
	private String nombre;
	private String apPaterno;
	private String apMaterno;
	private String tribunal;
	private String victima;
	private String area;
	
	
	
	
	public ExpedienteViewDTO(String casoId, String numeroGeneralCaso,
			String expedienteId, String numeroExpedienteId,
			String numeroExpediente, String nombreInvolucrado,
			String nombreCalidadInvolucrado, String delito,
			String esPrincipal, String claveFuncionario,
			String nombreFuncionario, String areaFuncionario, String edificio,
			String estatus, String esConsulta, String totalRegistros,
			String fechaApertura, String idArea) {
		super();
		this.casoId = casoId;
		this.numeroGeneralCaso = numeroGeneralCaso;
		this.expedienteId = expedienteId;
		this.numeroExpedienteId = numeroExpedienteId;
		this.numeroExpediente = numeroExpediente;
		this.nombreInvolucrado = nombreInvolucrado;
		this.nombreCalidadInvolucrado = nombreCalidadInvolucrado;
		this.delito = delito;
		this.esPrincipal = esPrincipal;
		this.claveFuncionario = claveFuncionario;
		this.nombreFuncionario = nombreFuncionario;
		this.areaFuncionario = areaFuncionario;
		this.edificio = edificio;
		this.estatus = estatus;
		this.esConsulta = esConsulta;
		this.totalRegistros = totalRegistros;
		this.fechaApertura = fechaApertura;
		this.idArea = idArea;
	}
	
	/**
	 *  Constructor para utilizar cuando la institucion actual es PJ 
	 *  utilizado en consultarExpedientesConSP() de BuscarExpedienteServiceImpl.java
	 */
	public ExpedienteViewDTO(String casoId, String numeroGeneralCaso,
			String expedienteId, String numeroExpedienteId,
			String numeroExpediente, String nombre, String apPaterno,
			String apMaterno, String nombreCalidadInvolucrado, String tribunal,
			String esConsulta, String totalRegistros) {
		super();
		this.casoId = casoId;
		this.numeroGeneralCaso = numeroGeneralCaso;
		this.expedienteId = expedienteId;
		this.numeroExpedienteId = numeroExpedienteId;
		this.numeroExpediente = numeroExpediente;
		this.nombre = nombre;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombreCalidadInvolucrado = nombreCalidadInvolucrado;
		this.tribunal = tribunal;
		this.esConsulta = esConsulta;
		this.totalRegistros = totalRegistros;
	}
	
	public ExpedienteViewDTO() {
	}
	/**
	 * @return the casoId
	 */
	public String getCasoId() {
		return casoId;
	}
	/**
	 * @param casoId the casoId to set
	 */
	public void setCasoId(String casoId) {
		this.casoId = casoId;
	}
	/**
	 * @return the numeroGeneralCaso
	 */
	public String getNumeroGeneralCaso() {
		return numeroGeneralCaso;
	}
	/**
	 * @param numeroGeneralCaso the numeroGeneralCaso to set
	 */
	public void setNumeroGeneralCaso(String numeroGeneralCaso) {
		this.numeroGeneralCaso = numeroGeneralCaso;
	}
	/**
	 * @return the expedienteId
	 */
	public String getExpedienteId() {
		return expedienteId;
	}
	/**
	 * @param expedienteId the expedienteId to set
	 */
	public void setExpedienteId(String expedienteId) {
		this.expedienteId = expedienteId;
	}
	/**
	 * @return the numeroExpedienteId
	 */
	public String getNumeroExpedienteId() {
		return numeroExpedienteId;
	}
	/**
	 * @param numeroExpedienteId the numeroExpedienteId to set
	 */
	public void setNumeroExpedienteId(String numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}
	/**
	 * @return the numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	/**
	 * @param numeroExpediente the numeroExpediente to set
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	/**
	 * @return the nombreInvolucrado
	 */
	public String getNombreInvolucrado() {
		return nombreInvolucrado;
	}
	/**
	 * @param nombreInvolucrado the nombreInvolucrado to set
	 */
	public void setNombreInvolucrado(String nombreInvolucrado) {
		this.nombreInvolucrado = nombreInvolucrado;
	}
	/**
	 * @return the nombreCalidadInvolucrado
	 */
	public String getNombreCalidadInvolucrado() {
		return nombreCalidadInvolucrado;
	}
	/**
	 * @param nombreCalidadInvolucrado the nombreCalidadInvolucrado to set
	 */
	public void setNombreCalidadInvolucrado(String nombreCalidadInvolucrado) {
		this.nombreCalidadInvolucrado = nombreCalidadInvolucrado;
	}
	/**
	 * @return the delito
	 */
	public String getDelito() {
		return delito;
	}
	/**
	 * @param delito the delito to set
	 */
	public void setDelito(String delito) {
		this.delito = delito;
	}
	/**
	 * @return the esPrincipal
	 */
	public String getEsPrincipal() {
		return esPrincipal;
	}
	/**
	 * @param esPrincipal the esPrincipal to set
	 */
	public void setEsPrincipal(String esPrincipal) {
		this.esPrincipal = esPrincipal;
	}
	/**
	 * @return the claveFuncionario
	 */
	public String getClaveFuncionario() {
		return claveFuncionario;
	}
	/**
	 * @param claveFuncionario the claveFuncionario to set
	 */
	public void setClaveFuncionario(String claveFuncionario) {
		this.claveFuncionario = claveFuncionario;
	}
	/**
	 * @return the nombreFuncionario
	 */
	public String getNombreFuncionario() {
		return nombreFuncionario;
	}
	/**
	 * @param nombreFuncionario the nombreFuncionario to set
	 */
	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}
	/**
	 * @return the areaFuncionario
	 */
	public String getAreaFuncionario() {
		return areaFuncionario;
	}
	/**
	 * @param areaFuncionario the areaFuncionario to set
	 */
	public void setAreaFuncionario(String areaFuncionario) {
		this.areaFuncionario = areaFuncionario;
	}
	/**
	 * @return the edificio
	 */
	public String getEdificio() {
		return edificio;
	}
	/**
	 * @param edificio the edificio to set
	 */
	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the esConsulta
	 */
	public String getEsConsulta() {
		return esConsulta;
	}
	/**
	 * @param esConsulta the esConsulta to set
	 */
	public void setEsConsulta(String esConsulta) {
		this.esConsulta = esConsulta;
	}
	/**
	 * @return the totalRegistros
	 */
	public String getTotalRegistros() {
		return totalRegistros;
	}
	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(String totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	/**
	 * @return the fechaApertura
	 */
	public String getFechaApertura() {
		return fechaApertura;
	}
	/**
	 * @param fechaApertura the fechaApertura to set
	 */
	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}
	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	/**
	 * @return the denunciate
	 */
	public String getDenunciate() {
		return denunciate;
	}
	/**
	 * @param denunciate the denunciate to set
	 */
	public void setDenunciate(String denunciate) {
		this.denunciate = denunciate;
	}
	/**
	 * @return the tipoDenuncia
	 */
	public String getTipoDenuncia() {
		return tipoDenuncia;
	}
	/**
	 * @param tipoDenuncia the tipoDenuncia to set
	 */
	public void setTipoDenuncia(String tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}
	/**
	 * M&eacute;todo de acceso al campo nombre
	 * @return El valor del campo nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Asigna el valor al campo nombre.
	 * @param El valor nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * M&eacute;todo de acceso al campo apPaterno
	 * @return El valor del campo apPaterno
	 */
	public String getApPaterno() {
		return apPaterno;
	}
	/**
	 * Asigna el valor al campo apPaterno.
	 * @param El valor del campo apPaterno
	 */
	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}
	/**
	 * M&eacute;todo de acceso al campo apMaterno
	 * @return El valor del campo apMaterno
	 */
	public String getApMaterno() {
		return apMaterno;
	}
	/**
	 * Asigna el valor al campo apMaterno
	 * @param El valor del campo apMaterno
	 */
	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}
	/**
	 * M&eacute;todo de acceso al campo tribunal
	 * @return El valor del campo tribunal
	 */
	public String getTribunal() {
		return tribunal;
	}
	/**
	 * Asigna el valor al campo tribunal
	 * @param El valor del campo tribunal
	 */
	public void setTribunal(String tribunal) {
		this.tribunal = tribunal;
	}

	public String getVictima() {
		return victima;
	}

	public void setVictima(String victima) {
		this.victima = victima;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
