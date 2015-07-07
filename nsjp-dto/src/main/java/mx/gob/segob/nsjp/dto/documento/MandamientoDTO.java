/**
 * Nombre del Programa		: MandatoDTO.java
 * Autor                    : Emigdio
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha: 24/08/2011
 * Marca de cambio        	: N/A
 * Descripcion General    	: Describir el objetivo de la clase de manera breve
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion		: N/A
 * Dias de ejecucion		: N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania					: N/A
 * Proyecto                 : N/A						Fecha: N/A
 * Modificacion				: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.documento;

import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;

/**
 * DTO para el mandato judicial
 * @version 1.0
 * @author Emigdio
 * 
 */
public class MandamientoDTO extends DocumentoDTO {

	/**
	 * Campos del mandamiento
	 */
	private static final long serialVersionUID = 7283897765791407211L;

	private ResolutivoDTO resolutivo;
	private ValorDTO tipoMandamiento;
	private ValorDTO estatus;
	private String numeroCausa;
	private List<DelitoPersonaDTO> delitosPersona;
	private List<MandamientoPersonaDTO> mandamientosPersona;

	

	/**
	 * M&eacute;todo de acceso al campo resolutivo.
	 * 
	 * @return El valor del campo resolutivo
	 */
	public ResolutivoDTO getResolutivo() {
		return resolutivo;
	}

	/**
	 * Asigna el valor al campo resolutivo.
	 * 
	 * @param resolutivo
	 *            el valor resolutivo a asignar
	 */
	public void setResolutivo(ResolutivoDTO resolutivo) {
		this.resolutivo = resolutivo;
	}

	/**
	 * M&eacute;todo de acceso al campo tipoMandamiento.
	 * 
	 * @return El valor del campo tipoMandamiento
	 */
	public ValorDTO getTipoMandamiento() {
		return tipoMandamiento;
	}

	/**
	 * Asigna el valor al campo tipoMandamiento.
	 * 
	 * @param tipoMandamiento
	 *            el valor tipoMandamiento a asignar
	 */
	public void setTipoMandamiento(ValorDTO tipoMandamiento) {
		this.tipoMandamiento = tipoMandamiento;
	}

	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * 
	 * @return El valor del campo estatus
	 */
	public ValorDTO getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el valor al campo estatus.
	 * 
	 * @param estatus
	 *            el valor estatus a asignar
	 */
	public void setEstatus(ValorDTO estatus) {
		this.estatus = estatus;
	}

	/**
	 * M&eacute;todo de acceso al campo numeroCausa.
	 * 
	 * @return El valor del campo numeroCausa
	 */
	public String getNumeroCausa() {
		return numeroCausa;
	}

	/**
	 * Asigna el valor al campo numeroCausa.
	 * 
	 * @param numeroCausa
	 *            el valor numeroCausa a asignar
	 */
	public void setNumeroCausa(String numeroCausa) {
		this.numeroCausa = numeroCausa;
	}

	/**
	 * M&eacute;todo de acceso al campo delitosPersona.
	 * 
	 * @return El valor del campo delitosPersona
	 */
	public List<DelitoPersonaDTO> getDelitosPersona() {
		return delitosPersona;
	}

	/**
	 * Asigna el valor al campo delitosPersona.
	 * 
	 * @param delitosPersona
	 *            el valor delitosPersona a asignar
	 */
	public void setDelitosPersona(List<DelitoPersonaDTO> delitosPersona) {
		this.delitosPersona = delitosPersona;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientosPersona.
	 * 
	 * @return El valor del campo mandamientosPersona
	 */
	public List<MandamientoPersonaDTO> getMandamientosPersona() {
		return mandamientosPersona;
	}

	/**
	 * Asigna el valor al campo mandamientosPersona.
	 * 
	 * @param mandamientosPersona
	 *            a asignar
	 */
	public void setMandamientosPersona(
			List<MandamientoPersonaDTO> mandamientosPersona) {
		this.mandamientosPersona = mandamientosPersona;
	}
}
