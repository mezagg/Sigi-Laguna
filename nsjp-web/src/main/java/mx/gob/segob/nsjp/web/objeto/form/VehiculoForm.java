/**
* Nombre del Programa		: VehiculoForm.java
* Autor                 	: AlejandroGA
* Compania                  : Ultrasist
* Proyecto                  : NSJP                    Fecha: 28 Apr 2011
* Marca de cambio        	: N/A
* Descripcion General    	: Forma para el objeto Vehiculo
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
package mx.gob.segob.nsjp.web.objeto.form;

import org.apache.struts.upload.FormFile;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

/**
 * Forma para el objeto Vehiculo.
 * @version 1.0
 * @author AlejandroGA
 *
 */
public class VehiculoForm extends GenericForm{

	/**
	 * Serialize de la clase
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idVehiculo;
	
	/** archivo adjunto */
	private FormFile archivo;
	
	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	//Campos para la insercion desde cadena de custodia
	private String fechaLevCadCus; 
	private String origenEvdCadCus;
	private String cadenaCustodia;
	
	private String propietario;
	private Long relacionHechoId;
	private Boolean anular;
	/**
	 * indica si el No. de Motor es alterado o no
	 */
	private Boolean gbEsNumMotorAlterado;
	/**
	 * indica si el No. de Serie es alterado o no
	 */
	private Boolean gbEsNumSerieAlterado;

	
	
	
	//Fin Campos para la insercion desde cadena de custodia
	
	/**
	 * @return the fechaLevCadCus
	 */
	public String getFechaLevCadCus() {
		return fechaLevCadCus;
	}

	/**
	 * @param fechaLevCadCus the fechaLevCadCus to set
	 */
	public void setFechaLevCadCus(String fechaLevCadCus) {
		this.fechaLevCadCus = fechaLevCadCus;
	}

	/**
	 * @return the origenEvdCadCus
	 */
	public String getOrigenEvdCadCus() {
		return origenEvdCadCus;
	}

	/**
	 * @param origenEvdCadCus the origenEvdCadCus to set
	 */
	public void setOrigenEvdCadCus(String origenEvdCadCus) {
		this.origenEvdCadCus = origenEvdCadCus;
	}
	
	/**
	 * @return the cadenaCustodia
	 */
	public String getCadenaCustodia() {
		return cadenaCustodia;
	}

	/**
	 * @param cadenaCustodia the cadenaCustodia to set
	 */
	public void setCadenaCustodia(String cadenaCustodia) {
		this.cadenaCustodia = cadenaCustodia;
	}

	/**
	 * identificador del tipo de vehiculo
	 */
	private Long glTipoVehiculoId;
	
	/**
	 * identificador de la marca del vehiculo
	 */  
	private Long glMarcaVehiculoId;
	
	/**
	 * identificador de la sub marca del vehiculo
	 */  
	private Long glSubMarcaVehiculoId;
	
	/**
	 * identificador del color del vehiculo
	 */  
	private Long glColorVehiculoId;
	
	/**
	 * identificador del pais de origen del vehiculo
	 */  
	private Long glIdPaisOrigenId;
	
	/**
	 * modelo del vehiculo (a�o)
	 */  
	private Short gsModelo;
	
	/**
	 * no. de placas vehiculo
	 */  
	private String gcNoPlaca;
	
	/**
	 * no. de serie del vehiculo
	 */  
	private String gcNoSerie;
	
	/**
	 * No de motor del vehiculo
	 */  
	private String gcNoMotor;
	
	/**
	 * No de Registro Federal de Vehiculos
	 */
	private String gcNoRegFed;
	
	/**
	 * indica si es blindado o no
	 */
	private Boolean gbEsBlindado;
	
	/**
	 * No de puertas del vehiculo
	 */
	private Short gsNoPuertas;
	
	/**
	 * No de cilindros del vehiculo
	 */
	private Short gsNoCilindros;
	
