/**
 * Nombre del Programa : SolicitarPericialServiceImpl.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 03 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion del servicio para realizar la solicitud pericial
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoElementoDAO;
import mx.gob.segob.nsjp.dao.documento.RelacionSolicitudDocumentoFuncionarioDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.RelacionDocumentoElemento;
import mx.gob.segob.nsjp.model.SolicitudPericial;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudPericialTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar la solicitud pericial
 * 
 * @version 1.0
 * @author rgama
 * 
 */
@Service
@Transactional
public class SolicitarPericialServiceImpl implements SolicitarPericialService {

	private final static Logger logger = Logger
			.getLogger(SolicitarPericialServiceImpl.class);

	@Autowired
	private SolicitudPericialDAO solicitudPericialDAO;
	@Autowired 
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private RelacionDocumentoElementoDAO relacionDocumentoElementoDAO;
	@Autowired
	private RelacionSolicitudDocumentoFuncionarioDAO relacionSolicitudDocumentoFuncionarioDAO;

	@Override
	public SolicitudPericialDTO registrarSolicitudPericial(
			SolicitudPericialDTO solicitudPericialDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA REGISTRAR UN SOLICITUD PERICIAL ****/");
		}
		SolicitudPericial solicitudPericial = SolicitudPericialTransformer
				.solPericialTransformer(solicitudPericialDTO);
		// Atributos harcodeados para poder registrar una solicitud pericial
		solicitudPericial.setFechaCreacion(new Date());
		solicitudPericial.setForma(new Forma(1L));
		solicitudPericial.setTipoDocumento(new Valor(82L));// Solicitud
		solicitudPericial.setFolioDocumento("Folio Documento");
		solicitudPericial.setNombreDocumento("Nombre Documento");

