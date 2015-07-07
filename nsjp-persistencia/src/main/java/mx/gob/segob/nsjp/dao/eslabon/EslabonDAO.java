/**
 * Nombre del Programa : EslabonDAO.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 30-jun-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.eslabon;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Eslabon;
import mx.gob.segob.nsjp.model.Evidencia;

/**
 * 
 * @version 1.0
 * @author Jacob Lobaco
 */
public interface EslabonDAO extends GenericDao<Eslabon, Long>{

    /**
     * Consulta el ultimo eslabon asociado a una evidencia, opcionalmente por tipo de eslabon
     * @param evidencia La evidencia de la que se requiere el ultimo eslabon.
     * @param tipoEslabon Representa el tipo de eslabon
     * @return El ultimo eslabon asociado a una evidencia o {@code null} en caso
     * que la evidencia solicitada no exista o que no tenga eslabones asociados.
     */
	 Eslabon consultaUltimoEslabonXEvidenciaYTipo(Evidencia evidencia, Long tipoEslabon);    
    /**
     * Consulta los eslabones asociados a una evidencia.
     * Se ordenan ascendentemente
     * @param idEvidencia
     * @return
     */
    List<Eslabon> consultaEslabonesPorEvidencia(Long idEvidencia);

    /**
	 * Operaci�n que permite ver los documentos asociados a los eslabones de una evidencia dada
	 * @param evidenciaDTO: idEvidencia
	 * @return
	 */
	List<Documento> consultarDocumentosXEslabonesDEvidencia(Long evidenciaId);

	/**
	 * Operaci�n que permite ver las evidencias que para un estatus tengan fecha caduca
	 * @param identificadorAlmacen
	 * @param valorId
	 * @param date
	 * @return
	 */
	List<Evidencia> consultarEvidenciasNoDevueltas(Long identificadorAlmacen,
			Long estatusEv, Date date);

	/**
	 * Metodo que permite obtener el id del expediente al que esta asociado el
	 * eslabon en base al id del eslabon
	 * @param idEslabon
	 * @return
	 */
	Long obtenIdExpedienteDeUnEslabon(Long idEslabon);

	
}