	/**
	 *condicion del vehiculo
	 */
	private Long glCondicionFisica;
	
	/**
	 * Breve descripcion del vehiculo
	 */
	private String gsDescripcion;
	
	/**
	 * identificador de la causa del vehiculo
	 */
	private Long causaVehiculoId;
	
	/**
	 * identificador de la causa del vehiculo recuperado
	 */
	private Long causaVehiculoRecuperadoId;
	
	/**
	 * identificador de la causa del vehiculo recuperado por otros
	 */
	private Long causaVehiculoRecuperadoOtrosId;
	
	/**
	 * fecha en que se recupero el vehiculo (relacion hechos)
	 */
	private String fechaRecuperado;
	
	/**
	 * lugar donde se recupero el vehiculo (relacion hechos)
	 */
	private String lugarRecuperacion;
	
	/**
	 * autoridad que recupero el vehiculo (relacion hechos)
	 */
	private String autoridadRecupero;
	
	/**
	 * fecha en que se entrego el vehiculo recuperado (relacion hechos)
	 */
	private String fechaEntrega;
	
	/**
	 * M�todo de acceso al campo glTipoVehiculoId.
	 * @return El valor del campo glTipoVehiculoId
	 */
	public Long getGlTipoVehiculoId() {
		return glTipoVehiculoId;
	}

	/**
	 * Asigna el valor al campo glTipoVehiculoId.
	 * @param glTipoVehiculoId el valor glTipoVehiculoId a asignar
	 */
	public void setGlTipoVehiculoId(Long glTipoVehiculoId) {
		this.glTipoVehiculoId = glTipoVehiculoId;
	}

	/**
	 * M�todo de acceso al campo glMarcaVehiculoId.
	 * @return El valor del campo glMarcaVehiculoId
	 */
	public Long getGlMarcaVehiculoId() {
		return glMarcaVehiculoId;
	}

	/**
	 * Asigna el valor al campo glMarcaVehiculoId.
	 * @param glMarcaVehiculoId el valor glMarcaVehiculoId a asignar
	 */
	public void setGlMarcaVehiculoId(Long glMarcaVehiculoId) {
		this.glMarcaVehiculoId = glMarcaVehiculoId;
	}

	/**
	 * M�todo de acceso al campo serialversionuid.
	 * @return El valor del campo serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * M�todo de acceso al campo glSubMarcaVehiculoId.
	 * @return El valor del campo glSubMarcaVehiculoId
	 */
	public Long getGlSubMarcaVehiculoId() {
		return glSubMarcaVehiculoId;
	}

	/**
	 * Asigna el valor al campo glSubMarcaVehiculoId.
	 * @param glSubMarcaVehiculoId el valor glSubMarcaVehiculoId a asignar
	 */
	public void setGlSubMarcaVehiculoId(Long glSubMarcaVehiculoId) {
		this.glSubMarcaVehiculoId = glSubMarcaVehiculoId;
	}

	/**
	 * M�todo de acceso al campo glColorVehiculoId.
	 * @return El valor del campo glColorVehiculoId
	 */
	public Long getGlColorVehiculoId() {
		return glColorVehiculoId;
	}

	/**
	 * Asigna el valor al campo glColorVehiculoId.
	 * @param glColorVehiculoId el valor glColorVehiculoId a asignar
	 */
	public void setGlColorVehiculoId(Long glColorVehiculoId) {
		this.glColorVehiculoId = glColorVehiculoId;
	}

	/**
	 * M�todo de acceso al campo glIdPaisOrigenId.
	 * @return El valor del campo glIdPaisOrigenId
	 */
	public Long getGlIdPaisOrigenId() {
		return glIdPaisOrigenId;
	}

	/**
	 * Asigna el valor al campo glIdPaisOrigenId.
	 * @param glIdPaisOrigenId el valor glIdPaisOrigenId a asignar
	 */
	public void setGlIdPaisOrigenId(Long glIdPaisOrigenId) {
		this.glIdPaisOrigenId = glIdPaisOrigenId;
	}

