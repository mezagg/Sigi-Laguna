/**
 * 
 */
package mx.gob.segob.nsjp.service.test.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.service.catalogo.ConsultarEtapasService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author GustavoBP
 * 
 */
public class ConsultarEtapasServiceImplTest extends
		BaseTestServicios<ConsultarEtapasService> {

	public void testConsultarEtapas() {

		try {
			Boolean esEtapaExpediente = null;
			List<CatEtapaDTO> etapasDTO = service
					.consultarEtapas(esEtapaExpediente);
			assertNotNull("La lista no puede ser vacia", etapasDTO);

			this.imprimirCatEtapas(etapasDTO);
		} catch (NSJPNegocioException e) {
			fail(e.getMessage());
		}

	}

	public void testConsultarEtapasPorPadre() {

		try {
			Boolean esEtapaExpediente = null;
			List<CatEtapaDTO> etapasDTO = service
					.consultarEtapasJerarquiaPorPadre(esEtapaExpediente);
			assertNotNull("La lista no puede ser vacia", etapasDTO);

			this.imprimirCatEtapas(etapasDTO);
		} catch (NSJPNegocioException e) {
			fail(e.getMessage());
		}
	}

	
	private void imprimirCatEtapas(List<CatEtapaDTO> catEtapasDTO) {
		if (catEtapasDTO != null && !catEtapasDTO.isEmpty()) {
			for (CatEtapaDTO etapa : catEtapasDTO) {
				logger.info("\n\n***** ETAPA *****");
				this.imprimirCatEtapa(etapa);
			}
		}
	}

	private void imprimirCatEtapa(CatEtapaDTO catEtapaDTO) {
		if (catEtapaDTO == null)
			return;
		logger.info("Etapa ID:" + catEtapaDTO.getCatEtapaId());
		logger.info("Etapa - esEtapaExpediente:"
				+ catEtapaDTO.getEsEtapaExpediente());

		logger.info("Etapa Valor:" + catEtapaDTO.getCatEtapaValor());
		if (catEtapaDTO.getCatEtapaValor() != null) {
			logger.info("Etapa:" + catEtapaDTO.getCatEtapaValor().getValor());
			logger.info("Etapa:" + catEtapaDTO.getCatEtapaValor().getIdCampo());
		}
		
		this.imprimirCatEtapaHijas(catEtapaDTO);
//		logger.info("Etapa - Padre:" + catEtapaDTO.getCatEtapaPadre());
//		if (catEtapaDTO.getCatEtapaPadre() != null) {
//			logger.info("Etapa - ***Padre:" + catEtapaDTO.getCatEtapaPadre());
//			this.imprimirCatEtapa(catEtapaDTO.getCatEtapaPadre());
//			logger.info("Etapa - Padre***:" + catEtapaDTO.getCatEtapaPadre());
//		}

	}
	
	private void imprimirCatEtapaHijas(CatEtapaDTO catEtapaDTO) {
		if (catEtapaDTO == null)
			return;
		logger.info("Etapa:" + catEtapaDTO.getCatEtapaId());
		logger.info("Etapa - esEtapaExpediente:"
				+ catEtapaDTO.getEsEtapaExpediente());

		logger.info("Etapa Valor:" + catEtapaDTO.getCatEtapaValor());
		if (catEtapaDTO.getCatEtapaValor() != null) {
			logger.info("Etapa:" + catEtapaDTO.getCatEtapaValor().getValor());
			logger.info("Etapa:" + catEtapaDTO.getCatEtapaValor().getIdCampo());
		}
		logger.info("Etapa - HIJA:" + catEtapaDTO.getEtapasHijas());
		if (catEtapaDTO.getEtapasHijas() != null && !catEtapaDTO.getEtapasHijas().isEmpty()) {
			for (CatEtapaDTO etapaHijaDTO : catEtapaDTO.getEtapasHijas()) {
				logger.info("Etapa - HIJA DE:" + catEtapaDTO.getCatEtapaId());
				this.imprimirCatEtapa(etapaHijaDTO);
			}
		}
	}
	
}
