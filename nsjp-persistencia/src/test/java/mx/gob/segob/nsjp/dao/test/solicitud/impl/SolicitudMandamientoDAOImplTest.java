/**
* Nombre del Programa : SolicitudMandamientoDAOImplTest.java
* Autor                            : vaguirre
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20 Jul 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.test.solicitud.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.SolicitudMandamiento;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author vaguirre
 *
 */
public class SolicitudMandamientoDAOImplTest extends BaseTestPersistencia<SolicitudMandamientoDAO> {

    public void testCreate(){
        SolicitudMandamiento pojo = new SolicitudMandamiento();
        pojo.setFechaCreacion(new Date());
        pojo.setTipoDocumento(new Valor(1L));
//      pojo.setFechaLimite(new Date());
        pojo.setForma(new Forma(Formas.SOLICITUD.getValorId()));
        pojo.setNombreDocumento("Solicitud Mandamiento No." + Calendar.getInstance().get(Calendar.MINUTE));
        pojo.setEstatus(new Valor(EstatusSolicitud.EN_PROCESO.getValorId()));
        pojo.setTipoSolicitud(new Valor(TiposSolicitudes.MEDIDAS_ALTERNATIVAS.getValorId()));
        pojo.setNumeroExpediente(new NumeroExpediente(7L));
        pojo.setFuncionarioSolicitante(new Funcionario(3L));
        pojo.setDestinatario(new Funcionario(1L));
//      pojo.setConfInstitucion(new ConfInstitucion(Long.valueOf(Instituciones.PJ.ordinal())));
        daoServcice.create(pojo);
    }
    
    public void testCrearSolicitudMandamientoConInfoExistente(){
    	SolicitudMandamiento sm = new SolicitudMandamiento();
    	sm.setDocumentoId(5528L);
//    	Mandamiento mandamiento = new Mandamiento();
//    	mandamiento.setDocumentoId(5552L);
//    	sm.setMandamiento(mandamiento);
//    	Valor tipoMandamiento = new Valor(2850L);
//    	sm.setTipoMandamiento(tipoMandamiento);
    	try {
			daoServcice.crearSolicitudMandamientoConInfoExistente(sm);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
    }
    
    public void testConsultarSolicitudesMandamientoPorDestinatario(){
    	Funcionario funcionario = new Funcionario();
    	List<Valor> estatusSolicitud = new ArrayList<Valor>();
    	List<Valor> tiposSolicitud = new ArrayList<Valor>();
    	List<Valor> tipoMandamiento = new ArrayList<Valor>();
    	
    	funcionario.setClaveFuncionario(300L);
    	estatusSolicitud.add(new Valor(1775L));
    	tiposSolicitud.add(new Valor(7583L));
    	tipoMandamiento.add(new Valor(2853L));
    	
    	try {
			List<SolicitudMandamiento> solicitudesMandamiento = 
				daoServcice.consultarSolicitudesMandamientoPorDestinatario(
						funcionario, estatusSolicitud, tipoMandamiento, tiposSolicitud);
			if (solicitudesMandamiento != null
					&& !solicitudesMandamiento.isEmpty()){
				for (SolicitudMandamiento sm : solicitudesMandamiento){
					logger.info("Id de la solicitud: "+sm.getDocumentoId());
					logger.info("Id del mandamiento: "+sm.getMandamiento().getDocumentoId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
    }
    
	public void testConsultarSolicitudesMandatoJudicialPorFiltro() {

		SolicitudMandamiento solicitudMandamiento = new SolicitudMandamiento();
		Mandamiento mandamiento = new Mandamiento();
		mandamiento.setDocumentoId(7321L);
		solicitudMandamiento.setMandamiento(mandamiento);

		List<SolicitudMandamiento> listaSolMand = daoServcice
				.consultarSolicitudesMandatoJudicialPorFiltro(solicitudMandamiento);

		assertNotNull(listaSolMand);
		if (listaSolMand != null && !listaSolMand.isEmpty()) {
			for (SolicitudMandamiento sm : listaSolMand) {

				logger.info("Id del solicitud: " + sm.getDocumentoId());
				logger.info("Id del mandamiento: "
						+ sm.getMandamiento().getDocumentoId());
				logger.info("destinatario: "
						+ sm.getDestinatario().getNombreCompleto());
			}
		}
	}
}
