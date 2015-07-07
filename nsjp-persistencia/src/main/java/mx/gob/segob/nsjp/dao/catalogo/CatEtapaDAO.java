/**
 * Nombre del Programa : catEtapaDAO.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del objeto de acceso a datos para el catalogo CatEtapa.
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
package mx.gob.segob.nsjp.dao.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.CatEtapa;

/**
 * Contrato del objeto de acceso a datos para el catalogo CatEtapa.
 * 
 * @author GustavoBP
 */
public interface CatEtapaDAO extends GenericDao<CatEtapa, Long> {

	/**
	 * M&eacute;todo que consulta las etapas del expediente o involucrado,
	 * dependiendo del parametro.
	 * 
	 * @param esEtapaExpediente
	 * @return
	 */
	List<CatEtapa> consultarEtapasExpedienteInvolucrado(
			Boolean esEtapaExpediente);

	/**
	 * M&eacute;todo que consulta las etapas PADRE del expediente o involucrado,
	 * dependiendo del parametro.
	 * 
	 * @param esEtapaExpediente
	 * @return
	 */
	List<CatEtapa> consultarEtapasPadresExpedienteInvolucrado(
			Boolean esEtapaExpediente);

	/**
	 * M&eacute;todo que consulta las etapas HIJAS, del expediente o
	 * involucrado, dependiendo del parametro y del identificador del padre.
	 * 
	 * @param esEtapaExpediente
	 * @param catEtapaPadreId
	 * @return
	 */
	List<CatEtapa> consultarEtapasHijaExpedienteInvolucradoPorPadre(
			Boolean esEtapaExpediente, Long catEtapaPadreId);

	/**
	 * M&eacute;todo que consulta las etapas por filtro de la entidad.
	 * 
	 * @param filtro
	 *            Entidad con los datos para el filtro de busqueda
	 * @return Lista de resultados
	 */
	List<CatEtapa> consultarEtapaPorFiltro(CatEtapa filtro);

	/**
	 * M&eacute;todo para consultar la etapa inicial de acuerdo a la
	 * instituci&oacute;n
	 * 
	 * @param confInstitucionId
	 * @return
	 */
	CatEtapa consultarEtapaInicialPorInstitucion(Long confInstitucionId);
	
}
