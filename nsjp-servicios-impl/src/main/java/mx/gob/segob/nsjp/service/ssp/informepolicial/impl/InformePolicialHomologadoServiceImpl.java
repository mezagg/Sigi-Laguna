package mx.gob.segob.nsjp.service.ssp.informepolicial.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoIphDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.hecho.HechoDAO;
import mx.gob.segob.nsjp.dao.hecho.TiempoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.ssp.informepolicial.InformePolicialHomologadoDAO;
import mx.gob.segob.nsjp.dao.ssp.informepolicial.InvolucradoIPHDAO;
import mx.gob.segob.nsjp.dao.ssp.informepolicial.OperativoDAO;
import mx.gob.segob.nsjp.dao.ssp.informepolicial.TurnoLaboralIphIdDAO;
import mx.gob.segob.nsjp.dao.ssp.informepolicial.impl.OperativoDAOImpl;
import mx.gob.segob.nsjp.dao.ssp.informepolicial.impl.TurnoLaboralIphIdDAOImpl;
import mx.gob.segob.nsjp.dao.ssp.turnolaboral.impl.TurnoLaboralDAOImpl;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.DelitoIphDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.FaltaAdministrativaIphDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InvolucradoIPHDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.OperativoDTO;
import mx.gob.segob.nsjp.dto.ssp.turnolaboral.TurnoLaboralDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoIph;
import mx.gob.segob.nsjp.model.DelitoIphId;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.FaltaAdministrativaIph;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.ssp.*;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioPorFiltroService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.ssp.informepolicial.InformePolicialHomologadoService;
import mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform.CatDelitoTransformer;
import mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform.DelitoIphTransformer;
import mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform.FaltaAdministrativaIphTransformer;
import mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform.InformePolicialHomologadoTransformer;
import mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform.InvolucradoIPHTransformer;
import mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform.OperativoTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InformePolicialHomologadoServiceImpl implements
		InformePolicialHomologadoService {
	private static final Logger logger  = Logger.getLogger(ConsultarInformePolicialHomologadoServiceImpl.class);
	@Autowired
	private InformePolicialHomologadoDAO informePolicialHomologadoDAO;
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
//	@Autowired
//	private IngresarDomicilioService ingresarDomicilio;
	@Autowired
	HechoDAO hechoDAO;
	@Autowired
	TiempoDAO tiempoDAO;
	@Autowired
	ConsultarFuncionarioPorFiltroService funcionario;
	@Autowired
	OperativoDAO operativoDAO;
	@Autowired
	private InvolucradoIPHDAO involucradoIPHDAO;
	@Autowired
	FuncionarioDAO funcionarioDAO;
	@Autowired
	DelitoDAO delitoDAO;
	@Autowired
	BuscarExpedienteService buscarExpedienteService;
	@Autowired
	DelitoIphDAO delitoIphDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private TurnoLaboralIphIdDAO turnoLaboralDAO;
	
	@Transactional
	@Override
	public Long ingresarDatosGenerales(InformePolicialHomologadoDTO iph,
			OperativoDTO operativo) throws NSJPNegocioException {
		
		if(iph==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		if(iph.getFolioIPH()==null||iph.getFuncionarioDestinatario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		//Borra los delitos prexistentes
		delitoIphDAO.eliminaDelitosDelIph(iph.getFolioIPH());

		//Borra Faltas administrativas prexistentes
        informePolicialHomologadoDAO.eliminaFaltasAdminIph(iph.getFolioIPH());

		if(operativo!=null && iph.getBorrarOperativo()){
			Operativo operativoAnterior=new Operativo();
			operativoAnterior.setOperativoId(operativo.getOperativoId());
			operativoDAO.delete(operativoAnterior);
		}
		//borra Turno anterior
		if (iph.getTurnoIdAnt()!=null){
			informePolicialHomologadoDAO.eliminarTurnosByInformePolicialHomologadoId(iph.getTurnoIdAnt(),iph.getFolioIPH());
		}
		Funcionario destinatario = new Funcionario();
		destinatario.setNumeroEmpleado(iph.getFuncionarioDestinatario().getNumeroEmpleado());
		List<Funcionario> funcionarios = funcionarioDAO.consultarFuncionarioXFiltro(destinatario, false);	
		if(funcionarios.size()>0){
			destinatario = funcionarios.get(0);
		}else{
			destinatario = null;
		}
		
		InformePolicialHomologado informeActualizado = informePolicialHomologadoDAO.consultaInformePorFolio(iph.getFolioIPH());
		if(operativo!=null && !iph.getBorrarOperativo()){
			operativo.setInformePolicialHomologado(new InformePolicialHomologadoDTO(informeActualizado.getInformePolicialHomologadoId()));
			iph.setOperativo(operativo);
		}
		InformePolicialHomologadoTransformer.tranformIPHUpdate(informeActualizado,iph);
		Long informeCreado = 0L;
		Long idNuevoOperativo=0L;
		Operativo nuevoOperativo=new Operativo();
		
		
		informeActualizado.setFuncionarioDestinatario(destinatario);
		
		/* Ingresar Operativo si existe */
		if (informeActualizado.getInformePolicialHomologadoId() != null)
		{	
//			DelitoIphId delIphId=new DelitoIphId();
//			delIphId.setInformePolicialHomologadoId(informeActualizado.getInformePolicialHomologadoId());
//			for (DelitoIph delitos : informeActualizado.getDelitoIphs()) {
//				delIphId.setCatDelitoId(delitos.getId().getCatDelitoId());
//			}
//			delitoIphDAO.eliminaDelitosDelIph(delIphId);
//			delitoIphDAO.flush();
//			informePolicialHomologadoDAO.saveOrUpdate(informeActualizado);
			informePolicialHomologadoDAO.merge(informeActualizado);
		}
		else
			informeCreado = informePolicialHomologadoDAO.create(informeActualizado);

		if (operativo != null && !iph.getBorrarOperativo()) {
			//Permite asignar el idOperativo directamente de la consulta de IPH en lugar de pasarlo desde vista.
			if(informeActualizado.getOperativo() != null){
				operativo.setOperativoId(informeActualizado.getOperativo().getOperativoId());
			}
			if (operativo.getOperativoId()!=null) {
				Operativo operativoUpdate = operativoDAO.read(operativo.getOperativoId());
				operativoUpdate.setNombre(operativo.getNombre());
				operativoUpdate.setNombreComte(operativo.getNombreComte());
				operativoUpdate.setNombreComteAgrupto(operativo.getNombreComteAgrupto());
				operativoUpdate.setInformePolicialHomologado(informeActualizado);
				operativoDAO.update(operativoUpdate);
			} else {
				operativo.setInformePolicialHomologado(InformePolicialHomologadoTransformer.tranformIPHSimple(informeActualizado));
				nuevoOperativo=OperativoTransformer.transformOperativo(operativo);
				operativoDAO.create(nuevoOperativo);
			}

			idNuevoOperativo=informePolicialHomologadoDAO.consultaIdOperativoByFolioIph(iph.getFolioIPH());
		}
		return idNuevoOperativo;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public InformePolicialHomologadoDTO ObtenerFolioIPH(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		InformePolicialHomologadoDTO iphDTO = new InformePolicialHomologadoDTO();
		InformePolicialHomologado iph = new InformePolicialHomologado();
		long Folio = 0;
		Date d = new Date();
		long anio = d.getYear() + 1900;
		List<Object[]> lst = informePolicialHomologadoDAO.ObtenerFolioIPH();
		int regs = Integer.parseInt(lst.get(0)[0].toString());
		if (regs > 0) {
			Folio = Long.parseLong(lst.get(0)[1].toString()) + 1;
		} else {
			Folio = (anio * 1000000) + 1;
		}
		
		expedienteDTO.setFechaApertura(new Date());
		expedienteDTO.setArea(new AreaDTO(Areas.POLICIA_SSP));
		ExpedienteDTO respuesta =null;
		if(expedienteDTO.getNumeroExpediente()!=null){
			respuesta=buscarExpedienteService.obtenerExpedientePorNumeroExpediente(expedienteDTO.getNumeroExpediente());
		}else{
			respuesta = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);
		}
		
		iphDTO.setExpediente(respuesta);
		iphDTO.setFolioIPH(Folio);
		iphDTO.setAnio(anio);
		Expediente expediente = new Expediente();
		expediente.setFechaCreacion(respuesta.getFechaApertura());
		expediente.setNumeroExpediente(respuesta.getNumeroExpediente());
		expediente.setExpedienteId(respuesta.getExpedienteId());
		iph.setExpediente(expediente);
		iph.setFolioIPH(iphDTO.getFolioIPH());
		iph.setAnio(iphDTO.getAnio());
		iph.setFechaCaptura(new Date());
		Funcionario funcionario = new Funcionario();
		funcionario.setClaveFuncionario(expedienteDTO.getUsuario()
				.getFuncionario().getClaveFuncionario());
		iph.setFuncionarioElabora(funcionario);
		informePolicialHomologadoDAO.create(iph);
		return iphDTO;
	}

	@Override
	public InformePolicialHomologadoDTO consultarInformePorFolio(Long folio)
			throws NSJPNegocioException {
		InformePolicialHomologado informe = informePolicialHomologadoDAO
				.consultaInformePorFolio(folio);
		if (informe != null)
			return InformePolicialHomologadoTransformer.tranformIPH(informe);
		else
			return new InformePolicialHomologadoDTO();
	}

	@Override
	public List<InformePolicialHomologadoDTO> consultarInformes(Boolean conDetenido)
			throws NSJPNegocioException {
		
		//Consulta todos los informes existentes
		List<InformePolicialHomologado> informes = informePolicialHomologadoDAO.consultarInformes();
		List<InformePolicialHomologadoDTO> informesDTO = new ArrayList<InformePolicialHomologadoDTO>();
		
		if(informes!=null){
				if(conDetenido==null){
					for (InformePolicialHomologado inf : informes) {
						InformePolicialHomologadoDTO infDTO = InformePolicialHomologadoTransformer.tranformIPH(inf);
						
						/*Involucrados*/
						List<InvolucradoIph> involucrados = involucradoIPHDAO.consultarInvolucradosDeIPH(inf.getInformePolicialHomologadoId());
						if (involucrados != null) {
							List<InvolucradoIPHDTO> involucradoIphs=new ArrayList<InvolucradoIPHDTO>();
							for (InvolucradoIph invIph : involucrados) {
								involucradoIphs.add(InvolucradoIPHTransformer.transformarInvolucradoIPH(invIph));
							}
							infDTO.setInvolucradoIphs(involucradoIphs);
						}
						
						/*Delitos*/
						List<DelitoIph> delitos = informePolicialHomologadoDAO.consultarDelitosDeIPH(infDTO.getInformePolicialHomologadoId());
						if (delitos.size() > 0) {
							List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
							for (DelitoIph rowDelito : delitos) {
								delitoDTO.add(DelitoIphTransformer
										.transformarDelitoIph(rowDelito));
							}
							infDTO.setDelitoIph(delitoDTO);
						}else if(infDTO.getExpediente()!=null && infDTO.getExpediente().getExpedienteId()!=null){
							List<Delito> delitosExpediente = delitoDAO.obtenerDelitoPorExpediente(infDTO.getExpediente().getExpedienteId());
							List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
							for (Delito rowDelito : delitosExpediente) {
								DelitoIphDTO delitoIphDTO=new DelitoIphDTO();
								delitoIphDTO.setCatDelito(CatDelitoTransformer.transformarCatDelito(rowDelito.getCatDelito()));
								delitoDTO.add(delitoIphDTO);
							}
							infDTO.setDelitoIph(delitoDTO);
						}
						
						/*Faltas*/
						List<FaltaAdministrativaIph> faltasAdmin = informePolicialHomologadoDAO.consultarFaltaAdministrativaDeIPH(infDTO.getInformePolicialHomologadoId());
						if (faltasAdmin.size() > 0) {
							List<FaltaAdministrativaIphDTO> faltaDTO = new ArrayList<FaltaAdministrativaIphDTO>();
							for (FaltaAdministrativaIph rowFalta : faltasAdmin) {
								faltaDTO.add(FaltaAdministrativaIphTransformer
										.transformarFaltaAdministrativaIph(rowFalta));
							}
							infDTO.setFaltaIph(faltaDTO);
						}

						informesDTO.add(infDTO);
					}
				}else if(conDetenido){
					return informesConDetenido(informes);
				}else{
					return infromesSinDetenido(informes);
				}
		}
		return informesDTO;
	}

	@Override
	public List<InformePolicialHomologadoDTO> consultarInformes(Boolean conDetenido,UsuarioDTO user)
			throws NSJPNegocioException {
		
		//Consulta todos los informes existentes
		List<InformePolicialHomologado> informes = informePolicialHomologadoDAO.consultarInformesPorUsuario(user);
		List<InformePolicialHomologadoDTO> informesDTO = new ArrayList<InformePolicialHomologadoDTO>();
		
		if(informes!=null){
				if(conDetenido==null){
					for (InformePolicialHomologado inf : informes) {
						InformePolicialHomologadoDTO infDTO = InformePolicialHomologadoTransformer.tranformIPH(inf);
						
						/*Involucrados*/
						List<InvolucradoIph> involucrados = involucradoIPHDAO.consultarInvolucradosDeIPH(inf.getInformePolicialHomologadoId());
						if (involucrados != null) {
							List<InvolucradoIPHDTO> involucradoIphs=new ArrayList<InvolucradoIPHDTO>();
							for (InvolucradoIph invIph : involucrados) {
								involucradoIphs.add(InvolucradoIPHTransformer.transformarInvolucradoIPH(invIph));
							}
							infDTO.setInvolucradoIphs(involucradoIphs);
						}
						
						/*Delitos*/
						List<DelitoIph> delitos = informePolicialHomologadoDAO.consultarDelitosDeIPH(infDTO.getInformePolicialHomologadoId());
						if (delitos.size() > 0) {
							List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
							for (DelitoIph rowDelito : delitos) {
								delitoDTO.add(DelitoIphTransformer
										.transformarDelitoIph(rowDelito));
							}
							infDTO.setDelitoIph(delitoDTO);
						}else if(infDTO.getExpediente()!=null && infDTO.getExpediente().getExpedienteId()!=null){
							List<Delito> delitosExpediente = delitoDAO.obtenerDelitoPorExpediente(infDTO.getExpediente().getExpedienteId());
							List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
							for (Delito rowDelito : delitosExpediente) {
								DelitoIphDTO delitoIphDTO=new DelitoIphDTO();
								delitoIphDTO.setCatDelito(CatDelitoTransformer.transformarCatDelito(rowDelito.getCatDelito()));
								delitoDTO.add(delitoIphDTO);
							}
							infDTO.setDelitoIph(delitoDTO);
						}
						
						/*Faltas*/
						List<FaltaAdministrativaIph> faltasAdmin = informePolicialHomologadoDAO.consultarFaltaAdministrativaDeIPH(infDTO.getInformePolicialHomologadoId());
						if (faltasAdmin.size() > 0) {
							List<FaltaAdministrativaIphDTO> faltaDTO = new ArrayList<FaltaAdministrativaIphDTO>();
							for (FaltaAdministrativaIph rowFalta : faltasAdmin) {
								faltaDTO.add(FaltaAdministrativaIphTransformer
										.transformarFaltaAdministrativaIph(rowFalta));
							}
							infDTO.setFaltaIph(faltaDTO);
						}

						informesDTO.add(infDTO);
					}
				}else if(conDetenido){
					return informesConDetenido(informes);
				}else{
					return infromesSinDetenido(informes);
				}
		}
		return informesDTO;
	}
	private List<InformePolicialHomologadoDTO> infromesSinDetenido(
			List<InformePolicialHomologado> informes) {
		List<InformePolicialHomologadoDTO> informesDTO=new ArrayList<InformePolicialHomologadoDTO>();
		for (InformePolicialHomologado inf : informes) {
			InformePolicialHomologadoDTO infDTO = InformePolicialHomologadoTransformer.tranformIPH(inf);
			
			/*Involucrados*/
			List<Involucrado> involucrados = involucradoDAO.consultarInvolucradosByExpediente(inf.getExpediente().getExpedienteId());
			if (involucrados != null) {
				List<InvolucradoIPHDTO> involucradoIphs=new ArrayList<InvolucradoIPHDTO>();
				boolean tieneDetenido=false;
				for (Involucrado inv : involucrados) {
//					if(invIph.getInvolucrado().getEsDetenido()){
//						tieneDetenido = true;
//						break;
//					}

					involucradoIphs.add(new InvolucradoIPHDTO(InvolucradoTransformer.transformarInvolucrado(inv)));
				}
				//Se agrega validacion de datos nulos y/o vacios 
				if(tieneDetenido){
					continue;
				}else if (involucradoIphs!=null && involucradoIphs.size()!=0){
					infDTO.setInvolucradoIphs(involucradoIphs);
				}
			}
			
			/*Delitos*/
			List<DelitoIph> delitos = informePolicialHomologadoDAO.consultarDelitosDeIPH(infDTO.getInformePolicialHomologadoId());
			if (delitos.size() > 0) {
				List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
				for (DelitoIph rowDelito : delitos) {
					delitoDTO.add(DelitoIphTransformer
							.transformarDelitoIph(rowDelito));
				}
				infDTO.setDelitoIph(delitoDTO);
			}else if(infDTO.getExpediente()!=null && infDTO.getExpediente().getExpedienteId()!=null){
				List<Delito> delitosExpediente = delitoDAO.obtenerDelitoPorExpediente(infDTO.getExpediente().getExpedienteId());
				List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
				for (Delito rowDelito : delitosExpediente) {
					DelitoIphDTO delitoIphDTO=new DelitoIphDTO();
					delitoIphDTO.setCatDelito(CatDelitoTransformer.transformarCatDelito(rowDelito.getCatDelito()));
					delitoDTO.add(delitoIphDTO);
				}
				infDTO.setDelitoIph(delitoDTO);
			}
			
			/*Faltas*/
			List<FaltaAdministrativaIph> faltasAdmin = informePolicialHomologadoDAO.consultarFaltaAdministrativaDeIPH(infDTO.getInformePolicialHomologadoId());
			if (faltasAdmin.size() > 0) {
				List<FaltaAdministrativaIphDTO> faltaDTO = new ArrayList<FaltaAdministrativaIphDTO>();
				for (FaltaAdministrativaIph rowFalta : faltasAdmin) {
					faltaDTO.add(FaltaAdministrativaIphTransformer
							.transformarFaltaAdministrativaIph(rowFalta));
				}
				infDTO.setFaltaIph(faltaDTO);
			}

			informesDTO.add(infDTO);
		}
		return informesDTO;
	}

	private List<InformePolicialHomologadoDTO> informesConDetenido(
			List<InformePolicialHomologado> informes) {
		List<InformePolicialHomologadoDTO> informesDTO=new ArrayList<InformePolicialHomologadoDTO>();
		for (InformePolicialHomologado inf : informes) {
			InformePolicialHomologadoDTO infDTO = InformePolicialHomologadoTransformer.tranformIPH(inf);
			
			/*Involucrados*/
			List<Involucrado> involucrados = involucradoDAO.consultarInvolucradosByExpediente(inf.getExpediente().getExpedienteId());
			if (involucrados != null) {
				List<InvolucradoIPHDTO> involucradoIphs=new ArrayList<InvolucradoIPHDTO>();
				boolean tieneDetenido=false;
				for (Involucrado invIph : involucrados) {
					if(!tieneDetenido && invIph.getEsDetenido()){
						tieneDetenido = true;
					}
					
					involucradoIphs.add(new InvolucradoIPHDTO());
				}
				if(!tieneDetenido)
					continue;
				else
					infDTO.setInvolucradoIphs(involucradoIphs);
			}
			
			/*Delitos*/
			List<DelitoIph> delitos = informePolicialHomologadoDAO.consultarDelitosDeIPH(infDTO.getInformePolicialHomologadoId());
			if (delitos.size() > 0) {
				List<DelitoIphDTO> delitoDTO = new ArrayList<DelitoIphDTO>();
				for (DelitoIph rowDelito : delitos) {
					delitoDTO.add(DelitoIphTransformer
							.transformarDelitoIph(rowDelito));
				}
				infDTO.setDelitoIph(delitoDTO);
			}
			
			/*Faltas*/
			List<FaltaAdministrativaIph> faltasAdmin = informePolicialHomologadoDAO.consultarFaltaAdministrativaDeIPH(infDTO.getInformePolicialHomologadoId());
			if (faltasAdmin.size() > 0) {
				List<FaltaAdministrativaIphDTO> faltaDTO = new ArrayList<FaltaAdministrativaIphDTO>();
				for (FaltaAdministrativaIph rowFalta : faltasAdmin) {
					faltaDTO.add(FaltaAdministrativaIphTransformer
							.transformarFaltaAdministrativaIph(rowFalta));
				}
				infDTO.setFaltaIph(faltaDTO);
			}

			informesDTO.add(infDTO);
		}
		return informesDTO;
	}

}
