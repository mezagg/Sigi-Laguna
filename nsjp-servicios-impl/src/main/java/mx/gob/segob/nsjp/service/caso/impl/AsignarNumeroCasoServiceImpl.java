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

import java.util.*;

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



	private class Llave{
		HashSet<String> llaves = new HashSet<String>();
		String general;

		public Llave(){}


		public void init(String data){
			StringTokenizer st = new StringTokenizer(data,",");

			while( st.hasMoreTokens()){
				if( st.countTokens() == 1){
					general = st.nextToken();
				}else {
					llaves.add(st.nextToken());
				}
			}
		}

		public String getLlave(String baseData){
			if( llaves.contains(baseData) ){
				return baseData;
			}else {
				return general;
			}
		}
	}
	
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
        String agencia = "";
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

        //Se obtiene la agencia
        if (funcionarioDTO != null && funcionarioDTO.getDiscriminante() != null) {
            agencia = funcionarioDTO.getDiscriminante().getClave();
        }

		List<Parametro> parametrosNUC = parametroDAO.obtenerPorClaveBase("NUC");
		Boolean conEstado = false;
		Boolean conInstitucion = false;
		Boolean conRegion = false;
		Boolean conDistrito = false;
		Boolean conUnidad = false;
		Boolean conAgencia = false;

		//Dsitribucion de contadores y llaves
		Llave estadoLlave = new Llave();
		Llave institucionLlave = new Llave();
		Llave regionLlave = new Llave();
		Llave distritoLlave = new Llave();
		Llave unidadLlave = new Llave();
        Llave agengiaLlave = new Llave();


		for (Parametro parametro : parametrosNUC) {
			if (parametro.getClave().equalsIgnoreCase("NUC_ESTADO") ) {
				conEstado = true;
				estadoLlave.init(parametro.getValor());
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_REGION") ) {
				conRegion = true;
				regionLlave.init(parametro.getValor());
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_INSTITUCION") ) {
				conInstitucion = true;
				institucionLlave.init(parametro.getValor());
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_DISTRITO") ) {
				conDistrito = true;
				distritoLlave.init(parametro.getValor());
			}
			if (parametro.getClave().equalsIgnoreCase("NUC_UNIDAD") ) {
				conUnidad = true;
				unidadLlave.init(parametro.getValor());
			}
            if (parametro.getClave().equalsIgnoreCase("NUC_AGENCIA") ) {
                conAgencia = true;
                agengiaLlave.init(parametro.getValor());
            }
		}

		//Se genera el numero de Caso


		if (conEstado) {
			prefijoDelEstado = estadoLlave.getLlave(prefijoDelEstado);
			consecutivoDelCaso = prefijoDelEstado;
		}

		if (conInstitucion) {
			prefijoDeInstitucion = institucionLlave.getLlave(prefijoDeInstitucion);
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = prefijoDeInstitucion;
			}
		 	else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + prefijoDeInstitucion;
			}
		}

		if( conRegion ) {
			claveRegion = regionLlave.getLlave(claveRegion);
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = claveRegion;
			} else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + claveRegion;
			}
		}

		if( conDistrito ) {
			distrito = distritoLlave.getLlave(distrito);
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = distrito;
			} else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + distrito;
			}
		}

		if( conUnidad ) {
			unidad = unidadLlave.getLlave(unidad);
			if (consecutivoDelCaso.length() == 0) {
				consecutivoDelCaso = unidad;
			} else {
				consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + unidad;
			}
		}

        if( conAgencia ) {
            agencia = agengiaLlave.getLlave(agencia);
            if (consecutivoDelCaso.length() == 0) {
                consecutivoDelCaso = agencia;
            } else {
                consecutivoDelCaso = consecutivoDelCaso + SEPARADOR + agencia;
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