		if (solicitudPericialDTO.getDocumentoId() == null) {
			solicitudPericial.setFolioSolicitud(generarFolioSolicitudService
					.generarFolioSolicitud());
			solicitudPericialDTO.setDocumentoId(solicitudPericialDAO
					.create(solicitudPericial));
		} else {
			solicitudPericialDAO.saveOrUpdate(solicitudPericial);

		}
		return solicitudPericialDTO;
	}

	@Autowired
	private GenerarFolioSolicitudService generarFolioSolicitudService;

	@Override
	public SolicitudPericialDTO guardarParcialmenteSolicitudPericial(
			SolicitudPericialDTO solicitudPericialDTO)
			throws NSJPNegocioException {

		SolicitudPericial solicitudPericial = SolicitudPericialTransformer
				.solPericialTransformer(solicitudPericialDTO);
		// Atributos harcodeados para poder registrar/actualizar una solicitud
		// pericial
		solicitudPericial.setFechaCreacion(new Date());
		solicitudPericial.setForma(new Forma(1L));
		solicitudPericial.setTipoDocumento(new Valor(82L));// Solicitud
		solicitudPericial.setFolioDocumento("Folio Documento");
		solicitudPericial.setNombreDocumento("Nombre Documento");

		solicitudPericial.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));
		solicitudPericial.setPuestoUsuarioSolicitante("ABOGADO DEFENSOR");
		solicitudPericial.setFechaModificacion(new Date());

		if (solicitudPericialDTO.getDocumentoId() != null) {
			solicitudPericial.setDocumentoId(solicitudPericialDTO
					.getDocumentoId());
			solicitudPericialDAO.saveOrUpdate(solicitudPericial);
			return null;
		} else {
			solicitudPericial.setFolioSolicitud(generarFolioSolicitudService
					.generarFolioSolicitud());
			Long idCreado = solicitudPericialDAO.create(solicitudPericial);
			SolicitudPericialDTO nuevoDTO = new SolicitudPericialDTO();
			nuevoDTO.setDocumentoId(idCreado);
			return nuevoDTO;
		}

	}

	/**
	 * Metodo que permite recuperar a un Coordinador Defensor las solicitudes
	 * hechas por los abogados defensores
	 */
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesDeDictamen(
			Long estatusSolicitud) throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA CONSULTAR SOLICITUDES PERICIALES****/");
		}
		List<SolicitudPericial> loSolicitudes = solicitudPericialDAO
				.consultarSolicitudesPericiales(
						TiposSolicitudes.DICTAMEN.getValorId(),
						estatusSolicitud,
						"ABOGADO DEFENSOR");
		List<SolicitudPericialDTO> loSolicitudesDTO = new ArrayList<SolicitudPericialDTO>();
		if (loSolicitudes != null && loSolicitudes.size() > 0) {
			for (SolicitudPericial solicitudPericial : loSolicitudes) {
				SolicitudPericialDTO loSp = new SolicitudPericialDTO();
				loSp = SolicitudPericialTransformer
						.solPericialTransformer(solicitudPericial);
				loSolicitudesDTO.add(loSp);
			}
		}
		return loSolicitudesDTO;
	}

	/**
	 * Metodo que permite registrar a un Coordiandor Defensor una Solicitud
	 * Pericial As� como el estado de la solicitud a NUEVA
	 * 
	 * @param solicitudPericialDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	@Override
	public SolicitudPericialDTO registrarSolicitudPericialCD(
			SolicitudPericialDTO solicitudPericialDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA REGISTRAR UN SOLICITUD PERICIAL - Coord. Pericial****/");
		}

		SolicitudPericial solicitudPericial = SolicitudPericialTransformer
				.solPericialTransformer(solicitudPericialDTO);
		// Atributos harcodeados para poder registrar una solicitud pericial
		solicitudPericial.setFechaCreacion(new Date());
		solicitudPericial.setForma(new Forma(1L));
		solicitudPericial.setTipoDocumento(new Valor(82L));// Solicitud
		solicitudPericial.setFolioDocumento("Folio Documento");
		solicitudPericial.setNombreDocumento("Nombre Documento");

		solicitudPericial.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));
		solicitudPericial
				.setPuestoUsuarioSolicitante("COORDINADOR PERICIAL DE DEFENSORIA");
		solicitudPericial.setFolioSolicitud(generarFolioSolicitudService
				.generarFolioSolicitud());
		Long idSolPericial = solicitudPericialDAO.create(solicitudPericial);

		return new SolicitudPericialDTO(idSolPericial);
	}

	/**
	 * Metodo que permite registrar/actualizar a un Coordiandor Defensor una
	 * Solicitud Pericial As� como el estado de la solicitud a PENDIENTE
	 * 
	 * @param solicitudPericialDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	@Override
	public SolicitudPericialDTO guardarParcialmenteSolicitudPericialCD(
			SolicitudPericialDTO solicitudPericialDTO)
			throws NSJPNegocioException {

		SolicitudPericial solicitudPericial = SolicitudPericialTransformer
				.solPericialTransformer(solicitudPericialDTO);
		// Atributos harcodeados para poder registrar/actualizar una solicitud
		// pericial
		solicitudPericial.setFechaCreacion(new Date());
		solicitudPericial.setForma(new Forma(1L));
		solicitudPericial.setTipoDocumento(new Valor(82L));// Solicitud
		solicitudPericial.setFolioDocumento("Folio Documento");
		solicitudPericial.setNombreDocumento("Nombre Documento");

		solicitudPericial.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));
		solicitudPericial
				.setPuestoUsuarioSolicitante("COORDINADOR PERICIAL DE DEFENSORIA");

		if (solicitudPericialDTO.getDocumentoId() != null) {
			solicitudPericial.setDocumentoId(solicitudPericialDTO
					.getDocumentoId());
			solicitudPericialDAO.saveOrUpdate(solicitudPericial);
			return null;
		} else {
			solicitudPericial.setFolioSolicitud(generarFolioSolicitudService
					.generarFolioSolicitud());
			return new SolicitudPericialDTO(
					solicitudPericialDAO.create(solicitudPericial));
		}

	}

	/**
	 * Metodo que permite recuperar a un Coordinador de Periciales(Defensoria)
	 * las solicitudes hechas por el Coordinador Defensor
	 */
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesCP(
			Long estatusSolicitud) throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA CONSULTAR SOLICITUDES PERICIALES - Coordinador de Periciales****/");
		}
		List<SolicitudPericial> loSolicitudes = solicitudPericialDAO
				.consultarSolicitudesPericiales(null,
						estatusSolicitud,
						"COORDINADOR_DE_DEFENSORES");
		List<SolicitudPericialDTO> loSolicitudesDTO = new ArrayList<SolicitudPericialDTO>();
		if (loSolicitudes != null && loSolicitudes.size() > 0) {
			for (SolicitudPericial solicitudPericial : loSolicitudes) {
				SolicitudPericialDTO loSp = new SolicitudPericialDTO();
				loSp = SolicitudPericialTransformer
						.solPericialTransformer(solicitudPericial);
				loSolicitudesDTO.add(loSp);
			}
		}
		return loSolicitudesDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#
	 * consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario
	 * (mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes,
	 * mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud,
	 * mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
	 */
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(
			TiposSolicitudes tipo, EstatusSolicitud estado, Puestos puesto)
			throws NSJPNegocioException {
	    if (logger.isDebugEnabled()) {
	        logger.debug("tipo :: " + tipo);
	        logger.debug("estado :: " + estado);
	        logger.debug("puesto :: " + puesto);
	    }
		List<SolicitudPericialDTO> resultado = new ArrayList<SolicitudPericialDTO>();
		List<SolicitudPericial> solicitudes = solicitudPericialDAO
				.consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(
						tipo, estado, puesto);
		for (SolicitudPericial sol : solicitudes) {
			resultado.add(SolicitudPericialTransformer
					.solPericialTransformer(sol));
		}

		return resultado;

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#
	 * consultarSolicitudesPericialesPorTipoEstatusAreaDestinatario
	 * (mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes,
	 * mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud,
	 * mx.gob.segob.nsjp.comun.enums.institucion.Areas)
	 */
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesPorTipoEstatusAreaDestinatario(
			TiposSolicitudes tipo, EstatusSolicitud estado, Long area)
			throws NSJPNegocioException {
	    if (logger.isDebugEnabled()) {
	        logger.debug("tipo :: " + tipo);
	        logger.debug("estado :: " + estado);
	        logger.debug("area :: " + area);
	    }
		List<SolicitudPericialDTO> resultado = new ArrayList<SolicitudPericialDTO>();
		List<SolicitudPericial> solicitudes = solicitudPericialDAO
				.consultarSolicitudesPericialesPorTipoEstatusAreaDestinatario(
						tipo, estado, area);
		for (SolicitudPericial sol : solicitudes) {
			resultado.add(SolicitudPericialTransformer
					.solPericialTransformer(sol));
		}

		return resultado;

	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#
	 * consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario
	 * (mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes,
	 * mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud,
	 * mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
	 */
	public Long consultarPadreSolicitudPericial(
				Long documentoHijo)
			throws NSJPNegocioException {
		
		SolicitudPericial solicitud=solicitudPericialDAO.read(documentoHijo);
		
		while(solicitud!=null && solicitud.getSolicitudPadre()!=null && solicitud.getSolicitudPadre().getDocumentoId().longValue()!=0L){
			documentoHijo=solicitud.getSolicitudPadre().getDocumentoId().longValue();
			solicitud=solicitudPericialDAO.read(documentoHijo);
		}
		
		return documentoHijo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#
	 * consultarSolicitudPericialPorId(java.lang.Long)
	 */
	@Override
	public SolicitudPericialDTO consultarSolicitudPericialPorId(Long documentoId)
			throws NSJPNegocioException {

		return SolicitudPericialTransformer
				.solPericialTransformer(solicitudPericialDAO.read(documentoId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#
	 * consultarSolicitudesPericialesPorEstatusYDestinatario
	 * (mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud,
	 * mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO)
	 */
	@Override
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesPorEstatusYDestinatario(
			EstatusSolicitud estatus, FuncionarioDTO destinatario, Boolean esCoorPerDef)
			throws NSJPNegocioException {
		List<SolicitudPericialDTO> resultados = new ArrayList<SolicitudPericialDTO>();
		List<SolicitudPericial> solicitudesEncontradas = solicitudPericialDAO
				.consultarSolicitudesPericialesPorEstatusYDestinatario(estatus,
						new Funcionario(destinatario.getClaveFuncionario()), esCoorPerDef);
		for (SolicitudPericial pericial : solicitudesEncontradas) {
			List<SolicitudPericial> soli=null;
			if(estatus.equals(EstatusSolicitud.EN_PROCESO)){
				soli=solicitudPericialDAO.consultarSolicitudesPericialPorFolioEstatusNoCerrado(pericial);
			}
			if(soli==null){
				resultados.add(SolicitudPericialTransformer
						.solPericialTransformer(pericial));
			}
			
		}

		return resultados;
	}

	@Override
	public Long asignarFuncionarioASolicitud(Long idPerito, Long idSolicitud)
			throws NSJPNegocioException, Exception {
		
		SolicitudPericial sOrigen = solicitudPericialDAO.read(idSolicitud);
		SolicitudPericial sDestino = new SolicitudPericial();		
		Funcionario fDestinatario = funcionarioDAO.read(idPerito);
		
		sOrigen.setEstatus(new Valor(EstatusSolicitud.EN_PROCESO.getValorId()));
		sDestino.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
		

		sDestino.setFuncionarioSolicitante(sOrigen.getDestinatario());
		sDestino.setDestinatario(fDestinatario);
		
		sDestino.setFechaCreacion(new Date());
		sDestino.setSolicitudPadre(sOrigen);		
		//Datos obligatorios de la solicitud
		sDestino.setForma(new Forma(1L));				
		sDestino.setTipoDocumento(new Valor(82L));// Solicitud			
		sDestino.setFolioDocumento("Folio Documento");					
		sDestino.setNombreDocumento("Solicitud");				
//		sDestino.setFolioSolicitud(generarFolioSolicitudService.generarFolioSolicitud());
		sDestino.setFolioSolicitud(sOrigen.getFolioSolicitud());
		sDestino.setTipoSolicitud(sOrigen.getTipoSolicitud());
		if(sOrigen.getFuncionarioSolicitante().getPuesto()!=null)
			sDestino.setPuestoUsuarioSolicitante(sOrigen.getFuncionarioSolicitante().getPuesto().getValor());
		
		sDestino.setEspecialidad(sOrigen.getEspecialidad());
		sDestino.setTipoEstudio(sOrigen.getTipoEstudio());
		if(sOrigen.getObservaciones()!=null)
			sDestino.setObservaciones(sOrigen.getObservaciones());		
		sDestino.setNumeroExpediente(sOrigen.getNumeroExpediente());
		sDestino.setFechaLimite(sOrigen.getFechaLimite());
		Long IdDestino = 0L;
		try
		{
			solicitudPericialDAO.update(sOrigen);
			
			IdDestino = solicitudPericialDAO.create(sDestino);
			
			//Asociar los elementos relacionados de la solicitud origen a la solicitud destino
			if(sOrigen.getRelacionElementos() != null){
				RelacionDocumentoElemento nuevaRelacion = null;
				for(RelacionDocumentoElemento relacion:sOrigen.getRelacionElementos()){
					nuevaRelacion = new RelacionDocumentoElemento();
					nuevaRelacion.setElemento(relacion.getElemento());
					nuevaRelacion.setDocumento(sDestino);
					nuevaRelacion.setCatRelacion(relacion.getCatRelacion());
					relacionDocumentoElementoDAO.create(nuevaRelacion);
				}
			}
			
		}catch(Exception ex)
		{
			logger.debug(ex.getStackTrace());
			throw ex;
		}
		return IdDestino;		
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#finalizarSolicitudPericial(java.lang.Long)
	 */
	@Override
	public void finalizarSolicitudPericial(Long solicitudId) {
		
		SolicitudPericial solicitud = solicitudPericialDAO.read(solicitudId);
		SolicitudPericial solicitudPadre = null;
		if(!EstatusSolicitud.RESPONDIDA.getValorId().equals(solicitud.getEstatus().getValorId())){
			if(solicitud != null){
				solicitudPadre = solicitud.getSolicitudPadre();
				solicitud.setEstatus(new Valor(EstatusSolicitud.CERRADA.getValorId()));
				solicitud.setFechaCierre(new Date());
				solicitudPericialDAO.saveOrUpdate(solicitud);
				if(solicitudPadre != null){
					solicitudPadre.setEstatus(new Valor(EstatusSolicitud.CERRADA.getValorId()));
					solicitudPadre.setFechaCierre(new Date());
					solicitudPericialDAO.saveOrUpdate(solicitudPadre);
				}
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarPericialService#consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente(mx.gob.segob.nsjp.comun.enums.funcionario.Puestos, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud[], java.lang.Long)
	 */
	@Override
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente(
			Puestos puesto, EstatusSolicitud[] estados, Long numeroExpedienteId)
			throws NSJPNegocioException {

		List<SolicitudPericial> solicitudes = solicitudPericialDAO.
		consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente
		(puesto, estados, numeroExpedienteId);
		List<SolicitudPericialDTO> resultado = new ArrayList<SolicitudPericialDTO>();
		Map mapa=new HashMap<Long, SolicitudPericial>();
		for(SolicitudPericial sol:solicitudes){
			if(mapa.isEmpty()){
				mapa.put(sol.getFolioSolicitud(), sol);
				resultado.add(SolicitudPericialTransformer.solPericialTransformer(sol));
			}else if (!mapa.containsKey(sol.getFolioSolicitud())){
				mapa.put(sol.getFolioSolicitud(), sol);
				resultado.add(SolicitudPericialTransformer.solPericialTransformer(sol));		
			}
		}
		resultado = paginacionManualSolicitudPeri(resultado);
		return resultado;
	}
	
	private List<SolicitudPericialDTO> paginacionManualSolicitudPeri(
			List<SolicitudPericialDTO> soli) {
		final PaginacionDTO pag=PaginacionThreadHolder.get();
		if((pag.getPage()*pag.getRows())-pag.getRows()>soli.size()){
			pag.setPage(1);
		}
		int inicio=0;
		if(pag.getPage()<=1){
			inicio=0;
		}else{
			inicio=((pag.getPage()-1)*pag.getRows());
		}
		int indiceFinal=inicio+pag.getRows();
		List<SolicitudPericialDTO> listaAlterna=soli;
		soli=new ArrayList<SolicitudPericialDTO>();
		if(indiceFinal>=listaAlterna.size()){
			for (int i = inicio; i < listaAlterna.size(); i++) {
				soli.add(listaAlterna.get(i));
			}
		}else{
			for (int i = inicio; i < indiceFinal; i++) {
				soli.add(listaAlterna.get(i));
			}
		}
		
		
		pag.setTotalRegistros((long)listaAlterna.size());
		pag.setPaginacionHecha(true);
		PaginacionThreadHolder.set(pag);
		return soli;
	}

	@Override
	public List<SolicitudPericialDTO> consultarSolicitudesPericialesPorFolioParaReasignacion(
			SolicitudPericialDTO solicitud) throws NSJPNegocioException {
		
		SolicitudPericial solicitudPericial = SolicitudPericialTransformer.solPericialTransformer(solicitud);
		List<SolicitudPericial> solicitudes = solicitudPericialDAO.consultarSolicitudesPericialesPorFolioParaReasignacion(solicitudPericial);
		List<SolicitudPericialDTO> resultados = new ArrayList<SolicitudPericialDTO>();
		
		for (SolicitudPericial pericial : solicitudes){
			resultados.add(SolicitudPericialTransformer.solPericialTransformer(pericial));
		}
		return resultados;
	}

	@Override
	public void actualizarSolicitudPericial(SolicitudPericialDTO solicitud)
			throws NSJPNegocioException {
		SolicitudPericial solicitudDB = solicitudPericialDAO.read(solicitud.getIdSolicitudPericial());
		Long idFuncionarioAnterior = solicitudDB.getDestinatario().getClaveFuncionario();
		if(solicitud.getDestinatario() != null){
        	solicitudDB.setDestinatario(FuncionarioTransformer.transformarFuncionario(solicitud.getDestinatario()));
        }
        this.solicitudPericialDAO.update(solicitudDB);
        
		relacionSolicitudDocumentoFuncionarioDAO
				.actualizaFuncionarioDeDocumentosSegunRelacion(solicitud
						.getIdSolicitudPericial(), idFuncionarioAnterior,
						solicitud.getDestinatario().getClaveFuncionario());
		
		relacionSolicitudDocumentoFuncionarioDAO
		.actualizaFuncionarioDeActividadSegunRelacion(solicitud
				.getIdSolicitudPericial(), idFuncionarioAnterior,
				solicitud.getDestinatario().getClaveFuncionario());
		
		relacionSolicitudDocumentoFuncionarioDAO
		.actualizaFuncionarioDeRelacion(solicitud
				.getIdSolicitudPericial(), idFuncionarioAnterior,
				solicitud.getDestinatario().getClaveFuncionario());
		
	}

	@Override
	public List<SolicitudPericialDTO> consultarSolicitudesPericialPorNumeroExpediente(
			Long numeroExpedienteId) throws NSJPNegocioException {
		List<SolicitudPericial> solicitudes = solicitudPericialDAO.consultarSolicitudesPericialPorNumeroExpediente(numeroExpedienteId);
		List<SolicitudPericialDTO> resultados = new ArrayList<SolicitudPericialDTO>();
		
		for (SolicitudPericial pericial : solicitudes){
			resultados.add(SolicitudPericialTransformer.solPericialTransformer(pericial));
		}
		return resultados;
	}

}
