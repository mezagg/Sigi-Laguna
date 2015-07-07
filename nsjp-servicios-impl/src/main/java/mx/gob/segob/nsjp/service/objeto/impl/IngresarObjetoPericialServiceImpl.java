/**
 * Nombre del Programa : IngresarObjetoPericialServiceImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 12/09/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Implentacion de metodos del servicio de ingresar un objeto del tipo pericial
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                   Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.objeto.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import mx.gob.segob.nsjp.comun.enums.elemento.Elementos;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.elemento.CalidadDAO;
import mx.gob.segob.nsjp.dao.objeto.ObjetoPericialDAO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.ObjetoPericial;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.objeto.IngresarObjetoPericialService;
import mx.gob.segob.nsjp.service.objeto.impl.transform.ObjetoPericialTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implentacion de metodos del servicio de ingresar un objeto del tipo pericial
 * @author GustavoBP
 *
 */
@Service
@Transactional
public class IngresarObjetoPericialServiceImpl implements
		IngresarObjetoPericialService {

	private final static Logger logger = Logger.getLogger(IngresarObjetoPericialServiceImpl.class);
    @Autowired
    private ObjetoPericialDAO objetoPericialDAO;
    @Autowired
    private CalidadDAO calidadDAO;
    @Autowired
    private GuardaOActualizaEvidencia guardaOActualizaEvidencia;

    
	public Long ingresarObjetoPericial(ObjetoPericialDTO objetoPericialDTO)
			throws NSJPNegocioException {
		if(objetoPericialDTO==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Long idObjetoPericial = null;
		
		if(objetoPericialDTO.getElementoId() == null || objetoPericialDTO.getElementoId()==0){
			idObjetoPericial = guardarObjetoPericial(objetoPericialDTO);
		}else{
			idObjetoPericial = actualizaObjetoPericial(objetoPericialDTO);
		}
			
		return idObjetoPericial;
	}
	
	private Long guardarObjetoPericial(ObjetoPericialDTO  objetoPericialDTO)
			throws NSJPNegocioException {
		if(objetoPericialDTO.getExpedienteDTO() == null ||
				objetoPericialDTO.getExpedienteDTO().getExpedienteId() ==null){
			logger.error("El Expediente Id  es requerido para el guardado");
            throw new NSJPNegocioException(CodigoError.FORMATO);
		}
		
		Long idCalidad = 0L;
        Calidad cal = new Calidad();
        ObjetoPericial objetoPericial = new ObjetoPericial();
        
        objetoPericial = ObjetoPericialTransformer.transformarObjetoPericial(objetoPericialDTO);
        
        logger.debug("Verificando Calidad");
        
        if (objetoPericialDTO.getCalidadDTO() != null && objetoPericialDTO.getCalidadDTO().getDescripcionEstadoFisico() != null) {
            cal.setDescripcionEstadoFisico(objetoPericialDTO.getCalidadDTO().getDescripcionEstadoFisico());
        }
        if (objetoPericialDTO.getCalidadDTO() != null && objetoPericialDTO.getCalidadDTO().getCalidades() != null) {
            cal.setTipoCalidad(new Valor(objetoPericialDTO.getCalidadDTO().getCalidades().getValorId()));
        }
        idCalidad = this.calidadDAO.create(cal);
        logger.debug("Insertando Calidad " + idCalidad);
        
        cal.setCalidadId(idCalidad);
        objetoPericial.setCalidad(cal);
        objetoPericial.setValorByTipoObjetoVal(new Valor(Objetos.PERICIAL.getValorId()));
        objetoPericial.setTipoElemento(new Valor(Elementos.OBJETO.getValorId()));
        
        Long idObjetoPericial = objetoPericialDAO.create(objetoPericial);
        objetoPericialDTO.setElementoId(idObjetoPericial);
        
        guardaOActualizaObjetos(objetoPericialDTO);
        logger.debug("Se genero el Objeto Pericial con id: " + idObjetoPericial);
        return idObjetoPericial;
	}

	 private Long actualizaObjetoPericial(ObjetoPericialDTO objetoPericialDTO)
	            throws NSJPNegocioException {
	    	
	        if(objetoPericialDTO == null || objetoPericialDTO.getElementoId() == null ||
	                objetoPericialDTO.getElementoId() <= 0){
	            logger.debug("Se requier un ObjetoPericial y objetoPericialDto.elementoid > 0 : " +
	                    objetoPericialDTO);
	            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	        }
	        ObjetoPericial objetoPericial = objetoPericialDAO.read(objetoPericialDTO.getElementoId());
	        ObjetoPericial actualizacion =
	                ObjetoPericialTransformer.transformarObjetoPericial(objetoPericialDTO);
	        actualizaObjeto(actualizacion, objetoPericial);
	        guardaOActualizaObjetos(objetoPericialDTO);
	        objetoPericialDAO.merge(objetoPericial);
	        return objetoPericial.getElementoId();
	    }

	 
	private void guardaOActualizaObjetos(ObjetoPericialDTO objetoPericialDTO) throws NSJPNegocioException {
        CadenaDeCustodiaDTO cadenaDeCustodia = objetoPericialDTO.getCadenaDeCustodia();
        if (cadenaDeCustodia != null) {
            guardaOActualizaEvidencia.guardaOActualizaEvidencia(objetoPericialDTO);
        }
    }
	
	/**
     * Inspeccciona el objeto {@code actualizacion} y busca aquellos atributos
     * distintos de null de aquellos metodos que empieza con get y con estos
     * campos actualiza los valores de {@code actualizable} invocando al metodo
     * set correspondiente.
     * @param actualizador El objeto del que se toman los valores distintos de
     * null.
     * @param actualizable El objeto al cual se le actualizan los valores.
     * @throws IllegalArgumenException en caso que los objetos que recibe no
     * sean del mismo tipo.
     */
    public static void actualizaObjeto(Object actualizador, Object actualizable){
        if (actualizador == null || actualizable == null) {
            return;
        }
        if (!actualizador.getClass().equals(actualizable.getClass())) {
            if (logger.isDebugEnabled()) {
                logger.debug("actualizador.getClass() = " + actualizador.getClass());
                logger.debug("actualizable.getClass() = " + actualizable.getClass());
            }
            throw new IllegalArgumentException("Los objetos que se pueden"
                    + " actualizar con este metodo deben ser de la misma clase");
        }
        // Buscamos los campos del objeto actualizacion.
        Method[] metodos = actualizador.getClass().getMethods();
        for (Method metodo : metodos) {
            metodo.setAccessible(true);
            try {
                if (metodo.getName().startsWith("get")) {
                    // Obtenemos el valor del campo
                    Object objetoEnActualizacion = metodo.invoke(actualizador);
                    // Si el valor del campo es distinto de null
                    if (objetoEnActualizacion != null) {
                        String nombreCampo = metodo.getName().substring(3);
                        Method setter = actualizable.getClass().
                                getMethod("set" + nombreCampo,
                                objetoEnActualizacion.getClass());
                        setter.invoke(actualizable, objetoEnActualizacion);
                    }
                }
            } catch (InvocationTargetException ex) {
                logger.debug(ex);
            } catch (NoSuchMethodException ex) {
                logger.debug(ex);
            } catch (SecurityException ex) {
                logger.debug(ex);
            } catch (IllegalArgumentException ex) {
                logger.debug(ex);
            } catch (IllegalAccessException ex) {
                logger.debug(ex);
            }
        }
	
    }
}
