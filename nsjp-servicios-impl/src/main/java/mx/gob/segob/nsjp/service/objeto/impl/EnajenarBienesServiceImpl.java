package mx.gob.segob.nsjp.service.objeto.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;

import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.objeto.ObjetoDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Objeto;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.service.objeto.EnajenarBienesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.segob.nsjp.service.objeto.impl.transform.ObjetoTransformer;
import org.apache.log4j.Logger;

@Service
@Transactional
public class EnajenarBienesServiceImpl implements
		      EnajenarBienesService{

    private static final Logger log = Logger
			.getLogger(EnajenarBienesService.class);

	@Autowired
	private ObjetoDAO objetoDAO;
	@Autowired
	private ParametroDAO parametroDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;

    @Override
    public List<ObjetoDTO> consultarBienesPorEnajenar(Date fecha,Integer diasParaEnajenar) {
        Parametro parametro = parametroDAO.obtenerPorClave(Parametros.DIAS_PARA_ENAJENAR);
        List<Objeto> lista = null;
	lista = objetoDAO.consultarBienesPorEnajenar(fecha,diasParaEnajenar);
        List<ObjetoDTO> objetosDTO=new ArrayList<ObjetoDTO>();
		for (Objeto obj : lista) {
			ObjetoDTO objDTO=ObjetoTransformer.transformarObjeto(obj);

			if(objDTO!=null && objDTO.getExpedienteDTO() != null && objDTO.getExpedienteDTO().getExpedienteId() != null ) {

				ExpedienteDTO expedienteDTO = objDTO.getExpedienteDTO();
				NumeroExpediente numeroExpediente = numeroExpedienteDAO.obtenerNumeroExpedienteXExpediente(expedienteDTO.getExpedienteId());
				if( numeroExpediente != null) {
					objDTO.getExpedienteDTO().setNumeroExpediente(numeroExpediente.getNumeroExpediente());
					objetosDTO.add(objDTO);
				}
			}
		}
	return objetosDTO;	
    }

    @Override
    public void enajenarBienes(String idsBienes) {
        StringTokenizer st=new StringTokenizer(idsBienes, ",");
        List<Long> ids = new ArrayList<Long>();
        while(st.hasMoreTokens())
            ids.add(new Long(st.nextToken()));
        objetoDAO.enajenarBienes(ids);
    }

}