	/**
	 * M�todo de acceso al campo gsModelo.
	 * @return El valor del campo gsModelo
	 */
	public Short getGsModelo() {
		return gsModelo;
	}

	/**
	 * Asigna el valor al campo gsModelo.
	 * @param gsModelo el valor gsModelo a asignar
	 */
	public void setGsModelo(Short gsModelo) {
		this.gsModelo = gsModelo;
	}

	/**
	 * M�todo de acceso al campo gcPlaca.
	 * @return El valor del campo gcPlaca
	 */
	public String getGcNoPlaca() {
		return gcNoPlaca;
	}

	/**
	 * Asigna el valor al campo gcPlaca.
	 * @param gcPlaca el valor gcPlaca a asignar
	 */
	public void setGcNoPlaca(String gcNoPlaca) {
		this.gcNoPlaca = gcNoPlaca;
	}

	/**
	 * M�todo de acceso al campo gcNoSerie.
	 * @return El valor del campo gcNoSerie
	 */
	public String getGcNoSerie() {
		return gcNoSerie;
	}

	/**
	 * Asigna el valor al campo gcNoSerie.
	 * @param gcNoSerie el valor gcNoSerie a asignar
	 */
	public void setGcNoSerie(String gcNoSerie) {
		this.gcNoSerie = gcNoSerie;
	}

	/**
	 * M�todo de acceso al campo gcNoMotor.
	 * @return El valor del campo gcNoMotor
	 */
	public String getGcNoMotor() {
		return gcNoMotor;
	}

	/**
	 * Asigna el valor al campo gcNoMotor.
	 * @param gcNoMotor el valor gcNoMotor a asignar
	 */
	public void setGcNoMotor(String gcNoMotor) {
		this.gcNoMotor = gcNoMotor;
	}

	/**
	 * M�todo de acceso al campo gcNoRegFed.
	 * @return El valor del campo gcNoRegFed
	 */
	public String getGcNoRegFed() {
		return gcNoRegFed;
	}

	/**
	 * Asigna el valor al campo gcNoRegFed.
	 * @param gcNoRegFed el valor gcNoRegFed a asignar
	 */
	public void setGcNoRegFed(String gcNoRegFed) {
		this.gcNoRegFed = gcNoRegFed;
	}

	/**
	 * M�todo de acceso al campo gbEsBlindado.
	 * @return El valor del campo gbEsBlindado
	 */
	public Boolean getGbEsBlindado() {
		return gbEsBlindado;
	}

	/**
	 * Asigna el valor al campo gbEsBlindado.
	 * @param gbEsBlindado el valor gbEsBlindado a asignar
	 */
	public void setGbEsBlindado(Boolean gbEsBlindado) {
		this.gbEsBlindado = gbEsBlindado;
	}

	/**
	 * M�todo de acceso al campo gsNoPuertas.
	 * @return El valor del campo gsNoPuertas
	 */
	public Short getGsNoPuertas() {
		return gsNoPuertas;
	}

	/**
	 * Asigna el valor al campo gsNoPuertas.
	 * @param gsNoPuertas el valor gsNoPuertas a asignar
	 */
	public void setGsNoPuertas(Short gsNoPuertas) {
		this.gsNoPuertas = gsNoPuertas;
	}

	/**
	 * M�todo de acceso al campo gsNoCilindros.
	 * @return El valor del campo gsNoCilindros
	 */
	public Short getGsNoCilindros() {
		return gsNoCilindros;
	}

	/**
	 * Asigna el valor al campo gsNoCilindros.
	 * @param gsNoCilindros el valor gsNoCilindros a asignar
	 */
	public void setGsNoCilindros(Short gsNoCilindros) {
		this.gsNoCilindros = gsNoCilindros;
	}

