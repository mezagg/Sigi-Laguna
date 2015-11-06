/**
 * Nombre del Programa : GuardarDocumentoServiceImpl.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31/05/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci?n del servicio de negocio para almacenar documentos
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
package mx.gob.segob.nsjp.service.documento.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoOficioEstructurado;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.documento.CuerpoOficioEstructuradoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.IndiceEstructuradoDAO;
import mx.gob.segob.nsjp.dao.documento.OficioEstructuradoDAO;
import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.RelacionSolicitudDocumentoFuncionarioDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.BitacoraEstatusNumExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.objeto.ObjetoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudTranscricpionAudienciaDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.CuerpoOficioEstructuradoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;
import mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.BitacoraEstatusNumExpediente;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.CuerpoOficioEstructurado;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.IndiceEstructurado;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.OficioEstructurado;
import mx.gob.segob.nsjp.model.RelacionDocumento;
import mx.gob.segob.nsjp.model.RelacionSolicitudDocumentoFuncionario;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudTranscripcionAudiencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.actividad.AdministradorActividadesService;
import mx.gob.segob.nsjp.service.actividad.ConsultarConfActividadDocumentoService;
import mx.gob.segob.nsjp.service.actividad.RegistrarActividadService;
import mx.gob.segob.nsjp.service.archivo.GuardarArchivoDigitalService;
import mx.gob.segob.nsjp.service.documento.EnviarAcuseDeReciboDeCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.documento.EnviarAcuseDeReciboDeSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.CuerpoOficioEstructuradoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.OficioEstructuradoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.RelacionDocumentoTransformer;
import mx.gob.segob.nsjp.service.expediente.ActualizarEtapaExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ActualizarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.SolicitarDefensorService;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci?n del servicio de negocio para almacenar documentos.
 * 
 * @version 1.0
 * @author Emigdio Hern?ndez
 * 
 */
@Service
public class GuardarDocumentoServiceImpl implements GuardarDocumentoService {
	
	public final static Logger LOGGER = 
		Logger.getLogger(GuardarDocumentoServiceImpl.class);

	@Autowired
	ExpedienteDAO expedienteDAO;	
	@Autowired
	DocumentoDAO documentoDAO;
	@Autowired
	GuardarArchivoDigitalService guardarArchivoDigitalService;
	@Autowired
	ActividadDAO actividadDAO;
	@Autowired
	ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	FuncionarioDAO funcionarioDAO;
	@Autowired
	AdministradorActividadesService administradorActividadesService;
	@Autowired
	SolicitudTranscricpionAudienciaDAO solicitudTranscricpionAudienciaDAO;
	@Autowired
	OficioEstructuradoDAO oficioDAO;
	@Autowired
	CuerpoOficioEstructuradoDAO cuerpoDAO;
	@Autowired
	NumeroExpedienteDAO numExpDAO;
	@Autowired
	FormaDAO formaDAO;
	@Autowired
	IndiceEstructuradoDAO indiceEstructuradoDAO;
	@Autowired
	ConfActividadDocumentoDAO confActividadDocumentoDAO;
	@Autowired
	NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RegistrarActividadService registrarActividadService;
	@Autowired
	private ActualizarExpedienteService actualizarExpedienteService;
    @Autowired
    private ValorDAO valorDAO;
    @Autowired
    private SolicitarDefensorService solicitarDefensorService;
	@Autowired
	private RegistrarSolicitudService registrarSolicitudService;
	@Autowired
	private ConsultarConfActividadDocumentoService consultarConfActividadDocumentoService;
	@Autowired
	private EnviarAcuseDeReciboDeSolicitudDeDefensorService enviarAcuseDeReciboDeSolicitudDeDefensorServiceImpl;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private EnviarAcuseDeReciboDeCarpetaDeInvestigacionService enviarAcuseDeReciboDeCarpetaDeInvestigacionService;
	@Autowired
	private RelacionDocumentoDAO relacionDocumentoDAO;
	@Autowired
	private SolicitudDAO solDao;
	@Autowired
	private ActualizarEtapaExpedienteService actualizarEtapaExpedienteService;
	@Autowired
	private BitacoraEstatusNumExpedienteDAO bitacoraEstatusNumExpedienteDAO;
	@Autowired
	private ActualizarEstatusSolicitudService actualizarEstatusSolicitudService;
	@Autowired
	private RelacionSolicitudDocumentoFuncionarioDAO relacionSolicitudDocumentoFuncionarioDAO;
        @Autowired
        private ObjetoDAO objetoDAO;
	
