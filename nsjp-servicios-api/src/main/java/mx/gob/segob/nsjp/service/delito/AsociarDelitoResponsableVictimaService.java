/**
* Nombre del Programa : AsociarDelitoResponsableVictimaService.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 07/07/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.service.delito;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public interface AsociarDelitoResponsableVictimaService {

	/**
	 * Realiza la asociaci�n de un delito con el probable responsable y la v�ctima que afecta.
	 * Puede no existir v�ctima a registrar, como se puede no indicar si es "bien Tutelado"
	 * 
	 * @param delito: Identificador del delito
	 * @param responsable: Identificador del involucrado como probable responsable
	 * @param victima: Identificador del involucrado como v�ctima
	 * @param formaParticipacion:
	 * @param bienTutelado:
	 * @throws NSJPNegocioException
	 */
	Long asociarDelitoResponsableVictima(Long asociacionID,DelitoDTO delito,
			InvolucradoDTO responsable, InvolucradoDTO victima,
			Long formaParticipacion, Long bienTutelado, Long idClasificacion, Long idLugar,
			Long idModalidad,Long idModus, Long idCausa)throws NSJPNegocioException;

	/**
	 * Servicio que se encarga de verificar si existe, en BD, la relaci�n
	 * -Con el delito
	 * -El probable responsable
	 * -La v�ctima
	 * -La forma de participaci�n 
	 * 
	 * @param idDelito
	 * @param idProbableResponsable
	 * @param idVictima
	 * @param idFormaParticipacion
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(Long idDelito,
			Long idProbableResponsable, Long idVictima, Long idFormaParticipacion)
			throws NSJPNegocioException;
	
	/**
	 * Realiza la asociaci&oacuten, del los probables responsables con los delitos y las victimas
	 * (sin formas de participacion), si existe una relacion delito persona repetida, NO se guardar&aacute;
	 * dos veces
	 * 
	 * @param idsProbResponsables
	 * @param idsVictimas
	 * @param delito
	 * @return
	 * @throws NSJPNegocioException
	 */
	public void asociarDelitosConIvolucrados(List<Long> idsProbResponsables,
			List<Long> idsVictimas, Long delitoId) throws NSJPNegocioException;
	
	/**
	 *  M&eacute;todo para registrar las formas y grados de participaci&oacute;n,
	 * as&iacute; como los modus, modalidad y lugar; de una o varias relaciones delito-persona
	 * @param idsRelDelPersona
	 * @param delitoPersonaDtoUpdate
	 * @throws NSJPNegocioException
	 */
	public void establecerModosGradosYFormasDeParticipacion(
			List<Long> idsRelDelPersona, DelitoPersonaDTO delitoPersonaDtoUpdate)
			throws NSJPNegocioException;

	
}
