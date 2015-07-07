package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.model.Valor;

public class MandamientoDAOImplTest extends BaseTestPersistencia<MandamientoDAO>  {

	public void testConsultarMandamientoPorFiltro() throws ParseException {
		//String numeroExpediente="NSJZACPJ0100220123337R";
		String numeroExpediente= null;
		
		Mandamiento mandamiento=new Mandamiento();
		
		Long estado=EstatusMandamiento.EN_PROCESO.getValorId();		
		mandamiento.setEstatus(new Valor(estado));
		
		SimpleDateFormat loFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicial= loFormat.parse("13/08/2012");
		//mandamiento.setFechaInicial(fechaInicial);		
				
		Date fechaFinal= loFormat.parse("20/08/2012");		
		//mandamiento.setFechaFinal(fechaFinal);		
		
		//List<Mandamiento> Mandamientos = daoServcice.consultarMandamientoPorFiltro(mandamiento, numeroExpediente,null, null, null);
		
//		for (Mandamiento Md :Mandamientos) {
//			logger.info("----------------------");
//			logger.info("Mandamiento_id: " + Md.getDocumentoId());
//		}
//		logger.info("Existen " + Mandamientos.size()+ " Mandamientos");
		
	}
	
	public void testFiltro(){
		UsuarioDTO us = new UsuarioDTO();
		FuncionarioDTO fun = new FuncionarioDTO();
		fun.setClaveFuncionario(16L);
		us.setFuncionario(fun);		
		MandamientoDTO mandamiento = new MandamientoDTO();
		mandamiento.setUsuario(us);
		List<NumeroExpediente> ne =daoServcice.consultarMandamientosJudicialesPorFiltro(mandamiento);
		for (NumeroExpediente numeroExpediente : ne) {
			logger.info(numeroExpediente.getNumeroExpediente());
		}
	}		
	
