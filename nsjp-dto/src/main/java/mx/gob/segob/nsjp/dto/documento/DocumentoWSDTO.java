/**
 * Nombre del Programa : DocumentoWSDTO.java
 * Autor                            : GustavoBP                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 25/07/2012
 * Marca de cambio        : N/A
 * Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de un Documento.
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.documento;

import java.util.Date;

import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

/**
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de un Documento. 
 * @author GustavoBP
 * @version 1.0
 */
public class DocumentoWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3446165991139389796L;
	private Long documentoId;
	private Long tipoDocumentoDTO;
	private Long formaId;
	private String folioDocumento;
	private String nombreDocumento;
	private Date fechaCreacion;
	private String strFechaCreacion;
	private String strHoraCreacion;
	private Double version;
	private String numeroFojas;
	private Short origenDocumento;
	private String textoParcial;
	private ArchivoDigitalWSDTO archivoDigital;
	private ActividadWSDTO actividad;
	// private Long confInstitucionId; se encuentra en el GenericWSDTO
	/**
	 * Es la clave del distrito para saber a que distrito de defensoria se debe
	 * de asignar
	 */
	private String claveDiscriminanteOrigen;
	private String descripcion;
	//nuevo atributo mapeado del DTO
	private Boolean esGuardadoParcial;
	private Long jerarquiaOrganizacional;
	
	public DocumentoWSDTO() {
		super();
	}
	
	
	/**
	 * @return the documentoId
	 */
	public Long getDocumentoId() {
		return documentoId;
	}


	/**
	 * @param documentoId the documentoId to set
	 */
	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}


	/**
	 * M�todo de acceso al campo tipoDocumentoDTO.
	 * @return El valor del campo tipoDocumentoDTO
	 */
	public Long getTipoDocumentoDTO() {
		return tipoDocumentoDTO;
	}
	/**
	 * Asigna el valor al campo tipoDocumentoDTO.
	 * @param tipoDocumentoDTO el valor tipoDocumentoDTO a asignar
	 */
	public void setTipoDocumentoDTO(Long tipoDocumentoDTO) {
		this.tipoDocumentoDTO = tipoDocumentoDTO;
	}
	/**
	 * M�todo de acceso al campo formaId.
	 * @return El valor del campo formaId
	 */
	public Long getFormaId() {
		return formaId;
	}
	/**
	 * Asigna el valor al campo formaId.
	 * @param formaId el valor formaId a asignar
	 */
	public void setFormaId(Long formaId) {
		this.formaId = formaId;
	}
	/**
	 * @return the textoParcial
	 */
	public String getTextoParcial() {
		return textoParcial;
	}
	/**
	 * @param textoParcial the textoParcial to set
	 */
	public void setTextoParcial(String textoParcial) {
		this.textoParcial = textoParcial;
	}
	/**
	 * M�todo de acceso al campo folioDocumento.
	 * @return El valor del campo folioDocumento
	 */
	public String getFolioDocumento() {
		return folioDocumento;
	}
	/**
	 * Asigna el valor al campo folioDocumento.
	 * @param folioDocumento el valor folioDocumento a asignar
	 */
	public void setFolioDocumento(String folioDocumento) {
		this.folioDocumento = folioDocumento;
	}
	/**
	 * M�todo de acceso al campo nombreDocumento.
	 * @return El valor del campo nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	/**
	 * Asigna el valor al campo nombreDocumento.
	 * @param nombreDocumento el valor nombreDocumento a asignar
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	/**
	 * M�todo de acceso al campo fechaCreacion.
	 * @return El valor del campo fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * Asigna el valor al campo fechaCreacion.
	 * @param fechaCreacion el valor fechaCreacion a asignar
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * M�todo de acceso al campo version.
	 * @return El valor del campo version
	 */
	public Double getVersion() {
		return version;
	}
	/**
	 * Asigna el valor al campo version.
	 * @param version el valor version a asignar
	 */
	public void setVersion(Double version) {
		this.version = version;
	}
	/**
	 * M�todo de acceso al campo numeroFojas.
	 * @return El valor del campo numeroFojas
	 */
	public String getNumeroFojas() {
		return numeroFojas;
	}
	/**
	 * Asigna el valor al campo numeroFojas.
	 * @param numeroFojas el valor numeroFojas a asignar
	 */
	public void setNumeroFojas(String numeroFojas) {
		this.numeroFojas = numeroFojas;
	}
	/**
	 * M�todo de acceso al campo origenDocumento.
	 * @return El valor del campo origenDocumento
	 */
	public Short getOrigenDocumento() {
		return origenDocumento;
	}
	/**
	 * Asigna el valor al campo origenDocumento.
	 * @param origenDocumento el valor origenDocumento a asignar
	 */
	public void setOrigenDocumento(Short origenDocumento) {
		this.origenDocumento = origenDocumento;
	}
	/**
	 * M�todo de acceso al campo strFechaCreacion.
	 * @return El valor del campo strFechaCreacion
	 */
	public String getStrFechaCreacion() {
		return strFechaCreacion;
	}
	/**
	 * Asigna el valor al campo strFechaCreacion.
	 * @param strFechaCreacion el valor strFechaCreacion a asignar
	 */
	public void setStrFechaCreacion(String strFechaCreacion) {
		this.strFechaCreacion = strFechaCreacion;
	}
	/**
	 * M�todo de acceso al campo strHoraCreacion.
	 * @return El valor del campo strHoraCreacion
	 */
	public String getStrHoraCreacion() {
		return strHoraCreacion;
	}
	/**
	 * Asigna el valor al campo strHoraCreacion.
	 * @param strHoraCreacion el valor strHoraCreacion a asignar
	 */
	public void setStrHoraCreacion(String strHoraCreacion) {
		this.strHoraCreacion = strHoraCreacion;
	}
	/**
	 * M�todo de acceso al campo archivoDigital.
	 * @return El valor del campo archivoDigital
	 */
	public ArchivoDigitalWSDTO getArchivoDigital() {
		return archivoDigital;
	}
	/**
	 * Asigna el valor al campo archivoDigital.
	 * @param archivoDigital el valor archivoDigital a asignar
	 */
	public void setArchivoDigital(ArchivoDigitalWSDTO archivoDigital) {
		this.archivoDigital = archivoDigital;
	}


    /**
     * M�todo de acceso al campo actividad.
     * @return El valor del campo actividad
     */
    public ActividadWSDTO getActividad() {
        return actividad;
    }


    /**
     * Asigna el valor al campo actividad.
     * @param actividad el valor actividad a asignar
     */
    public void setActividad(ActividadWSDTO actividad) {
        this.actividad = actividad;
    }


	/**
	 * @return the claveDiscriminanteOrigen
	 */
	public String getClaveDiscriminanteOrigen() {
		return claveDiscriminanteOrigen;
	}


	/**
	 * @param claveDiscriminanteOrigen the claveDiscriminanteOrigen to set
	 */
	public void setClaveDiscriminanteOrigen(String claveDiscriminanteOrigen) {
		this.claveDiscriminanteOrigen = claveDiscriminanteOrigen;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	/**
	 * @return the esGuardadoParcial
	 */
	public Boolean getEsGuardadoParcial() {
		return esGuardadoParcial;
	}


	/**
	 * @param esGuardadoParcial the esGuardadoParcial to set
	 */
	public void setEsGuardadoParcial(Boolean esGuardadoParcial) {
		this.esGuardadoParcial = esGuardadoParcial;
	}


	public Long getJerarquiaOrganizacional() {
		return jerarquiaOrganizacional;
	}


	public void setJerarquiaOrganizacional(Long jerarquiaOrganizacional) {
		this.jerarquiaOrganizacional = jerarquiaOrganizacional;
	}
       
}
