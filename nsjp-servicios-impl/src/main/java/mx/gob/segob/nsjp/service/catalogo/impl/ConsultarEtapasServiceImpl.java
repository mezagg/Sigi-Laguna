/**
 *
 * Nombre del Programa : ConsultarEtapasServiceImpl.java                                    
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 21/01/2013 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Contrato de servicio para recuperar la información de las Etapas de -Involucrado -Expediente                     
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.service.catalogo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatEtapaDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.model.CatEtapa;
import mx.gob.segob.nsjp.service.catalogo.ConsultarEtapasService;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatEtapaTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Contrato de servicios para recuperar la información de las Etapas de
 * -Involucrado -Expediente
 *  
 * @author GustavoBP
 * 
 */
@Service
public class ConsultarEtapasServiceImpl implements ConsultarEtapasService {

	private final static Logger logger = Logger
			.getLogger(ConsultarEtapasServiceImpl.class);

	@Autowired
	private CatEtapaDAO catEtapaDAO;

	public List<CatEtapaDTO> consultarEtapas(Boolean esEtapaExpediente)
			throws NSJPNegocioException {

		logger.info("Servicio que consulta las etapas de Expediente o Involucrado:"
				+ esEtapaExpediente);

		List<CatEtapaDTO> etapasDTO = null;

		List<CatEtapa> etapasBD = catEtapaDAO
				.consultarEtapasExpedienteInvolucrado(esEtapaExpediente);
		if (etapasBD != null && !etapasBD.isEmpty()) {
			etapasDTO = new ArrayList<CatEtapaDTO>();
			for (CatEtapa catEtapa : etapasBD) {
				etapasDTO.add(CatEtapaTransformer.transformar(catEtapa));
			}
		}
		logger.info("Etapas:" + etapasDTO);
		return etapasDTO;
	}

	public List<CatEtapaDTO> consultarEtapasJerarquiaPorPadre(Boolean esEtapaExpediente)
			throws NSJPNegocioException {

		logger.info("Servicio que consulta las etapas de Expediente o Involucrado:"
				+ esEtapaExpediente + " - Con jerarquia del padre y sus hijos");

		List<CatEtapaDTO> etapasDTO = null;

		List<CatEtapa> etapasPadreBD = catEtapaDAO
				.consultarEtapasPadresExpedienteInvolucrado(esEtapaExpediente);
		if (etapasPadreBD != null && !etapasPadreBD.isEmpty()) {
			etapasDTO = new ArrayList<CatEtapaDTO>();
			for (CatEtapa catEtapa : etapasPadreBD) {

				CatEtapaDTO etapaPadreDTO = CatEtapaTransformer
						.transformarBasico(catEtapa);

				// Consultar las etapas Hijas del Padre y transformarlas
				List<CatEtapa> etapasHijasBD = catEtapaDAO
						.consultarEtapasHijaExpedienteInvolucradoPorPadre(
								esEtapaExpediente, catEtapa.getCatEtapaId());
				if (etapasHijasBD != null && !etapasHijasBD.isEmpty()) {
					etapaPadreDTO.setEsEtapaPadre(true);
					List<CatEtapaDTO> etapasHijasDTO = new ArrayList<CatEtapaDTO>();
					for (CatEtapa catEtapaHija : etapasHijasBD) {
						CatEtapaDTO etapaHijaDTO = CatEtapaTransformer
								.transformarBasico(catEtapaHija);
						etapasHijasDTO.add(etapaHijaDTO);
					}
					etapaPadreDTO.setEtapasHijas(etapasHijasDTO);
				}
				etapasDTO.add(etapaPadreDTO);
			}
		}
		logger.info("Etapas:" + etapasDTO);
		return etapasDTO;
	}
}
