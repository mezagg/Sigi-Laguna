/**
* Nombre del Programa : AsociarDocumentoCausaTOCAService.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24/06/2011
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
package mx.gob.segob.nsjp.service.expediente;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public interface AsociarDocumentoCausaTOCAService {

	/**
	    * Operaci�n que realiza la funcionalidad de relacionar el documento con el n�mero de Causa
	    * 
	    * @param documento
	    * @param el n�mero de causa
	    * @return  
	    * @throws NSJPNegocioException
	    */
	void asociarDocumentoCausaTOCA(DocumentoDTO documento,
			ExpedienteDTO causa, ActividadDTO actividadDTO)throws NSJPNegocioException;

}
