
package mx.gob.segob.nsjp.service.sentencia.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.URLUtils;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.programa.AsignacionCentroDetencionDAO;
import mx.gob.segob.nsjp.dao.programa.RemisionDAO;
import mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.DelitoPersonaWSDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.AsignacionCentroDetencion;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Remision;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Sentencia;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.caso.BuscarCasoService;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentosXTipoDocumentoService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviardocumento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.NombreDemograficoTransformer;
import mx.gob.segob.nsjp.service.programa.impl.transform.AsignacionProgramaTransformer;
import mx.gob.segob.nsjp.service.sentencia.SentenciaService;
import mx.gob.segob.nsjp.service.sentencia.impl.transform.SentenciaTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AntonioBV
 *
 */
@Service("sentenciaService")
@Transactional
public class SentenciaServiceImpl implements SentenciaService{

	@Autowired
	private SentenciaDAO sentenciaDAO;
	
	@Autowired
	private InvolucradoDAO involucradoDAO;
	
	@Autowired
	private ExpedienteDAO expedienteDAO;
	
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	
	@Autowired
	private CasoDAO casoDAO;
	
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
	
	@Autowired
	private FuncionarioDAO funcionarioDAO;
	
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	
	@Autowired
	private AsignacionCentroDetencionDAO asignacionCentroDetencionDAO;
	
	@Autowired
	private BuscarCasoService buscarCasoService;
	
	@Autowired
	private ConsultarDocumentosXTipoDocumentoService consultarDocumentoService;
	
	@Autowired
	private GuardarDocumentoService documentoService;
	
	@Autowired
	private RemisionDAO remisionDAO;
	
	@Autowired
	private DelitoDAO delitoDAO;
	
	@Autowired
	private DelitoPersonaDAO delitoPersonaDAO;
	
	private static final Logger LOG = Logger.getLogger(SentenciaServiceImpl.class);
	
	@Override
	public List<SentenciaDTO> consultarNUS(NombreDemograficoDTO nombreDemograficoDTO)
			throws NSJPNegocioException {
		
		List<SentenciaDTO> lstResultadoSentenciaDTO = new ArrayList<SentenciaDTO>();
		SentenciaDTO sentenciaDTO  = null;
		
		NombreDemografico nombreDemografico = NombreDemograficoTransformer.transformarNombreDemografico(nombreDemograficoDTO);
		List<Sentencia> lstSentencias = null;
		
		if(nombreDemografico != null && nombreDemografico.getCurp() != null && !nombreDemografico.getCurp().isEmpty() ){
			lstSentencias = sentenciaDAO.consultarNUS(nombreDemografico, Boolean.TRUE);
		}
		
		if (lstSentencias != null && !lstSentencias.isEmpty()) {
			sentenciaDTO = SentenciaTransformer.transformar(lstSentencias.get(0));
			sentenciaDTO.setEsUnicoNUS(Boolean.TRUE);
			lstResultadoSentenciaDTO.add(sentenciaDTO);
		} else {
			
			lstSentencias = sentenciaDAO.consultarNUS(nombreDemografico, Boolean.FALSE);
			if (lstSentencias != null && !lstSentencias.isEmpty()) {
				
				for (Sentencia sentencia : lstSentencias){
					sentenciaDTO = SentenciaTransformer.transformar(sentencia);
					sentenciaDTO.setEsUnicoNUS(Boolean.FALSE);
					lstResultadoSentenciaDTO.add(sentenciaDTO);
				}
			}
		}
			
			
		
		
			
				
		return lstResultadoSentenciaDTO;
	}


	@Override
	public SentenciaDTO consultarSentenciaCompleta(SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		
		Sentencia sentencia = sentenciaDAO.read(sentenciaDTO.getSentenciaId());
		
		Involucrado involucrado = involucradoDAO.read(sentencia.getInvolucrado().getElementoId());
		
		Expediente expediente = expedienteDAO.read(sentencia.getNumeroExpediente().getNumeroExpedienteId());
		
		SentenciaDTO sentenciaCompletaDTO = SentenciaTransformer.transformar(sentencia);
		
		InvolucradoDTO involucradoDTO = InvolucradoTransformer.transformarInvolucrado(involucrado);
		
		ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);
		
