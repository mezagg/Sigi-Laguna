/**
 * Nombre del Programa : ActividadDAO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 26 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato para el objeto de acceso a datos de la Actividad
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
package mx.gob.segob.nsjp.dao.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Documento;

/**
 * Contrato para el objeto de acceso a datos de la Actividad.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface ActividadDAO extends GenericDao<Actividad, Long> {
    /**
     * Obtiene la actividad actual (la m&aacute;s reciente).
     * 
     * @param idExpediente
     * @return
     */
    Actividad obtenerActividadActual(Long idExpediente);

	Documento consultarDocumentosXExpediente(Long expedienteId, Long tipoDocumento);

	Actividad consultarActividadXExpedienteYDocumento(Long expediente,
			Long documentoId);

	Actividad consultarActividadPorExpedienteIdTipoActividad(Long idExpediente, Long tipoActividad);
	
	/**
	 * Consulta todas las actividades asociadas al expediente (opcional) y de acuerdo al tipo de Actividad (opcional),
	 * se considera si se considera el documento generado o solo la actividad generada. 
	 * 
	 * @param idExpediente
	 * @param idTipoActividades
	 * @param documentoRec
	 * @return
	 */
	List<Actividad> consultarActividadesPorTipoActividadExpedienteId(Long idExpediente, List<Long> idTipoActividades, boolean documentoRec);
	
	/**
	 * Consulta el numero de actividades relacionadas al expediente de acuerdo a l tipo de atencion
	 * @param expedienteId
	 * @param tipoAtencionId
	 * @return
	 */
	Long consultarNumeroActividadesPorTipoAtencionExpedienteId(Long expedienteId,Long tipoAtencionId);
	
	/**
	 * Consulta el valor de la actividad mediante el documentoid
	 * @param documentoId
	 * @return Long actividadVal
	 */
	Long consultarActividadePorDocumentoId(Long documentoId); 
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la Actividad que se encuentra relacionada con un 
	 * Documento; debido a qu&eacute; por restricciones de negocio solamente un Documento se encuentra 
	 * relacionado con una actividad, se env&iacute;a un NonUniqueResultException en tiempo de ejecuci&oacute;n 
	 * si el documento se encuentra relacionado con m&aacute;s de una actividad.
	 * @param documento - El Documento del cual se va a consultar la Actividad asociada.
	 * @return Actividad - La Actividad relacionada con el Documento consultado.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los par&aacute;metros necesarios.
	 */
	public Actividad consultarActividadAsociadaDocumento(Documento documento) throws NSJPNegocioException;
	
	/**
	 * Permite consultar actividad la actividad por filtro
	 * @param actividad
	 * @return Actividad
	 */
	public Actividad consultarActividadPorFiltro(Actividad actividad);

	public void eliminarActividadPorFolioIPHYDocumentoId(Long folioIPH,Long documentoId);

}
