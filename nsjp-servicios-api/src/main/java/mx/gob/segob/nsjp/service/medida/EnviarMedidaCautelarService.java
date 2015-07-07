/**
 * Nombre del Programa  : EnviarMedidaCautelarService.java
 * Autor                : Daniel Jim�nez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 29 Ago 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio encargado de enviar objetos de tipo, 
 * medida cautelar.
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.medida;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;

public interface EnviarMedidaCautelarService {

	public MedidaCautelarDTO enviarMedidaCautelarInstitucion(Long idMedidaCautelar, Instituciones institucionEnviar) throws NSJPNegocioException;

	/**
	 * Servicio que invoca al cliente para enviar la actualizaci�n del estatus de la Medida Cautelar
	 * 
	 * @param idMedidaCautelar
	 * @param institucionEnviar
	 * @return
	 * @throws NSJPNegocioException
	 */
	public MedidaCautelarDTO actualizarEstatusMedidaCautelarInstitucion(Long idMedidaCautelar, Instituciones institucionEnviar) throws NSJPNegocioException;
	
}
