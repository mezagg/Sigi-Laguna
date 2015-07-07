/**
* Nombre del Programa : GraficaDetencionesPorTipoService.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 31 Aug 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del servicio para obtener la informacion requerida para la 
 * 							grafica Detenciones Por Tipo
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
package mx.gob.segob.nsjp.service.graficacion;

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

/**
 * Contrato del servicio para obtener la informacion requerida para la 
 * grafica Detenciones Por Tipo.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface GraficaDetencionesPorTipoService {

	/**
	 * Obtiene los detenidos por delito y mes, dentro de un rango de fechas
	 * @author cesarAgustin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param long1
	 * @throws NSJPNegocioException
	 * @return
	 */
	Long obtenerDetenidosPorMesYDelito(Date fechaInicio,
			Date fechaFin, Long catDelito) throws NSJPNegocioException;
}
