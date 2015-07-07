/**
* Nombre del Programa		: MandamientoWSDTO.java
* Autor                     : vaguirre
* Compania                  : Ultrasist
* Proyecto                  : NSJP                    Fecha: 31 Aug 2011
* Marca de cambio        	: N/A
* Descripcion General    	: Describir el objetivo de la clase de manera breve
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion        : N/A
* Dias de ejecucion			: N/A                     Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor						: N/A
* Compania               	: N/A
* Proyecto					: N/A                     Fecha: N/A
* Modificacion				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dto.documento;

import java.util.List;

import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.DelitoPersonaWSDTO;


/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author vaguirre
 *
 */
public class MandamientoWSDTO extends DocumentoWSDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4733272089344023858L;
	
	private Long estatus;
	private Long idTipoMandamiento;
	private String numeroCausa;
	private String numeroCaso;
	private List<DelitoPersonaWSDTO> delitosPersona;
	private List<MandamientoPersonaWSDTO> mandamientosPersona;
	
	
	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * @return El valor del campo estatus
	 */
	public Long getEstatus() {
		return estatus;
	}
	/**
	 * Asigna el valor al campo estatus.
	 * @param estatus a asignar
	 */
	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}
	/**
	 * M&eacute;todo de acceso al campo idTipoMandamiento.
	 * @return El valor del campo idTipoMandamiento
	 */
	public Long getIdTipoMandamiento() {
		return idTipoMandamiento;
	}
	/**
	 * Asigna el valor al campo idTipoMandamiento.
	 * @param idTipoMandamiento a asignar
	 */
	public void setIdTipoMandamiento(Long idTipoMandamiento) {
		this.idTipoMandamiento = idTipoMandamiento;
	}
	/**
	 * M&eacute;todo de acceso al campo numeroCausa.
	 * @return El valor del campo numeroCausa
	 */
	public String getNumeroCausa() {
		return numeroCausa;
	}
	/**
	 * Asigna el valor al campo numeroCausa.
	 * @param numeroCausa a asignar
	 */
	public void setNumeroCausa(String numeroCausa) {
		this.numeroCausa = numeroCausa;
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
	 * @param numeroCaso a asignar
	 */
	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}
	/**
	 * M&eacute;todo de acceso al campo delitosPersona.
	 * @return El valor del campo delitosPersona
	 */
	public List<DelitoPersonaWSDTO> getDelitosPersona() {
		return delitosPersona;
	}
	/**
	 * Asigna el valor al campo delitosPersona.
	 * @param delitosPersona a asignar
	 */
	public void setDelitosPersona(List<DelitoPersonaWSDTO> delitosPersona) {
		this.delitosPersona = delitosPersona;
	}
	/**
	 * M&eacute;todo de acceso al campo mandamientosPersona.
	 * @return El valor del campo mandamientosPersona
	 */
	public List<MandamientoPersonaWSDTO> getMandamientosPersona() {
		return mandamientosPersona;
	}
	/**
	 * Asigna el valor al campo mandamientosPersona.
	 * @param mandamientosPersona a asignar
	 */
	public void setMandamientosPersona(
			List<MandamientoPersonaWSDTO> mandamientosPersona) {
		this.mandamientosPersona = mandamientosPersona;
	}
}
