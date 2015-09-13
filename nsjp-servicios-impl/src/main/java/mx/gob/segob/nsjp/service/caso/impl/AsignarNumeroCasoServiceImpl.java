/**
* Nombre del Programa : AsignarNumeroCasoServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19 Apr 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servico de generacion de un nuevo numero de caso
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
package mx.gob.segob.nsjp.service.caso.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.domicilio.EntidadFederativaDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.EntidadFederativa;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.service.caso.AsignarNumeroCasoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servico de generacion de un nuevo numero de caso.
 * @version 1.0
 * @author cesarAgustin, rgama
 *
 */
@Service
public class AsignarNumeroCasoServiceImpl implements AsignarNumeroCasoService {

	private final static Logger logger = Logger.getLogger(AsignarNumeroCasoServiceImpl.class);
    
	private String SEPARADOR = "/";

	@Autowired
	private CasoDAO casoDao;	
	@Autowired
	private EntidadFederativaDAO entidadFederativaDAO;
	@Autowired
	private ParametroDAO parametroDAO;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private JerarquiaOrganizacionalDAO jerarquiaOrganizacionalDAO;
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	//     Estado / Instituci�n / Libres / Unidad / A�o / Letra - Consecutivo
	//       3    /      2      /    2   /   3    /  4  /   2   -     5
	// 	   ZAC/PG/XX/UNI/2011/CC-12345		   
	public synchronized CasoDTO asignarNumeroCaso(CasoDTO casoDTO, FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		if (logger.isInfoEnabled())
			logger.info("");

		if (funcionarioDTO == null || funcionarioDTO.getDepartamento() == null || funcionarioDTO.getDepartamento().getArea() == null || funcionarioDTO.getDepartamento().getArea().getAreaId() <= 0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Long idDepartamento = 0L;
		if (funcionarioDTO.getDepartamento().getDepartamentoId() != null && funcionarioDTO.getDepartamento().getDepartamentoId() > 0)
			idDepartamento = funcionarioDTO.getDepartamento().getDepartamentoId();
		Long idArea = funcionarioDTO.getDepartamento().getArea().getAreaId();

		String prefijoDelEstado = "";
		String prefijoDeInstitucion = "";
		//String libres = "XX";
		String distrito = "";
		String claveRegion = "";
		String unidad = "";
		String consecutivoDelCaso = "";

		//Fecha actual
		Date fechaApertura = casoDTO.getFechaApertura() == null ? new Date() : casoDTO.getFechaApertura();
		Calendar calTemp = Calendar.getInstance();
		calTemp.setTime(casoDTO.getFechaApertura());
		String anio = String.valueOf(calTemp.get(Calendar.YEAR));

		//Obtener la institucion actual
		ConfInstitucion institucionActual = casoDao.consultarInsitucionActual();

		//Se obtiene el prefijo de la unidad
		unidad = jerarquiaOrganizacionalDAO.read(idArea).getAbreviatura();
		//Codigo para controlar la lonjitud de la abreviatura de la unidad ya que puede estar mas grande
		if (unidad.length() > 3) {
			unidad = unidad.substring(0, 3);
		}

		//Se obtiene el prefijo del estado
		prefijoDelEstado = obtenerPrefijoDelEstado();

		//Se obtiene el prefijo de la institucion
		ConfInstitucion loConfIns = obtenerInstitucion();
		prefijoDeInstitucion = loConfIns.getMonograma();

		//Se obtiene el distrito
		distrito = funcionarioDTO.getDiscriminante().getDistrito().getClaveRomanaDistrito();
		//Se obtiene la region
		if (funcionarioDTO != null && funcionarioDTO.getDiscriminante() != null) {
			claveRegion = funcionarioDTO.getDiscriminante().getClaveRegion();
		}


		List<Parametro> parametrosNUC = parametroDAO.obtenerPorClaveBase("NUC");
		Boolean conEstado = false;
		Boolean conInstitucion = false;
		Boolean conRegion = false;
		Boolean conDistrito = false;
		Boolean conUnidad = false;

		for (Parametro parametro : parametrosNUC) {
			if (parametro.getClave().equalsIgnoreCase("NUC_ESTADO") && parametro.getValor().equals("*")) {
				conEstado = true;
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_REGION") && parametro.getValor().equals("*")) {
				conRegion = true;
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_INSTITUCION") && parametro.getValor().equals("*")) {
				conInstitucion = true;
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_DISTRITO") && parametro.getValor().equals("*")) {
				conDistrito = true;
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_UNIDAD") && parametro.getValor().equals("*")) {
				conUnidad = true;
			}
		}

		//Se genera el numero de Caso


		if (conEstado) {
			consecutivoDelCaso = prefijoDelEstado;
		}

		if (conInstitucion) {
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = prefijoDeInstitucion;
			}
		 	else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + prefijoDeInstitucion;
			}
		}

