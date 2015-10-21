/**
* Nombre del Programa 		: HechoForm.java
* Autor                     : ArmandoCT
* Compania                  : Ultrasist
* Proyecto                  : NSJP                    Fecha: 14 Junio 2011
* Marca de cambio        	: N/A
* Descripcion General    	: Forma para el objeto Hecho.
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion        : N/A
* Dias de ejecucion         : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                     : N/A
* Compania               	: N/A
* Proyecto                 	: N/A                                 Fecha: N/A
* Modificacion           	: N/A
*------------------------------------------------------------------------------
*/ 
package mx.gob.segob.nsjp.web.hecho.form;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

/**
 * Forma para el objeto Hecho.
 * @version 1.0
 * @author ArmandoCT
 *
 */
public class HechoForm extends GenericForm{

	/**
	 * Serialize de la clase
	 */
	private static final long serialVersionUID = 1L;
	
	//id para el Hecho
	private String idHecho;
	//para guardar si el expediente es denuncia o querella
	private String origenExpediente;

	/**
	 * @return the origenExpediente
	 */
	public String getOrigenExpediente() {
		return origenExpediente;
	}

	/**
	 * @param origenExpediente the origenExpediente to set
	 */
	public void setOrigenExpediente(String origenExpediente) {
		this.origenExpediente = origenExpediente;
	}

	/*
	 * identificador para la descripcion del hecho
	 */
	private String gcDescripcionHecho;
	
	//ids para los campos de Domicilio
	private String pais;
	private String codigoPostal;
	private String entidadFederativa;
	private String ciudad;
	private String delegacionMunicipio;
	private String asentamientoColonia;
	private String tipoAsentamiento;
	private String tipoCalle;
	private String calle;
	private String numExterior;
	private String numInterior;
	private String referencias;
	private String entreCalle;
	private String ycalle;
	private String aliasDomicilio;
	private String edificio;
	private String longitud;
	private String latitud;
	
	private String latitudN;
	private String latitudGrados;
	private String latitudMinutos;
	private String latitudSegundos;
	private String longitudE;
	private String longitudGrados;
	private String longitudMinutos;
	private String longitudSegundos;
	//private String mismoDomicilioNotificaciones;
	
	private String numeroExpedienteId;
	
	private String expedienteId;
	
	// Variables para los campos de conclusion
	private String fechaConclusion;
	private String tipoConclusion;
	private String tipoSubConclusion;
	
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
	 * @return the latitudN
	 */
	public String getLatitudN() {
		return latitudN;
	}

	/**
	 * @param latitudN the latitudN to set
	 */
	public void setLatitudN(String latitudN) {
		this.latitudN = latitudN;
	}

	/**
	 * @return the latitudGrados
	 */
	public String getLatitudGrados() {
		return latitudGrados;
	}

	/**
	 * @param latitudGrados the latitudGrados to set
	 */
	public void setLatitudGrados(String latitudGrados) {
		this.latitudGrados = latitudGrados;
	}

	/**
	 * @return the latitudMinutos
	 */
	public String getLatitudMinutos() {
		return latitudMinutos;
	}

	/**
	 * @param latitudMinutos the latitudMinutos to set
	 */
	public void setLatitudMinutos(String latitudMinutos) {
		this.latitudMinutos = latitudMinutos;
	}

	/**
	 * @return the latitudSegundos
	 */
	public String getLatitudSegundos() {
		return latitudSegundos;
	}

	/**
	 * @param latitudSegundos the latitudSegundos to set
	 */
	public void setLatitudSegundos(String latitudSegundos) {
		this.latitudSegundos = latitudSegundos;
	}

	/**
	 * @return the longitudE
	 */
	public String getLongitudE() {
		return longitudE;
	}

	/**
	 * @param longitudE the longitudE to set
	 */
	public void setLongitudE(String longitudE) {
		this.longitudE = longitudE;
	}

	/**
	 * @return the longitudGrados
	 */
	public String getLongitudGrados() {
		return longitudGrados;
	}

	/**
	 * @param longitudGrados the longitudGrados to set
	 */
	public void setLongitudGrados(String longitudGrados) {
		this.longitudGrados = longitudGrados;
	}

	/**
	 * @return the longitudMinutos
	 */
	public String getLongitudMinutos() {
		return longitudMinutos;
	}

	/**
	 * @param longitudMinutos the longitudMinutos to set
	 */
	public void setLongitudMinutos(String longitudMinutos) {
		this.longitudMinutos = longitudMinutos;
	}

