/**
 * Nombre del Programa		: GenerarFolioDelitoPersonaServiceImpl.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP                    Fecha: 26-Abril-2013
 * Marca de cambio        	: N/A
 * Descripcion General    	: N/A
 * Programa Dependient    	: N/A
 * Programa Subsecuente   	: N/A
 * Cond. de ejecucion     	: N/A
 * Dias de ejecucion      	: N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    :N/A
 * Compania                 :N/A
 * Proyecto                 :N/A                      Fecha: N/A
 * Modificacion             :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.delito.impl;

import java.util.Calendar;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.service.delito.GenerarFolioDelitoPersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GenerarFolioDelitoPersonaServiceImpl implements
		GenerarFolioDelitoPersonaService {

	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private DelitoPersonaDAO delitoPersonaDAO;

	private static Long folioDelitoPersona = 0L;
	private static ConfInstitucion institucion = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.delito.GenerarFolioDelitoPersonaService#
	 * generarFolioDelitoPersona()
	 */
	@Override
	public synchronized String generarFolioDelitoPersona()
			throws NSJPNegocioException {

		if (folioDelitoPersona == 0) {
			Long ultimo = delitoPersonaDAO.obtenerUltimoFolioDelitoPersona();
			if (ultimo != null) {
				folioDelitoPersona = ultimo;
			}
		}

		if (institucion == null) {
			institucion = confInstitucionDAO.consultarInsitucionActual();
		}

		if(institucion == null){
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		return institucion.getMonograma()
				+ Calendar.getInstance().get(Calendar.YEAR) + "-"
				+ (++folioDelitoPersona).toString();
	}

}
