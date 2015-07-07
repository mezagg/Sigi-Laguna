/**
* Nombre del Programa 		: ObjetoEstudioDAO.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 11 Sep 2012
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
package mx.gob.segob.nsjp.dao.objeto;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ObjetoEstudio;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public interface ObjetoEstudioDAO extends GenericDao<ObjetoEstudio, Long> {
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los distintos estudios periciales
	 * que se encuentran asociados con un tipo de objeto.
	 * @param TipoObj - El tipo de objeto a partir del cual se consultar&aacute;n los 
	 * 					estudios periciales asociados.
	 * @return List<ObjetoEstudio> - Lista con la relaci&oacute;n de objetos y estudios 
	 * 								 asociados al tipo de objeto consultado.
	 */
	public List<ObjetoEstudio> consultarEstudiosPorTipoObjeto (Valor tipoObj);

}
