/**
 * Nombre del Programa : BuscarExpedienteServiceImplTest.java
 * Autor                            : cesar
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.service.test.expediente.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.menu.EstatusMenuJAR;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraPermisoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author cesar
 * 
 */
public class BuscarExpedienteServiceImplTest extends
		BaseTestServicios<BuscarExpedienteService> {

	public void testBuscarExpedientePorEvidencia() {
		FiltroExpedienteDTO filtros = new FiltroExpedienteDTO();
		List<String> palabrasClave = new ArrayList<String>();

//		Id Usuario  7
//		claveUsuario= agentemp
//		Area = 10
		
		
		filtros.setNombreEvidencia(Objetos.VEHICULO.getNombreEntity());
		palabrasClave.add("vehiculo");
//		palabrasClave.add("veloz");
//		palabrasClave.add("60321975");
//		palabrasClave.add("papito");
//		palabrasClave.add("arena");
		filtros.setPalabrasClave(palabrasClave);

		List<ExpedienteDTO> respuesta = new ArrayList<ExpedienteDTO>();
		try {
			respuesta = service.buscarExpedientes(filtros);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}

		assertFalse("Lista respuesta ", respuesta.isEmpty());
		logger.info("Expedientes : " + respuesta.size());

	}

	public void _testBuscarExpedientePorAlias() {
		FiltroExpedienteDTO filtros = new FiltroExpedienteDTO();
		filtros.setAlias("chompi");
		filtros.setTipoBusqueda(1L);

		List<InvolucradoDTO> involocrados = new ArrayList<InvolucradoDTO>();
		try {
			involocrados = service.buscarExpedientesPorFiltros(filtros);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}

		assertFalse("Lista respuesta ", involocrados.isEmpty());

	}

	public void _testBuscarExpedientePorNombre() {
		FiltroExpedienteDTO filtros = new FiltroExpedienteDTO();
		filtros.setNombre("v");
		filtros.setApellidos("agus");

		List<InvolucradoDTO> involocrados = new ArrayList<InvolucradoDTO>();
		try {
			involocrados = service.buscarExpedientesPorFiltros(filtros);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}

		assertFalse("Lista respuesta ", involocrados.isEmpty());
		for (InvolucradoDTO involucradoDTO : involocrados) {
			logger.info("Involucrado " + involucradoDTO.getElementoId());
		}
	}

	public void testBuscarExpediente() {
		FiltroExpedienteDTO filtrosBusqueda = new FiltroExpedienteDTO();

		filtrosBusqueda.setNumeroExpediente("33");
//		UsuarioDTO usuarioDTO = new UsuarioDTO();
//		usuarioDTO.setAreaActual(new AreaDTO(Areas.COORDINACION_ATENCION_TEMPRANA_PG));
//		filtrosBusqueda.setUsuario(usuarioDTO);
		try {
			List<ExpedienteDTO> respuesta = service
					.buscarExpedientes(filtrosBusqueda);
			logger.info("Expedientes : " + respuesta.size());

			assertFalse("La lista de expedientes no debe estar vacia ",
					respuesta.isEmpty());
			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info(" Involucrados"
						+ expedienteDTO.getInvolucradosDTO());
				List<InvolucradoDTO> linvolucradoDTO = expedienteDTO
						.getInvolucradosDTO();
				for (InvolucradoDTO involucradoDTO : linvolucradoDTO) {
					logger.info(" Involucrado "
							+ involucradoDTO.getElementoId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testBuscarExpedienteById() {
		try {
			ExpedienteDTO expDTO = new ExpedienteDTO();

			expDTO.setNumeroExpedienteId(3779L);
			//expDTO.setInvolucradosSolicitados(true);
//			expDTO.setHechoSolicitado(true);
			// expDTO.setArea(new AreaDTO(Areas.ATENCION_TEMPRANA_PENAL));
			
			ExpedienteDTO respuesta = service.obtenerExpediente(expDTO);

			assertTrue("El expediente debe tener un numero ",
					respuesta.getNumeroExpedienteId() > 0);
			logger.info("getNumeroExpedienteId "
					+ respuesta.getNumeroExpedienteId());
			logger.info("Expediente id " + respuesta.getExpedienteId());
			logger.info("Expediente Area " + respuesta.getArea());
			logger.info("CASO" + respuesta.getCasoDTO().getNumeroGeneralCaso());
			logger.info("ELEMENTOS:" + respuesta.getElementosDTO());
			logger.info("No. expediente " + respuesta.getNumeroExpediente());
			logger.info("Involucrados " + respuesta.getInvolucradosDTO().size());
			logger.info("Involucrados por calidad "
					+ respuesta.getInvolucradoByCalidad(Calidades.DENUNCIANTE)
							.size());
			logger.info("Actividad Actual " + respuesta.getActividadActual());
			logger.info("Delitos " + respuesta.getDelitos());
			logger.info("Delito principal " + respuesta.getDelitoPrincipal());
			logger.info("Hecho " + respuesta.getHechoDTO());
			logger.info("PropietarioTramite: " + respuesta.getResponsableTramite());
			
			if (respuesta.getHechoDTO() != null) {
				HechoDTO hechoDTO = respuesta.getHechoDTO();
				logger.info("HechoId:" + hechoDTO.getHechoId());
				logger.info("DescNarrativa:" + hechoDTO.getDescNarrativa());
				if( hechoDTO.getTiempo()!= null){
					logger.info("Tiempo Hecho TiempoId : " + respuesta.getHechoDTO().getTiempo().getTiempoId());
					logger.info("Tiempo Hecho Descripcion: " + respuesta.getHechoDTO().getTiempo().getDescripcion());
					logger.info("Tiempo Hecho Inicio: " + respuesta.getHechoDTO().getTiempo().getFechaInicio());
					logger.info("Tiempo Hecho FechaFin: " + respuesta.getHechoDTO().getTiempo().getFechaFin());
					logger.info("Tiempo Hecho TipoRegistro: " + respuesta.getHechoDTO().getTiempo().getTipoRegistro());
				}
				if (hechoDTO.getLugar() != null){
					LugarDTO lugarDTO = hechoDTO.getLugar();	
					logger.info("Lugar Hecho Lugar : "+ lugarDTO);
					logger.info("Lugar Hecho Lugar Latitud: "+ lugarDTO.getLatitud());
					logger.info("Lugar Hecho Lugar Longitud: "+ lugarDTO.getLongitud());
					
					if(lugarDTO instanceof DomicilioDTO){
						DomicilioDTO domicilioDTO = (DomicilioDTO) lugarDTO;
						logger.info("DomicilioDTO : "+ domicilioDTO);
						
					}
				}
			}
			logger.info("Tiempo getObjetosDTO : " + respuesta.getObjetosDTO());

			logger.info("Documentos: " + respuesta.getDocumentosDTO().size());
			for (DocumentoDTO loDocumentoDTO : respuesta.getDocumentosDTO()) {
				logger.info("--------- ID Documento: "
						+ loDocumentoDTO.getDocumentoId() + " -------------");
				logger.info("Nombre Documento: "
						+ loDocumentoDTO.getNombreDocumento());
				logger.info("Tipo Documento: "
						+ loDocumentoDTO.getTipoDocumentoDTO().getValor());
				logger.info("Archivo Digital: "
						+ loDocumentoDTO.getArchivoDigital());
				if (loDocumentoDTO.getArchivoDigital() != null) {
					ArchivoDigitalDTO archivoDigitalDTO = loDocumentoDTO
							.getArchivoDigital();
					logger.info("Archivo Digital: "
							+ archivoDigitalDTO.getNombreArchivo());
					logger.info("Tipo Archivo: "
							+ archivoDigitalDTO.getTipoArchivo());
					logger.info("Size: "
							+ archivoDigitalDTO.getContenido().length);
				}
				logger.info("-------------------------------");

			}
			List<InvolucradoDTO> lInvolucradoDTO = respuesta
					.getInvolucradosDTO();
			for (InvolucradoDTO involucradoDTO : lInvolucradoDTO) {
				logger.info("-------------INVOLUCRADO------------------");
				logger.info("** ID:" + involucradoDTO.getElementoId());
				logger.info("Calidad:** "
						+ (involucradoDTO.getCalidadDTO() != null ? involucradoDTO
								.getCalidadDTO().getValorIdCalidad().getValor()
								: null) + "**  - "
						+ involucradoDTO.getCalidadDTO());
				logger.info(" TIPO PERSONA: **"
						+ (involucradoDTO.getTipoPersona().equals(0L) ? "Moral"
								: "Fisica") + "**");

				
				logger.info(" OCUPACIONES: **"
						+ involucradoDTO.getValorIdOcupacion());
				if(involucradoDTO.getValorIdOcupacion()!=null && !involucradoDTO.getValorIdOcupacion().isEmpty()){
					List<ValorDTO> ocupaciones = involucradoDTO.getValorIdOcupacion();
					for (ValorDTO valorDTO : ocupaciones) {
						logger.info(" Ocupacion: " + valorDTO.getIdCampo() +"-"+ valorDTO.getValor() );
					}
				}
				
				logger.info(" NACIONALIDADES: **"
						+ involucradoDTO.getValorIdNacionalidad());
				if(involucradoDTO.getValorIdNacionalidad()!=null && !involucradoDTO.getValorIdNacionalidad().isEmpty()){
					List<ValorDTO> nacionalidades = involucradoDTO.getValorIdNacionalidad();
					for (ValorDTO valorDTO : nacionalidades) {
						logger.info(" nacionalidades: " + valorDTO.getIdCampo() +"-"+ valorDTO.getValor() );
					}
				}

				logger.info(" ALIAS: **"
						+ involucradoDTO.getAliasInvolucradoDTO());
				if(involucradoDTO.getAliasInvolucradoDTO()!=null && !involucradoDTO.getAliasInvolucradoDTO().isEmpty()){
					List<AliasInvolucradoDTO> alias = involucradoDTO.getAliasInvolucradoDTO();
					for (AliasInvolucradoDTO aliasDTO : alias) {
						logger.info(" alias: " + aliasDTO.getAliasInvolucradoId() +"-"+aliasDTO.getAlias());
					}
				}

				
				
				
				// DATOS GENRALES
				logger.info(" Domicilio:" + involucradoDTO.getDomicilio());
				// MORAL
				if (involucradoDTO.getTipoPersona().equals(0L)) {
					logger.info("** ORGNANIZACION:"
							+ involucradoDTO.getOrganizacionDTO());
					if (involucradoDTO.getOrganizacionDTO() != null) {
						OrganizacionDTO organizacionDTO = involucradoDTO
								.getOrganizacionDTO();
						logger.info("Nombre:"
								+ organizacionDTO.getNombreOrganizacion());
						logger.info("Domicilio:"
								+ organizacionDTO.getDomicilioDTO());
						logger.info("Nombre:"
								+ organizacionDTO.getNombreOrganizacion());

						logger.info("Representante Legal:"
								+ organizacionDTO.getRepresentanteLegal());
					}
				}
				// FISICA
				else {
					logger.info("** FISICA:"
							+ involucradoDTO.getNombresDemograficoDTO());

				}
			}
			logger.info(" Es Replicado:" + respuesta.getEsReplicado());
			logger.info("Tipo Denuncia:" + respuesta.getTipoExpediente().getValor());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testConsultarExpedientesPorCasoOK() {
		try {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO
					.setAreaActual(new AreaDTO(Areas.UNIDAD_INVESTIGACION));

			CasoDTO cdto = new CasoDTO();
//			cdto.setNumeroGeneralCaso("ZAC/FG/XX/PGU/2012/AA-01786");
			cdto.setCasoId(1871L);
			
			cdto.setUsuario(usuarioDTO);
			
			List<ExpedienteDTO> respuesta = service
					.consultarExpedientesPorCaso(cdto);
			logger.info("Expedientes : " + respuesta.size());

			assertFalse("La lista de expedientes no debe estar vacia ",
					respuesta.isEmpty());
			
			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info("NumeroExp:"+expedienteDTO.getNumeroExpediente());
				logger.info("NumeroExp:"+expedienteDTO.getNumeroExpedienteId());
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testObtenerExpedientePorNumeroExpediente() {
		try {
			ExpedienteDTO resp = this.service
					.obtenerExpedientePorNumeroExpediente("NSJYUCPG20114433337");
			logger.info("Expedientes : " + resp.getNumeroExpedienteId());
			logger.info("Expedientes : " + resp.getExpedienteId());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("deprecation")
	public void testConsultarExpedienteActividadAreaAnio() {
		PaginacionDTO loPaginacionDTO = new PaginacionDTO();
		loPaginacionDTO.setPage(1);
		loPaginacionDTO.setRows(1000);
		loPaginacionDTO.setCampoOrd("Fecha");
		loPaginacionDTO.setDirOrd("asc");
		PaginacionThreadHolder.set(loPaginacionDTO);
		
		FiltroExpedienteDTO filtrosBusqueda = new FiltroExpedienteDTO();

		//filtrosBusqueda.setAnio(2013L);
		filtrosBusqueda.setIdActividad(Actividades.GENERAR_DENUNCIA_EN_PM.getValorId());
		filtrosBusqueda.setIdArea(new Long(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()));
		filtrosBusqueda.setestatusMenuCoorJAR(EstatusExpediente.ABIERTO.getValorId());
		filtrosBusqueda.setEstatus(EstatusExpediente.ABIERTO.getValorId());

		
		FuncionarioDTO nuevoFuncionario = new FuncionarioDTO();
		nuevoFuncionario.setClaveFuncionario(278L);
		nuevoFuncionario.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(new Long(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal())));
		UsuarioDTO nuevoUsuario = new UsuarioDTO(381L);
		nuevoUsuario.setFuncionario(nuevoFuncionario);
		Set<UsuarioRolDTO> usuarioRoles = new HashSet<UsuarioRolDTO>();
		UsuarioRolDTO loUsuarioRolDTO = new UsuarioRolDTO();
		nuevoUsuario.setRolActivo(Roles.POLICIAMINISTER.toString());
		RolDTO loRol = new RolDTO(Roles.POLICIAMINISTER.getValorId());
		loRol.setNombreRol(Roles.POLICIAMINISTER.toString());
		loRol.setJerarquiaOrganizacionalDTO(new JerarquiaOrganizacionalDTO(new Long(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal())));
		loUsuarioRolDTO.setRol(loRol );
		loUsuarioRolDTO.setUsuario(nuevoUsuario);
		usuarioRoles.add(loUsuarioRolDTO);
		nuevoUsuario.setUsuarioRoles(usuarioRoles );

		
		filtrosBusqueda. setUsuario(nuevoUsuario);

		try {
			List<ExpedienteDTO> respuesta = service
					.consultarExpedienteActividadAreaAnio(filtrosBusqueda);

			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info("/**** Expediente ID "
						+ expedienteDTO.getExpedienteId()
						+ " Numero Expediente : "
						+ expedienteDTO.getNumeroExpediente()
						+ "estatus: "
						+ expedienteDTO.getEstatus().getValor()
						+ "fecha: "
						+expedienteDTO.getFechaApertura()
						);
//				if (expedienteDTO.getActividadActual() != null) {
//					logger.info("/**** Actividad ID "
//							+ expedienteDTO.getActividadActual().getNombre()
//							+ " Fecha: "
//							+ expedienteDTO.getActividadActual()
//									.getFechaCreacion());
//				}

//				List<InvolucradoDTO> invsRes = expedienteDTO
//						.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
//				for (InvolucradoDTO involucradoDTO : invsRes) {
//					logger.info("/**** Involucrado : "
//							+ involucradoDTO.getNombreCompleto());
//				}
			}

			assertNotNull("La lista no puede estar vacia", respuesta);
			logger.info(respuesta.size() + " Expedientes recuperados : ");
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Permite consultar los datos generales de un Expediente dado un Numero de
	 * expediente
	 * 
	 * @author ricardog
	 */
	public void testBuscarDatosGeneralesExpedienteById() {
		try {
			ExpedienteDTO expDTO = new ExpedienteDTO();
			expDTO.setNumeroExpedienteId(183L);
			DatosGeneralesExpedienteDTO respuesta = service
					.obtenerDatosGeneralesExpediente(expDTO);
			logger.info("Fecha de apertura: " + respuesta.getFechaApertura());

			logger.info("Id Expediente: " + respuesta.getExpedienteId());
			logger.info("Estatus del Numero del expediente: "
					+ respuesta.getEstatusNumeroExpediente());
			logger.info("Tipo de Denuncia: " + respuesta.getOrigenExpediente());

			logger.info("TotalVehiculos: " + respuesta.getTotalVehiculos());
			logger.info("TotalEquiposComputo:"
					+ respuesta.getTotalEquiposComputo());
			logger.info("TotalEquiposTelefonicos:"
					+ respuesta.getTotalEquiposTelefonicos());
			logger.info("TotalArmas:" + respuesta.getTotalArmas());
			logger.info("TotalExplosivos:" + respuesta.getTotalExplosivos());
			logger.info("TotalSustancias:" + respuesta.getTotalSustancias());
			logger.info("TotalAnimales:" + respuesta.getTotalAnimales());
			logger.info("TotalAeronaves:" + respuesta.getTotalAeronaves());
			logger.info("TotalEmbarcaciones:"
					+ respuesta.getTotalEmbarcaciones());
			logger.info("TotalInmuelbes:" + respuesta.getTotalInmuelbes());
			logger.info("TotalNumerarios:" + respuesta.getTotalNumerarios());
			logger.info("TotalVegetales:" + respuesta.getTotalVegetales());
			logger.info("TotalDocumentosOficiales:"
					+ respuesta.getTotalDocumentosOficiales());
			logger.info("TotalJoyas:" + respuesta.getTotalJoyas());
			logger.info("TotalObrasDeArte:" + respuesta.getTotalObrasDeArte());
			logger.info("TotalOtrosObjestos:"
					+ respuesta.getTotalOtrosObjestos());

			logger.info("Denunciantes:" + respuesta.getTotalDenunciantes());
			logger.info("Victimas:" + respuesta.getTotalVictimas());
			logger.info("Probables responsables:"
					+ respuesta.getTotalProbablesResposables());
			logger.info("Testigos:" + respuesta.getTotalTestigos());
			logger.info("Traductores:" + respuesta.getTotalTraductores());
			logger.info("Quien detuvo:" + respuesta.getQuienDetuvo());
			logger.info("DOCUMENTOS: " + respuesta.getTotalDocumentosDelExpediente());
			logger.info("Origen del documento: " + respuesta.getOrigenExpediente());
			logger.info("Origen del Numero de Expediente: " + respuesta.getOrigenNumeroExpediente());

			
			assertTrue("El expediente debe tener un numero ",
					respuesta.getExpedienteId() > 0);

		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testBuscarExpedienteConEventosHistorico() {

		try {
			List<ExpedienteDTO> expedientes = service
					.consultarExpedientesConEventosHistorico(1L,
							new UsuarioDTO());
			assertNotNull(expedientes);
			logger.debug("Expediente encontrados para el caso 1 :"
					+ expedientes.size());
			for (ExpedienteDTO expediente : expedientes) {
				logger.debug("Expediente (" + expediente.getExpedienteId()
						+ "): " + expediente.getNumeroExpediente());
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			logger.error(e);
			assertNull(e);

		}
	}

	public void testObtenerNumeroExpedienteByNumExp() {
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();

		expedienteDTO.setNumeroExpediente("NSJZACSP010002012333EY");
		UsuarioDTO usuario = new UsuarioDTO();
		FuncionarioDTO funcionario = new FuncionarioDTO();
		CatDiscriminanteDTO catDis = new CatDiscriminanteDTO();
		catDis.setCatDiscriminanteId(26L);
		
		funcionario.setDiscriminante(catDis);
		usuario.setFuncionario(funcionario);
		

		try {
			expedienteDTO = service
					.obtenerNumeroExpedienteByNumExp(expedienteDTO,null);
			
			
			assertTrue("ExpedienteDTO no puede ser nulo : ",
					expedienteDTO.getNumeroExpedienteId() > 0);
			
			logger.info("Numero de expediente: " + expedienteDTO.getNumeroExpediente());
			logger.info("Numero de Caso: " + expedienteDTO.getCasoDTO());

			logger.info("ExpedienteDTO no puede ser nulo : "
					+ expedienteDTO.getNumeroExpedienteId());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}

	}

	public void testConsultarNumeroExpedienteHistorico() {
		try {
			UsuarioDTO usuario = new UsuarioDTO();
			FuncionarioDTO funcionario = new FuncionarioDTO();
			CatDiscriminanteDTO catDiscri = new CatDiscriminanteDTO();
			catDiscri.setCatDiscriminanteId(26L);
			
			funcionario.setDiscriminante(catDiscri);
			usuario.setFuncionario(funcionario);
			
			List<ExpedienteDTO> respuesta = service
					.consultarNumeroExpedienteHistorico(usuario);
			assertTrue("La lista debe tener minimo un registro: ",
					respuesta.size() > 0);
			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info("-------------------------");
				logger.info("Causa ID : "
						+ expedienteDTO.getNumeroExpedienteId());
				for (ExpedienteDTO carpeta : expedienteDTO.getNumExpHijos()) {
					logger.info("  **Carpeta ID : "
							+ carpeta.getNumeroExpedienteId());
				}
				logger.info("-------------------------");
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}

	public void testConsultarExpedientesUsuarioArea() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		AreaDTO areaActual = new AreaDTO();
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO(9L);
		
//		areaActual.setAreaId(Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong());
		areaActual.setAreaId(15L);
		//usuarioDTO.setIdUsuario(1L);
		usuarioDTO.setAreaActual(areaActual);
		usuarioDTO.setFuncionario(funcionarioDTO);

		try {
			List<ExpedienteDTO> respuesta = service
					.consultarExpedientesUsuarioArea(usuarioDTO);
			assertTrue("La lista debe tener minimo un Expediente",
					respuesta.size() > 0);
			logger.info("La lista debe tener minimo un Expediente"
					+ respuesta.size());
			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info("-------------------------");
				logger.info("Expediente: "+ expedienteDTO);
				logger.info("NumeroExpeidnte ID : "
						+ expedienteDTO.getNumeroExpedienteId());
				logger.info("NumeroExpeidnte : "
						+ expedienteDTO.getNumeroExpediente());
				logger.info("Estatus : " + expedienteDTO.getEstatus());
				logger.info("Etapa : " + expedienteDTO.getEtapa());
				logger.info("Delitos : " + expedienteDTO.getDelitos());
				logger.info("DelitoPrincipal : " + expedienteDTO.getDelitoPrincipal());
				logger.info("Caso : " + expedienteDTO.getCasoDTO());
				logger.info("-------------------------");
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}

	public void testConsultarExpedientePorAreaEstatusRemitente_Estatus() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(1L);
		usuarioDTO.setAreaActual(new AreaDTO(1L));
		try {
			List<ExpedienteDTO> expedientes = service
					.consultarExpedientePorAreaEstatusRemitente(usuarioDTO,
							null, EstatusExpediente.CERRADO.getValorId());
			logger.info("Existen " + expedientes.size() + " expedientes");
			for (ExpedienteDTO nexp : expedientes) {
				logger.info("-------------------------------");
				logger.info("ID: " + nexp.getNumeroExpedienteId());
				logger.info("Num: " + nexp.getNumeroExpediente());
				logger.info("Fecha Can (Apertura): " + nexp.getFechaApertura());
				logger.info("Involucrados: " + nexp.getInvolucradosDTO().size());
				if (nexp.getInvolucradosDTO().size() > 0) {
					logger.info("*Inv1: "
							+ nexp.getInvolucradosDTO().get(0)
									.getNombreCompleto());
					logger.info("*Inv1 calidad: "
							+ nexp.getInvolucradosDTO().get(0).getCalidadDTO()
									.getValorIdCalidad().getValor());
				}
				String strDelito = (nexp.getDelitoPrincipal() != null) ? nexp
						.getDelitoPrincipal().getCatDelitoDTO().getNombre()
						: "Sin principal";
				logger.info("Delito Princ: " + strDelito);
				logger.info("Origen (area anterior): " + "TODO");
				logger.info("Estado: " + nexp.getEstatus().getValor());
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue("FALLO", false);
		}
	}

	public void testConsultarExpedientePorAreaEstatusRemitente_Remitente() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(1L);
		usuarioDTO.setAreaActual(new AreaDTO(1L));
		AreaDTO area = new AreaDTO(2L);
		try {
			List<ExpedienteDTO> expedientes = service
					.consultarExpedientePorAreaEstatusRemitente(usuarioDTO,
							area, null);
			logger.info("Existen " + expedientes.size() + " expedientes");
			for (ExpedienteDTO nexp : expedientes) {
				logger.info("-------------------------------");
				logger.info("ID: " + nexp.getNumeroExpedienteId());
				logger.info("Num: " + nexp.getNumeroExpediente());
				logger.info("Fecha Can (Apertura): " + nexp.getFechaApertura());
				logger.info("Involucrados: " + nexp.getInvolucradosDTO().size());
				if (nexp.getInvolucradosDTO().size() > 0) {
					logger.info("*Inv1: "
							+ nexp.getInvolucradosDTO().get(0)
									.getNombreCompleto());
					logger.info("*Inv1 calidad: "
							+ nexp.getInvolucradosDTO().get(0).getCalidadDTO()
									.getValorIdCalidad().getValor());
				}
				String strDelito = (nexp.getDelitoPrincipal() != null) ? nexp
						.getDelitoPrincipal().getCatDelitoDTO().getNombre()
						: "Sin principal";
				logger.info("Delito Princ: " + strDelito);
				logger.info("Origen (area anterior): " + "TODO");
				logger.info("Estado: " + nexp.getEstatus().getValor());
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue("FALLO", false);
		}
	}

	public void testBuscarRemisionesConIPH() {
		try {
			List<ExpedienteDTO> respuesta = service
			.buscarRemisionesConIPH(EstatusExpediente.PENDIENTE_REVISION_COMO_IPH);
//			List<ExpedienteDTO> respuesta = service
//					.consultarNumeroExpedienteByTipoYEstatus(TipoExpediente.CARPETA_DE_EJECUCION, EstatusExpediente.ABIERTO);
			assertTrue("La lista debe tener minimo un registro : ",
					respuesta.size() > 0);
			logger.info("La lista debe tener minimo un registro : "
					+ respuesta.size());
			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info(" Exp_:"+expedienteDTO.getExpedienteId());
				logger.info(" Exp_:"+expedienteDTO);
				logger.info(" Exp_DElito Principal:"+expedienteDTO.getDelitoPrincipal());
				logger.info(" Exp_Delitos:"+expedienteDTO.getDelitos());
				logger.info(" Exp_Involucrados:"+expedienteDTO.getInvolucradosDTO());
				List<InvolucradoDTO> involucradosDTO = expedienteDTO.getInvolucradosDTO();
				for (InvolucradoDTO involucradoDTO : involucradosDTO) {
					logger.info(" Inv_:"+involucradoDTO.getNombreCompleto());
					logger.info(" Inv_:"+involucradoDTO.getNombresDemograficoDTO());
				}
				logger.info(" Exp_Origen:"+expedienteDTO.getOrigen());
				logger.info(" Exp_Caso:"+expedienteDTO.getCasoDTO());
				logger.info(" Exp_:"+expedienteDTO.getEstatus());
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}

	}
	
	public void testConsultarCarpetasEjecucionPorCausa() {
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(36L);

		try {
			List<ExpedienteDTO> respuesta = service
					.consultarCarpetasEjecucionPorCausa(expedienteDTO);
			assertTrue("La lista debe tener minimo un registro : ",
					respuesta.size() > 0);
			logger.info("La lista debe tener minimo un registro : "
					+ respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Prueba del uso del método de buscar expedientes por filtro desde poder
	 * judicial donde se buscan las causas por fecha
	 */
	public void testBuscarExpedientePJPorFiltro() {

		try {
			List<ExpedienteDTO> resultado = service
					.consultarExpedientesPorFiltro(new Date(), null, null,
							null, null);
			logger.debug("Tamaño del resultado:" + resultado.size());
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	
	public void testConsultarExpedientesPorUsuarioAreaEstatus() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		AreaDTO areaActual = new AreaDTO();
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO(8L);
		
//		areaActual.setAreaId(Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong());
		areaActual.setAreaId(10L);
		usuarioDTO.setAreaActual(areaActual);
		usuarioDTO.setFuncionario(funcionarioDTO);

		Long estatus = null; //EstatusExpediente.CERRADO.getValorId();

		try {
			List<ExpedienteDTO> respuesta = service
					.consultarExpedientesPorUsuarioAreaEstatus(usuarioDTO, estatus);
			assertTrue("La lista debe tener minimo un Expediente",
					respuesta.size() > 0);
			logger.info("La lista debe tener minimo un Expediente"
					+ respuesta.size());
			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info("-------------------------");
				logger.info("Expediente: "+ expedienteDTO);
				logger.info("NumeroExpeidnte ID : "
						+ expedienteDTO.getNumeroExpedienteId());
				logger.info("NumeroExpeidnte : "
						+ expedienteDTO.getNumeroExpediente());
				logger.info("Estatus : " + expedienteDTO.getEstatus());
				logger.info("Etapa : " + expedienteDTO.getEtapa());
				logger.info("Delitos : " + expedienteDTO.getDelitos());
				logger.info("DelitoPrincipal : " + expedienteDTO.getDelitoPrincipal());
				logger.info("Caso : " + expedienteDTO.getCasoDTO());
				logger.info("-------------------------");
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testConsultarNumExpPorFuncionario() {
		try {
			ExpedienteDTO respuesa =service.consultarNumExpPorFuncionarioYNumExp(new Long(3), new Long(1));
			assertNotNull(respuesa);
			
			logger.info("-------------------------");
			logger.info("NumeroExpediente ID :: "+respuesa.getNumeroExpedienteId());
			logger.info("NumeroExpediente :: "+respuesa.getNumeroExpediente());
			logger.info("Permiso :: "+respuesa.isEsEscritura());
			logger.info("Propietario :: "+respuesa.isEsPropietario());
			logger.info("-------------------------");
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testConsultarExpedienteRelacionadoAArea(){
		
		String numeroExpediente = "NSJYUCPG2011543333D";
		Long areaId = 10L;
		try{
			ExpedienteDTO  expedienteDTO = service.consultarExpedienteRelacionadoAArea(numeroExpediente, areaId);
			assertNotNull(expedienteDTO);
			
			logger.info("-------------------------");
			logger.info("Expediente ID :: "+expedienteDTO.getExpedienteId());
			logger.info("NumeroExpediente ID :: "+expedienteDTO.getNumeroExpedienteId());
			logger.info("NumeroExpediente :: "+expedienteDTO.getNumeroExpediente());
			logger.info("-------------------------");
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Permite consultar los datos generales de un Expediente de UAVD
	 * 
	 * @author ricardog
	 */
	public void obtenerResumenDeExpedienteUAVD() {
		try {

			DatosGeneralesExpedienteUAVDDTO respuesta = service
					.obtenerResumenDeExpedienteUAVD(new SolicitudDTO(643L));			
			logger.info("Tipo Solicitud: " + respuesta.getTipoSolicitud());
			logger.info("Nombre De La Victima: " + respuesta.getNombreDeLaVictima());
			logger.info("Delito: " + respuesta.getDelito());
			logger.info("Amp Solicitante: " + respuesta.getAmpSolicitante());
			logger.info("Area solicitante: " + respuesta.getAreaSolicitante());
			logger.info("Fecha de creacion del exp: " + respuesta.getFechaDeCreacionDelExpediente());
			logger.info("Estatus del expediente: " + respuesta.getEstatusDelExpediente());			
			
			assertTrue("El expediente debe tener un numero ",
					respuesta.getExpedienteId() > 0);

		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public void testConsultarExpedientesConPermisoFuncionario(){
		
		Long claveFuncionario = 97L;
		
		 try {
			List<ExpedienteDTO> expedientesConPermisos = service.consultarExpedientescConPermisoFuncionario(claveFuncionario);
			assertFalse("La lista no puede ser vacia", expedientesConPermisos.isEmpty());
			for (ExpedienteDTO expedienteDTO : expedientesConPermisos) {
				logger.info("Expediente: "+ expedienteDTO.getExpedienteId());
				logger.info("Expediente: "+ expedienteDTO.getNumeroExpediente());
				logger.info("Expediente: "+ expedienteDTO.getDiscriminante());
				logger.info("Expediente: "+ expedienteDTO.getDiscriminante());
			}
			logger.info("Total: "+ expedientesConPermisos.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
		 
		
	}

	public void testConsultarExpedientesPermisoFuncionarioJerarquiaRol(){
		Long claveFuncionarioPermiso = 342L;
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		AreaDTO areaDTO = new AreaDTO(15L);
		usuarioDTO.setAreaActual(areaDTO);
		
		 try {
			List<ExpedienteDTO> expedientesConPermisos = service
					.consultarExpedientesPermisoFuncionarioJerarquiaRol(usuarioDTO, claveFuncionarioPermiso);
			assertFalse("La lista no puede ser vacia", expedientesConPermisos.isEmpty());
			for (ExpedienteDTO expedienteDTO : expedientesConPermisos) {
				logger.info("Expediente: "+ expedienteDTO.getExpedienteId());
				logger.info("Expediente: "+ expedienteDTO.getNumeroExpediente());
				logger.info("Expediente: "+ expedienteDTO.getDiscriminante());
				logger.info("Expediente: "+ expedienteDTO.getDiscriminante());
			}
			logger.info("Total: "+ expedientesConPermisos.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testbuscarExpedientesPorNumExpDiscriminanteArea(){
		 try {
			 
			 FiltroExpedienteDTO filtrosBusquedaExpediente = new FiltroExpedienteDTO();
			 UsuarioDTO usuariofir = new UsuarioDTO();
			 usuariofir.setAreaActual(new AreaDTO(27L));
			 
			 CatDiscriminanteDTO catDis = new CatDiscriminanteDTO();
			 catDis.setCatDiscriminanteId(2L); 
			 
			 FuncionarioDTO funcionario = new FuncionarioDTO();
			 funcionario.setDiscriminante(catDis);
			 usuariofir.setFuncionario(funcionario);
			 
			 ConfInstitucionDTO conIns = new ConfInstitucionDTO();
			 conIns.setConfInstitucionId(3L);
			 usuariofir.setInstitucion(conIns);
			 
			 String numeroExpediente = "NSJYUCPJ0100220123333";
			 
			 filtrosBusquedaExpediente.setNumeroExpediente(numeroExpediente);
			 filtrosBusquedaExpediente.setUsuario(usuariofir);
			 
			List<ExpedienteDTO> expedientesConPermisos = service.buscarExpedientesPorNumExpDiscriminanteArea(filtrosBusquedaExpediente);
			
			for (ExpedienteDTO expedienteDTO : expedientesConPermisos) {
				logger.info("Expediente: "+ expedienteDTO.getExpedienteId());
				logger.info("Expediente: "+ expedienteDTO.getNumeroExpediente());
				logger.info("Expediente: "+ expedienteDTO.getNumeroExpedienteId());
				logger.info("Expediente: "+ expedienteDTO.getCasoDTO().getNumeroGeneralCaso());
			}
			logger.info("Total: "+ expedientesConPermisos.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
		 
		
	}
	
	public void testConsultarNumeroDeExpedienteConHechoPorFiltros() {
		try {

			List<ExpedienteDTO> respuesta = service
					.consultarNumeroDeExpedienteConHechoPorFiltros(
							EstatusExpediente.NO_ATENDIDO, null, null, null);

			for (ExpedienteDTO expedienteDTO : respuesta) {
				logger.info("__________________________________________");
				logger.info("Numero de Expediente:"
						+ expedienteDTO.getNumeroExpediente());
				logger.info("Folio Notificacion:"
						+ expedienteDTO.getHechoDTO().getAvisoHechoDelictivo()
								.getFolioNotificacion());
				logger.info("Fecha del Aviso:"
						+ DateUtils.formatear(expedienteDTO.getHechoDTO()
								.getAvisoHechoDelictivo().getFechaCreacion()));
				logger.info("Probable Delito:"
						+ expedienteDTO.getHechoDTO().getAvisoHechoDelictivo()
								.getCatDelito().getNombre());
				logger.info("__________________________________________");
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
	public void consultarExpedientesPorFiltroST(){
		try{
			UsuarioDTO usuario = new UsuarioDTO();
			FuncionarioDTO funcionario = new FuncionarioDTO();
			funcionario.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.parseLong()));
			//funcionario.setDiscriminante(new CatDiscriminanteDTO(1L));
			usuario.setFuncionario(funcionario);

			Long idDistrito = 1L;
			Long idFuncioario = 280L;
			Long idDiscriminante =1L;
			
			List<Long> estatus = new ArrayList<Long>();
			estatus.add(EstatusExpediente.ABIERTO.getValorId());
	    	estatus.add(EstatusExpediente.DEVUELTO.getValorId());
					
			List<ExpedienteDTO> numerosExpdientes = service.consultarExpedientesPorFiltroST(null, null, usuario,
					estatus,idDiscriminante, Roles.COORDINADOR_CONSIGNACION.getValorId(), idDistrito, idFuncioario);
			assertNotNull(numerosExpdientes);
			
			for (ExpedienteDTO expBD : numerosExpdientes) {
				logger.info("-------------------------");
				logger.info("Expediente ID :: "+expBD.getExpedienteId());
				logger.info("NumeroExpediente ID :: "+expBD.getNumeroExpedienteId());
				logger.info("NumeroExpediente :: "+expBD.getNumeroExpediente());
				logger.info("Fecha de creacion :: "+expBD.getFechaApertura());
				logger.info("Estatus :: "+expBD.getEstatus().getValor());
				if(expBD.getTipoExpediente() != null && expBD.getTipoExpediente().getValor() != null)
					logger.info("Tipo Expediente :: "+expBD.getTipoExpediente().getValor());
				logger.info("-------------------------");
			}
			
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}

	
	
	public void testActualizarEstatusNumerosDeExpedientesPorRolST() {
		try {
			
			Long idExpediente = 2153L;
			
	    	List<Long> idRoles = new ArrayList<Long>();
	    	idRoles.add(Roles.COORDINADOR_CONSIGNACION.getValorId());
	    	idRoles.add(Roles.CONSIGNADOR.getValorId());
	    	idRoles.add(Roles.AGENTEMPSISTRAD.getValorId());
			service.actualizarEstatusNumerosDeExpedientesPorRolST(idRoles, idExpediente, EstatusExpediente.DEVUELTO.getValorId(), EstatusExpediente.CERRADO.getValorId());
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testConsultarExpedientesConSP() {
		try {
			TipoDeBusquedaDeExpediente tipoBusqueda = TipoDeBusquedaDeExpediente.POR_ATP;
		
			HashMap<String, String> valores = new HashMap<String, String>();
			valores.put("ai_iClaveFuncionario", "8");
			valores.put("ai_rol_id", "7");
			valores.put("ai_pagina", "0");
			valores.put("ai_registros", "10");
			valores.put("as_columnaOrd", "iClaveFuncionario");
			valores.put("as_direccionOrd", "asc");
			//valores.put("ai_catdelito_id", "2");
			valores.put("as_cNumeroExpediente", "00296");
			//valores.put("as_cNumeroCaso", "AA-006");
			//valores.put("as_fechaIni", "29/10/2012");
			//valores.put("as_fechaFin", "01/11/2012");
			List<ExpedienteViewDTO> respuesta = service.consultarExpedientesConSP(tipoBusqueda , valores);
			
			imprimeInformacion(respuesta);
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	private void imprimeInformacion(List<ExpedienteViewDTO> respuesta) {
		logger.info("Tamaño de la lista: "+ respuesta.size());
		for (ExpedienteViewDTO loExp : respuesta) {
			logger.info("============================================================");
			logger.info("caso_id: "+ loExp.getCasoId());
			logger.info("cNumeroGeneralCaso:"+ loExp.getNumeroGeneralCaso());
			logger.info("expediente_id :"+ loExp.getExpedienteId());
			logger.info("numeroExpediente_id :"+ loExp.getNumeroExpedienteId());
			logger.info("cNumeroExpediente :"+ loExp.getNumeroExpediente());
			logger.info("cInvolucrado :"+ loExp.getNombreInvolucrado());
			logger.info("cNombreCalidad :"+ loExp.getNombreCalidadInvolucrado());
			logger.info("cDelito :"+ loExp.getDelito());
			logger.info("bEsPrincipal :"+ loExp.getEsPrincipal());
			logger.info("iClaveFuncionario :"+ loExp.getClaveFuncionario());
			logger.info("cFuncionario :"+ loExp.getNombreFuncionario());
			logger.info("cArea :"+ loExp.getAreaFuncionario());
			logger.info("cEdificio :"+ loExp.getEdificio());
			logger.info("cEstatus :"+ loExp.getEstatus());
			logger.info("bEsConsulta :"+ loExp.getEsConsulta());
			logger.info("nTotalRegistros :"+ loExp.getTotalRegistros());
			logger.info("RowNumber :"+ loExp.getFechaApertura());
		}		
	}
	
	public void testConsultarGeneralesDeHistorialDeExp() {
		try {
			ExpedienteViewDTO loExp = service.consultarGeneralesDeHistorialDeExp(3779L);
			
			logger.info("============================================================");
			logger.info("cNumeroGeneralCaso:"+ loExp.getNumeroGeneralCaso());
			logger.info("numeroExpediente_id :"+ loExp.getNumeroExpedienteId());
			logger.info("cNumeroExpediente :"+ loExp.getNumeroExpediente());
			logger.info("Denunciante:"+ loExp.getDenunciate());
			logger.info("cDelito :"+ loExp.getDelito());
			logger.info("Tipo de denuncia :"+ loExp.getTipoDenuncia());
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testConsultarNumeroExpedientesPorIdExpediente() {
		try {
			List<ExpedienteDTO> loExps = service.consultarNumerosExpedientesPorIdExpediente(2949L,null);
			
			for (ExpedienteDTO loExp : loExps) {
				logger.info("Numero Expediente ID:"+ loExp.getNumeroExpedienteId());
				logger.info("Fecha de creacion:"+ loExp.getFechaAperturaNumeroExp());
				if(loExp.getArea() != null && loExp.getArea().getNombre() != null)
					logger.info("Area :"+ loExp.getArea().getNombre());
				logger.info("Numero de expediente:"+ loExp.getNumeroExpediente());
				logger.info("Funcionario:"+ loExp.getResponsableTramite().getNombreCompleto());
				if(loExp.getEstatusNumeroExpediente() != null && loExp.getEstatusNumeroExpediente().getValor() != null)
					logger.info("Estatus Actual :"+ loExp.getEstatusNumeroExpediente().getValor());
			}
			
			
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void testObtnerPermisosDeNumeroExpediente() {
		try {
			List<BitacoraPermisoExpedienteDTO> loPermisos = service.obtenerPermisosDeExpediente(3787L);
			
			for (BitacoraPermisoExpedienteDTO loPermiso : loPermisos) {
				logger.info("Permiso ID:"+ loPermiso.getBitacoraPermisoExpediente());
				logger.info("Numero Expediente ID:"+ loPermiso.getExpediente().getNumeroExpediente());
				logger.info("Funcionario:"+ loPermiso.getFuncionario().getNombreCompleto());
				logger.info("Area:"+ loPermiso.getJerarquiaOrganizacional().getNombre());
				logger.info("Fecha movimiento:"+ loPermiso.getFechaMovimiento());
				logger.info("Fecha limite:"+ loPermiso.getFechaLimite());
				logger.info("Es activo:"+ loPermiso.getEsActivo());
				logger.info("===================================================");
			}
			
			logger.info("TOTAL:"+ loPermisos.size());
			
			
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void testConsultarBitacoraEstatusNumExpedientePorIdExpediente() {
		try {
			List<BitacoraEstatusNumExpedienteDTO> loRegistros = service.consultarBitacoraEstatusNumExpedientePorIdExpediente(2500L);
			
			for (BitacoraEstatusNumExpedienteDTO loRegistro : loRegistros) {
				logger.info("Registro ID:"+ loRegistro.getBitacoraEstatusNumExpedienteId());
				logger.info("Fecha creacion:"+ loRegistro.getFechaCreacion());
				logger.info("Area:"+ loRegistro.getJerarquiaOrganizacional().getNombre());
				logger.info("Numero Expediente ID:"+ loRegistro.getExpediente().getNumeroExpediente());
				logger.info("Funcionario:"+ loRegistro.getFuncionario().getNombreCompleto());
				logger.info("Estatus:"+ loRegistro.getEstatus().getValor());
				logger.info("Actuacion:"+ loRegistro.getTipoActividad().getValor());
				logger.info("Documento:"+ loRegistro.getTipoDocumento().getValor());
				logger.info("===================================================");
			}
			logger.info("TOTAL:"+ loRegistros.size());
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
	public void testBuscadorDeExpedientesAReasignarPM(){
		 try {
				FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
				
				filtroExpedienteDTO.setNumeroExpediente("");
				
				FuncionarioDTO loFuncionarioDTO = new FuncionarioDTO(278L);
				CatDiscriminanteDTO loCatDiscriminanteDTO = new CatDiscriminanteDTO();
				loCatDiscriminanteDTO.setDistrito(new CatDistritoDTO(1L));
				loFuncionarioDTO.setDiscriminante(loCatDiscriminanteDTO);
				
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setFuncionario(loFuncionarioDTO);		
				filtroExpedienteDTO.setUsuario(usuario);
			 
			List<ExpedienteDTO> expedientesConPermisos = service.buscadorDeExpedientesAReasignarPM(filtroExpedienteDTO);
			
			for (ExpedienteDTO expedienteDTO : expedientesConPermisos) {
				logger.info("Numero Expediente ID: " + expedienteDTO.getNumeroExpedienteId());	
			//	logger.info("Numero de caso: " + expedienteDTO.getCasoDTO().getNumeroGeneralCaso());	
				logger.info("Numero de expediente: " + expedienteDTO.getNumeroExpediente());	
				//logger.info("Expediente Id: " + expedienteDTO.getExpedienteId());	
				//logger.info("Estatus del numero de expediente: " + expedienteDTO.getEstatus().getValor());	
				//logger.info("Nombre del responsable: " + expedienteDTO.getResponsableTramite().getNombreCompleto());	
				//logger.info("Delito Principal: " + (expedienteDTO.getDelitoPrincipal() != null && expedienteDTO.getDelitoPrincipal().getCatDelitoDTO().getNombre()!= null ?
					//expedienteDTO.getDelitoPrincipal().getNombreDelito() :"-"));	
				//logger.info("Jerarquia del num exp: " + expedienteDTO.getArea().getAreaId());	
			}
			
			logger.info("Total: "+ expedientesConPermisos.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
		 
		
	}
	

}
