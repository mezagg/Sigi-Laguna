/**
 * Nombre del Programa  : FinalizarAudienciaServieImpl
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 29 Jun 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Finaliza la audiencia y almacena la información de final de audienccia
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
package mx.gob.segob.nsjp.service.audiencia.impl;

import java.util.Calendar;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.documento.AudienciaDocumentoDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.audiencia.FinalizarAudienciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class FinalizarAudienciaServiceImpl implements FinalizarAudienciaService {

	/**
     * Objeto de Acceso a Datos para la entidad Involucrado.
     */
	@Autowired
	private AudienciaDocumentoDAO audienciaDocumentoDAO;
	
	@Autowired
	private AudienciaDAO audienciaDAO;
	
	@Override
	public void finalizarAudienciaService(AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {

		if (audienciaDTO == null || audienciaDTO.getId() == null
				|| audienciaDTO.getId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Audiencia audiencia = audienciaDAO.read(audienciaDTO.getId());
		
		if (audiencia.getEstatus() != null
				&& audiencia.getEstatus().getValorId() != null
				&& audiencia.getEstatus().getValorId()
						.equals(EstatusAudiencia.FINALIZADA.getValorId())) {
			throw new NSJPNegocioException(CodigoError.AUDIENCIA_FINALIZADA);
		}
		
		if (audiencia.getEstatus() != null
				&& audiencia.getEstatus().getValorId() != null
				&& audiencia.getEstatus().getValorId()
						.equals(EstatusAudiencia.CANCELADA.getValorId())) {
			throw new NSJPNegocioException(CodigoError.AUDIENCIA_CANCELADA);
		}
		
		List<Documento> listaDocumentoAudiencia = audienciaDocumentoDAO
				.consultarDocumentosAudiencia(audienciaDTO.getId());

		Boolean finalAudiencia = false;

		for (Documento documentoAudiencia : listaDocumentoAudiencia) {
			if (documentoAudiencia.getActividad() != null
					&& documentoAudiencia.getActividad().getTipoActividad() != null
					&& documentoAudiencia.getActividad().getTipoActividad()
							.getValorId() != null
					&& documentoAudiencia
							.getActividad()
							.getTipoActividad()
							.getValorId()
							.equals(Actividades.GENERAR_ACTA_DE_AUDIENCIA
									.getValorId())) {
				
				if(documentoAudiencia.getArchivoDigital() != null
						&& documentoAudiencia.getArchivoDigital()
						.getArchivoDigitalId() != null
				&& documentoAudiencia.getArchivoDigital()
						.getArchivoDigitalId() > 0L){
					finalAudiencia = true;
				}else{
					throw new NSJPNegocioException(
							CodigoError.ACTA_DE_AUDIENCIA_CON_GUARDADO_PARCIAL);
				}
				break;
			}
		}

		if (finalAudiencia.equals(false)) {
			throw new NSJPNegocioException(
					CodigoError.SIN_ACTA_DE_AUDIENCIA);
		}

		audiencia.setFechaHoraFin(Calendar.getInstance().getTime());
		audiencia
				.setEstatus(new Valor(EstatusAudiencia.FINALIZADA.getValorId()));
		audienciaDAO.saveOrUpdate(audiencia);
	}

}