		sentenciaCompletaDTO.setInvolucradoDTO(involucradoDTO);
		
		sentenciaCompletaDTO.setNumeroExpedienteDTO(expedienteDTO);
		
		return sentenciaCompletaDTO;
	}

	@Override
	public void crearSentencia(SentenciaWSDTO sentenciaWSDTO) throws NSJPNegocioException {
	
		String cNumeroGeneralCaso = null;
		String folioInvolucrado  = null;
		Caso caso = null;
		
		Sentencia sentencia = SentenciaTransformer.transformarLocalWSDTO2Entity(sentenciaWSDTO);
		
		if (sentencia.getInvolucrado() == null || sentencia.getNumeroExpediente() == null){
			LOG.error("No llega el Involucrado de la sentencia: "+sentencia.getInvolucrado()
					+" o el numero de expediente: " +sentencia.getNumeroExpediente());
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} 
		
		if(sentenciaWSDTO.getNumeroExpedienteDTO() != null) {
			if (sentenciaWSDTO.getNumeroExpedienteDTO().getCasoWSDTO() != null){
				cNumeroGeneralCaso = sentenciaWSDTO.getNumeroExpedienteDTO().getCasoWSDTO().getNumeroGeneralCaso();
			}
		}

		if(cNumeroGeneralCaso != null){
			 caso = casoDAO.consultarCasoPorNumeroCaso(cNumeroGeneralCaso);	
		}

		if (caso != null) {
			Involucrado involucrado = sentencia.getInvolucrado();
			
			folioInvolucrado = involucrado.getFolioElemento();
			
			involucrado = involucradoDAO.obtenerInvolucradoPorCasoYFolioElemento(involucrado, caso);
			
			Expediente expediente = expedienteDAO.buscarExpedientePorCasoFolioInvolucrado(cNumeroGeneralCaso, folioInvolucrado);
			
			if (expediente != null ) {
				
				NumeroExpediente numeroExpediente = obtenerNumeroExpedienteParaSentencia(expediente);
						
				sentencia = SentenciaTransformer.quitarIDs(sentencia);
				sentencia.setInvolucrado(involucrado);
				sentencia.setNumeroExpediente(numeroExpediente);	
				Long sentenciaId = sentenciaDAO.create(sentencia);
				
				asociarDocumentosAdjuntosExpediente(sentenciaWSDTO.getNumeroExpedienteDTO().getDocumentosDTO(), 
						new ExpedienteDTO(expediente.getExpedienteId()));
				
				sentencia.setSentenciaId(sentenciaId);
				persistirRemisiones(sentencia.getRemisions(), sentencia);
				
				ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
				
				//Solamente se persisten las relaciones delito persona en el caso de que la institucion sea SSP(RS).
				if (institucionActual.getConfInstitucionId().equals(Instituciones.SSP.getValorId())){
					crearDelitosPersonaSentencia(expediente, involucrado, sentenciaWSDTO.getNumeroExpedienteDTO().getDelitosPersona());					
				}
				
			} else {
				LOG.error("No se encontro el expediente en la base de datos " +
						"local utilizando como filtros el folio del elemento: " +
						involucrado.getFolioElemento() + " y el numero de caso: " + cNumeroGeneralCaso);
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
		} else {
			LOG.error("No se encontro el numero de caso: " + cNumeroGeneralCaso +
					"asociado a la sentencia dentro de la base de datos local");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);			
		}
	}

	@Override
	public boolean enviarSentencia(SentenciaDTO sentenciaDTO, Instituciones institucion) throws NSJPNegocioException {
		
		//sentenciaDTO = this.consultarSentenciaCompleta(sentenciaDTO);
		try {
			clienteGeneralService.enviarSentencia(sentenciaDTO, institucion);
		} catch(Exception e) {
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
		return false;
	}

	
	public SentenciaDTO realizarAltasCambiosASentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		SentenciaDTO actualDTO = null; 
		try{
			Sentencia sentencia = null;
			if(sentenciaDTO != null){
				sentencia = SentenciaTransformer.transformar(sentenciaDTO);
				if (sentencia != null 
						&& sentencia.getSentenciaId() != null ){
					sentenciaDAO.saveOrUpdate(sentencia);	
					sentencia = sentenciaDAO.read(sentenciaDTO.getSentenciaId());
				} else {
					Long sentenciaId = sentenciaDAO.create(sentencia);
					sentencia = sentenciaDAO.read(sentenciaId);
				}
				actualDTO = SentenciaTransformer.transformar(sentencia);
			}
		}catch(Exception e){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES,e);
		}
		return actualDTO;
		
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la l&oacute;gica para asignar el n&uacute;mero de expediente a 
	 * la sentencia que se va a crear dentro de la base de datos, en el caso de que ya exista un 
	 * n&uacute;mero de expediente asignado al expediente pasado como par&aacute;metro, se regresa 
	 * dicho n&uacute;mero de expediente, en caso contrario se genera un nuevo n&uacute;mero de 
	 * expediente dependiendo de la instituci&oacute;n en la cual se va a persistir la sentencia. 
	 * @param expediente - Expediente del cual se va a obtener el N&uacute;mero de expediente asociado.
	 * @return NumeroExpediente - Número de expediente que se va a asociar a la sentencia que se va a 
	 * 							  persistir.
	 * @throws NSJPNegocioException - En el caso de que no se cuente con los par&aacute;metros necesarios
	 * 								  para obtener o generar el n&uacute;mero de expediente. 
	 */
	private NumeroExpediente obtenerNumeroExpedienteParaSentencia(Expediente expediente) throws NSJPNegocioException{
		NumeroExpediente numExp = null;

		numExp = numeroExpedienteDAO.obtenerNumeroExpedienteXExpediente(expediente.getExpedienteId());

		if (numExp == null 
				|| numExp.getNumeroExpedienteId() == null 
				|| numExp.getNumeroExpedienteId() < 1L){

			ConfInstitucion institucionActual = confInstitucionDAO.consultarIntitucionActual();
			Long idInstitucion = institucionActual.getConfInstitucionId();
			Long rolId = null;

			if (idInstitucion.equals(Instituciones.PGJ.getValorId())){
				rolId = Roles.AGENTEMP.getValorId();
			}else if (idInstitucion.equals(Instituciones.DEF.getValorId())){
				rolId = Roles.DEFENSOR.getValorId(); 			
			}else if (idInstitucion.equals(Instituciones.SSP.getValorId())){
				rolId = Roles.DSE.getValorId(); 				
			}else{
				rolId = Roles.DSE.getValorId();
			}
			
			List<Funcionario> lstFuncionarios = funcionarioDAO.consultarFuncionariosPorRolMultiRol(rolId);
			Rol rolAsignado = null;

			if (lstFuncionarios != null){
				Usuario usuario = null;
				for (Funcionario funcionario : lstFuncionarios) {
					if (funcionario.getUsuario() != null){
						usuario = funcionario.getUsuario();
						for (UsuarioRol ur : usuario.getUsuarioRoles()){
							if (ur.getRol().getRolId().equals(rolId)){
								rolAsignado = ur.getRol();
								break;
							}
						}
						break;
					}
				}
				if (usuario != null){
					Areas areaAsignada = Areas.values()[rolAsignado.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().intValue()];

					ExpedienteDTO nuevoExp = new ExpedienteDTO();
					nuevoExp.setExpedienteId(expediente.getExpedienteId());
					nuevoExp.setArea(new AreaDTO(areaAsignada));
					nuevoExp.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));

					UsuarioDTO usuarioDTO = UsuarioTransformer.transformarUsuario(usuario);
					nuevoExp.setUsuario(usuarioDTO);

					nuevoExp = asignarNumeroExpedienteService.asignarNumeroExpediente(nuevoExp);
					numExp = numeroExpedienteDAO.read(nuevoExp.getNumeroExpedienteId());

					numExp.setEstatus(new Valor(EstatusExpediente.ENVIADO.getValorId()));

					numeroExpedienteDAO.update(numExp);

				} else {
					LOG.error("No hay usuarios registrados con rol de: "+ rolAsignado.getRolId() +" - "
							+ rolAsignado.getNombreRol() + " en esta institucion");
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
			} else {
				LOG.error("No hay funcionarios registrados con el rol con identificador : "+ rolId +" en esta institucion");
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
		}
		return numExp;
	}


	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#consultarAsignacionCentroDetencionActual(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	public AsignacionCentroDetencionDTO consultarAsignacionCentroDetencionActual(
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		
		Sentencia sentencia = SentenciaTransformer.transformar(sentenciaDTO);
		AsignacionCentroDetencion asignacionCentroActual = null;
		
		asignacionCentroActual = asignacionCentroDetencionDAO.consultarAsignacionCentroDetencionActual(sentencia);
		
		return AsignacionProgramaTransformer.transformar(asignacionCentroActual);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#consultarInvolucradosXSituacionSinSentencia(mx.gob.segob.nsjp.dto.catalogo.ValorDTO)
	 */
	@Override
	public List<InvolucradoDTO> consultarInvolucradosXSituacionSinSentencia(
			ValorDTO situacionJuridica) throws NSJPNegocioException {
		List<InvolucradoDTO> involucradosDTO = null;
		Valor situacion = ValorTransformer.transformar(situacionJuridica);
		List<Involucrado> involucrados = involucradoDAO.consultarInvolucradosXSituacionSinSentencia(situacion);
		if (involucrados != null
				&& !involucrados.isEmpty()){
			involucradosDTO = new ArrayList<InvolucradoDTO>();
			for (Involucrado inv : involucrados){
				involucradosDTO.add(InvolucradoTransformer.transformarInvolucrado(inv));
			}
		}
		return involucradosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#registrarSentenciaEjecutoriada(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO, mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO)
	 */
	@Override
	public void registrarSentenciaEjecutoriada(SentenciaDTO sentenciaDTO,
			InvolucradoDTO involucradoDTO) throws NSJPNegocioException {
		NumeroExpediente carpetaEjecucion = numeroExpedienteDAO.obtenerCarpetaEjecucionPorInvolucrado(involucradoDTO.getElementoId()); 
		
		if (carpetaEjecucion==null) {
			LOG.debug("/**** COMIENZA GENERACION DE CARPETA DE EJECUCION ****/");
			
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setExpedienteId(involucradoDTO.getExpedienteDTO().getExpedienteId());
			expedienteDTO.setArea(new AreaDTO(Areas.DEPARTAMENTO_CAUSA));
			expedienteDTO.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));
			expedienteDTO.setUsuario(involucradoDTO.getUsuario());
			expedienteDTO.setNumeroExpedienteId(involucradoDTO.getExpedienteDTO().getNumeroExpedienteId());
			expedienteDTO.setEstatus(new ValorDTO(EstatusExpediente.ENVIADO.getValorId()));
			
			ExpedienteDTO nuevoExp = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);
						
			Sentencia sentencia = SentenciaTransformer.transformar(sentenciaDTO);
			
			NumeroExpediente numeroExpediente = new NumeroExpediente();
			numeroExpediente.setNumeroExpedienteId(nuevoExp.getNumeroExpedienteId());
			sentencia.setNumeroExpediente(numeroExpediente);
			sentencia.setCnumeroCausa(nuevoExp.getNumeroExpediente());
			
			Involucrado involucradoSentencia  = involucradoDAO.read(involucradoDTO.getElementoId());
			sentencia.setInvolucrado(involucradoSentencia);
			Long sentenciaId = sentenciaDAO.create(sentencia);
			
			sentencia.setSentenciaId(sentenciaId);
			persistirRemisiones(sentencia.getRemisions(), sentencia);

			CasoDTO casoDTO = buscarCasoService.consultarCasoPorExpediente(expedienteDTO);
			expedienteDTO.setCasoDTO(casoDTO);
			sentenciaDTO.setSentenciaId(sentenciaId);
			
			//Se agregan las consultas de los documentos para auto de inicio y sentencia.
			List<ValorDTO> tiposDocConsulta = new ArrayList<ValorDTO>();
			tiposDocConsulta.add(new ValorDTO(TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId()));
			tiposDocConsulta.add(new ValorDTO(TipoDocumento.SENTENCIA_EJECUTORIADA.getValorId()));
			
			List<DocumentoDTO> lstDocAdj = consultarDocumentoService.consultarDocumentosPorExpedienteYTipos(expedienteDTO, tiposDocConsulta);
			
			expedienteDTO.setDocumentosDTO(lstDocAdj);
			
			sentenciaDTO.setInvolucradoDTO(involucradoDTO);
			sentenciaDTO.setNumeroExpedienteDTO(expedienteDTO);			
			sentenciaDTO.setCnumeroCausa(nuevoExp.getNumeroExpediente());
			 
	        List<ConfInstitucion> instituciones = confInstitucionDAO.consultarDemasIntituciones();

		    for (ConfInstitucion inst : instituciones) {
		    	try {
		    		if (URLUtils.validaURLWSDL(inst.getUrlInst())){
				    	Instituciones destino = Instituciones.getByValor(inst.getConfInstitucionId());
				    	enviarSentencia(sentenciaDTO, destino );
		    		}
				} catch(Exception e) {
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES,e);
				}
		    }
		}
	}
	

	
	/**
	 * M&eacute;todo que lleva a cabo la persistencia de los documentos adjuntos
	 * enviados a trav&eacute;s de un web service y se asocian al expediente pasado
	 * como par&aacute;metro.
	 * @param lstDocumentosAdjuntos - List<DocumentoWSDTO> que se transformar&aacute;
	 * 								  a DocumentoDTO y se asociar&aacute; al expediente
	 * 								  pasado como par&aacute;metro.
	 * @param expedienteDTO - ExpedienteDTO al cual se asociar&aacute;n los documentos
	 * 						  adjuntos.
	 */
	private void asociarDocumentosAdjuntosExpediente (List <DocumentoWSDTO> lstDocumentosAdjuntos, ExpedienteDTO expedienteDTO){
		//Asociar los documentos adjuntos al momento de enviar la sentencia al expediente
		if (lstDocumentosAdjuntos!= null 
				&& !lstDocumentosAdjuntos.isEmpty()) {
        	for (DocumentoWSDTO documentoWSDTO : lstDocumentosAdjuntos) {
        		DocumentoDTO documentoDTO = DocumentoWSDTOTransformer.transformarDTO(documentoWSDTO);
        		documentoDTO.setEsGuardadoParcial(false);
        		//se obtiene el tipo actividad para saber el tipo actividad origen
        		Long tipoActividad = null;
        		if (documentoDTO.getTipoDocumentoDTO().getIdCampo().equals(TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId())){
        			tipoActividad = Actividades.ADJUNTAR_AUTO_DE_PROCEDIMIENTO_DE_EJECUCION.getValorId();
        		}else if (documentoDTO.getTipoDocumentoDTO().getIdCampo().equals(TipoDocumento.SENTENCIA_EJECUTORIADA.getValorId())){
        			tipoActividad = Actividades.ADJUNTAR_SENTENCIA_EJECUTORIADA.getValorId();
        		}
        		try {
					documentoService.guardarDocumento(documentoDTO, expedienteDTO, tipoActividad,null);
				} catch (NSJPNegocioException e) {
					LOG.error(e.getMessage(), e);
				}
        	}
		}
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la persistencia de las remisiones 
	 * que se encuentran asociadas a una sentencia.
	 * @param remisiones - Las remisiones que se van a persistir.
	 * @param sentencia - La sentencia con la cual se van a asociar las remisiones a
	 * 					  persistir dentro de la base de datos.
	 */
	private void persistirRemisiones(Set<Remision> remisiones, Sentencia sentencia){
		if (remisiones != null
				&& !remisiones.isEmpty()){
			for (Remision rem : sentencia.getRemisions()){
				rem.setSentencia(sentencia);
				remisionDAO.create(rem);
			}
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#actualizarSentenciaExterna(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public void actualizarSentenciaExterna(SentenciaDTO sentenciaDTO,
			Instituciones institucion) throws NSJPNegocioException {
		clienteGeneralService.actualizarSentencia(sentenciaDTO, institucion);
	}


	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#consultarSentenciasPorIdsNumExp(java.util.List)
	 */
	@Override
	public Map<Long, SentenciaDTO> consultarSentenciasPorIdsNumExp(
			List<Long> idsNumExp) throws NSJPNegocioException {
		Map<Long,SentenciaDTO> mapaSentencias = new HashMap<Long,SentenciaDTO>();
		
		List<Sentencia> sentenciasBD = sentenciaDAO.consultarSentenciasPorIdsNumExp(idsNumExp);
		if (sentenciasBD != null 
				&& !sentenciasBD.isEmpty()){
			for (Sentencia sentencia : sentenciasBD){
				Long idNumeroExpediente = sentencia.getNumeroExpediente().getNumeroExpedienteId();
				if(!mapaSentencias.containsKey(idNumeroExpediente)){
					mapaSentencias.put(idNumeroExpediente, SentenciaTransformer.transformar(sentencia));
				}
			}
		}
		return mapaSentencias;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de las relaciones 
	 * Delito-Persona para el involucrado asociado a una sentencia.
	 * 
	 * @param expediente - Expediente asociado a la sentencia.
	 * 
	 * @param probableResponsable - Involucrado del cual se crear&aacute;n
	 * 								las relaciones delito-persona. 
	 * 
	 * @param delitosPersona - List<DelitoPersonaWSDTO> con las relaciones
	 * 						   Delito-Persona asociadas al involucrado.
	 */
	private void crearDelitosPersonaSentencia(Expediente expediente,
			Involucrado probableResponsable,
			List<DelitoPersonaWSDTO> delitosPersona){
		//Al momento de crear la sentencia no hay delitos persona para ning&uacute;n involucrado.
		Map<String, Delito> delitosExpediente = new HashMap<String, Delito>();
		Map<String, Involucrado> victimasExpediente = new HashMap<String, Involucrado>();
		
		if (delitosPersona != null
				&& !delitosPersona.isEmpty()){
			for (DelitoPersonaWSDTO wsdto : delitosPersona){
				if (!delitosExpediente.containsKey(wsdto.getClaveInterIntitucionalDelito())){
					Delito delitoCometido = delitoDAO.consultarDelitoPorExpedienteYClaveInstitucional(expediente, wsdto.getClaveInterIntitucionalDelito());
					if (delitoCometido != null){
						delitosExpediente.put(delitoCometido.getCatDelito().getClaveInterInstitucional(), delitoCometido);
					}
				}
				if (!victimasExpediente.containsKey(wsdto.getFolioVictima())){
					Involucrado victima = involucradoDAO.obtenerInvolucradoByFolioElemento(wsdto.getFolioVictima());
					if (victima != null){
						victimasExpediente.put(victima.getFolioElemento(), victima);
					}
				}
			}
			persistirRelacionesDP(delitosPersona, probableResponsable, delitosExpediente, victimasExpediente);
		}
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la persistencia de las relaciones Delito - Persona
	 * que se env&iacute;an a la instituci&oacute;n de reinserci&oacute;n social.
	 * 
	 * @param delitosPersona - Lista de DelitosPersonaWSDTO con la informaci&oacute;n de la 
	 * 						   relaci&oacute;n que se debe de crear en RS.
	 * @param probableResponsable - Probable responsable con el cual se van a asociar los
	 * 								Delitos - Persona.
	 * @param delitosExpediente - Mapa que contiene los delitos que se encuentran asociados 
	 * 							  al expediente y que se van a asociar al probable responsable.
	 * @param victimasExpediente - Mapa que contiene las v&iacute;ctimas que se encuentran 
	 * 							   asociadas al expediente y que se van a asociar al probable
	 * 							   responsable.
	 */
	private void persistirRelacionesDP(List<DelitoPersonaWSDTO> delitosPersona, 
			Involucrado probableResponsable, Map<String, Delito> delitosExpediente, 
			Map<String, Involucrado> victimasExpediente){
		
		for(DelitoPersonaWSDTO wsdto : delitosPersona){
			DelitoPersona entity = new DelitoPersona();
			entity.setProbableResponsable(probableResponsable);
			entity.setDelito(delitosExpediente.get(wsdto.getClaveInterIntitucionalDelito()));
			Involucrado victima = victimasExpediente.get(wsdto.getFolioVictima());
			if (victima != null){
				entity.setVictima(victima);
			}
			delitoPersonaDAO.create(entity);
		}
	}
}