	/**
	 * @return the longitudSegundos
	 */
	public String getLongitudSegundos() {
		return longitudSegundos;
	}

	/**
	 * @param longitudSegundos the longitudSegundos to set
	 */
	public void setLongitudSegundos(String longitudSegundos) {
		this.longitudSegundos = longitudSegundos;
	}

	//id para el tipo de tiempo ha ser insertado
	private String tipoTiempoHecho;
	
	//ids para los campos de tiempo especifico
	private String fecha;
	private String hora;
	
	//id para los campos de tiempo por lapso
	private String fechaInicioLapso;
	private String horaInicioLapso;
	private String fechaFinLapso;
	private String horaFinLapso;
	
	//id para la descripcion del evento en el tiempo
	private String gsNarrativa;
	
	//id de numero de Expediente y id de usuario
	private String numExpediente;
	private String idUsuario;
	//id para el tipo de lugar
	private String tipoLugar;
	private String fechaArribo;
	private String horaArribo;
	
	/**
	 * @return the idHecho
	 */
	public String getIdHecho() {
		return idHecho;
	}

	/**
	 * @param idHecho the idHecho to set
	 */
	public void setIdHecho(String idHecho) {
		this.idHecho = idHecho;
	}
	
	/**
	 * M�todo de acceso al campo numExpediente
	 * @return El valor del campo numExpediente
	 */
	public String getNumExpediente() {
		return numExpediente;
	}

	/**
	 * M�todo de acceso al campo numExpediente
	 * @param numExpediente  el valor numExpediente a asignar
	 */
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	/**
	 * M�todo de acceso al campo idUsuario
	 * @return El valor del campo idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * M�todo de acceso al campo idUsuario
	 * @param idUsuario  el valor idUsuario a asignar
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * M�todo de acceso al campo serialVersionUID
	 * @return El valor del campo serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/*****************************************  GET SET  DATO GENERAL HECHO ************************************************/
	/**
	 * M�todo de acceso al campo gcDescripcionHecho
	 * @return El valor del campo gcDescripcionHecho
	 */
	public String getGcDescripcionHecho() {
		return gcDescripcionHecho;
	}

	/**
	 * M�todo de acceso al campo gcDescripcionHecho
	 * @param gcDescripcionHecho  el valor gcDescripcionHecho a asignar
	 */
	public void setGcDescripcionHecho(String gcDescripcionHecho) {
		this.gcDescripcionHecho = gcDescripcionHecho;
	}
	/*******************************************  FIN GET SET  DATO GENERAL HECHO *******************************************/

	/*******************************************  GET SET  LUGAR     *******************************************/
	/**
	 * M�todo de acceso al campo tipoLugar
	 * @return El valor del campo tipoLugar
	 */
	public String getTipoLugar() {
		return tipoLugar;
	}

	/**
	 * M�todo de acceso al campo tipoLugar
	 * @param tipoLugar  el valor tipoLugar a asignar
	 */
	public void setTipoLugar(String tipoLugar) {
		this.tipoLugar = tipoLugar;
	}
	
	/**
	 * M�todo de acceso al campo pais
	 * @return El valor del campo pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * M�todo de acceso al campo pais
	 * @param pais  el valor pais a asignar
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * M�todo de acceso al campo codigoPostal
	 * @return El valor del campo codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * M�todo de acceso al campo codigoPostal
	 * @param idUsuario  el valor codigoPostal a asignar
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * M�todo de acceso al campo entidadFederativa
	 * @return El valor del campo entidadFederativa
	 */
	public String getEntidadFederativa() {
		return entidadFederativa;
	}

	/**
	 * M�todo de acceso al campo entidadFederativa
	 * @param entidadFederativa  el valor entidadFederativa a asignar
	 */
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	/**
	 * M�todo de acceso al campo ciudad
	 * @return El valor del campo ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * M�todo de acceso al campo ciudad
	 * @param ciudad  el valor ciudad a asignar
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * M�todo de acceso al campo delegacionMunicipio
	 * @return El valor del campo delegacionMunicipio
	 */
	public String getDelegacionMunicipio() {
		return delegacionMunicipio;
	}

	/**
	 * M�todo de acceso al campo delegacionMunicipio
	 * @param delegacionMunicipio  el valor delegacionMunicipio a asignar
	 */
	public void setDelegacionMunicipio(String delegacionMunicipio) {
		this.delegacionMunicipio = delegacionMunicipio;
	}