	public void testMandamientoPorResolutivo(){
		
		Mandamiento mandamiento = new Mandamiento();
		Resolutivo resolutivo = new Resolutivo();
		resolutivo.setResolutivoId(115L);
		try {
			mandamiento=daoServcice.obtenerMandamientoPorResolutivo(resolutivo);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		
		logger.info("mandamientoId="+mandamiento.getDocumentoId());
		if(mandamiento.getArchivoDigital() != null && mandamiento.getArchivoDigital().getArchivoDigitalId() != null){
			logger.info("archivoDigitalId="+mandamiento.getArchivoDigital().getArchivoDigitalId());
		}
		logger.info("resolutivoId="+mandamiento.getResolutivo().getResolutivoId());
		
	}
	
	public void testConsultarDelitosPorMandamiento(){
		Mandamiento man = daoServcice.read(2625L);
		if (man != null
				&& man.getDelitosPersona() != null
				&& !man.getDelitosPersona().isEmpty()){
			logger.info("Numero de delitos persona: "+man.getDelitosPersona().size());
			for (DelitoPersona dp : man.getDelitosPersona()){
				logger.info("Id delitoPersona: "+dp.getDelitoPersonaId());
				logger.info("Id participe: "+dp.getProbableResponsable().getElementoId());
				logger.info("Id de la victima: "+dp.getVictima().getElementoId());
				logger.info("Id del delito: "+dp.getDelito().getDelitoId());
			}
		}
	}
	
	public void testConsultarDocumentosRelacionadosMandamientoPorTipo(){
		try {
			List<Documento> documentos = daoServcice.consultarDocumentosRelacionadosMandamientoPorTipo(5552L, 7638L);
			if (documentos != null
					&& !documentos.isEmpty()){
				for (Documento doc : documentos){
					logger.info("Id del documento: "+doc.getDocumentoId());
					logger.info("Tipo de documento: "+doc.getTipoDocumento().getValorId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public void testGenerarMandamientoJudicial() {

		logger.info("TEST PARA GENERAR UN MANDAMIENTO JUDICIAL:");
		Mandamiento mandamiento = new Mandamiento();
		
		
		/**************************************
		 *Campos para documento 
		 */
		mandamiento.setForma(new Forma(425L));
		mandamiento.setFolioDocumento("PJ/201300001");
		mandamiento.setTipoDocumento(new Valor(TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()));
		mandamiento.setFechaCreacion(new Date());
		mandamiento.setNombreDocumento("Mandamiento Judicial");
		mandamiento.setDescripcion("Descripcion del mandamiento judicial");
		
		
		// Tipo Mandamiento
		mandamiento.setTipoMandamiento(new Valor(TipoMandamiento.ORDEN_DE_CATEO
				.getValorId()));
		// Resolutivo
		Resolutivo resolutivo = new Resolutivo();
		resolutivo.setResolutivoId(346L);
		mandamiento.setResolutivo(resolutivo);
		// estatus
		mandamiento.setEstatus(new Valor(EstatusMandamiento.EN_PROCESO
				.getValorId()));
		// numero de causa
		mandamiento.setNumeroCausa("00012/CA/2013-PJ-YUC-002");
		// Delito Persona
		Set<DelitoPersona> delitosPersona = new HashSet<DelitoPersona>();
		DelitoPersona delitoPersona = new DelitoPersona();
		delitoPersona.setDelitoPersonaId(696L);
		delitosPersona.add(delitoPersona);
		mandamiento.setDelitosPersona(delitosPersona);
		
		//Mandamiento Persona
		Set<MandamientoPersona> mandamientosPersona = new HashSet<MandamientoPersona>();
		MandamientoPersona mandamientoPersona = new MandamientoPersona();
		
		mandamientosPersona.add(mandamientoPersona);
		mandamiento.setMandamientosPersona(mandamientosPersona);

		// Creamos el mandamiento
		daoServcice.create(mandamiento);

		logger.info("MADAMIENTO CREADO:" + mandamiento.getDocumentoId());
	}
	
	
	public void testActualizarMandamientoJudicial() {

		Mandamiento mandamiento = daoServcice.read(2610L);
		logger.info("****MADAMIENTO OBTENIDO****");
		logger.info("mandamientoId=" + mandamiento.getDocumentoId());
		logger.info("ResolutivoId="
				+ mandamiento.getResolutivo().getResolutivoId());
		
		/*logger.info("AudienciaId="
				+ mandamiento.getResolutivo().getAudiencia().getAudienciaId());
		logger.info("ExpedienteId="
				+ mandamiento.getResolutivo().getAudiencia().getExpediente()
						.getExpedienteId());*/
		
		for (DelitoPersona delitoPersona : mandamiento.getDelitosPersona()) {
			logger.info("delitoPersona-folio="
					+ delitoPersona.getFolioDelitoPersona());
			logger.info("delitoPersona-delitoId="
					+ delitoPersona.getDelito().getDelitoId());
			logger.info("delitoPersona-PR_id="
					+ delitoPersona.getProbableResponsable().getElementoId());
			logger.info("delitoPersona-VICTIMA_id="
					+ delitoPersona.getVictima().getElementoId());
		}

		for (MandamientoPersona mandamientoPersona : mandamiento
				.getMandamientosPersona()) {
			logger.info("mandamientoPersona_ID="
					+ mandamientoPersona.getMandamientoPersonaId());
			/*logger.info("mandamientoAsociado_ID="
					+ mandamientoPersona.getMandamiento().getDocumentoId());
			logger.info("mandamientoPersona-folioInter="
					+ mandamientoPersona.getFolioInterInstitucional());
			logger.info("mandamientoPersona-Estatus="
					+ mandamientoPersona.getEstatus().getValor());
			/*if (mandamientoPersona.getMandamientosPersonaDocumento() != null
					&& !mandamientoPersona.getMandamientosPersonaDocumento()
							.isEmpty()) {
				logger.info("mandamientoPersona-MandamientoPersonaDocumento_ID="
						+ mandamientoPersona.getMandamientosPersonaDocumento()
								.get(0).getMandamientoPersonaDocumentoId());
			}*/

		}
		
		mandamiento.setEstatus(new Valor(EstatusMandamiento.CANCELADO.getValorId()));
		daoServcice.update(mandamiento);
		

	}

	public void testsConsultarRelacionesDelitoPersonaPorMandamiento() {

		logger.info("****CONSULTAR RELACIONES DELITO PERSONA POR MANDAMIENTO****");
		Boolean respuesta = false;

		respuesta = daoServcice
				.consultarExistenRelacionesDelitoPersonaPorMandamiento(1008L,
						2851L, 706L);

		logger.info("RESPUESTA:" + respuesta);
		assertEquals(new Boolean(true), respuesta);
	}

	public void testObtenerUltimoFolioMandamientoPorCausa() {

		Long ultimoConsecutivo = null;

		ultimoConsecutivo = daoServcice
				.obtenerUltimoFolioMandamientoPorCausa("00012/CA/2013-PJ-YUC-002");

		logger.info("ULTIMO CONSECUTIVO DE MANDAMIENTO:" + ultimoConsecutivo);

		assertEquals(new Long("114"), ultimoConsecutivo);

	}
}


