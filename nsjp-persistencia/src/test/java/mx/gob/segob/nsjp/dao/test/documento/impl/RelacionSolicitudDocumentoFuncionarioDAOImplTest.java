package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.RelacionSolicitudDocumentoFuncionarioDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.RelacionSolicitudDocumentoFuncionario;
import mx.gob.segob.nsjp.model.Solicitud;

/**
 * 
 * @author JoseFP
 *
 */
public class RelacionSolicitudDocumentoFuncionarioDAOImplTest extends BaseTestPersistencia<RelacionSolicitudDocumentoFuncionarioDAO> {
	
	public void testConsultarRelacionesPorDocPrincipal(){
		Solicitud solicitud = new Solicitud();
		solicitud.setDocumentoId(7561L);
		try {
			List<RelacionSolicitudDocumentoFuncionario> relaciones = daoServcice.consultarRelacionesPorSolicitud(solicitud);
			if (relaciones != null){
				logger.info("Numero de relaciones: "+relaciones.size());
				for (RelacionSolicitudDocumentoFuncionario rd : relaciones){
					logger.info("Id relacion: " +rd.getRelacionId());
					logger.info("Id Solicitud: " +rd.getSolicitud().getDocumentoId());
					logger.info("Id Documento: " +rd.getDocumento().getDocumentoId());
					logger.info("Id Funcionario: " +rd.getFuncionario().getClaveFuncionario());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testCrear(){
		RelacionSolicitudDocumentoFuncionario relacion = new RelacionSolicitudDocumentoFuncionario();
		
		Solicitud solicitud = new Solicitud();
		solicitud.setDocumentoId(7561L);
		relacion.setSolicitud(solicitud);
		
		Documento documento = new Documento(7558L);
		relacion.setDocumento(documento);
		
		Funcionario funcionario = new Funcionario(317L);
		relacion.setFuncionario(funcionario);
		
		Long id = daoServcice.create(relacion);
		logger.info("se creo la relacion con Id = "+id);
	}
	
	public void testActualizaFuncionarioDeDocumentosSegunRelacion(){
		
		Long idSolicitud = 7561L;
		Long idFuncionarioAnterior = 317L;
		Long idFuncionarioNuevo = 254L;
		try {
			daoServcice.actualizaFuncionarioDeDocumentosSegunRelacion(idSolicitud, idFuncionarioAnterior, idFuncionarioNuevo);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testActualizaFuncionarioDeRelacion(){
		
		Long idSolicitud = 7556L;
		Long idFuncionarioAnterior = 317L;
		Long idFuncionarioNuevo = 254L;
		try {
			daoServcice.actualizaFuncionarioDeRelacion(idSolicitud, idFuncionarioAnterior, idFuncionarioNuevo);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testActualizaFuncionarioDeActividad(){
		
		Long idSolicitud = 7556L;
		Long idFuncionarioAnterior = 317L;
		Long idFuncionarioNuevo = 254L;
		try {
			daoServcice.actualizaFuncionarioDeActividadSegunRelacion(idSolicitud, idFuncionarioAnterior, idFuncionarioNuevo);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}

}
