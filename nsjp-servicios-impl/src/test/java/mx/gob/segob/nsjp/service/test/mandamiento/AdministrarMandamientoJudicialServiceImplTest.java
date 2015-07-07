/**
 * Nombre del Programa		: AdministrarMandamientoJudicialServiceImplTest.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 15/08/2011
 * Marca de cambio			: N/A
 * Descripcion General		: Clase Tests para lo relacionado con mandamiento judicial
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.mandamiento;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.mandamiento.EstatusMandamientoPersona;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.persona.PersonaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author AlejandroGA
 * 
 */
public class AdministrarMandamientoJudicialServiceImplTest extends
		BaseTestServicios<AdministrarMandamientoJudicialService> {

	public void testGenerarMandamientoJudicial() {
		logger.info("PRUEBA PARA EL SERVICIO GENERAR MANDAMIENTO JUDICIAL");
		try {
			
			MandamientoDTO mandamientoPru = new MandamientoDTO();
			UsuarioDTO usuarioPru = new UsuarioDTO();
			FuncionarioDTO funcionarioPru = new FuncionarioDTO(16L);
			List<DelitoPersonaDTO> delitosPersona = new ArrayList<DelitoPersonaDTO>();
			DelitoPersonaDTO delPer = new DelitoPersonaDTO();
			delPer.setDelitoPersonaId(706L);
			delitosPersona.add(delPer);
			String numeroCausa = "00012/CA/2013-PJ-YUC-002";
			
			ResolutivoDTO resolutivoDto = new ResolutivoDTO();
			resolutivoDto.setResolutivoId(346L);
			
			usuarioPru.setFuncionario(funcionarioPru);
			
			mandamientoPru.setTipoMandamiento(new ValorDTO(TipoMandamiento.ORDEN_DE_APREHENSION.getValorId()));
			mandamientoPru.setUsuario(usuarioPru);
			mandamientoPru.setDelitosPersona(delitosPersona);
			mandamientoPru.setResolutivo(resolutivoDto);
			mandamientoPru.setNumeroCausa(numeroCausa);
			
			List<MandamientoPersonaDTO> mandamientosPersona = new ArrayList<MandamientoPersonaDTO>();
			MandamientoPersonaDTO mandamientoPersonaDTO = new MandamientoPersonaDTO();
			mandamientoPersonaDTO.setPersona(new PersonaDTO(4391L));
			mandamientosPersona.add(mandamientoPersonaDTO);
			mandamientoPru.setMandamientosPersona(mandamientosPersona);
			
			MandamientoDTO mandaDTO = service
					.generarMandamientoJudicial(mandamientoPru);
			
			assertNotNull(mandaDTO.getDocumentoId());
			logger.info("Se guardo correctamente el mandamiento");
			logger.info("Madamiento creado:" + mandaDTO.getDocumentoId());

		} catch (NSJPNegocioException e) {
			logger.info("EXCEPCION:" + e.getMessage());
		}
	}
	
	public void testGenerarMandamientoJudicialPg() {
		logger.info("PRUEBA PARA EL SERVICIO GENERAR MANDAMIENTO JUDICIAL  EN PG");
		try {
			
			MandamientoDTO mandamientoPru = new MandamientoDTO();
			
			//Dueño del expediente en PG
			UsuarioDTO usuarioPru = new UsuarioDTO();
			FuncionarioDTO funcionarioPru = new FuncionarioDTO(295L);
			usuarioPru.setFuncionario(funcionarioPru);
			mandamientoPru.setUsuario(usuarioPru);
			
			//Set de expediente 
			mandamientoPru.setExpedienteDTO(new ExpedienteDTO(3793L));
			
			//Delitos Persona
			List<DelitoPersonaDTO> delitosPersona = new ArrayList<DelitoPersonaDTO>();
			DelitoPersonaDTO delPer = new DelitoPersonaDTO();
			delPer.setDelitoPersonaId(1576L);
			delitosPersona.add(delPer);
			mandamientoPru.setDelitosPersona(delitosPersona);
			
			//numero de causa
			String numeroCausa = "00036/CA/2013-PJ-ZAC-002";
			mandamientoPru.setNumeroCausa(numeroCausa);
			
			//Tipo Mandamiento			
			mandamientoPru.setTipoMandamiento(new ValorDTO(TipoMandamiento.ORDEN_DE_APREHENSION.getValorId()));
			
			//Mandamientos persona
			List<MandamientoPersonaDTO> mandamientosPersona = new ArrayList<MandamientoPersonaDTO>();
			MandamientoPersonaDTO mandamientoPersonaDTO = new MandamientoPersonaDTO();
			mandamientoPersonaDTO.setPersona(new PersonaDTO(18612L));
			mandamientoPersonaDTO.setFolioInterInstitucional("PJ/MJ2013-00026");
			mandamientosPersona.add(mandamientoPersonaDTO);
			mandamientoPru.setMandamientosPersona(mandamientosPersona);
			
			//Fecha de creacion
			mandamientoPru.setFechaCreacion(DateUtils.obtener("25/06/2013","18:18"));
			
			//Folio
			mandamientoPru.setFolioDocumento("00014/CA/2013-PJ-YUC-002|25-06-2013|OAP|002");
			
			//ActividadId
			ActividadDTO actividadDTO = new ActividadDTO();
			actividadDTO.setActividadId(Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId());
			mandamientoPru.setActividadDTO(actividadDTO);
			
			//Estatus
			mandamientoPru.setEstatus(new ValorDTO(EstatusMandamiento.NO_ATENDIDO.getValorId()));
			
			//Mandamiento
			mandamientoPru.setJerarquiaOrganizacional(26L);
			
			MandamientoDTO mandaDTO = service
					.generarMandamientoJudicial(mandamientoPru);
			
			assertNotNull(mandaDTO.getDocumentoId());
			logger.info("Se guardo correctamente el mandamiento");
			logger.info("Madamiento creado:" + mandaDTO.getDocumentoId());

		} catch (NSJPNegocioException e) {
			logger.info("EXCEPCION:" + e.getMessage());
		}
	}
	
	public void testConsultarMandamientoPorId() {

		try {
			MandamientoDTO mandamientoDTO = service
					.consultarMandamientoPorId(2625L);

			assertNotNull(mandamientoDTO.getDocumentoId());
			logger.info("SE CONSULTO EL MANDAMIENTO");
			logger.info("MANDAMIENTO_ID:" + mandamientoDTO.getDocumentoId());
			logger.info("FOLIO:" + mandamientoDTO.getFolioDocumento());
			logger.info("EXPEDIENTE ID:"
					+ mandamientoDTO.getExpedienteDTO().getExpedienteId());
			logger.info("CASO:"
					+ mandamientoDTO.getExpedienteDTO().getCasoDTO()
							.getNumeroGeneralCaso());
			logger.info("DELITO_PERSONA:::::::::::::::::::::::::::");
			for (DelitoPersonaDTO delitoPersonaDTO : mandamientoDTO
					.getDelitosPersona()) {
				logger.info("Invo nombre:"
						+ delitoPersonaDTO.getProbableResponsable().getNombreCompleto());
				logger.info("Nombre delito:" + delitoPersonaDTO.getDelito().getCatDelitoDTO().getNombre());
				logger.info("Victima:"+ delitoPersonaDTO.getVictima().getNombreCompleto());
			}
			logger.info("MANDAMIENTOS_PERSONA:"
					+ mandamientoDTO.getMandamientosPersona());
			for (MandamientoPersonaDTO mandamientoPersona : mandamientoDTO
					.getMandamientosPersona()) {
				logger.info("MANDAMIENTO_PERSONA_ID:"
						+ mandamientoPersona.getMandamientoPersonaId());
				logger.info("FECHA:" + mandamientoPersona.getFechaEjecucion());
				logger.info("ELEMENTO_ID:"
						+ mandamientoPersona.getPersona().getElementoId());
			}

		} catch (NSJPNegocioException e) {
			logger.info("EXCEPCION:" + e.getMessage());
		}
	}
	
	public void testConsultarMandamientosPersonaPorFiltro() {

		try {

			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO = new FiltroMandamientoPersonaDTO();
			//filtroMandamientoPersonaDTO.setMandamientoId(2610L);
			filtroMandamientoPersonaDTO.setPersonaId(4400L);
			
			List<MandamientoPersonaDTO> listaMandamientoPersonaDTO = service
					.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);

			assertNotNull(listaMandamientoPersonaDTO);
			logger.info("SE CONSULTARON LOS MANDAMIENTOS PERSONA");
			for (MandamientoPersonaDTO mandamientoPersonaDTO : listaMandamientoPersonaDTO) {
				logger.info("MANDAMIENTO_PERSONA_ID:"
						+ mandamientoPersonaDTO.getMandamientoPersonaId());
				logger.info("NOMBRE:"
						+ mandamientoPersonaDTO.getPersona()
								.getNombreCompleto());
				logger.info("FECHA RESOLUTIVO:"
						+ mandamientoPersonaDTO.getMandamiento()
								.getResolutivo().getTemporizador());
				logger.info("ESTATUS:"
						+ mandamientoPersonaDTO.getEstatus().getValor());
				logger.info("FECHA EJECUCION:"
						+ mandamientoPersonaDTO.getFechaEjecucion());
				logger.info("FECHA EJECUCION STR:"
						+ mandamientoPersonaDTO.getStrFechaEjecucion());
				
				logger.info("ESTATUS ACTUAL:"
						+ mandamientoPersonaDTO.getEstatus().getValor());
				
				/*logger.info("NOMBRE DOCUMENTO ESTATUS ACTUAL:"
						+ mandamientoPersonaDTO.getDocumentoEstatusActual()
								.getDocumento().getNombreDocumento());*/
				
				logger.info("DOCUMENTO ESTATUS DE TRANSICION:::::::::::::");
				for(CatalogoDTO catalogo:mandamientoPersonaDTO.getEstatusTransicion()){
					logger.info(":::::::::::::");
					logger.info("Valor="+catalogo.getValor());
					logger.info("clave="+catalogo.getClave());
				}

			}

		} catch (NSJPNegocioException e) {
			logger.info("EXCEPCION:" + e.getMessage());
		}
	}
	
	public void testActualizarMandamientoPersona(){
		
		//Datos del usuario
		UsuarioDTO usuario = new UsuarioDTO();
		FuncionarioDTO funcionario = new FuncionarioDTO(16L);
		
		usuario.setFuncionario(funcionario);
		AreaDTO areaActual = new AreaDTO();
		areaActual.setAreaId(26L);
		usuario.setAreaActual(areaActual);
		
		//Mandamiento judicial
		MandamientoDTO mandamiento = new MandamientoDTO();
		mandamiento.setDocumentoId(2633L);
		ExpedienteDTO exp = new ExpedienteDTO();
		exp.setExpedienteId(749L);
		mandamiento.setExpedienteDTO(exp);
		
		//Lista mandamientos persona
		List<MandamientoPersonaDTO> listaMandamientoPers = new ArrayList<MandamientoPersonaDTO>();
		
		MandamientoPersonaDTO mandamientoPer_1 = new MandamientoPersonaDTO();
		mandamientoPer_1.setMandamientoPersonaId(20L);
		mandamientoPer_1.setEstatus(new ValorDTO(EstatusMandamientoPersona.EN_PROCESO.getValorId()));
		listaMandamientoPers.add(mandamientoPer_1);
		
		MandamientoPersonaDTO mandamientoPer_2 = new MandamientoPersonaDTO();
		mandamientoPer_2.setMandamientoPersonaId(21L);
		mandamientoPer_2.setEstatus(new ValorDTO(EstatusMandamientoPersona.EN_PROCESO.getValorId()));
		listaMandamientoPers.add(mandamientoPer_2);
		
		MandamientoPersonaDTO mandamientoPer_3 = new MandamientoPersonaDTO();
		mandamientoPer_3.setMandamientoPersonaId(22L);
		mandamientoPer_3.setEstatus(new ValorDTO(EstatusMandamientoPersona.EN_PROCESO.getValorId()));
		listaMandamientoPers.add(mandamientoPer_3);
		
		
		Long documentoId = null;
		
		try {
			documentoId = service.actualizarMandamientoPersona(listaMandamientoPers, usuario, mandamiento,null);
			assertNotNull(documentoId);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
			e.getMessage();
		}
	
		logger.info("EL ID DEL DOCUMENTO GENERADO ES:::::::::"+documentoId);
	}
	
	
	public void testActualizarEstatusMandamiento(){
		
		MandamientoDTO mandamientoDTO = new MandamientoDTO();
		mandamientoDTO.setDocumentoId(2684L);
		//original 6359
		//mandamientoDTO.setEstatus(new ValorDTO(EstatusMandamiento.ATENDIDO.getValorId()));
		Boolean esCalcularEstatus = true;
		
		try {
			
			service.actualizarEstatusMandamiento(mandamientoDTO, esCalcularEstatus);
			Assert.assertNotNull(mandamientoDTO);
			
		} catch (NSJPNegocioException e) {
			
			e.printStackTrace();
		}
	}
	
	public void testEnviarDocumentoCambioEstatusMandamiento(){
		
		MandamientoDTO mandamientoDTO = new MandamientoDTO();
		mandamientoDTO.setDocumentoId(2520L);
		
		try {
			service.enviarDocumentoCambioEstatusMandamiento(mandamientoDTO, null);
			
			assertEquals(true, true);
			
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
