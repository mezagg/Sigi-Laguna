package mx.gob.segob.nsjp.web.documento.form;

import org.apache.struts.upload.FormFile;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

/**
 * Clase que implementa la forma para registrar una solicitud en la ventanilla de atención 
 * al público de poder judicial
 * 
 * @version 1.0 
 * @author ArmandoCT
 */
public class AdjuntarDocumentoAsociadoAExpedienteForm extends GenericForm {

	private static final long serialVersionUID = 1L;
	
	/** archivo adjunto */
	private FormFile archivoAdjunto;
		
	/** numero expediente id */
	private Long expedienteId;
	
	/** numero expediente id */
	private String numeroExpedienteCadena;
	
	/** tipo */
	private Long tipo;
	
	private long tipoActividad;
	
	private long tipoDocumento;
	

	/**
	 * @return the tipo
	 */
	public Long getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the numeroExpedienteCadena
	 */
	public String getNumeroExpedienteCadena() {
		return numeroExpedienteCadena;
	}

	/**
	 * @param numeroExpedienteCadena the numeroExpedienteCadena to set
	 */
	public void setNumeroExpedienteCadena(String numeroExpedienteCadena) {
		this.numeroExpedienteCadena = numeroExpedienteCadena;
	}

	/**
	 * M&eacute;todo de acceso al campo archivoAdjunto.
	 * @return El valor del campo archivoAdjunto
	 */
	public FormFile getArchivoAdjunto() {
		return archivoAdjunto;
	}

	/**
	 * Asigna el valor al campo archivoAdjunto.
	 * @param archivoAdjunto el valor archivoAdjunto a asignar
	 */
	public void setArchivoAdjunto(FormFile archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}

	/**
	 * M&eacute;todo de acceso al campo numeroExpedienteId.
	 * @return El valor del campo numeroExpedienteId
	 */
	public Long getExpedienteId() {
		return expedienteId;
	}

	/**
	 * Asigna el valor al campo numeroExpedienteId.
	 * @param numeroExpedienteId el valor numeroExpedienteId a asignar
	 */
	public void setExpedienteId(Long expedienteId) {
		this.expedienteId = expedienteId;
	}

	/**
	 * M&eacute;todo de acceso al campo tipoActividad.
	 * @return El valor del campo tipoActividad
	 */
	public long getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * Asigna el valor al campo tipoActividad.
	 * @param tipoActividad el valor tipoActividad a asignar
	 */
	public void setTipoActividad(long tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	/**
	 * M&eacute;todo de acceso al campo tipoDocumento.
	 * @return El valor del campo tipoDocumento
	 */
	public long getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Asigna el valor al campo tipoDocumento.
	 * @param tipoDocumento el valor tipoDocumento a asignar
	 */
	public void setTipoDocumento(long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
