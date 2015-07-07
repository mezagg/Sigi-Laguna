/**
* Nombre del Programa : AsignarTareaFuncionarioServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 12 Jul 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar la asignacion de tareas a los funcionarios
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
package mx.gob.segob.nsjp.service.tarea.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.alarmas.EstatusAlarmaAlerta;
import mx.gob.segob.nsjp.comun.enums.alarmas.TipoEventoAlarma;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.tarea.TipoEvento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.alarma.AlarmaDAO;
import mx.gob.segob.nsjp.dao.alerta.AlertaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.tarea.AgendaFuncionarioDAO;
import mx.gob.segob.nsjp.dao.tarea.EventoCitaDAO;
import mx.gob.segob.nsjp.dao.tarea.TareaDAO;
import mx.gob.segob.nsjp.dto.tarea.EventoCitaDTO;
import mx.gob.segob.nsjp.dto.tarea.TareaDTO;
import mx.gob.segob.nsjp.model.AgendaFuncionario;
import mx.gob.segob.nsjp.model.Alarma;
import mx.gob.segob.nsjp.model.Alerta;
import mx.gob.segob.nsjp.model.EventoCita;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Tarea;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.tarea.AsignarTareaFuncionarioService;
import mx.gob.segob.nsjp.service.tarea.impl.transform.EventoCitaTransformer;
import mx.gob.segob.nsjp.service.tarea.impl.transform.TareaTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar la asignacion de tareas a los funcionarios.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional (propagation = Propagation.REQUIRED)
public class AsignarTareaFuncionarioServiceImpl implements
		AsignarTareaFuncionarioService {

	/**
	 * 
	 */
	public static final Logger logger = Logger.getLogger(AsignarTareaFuncionarioServiceImpl.class);
	
	@Autowired
	private TareaDAO tareaDAO;
	@Autowired
	private AgendaFuncionarioDAO agendaFuncionarioDAO;
	@Autowired
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private EventoCitaDAO eventoCitaDAO;
	@Autowired
	private AlarmaDAO alarmaDAO;
	@Autowired
	private AlertaDAO alertaDAO;
	
	
	@Override
	public TareaDTO asignarTareaFuncionario(TareaDTO tareaDTO)
			throws NSJPNegocioException {
		
		if(logger.isDebugEnabled()){
			logger.debug("/**** SERVICIO PARA ASIGNAR TAREAS A LOS FUNCIONARIAS ****/");			
		}
		
		if (tareaDTO == null || tareaDTO.getValor() == null
				|| tareaDTO.getValor().getIdCampo() == null
				|| tareaDTO.getValor().getIdCampo() <= 0L
				|| tareaDTO.getIdFuncionario() == null
				|| tareaDTO.getEventoCita() == null
				|| tareaDTO.getEventoCita().getFechaInicioEvento() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		TareaDTO tareaDTORetorno = new TareaDTO();
		
		EventoCitaDTO eventoCitaDTO = tareaDTO.getEventoCita();
		
		if (tareaDTO.getNtiempoReal() == null) {

			if (eventoCitaDTO.getFechaFinEvento() != null) {
				Calendar calFechaIni = Calendar.getInstance();
				calFechaIni.setTime(eventoCitaDTO.getFechaInicioEvento());
				int minutosInicio = calFechaIni.get(Calendar.MINUTE);

				Calendar calFechaFin = Calendar.getInstance();
				calFechaFin.setTime(eventoCitaDTO.getFechaFinEvento());
				int minutosFin = calFechaFin.get(Calendar.MINUTE);

				int minutos = minutosFin - minutosInicio;
				tareaDTO.setNtiempoReal((short) minutos);

			} else {
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
		}		
	
		Funcionario funcionario = funcionarioDAO.read(tareaDTO.getIdFuncionario());
		
		if(funcionario == null){
			throw new NSJPNegocioException(CodigoError.FUNCIONARIO_INEXISTENTE);
		}
		
		AgendaFuncionario agendaFuncionario = agendaFuncionarioDAO.obtenerAgendaFuncionario(tareaDTO.getIdFuncionario());
		
		/*Revisar existencia de Agenda*/
		if(agendaFuncionario==null){
			agendaFuncionario=new AgendaFuncionario();
			
			agendaFuncionario.setFuncionario(new Funcionario(tareaDTO.getIdFuncionario()));
			agendaFuncionario.setDinicioAgenda(new Date());
			Long idAgenda = agendaFuncionarioDAO.create(agendaFuncionario);
			agendaFuncionario.setAgendaFuncionarioId(idAgenda);
		}
		
		
		//Validar si el horario esta disponible
		List<Tarea> tareas = tareaDAO.obtenerDisponibilidadHorarioActividad(tareaDTO.getEventoCita().getFechaInicioEvento(), tareaDTO.getNtiempoReal(), funcionario.getClaveFuncionario());
		List<EventoCita> vacaciones = eventoCitaDAO.consultarEventosPorTipoYFecha(TipoEvento.VACACIONES, eventoCitaDTO.getFechaInicioEvento());
		List<EventoCita> incapacidad = eventoCitaDAO.consultarEventosPorTipoYFecha(TipoEvento.INCAPACIDAD, eventoCitaDTO.getFechaInicioEvento());
						
		if (tareas.isEmpty() && vacaciones.isEmpty() && incapacidad.isEmpty()) {
			logger.debug("/**** LA TAREA PUEDE SER GENERADA ****/");
			Calendar cal = Calendar.getInstance();
		    cal.setTime(eventoCitaDTO.getFechaInicioEvento());
			
			Tarea tarea = TareaTransformer.transfromarTarea(tareaDTO);
			
			EventoCita eventoCita = EventoCitaTransformer.transformarEventoCita(eventoCitaDTO);					
			eventoCita.setAgendaFuncionario(agendaFuncionario);
			eventoCita.setFechaInicioEvento(cal.getTime());
			cal.add(Calendar.MINUTE, tareaDTO.getNtiempoReal());
			eventoCita.setFechaFinEvento(cal.getTime());
			
			Long eventoCitaId = eventoCitaDAO.create(eventoCita);
			
			tarea.setEventoCita(eventoCita);
			Long idTarea = tareaDAO.create(tarea);
			
			Double cargaDeTrabajo = funcionario.getCargaTrabajo();
			
			if(cargaDeTrabajo==null){
				cargaDeTrabajo=0D;				
			}
			Double cargaNueva = tareaDTO.getNtiempoReal().doubleValue()/60;
			logger.info("/****** NUEVA CARGA " + cargaNueva);
			Double actCarGenTrab = cargaDeTrabajo+cargaNueva;
			funcionario.setCargaTrabajo(actCarGenTrab);
			funcionarioDAO.update(funcionario);
			
			if (tareaDTO.getEventoCita()!=null && tareaDTO.getEventoCita().getEsAlertaAlarma()!=null
					&& tareaDTO.getEventoCita().getEsAlertaAlarma().equals(true)) {
				
				Alarma alarma = new Alarma();
				Alerta alerta = new Alerta();
				
				alarma.setFechaAlarma(eventoCita.getFechaInicioEvento());
				alarma.setEstatusAlarmaAlerta(new Valor(EstatusAlarmaAlerta.NO_EJECUTADA.getValorId()));
				alarma.setMotivo(eventoCita.getDescripcionEvento());
				alarma.setDatosAsociados(eventoCitaId.toString());
				alarma.setFuncionario(funcionario);
				alarma.setTipoEventoAlarma(new Valor(TipoEventoAlarma.VENCIMIENTO.getValorId()));				
				alarmaDAO.create(alarma);
				logger.debug("/**** ALARMA GENERADA ****/");
				
				Calendar fechaAlerta = Calendar.getInstance();
				fechaAlerta.setTime(eventoCita.getFechaInicioEvento());
				fechaAlerta.add(Calendar.MINUTE, -30);
				alerta.setFechaAlerta(fechaAlerta.getTime());
				alerta.setEstatusAlarmaAlerta(new Valor(EstatusAlarmaAlerta.NO_EJECUTADA.getValorId()));
				alerta.setTipoAlerta(new Valor(TipoEventoAlarma.SEGUIMIENTO_A_SOLICITUD.getValorId()));
				alerta.setNombre(tareaDTO.getEventoCita().getNombreEvento());
				alerta.setEsAplaza(true); //Definido por default 
				alerta.setTiempo((short) 30);
				alerta.setUnidadDeTiempo(new Valor(2534L));
				alerta.setUsuario(new Usuario(tareaDTO.getUsuario().getIdUsuario()));
				alerta.setAlarma(alarma);
				alertaDAO.create(alerta);
				logger.debug("/**** ALERTA GENERADA ****/");
			}
			
			tareaDTORetorno.setTareaId(idTarea);
		}else{
			// TODO DAJV ¿QUE HACER CUANDO YA HAY UNA TAREA REGISTRADA?
			tareaDTORetorno = null;
		}
		return tareaDTORetorno;
	}

}
