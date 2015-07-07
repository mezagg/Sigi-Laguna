/**
* Nombre del Programa 		: IngresarSentenciaForm.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 08/11/2012
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

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.web.base.form.GenericForm;
import java.util.List;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class IngresarSentenciaForm extends GenericForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8078341177364939977L;

	private String nus;
	private String sentenciado;
	private boolean lesionado;
	private String caso;
	private String causa;
	private String delitos;
	private String fechaInicioPena;
	private String fechaFinPena;
	private boolean aplicaMulta;
	private boolean reparacionDanio;
	private String duracionPena;
	private int puntosTotales;
	private List<ValorDTO> estatusSentencia;
	private List<SentenciaDTO> sentenciasNUS;
	private long idEstatus;
	private long expedienteId;
	private List<ValorDTO> lstTiposDocumento;
	private long involucradoId;
	private long numeroExpedienteId;
	private String computoActual;
	private float montoMulta;
	private boolean multaPagada;
	private float montoDanioReparado;
	private boolean reparacionPagada;
	
	
	/**
	 * M&eacute;todo de acceso al campo nus.
	 * @return El valor del campo nus
	 */
	public String getNus() {
		return nus;
	}
	
	/**
	 * Asigna el valor al campo nus.
	 * @param nus el valor nus a asignar
	 */
	public void setNus(String nus) {
		this.nus = nus;
	}
	
	/**
	 * M&eacute;todo de acceso al campo sentenciado.
	 * @return El valor del campo sentenciado
	 */
	public String getSentenciado() {
		return sentenciado;
	}
	
	/**
	 * Asigna el valor al campo sentenciado.
	 * @param sentenciado el valor sentenciado a asignar
	 */
	public void setSentenciado(String sentenciado) {
		this.sentenciado = sentenciado;
	}
	
	/**
	 * M&eacute;todo de acceso al campo lesionado.
	 * @return El valor del campo lesionado
	 */
	public boolean getLesionado() {
		return lesionado;
	}
	
	/**
	 * Asigna el valor al campo lesionado.
	 * @param lesionado el valor lesionado a asignar
	 */
	public void setLesionado(boolean lesionado) {
		this.lesionado = lesionado;
	}
	
	/**
	 * M&eacute;todo de acceso al campo caso.
	 * @return El valor del campo caso
	 */
	public String getCaso() {
		return caso;
	}
	
	/**
	 * Asigna el valor al campo caso.
	 * @param caso el valor caso a asignar
	 */
	public void setCaso(String caso) {
		this.caso = caso;
	}
	
	/**
	 * M&eacute;todo de acceso al campo causa.
	 * @return El valor del campo causa
	 */
	public String getCausa() {
		return causa;
	}
	
	/**
	 * Asigna el valor al campo causa.
	 * @param causa el valor causa a asignar
	 */
	public void setCausa(String causa) {
		this.causa = causa;
	}
	
	/**
	 * M&eacute;todo de acceso al campo delitos.
	 * @return El valor del campo delitos
	 */
	public String getDelitos() {
		return delitos;
	}
	
	/**
	 * Asigna el valor al campo delitos.
	 * @param delitos el valor delitos a asignar
	 */
	public void setDelitos(String delitos) {
		this.delitos = delitos;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaInicioPena.
	 * @return El valor del campo fechaInicioPena
	 */
	public String getFechaInicioPena() {
		return fechaInicioPena;
	}
	
	/**
	 * Asigna el valor al campo fechaInicioPena.
	 * @param fechaInicioPena el valor fechaInicioPena a asignar
	 */
	public void setFechaInicioPena(String fechaInicioPena) {
		this.fechaInicioPena = fechaInicioPena;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaFinPena.
	 * @return El valor del campo fechaFinPena
	 */
	public String getFechaFinPena() {
		return fechaFinPena;
	}
	
	/**
	 * Asigna el valor al campo fechaFinPena.
	 * @param fechaFinPena el valor fechaFinPena a asignar
	 */
	public void setFechaFinPena(String fechaFinPena) {
		this.fechaFinPena = fechaFinPena;
	}
	
	/**
	 * M&eacute;todo de acceso al campo aplicaMulta.
	 * @return El valor del campo aplicaMulta
	 */
	public boolean isAplicaMulta() {
		return aplicaMulta;
	}
	
	/**
	 * Asigna el valor al campo aplicaMulta.
	 * @param aplicaMulta el valor aplicaMulta a asignar
	 */
	public void setAplicaMulta(boolean aplicaMulta) {
		this.aplicaMulta = aplicaMulta;
	}
	
	/**
	 * M&eacute;todo de acceso al campo reparacionDanio.
	 * @return El valor del campo reparacionDanio
	 */
	public boolean isReparacionDanio() {
		return reparacionDanio;
	}
	
	/**
	 * Asigna el valor al campo reparacionDanio.
	 * @param reparacionDanio el valor reparacionDanio a asignar
	 */
	public void setReparacionDanio(boolean reparacionDanio) {
		this.reparacionDanio = reparacionDanio;
	}
	
	/**
	 * M&eacute;todo de acceso al campo duracionPena.
	 * @return El valor del campo duracionPena
	 */
	public String getDuracionPena() {
		return duracionPena;
	}
	
	/**
	 * Asigna el valor al campo duracionPena.
	 * @param duracionPena el valor duracionPena a asignar
	 */
	public void setDuracionPena(String duracionPena) {
		this.duracionPena = duracionPena;
	}
	
	/**
	 * M&eacute;todo de acceso al campo puntosTotales.
	 * @return El valor del campo puntosTotales
	 */
	public int getPuntosTotales() {
		return puntosTotales;
	}
	
	/**
	 * Asigna el valor al campo puntosTotales.
	 * @param puntosTotales el valor puntosTotales a asignar
	 */
	public void setPuntosTotales(int puntosTotales) {
		this.puntosTotales = puntosTotales;
	}

	/**
	 * M&eacute;todo de acceso al campo estatusSentencia.
	 * @return El valor del campo estatusSentencia
	 */
	public List<ValorDTO> getEstatusSentencia() {
		return estatusSentencia;
	}

	/**
	 * Asigna el valor al campo estatusSentencia.
	 * @param estatusSentencia el valor estatusSentencia a asignar
	 */
	public void setEstatusSentencia(List<ValorDTO> estatusSentencia) {
		this.estatusSentencia = estatusSentencia;
	}

	/**
	 * M&eacute;todo de acceso al campo sentenciasNUS.
	 * @return El valor del campo sentenciasNUS
	 */
	public List<SentenciaDTO> getSentenciasNUS() {
		return sentenciasNUS;
	}

	/**
	 * Asigna el valor al campo sentenciasNUS.
	 * @param sentenciasNUS el valor sentenciasNUS a asignar
	 */
	public void setSentenciasNUS(List<SentenciaDTO> sentenciasNUS) {
		this.sentenciasNUS = sentenciasNUS;
	}

	/**
	 * M&eacute;todo de acceso al campo idEstatus.
	 * @return El valor del campo idEstatus
	 */
	public long getIdEstatus() {
		return idEstatus;
	}

	/**
	 * Asigna el valor al campo idEstatus.
	 * @param idEstatus el valor idEstatus a asignar
	 */
	public void setIdEstatus(long idEstatus) {
		this.idEstatus = idEstatus;
	}

	/**
	 * M&eacute;todo de acceso al campo expedienteId.
	 * @return El valor del campo expedienteId
	 */
	public long getExpedienteId() {
		return expedienteId;
	}

	/**
	 * Asigna el valor al campo expedienteId.
	 * @param expedienteId el valor expedienteId a asignar
	 */
	public void setExpedienteId(long expedienteId) {
		this.expedienteId = expedienteId;
	}

	/**
	 * M&eacute;todo de acceso al campo lstTiposDocumento.
	 * @return El valor del campo lstTiposDocumento
	 */
	public List<ValorDTO> getLstTiposDocumento() {
		return lstTiposDocumento;
	}

	/**
	 * Asigna el valor al campo lstTiposDocumento.
	 * @param lstTiposDocumento el valor lstTiposDocumento a asignar
	 */
	public void setLstTiposDocumento(List<ValorDTO> lstTiposDocumento) {
		this.lstTiposDocumento = lstTiposDocumento;
	}

	/**
	 * M&eacute;todo de acceso al campo involucradoId.
	 * @return El valor del campo involucradoId
	 */
	public long getInvolucradoId() {
		return involucradoId;
	}

	/**
	 * Asigna el valor al campo involucradoId.
	 * @param involucradoId el valor involucradoId a asignar
	 */
	public void setInvolucradoId(long involucradoId) {
		this.involucradoId = involucradoId;
	}

	/**
	 * M&eacute;todo de acceso al campo numeroExpedienteId.
	 * @return El valor del campo numeroExpedienteId
	 */
	public long getNumeroExpedienteId() {
		return numeroExpedienteId;
	}

	/**
	 * Asigna el valor al campo numeroExpedienteId.
	 * @param numeroExpedienteId el valor numeroExpedienteId a asignar
	 */
	public void setNumeroExpedienteId(long numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}

	/**
	 * M&eacute;todo de acceso al campo computoActual.
	 * @return El valor del campo computoActual
	 */
	public String getComputoActual() {
		return computoActual;
	}

	/**
	 * Asigna el valor al campo computoActual.
	 * @param computoActual el valor computoActual a asignar
	 */
	public void setComputoActual(String computoActual) {
		this.computoActual = computoActual;
	}

	/**
	 * M&eacute;todo de acceso al campo montoMulta.
	 * @return El valor del campo montoMulta
	 */
	public float getMontoMulta() {
		return montoMulta;
	}

	/**
	 * Asigna el valor al campo montoMulta.
	 * @param montoMulta el valor montoMulta a asignar
	 */
	public void setMontoMulta(float montoMulta) {
		this.montoMulta = montoMulta;
	}

	/**
	 * M&eacute;todo de acceso al campo multaPagada.
	 * @return El valor del campo multaPagada
	 */
	public boolean isMultaPagada() {
		return multaPagada;
	}

	/**
	 * Asigna el valor al campo multaPagada.
	 * @param multaPagada el valor multaPagada a asignar
	 */
	public void setMultaPagada(boolean multaPagada) {
		this.multaPagada = multaPagada;
	}

	/**
	 * M&eacute;todo de acceso al campo montoDanioReparado.
	 * @return El valor del campo montoDanioReparado
	 */
	public float getMontoDanioReparado() {
		return montoDanioReparado;
	}

	/**
	 * Asigna el valor al campo montoDanioReparado.
	 * @param montoDanioReparado el valor montoDanioReparado a asignar
	 */
	public void setMontoDanioReparado(float montoDanioReparado) {
		this.montoDanioReparado = montoDanioReparado;
	}

	/**
	 * M&eacute;todo de acceso al campo reparacionPagada.
	 * @return El valor del campo reparacionPagada
	 */
	public boolean isReparacionPagada() {
		return reparacionPagada;
	}

	/**
	 * Asigna el valor al campo reparacionPagada.
	 * @param reparacionPagada el valor reparacionPagada a asignar
	 */
	public void setReparacionPagada(boolean reparacionPagada) {
		this.reparacionPagada = reparacionPagada;
	}
}