	/**
	 * M�todo de acceso al campo glCondicionFisica.
	 * @return El valor del campo glCondicionFisica
	 */
	public Long getGlCondicionFisica() {
		return glCondicionFisica;
	}

	/**
	 * Asigna el valor al campo glCondicionFisica.
	 * @param glCondicionFisica el valor glCondicionFisica a asignar
	 */
	public void setGlCondicionFisica(Long glCondicionFisica) {
		this.glCondicionFisica = glCondicionFisica;
	}

	/**
	 * M�todo de acceso al campo gsDescripcion.
	 * @return El valor del campo gsDescripcion
	 */
	public String getGsDescripcion() {
		return gsDescripcion;
	}

	/**
	 * Asigna el valor al campo gsDescripcion.
	 * @param gsDescripcion el valor gsDescripcion a asignar
	 */
	public void setGsDescripcion(String gsDescripcion) {
		this.gsDescripcion = gsDescripcion;
	}

	/**
	 * @return the archivo
	 */
	public FormFile getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(FormFile archivo) {
		this.archivo = archivo;
	}

	/**
	 * M�todo de acceso al campo propietario.
	 * @return El valor del campo propietario
	 */
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

	/**
	 * M�todo de acceso al campo relacionHechoId.
	 * @return El valor del campo relacionHechoId
	 */
	public Long getRelacionHechoId() {
		return relacionHechoId;
	}

	/**
	 * Asigna el valor al campo relacionHechoId.
	 * @param relacionHechoId el valor relacionHechoId a asignar
	 */
	public void setRelacionHechoId(Long relacionHechoId) {
		this.relacionHechoId = relacionHechoId;
	}

	/**
	 * @return the anular
	 */
	public Boolean getAnular() {
		return anular;
	}

	/**
	 * @param anular the anular to set
	 */
	public void setAnular(Boolean anular) {
		this.anular = anular;
	}

	public void setGbEsNumMotorAlterado(Boolean gbEsNumMotorAlterado) {
		this.gbEsNumMotorAlterado = gbEsNumMotorAlterado;
	}

	public Boolean getGbEsNumMotorAlterado() {
		return gbEsNumMotorAlterado;
	}

	public void setGbEsNumSerieAlterado(Boolean gbEsNumSerieAlterado) {
		this.gbEsNumSerieAlterado = gbEsNumSerieAlterado;
	}

	public Boolean getGbEsNumSerieAlterado() {
		return gbEsNumSerieAlterado;
	}

	public Long getCausaVehiculoId() {
		return causaVehiculoId;
	}

	public void setCausaVehiculoId(Long causaVehiculoId) {
		this.causaVehiculoId = causaVehiculoId;
	}

	public Long getCausaVehiculoRecuperadoId() {
		return causaVehiculoRecuperadoId;
	}

	public void setCausaVehiculoRecuperadoId(Long causaVehiculoRecuperadoId) {
		this.causaVehiculoRecuperadoId = causaVehiculoRecuperadoId;
	}

	public Long getCausaVehiculoRecuperadoOtrosId() {
		return causaVehiculoRecuperadoOtrosId;
	}

	public void setCausaVehiculoRecuperadoOtrosId(
			Long causaVehiculoRecuperadoOtrosId) {
		this.causaVehiculoRecuperadoOtrosId = causaVehiculoRecuperadoOtrosId;
	}

	public String getFechaRecuperado() {
		return fechaRecuperado;
	}

	public void setFechaRecuperado(String fechaRecuperado) {
		this.fechaRecuperado = fechaRecuperado;
	}

	public String getLugarRecuperacion() {
		return lugarRecuperacion;
	}

	public void setLugarRecuperacion(String lugarRecuperacion) {
		this.lugarRecuperacion = lugarRecuperacion;
	}

	public String getAutoridadRecupero() {
		return autoridadRecupero;
	}

	public void setAutoridadRecupero(String autoridadRecupero) {
		this.autoridadRecupero = autoridadRecupero;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

		
}
