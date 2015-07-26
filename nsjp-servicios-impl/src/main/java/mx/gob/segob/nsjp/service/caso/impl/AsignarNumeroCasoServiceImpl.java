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
		
		if (funcionarioDTO == null || funcionarioDTO.getDepartamento() == null || funcionarioDTO.getDepartamento().getArea() == null || funcionarioDTO.getDepartamento().getArea().getAreaId()<= 0) 
	           throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Long idDepartamento = 0L;
		if( funcionarioDTO.getDepartamento().getDepartamentoId() != null && funcionarioDTO.getDepartamento().getDepartamentoId()> 0)
			idDepartamento = funcionarioDTO.getDepartamento().getDepartamentoId();
		Long idArea = funcionarioDTO.getDepartamento().getArea().getAreaId();
		
		String prefijoDelEstado = "";
		String prefijoDeInstitucion = "";
		//String libres = "XX";
		String distrito = "";
		String unidad = "";
		String consecutivoDelCaso = "";
		
		//Fecha actual
		Date fechaApertura = casoDTO.getFechaApertura()==null?new Date():casoDTO.getFechaApertura(); 
		Calendar calTemp = Calendar.getInstance();
        calTemp.setTime(casoDTO.getFechaApertura());
        String anio = String.valueOf(calTemp.get(Calendar.YEAR));
        
        //Obtener la institucion actual
        ConfInstitucion institucionActual = casoDao.consultarInsitucionActual();

        //Se obtiene el prefijo de la unidad
      	unidad = jerarquiaOrganizacionalDAO.read(idArea).getAbreviatura();
      	//Codigo para controlar la lonjitud de la abreviatura de la unidad ya que puede estar mas grande
      	if(unidad.length()>3){
      		unidad=unidad.substring(0, 3);
      	}
      	
		// El sistema consulta el �ltimo n�mero de caso creado
		String ultimoNumeroGeneralCaso = casoDao.recuperarUltimoNumero(institucionActual.getMonograma());

		//Consecutivo del n�mero del caso consultado
		consecutivoDelCaso = consultarConsecutivoCaso(ultimoNumeroGeneralCaso);
							
		//Se obtiene el prefijo del estado
		prefijoDelEstado = obtenerPrefijoDelEstado();
		
		//Se obtiene el prefijo de la institucion
		ConfInstitucion loConfIns = obtenerInstitucion();
		prefijoDeInstitucion = loConfIns.getMonograma();

		//Se obtiene el distrito
		distrito = funcionarioDTO.getDiscriminante().getDistrito().getClaveRomanaDistrito();

		//Se genera el numero de Caso
		consecutivoDelCaso = prefijoDelEstado + SEPARADOR + prefijoDeInstitucion + SEPARADOR + distrito + SEPARADOR +
			unidad + SEPARADOR + anio + SEPARADOR + consecutivoDelCaso;
		
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
		if(ultimoNumeroGeneralCaso!=null && ultimoNumeroGeneralCaso.length() == 27) {
			//pe 01/02/XX/RBO/2011/CC-12345 -> CC-12346 
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