	/* 
	 * Se publico como transaccional dentro del metodo: 
	 * mx.gob.segob.nsjp.delegate.documento.impl.DocumentoDelegateImpl.guardarDocumento(DocumentoDTO, ExpedienteDTO, Long, Long)
	 * de esta manera se asegura que tanto el guardado definitivo como el guardado transaccional sigan funcionando de manera
	 * correcta.
	 */
	@Override
	public Long guardarDocumento(DocumentoDTO documento,
			ExpedienteDTO expedienteActual, Long nuevaActividad, Long idActividadExistente) throws NSJPNegocioException {

		if(expedienteActual==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Long idDocumentoGen=0L;
		
		if(expedienteActual.getExpedienteId()==null && expedienteActual.getNumeroExpedienteId()!=null){
			Expediente consulta = null;
			consulta = expedienteDAO.consultarExpedientePorIdNumerExpediente(expedienteActual.getNumeroExpedienteId());
			if(consulta.getExpedienteId()!=null){
				expedienteActual.setExpedienteId(consulta.getExpedienteId());
			}
			if(consulta.getNumeroExpedienteId()!=null){
				expedienteActual.setNumeroExpedienteId(consulta.getNumeroExpedienteId());
			}			
		}
		
		// Nuevo documento - nueva actividad
		if(documento.getDocumentoId()==null){
			Actividad actividadActual = null;
			
			if(idActividadExistente != null && idActividadExistente > 0){
				actividadActual = actividadDAO.read(idActividadExistente);
			}else{
				actividadActual = actividadDAO.obtenerActividadActual(expedienteActual.getExpedienteId());
			}
			
			if((actividadActual!=null && actividadActual.getDocumento()!=null) ||
			   (nuevaActividad!=null && nuevaActividad!=0L)){
				ActividadDTO actividadDTO = administradorActividadesService
						.generarActividadEnBaseAOtra(actividadActual.getActividadId());
				actividadActual = actividadDAO.read(actividadDTO.getActividadId());
				
				if(nuevaActividad!=null && nuevaActividad!=0L){
					actividadActual.setTipoActividad(new Valor(nuevaActividad));
				}						
			}			

			Documento docBD = new Documento(); 
			docBD = DocumentoTransformer.transformarDocumentoDTO(documento);
			List<ConfActividadDocumento> actDocumento = confActividadDocumentoDAO.consultarPorTipoActFormaAndJerarquia(actividadActual.getTipoActividad().getValorId(),
					documento.getFormaDTO().getFormaId(), documento.getJerarquiaOrganizacional());

			if (actDocumento!=null && !actDocumento.isEmpty() && actDocumento.get(0) != null &&
					actDocumento.get(0).getTipoDocumento() != null && actDocumento.get(0).getTipoDocumento().getValorId() != null) {				
				docBD.setTipoDocumento(new Valor(actDocumento.get(0).getTipoDocumento().getValorId()));
			} else if (documento.getTipoDocumentoDTO()!=null && documento.getTipoDocumentoDTO().getIdCampo()!=null) {			
				docBD.setTipoDocumento(new Valor (documento.getTipoDocumentoDTO().getIdCampo()));
				if(documento.getFormaDTO() == null || documento.getFormaDTO().getFormaId() == null || documento.getFormaDTO().getFormaId() < 1){
					docBD.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO.getValorId()));
				}
			} else {
				docBD.setTipoDocumento(new Valor(TipoDocumento.DOCUMENTO.getValorId()));
				if(documento.getFormaDTO() == null || documento.getFormaDTO().getFormaId() == null || documento.getFormaDTO().getFormaId() < 1){
					docBD.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO.getValorId()));
				}
			}
			
			if (docBD.getArchivoDigital() != null) {
				archivoDigitalDAO.create(docBD.getArchivoDigital());
			}

			
			//RGG: El documento se regisrar con el area de unidad de invistigacion
			//Esto sirve para filtrar la consulta de documentos
			ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
			if (Instituciones.PGJ.getValorId().equals(institucionActual.getConfInstitucionId())) {
				if(docBD.getJerarquiaOrganizacional() == null){
					docBD.setJerarquiaOrganizacional(new JerarquiaOrganizacional(Areas.UNIDAD_INVESTIGACION.parseLong()));
				}
			}	
			
			idDocumentoGen = documentoDAO.create(docBD);

			actividadActual.setDocumento(docBD);

			actividadDAO.saveOrUpdate(actividadActual);

		}
		else{
			
			// Modificaci&oacute;n de documento, recuperaci&oacute;n de la actividad asociada
			
			Actividad actividadActual = null;
			actividadActual = actividadDAO.consultarActividadXExpedienteYDocumento(null, documento.getDocumentoId());
			
			Documento docBD = new Documento(); 
			docBD = DocumentoTransformer.transformarDocumentoDTO(documento);
			List<ConfActividadDocumento> actDocumento = null; 
			
			if(actividadActual != null){
				actDocumento =confActividadDocumentoDAO.consultarPorTipoActFormaAndJerarquia(actividadActual.getTipoActividad().getValorId(),
					documento.getFormaDTO().getFormaId(), documento.getJerarquiaOrganizacional());
			}
			
			if (actDocumento!=null && !actDocumento.isEmpty() && actDocumento.get(0) != null && 
					actDocumento.get(0).getTipoDocumento() != null && actDocumento.get(0).getTipoDocumento().getValorId() != null) {
				docBD.setTipoDocumento(new Valor(actDocumento.get(0).getTipoDocumento().getValorId()));
			} else if (documento.getTipoDocumentoDTO()!=null && documento.getTipoDocumentoDTO().getIdCampo()!=null) {			
				docBD.setTipoDocumento(new Valor (documento.getTipoDocumentoDTO().getIdCampo()));
			} else {
				docBD.setTipoDocumento(new Valor(TipoDocumento.DOCUMENTO.getValorId()));
			}
			
			if (docBD.getArchivoDigital() != null) {
				archivoDigitalDAO.create(docBD.getArchivoDigital());
				docBD.setTextoParcial(null);
			}
			
			//El registro de folio e institucion se efectua dentro del "create" de DocumentoDAOImpl
//			if(docBD.getFolioDocumento()==null || docBD.getFolioDocumento().equals("")){
//				docBD.setFolioDocumento(docBD.getDocumentoId().toString());
//			}
			
			documentoDAO.saveOrUpdate(docBD);

			if (actividadActual != null) {
				actividadActual.setDocumento(docBD);
	
				idDocumentoGen = docBD.getDocumentoId();
				
				actividadDAO.saveOrUpdate(actividadActual);
			}
		}

		return idDocumentoGen;
	}

	@Transactional
	@Override
	public Long guardarDocumentoTranscripcion(DocumentoDTO documentoDTO,
			Long idSolTrans) throws NSJPNegocioException {
		final Actividad actividad = new Actividad();

		SolicitudTranscripcionAudiencia solTranAudi = solicitudTranscricpionAudienciaDAO
				.read(idSolTrans);
		ExpedienteDTO expedienteDTO = ExpedienteTransformer
				.transformaExpediente(solTranAudi.getExpediente());
		// final Funcionario funcionario =
		// FuncionarioTransformer.transformarFuncionario(expTrabajo.getUsuario().getFuncionario());
		Valor tipoActividad = new Valor();
		tipoActividad.setValorId(Actividades.ELABORAR_TRANSCRIPCION_DE_AUDIENCIA
				.getValorId());
		actividad.setExpediente(solTranAudi.getExpediente());
		// actividad.setFuncionario(funcionario);
		actividad.setTipoActividad(tipoActividad);
		actividadDAO.create(actividad);

		return this.guardarDocumento(documentoDTO, expedienteDTO, null,null);
	}

	@Transactional
	@Override
	public Long guardarActaAudiencia(DocumentoDTO documento,
			ExpedienteDTO expTrabajo) throws NSJPNegocioException {
		final Actividad actividad = new Actividad();
		Expediente expediente = ExpedienteTransformer
				.transformarExpediente(expTrabajo);
		final Funcionario funcionario = FuncionarioTransformer
				.transformarFuncionario(expTrabajo.getUsuario()
						.getFuncionario());
		Valor tipoActividad = new Valor();
		tipoActividad.setValorId(Actividades.GENERAR_ACTA_DE_AUDIENCIA.getValorId());
		actividad.setExpediente(expediente);
		actividad.setFuncionario(funcionario);
		actividad.setTipoActividad(tipoActividad);
		actividadDAO.create(actividad);
		return this.guardarDocumento(documento, expTrabajo, null,null);
	}

	@Transactional
	@Override
	public String guardarTeoriaDeCaso(DocumentoDTO documentoDTO)
			throws NSJPNegocioException {
		if (documentoDTO == null ||
			documentoDTO.getOficioEstructuradoDTO() == null ||
			documentoDTO.getExpedienteDTO() == null ||
			documentoDTO.getOficioEstructuradoDTO() == null ||
			documentoDTO.getOficioEstructuradoDTO().getCuerposOficio() == null ||
			documentoDTO.getOficioEstructuradoDTO().getCuerposOficio().size() < 0){ 
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Documento teo = actividadDAO.consultarDocumentosXExpediente(
				documentoDTO.getExpedienteDTO().getExpedienteId(),
				TipoDocumento.TEORIA_DEL_CASO.getValorId());
		
		// valorRetorno concatena id del documento , id del cuerpoOficioEstructurado
		String valorRetorno = "";
		Long cuerpoOficioEstructuradoId = 0L;
		
		if (teo == null) {// SI NO Existe TEORIA DEL CASO por el expediente
							// -->Guarda objeto estructurado y guarda documento
			OficioEstructurado oficio = OficioEstructuradoTransformer
					.transformarOficioDTO(documentoDTO
							.getOficioEstructuradoDTO());

			// Obligatorios de Oficio 
			oficio.setEncabezado("TEORIA DEL CASO");
			oficio.setPie("");
			oficio.setTipoOficio(new Valor(TipoOficioEstructurado.TEORIA_DEL_CASO
					.getValorId()));
			
			Long idOficio = oficio.getOficioEstructuradoId(); //Siempre regresa nulo? Ver CU
			
			documentoDTO.setFechaCreacion(new Date());
			FormaDTO formaDTO=new FormaDTO();
			formaDTO.setFormaId(Formas.ACTA.getValorId());
			documentoDTO.setFormaDTO(formaDTO);
			documentoDTO.setNombreDocumento("Teoria del caso");
			ValorDTO valorDTO=new ValorDTO();
			valorDTO.setIdCampo(TipoDocumento.TEORIA_DEL_CASO.getValorId());
			documentoDTO.setTipoDocumentoDTO(valorDTO);
			Documento documento=new Documento();
			DocumentoTransformer.tranformarDocumentoUpdate(documento, documentoDTO);
			oficio.setDocumento(documento);
			
			if(idOficio==null){ //Se crea el oficio
				List<CuerpoOficioEstructurado> cuerposVista = oficio.getCuerposOficio();
				oficio.setCuerposOficio(null);
				idOficio = oficioDAO.create(oficio);//Al momento de crear el Oficio se crea el documento asociado
				oficio.setCuerposOficio(cuerposVista);
			}
			//Se guardan los Cuerpos de Oficio 
			if (oficio.getCuerposOficio() != null
					&& oficio.getCuerposOficio().size() > 0) {
				List<CuerpoOficioEstructurado> cuerpos = oficio
						.getCuerposOficio();
				for (CuerpoOficioEstructurado coe : cuerpos) {
					coe.setOficioEstructurado(new OficioEstructurado(idOficio));
					if (coe.getCuerpoOficioEstructuradoId() == null) {
						//Si es el que se modifico el texto se guarda el texto que se envia
						//Si no lo es, se tienen que consulta del Indice Estructurado
						if(coe.getInteresa()== null || !coe.getInteresa()){
							IndiceEstructurado indice =  indiceEstructuradoDAO.read(coe.getIndiceEstructurado().getIndiceEstructuradoId());
							coe.setTexto( indice.getTextoEtiqueta());
						}
						cuerpoOficioEstructuradoId = cuerpoDAO.create(coe);
					}
				}
			}
			Long idDocumento = 0L;
			if( oficio!= null && oficio.getDocumento()!= null && oficio.getDocumento().getDocumentoId()!= null)
				idDocumento = oficio.getDocumento().getDocumentoId();
			else{
				//Leer el documento asociado al oficio	
				OficioEstructurado oficioLect = oficioDAO.read(idOficio);
				idDocumento = oficioLect.getDocumento().getDocumentoId();
			}
	
			/* Asignar en actividad */
			Documento docu = new Documento();
			docu.setDocumentoId(idDocumento);
			Actividad actividadActual = actividadDAO
					.obtenerActividadActual(documentoDTO.getExpedienteDTO().getExpedienteId());
			if (actividadActual != null) {
				actividadActual.setDocumento(docu);
				if (actividadActual.getDocumento() == null) {
					actividadDAO.update(actividadActual);
				} else {
					actividadActual.setFechaCreacion(new Date());
					actividadDAO.create(actividadActual);
				}
			} else {
				Actividad nuevaAct = new Actividad();
				nuevaAct.setDocumento(docu);
				nuevaAct.setExpediente(new Expediente(documentoDTO.getExpedienteDTO().getExpedienteId()));
				nuevaAct.setFechaCreacion(new Date());
				nuevaAct.setTipoActividad(new Valor(
						Actividades.ATENDER_CANALIZACION_JAR.getValorId()));
				NumeroExpediente numero = numExpDAO.obtenerNumeroExpedienteXExpediente(documentoDTO.getExpedienteDTO().getExpedienteId());
				Long claveFunc=1L;
				if(numero!=null){
				Funcionario funcionar=funcionarioDAO.consultarFuncionarioXExpediente(numero);
				if(funcionar!=null)
					claveFunc=funcionar.getClaveFuncionario();
				}
				
				nuevaAct.setFuncionario(new Funcionario(claveFunc));
				actividadDAO.create(nuevaAct);
			}

			valorRetorno = idDocumento.toString() + "," + cuerpoOficioEstructuradoId.toString();

		} else {// SI Existe TEORIA DEL CASO por expediente --> Guarda, Elimina
				// y Actualiza objeto estructurado

			List<CuerpoOficioEstructurado> cuerposVista = sacaConvierteCuerpos(documentoDTO
					.getOficioEstructuradoDTO().getCuerposOficio());

			if (teo.getOficioEstructurado() != null && teo.getOficioEstructurado().getCuerposOficio() != null) {
				
				List<CuerpoOficioEstructurado> cuerposBD = teo.getOficioEstructurado().getCuerposOficio();

				//Para los Cuerpos estructurados asociados, la busqueda es sobre BD y se divide en 3 pasos 
				siguiente:
				for (int contBD=0; contBD<cuerposBD.size(); contBD++) {
					CuerpoOficioEstructurado coeBD = cuerposBD.get(contBD);
					LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
					for (int contVista = 0; contVista< cuerposVista.size(); contVista++) {
						CuerpoOficioEstructurado coeVI = cuerposVista.get(contVista);
						LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
						
						//1.- Si tiene ID (Vista)  y se tiene en BD  --> Actualizar
						if(coeVI.getCuerpoOficioEstructuradoId()!= null &&
								coeVI.getCuerpoOficioEstructuradoId().equals(coeBD.getCuerpoOficioEstructuradoId())){
							
							//Si no es el que interesa, se conserva el que tiene en BD
							if(coeVI.getInteresa()== null || !coeVI.getInteresa())
								coeBD.setTexto( coeBD.getTexto());
							else //Si interesa entonces se debe setear el texto de vista
								coeBD.setTexto( coeVI.getTexto());
														
							CuerpoOficioEstructuradoTransformer.transformarCuerpoUpdate(coeBD, coeVI);
							cuerpoDAO.update(coeBD);
							cuerpoOficioEstructuradoId=coeBD.getCuerpoOficioEstructuradoId();
							
							LOGGER.info("Actualiz? el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
							
							cuerposBD.remove(contBD);
							cuerposVista.remove(contVista);
							LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
							LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
							contBD--;
							continue siguiente;
						}
					}
				}
				
				//2.- Los que estan en la lista de cuerpos BD se eliminan No se hace uso del deleteAll 
				for (CuerpoOficioEstructurado coeBD : cuerposBD) {
					coeBD.setIndiceEstructurado(null);
					coeBD.setOficioEstructurado(null);
					LOGGER.info("Va a eliminar el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
					LOGGER.info("texto: "+coeBD.getTexto());
					cuerpoDAO.delete(coeBD);
				}
				
				//3.- Los que estan en cuerposVista se crean. No se hace uso del CreateAll
				for (CuerpoOficioEstructurado coeVI : cuerposVista) {
					coeVI.setOficioEstructurado(new OficioEstructurado(teo.getOficioEstructurado().getOficioEstructuradoId()));
					//Si es el que se modifico el texto se guarda el texto que se envia
					//Si no lo es, se tienen que consulta del Indice Estructurado
					if(coeVI.getInteresa()== null || !coeVI.getInteresa()){
						IndiceEstructurado indice =  indiceEstructuradoDAO.read(coeVI.getIndiceEstructurado().getIndiceEstructuradoId());
						coeVI.setTexto( indice.getTextoEtiqueta());
					}
					
					cuerpoOficioEstructuradoId=cuerpoDAO.create(coeVI);
				}
			}


			valorRetorno = teo.getDocumentoId().toString() + "," + cuerpoOficioEstructuradoId.toString();
		}
		
		return valorRetorno;
	}
	
	/**
	 * Metodo para guardar el pliego de consignacion
	 */
	
	@Transactional
	@Override
	public Long guardarPliegoConsignacion(DocumentoDTO documentoDTO)
			throws NSJPNegocioException {
		if (documentoDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if (documentoDTO.getOficioEstructuradoDTO() == null
				|| documentoDTO.getExpedienteDTO() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(documentoDTO.getOficioEstructuradoDTO() == null ||
				documentoDTO.getOficioEstructuradoDTO().getCuerposOficio() == null ||
				documentoDTO.getOficioEstructuradoDTO().getCuerposOficio().size() < 0) 
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Documento teo = actividadDAO.consultarDocumentosXExpediente(
				documentoDTO.getExpedienteDTO().getExpedienteId(),
				TipoDocumento.PLIEGO_DE_CONSIGNACION.getValorId());
		if (teo == null) {// SI NO Existe PLIEGOO DE CONSIGNACION por el expediente
							// -->Guarda objeto estructurado y guarda documento
			OficioEstructurado oficio = OficioEstructuradoTransformer
					.transformarOficioDTO(documentoDTO
							.getOficioEstructuradoDTO());

			// Obligatorios de Oficio 
			oficio.setEncabezado("PLIEGO DE CONSIGNACION");
			oficio.setPie("");
			oficio.setTipoOficio(new Valor(TipoOficioEstructurado.PLIEGO_DE_CONSIGNACION
					.getValorId()));
			
			Long idOficio = oficio.getOficioEstructuradoId(); //Siempre regresa nulo? Ver CU
			
			documentoDTO.setFechaCreacion(new Date());
			FormaDTO formaDTO=new FormaDTO();
			formaDTO.setFormaId(Formas.ACTA.getValorId());
			documentoDTO.setFormaDTO(formaDTO);
			documentoDTO.setNombreDocumento("Pliego de Consignacion");
			ValorDTO valorDTO=new ValorDTO();
			valorDTO.setIdCampo(TipoDocumento.PLIEGO_DE_CONSIGNACION.getValorId());
			documentoDTO.setTipoDocumentoDTO(valorDTO);
			Documento documento=new Documento();
			DocumentoTransformer.tranformarDocumentoUpdate(documento, documentoDTO);
			oficio.setDocumento(documento);
			
			if(idOficio==null){ //Se crea el oficio
				List<CuerpoOficioEstructurado> cuerposVista = oficio.getCuerposOficio();
				oficio.setCuerposOficio(null);
				idOficio = oficioDAO.create(oficio);//Al momento de crear el Oficio se crea el documento asociado
				oficio.setCuerposOficio(cuerposVista);
			}
			//Se guardan los Cuerpos de Oficio 
			if (oficio.getCuerposOficio() != null
					&& oficio.getCuerposOficio().size() > 0) {
				List<CuerpoOficioEstructurado> cuerpos = oficio
						.getCuerposOficio();
				for (CuerpoOficioEstructurado coe : cuerpos) {
					coe.setOficioEstructurado(new OficioEstructurado(idOficio));
					if (coe.getCuerpoOficioEstructuradoId() == null) {
						//Si es el que se modifico el texto se guarda el texto que se envia
						//Si no lo es, se tienen que consulta del Indice Estructurado
						if(coe.getInteresa()== null || !coe.getInteresa()){
							IndiceEstructurado indice =  indiceEstructuradoDAO.read(coe.getIndiceEstructurado().getIndiceEstructuradoId());
							coe.setTexto( indice.getTextoEtiqueta());
						}
						cuerpoDAO.create(coe);
					}
				}
			}
			Long idDocumento = 0L;
			if( oficio!= null && oficio.getDocumento()!= null && oficio.getDocumento().getDocumentoId()!= null)
				idDocumento = oficio.getDocumento().getDocumentoId();
			else{
				//Leer el documento asociado al oficio	
				OficioEstructurado oficioLect = oficioDAO.read(idOficio);
				idDocumento = oficioLect.getDocumento().getDocumentoId();
			}
	
			/* Asignar en actividad */
			Documento docu = new Documento();
			docu.setDocumentoId(idDocumento);
			Actividad actividadActual = actividadDAO
					.obtenerActividadActual(documentoDTO.getExpedienteDTO().getExpedienteId());
			if (actividadActual != null) {
				actividadActual.setDocumento(docu);
				if (actividadActual.getDocumento() == null) {
					actividadDAO.update(actividadActual);
				} else {
					actividadActual.setFechaCreacion(new Date());
					actividadDAO.create(actividadActual);
				}
			} else {
				Actividad nuevaAct = new Actividad();
				nuevaAct.setDocumento(docu);
				nuevaAct.setExpediente(new Expediente(documentoDTO.getExpedienteDTO().getExpedienteId()));
				nuevaAct.setFechaCreacion(new Date());
				nuevaAct.setTipoActividad(new Valor(
						Actividades.ATENDER_CANALIZACION_JAR.getValorId()));
				NumeroExpediente numero = numExpDAO.obtenerNumeroExpedienteXExpediente(documentoDTO.getExpedienteDTO().getExpedienteId());
				Long claveFunc=1L;
				if(numero!=null){
				Funcionario funcionar=funcionarioDAO.consultarFuncionarioXExpediente(numero);
				if(funcionar!=null)
					claveFunc=funcionar.getClaveFuncionario();
				}
				
				nuevaAct.setFuncionario(new Funcionario(claveFunc));
				actividadDAO.create(nuevaAct);
			}

			return idDocumento;

		} else {// SI Existe PLIEGO DE CONSIGNACION por expediente --> Guarda, Elimina
				// y Actualiza objeto estructurado

			List<CuerpoOficioEstructurado> cuerposVista = sacaConvierteCuerpos(documentoDTO
					.getOficioEstructuradoDTO().getCuerposOficio());

			if (teo.getOficioEstructurado() != null && teo.getOficioEstructurado().getCuerposOficio() != null) {
				
				List<CuerpoOficioEstructurado> cuerposBD = teo.getOficioEstructurado().getCuerposOficio();

				//Para los Cuerpos estructurados asociados, la busqueda es sobre BD y se divide en 3 pasos 
				siguiente:
				for (int contBD=0; contBD<cuerposBD.size(); contBD++) {
					CuerpoOficioEstructurado coeBD = cuerposBD.get(contBD);
					LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
					for (int contVista = 0; contVista< cuerposVista.size(); contVista++) {
						CuerpoOficioEstructurado coeVI = cuerposVista.get(contVista);
						LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
						
						//1.- Si tiene ID (Vista)  y se tiene en BD  --> Actualizar
						if(coeVI.getCuerpoOficioEstructuradoId()!= null &&
								coeVI.getCuerpoOficioEstructuradoId().equals(coeBD.getCuerpoOficioEstructuradoId())){
							
							//Si no es el que interesa, se conserva el que tiene en BD
							if(coeVI.getInteresa()== null || !coeVI.getInteresa())
								coeBD.setTexto( coeBD.getTexto());
							else //Si interesa entonces se debe setear el texto de vista
								coeBD.setTexto( coeVI.getTexto());
														
							CuerpoOficioEstructuradoTransformer.transformarCuerpoUpdate(coeBD, coeVI);
							cuerpoDAO.update(coeBD);
							
							LOGGER.info("Actualiz? el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
							
							cuerposBD.remove(contBD);
							cuerposVista.remove(contVista);
							LOGGER.info(" cuerposBD ("+ cuerposBD.size()+") - contBD->"+contBD+": "+ cuerposBD);
							LOGGER.info(" cuerposVista ("+ cuerposVista.size()+") - contVista->"+contVista+": "+ cuerposVista);
							contBD--;
							continue siguiente;
						}
					}
				}
				
				//2.- Los que estan en la lista de cuerpos BD se eliminan No se hace uso del deleteAll 
				for (CuerpoOficioEstructurado coeBD : cuerposBD) {
					coeBD.setIndiceEstructurado(null);
					coeBD.setOficioEstructurado(null);
					LOGGER.info("Va a eliminar el cuerpo: "+coeBD.getCuerpoOficioEstructuradoId());
					LOGGER.info("texto: "+coeBD.getTexto());
					cuerpoDAO.delete(coeBD);
				}
				
				//3.- Los que estan en cuerposVista se crean. No se hace uso del CreateAll
				for (CuerpoOficioEstructurado coeVI : cuerposVista) {
					coeVI.setOficioEstructurado(new OficioEstructurado(teo.getOficioEstructurado().getOficioEstructuradoId()));
					//Si es el que se modifico el texto se guarda el texto que se envia
					//Si no lo es, se tienen que consulta del Indice Estructurado
					if(coeVI.getInteresa()== null || !coeVI.getInteresa()){
						IndiceEstructurado indice =  indiceEstructuradoDAO.read(coeVI.getIndiceEstructurado().getIndiceEstructuradoId());
						coeVI.setTexto( indice.getTextoEtiqueta());
					}
					
					Long idLoc=cuerpoDAO.create(coeVI);
					LOGGER.info("Creo el cuerpo: "+idLoc);
				}
			}

			return teo.getDocumentoId();
		}
	}

	private List<CuerpoOficioEstructurado> sacaConvierteCuerpos(
			List<CuerpoOficioEstructuradoDTO> cuerposOficio) {
		List<CuerpoOficioEstructurado> cuerpos = new ArrayList<CuerpoOficioEstructurado>();
		for (CuerpoOficioEstructuradoDTO cdto : cuerposOficio) {
			cuerpos.add(CuerpoOficioEstructuradoTransformer
					.transformarCuerpoDTO(cdto));
		}
		return cuerpos;
	}

	@Transactional
    @Override
    public Long guardarDocumentoIntraInstitucion(DocumentoDTO documentoDTO,
            ExpedienteDTO expedienteDTO) {
		try {
		//Buscar el documento por folio e institucion
		List<Documento> documentos = documentoDAO.consultarDocumentosPorFolioInstitucion(
				documentoDTO.getFolioDocumento(), documentoDTO
						.getConfInstitucion() != null ? documentoDTO
						.getConfInstitucion().getConfInstitucionId() : null);
		ConfInstitucion institucionActual;
		institucionActual = confInstitucionDAO.consultarInsitucionActual();
        NumeroExpediente neActual = this.numExpDAO.read(expedienteDTO
                .getNumeroExpedienteId());
		if(Instituciones.PGJ.getValorId().equals(institucionActual.getConfInstitucionId()) 
				&& documentos != null && !documentos.isEmpty()){
			for (Documento documento2 : documentos) {
				
			        Actividad act = new Actividad();
			        if(documentoDTO.getActividadDTO().getTipoActividad()!=null &&
			        		documentoDTO.getActividadDTO().getTipoActividad().getValorId()!=null){
			        	act.setTipoActividad(new Valor(documentoDTO.getActividadDTO()
			                    .getTipoActividad().getValorId()));
			        }else if(documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum()!=null &&
			        		documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum().getIdCampo()!=null ){
			        	act.setTipoActividad(new Valor(documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum().getIdCampo()));
			        }
			        act.setFechaCreacion(documentoDTO.getActividadDTO().getFechaCreacion());
			        act.setFuncionario(neActual.getFuncionario());
			        act.setExpediente(neActual.getExpediente());
			        act.setDocumento(documento2);
			        if(act.getTipoActividad()!=null){
			        	this.actividadDAO.create(act);
			        }else{
			        	return 0L;
			        }

				
			}
		}
		
		if (documentos != null && !documentos.isEmpty()) {
			LOGGER.debug(" El documento ya estaba registrado: "
					+ documentoDTO.getFolioDocumento());
			return 0L;
		}
		

        Documento docBD = DocumentoTransformer
                .transformarDocumentoDTO(documentoDTO);
        
        ArchivoDigital arch = docBD.getArchivoDigital();
        arch.setArchivoDigitalId(null);
        this.archivoDigitalDAO.create(arch);
        docBD.setArchivoDigital(arch);
        
        docBD.setResponsableDocumento(neActual.getFuncionario());
        
        if(docBD.getForma() !=null && docBD.getForma().getFormaId()!=null){
        	Forma forma=formaDAO.consultarFormaPorId(docBD.getForma().getFormaId());
        	if(forma==null){
        		docBD.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO.getValorId()));
        	}
        }else{
        	docBD.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO.getValorId()));
        }
        //Al guardar datos de la carpeta de investigacion el documento siempre es guardado definitivo.
        docBD.setEsGuardadoParcial(false);
        
        
      //RGG: El documento se regisrar con el area de unidad de investigacion
		//Esto sirve para filtrar la consulta de documentos
		
		
			
			if (Instituciones.PGJ.getValorId().equals(institucionActual.getConfInstitucionId())) {
				if(docBD.getJerarquiaOrganizacional() == null){
					docBD.setJerarquiaOrganizacional(new JerarquiaOrganizacional(Areas.UNIDAD_INVESTIGACION.parseLong()));
				}
			}	
		
        
        docBD.setDocumentoId(this.documentoDAO.create(docBD));

        Actividad act = new Actividad();
        if(documentoDTO.getActividadDTO().getTipoActividad()!=null &&
        		documentoDTO.getActividadDTO().getTipoActividad().getValorId()!=null){
        	act.setTipoActividad(new Valor(documentoDTO.getActividadDTO()
                    .getTipoActividad().getValorId()));
        }else if(documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum()!=null &&
        		documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum().getIdCampo()!=null ){
        	act.setTipoActividad(new Valor(documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum().getIdCampo()));
        }
        act.setFechaCreacion(documentoDTO.getActividadDTO().getFechaCreacion());
        act.setFuncionario(neActual.getFuncionario());
        act.setExpediente(neActual.getExpediente());
        act.setDocumento(docBD);
        if(act.getTipoActividad()!=null){
        	this.actividadDAO.create(act);
        }else{
        	return 0L;
        }
        	return docBD.getDocumentoId();
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			return null;
		}
        
    }

	@Transactional
	@Override
	public Long guardarDocumentoConActividadDocumento(DocumentoDTO documentoDto,
			ExpedienteDTO expedienteDto) throws NSJPNegocioException {

		if (expedienteDto == null
				|| expedienteDto.getExpedienteId() == null
				|| documentoDto == null
				|| documentoDto.getActividadDTO() == null
				|| documentoDto.getActividadDTO().getTipoActividad() == null
				|| documentoDto.getActividadDTO().getTipoActividad().getValorId() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		//OBTENEMOS EL FUNCIONARIO RESPONSABLE DEL NUMERO DE EXPEDIENTE
		NumeroExpediente numExp = null;
		
		//List<NumeroExpediente> numeroExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesXIdExpAreaDiscriminante(expedienteDto.getExpedienteId(),Areas.UNIDAD_INVESTIGACION.parseLong(),null);
		List<NumeroExpediente> numeroExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesXIdExpAreaDiscriminante(expedienteDto.getExpedienteId(),null,null);
		
		
		if (numeroExpedientes == null || numeroExpedientes.size() < 1
				|| numeroExpedientes.get(0).getFuncionario() == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		numExp = numeroExpedientes.get(0);
		
		
		//TRANSFORMAMOS EL DOCUMENTO
		Documento documento = DocumentoTransformer.transformarDocumentoDTO(documentoDto);
		
		if(documentoDto.getActividadDTO().getTipoActividad().getValorId().equals(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR.getValorId())){
			
			documentoDto
					.getActividadDTO()
					.setActividadId(
							mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR
									.getValorId());
		}
		if(documentoDto.getActividadDTO().getTipoActividad().getValorId().equals(Actividades.ANEXAR_DOCUMENTO.getValorId())){
			documentoDto
					.getActividadDTO()
					.setActividadId(
							mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.ARCHIVO_ADJUNTADO.getValorId());
		}
		if(documentoDto.getActividadDTO().getTipoActividad().getValorId().equals(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId())){
			documentoDto
			.getActividadDTO()
			.setActividadId(
					mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
							.getValorId());
		}
		/**
		 * SECCION PARA EL ACUSE DE RECIBO DE SOLICITUD DE DEFENSOR PUBLICO
		 */
		if (documentoDto
				.getActividadDTO()
				.getTipoActividad()
				.getValorId()	//FIXME AGA ESPECIFICAR LA ACTIVIDAD CORRECTA
				.equals(Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO
						.getValorId())) {

			ConfActividadDocumentoDTO filtroConfActividadDocumentoDTO = new ConfActividadDocumentoDTO();
			filtroConfActividadDocumentoDTO.setCategoriaActividad(new ValorDTO(
					CategoriasActividad.DEFENSORIA_PENAL.getValorId()));
			filtroConfActividadDocumentoDTO
					.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(
							Areas.COORDINACION_DEFENSORIA.parseLong()));
			filtroConfActividadDocumentoDTO.setMuestraEnCombo(true);

			List<ConfActividadDocumentoDTO> listActividadDocumentoDTOs = consultarConfActividadDocumentoService
					.consultarConfActividadDocumentoFiltro(filtroConfActividadDocumentoDTO);
			
			if (listActividadDocumentoDTOs == null
					|| listActividadDocumentoDTOs.isEmpty()
					|| listActividadDocumentoDTOs.get(0) == null
					|| listActividadDocumentoDTOs.get(0)
							.getConfActividadDocumentoId() == null) {

				LOGGER.info("*NO EXISTE REGISTRO EN CONF ACTIVIDAD PARA ESTA ACTIVIDAD ID...: "
						+ documentoDto.getActividadDTO().getTipoActividad()
								.getValorId());
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			
			documentoDto.getActividadDTO().setActividadId(
					listActividadDocumentoDTOs.get(0)
							.getConfActividadDocumentoId());
		}
		
		/** SECCION PARA EL ACUSE DE RECIBO DE LA CARPETA DE INVESTIGACION **/
		if (documentoDto
				.getActividadDTO()
				.getTipoActividad()
				.getValorId()
				.equals(Actividades.RECIBIR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION
						.getValorId())) {

			ConfActividadDocumentoDTO filtro = new ConfActividadDocumentoDTO();
			filtro.setTipoActividadId(Actividades.RECIBIR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION
					.getValorId());

			// Consultamos el registro confActividad para obtener el id, de la actividad
			//y obtener la forma y los dem&acute;s parametros
			ConfActividadDocumentoDTO registroConfActividad = consultarConfActividadDocumentoService
					.consultaConfActividadDocumentoPorIdActividad(filtro);

			if (registroConfActividad != null) {
				documentoDto.getActividadDTO().setActividadId(
						registroConfActividad.getConfActividadDocumentoId());
			}
		}
		
		ConfActividadDocumento confActividadDocumento = null;
		
		//OBTENEMOS EL REGISTRO DE LA ACTIVIDAD EN BASE A LA ACTIVIDAD RECIBIDA COMO PARAMETRO
		if (documentoDto.getActividadDTO() != null
				&& documentoDto.getActividadDTO().getActividadId() != null) {
			
			confActividadDocumento = confActividadDocumentoDAO
					.read(documentoDto.getActividadDTO().getActividadId());
		}
		
		if (confActividadDocumento == null
				|| confActividadDocumento.getTipoDocumento() == null
				|| confActividadDocumento.getTipoDocumento().getValorId() == null
				|| confActividadDocumento.getTipoDocumento().getValor() == null
				|| confActividadDocumento.getTipoActividad() == null
				|| confActividadDocumento.getTipoActividad().getValorId() == null
				|| confActividadDocumento.getTipoActividad().getValor() == null) {

			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		if(confActividadDocumento.getForma() != null && confActividadDocumento.getForma().getFormaId() != null){			
			documento.setForma(new Forma(confActividadDocumento.getForma().getFormaId()));
		}
		documento.setTipoDocumento(new Valor(confActividadDocumento.getTipoDocumento().getValorId()));
		if(documentoDto.getActividadDTO().getTipoActividad().getValorId().equals(Actividades.ANEXAR_DOCUMENTO.getValorId())
				&& documento.getArchivoDigital() != null && documento.getArchivoDigital().getNombreArchivo() != null
				&& !documento.getArchivoDigital().getNombreArchivo().isEmpty()){
			documento.setNombreDocumento(documento.getArchivoDigital().getNombreArchivo());
		}else{			
			documento.setNombreDocumento(confActividadDocumento.getTipoDocumento().getValor());
		}
		
		
		
		//GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE
		Actividad actividad = new Actividad();
		actividad.setFuncionario(numExp.getFuncionario());
		actividad.setExpediente(new Expediente(expedienteDto.getExpedienteId()));
		
		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());
		
		actividad.setTipoActividad(tipoActividad);
		actividad.setFechaCreacion(new Date());
		
		documento.setActividad(actividad);
		actividad.setDocumento(documento);
		
		if (documento.getArchivoDigital() != null) {
			//Si tiene archivo digital NO debe guardarse como documento parcial
			archivoDigitalDAO.create(documento.getArchivoDigital());
			documento.setEsGuardadoParcial(false);
		}

		//RGG: El documento se regisrar con el area de unidad de invistigacion
		//Esto sirve para filtrar la consulta de documentos
		ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
		if (Instituciones.PGJ.getValorId().equals(institucionActual.getConfInstitucionId())) {
			if(documento.getJerarquiaOrganizacional() == null){
				documento.setJerarquiaOrganizacional(new JerarquiaOrganizacional(Areas.UNIDAD_INVESTIGACION.parseLong()));
			}
		}	
		
		return documentoDAO.create(documento);
	}
	
	
	
	@Override
	@Transactional
	public Long ejecutaGuardadoDefinitivo(GuardadoDefinitivoDTO aoGuardadoDefinitivo) throws NSJPNegocioException {
		
		if(aoGuardadoDefinitivo==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		/**
		 * CONFIGURA Y REGISTRA UNA ACTIVIDAD ASOCIADA AL EXPEDIENTE
		 */
		ExpedienteDTO loExpedienteDTO = aoGuardadoDefinitivo.getExpedienteDTO();
		Long idActuacion = aoGuardadoDefinitivo.getIdActividad();
		FuncionarioDTO loFuncionarioDTO = aoGuardadoDefinitivo.getFuncionarioDTO();
		Long idClaveFuncionarioAsignado = aoGuardadoDefinitivo.getIdClaveFuncionarioAsignado();
		Long idNuevaActividad = aoGuardadoDefinitivo.getIdNuevaActividad();
		DocumentoDTO loDocumentoDTO = aoGuardadoDefinitivo.getDocumentoDTO();
		
		Long idActividadRegistrada=0L;
		Long idDocumentoRegistrado=0L;
		Long idNuevoEstatusNumExp = 0L;
		
		boolean asignarFunc=false;
		
		Long actividad=0L;
		if(idActuacion > 0){
			if(idActuacion.equals(Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId())){
				actividad=Actividades.RECIBIR_CANALIZACION_UI.getValorId();
			}else if(idActuacion.equals(Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId()) || idActuacion.equals(Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId())){
				actividad=Actividades.RECIBIR_CANALIZACION_JAR.getValorId();
			}else if(idActuacion.equals(new Long("151"))){
				actividad=Actividades.ATENDER_CANALIZACION_UI.getValorId();
				asignarFunc=true;
			}else{
				
				if(idActuacion.equals(Actividades.ASIGNAR_SOLICITUD_DE_AYUDA.getValorId())
						|| idActuacion.equals(Actividades.ENVIAR_ESTUDIO_SOCIOECONOMICO.getValorId())
						|| idActuacion.equals(Actividades.PROPORCIONAR_APOYO_LEGAL.getValorId())
						|| idActuacion.equals(Actividades.ATENDER_CANALIZACION_JAR.getValorId())){
					
					asignarFunc=true;
				}
				actividad=idActuacion;
			}
		}		 
		
		
		//Usado para las areas de uavd, para designar el funcionario al que se le asigna la solicitud
		if(aoGuardadoDefinitivo.getEsGuardadoParcial() == false || (aoGuardadoDefinitivo.getDocumentoDTO() != null &&
				aoGuardadoDefinitivo.getDocumentoDTO().getDocumentoId() == null)){
			if(asignarFunc == true){
				UsuarioDTO usuarioDestino = new UsuarioDTO();
					usuarioDestino=usuarioService.consultarUsuarioPorClaveFuncionario(idClaveFuncionarioAsignado);
				if(usuarioDestino != null && usuarioDestino.getFuncionario() != null){
					idActividadRegistrada=registrarActividadService.registrarActividad(loExpedienteDTO,usuarioDestino.getFuncionario(),actividad);
				}
			}else{
				if(aoGuardadoDefinitivo.getDocumentoDTO() != null && aoGuardadoDefinitivo.getDocumentoDTO().getDocumentoId() == null){
					//Registra la actividad en el caso de ser el primer guardado parcial o definitivo
					idActividadRegistrada=registrarActividadService.registrarActividad(loExpedienteDTO, loFuncionarioDTO ,actividad);
				}else{
					//Para no volver a registar una actividad desde el guardado definitivo desde la consulta de documentos
					if(aoGuardadoDefinitivo.getDocumentoDTO().getDocumentoId() == 0){ 						
						idActividadRegistrada=registrarActividadService.registrarActividad(loExpedienteDTO, loFuncionarioDTO ,actividad);
					}
				}
			} 
		}
				
		
		/**
		 * GENERA UN DOCUMENTO ASOCIADO AL EXPEDIENTE
		 */
	    if(idNuevaActividad!=null && idNuevaActividad!=0L){
	    	idDocumentoRegistrado = this.guardarDocumento(loDocumentoDTO,loExpedienteDTO,idNuevaActividad, idActividadRegistrada);
	    } else {
	    	idDocumentoRegistrado = this.guardarDocumento(loDocumentoDTO,loExpedienteDTO,null, idActividadRegistrada);	
	    }
	    
	    ConfInstitucion confInstitucion = confInstitucionDAO.consultarIntitucionActual();
	    
	    //Cambia el estatus del expediente cuando es guardado definitivo
	    if(!aoGuardadoDefinitivo.getEsGuardadoParcial()){
	    	
	    	
			/**
			 * CAMBIA LA ETAPA DEL NUMERO DE EXPEDIENTE 
			 * Se considera que las actuaciones, asociada a los involucrados, 
			 * cambian la etapa del involucrado y la etapa del expediente. 
			 * Las actuaciones del expediente NO CAMBIAN el 
			 * ESTATUS del NUMERO DE EXPEDIENTE
			 */
			if (confInstitucion.getConfInstitucionId().equals(
					Instituciones.DEF.getValorId())
					&& aoGuardadoDefinitivo.getInvolcradoId() != null
					&& aoGuardadoDefinitivo.getInvolcradoId() > 0L
					&& aoGuardadoDefinitivo.getConfActividadId() != null
					&& aoGuardadoDefinitivo.getConfActividadId() > 0L) {
				LOGGER.info("ACtuación de Defensoria-getInvolcradoId:"
						+ aoGuardadoDefinitivo.getInvolcradoId());
				LOGGER.info("ACtuación de Defensoria-idActuacion:"
						+ idActuacion);
				LOGGER.info("ACtuación de Defensoria-ConfActividadId:"
						+ aoGuardadoDefinitivo.getConfActividadId());
				
				// Obtener la siguiente etapa para actualizar el involucrado y
				// expediente
				ConfActividadDocumentoDTO filtroConfActividadDocumentoDTO = new ConfActividadDocumentoDTO(
						aoGuardadoDefinitivo.getConfActividadId());
				List<ConfActividadDocumentoDTO> confActividadDocumentoDTO = consultarConfActividadDocumentoService
						.consultarConfActividadDocumentoFiltro(filtroConfActividadDocumentoDTO);
				if (confActividadDocumentoDTO.isEmpty()
						|| confActividadDocumentoDTO.get(0) == null
						|| confActividadDocumentoDTO.get(0)
								.getEstadoCambioExpediente() == null
						|| confActividadDocumentoDTO.get(0)
								.getEstadoCambioExpediente().getIdCampo() == null
						|| confActividadDocumentoDTO.get(0)
								.getEstadoCambioExpediente().getIdCampo() <= 0) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}

				actualizarEtapaExpedienteService
						.actualizarEtapaInvolucradoExpediente(
								aoGuardadoDefinitivo.getInvolcradoId(),
								confActividadDocumentoDTO.get(0)
										.getEstadoCambioExpediente()
										.getIdCampo());

			} else {
		    	/**
				 * CAMBIA EL ESTATUS DEL NUMERO DE EXPEDIENTE
				 */
		    	if (actividad != Actividades.SOLICITAR_ATENCION_PSICOLOGICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS.getValorId() && 
			    	actividad != Actividades.SOLICITAR_DEFENSOR.getValorId()){
			    	
		    		Long estatusActual = numeroExpedienteDAO.consultarEstatusNumeroExpedienteByNumeroExpedienteId(loExpedienteDTO.getNumeroExpedienteId());
			    	//Obtiene el nuevo numero de estatus de expediente (pensado para el guardado parcial)		    	
			    	ConfActividadDocumento loConfActividadDocumento = confActividadDocumentoDAO.consultarProximoEstatusDeNumExp(idActuacion, 
		    			estatusActual, loExpedienteDTO.getArea().getAreaId());
			    	if(loConfActividadDocumento != null && loConfActividadDocumento.getEstadoCambioExpediente() != null && loConfActividadDocumento.getEstadoCambioExpediente().getValorId() != null){
			    		idNuevoEstatusNumExp = loConfActividadDocumento.getEstadoCambioExpediente().getValorId();
			    		loExpedienteDTO.setEstatus(new ValorDTO(idNuevoEstatusNumExp));
			    		actualizarExpedienteService.actualizarEstatusExpediente(loExpedienteDTO);
		    			
						// ACTUALIZAR EL ESTATUS DE LA(S) SOLCITUD(ES) - DEFENSORIA
						if (confInstitucion.getConfInstitucionId().equals(
								Instituciones.DEF.getValorId())) {
							actualizarEstatusSolicitudService
									.actualizarEstatusSolicitudes(
											idNuevoEstatusNumExp,
											loExpedienteDTO
													.getNumeroExpedienteId());
						}
		    			
						//Registramos el cambio del estatus del numero de expediente en bitacora
		    			if(idNuevoEstatusNumExp.longValue() != estatusActual.longValue()){
		    				BitacoraEstatusNumExpediente loBitacoraEstatusNumExpediente = new BitacoraEstatusNumExpediente();		    			
		    				loBitacoraEstatusNumExpediente.setFechaCreacion(new Date());
		    				if(loExpedienteDTO.getArea() != null && loExpedienteDTO.getArea().getAreaId() != null)
		    					loBitacoraEstatusNumExpediente.setJerarquiaOrganizacional(new JerarquiaOrganizacional(loExpedienteDTO.getArea().getAreaId()));
		    				loBitacoraEstatusNumExpediente.setNumeroExpediente(new NumeroExpediente(loExpedienteDTO.getNumeroExpedienteId()));
		    				loBitacoraEstatusNumExpediente.setFuncionario(FuncionarioTransformer.transformarFuncionario(loFuncionarioDTO));
		    				loBitacoraEstatusNumExpediente.setEstatus(loConfActividadDocumento.getEstadoCambioExpediente());
		    				loBitacoraEstatusNumExpediente.setTipoActividad(loConfActividadDocumento.getTipoActividad());
		    				loBitacoraEstatusNumExpediente.setTipoDocumento(loConfActividadDocumento.getTipoDocumento());
		    				loBitacoraEstatusNumExpediente.setTipoMovimiento(4L);
		    				LOGGER.info("**** Registrando en BitacoraEstatusNumExpediente ****");
		    				bitacoraEstatusNumExpedienteDAO.create(loBitacoraEstatusNumExpediente);
		    			}
		    			LOGGER.info("Estatus actual: " + estatusActual);
	    		    	LOGGER.info("Estatus nuevo: " + idNuevoEstatusNumExp);
			    	}
			    	
			    	//Permite actualizar el estatus del expediente
			    	if(loConfActividadDocumento != null && loConfActividadDocumento.getEstadoExpediente() != null 
			    			&& loConfActividadDocumento.getEstadoExpediente().getValorId() != null && loConfActividadDocumento.getEstadoExpediente().getValorId() > 0
			    			&& loExpedienteDTO.getExpedienteId() != null){
			    		Expediente loExpediente = expedienteDAO.read(loExpedienteDTO.getExpedienteId());
			    		loExpediente.setEstatus(new Valor(loConfActividadDocumento.getEstadoExpediente().getValorId()));
			    		expedienteDAO.update(loExpediente);
			    		LOGGER.info("**** Se actualizo de forma correcta el expediente ****");
			    		LOGGER.info("Estatus nuevo: " + loConfActividadDocumento.getEstadoExpediente().getValorId());
			    		LOGGER.info("Expediente Id: " + loExpedienteDTO.getExpedienteId());
			    	}else{
			    		LOGGER.info("**** Error al actualizar el estatus del expediente ****");
			    	}

				}
	    	}
		    /**
			 * CAMBIA EL CATDISCRIMINANTE DEL EXPEDIENTE (OPCIONAL)
			 */
		    if(loExpedienteDTO != null && loExpedienteDTO.getDiscriminante() != null && loExpedienteDTO.getDiscriminante().getCatDiscriminanteId() != null
		    		&& loExpedienteDTO.getDiscriminante().getCatDiscriminanteId() > 0){
		    	actualizarExpedienteService.actualizarCatDiscriminanteDeExpediente(loExpedienteDTO);
		    }
		    
		    
		    /**
		     * ESTA FUNCIONALIDAD ES EXCLUSIVA PARA LA SOLICITUD DE DEFENSOR
		     */
		    if(actividad.equals(Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId().longValue())){
		    	
		    	if(!confInstitucion.getConfInstitucionId().equals(Instituciones.DEF.getValorId().longValue())){
		    		solicitarDefensorService.solicitarDefensor(loExpedienteDTO,
		    				aoGuardadoDefinitivo.getDocumentoDTO());		    		
		    	}else{
		    		solicitarDefensorService.solicitarDefensorDesdeDefensoria(aoGuardadoDefinitivo);
		    	}
		    }
		    
		    
		    /**
			 * SECCION ENCARGADA DE REGISTRAR UNA SOLICITUD (OPCIONAL)
			 */
		    if((actividad.equals(Actividades.ELABORAR_OFICIO_INVESTIGACION_POLICIAL.getValorId().longValue()) || 
		       actividad.equals(Actividades.SOLICITAR_ATENCION_MEDICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS.getValorId().longValue()) ||
		       actividad.equals(Actividades.SOLICITAR_ORIENTACION_LEGAL_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS.getValorId().longValue()) ||
		       actividad.equals(Actividades.SOLICITAR_SEGURIDAD_POLICIAL_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS.getValorId().longValue()) ||
		       actividad.equals(Actividades.ELABORAR_OFICIO_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId().longValue())) && 
		       aoGuardadoDefinitivo.getSolicitudDTO() != null ){
		    	if (actividad.equals(Actividades.ELABORAR_OFICIO_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId())){
		    		aoGuardadoDefinitivo.getSolicitudDTO().setDocumentoId(idDocumentoRegistrado);
		    	}
		    	registrarSolicitudService.registrarSolicitud(aoGuardadoDefinitivo.getSolicitudDTO());
		    }
		    
		    if(actividad.equals(Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO.getValorId().longValue())){
				
		    	 LOGGER.info("aoGuardadoDefinitivo.solicitud"+aoGuardadoDefinitivo.getSolicitudDTO());
				enviarAcuseDeReciboDeSolicitudDeDefensorServiceImpl
						.enviarAcuseDeReciboDeSolicitudDeDefensorService(
								new SolicitudDefensorDTO(aoGuardadoDefinitivo
										.getSolicitudDTO().getDocumentoId()),
								new DocumentoDTO(idDocumentoRegistrado));
		    }
			if (actividad
					.equals(Actividades.GENERAR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION
							.getValorId().longValue())) {
				
				LOGGER.info("GuardarDocumentoService ACTIVIDAD:::::GENERAR_ACUSE_DE_RECIBO_DE_CARPETA_DE INVESTIGACION");
				// Seteamos el id del documento que se acaba de crear
				if (aoGuardadoDefinitivo.getDocumentoDTO() != null) {
					aoGuardadoDefinitivo.getDocumentoDTO().setDocumentoId(
							idDocumentoRegistrado);
				}
				enviarAcuseDeReciboDeCarpetaDeInvestigacionService
						.enviarAcuseDeReciboDeCarpetaDeInvestigacionService(aoGuardadoDefinitivo);
			}
		    
			LOGGER.debug("Cambiando al estatus a la solicitud: " + EstatusSolicitud.RESPONDIDA.getValorId());
			if(Actividades.SOLICITAR_MAS_INFORMACION_DICTAMEN.getValorId().equals(idActuacion) && aoGuardadoDefinitivo.getSolicitudPeritoId()!=null 
					&& !aoGuardadoDefinitivo.getSolicitudPeritoId().equals(0L)){
		        Solicitud solBD = this.solDao.read(aoGuardadoDefinitivo.getSolicitudPeritoId());
		        solBD.setEstatus(new Valor(EstatusSolicitud.RESPONDIDA.getValorId()));
		        this.solDao.update(solBD);

			}
			
			/**
			 * Cuando el rol de Policia Ministerial genera un acuerdo de abstencion debe de cambiar el 
			 * estatus de las solicitudes de tipo Investigacion by RGG
			 */
		    if(actividad.equals(Actividades.ELABORAR_ACUERDO_DE_ABSTENCION.getValorId().longValue())){
		    	Solicitud loFiltro = new Solicitud();
		    	NumeroExpediente loNumeroExpediente = new NumeroExpediente();
		    	loNumeroExpediente.setExpediente(new Expediente(loExpedienteDTO.getExpedienteId()));
		    	loFiltro.setTipoSolicitud(new Valor(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId()));
		    	loFiltro.setNumeroExpediente(loNumeroExpediente);
		    	
				List<Solicitud> lstSolicitudes = solDao.consultarSolicitudPorFiltro(loFiltro, null, null, null);
					for (Solicitud solicitud : lstSolicitudes) {
						Solicitud loSolicitud = solDao.read(solicitud.getDocumentoId());
						loSolicitud.setEstatus(new Valor(EstatusSolicitud.CERRADA.getValorId()));
						solDao.update(loSolicitud);
					}
		    }
		    
		    
		    RolDTO rolDTO = aoGuardadoDefinitivo.getUsuario().getRolACtivo().getRol();
		    if( rolDTO.getRolId() == Roles.PERITOAMP.getValorId() 
		    		&& idDocumentoRegistrado != null && idDocumentoRegistrado > 0L
		    		&& aoGuardadoDefinitivo.getSolicitudPeritoId() != null && aoGuardadoDefinitivo.getSolicitudPeritoId() > 0L
		    		&& loFuncionarioDTO.getClaveFuncionario() != null && loFuncionarioDTO.getClaveFuncionario() > 0L){
		    	
		    	RelacionSolicitudDocumentoFuncionario relacion = new RelacionSolicitudDocumentoFuncionario();
		    	
		    	Solicitud solicitud = new Solicitud();
		    	solicitud.setDocumentoId(aoGuardadoDefinitivo.getSolicitudPeritoId());
		    	relacion.setSolicitud(solicitud);
		    	
		    	Documento documento = new Documento(idDocumentoRegistrado);
		    	relacion.setDocumento(documento);
		    	
		    	Funcionario funcionario = new Funcionario(loFuncionarioDTO.getClaveFuncionario());
		    	relacion.setFuncionario(funcionario);
		    	relacionSolicitudDocumentoFuncionarioDAO.create(relacion);
		    }
			
			
			
		    Valor loValor = valorDAO.read(idActuacion);
		    LOGGER.info("****************************INFO ACTUACION**********************************");
		    LOGGER.info("* Actuacion seleccionada...: " + idActuacion);
		    LOGGER.info("* Nombre Actuacion seleccio: " + loValor.getValor());
		    LOGGER.info("* Id Numero Expediente.....: " + loExpedienteDTO.getNumeroExpedienteId());
		    LOGGER.info("* Id Expediente............: " + loExpedienteDTO.getExpedienteId());
		    if(loExpedienteDTO.getEstatus() != null){
		    	LOGGER.info("* Estatus Actual...........: " + loExpedienteDTO.getEstatus().getIdCampo());
		    }
		    LOGGER.info("* Nuevo Estatus............: " + idNuevoEstatusNumExp);
		    LOGGER.info("* Jerarquia Organizacional.: " + loExpedienteDTO.getArea().getAreaId());
		    LOGGER.info("****************************************************************************");
	    }
	    
		return idDocumentoRegistrado;
	}

	@Override
	public RelacionDocumentoDTO guardarRelacionDocumento(
			RelacionDocumentoDTO relacion) {
		
		RelacionDocumento relacionBD = RelacionDocumentoTransformer.transformar(relacion);
		Long relacionId = relacionDocumentoDAO.create(relacionBD);
		relacion.setRelacionId(relacionId);
		return relacion;
	}
        
        @Transactional
	@Override
	public Long guardarOficioEnajenacion(DocumentoDTO documentoDTO,String ids)
			throws NSJPNegocioException {
                        Documento documento=new Documento();
			DocumentoTransformer.tranformarDocumentoUpdate(documento, documentoDTO);
			
                        Long idDocumento = 0L;
                        idDocumento=documentoDAO.create(documento);
                        
                        StringTokenizer st=new StringTokenizer(ids, ",");
                        List<Long> idBienes = new ArrayList<Long>();
                        while(st.hasMoreTokens())
                            idBienes.add(new Long(st.nextToken()));
                        objetoDAO.asignaOficioEnajenacion(idDocumento,idBienes); 
                        return idDocumento;
	}

}
