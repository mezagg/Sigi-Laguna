/**
* Nombre del Programa 		: DetalleSolicitudRSForm.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 11/01/2013
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.reinsercionsocial.form;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class DetalleSolicitudRSForm extends GenericForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4851479174915092648L;
	
	private String numeroExpediente;
	private String numeroCaso;
	private String folioSolicitud;
	private String estatusSolicitud;
	private String fechaSolicitudSTR; 
	private String tipoSolicitud;
	private String destinatario;
	private String institucion;
	private String folioDocumento;
	private String fechaCreacion;
	private String tipoDocumento;
	private long idDocumento;
	
	/**
	 * M&eacute;todo de acceso al campo numeroExpediente.
	 * @return El valor del campo numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	
	/**
	 * Asigna el valor al campo numeroExpediente.
	 * @param numeroExpediente el valor numeroExpediente a asignar
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	
	/**
	 * M&eacute;todo de acceso al campo numeroCaso.
	 * @return El valor del campo numeroCaso
	 */
	public String getNumeroCaso() {
		return numeroCaso;
	}
	
	/**
	 * Asigna el valor al campo numeroCaso.
	 * @param numeroCaso el valor numeroCaso a asignar
	 */
	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}
	
	/**
	 * M&eacute;todo de acceso al campo folioSolicitud.
	 * @return El valor del campo folioSolicitud
	 */
	public String getFolioSolicitud() {
		return folioSolicitud;
	}
	
	/**
	 * Asigna el valor al campo folioSolicitud.
	 * @param folioSolicitud el valor folioSolicitud a asignar
	 */
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}
	
	/**
	 * M&eacute;todo de acceso al campo estatusSolicitud.
	 * @return El valor del campo estatusSolicitud
	 */
	public String getEstatusSolicitud() {
		return estatusSolicitud;
	}
	
	/**
	 * Asigna el valor al campo estatusSolicitud.
	 * @param estatusSolicitud el valor estatusSolicitud a asignar
	 */
	public void setEstatusSolicitud(String estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaSolicitudSTR.
	 * @return El valor del campo fechaSolicitudSTR
	 */
	public String getFechaSolicitudSTR() {
		return fechaSolicitudSTR;
	}
	
	/**
	 * Asigna el valor al campo fechaSolicitudSTR.
	 * @param fechaSolicitudSTR el valor fechaSolicitudSTR a asignar
	 */
	public void setFechaSolicitudSTR(String fechaSolicitudSTR) {
		this.fechaSolicitudSTR = fechaSolicitudSTR;
	}
	
	/**
	 * M&eacute;todo de acceso al campo tipoSolicitud.
	 * @return El valor del campo tipoSolicitud
	 */
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	
	/**
	 * Asigna el valor al campo tipoSolicitud.
	 * @param tipoSolicitud el valor tipoSolicitud a asignar
	 */
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	/**
	 * M&eacute;todo de acceso al campo destinatario.
	 * @return El valor del campo destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}
	
	/**
	 * Asigna el valor al campo destinatario.
	 * @param destinatario el valor destinatario a asignar
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	/**
	 * M&eacute;todo de acceso al campo institucion.
	 * @return El valor del campo institucion
	 */
	public String getInstitucion() {
		return institucion;
	}
	
	/**
	 * Asigna el valor al campo institucion.
	 * @param institucion el valor institucion a asignar
	 */
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
	/**
	 * M&eacute;todo de acceso al campo folioDocumento.
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
	 * M&eacute;todo de acceso al campo fechaCreacion.
	 * @return El valor del campo fechaCreacion
	 */
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	
	/**
	 * Asigna el valor al campo fechaCreacion.
	 * @param fechaCreacion el valor fechaCreacion a asignar
	 */
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	/**
	 * M&eacute;todo de acceso al campo tipoDocumento.
	 * @return El valor del campo tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	
	/**
	 * Asigna el valor al campo tipoDocumento.
	 * @param tipoDocumento el valor tipoDocumento a asignar
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	/**
	 * M&eacute;todo de acceso al campo idDocumento.
	 * @return El valor del campo idDocumento
	 */
	public long getIdDocumento() {
		return idDocumento;
	}
	
	/**
	 * Asigna el valor al campo idDocumento.
	 * @param idDocumento el valor idDocumento a asignar
	 */
	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	
	
}
