/**
 * Nombre del Programa : CatEtapaDAOImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Clase para generar pruebas unitarias de los medotos de catEtapaDAO
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
package mx.gob.segob.nsjp.dao.test.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.catalogo.CatEtapaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatEtapa;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Clase para generar pruebas unitarias de los medotos de catEtapaDAO.
 * 
 * @author GustavoBP
 */
public class CatEtapaDAOImplTest extends BaseTestPersistencia<CatEtapaDAO> {

	public void testConsultarEtapasExpedienteInvolucrado() {

		Boolean esEtapaExpediente = null;
		List<CatEtapa> etapas = daoServcice
				.consultarEtapasExpedienteInvolucrado(esEtapaExpediente);

		assertFalse("La lista debe de contener al menos un registro",
				etapas.isEmpty());
		logger.info("Total de registros:" + etapas.size());
		this.imprimirCatEtapas(etapas);
		logger.info("Total de registros:" + etapas.size());
	}

	public void testConsultarEtapasPadresExpedienteInvolucrado() {

		Boolean esEtapaExpediente = true;
		List<CatEtapa> etapas = daoServcice
				.consultarEtapasPadresExpedienteInvolucrado(esEtapaExpediente);

		assertFalse("La lista debe de contener al menos un registro",
				etapas.isEmpty());
		logger.info("Total de registros:" + etapas.size());
		this.imprimirCatEtapas(etapas);
		logger.info("Total de registros:" + etapas.size());
	}

	public void testConsultarEtapasHijoExpedienteInvolucradoPorPadre() {

		Boolean esEtapaExpediente = true;
		Long catEtapaPadreId = 3L;
		List<CatEtapa> etapas = daoServcice
				.consultarEtapasHijaExpedienteInvolucradoPorPadre(
						esEtapaExpediente, catEtapaPadreId);

		assertFalse("La lista debe de contener al menos un registro",
				etapas.isEmpty());
		logger.info("Total de registros:" + etapas.size());
		this.imprimirCatEtapas(etapas);
		logger.info("Total de registros:" + etapas.size());
	}

	public void testConsultarCatEtapaPorFiltro() {

		CatEtapa filtro = new CatEtapa();
		Long valorId = 7581L;
		filtro.setCatEtapaValor(new Valor(valorId));
		
		List<CatEtapa> etapas = daoServcice
				.consultarEtapaPorFiltro(filtro);

		assertFalse("La lista debe de contener al menos un registro",
				etapas.isEmpty());
		logger.info("Total de registros:" + etapas.size());
		this.imprimirCatEtapas(etapas);
		logger.info("Total de registros:" + etapas.size());
	}

	public void testConsultarEtapaInicialPorInstitucion() {
		Long confInstitucionId = 2L; 

		CatEtapa etapa = daoServcice
				.consultarEtapaInicialPorInstitucion(confInstitucionId);

		assertNotNull("No se encontro el elemento", etapa);
		logger.info("Total de registros:" + etapa);
		this.imprimirCatEtapa(etapa);
	}

	/**
	 * M&eacute;todo para imprimir una lista de etapas.
	 * 
	 * @param catEtapas
	 */
	private void imprimirCatEtapas(List<CatEtapa> catEtapas) {
		if (catEtapas != null && !catEtapas.isEmpty()) {
			for (CatEtapa etapa : catEtapas) {
				logger.info("***** ETAPA *****");
				this.imprimirCatEtapa(etapa);
			}
		}
	}

	/**
	 * M&eacute;todo para imprimir los datos de la entidad etapa
	 * 
	 * @param catEtapa
	 */
	private void imprimirCatEtapa(CatEtapa catEtapa) {
		if (catEtapa == null)
			return;
		logger.info("Etapa Id:" + catEtapa.getCatEtapaId());
		logger.info("Etapa esEtapaExpediente:"
				+ catEtapa.getEsEtapaExpediente());
		logger.info("Etapa Valor:" + catEtapa.getCatEtapaValor());
		if (catEtapa.getCatEtapaValor() != null) {
			logger.info("Etapa:" + catEtapa.getCatEtapaValor().getValor());
			logger.info("Etapa:" + catEtapa.getCatEtapaValor().getValorId());
		}
		logger.info("Etapa - Padre:" + catEtapa.getCatEtapaPadre());
		if (catEtapa.getCatEtapaPadre() != null) {
			logger.info("Etapa - ***Padre:" + catEtapa.getCatEtapaPadre());
			this.imprimirCatEtapa(catEtapa.getCatEtapaPadre());
			logger.info("Etapa - Padre***:" + catEtapa.getCatEtapaPadre());
		}

	}

}
