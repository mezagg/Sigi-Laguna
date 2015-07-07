/**
 * 
 */
package mx.gob.segob.nsjp.service.ws.impl;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.service.sentencia.SentenciaService;
import mx.gob.segob.nsjp.service.ws.SentenciaExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

/**
 * @author AntonioBV
 *
 */
@WebService
public class SentenciaExporterImpl implements SentenciaExporter {

	//private final static Logger LOG = Logger.getLogger(SentenciaExporterImpl.class);	
	
	
	private SentenciaService sentenciaService;
	
	@WebMethod (exclude = true)
	private void init(){
		try{
		sentenciaService = (SentenciaService)ApplicationContextAwareWSNSJPImpl
						.springApplicationContext.getBean("sentenciaService");
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#consultarNUS(mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO)
	 */
	@Override
	@WebMethod (exclude = true)
	public List<SentenciaDTO> consultarNUS(NombreDemograficoDTO nombreDemograficoDTO)
			throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.SentenciaService#consultarSentenciaCompleta(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO)
	 */
	@Override
	@WebMethod (exclude = true)
	public SentenciaDTO consultarSentenciaCompleta( SentenciaDTO sentenciaDTO) throws NSJPNegocioException   {
		// TODO Auto-generated method stub
		return null;		
	}


	@Override
	@WebMethod
	public void crearSentencia(SentenciaWSDTO sentenciaWSDTO)
			throws NSJPNegocioException {
		init();
		sentenciaService.crearSentencia(sentenciaWSDTO);
		
	}


	@Override
	@WebMethod (exclude = true)
	public boolean enviarSentencia(SentenciaDTO sentenciaDTO, Instituciones institucion) throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	@WebMethod (exclude = true)
	public SentenciaDTO realizarAltasCambiosASentencia(SentenciaDTO sentenciaDTO)
			throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@WebMethod (exclude = true)
	public AsignacionCentroDetencionDTO consultarAsignacionCentroDetencionActual(
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@WebMethod (exclude = true)
	public List<InvolucradoDTO> consultarInvolucradosXSituacionSinSentencia(
			ValorDTO situacionJuridica) throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@WebMethod (exclude = true)
	public void registrarSentenciaEjecutoriada(SentenciaDTO sentenciaDTO,
			InvolucradoDTO involucradoDTO) throws NSJPNegocioException {
		// TODO Auto-generated method stub
		
	}


	@Override
	@WebMethod (exclude = true)
	public void actualizarSentenciaExterna(SentenciaDTO sentenciaDTO,
			Instituciones institucion) throws NSJPNegocioException {
		// TODO Auto-generated method stub
		
	}


	@Override
	@WebMethod (exclude = true)
	public Map<Long, SentenciaDTO> consultarSentenciasPorIdsNumExp(
			List<Long> idsNumExp) throws NSJPNegocioException {
		// TODO Auto-generated method stub
		return null;
	}

}
