/**
 * Nombre del Programa : IngresarObjetoServiceImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 26/08/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion del servicios para ingresar un objeto dependiendo de la instancia 
 * 							de la cual fue creado el objeto.
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
package mx.gob.segob.nsjp.service.objeto.impl;

import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.objeto.AeronaveDTO;
import mx.gob.segob.nsjp.dto.objeto.AnimalDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.EquipoComputoDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.ElementoArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigitalId;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.service.archivo.GuardarArchivoDigitalService;
import mx.gob.segob.nsjp.service.cadenadecustodia.ConsultarCadenaDeCustodiaXFolioService;
import mx.gob.segob.nsjp.service.cadenadecustodia.GuardarCadenaCustodiaService;
import mx.gob.segob.nsjp.service.catalogo.AdministrarCatalogoService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.elemento.AdjuntarArchivoAElementoService;
import mx.gob.segob.nsjp.service.elemento.impl.ElementoTransformer;
import mx.gob.segob.nsjp.service.eslabon.GuardarEslabonService;
import mx.gob.segob.nsjp.service.evidencia.GuardarEvidenciaService;
import mx.gob.segob.nsjp.service.objeto.IngresarAeronaveService;
import mx.gob.segob.nsjp.service.objeto.IngresarAnimalService;
import mx.gob.segob.nsjp.service.objeto.IngresarArmaService;
import mx.gob.segob.nsjp.service.objeto.IngresarDocumentoOficialService;
import mx.gob.segob.nsjp.service.objeto.IngresarEmbarcacionService;
import mx.gob.segob.nsjp.service.objeto.IngresarEquipoDeComputoService;
import mx.gob.segob.nsjp.service.objeto.IngresarExplosivoService;
import mx.gob.segob.nsjp.service.objeto.IngresarJoyaService;
import mx.gob.segob.nsjp.service.objeto.IngresarNumerarioService;
import mx.gob.segob.nsjp.service.objeto.IngresarObjetoPericialService;
import mx.gob.segob.nsjp.service.objeto.IngresarObjetoService;
import mx.gob.segob.nsjp.service.objeto.IngresarObraArteService;
import mx.gob.segob.nsjp.service.objeto.IngresarOtroObjetoService;
import mx.gob.segob.nsjp.service.objeto.IngresarSustanciaService;
import mx.gob.segob.nsjp.service.objeto.IngresarTelefonoService;
import mx.gob.segob.nsjp.service.objeto.IngresarVegetalService;
import mx.gob.segob.nsjp.service.objeto.IngresarVehiculoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicios para ingresar un objeto dependiendo de la
 * instancia de la cual fue creado el objeto.
 * 
 * @version 1.0
 * @author GustavoBP
 * 
 */
@Service
@Transactional
public class IngresarObjetoServiceImpl implements IngresarObjetoService {

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(IngresarObjetoServiceImpl.class);

	@Autowired
	private IngresarVehiculoService ingresarVehiculoService;
	@Autowired
	private IngresarEquipoDeComputoService ingresarEquipoDeComputoService;
	@Autowired
	private IngresarTelefonoService ingresarTelefonoService;
	@Autowired
	private IngresarArmaService ingresarArmaService;
	@Autowired
	private IngresarExplosivoService ingresarExplosivoService;
	@Autowired
	private IngresarSustanciaService ingresarSustanciaService;
	@Autowired
	private IngresarAnimalService ingresarAnimalService;
	@Autowired
	private IngresarAeronaveService ingresarAeronaveService;
	@Autowired
	private IngresarEmbarcacionService ingresarEmbarcacionService;
	@Autowired
	private IngresarNumerarioService ingresarNumerarioService;
	@Autowired
	private IngresarVegetalService ingresarVegetalService;
	@Autowired
	private IngresarDocumentoOficialService ingresarDocumentoOficialService;
	@Autowired
	private IngresarJoyaService ingresarJoyaService;
	@Autowired
	private IngresarObraArteService ingresarObraArteService;
	@Autowired
	private IngresarObjetoPericialService ingresarObjetoPericialService;
	@Autowired
	private IngresarOtroObjetoService ingresarOtroObjetoService;
	