	/**
	 * M�todo de acceso al campo asentamientoColonia
	 * @return El valor del campo asentamientoColonia
	 */
	public String getAsentamientoColonia() {
		return asentamientoColonia;
	}

	/**
	 * M�todo de acceso al campo asentamientoColonia
	 * @param asentamientoColonia  el valor asentamientoColonia a asignar
	 */
	public void setAsentamientoColonia(String asentamientoColonia) {
		this.asentamientoColonia = asentamientoColonia;
	}

	/**
	 * M�todo de acceso al campo tipoAsentamiento
	 * @return El valor del campo tipoAsentamiento
	 */
	public String getTipoAsentamiento() {
		return tipoAsentamiento;
	}

	/**
	 * M�todo de acceso al campo tipoAsentamiento
	 * @param tipoAsentamiento  el valor tipoAsentamiento a asignar
	 */
	public void setTipoAsentamiento(String tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}

	/**
	 * M�todo de acceso al campo tipoCalle
	 * @return El valor del campo tipoCalle
	 */
	public String getTipoCalle() {
		return tipoCalle;
	}

	/**
	 * M�todo de acceso al campo tipoCalle
	 * @param tipoCalle  el valor tipoCalle a asignar
	 */
	public void setTipoCalle(String tipoCalle) {
		this.tipoCalle = tipoCalle;
	}

	/**
	 * M�todo de acceso al campo calle
	 * @return El valor del campo calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * M�todo de acceso al campo calle
	 * @param calle  el valor calle a asignar
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * M�todo de acceso al campo numExterior
	 * @return El valor del campo numExterior
	 */
	public String getNumExterior() {
		return numExterior;
	}

	/**
	 * M�todo de acceso al campo numExterior
	 * @param numExterior  el valor numExterior a asignar
	 */
	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	/**
	 * M�todo de acceso al campo numInterior
	 * @return El valor del campo numInterior
	 */
	public String getNumInterior() {
		return numInterior;
	}

	/**
	 * M�todo de acceso al campo numInterior
	 * @param numInterior  el valor numInterior a asignar
	 */
	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	/**
	 * M�todo de acceso al campo referencias
	 * @return El valor del campo referencias
	 */
	public String getReferencias() {
		return referencias;
	}

