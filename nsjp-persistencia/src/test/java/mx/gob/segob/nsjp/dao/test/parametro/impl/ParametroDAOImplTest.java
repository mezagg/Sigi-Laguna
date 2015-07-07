package mx.gob.segob.nsjp.dao.test.parametro.impl;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Parametro;

public class ParametroDAOImplTest extends BaseTestPersistencia<ParametroDAO> {
	
	public void testConsultarTodas() {
//        List<Parametro> parametros = super.daoServcice.findAll("clave",true);
        List<Parametro> parametros = super.daoServcice.findAll("descripcion",true);
        logger.debug("formas.size() :: "+parametros.size());
        for (Parametro ff : parametros) {
    		logger.info(" ID :  "+ ff.getParametroId());
    		logger.info(" Desc: "+ ff.getDescripcion());
    		logger.info(" nombre: "+ ff.getClave());
    		logger.info(" Valor: "+ ff.getValor());
        }
    }
	
	public void testConsultarEntidadDespliegue(){
		Parametro entidadDespliegue =  super.daoServcice.obtenerPorClave(Parametros.ENTIDAD_FEDERATIVA_DESPLIEGUE);
		logger.info(" ID :  "+ entidadDespliegue.getParametroId());
		logger.info(" Desc: "+ entidadDespliegue.getDescripcion());
		logger.info(" nombre: "+ entidadDespliegue.getClave());
		logger.info(" Valor: "+ entidadDespliegue.getValor());
		logger.info(" Valor as Long: "+ entidadDespliegue.getValorAsLong());
		logger.info(" Valor: "+ entidadDespliegue.getTipoValor());
	}
	
	public void testsConsultarEditarNumeroExpediente() {
		Parametro editarNumExpParam = super.daoServcice
				.obtenerPorClave(Parametros.EDITAR_NUMERO_EXPEDIENTE);
		logger.info(" ID :  " + editarNumExpParam.getParametroId());
		logger.info(" Desc: " + editarNumExpParam.getDescripcion());
		logger.info(" nombre: " + editarNumExpParam.getClave());
		logger.info(" Valor: " + editarNumExpParam.getValor());
		logger.info(" Valor as Long: " + editarNumExpParam.getValorAsLong());
		logger.info(" Valor as Boolean: "
				+ BooleanUtils
						.isTrue(editarNumExpParam.getValor().equals("1") ? true
								: false));
		
		assertTrue(BooleanUtils
				.isTrue(editarNumExpParam.getValor().equals("1") ? true
						: false));
	}

}
