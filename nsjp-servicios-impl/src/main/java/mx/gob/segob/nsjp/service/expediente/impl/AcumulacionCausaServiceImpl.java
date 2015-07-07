
package mx.gob.segob.nsjp.service.expediente.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.domicilio.DomicilioDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.expediente.AcumulacionNumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.expediente.AcumulacionNumeroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.AnimalDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.EquipoComputoDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.AcumulacionNumeroExpediente;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Domicilio;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.domicilio.impl.transform.DomicilioTransformer;
import mx.gob.segob.nsjp.service.expediente.AcumulacionCausaService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ClonarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.objeto.IngresarAnimalService;
import mx.gob.segob.nsjp.service.objeto.IngresarArmaService;
import mx.gob.segob.nsjp.service.objeto.IngresarDocumentoOficialService;
import mx.gob.segob.nsjp.service.objeto.IngresarEmbarcacionService;
import mx.gob.segob.nsjp.service.objeto.IngresarEquipoDeComputoService;
import mx.gob.segob.nsjp.service.objeto.IngresarExplosivoService;
import mx.gob.segob.nsjp.service.objeto.IngresarJoyaService;
import mx.gob.segob.nsjp.service.objeto.IngresarNumerarioService;
import mx.gob.segob.nsjp.service.objeto.IngresarObjetoService;
import mx.gob.segob.nsjp.service.objeto.IngresarObraArteService;
import mx.gob.segob.nsjp.service.objeto.IngresarSustanciaService;
import mx.gob.segob.nsjp.service.objeto.IngresarTelefonoService;
import mx.gob.segob.nsjp.service.objeto.IngresarVegetalService;
import mx.gob.segob.nsjp.service.objeto.IngresarVehiculoService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AcumulacionCausaServiceImpl implements AcumulacionCausaService{

//    @Autowired
//    private BuscarExpedienteService buscarExpService;
//    @Autowired
//    private AsignarNumeroExpedienteService aisgnarExpService;
//
//    @Autowired
//    private IngresarIndividuoService ingresarInvoService;

    @Autowired
    private ExpedienteDAO expDao;
    @Autowired
    private NumeroExpedienteDAO noExpDao;
    @Autowired
    private AcumulacionNumeroExpedienteDAO acumulacionNumeroExpedienteDAO;
    @Autowired
	private FuncionarioDAO funDao;

    public final static Logger logger = Logger
            .getLogger(AcumulacionCausaServiceImpl.class);
    
    @Override
    public List<AcumulacionNumeroExpedienteDTO> consultarAcumulacionCausa(String numeroExpediente)
            throws NSJPNegocioException {
        logger.info("Inicia - consulta Acumulacion Causas");
        NumeroExpediente numeroExpedienteId =noExpDao.obtenerNumeroExpediente(numeroExpediente,null);
        List<AcumulacionNumeroExpedienteDTO> listAcumulacionNumeroExpedienteDTO= new ArrayList<AcumulacionNumeroExpedienteDTO>();
        List<AcumulacionNumeroExpediente> listaCausasAcumuladas=acumulacionNumeroExpedienteDAO.consultaAcumulacion(numeroExpedienteId.getNumeroExpedienteId());
        for (AcumulacionNumeroExpediente acumulacionNumeroExpediente : listaCausasAcumuladas) {
        	AcumulacionNumeroExpedienteDTO acumulacionNumeroExpedienteDTO=new AcumulacionNumeroExpedienteDTO();
        	acumulacionNumeroExpedienteDTO.setAcumulacionId(acumulacionNumeroExpediente.getAcumulacionId());
        	acumulacionNumeroExpedienteDTO.setClaveFuncionario(acumulacionNumeroExpediente.getClaveFuncionario());
        	acumulacionNumeroExpedienteDTO.setNombreFuncionario(obtenerNombreFuncionario(acumulacionNumeroExpediente.getClaveFuncionario()));
        	acumulacionNumeroExpedienteDTO.setFecha(acumulacionNumeroExpediente.getFecha());
        	acumulacionNumeroExpedienteDTO.setExpedienteDTOPrincipal(ExpedienteTransformer.transformarExpediente(acumulacionNumeroExpediente.getNumeroExpedienteId()));
        	acumulacionNumeroExpedienteDTO.setNumeroExpedienteAcumuladoId(ExpedienteTransformer.transformarExpediente(acumulacionNumeroExpediente.getNumeroExpedienteAcumuladoId()));
        	listAcumulacionNumeroExpedienteDTO.add(acumulacionNumeroExpedienteDTO);
		}
        logger.info("Fin - consulta Acumulacion Causas");
        return listAcumulacionNumeroExpedienteDTO;
    }
    
    private String obtenerNombreFuncionario(Long claveFuncionario) {
    	Funcionario funcionarioBD=funDao.read(claveFuncionario);
    	return funcionarioBD.getNombreCompleto();
	}
    
    
	@Override
    public String crearAcumulacion(AcumulacionNumeroExpedienteDTO acumulacion) throws NSJPNegocioException {
    	Long idAcumulacion=-1L;
    	String respuesta="";
    	try{
    		
    	
    	AcumulacionNumeroExpediente acumulacionNumeroExpediente=new AcumulacionNumeroExpediente();
    	acumulacionNumeroExpediente.setClaveFuncionario(acumulacion.getClaveFuncionario());
    	acumulacionNumeroExpediente.setFecha(new Date());
    	
    	NumeroExpediente numeroExpedienteId =noExpDao.obtenerNumeroExpediente(acumulacion.getExpedienteDTOPrincipal().getNumeroExpediente(),null);
    	acumulacionNumeroExpediente.setNumeroExpedienteId(numeroExpedienteId);
    	
    	NumeroExpediente numeroExpedienteAcumuladoId =noExpDao.read(acumulacion.getNumeroExpedienteAcumuladoId().getNumeroExpedienteId());
    	acumulacionNumeroExpediente.setNumeroExpedienteAcumuladoId(numeroExpedienteAcumuladoId);
    	List<AcumulacionNumeroExpediente> listaCausasAcumuladas=acumulacionNumeroExpedienteDAO.consultaAcumulacion(numeroExpedienteAcumuladoId.getNumeroExpedienteId());
	    	if(listaCausasAcumuladas!=null && !listaCausasAcumuladas.isEmpty()){
	    		respuesta="Esta Causa ya se encuentra acumulada";
	    	}else{
	    		idAcumulacion=acumulacionNumeroExpedienteDAO.create(acumulacionNumeroExpediente);
	    		respuesta="Causa Acumulada :"+idAcumulacion;
	    	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return respuesta;
    }

    
}
