/**
 * Nombre del Programa : DelitoWSDTOTransformer.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15/11/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de transformaci&oacute;n para los Delitos asociados al expediente.
 * 							Transforma de un Delito WSDTO a un Delito DTO y viceversa.
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
package mx.gob.segob.nsjp.service.infra.impl.transform.solicituddefensor;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoWSDTO;

/**
 * Clase de transformaci&oacute;n para los Delitos asociados al expediente.
 * Transforma de un Delito WSDTO a un Delito DTO y viceversa.
 * 
 * @author GustavoBP
 * @version 1.0
 */
public class DelitoWSDTOTransformer {
	
	/**
	 * Transforma un DelitoWSDTO a un objeto DelitoDTO
	 * 
	 * @param delitoWSDTO
	 * @return
	 */
	public static DelitoDTO transformarDelito(DelitoWSDTO delitoWSDTO) {

		DelitoDTO delitoDTO = null;

		if (delitoWSDTO == null) {
			return null;
		}

		delitoDTO = new DelitoDTO();

		if (delitoWSDTO.isEsPrincipal() != null) {
			delitoDTO.setEsPrincipal(delitoWSDTO.isEsPrincipal());
		}
		if (delitoWSDTO.isEsProbable() != null) {
			delitoDTO.setEsProbable(delitoWSDTO.isEsProbable());
		}
		if (delitoWSDTO.getIdCatDelito() != null
				&& delitoWSDTO.getIdCatDelito() > 0) {
			delitoDTO.setCatDelitoDTO(new CatDelitoDTO(delitoWSDTO
					.getIdCatDelito()));
		}
		delitoDTO.setClaveInterInstitucional(delitoWSDTO.getClaveInterInstitucional());
		
		return delitoDTO;
	}

	/**
	 * Transforma una lista de delitosWSDTO a una lista de delitosDTO
	 * @param delitosWSDTO
	 * @return
	 */
	public static List<DelitoDTO> transformaDelitos(
			List<DelitoWSDTO> delitosWSDTO) {
		List<DelitoDTO> delitosDTO = null;
		if (delitosWSDTO == null || delitosWSDTO.isEmpty()) {
			return delitosDTO;
		}
		delitosDTO = new ArrayList<DelitoDTO>();

		for (DelitoWSDTO delitoWSDTO : delitosWSDTO) {
			delitosDTO.add(DelitoWSDTOTransformer
					.transformarDelito(delitoWSDTO));
		}
		return delitosDTO;
	}
}
