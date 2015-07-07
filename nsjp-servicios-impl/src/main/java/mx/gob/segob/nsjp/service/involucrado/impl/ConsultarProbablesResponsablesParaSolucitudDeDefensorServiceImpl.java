/**
* Nombre del Programa		: ConsultarProbablesResponsablesParaSolucitudDeDefensorServiceImpl.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto                  : NSJP                    Fecha: 31 Oct 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Servicio para obtener probables responsables 
* 							  para enviar la solicitud de defensor
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion        : N/A
* Dias de ejecucion         : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                     :N/A
* Compania               	:N/A
* Proyecto                 	:N/A                                 Fecha: N/A
* Modificacion           	:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.involucrado.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.involucrado.DetencionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Detencion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.service.involucrado.ConsultarProbablesResponsablesParaSolucitudDeDefensorService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;

import org.apache.commons.collections.SetUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlejandroGA
 */

@Service
@Transactional
public class ConsultarProbablesResponsablesParaSolucitudDeDefensorServiceImpl
		implements ConsultarProbablesResponsablesParaSolucitudDeDefensorService {

	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private DetencionDAO detencionDAO;
	
	private static final Logger logger = Logger.getLogger(ConsultarProbablesResponsablesParaSolucitudDeDefensorServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.involucrado.ConsultarProbablesResponsablesParaSolucitudDeDefensorService#consultarProbablesResponsablesParaSolucitudDeDefensor(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public List<InvolucradoDTO> consultarProbablesResponsablesParaSolucitudDeDefensor(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("BIENVENIDO AL SERVICIO PARA CONSULTAR PROBABLES "
					+ "RESPONSABLES CANDIDATOS PARA ENVIAR SOLICITUD DE DEFENSOR");
		}

		if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null
				|| expedienteDTO.getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Involucrado> listaProbablesresponsables = involucradoDAO
				.consultarProbablesResponsablesParaSolucitudDeDefensor(
						expedienteDTO.getExpedienteId(),
						Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), false, true,true);
		
		List<InvolucradoDTO> listaProbablesresponsablesDTO = null;

		if (listaProbablesresponsables != null
				&& !listaProbablesresponsables.isEmpty()) {
			listaProbablesresponsablesDTO = new ArrayList<InvolucradoDTO>();

			for (Involucrado probResp : listaProbablesresponsables) {
				//Detenciones del involucrado
				List<Detencion> detenciones = detencionDAO.consultarDetencionByInvolucrado(probResp.getElementoId());
				if(detenciones!=null && !detenciones.isEmpty()){
					probResp.setDetencions( new HashSet<Detencion>(detenciones));
				}
				
				listaProbablesresponsablesDTO.add(InvolucradoTransformer
						.transformarInvolucrado(probResp));
			}
		}

		return listaProbablesresponsablesDTO;
	}
}
