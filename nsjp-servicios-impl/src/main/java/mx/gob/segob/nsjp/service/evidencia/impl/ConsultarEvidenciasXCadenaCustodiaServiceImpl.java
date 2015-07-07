/**
 * Nombre del Programa : ConsultarEvidenciasXCadenaCustodiaServiceImpl.java
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
package mx.gob.segob.nsjp.service.evidencia.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.eslabon.EslabonDAO;
import mx.gob.segob.nsjp.dao.evidencia.EvidenciaDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO;
import mx.gob.segob.nsjp.model.CadenaDeCustodia;
import mx.gob.segob.nsjp.model.Eslabon;
import mx.gob.segob.nsjp.model.Evidencia;
import mx.gob.segob.nsjp.model.Objeto;
import mx.gob.segob.nsjp.model.RelacionDocumentoElemento;
import mx.gob.segob.nsjp.model.SolicitudPericial;
import mx.gob.segob.nsjp.service.cadenadecustodia.impl.transform.CadenaDeCustodiaTransformer;
import mx.gob.segob.nsjp.service.eslabon.impl.EslabonTransformer;
import mx.gob.segob.nsjp.service.evidencia.ConsultarEvidenciasXCadenaCustodiaService;
import mx.gob.segob.nsjp.service.evidencia.impl.transform.EvidenciaTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

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
public class ConsultarEvidenciasXCadenaCustodiaServiceImpl
        implements ConsultarEvidenciasXCadenaCustodiaService {

    /**
     * Logger de la clase.
     */
    private final static Logger logger = Logger.getLogger(
            ConsultarEvidenciasXCadenaCustodiaServiceImpl.class);
    @Autowired
    private EvidenciaDAO evidenciaDao;
    @Autowired
    private EslabonDAO eslabonDao;
    @Autowired
    SolicitudPericialDAO solicitudPericialDAO;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<EvidenciaDTO> consultarEvidenciasXCadenaCustodia(
            CadenaDeCustodiaDTO cadenaDeCustodiaDTO) throws NSJPNegocioException {
        if(cadenaDeCustodiaDTO == null || cadenaDeCustodiaDTO.getCadenaDeCustodiaId() == null){
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        // cadenaDeCustodiaDTO -> cadenaDeCustodia
        CadenaDeCustodia cadenaDeCustodia = CadenaDeCustodiaTransformer.
                transformarCadenaDeCustodia(cadenaDeCustodiaDTO);
        // consultamos las evidencias desde el dao
        List<Evidencia> evidencias =
                evidenciaDao.consultarEvidenciasXCadenaCustodia(cadenaDeCustodia);
        List<EvidenciaDTO> evidenciasDto = Collections.EMPTY_LIST;
        // si hubo evidencias traidas por el dao...
        if (!evidencias.isEmpty()) {
            evidenciasDto = new LinkedList<EvidenciaDTO>();
            for (Evidencia evidencia : evidencias) {
                // convertimos cada evidencia -> evidenciaDto y la agregamos
                // al resultado final.
                EvidenciaDTO evidenciaDto =
                        EvidenciaTransformer.transformarEvidencia(evidencia, true);
                // Consultamos el ultimo eslabon de la evidencia
                Eslabon ultimoEslabon =
                        eslabonDao.consultaUltimoEslabonXEvidenciaYTipo(evidencia,null);
                // Eslabon -> EslabonDTO
                if (ultimoEslabon != null) {
                    EslabonDTO ultimoEslabonDto =
                            EslabonTransformer.transformarEslabon(ultimoEslabon);
                    evidenciaDto.setUltimoEslabon(ultimoEslabonDto);
                }
                evidenciasDto.add(evidenciaDto);
            }
        }
        return evidenciasDto;
    }
    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.evidencia.ConsultarEvidenciasXCadenaCustodiaService#consultarEvidenciaXCadenaCustodiaYPeritoDeSolicitudPericial(mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO, mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO)
     */
	@Override
	public List<EvidenciaDTO> consultarEvidenciaXCadenaCustodiaYPeritoDeSolicitudPericial(
			CadenaDeCustodiaDTO cadena, SolicitudPericialDTO solicitud)
			throws NSJPNegocioException {
		SolicitudPericial sol = solicitudPericialDAO.read(solicitud.getDocumentoId());
		List<EvidenciaDTO> evidencias = consultarEvidenciasXCadenaCustodia(cadena);
		FuncionarioDTO perito = null;
		if(evidencias != null){
		
			for(EvidenciaDTO evidencia:evidencias){
				if(evidencia.getUltimoEslabon() != null && evidencia.getUltimoEslabon().getFuncionariRecibe() != null){
					//verificar si es un perito de la solicitud
					perito = obtenerPeritoConEvidenciaAsignada(sol,evidencia);
					if(perito!=null){
						evidencia.setUltimoEslabon(new EslabonDTO());
						evidencia.getUltimoEslabon().setFuncionariRecibe(perito);
					}
				}
			}
		
		}
		return evidencias;
	}
	
	/**
	 * Obtiene el perito al que est� asignado una evidencia en alguna de las solicitudes hijas de la solicitud
	 * enviada como par�metro, si encuentra la evidencia enviada como par�metro en la 
	 * relaci�n de solicitud evidencias de alguna solicitud hija entonces el perito de esa solicitud
	 * es devuelto como par�metro.
	 * @param sol Solicitud inicial de pericial donde se buscar� para cada solicitud hija
	 * @param evidencia Evidencia a buscar
	 * @return Perito que tiene asignada actualmente la evidencia, null en otro caso
	 */
	private FuncionarioDTO obtenerPeritoConEvidenciaAsignada(
			SolicitudPericial sol, EvidenciaDTO evidencia) {
		Evidencia evidenciaActual = null;
		for(SolicitudPericial solicitudHja:sol.getSolicitudesHijas()){
			for(RelacionDocumentoElemento elemento:solicitudHja.getRelacionElementos()){
				if(elemento.getElemento() instanceof Objeto){
					evidenciaActual = ((Objeto)elemento.getElemento()).getEvidencia();
					if(evidenciaActual != null && evidenciaActual.getEvidenciaId().equals(evidencia.getEvidenciaId())){
						return FuncionarioTransformer.transformarFuncionarioBasico(solicitudHja.getDestinatario());
					}
				}
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.evidencia.ConsultarEvidenciasXCadenaCustodiaService#consultarPeritosSinAsignacionEnCadenaDeCustodia(mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO, mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO)
	 */
	@Override
	public List<FuncionarioDTO> consultarPeritosSinAsignacionEnCadenaDeCustodia(
			CadenaDeCustodiaDTO cadena, SolicitudPericialDTO solicitud)
			throws NSJPNegocioException {
		SolicitudPericial sol = solicitudPericialDAO.read(solicitud.getDocumentoId());
		
		List<FuncionarioDTO> peritosLibres = new ArrayList<FuncionarioDTO>();
		
		
		for(SolicitudPericial solHija:sol.getSolicitudesHijas()){
			if(solHija.getDestinatario() != null){
				
				if(!existenEvidenciasRelacionadas(solHija)){
					peritosLibres.add(FuncionarioTransformer.transformarFuncionarioBasico(solHija.getDestinatario()));
				}
				
				
			}
			
		}
		
		
		return peritosLibres;
	}
	
	/**
	 * Busca si existe al menos un objeto evidencia en las relaciones de las cadenas de custodia
	 * @param solHija Solicitud donde se buscan los elementos evidencia
	 * @return True si existen evidencias ya asignadas, false en otro caso
	 */
	private boolean existenEvidenciasRelacionadas(SolicitudPericial solHija) {
		
		for(RelacionDocumentoElemento elemento:solHija.getRelacionElementos()){
			if(elemento.getElemento() instanceof Objeto){
				return true;
			}
		}
		return false;
	}
	
}