	/**
	 * M�todo de acceso al campo referencias
	 * @param referencias  el valor referencias a asignar
	 */
	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}

	/**
	 * M�todo de acceso al campo entreCalle
	 * @return El valor del campo entreCalle
	 */
	public String getEntreCalle() {
		return entreCalle;
	}

	/**
	 * M�todo de acceso al campo entreCalle
	 * @param entreCalle  el valor entreCalle a asignar
	 */
	public void setEntreCalle(String entreCalle) {
		this.entreCalle = entreCalle;
	}

	/**
	 * M�todo de acceso al campo ycalle
	 * @return El valor del campo ycalle
	 */
	public String getYcalle() {
		return ycalle;
	}

	/**
	 * M�todo de acceso al campo ycalle
	 * @param ycalle  el valor ycalle a asignar
	 */
	public void setYcalle(String ycalle) {
		this.ycalle = ycalle;
	}

	/**
	 * M�todo de acceso al campo aliasDomicilio
	 * @return El valor del campo aliasDomicilio
	 */
	public String getAliasDomicilio() {
		return aliasDomicilio;
	}

	/**
	 * M�todo de acceso al campo aliasDomicilio
	 * @param aliasDomicilio  el valor aliasDomicilio a asignar
	 */
	public void setAliasDomicilio(String aliasDomicilio) {
		this.aliasDomicilio = aliasDomicilio;
	}

	/**
	 * M�todo de acceso al campo edificio
	 * @return El valor del campo edificio
	 */
	public String getEdificio() {
		return edificio;
	}

	/**
	 * M�todo de acceso al campo edificio
	 * @param edificio  el valor edificio a asignar
	 */
	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	/**
	 * M�todo de acceso al campo longitud
	 * @return El valor del campo longitud
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * M�todo de acceso al campo longitud
	 * @param longitud  el valor longitud a asignar
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	/**
	 * M�todo de acceso al campo latitud
	 * @return El valor del campo latitud
	 */
	public String getLatitud() {
		return latitud;
	}

	/**
	 * M�todo de acceso al campo latitud
	 * @param latitud  el valor latitud a asignar
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	/*******************************************  FIN GET SET  LUGAR *******************************************/
	
	/*******************************************  GET SET  TIEMPO    *******************************************/
	/**
	 * M�todo de acceso al campo tipoTiempoHecho
	 * @return El valor del campo tipoTiempoHecho
	 */
	public String getTipoTiempoHecho() {
		return tipoTiempoHecho;
	}

	/**
	 * M�todo de acceso al campo tipoTiempoHecho
	 * @param tipoTiempoHecho  el valor tipoTiempoHecho a asignar
	 */
	public void setTipoTiempoHecho(String tipoTiempoHecho) {
		this.tipoTiempoHecho = tipoTiempoHecho;
	}

	/**
	 * M�todo de acceso al campo fecha
	 * @return El valor del campo fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * M�todo de acceso al campo fecha
	 * @param fecha  el valor fecha a asignar
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * M�todo de acceso al campo hora
	 * @return El valor del campo hora
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * M�todo de acceso al campo hora
	 * @param hora  el valor hora a asignar
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * M�todo de acceso al campo fechaInicioLapso
	 * @return El valor del campo fechaInicioLapso
	 */
	public String getFechaInicioLapso() {
		return fechaInicioLapso;
	}

	/**
	 * M�todo de acceso al campo fechaInicioLapso
	 * @param fechaInicioLapso  el valor fechaInicioLapso a asignar
	 */
	public void setFechaInicioLapso(String fechaInicioLapso) {
		this.fechaInicioLapso = fechaInicioLapso;
	}

	/**
	 * M�todo de acceso al campo horaInicioLapso
	 * @return El valor del campo horaInicioLapso
	 */
	public String getHoraInicioLapso() {
		return horaInicioLapso;
	}

	/**
	 * M�todo de acceso al campo horaInicioLapso
	 * @param horaInicioLapso  el valor horaInicioLapso a asignar
	 */
	public void setHoraInicioLapso(String horaInicioLapso) {
		this.horaInicioLapso = horaInicioLapso;
	}

	/**
	 * M�todo de acceso al campo fechaFinLapso
	 * @return El valor del campo fechaFinLapso
	 */
	public String getFechaFinLapso() {
		return fechaFinLapso;
	}

	/**
	 * M�todo de acceso al campo fechaFinLapso
	 * @param fechaFinLapso  el valor fechaFinLapso a asignar
	 */
	public void setFechaFinLapso(String fechaFinLapso) {
		this.fechaFinLapso = fechaFinLapso;
	}

	/**
	 * M�todo de acceso al campo horaFinLapso
	 * @return El valor del campo horaFinLapso
	 */
	public String getHoraFinLapso() {
		return horaFinLapso;
	}

	/**
	 * M�todo de acceso al campo horaFinLapso
	 * @param horaFinLapso  el valor horaFinLapso a asignar
	 */
	public void setHoraFinLapso(String horaFinLapso) {
		this.horaFinLapso = horaFinLapso;
	}

	/**
	 * M�todo de acceso al campo gsNarrativa
	 * @return El valor del campo gsNarrativa
	 */
	public String getGsNarrativa() {
		return gsNarrativa;
	}

	/**
	 * M�todo de acceso al campo gsNarrativa
	 * @param gsNarrativa  el valor gsNarrativa a asignar
	 */
	public void setGsNarrativa(String gsNarrativa) {
		this.gsNarrativa = gsNarrativa;
	}
	/*******************************************  FIN GET SET  TIEMPO ******************************************/

	public String getFechaArribo() {
		return fechaArribo;
	}

	public void setFechaArribo(String fechaArribo) {
		this.fechaArribo = fechaArribo;
	}

	public String getHoraArribo() {
		return horaArribo;
	}

	public void setHoraArribo(String horaArribo) {
		this.horaArribo = horaArribo;
	}

	/**
	 * Metodo de acceso al campo expedienteId
	 * @return
	 */
	public String getExpedienteId() {
		return expedienteId;
	}

	/**
	 * Recupera el expedienteId
	 * @param expedienteId
	 */
	public void setExpedienteId(String expedienteId) {
		this.expedienteId = expedienteId;
	}

	public String getFechaConclusion() {
		return fechaConclusion;
	}

	public void setFechaConclusion(String fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}

	public String getTipoConclusion() {
		return tipoConclusion;
	}

	public void setTipoConclusion(String tipoConclusion) {
		this.tipoConclusion = tipoConclusion;
	}

	public String getTipoSubConclusion() {
		return tipoSubConclusion;
	}

	public void setTipoSubConclusion(String tipoSubConclusion) {
		this.tipoSubConclusion = tipoSubConclusion;
	}
	
	
}