		if( conRegion ) {
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = claveRegion;
			} else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + claveRegion;
			}
		}

		if( conDistrito ) {
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = distrito;
			} else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + distrito;
			}
		}

		if( conUnidad ) {
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = unidad;
			} else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + unidad;
			}
		}

		consecutivoDelCaso = consecutivoDelCaso +SEPARADOR + anio;


		// El sistema consulta el �ltimo n�mero de caso creado
		String ultimoNumeroGeneralCaso = casoDao.recuperarUltimoNumeroCasoXCadenaBase(consecutivoDelCaso);

		//Consecutivo del n�mero del caso consultado
		String consecutivoDelCasoBase = consultarConsecutivoCaso(ultimoNumeroGeneralCaso);

		consecutivoDelCaso = consecutivoDelCaso +SEPARADOR + consecutivoDelCasoBase;

		/*
		consecutivoDelCaso = prefijoDelEstado + SEPARADOR + prefijoDeInstitucion + SEPARADOR
				+ claveRegion +SEPARADOR
				+ distrito + SEPARADOR
				+ unidad + SEPARADOR + anio + SEPARADOR + consecutivoDelCaso;
		*/
		Caso caso = new Caso();
		caso.setEstatus(casoDTO.getEstatus().getShort());
		caso.setFechaApertura(fechaApertura);
		caso.setImputado(casoDTO.getImputado());
		caso.setVictima(casoDTO.getVictima());
		caso.setNumeroGeneralCaso(consecutivoDelCaso);								
		CasoDTO resp = new CasoDTO();
		resp.setNumeroGeneralCaso(caso.getNumeroGeneralCaso());
		logger.debug("El Numero de caso generado es:" + consecutivoDelCaso);
		final Long idCaso = casoDao.create(caso);
		resp.setCasoId(idCaso);
		return resp;
	}
	
	private ConfInstitucion obtenerInstitucion() throws NSJPNegocioException {
		ConfInstitucion cveInstitucion = confInstitucionDAO.consultarInsitucionActual();
		return cveInstitucion;
	}

	public String consultarConsecutivoCaso(String ultimoNumeroGeneralCaso) throws NSJPNegocioException{
		String consecutivoDelCaso = "";
		if(ultimoNumeroGeneralCaso!=null && ultimoNumeroGeneralCaso.length() > 0) {
			consecutivoDelCaso = ConsecutivosUtil.incrementarConsecutivoNumeroCaso(ultimoNumeroGeneralCaso);
		} else {
			consecutivoDelCaso = "AA-00001";
		}		
		return consecutivoDelCaso;
	}
	
	public String obtenerPrefijoDelEstado() {
		//Obtenemos el prefijo del estado en la tabla de parametros
		Parametro loParametro= parametroDAO.obtenerPorClave(Parametros.ENTIDAD_FEDERATIVA_DESPLIEGUE);
		logger.info("***obtenerPrefijoDelEstado  :: " +loParametro);
		EntidadFederativa loEntFed =entidadFederativaDAO.read(Long.parseLong(loParametro.getValor()));
		if(loEntFed != null){
			logger.info("MONOGRAMA DE BD  :: " + loEntFed.getMonograma().trim());
			return loEntFed.getMonograma().trim();
		}
		else
			return "";
	}
}
