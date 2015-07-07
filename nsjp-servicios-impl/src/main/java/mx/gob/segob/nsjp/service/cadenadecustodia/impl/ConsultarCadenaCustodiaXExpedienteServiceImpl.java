/**
 * Nombre del Programa : ConsultarCadenaCustodiaXExpedienteServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 30-jun-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.cadenadecustodia.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.cadenadecustodia.CadenaDeCustodiaDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.CadenaDeCustodia;
import mx.gob.segob.nsjp.model.Evidencia;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.cadenadecustodia.ConsultarCadenaCustodiaXExpedienteService;
import mx.gob.segob.nsjp.service.cadenadecustodia.impl.transform.CadenaDeCustodiaTransformer;
import mx.gob.segob.nsjp.service.evidencia.impl.transform.EvidenciaTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ConsultarCadenaCustodiaXExpedienteServiceImpl
        implements ConsultarCadenaCustodiaXExpedienteService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(ConsultarCadenaCustodiaXExpedienteServiceImpl.class);

    @Autowired
    private CadenaDeCustodiaDAO cadenaDeCustodiaDAO;
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CadenaDeCustodiaDTO> consultarCadenaCustodiaXExpediente(
            ExpedienteDTO expedienteDto) throws NSJPNegocioException {
        if(expedienteDto == null){
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("\n\nconsultarCadenaCustodiaXEpediente = " +
                    expedienteDto + "\n\n");
        }
        // expedienteDto -> expediente
        Expediente expediente = ExpedienteTransformer.
                transformarExpediente(expedienteDto);
        // llamada al dao
        List<CadenaDeCustodia> cadenas = cadenaDeCustodiaDAO.consultarCadenaCustodiaXExpediente(expediente);
        List<CadenaDeCustodiaDTO> cadenasDTO=new ArrayList<CadenaDeCustodiaDTO>();
        for (CadenaDeCustodia cad : cadenas) {
        	cadenasDTO.add(CadenaDeCustodiaTransformer.transformarCadenaDeCustodia(cad));
		}
        return cadenasDTO;
    }

	@Override
	public CadenaDeCustodiaDTO consultarCadenaCustodia(
			CadenaDeCustodiaDTO cadenaDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled()) 
            logger.debug("\n************ SERVICIO PARA CONSULTAR UNA CADENA DE CUSTODIA DADA **************\n");
		
		if(cadenaDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(cadenaDTO.getCadenaDeCustodiaId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		CadenaDeCustodia cadena = cadenaDeCustodiaDAO.read(cadenaDTO.getCadenaDeCustodiaId());
		return CadenaDeCustodiaTransformer.transformarCadenaDeCustodia(cadena);
	}

	@Override
	public List<EvidenciaDTO> consultarCadenaCustodiaXExpedienteYFolio(
			Long expedienteId) throws NSJPNegocioException{	
		
		if(expedienteId==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		NumeroExpediente ne = numeroExpedienteDAO.read(expedienteId);
		
		List<Evidencia> evidencias = cadenaDeCustodiaDAO.consultarEvidenciasDeCadenaCustodiaXExpedienteId(ne.getExpediente().getExpedienteId());
        List<EvidenciaDTO> evidenciasDTO = new ArrayList<EvidenciaDTO>();
        for (Evidencia ev : evidencias) {
        	evidenciasDTO.add(EvidenciaTransformer.transformarEvidencia(ev, true));
		}
        
        return evidenciasDTO;
	}
   
}