	@Autowired
	private GuardarArchivoDigitalService guardarArchivoDigitalService;
	@Autowired
	private AdjuntarArchivoAElementoService adjuntarArchivoAElementoService;
	@Autowired
	private GuardarDocumentoService guardarDocumentoService;
	@Autowired
	private ConsultarCadenaDeCustodiaXFolioService consultarCadenaDeCustodiaXFolioService;
	@Autowired
	private GuardarCadenaCustodiaService guardarCadenaCustodiaService;
	@Autowired
	private GuardarEvidenciaService guardarEvidenciaService;
	@Autowired
	private ElementoDAO eleDao;
    @Autowired
    private AdministrarCatalogoService administrarCatalogoService;

	@Autowired
	private GuardarEslabonService guardarEslabonService;
	
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	
    @Autowired
    private ElementoArchivoDigitalDAO elementoArchivoDigitalDAO;

	public Long ingresarObjetos(List<ObjetoDTO> objetosDTO)
			throws NSJPNegocioException {
		Long cont = 0L;
		for (ObjetoDTO objetoDTO : objetosDTO) {
			ingresarObjeto(objetoDTO);
			cont++;
		}
		return cont;
	}

	@Override
	public Long ingresarObjetoCarpetaInvestigacion(ObjetoDTO objetoDTO)
			throws NSJPNegocioException {
		
		if (objetoDTO == null || objetoDTO.getFolioElemento() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		final Elemento elemento = eleDao.consultarElementoPorFolio(objetoDTO.getFolioElemento());
		
		Long idObjeto = 0L;
		
		if(elemento != null){//Se asigna el id para que actualice la informaci�n del objeto
			objetoDTO.setElementoId(elemento.getElementoId());
		}
		
		if (objetoDTO instanceof AeronaveDTO) {
			idObjeto = ingresarAeronaveService
					.ingresarAeronave((AeronaveDTO) objetoDTO);
		}
		if (objetoDTO instanceof AnimalDTO) {
			idObjeto = ingresarAnimalService
					.ingresarAnimal((AnimalDTO) objetoDTO);
		}
		if (objetoDTO instanceof ArmaDTO) {
			
			ConfInstitucion institucionActual = confInstitucionDAO.consultarInsitucionActual();
			if (institucionActual.getConfInstitucionId().equals(Instituciones.DEF.getValorId())){
				ArmaDTO armaDTO=(ArmaDTO) objetoDTO;
				ValorDTO valor=armaDTO.getMarca();
				valor.setCatalogoPadre(armaDTO.getCatalogoPadre());
				valor.setNombreCampo(armaDTO.getValor());
	        	Long idMarca=administrarCatalogoService.validadorCatalogosInterIntitucional(valor, true);
	        	armaDTO.getMarca().setIdCampo(idMarca);				
			}
	        
	        idObjeto = ingresarArmaService.ingresarArma((ArmaDTO) objetoDTO);
		}
		if (objetoDTO instanceof DocumentoOficialDTO) {
			idObjeto = ingresarDocumentoOficialService
					.ingresarDocumentoOficial((DocumentoOficialDTO) objetoDTO);
		}
		if (objetoDTO instanceof EmbarcacionDTO) {
			idObjeto = ingresarEmbarcacionService
					.ingresarEmbarcacion((EmbarcacionDTO) objetoDTO);
		}
		if (objetoDTO instanceof EquipoComputoDTO) {
			idObjeto = ingresarEquipoDeComputoService
					.ingresarEquipoComputo((EquipoComputoDTO) objetoDTO);
		}
		if (objetoDTO instanceof ExplosivoDTO) {
			idObjeto = ingresarExplosivoService
					.ingresarExplosivo((ExplosivoDTO) objetoDTO);
		}
		if (objetoDTO instanceof JoyaDTO) {
			idObjeto = ingresarJoyaService.ingresarJoya((JoyaDTO) objetoDTO);
		}
		if (objetoDTO instanceof NumerarioDTO) {
			idObjeto = ingresarNumerarioService
					.ingresarNumerario((NumerarioDTO) objetoDTO);
		}
		if (objetoDTO instanceof ObraArteDTO) {
			idObjeto = ingresarObraArteService
					.ingresarObraArte((ObraArteDTO) objetoDTO);
		}
		if (objetoDTO instanceof SustanciaDTO) {
			idObjeto = ingresarSustanciaService
					.ingresarSustancia((SustanciaDTO) objetoDTO);
		}
		if (objetoDTO instanceof TelefoniaDTO) {
			idObjeto = ingresarTelefonoService
					.ingresarTelefono((TelefoniaDTO) objetoDTO);
		}
		if (objetoDTO instanceof VegetalDTO) {
			idObjeto = ingresarVegetalService
					.ingresarVegetalService((VegetalDTO) objetoDTO);
		}
		if (objetoDTO instanceof VehiculoDTO) {
			idObjeto = ingresarVehiculoService
					.ingresarVehiculo((VehiculoDTO) objetoDTO);
		}
		if (objetoDTO instanceof ObjetoPericialDTO){
			idObjeto = ingresarObjetoPericialService
					.ingresarObjetoPericial((ObjetoPericialDTO) objetoDTO);
		}
		//Este es para ingresar el objeto de tipo Otro y/o los objetos que sean del tipo ObjetoDTO
		if (objetoDTO.getClass().equals(ObjetoDTO.class)){
			idObjeto = ingresarOtroObjetoService
					.ingresarOtroObjeto((ObjetoDTO) objetoDTO);
		}
			
		objetoDTO.setElementoId(idObjeto);
		
		if(elemento == null){//Significa que no se ha registrado cadena de custodia
			
			if (objetoDTO.getEvidencia() != null) {
				EvidenciaDTO evidencia = objetoDTO.getEvidencia();
				CadenaDeCustodiaDTO cadena = objetoDTO.getEvidencia()
						.getCadenaDeCustodia();
				if (cadena != null && cadena.getFolio() != null && !cadena.getFolio().equals("null")) {
					cadena = consultarCadenaDeCustodiaXFolioService
							.consultarCadenaDeCustodiaPorFolioExpediente(cadena
									.getFolio(), objetoDTO.getExpedienteDTO()
									.getExpedienteId());
					if (cadena == null) {
						cadena = objetoDTO.getEvidencia().getCadenaDeCustodia();
						cadena = guardarCadenaCustodiaService
								.guardarCadenaCustodia(cadena,
										objetoDTO.getExpedienteDTO());
					}
					logger.info("CADENA DE CUSTOIA ENCONTRADA " + cadena.getFolio()
					        + " CON ID " + cadena.getCadenaDeCustodiaId());
					
					
					Set<EslabonDTO> eslabones = evidencia.getEslabones();
					evidencia.setEslabones(null);
					evidencia.setCadenaDeCustodia(cadena);
					evidencia.setObjeto(objetoDTO);
					evidencia.setEvidenciaId(guardarEvidenciaService
							.guardarEvidencia(evidencia));

					for (EslabonDTO eslabon : eslabones) {
						if(eslabon.getDocumentoDTO() != null){
							DocumentoDTO documento = eslabon.getDocumentoDTO();
							// De requerir la creacion de una nueva actividad de un tipo espec�fico, basta con cambiarle el valor al par�metro
							// nuevaActividad = null, por el nuevo valor. En caso contrario, es el flujo normal.
							Long idDocumento = guardarDocumentoService.guardarDocumento(
									documento, new ExpedienteDTO(objetoDTO
											.getExpedienteDTO().getExpedienteId()), null,null);
							documento.setDocumentoId(idDocumento);
							eslabon.setDocumentoDTO(documento);
						}
						guardarEslabonService.guardarEslabonCarpetaInvestigacion(
								evidencia, eslabon);
					}
					
				}
			}
		}

	
		//Se valida que traiga archivos digitales (Imagenes asociadas)
		if(objetoDTO.getImagenesAsociadas() != null && !objetoDTO.getImagenesAsociadas().isEmpty() && objetoDTO.getImagenesAsociadas().size() > 0){
			
			for (ArchivoDigitalDTO imagen : objetoDTO.getImagenesAsociadas()) {
				//Se registra el archivo digital y la asociacion entre el objeto (elemento) y archivo digital
				 ElementoArchivoDigitalId loElementoArchivoDigitalId = new ElementoArchivoDigitalId();
		        loElementoArchivoDigitalId.setArchivoDigitalId(guardarArchivoDigitalService.guardarArchivoDigital(imagen));
		        loElementoArchivoDigitalId.setElementoId(idObjeto);
		        
		        ElementoArchivoDigital loElementoArchivoDigital = new ElementoArchivoDigital();
		        loElementoArchivoDigital.setId(loElementoArchivoDigitalId);
		        
		        elementoArchivoDigitalDAO.create(loElementoArchivoDigital);
			}
		}
		
		return idObjeto;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.objeto.IngresarObjetoService#ingresarObjeto(mx.gob.segob.nsjp.dto.objeto.ObjetoDTO)
	 */
	public Long ingresarObjeto(ObjetoDTO objetoDTO) throws NSJPNegocioException {
		Long idObjeto = 0L;

		if (objetoDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		// Se extrae el Archivo Digital para que primero se guarde el objeto y
		// posteriormente se adjunte el archivo digital.
		// Solucion al problema de guardar primero el archivo sin tener la
		// refrencia
		// del objeto
		ArchivoDigitalDTO archivoDigitalDTO = objetoDTO.getFotoDelElemento();
		objetoDTO.setFotoDelElemento(null);

		if (objetoDTO.getEvidencia() != null) {
			EvidenciaDTO evidencia = objetoDTO.getEvidencia();
			evidencia.setEvidenciaId(guardarEvidenciaService
					.guardarEvidencia(evidencia));
			objetoDTO.setEvidencia(evidencia);
		}

		if (objetoDTO.getCadenaDeCustodia() != null)

			if (objetoDTO instanceof AeronaveDTO) {
				idObjeto = ingresarAeronaveService
						.ingresarAeronave((AeronaveDTO) objetoDTO);
			}
		if (objetoDTO instanceof AnimalDTO) {
			idObjeto = ingresarAnimalService
					.ingresarAnimal((AnimalDTO) objetoDTO);
		}
		if (objetoDTO instanceof ArmaDTO) {
			idObjeto = ingresarArmaService.ingresarArma((ArmaDTO) objetoDTO);
		}
		if (objetoDTO instanceof DocumentoOficialDTO) {
			idObjeto = ingresarDocumentoOficialService
					.ingresarDocumentoOficial((DocumentoOficialDTO) objetoDTO);
		}
		if (objetoDTO instanceof EmbarcacionDTO) {
			idObjeto = ingresarEmbarcacionService
					.ingresarEmbarcacion((EmbarcacionDTO) objetoDTO);
		}
		if (objetoDTO instanceof EquipoComputoDTO) {
			idObjeto = ingresarEquipoDeComputoService
					.ingresarEquipoComputo((EquipoComputoDTO) objetoDTO);
		}
		if (objetoDTO instanceof ExplosivoDTO) {
			idObjeto = ingresarExplosivoService
					.ingresarExplosivo((ExplosivoDTO) objetoDTO);
		}
		// if(objetoDTO instanceof InmuebleDTO){
		// idObjeto =
		// ingresarInmuebleService.ingresarInmueble((InmuebleDTO)objetoDTO);
		// }
		if (objetoDTO instanceof JoyaDTO) {
			idObjeto = ingresarJoyaService.ingresarJoya((JoyaDTO) objetoDTO);
		}
		if (objetoDTO instanceof NumerarioDTO) {
			idObjeto = ingresarNumerarioService
					.ingresarNumerario((NumerarioDTO) objetoDTO);
		}
		if (objetoDTO instanceof ObraArteDTO) {
			idObjeto = ingresarObraArteService
					.ingresarObraArte((ObraArteDTO) objetoDTO);
		}
		if (objetoDTO instanceof SustanciaDTO) {
			idObjeto = ingresarSustanciaService
					.ingresarSustancia((SustanciaDTO) objetoDTO);
		}
		if (objetoDTO instanceof TelefoniaDTO) {
			idObjeto = ingresarTelefonoService
					.ingresarTelefono((TelefoniaDTO) objetoDTO);
		}
		if (objetoDTO instanceof VegetalDTO) {
			idObjeto = ingresarVegetalService
					.ingresarVegetalService((VegetalDTO) objetoDTO);
		}
		if (objetoDTO instanceof VehiculoDTO) {
			idObjeto = ingresarVehiculoService
					.ingresarVehiculo((VehiculoDTO) objetoDTO);
		}
		if (objetoDTO instanceof ObjetoPericialDTO ){
			idObjeto = ingresarObjetoPericialService
					.ingresarObjetoPericial((ObjetoPericialDTO) objetoDTO);
		}

		if (archivoDigitalDTO != null) {
			objetoDTO.setElementoId(idObjeto);
			adjuntarArchivoAElementoService.adjuntarArchivoAElemento(objetoDTO,
					archivoDigitalDTO);
		}
		return idObjeto;
	}
}
