/**
 * Nombre del Programa : ClienteGeneralServiceImplTest.java
 * Autor                            : rgama
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 05-Sep-2011
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
package mx.gob.segob.nsjp.service.test.infra;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDocumentoDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.ConsultarArchivosDigitalesService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.mandamiento.impl.MandamientoPersonaTransformer;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @version 1.0
 * @author rgama
 */
public class ClienteGeneralServiceImplTest
    extends BaseTestServicios<ClienteGeneralService> {
	
	@Autowired
	ConsultarArchivosDigitalesService consultarArchivosDigitalesService;
	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private MandamientoPersonaDocumentoDAO mandamientoPersonaDocumentoDAO; 

    public void testConsultarFuncionariosXTribunal(){
		
		try {	
			List<FuncionarioDTO> loFuncionarios = service.consultarFuncionariosXTribunal(26L,Instituciones.PJ);
			for (FuncionarioDTO funcionarioDTO : loFuncionarios) {
				System.out.println("Clave" + funcionarioDTO.getClaveFuncionario());
				System.out.println("Nombre" + funcionarioDTO.getNombreFuncionario() + " " + funcionarioDTO.getApellidoPaternoFuncionario() + " "
						+ funcionarioDTO.getApellidoMaternoFuncionario());
			}
			System.out.println("Total:" + loFuncionarios.size());

		} catch (NSJPNegocioException e) {
			logger.debug(e.getMessage(), e);
			fail("Ocurrio una excepcion al ejecutar el test PJClienteService - testEnviarDocumentoIncumplimientoMedidaPJ");
		} 
    }
    
    public void testConsultarTribunalesPorDistrito() {
		List<CatDiscriminanteDTO> discriminantesDTO = new ArrayList<CatDiscriminanteDTO>();
		try {
            logger.info("Probando el servicio de: consultarTribunalesPorDistrito");

            Long distritoId = 1L;
            Instituciones target = Instituciones.PGJ;
            
            discriminantesDTO = service.consultarTribunalesPorDistrito(distritoId, target);
            
            for (CatDiscriminanteDTO catDiscriminanteDTO : discriminantesDTO) {
            	logger.info("----------------------");
				logger.info("ID: "+catDiscriminanteDTO.getCatDiscriminanteId());
				logger.info("Clave: "+catDiscriminanteDTO.getClave());
				logger.info("Nombre: "+catDiscriminanteDTO.getNombre());
			}
            assertNotNull("discriminantesDTO", discriminantesDTO);
        } catch (NSJPNegocioException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            fail("Ocurrio una excepcion al ejecutar el test PJClienteService");
        }
	}
   
    
    public void testConsultarAgenciasPorDistrito() {
		List<CatDiscriminanteDTO> discriminantesDTO = new ArrayList<CatDiscriminanteDTO>();
		try {
            logger.info("Probando el servicio de: testConsultarAgenciasPorDistrito");

            Long distritoId = 1L;
            Instituciones target = Instituciones.PGJ;
            
            discriminantesDTO = null; //service.consultarAgenciasPorDistrito(distritoId, target);
            
            for (CatDiscriminanteDTO catDiscriminanteDTO : discriminantesDTO) {
            	logger.info("----------------------");
				logger.info("ID: "+catDiscriminanteDTO.getCatDiscriminanteId());
				logger.info("Clave: "+catDiscriminanteDTO.getClave());
				logger.info("Nombre: "+catDiscriminanteDTO.getNombre());
}
            assertNotNull("discriminantesDTO", discriminantesDTO);
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            fail("Ocurrio una excepcion al ejecutar el test ClienteGeneralService");
        }
	}
    
    /**
     * M&eacute;todo de prueba para consultar los funcionarios externos a 
     * trav&eacute;s de un web service.
     */
    public void testConsultarFuncionariosXCriterio(){
    	CriterioConsultaFuncionarioExternoDTO criterio = new CriterioConsultaFuncionarioExternoDTO();
		//Rol
		RolDTO rol = new RolDTO();
		rol.setNombreRol("cer");
		criterio.setRolDTO(rol);
		
		//Numero de expediente y n&uacute;mero de caso
		ExpedienteDTO expediente = new ExpedienteDTO();
		expediente.setNumeroExpediente("NSJYUCFG01001201233333");
		CasoDTO caso = new CasoDTO();
		caso.setNumeroGeneralCaso("YUC/FG/XX/PGE/2012/AA-00000");
		expediente.setCasoDTO(caso);
		criterio.setExpendienteDTO(expediente);
		
		//Discriminante
		FuncionarioDTO funcionario = new FuncionarioDTO();
		CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
		discriminante.setNombre("Sin agencia ni tribunal");
		funcionario.setDiscriminante(discriminante);
		criterio.setFuncionarioDTO(funcionario);
		try {
			List<FuncionarioDTO> funcionarios = service.consultarFuncionariosXCriterio(criterio, Instituciones.RS);
			if (funcionarios != null && !funcionarios.isEmpty()){
				for (FuncionarioDTO func : funcionarios) {
					logger.info("------------------------------------------");
					logger.info("Clave :"+func.getClaveFuncionario());
					logger.info("Nombre :"+func.getNombreFuncionario()+" "+func.getApellidoPaternoFuncionario()+" "+func.getApellidoMaternoFuncionario());
					logger.info("Area :"+func.getJerarquiaOrganizacional().getNombre());
					logger.info("Puesto :"+func.getPuesto().getValor());
					logger.info("Email :"+func.getEmail());
				}
				logger.info("------------------------------------------");
				logger.info("Total de funcionarios: " +funcionarios.size());
			}else{
				System.out.println("No se regresaron resultados de la consulta");
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
    }

    
    public void testEnviarSentencia() {
    	Boolean resultado = Boolean.FALSE;
		SentenciaDTO sentenciaDTO = new SentenciaDTO();
		sentenciaDTO.setSentenciaId(1L);
		sentenciaDTO.setBcumplida(false);
		sentenciaDTO.setBlesionado(false);
		sentenciaDTO.setCnumeroCausa("123");
		sentenciaDTO.setCnus("1");
		sentenciaDTO.setDfechaFinPena(new Date());
		sentenciaDTO.setDfechaInicioPena(new Date());
		sentenciaDTO.setIpuntosPorAcumular(10L);
		sentenciaDTO.setSentenciaId(1L);
		sentenciaDTO.setValorDTO(new ValorDTO(1L));
		
		try {
            logger.info("Probando el servicio de: testConsultarAgenciasPorDistrito");
            Instituciones institucion = Instituciones.RS;
            
            resultado = service.enviarSentencia(sentenciaDTO, institucion);
            

            assertTrue("El servicio funciona", resultado);
        } catch (NSJPNegocioException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            fail("Ocurrio una excepcion al ejecutar el test ClienteGeneralService");
        }
	}
    
	public void tetsEnviarDocumentoAInstitucion() {

		ArrayList<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();

		try {

			DocumentoDTO document = new DocumentoDTO();

			document.setDocumentoId(1206L);
			ActividadDTO actividadDTO = new ActividadDTO();

			actividadDTO
					.setNombre("Generar documento de cambio de estatus de medida cautelar");
			actividadDTO
					.setTipoActividad(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR);
			actividadDTO
					.setActividadId(ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR
							.getValorId());
			document.setActividadDTO(actividadDTO);

			ArchivoDigitalDTO archivoDigitalDTO = new ArchivoDigitalDTO();
			archivoDigitalDTO = consultarArchivosDigitalesService
					.consultarArchivoDigitalCompleto(1206L);
			document.setArchivoDigital(archivoDigitalDTO);

			lstDocumentoDTO.add(document);

			service.enviarDocumentoAInstitucion(lstDocumentoDTO,
					"ZAC/FG/XX/PGU/2012/AA-00167", Instituciones.PGJ);

		} catch (NSJPNegocioException e) {

			e.printStackTrace();
		}
	}
    
    public void testActualizarEstatusMandamientoInstitucion(){
    	Mandamiento loMandamiento = new Mandamiento();
    	loMandamiento.setFolioDocumento("PJ/201200520");
    	loMandamiento.setEstatus(new Valor(EstatusMandamiento.EN_PROCESO.getValorId()));
    	try {
			service.actualizarEstatusMandamientoInstitucion(loMandamiento, Instituciones.PGJ);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
    }
    
	public void testEnviarAcuseDeReciboDeSolicitudDeDefensor() {

		try {
			SolicitudDefensorDTO solicitdDTOtoSend = new SolicitudDefensorDTO();
			DocumentoDTO documentoDTOtoSend = new DocumentoDTO();

			solicitdDTOtoSend.setFolioSolicitud("FG/201200210");

			// Datos de la actividad
			ActividadDTO actividadDTO = new ActividadDTO();
			actividadDTO
					.setTipoActividad(Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO);

			ArchivoDigitalDTO archivoDigitalDTO = consultarArchivosDigitalesService
					.consultarArchivoDigitalCompleto(1800L);

			// Documento
			documentoDTOtoSend.setArchivoDigital(archivoDigitalDTO);
			documentoDTOtoSend.setActividadDTO(actividadDTO);
			
			DocumentoDTO documentoDTORespuesta = service.enviarAcuseDeReciboDeSolicitudDeDefensor(solicitdDTOtoSend, documentoDTOtoSend, Instituciones.PGJ);
			
			if(documentoDTORespuesta != null){
				logger.info("DOCUMENTO RESULTADO DE LA PRUEBA"+documentoDTORespuesta);
				logger.info("DOCUMENTO ID"+documentoDTORespuesta.getDocumentoId());				
			}else{
				logger.info("ERROR.....ERROR....ALGO FALLO");
			}

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testEnviarActualizacionMandamientoInterInstitucional() {

		try {

			Documento documento = documentoDAO.read(2777L);

			DocumentoDTO documentoDTO = DocumentoTransformer
					.transformarDatosLista(documento);

			MandamientoDTO MandamientoDTOSend = new MandamientoDTO();

			MandamientoDTOSend.setDocumentoId(2776L);
			MandamientoDTOSend
					.setFolioDocumento("00019/CA/2013-PJ-YUC-002|08-07-2013|OTR|003");
			MandamientoDTOSend.setEstatus(new ValorDTO(
					EstatusMandamiento.ACTUALIZACION_NO_ENVIADA.getValorId()));

			/**
			 * Consultamos los mandamientos persona que vamos a enviar de
			 * acuerdo al documento de cambio de estatus obtenido en el paso
			 * anterior o el recibido como parametro
			 */
			List<MandamientoPersona> listaMandamientosPersonaRelacionados = mandamientoPersonaDocumentoDAO
					.consultarMandamientosPersonaPorDocumentoId(documentoDTO
							.getDocumentoId());

			MandamientoDTOSend
					.setMandamientosPersona(MandamientoPersonaTransformer
							.transformar(listaMandamientosPersonaRelacionados));

			service.enviarActualizacionMandamientoInterInstitucional(
					MandamientoDTOSend, documentoDTO, Instituciones.PGJ);

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

	}
    
}
