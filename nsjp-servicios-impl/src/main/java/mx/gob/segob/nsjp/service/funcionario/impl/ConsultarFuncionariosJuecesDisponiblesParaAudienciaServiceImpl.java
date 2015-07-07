/**
* Nombre del Programa : ConsultarJuezServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 22/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Implementaci�n del servicio de negocio para la consulta de jueces
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
package mx.gob.segob.nsjp.service.funcionario.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.expediente.CalcularParidadNumeroExpedienteService;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionariosJuecesDisponiblesParaAudienciaService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del servicio de negocio para la consulta de jueces
 * @version 1.0
 * @author Emigdio Hern�ndez
 *
 */
@Service
@Transactional
public class ConsultarFuncionariosJuecesDisponiblesParaAudienciaServiceImpl implements ConsultarFuncionariosJuecesDisponiblesParaAudienciaService {

	@Autowired
	FuncionarioDAO funcionarioDAO;
	@Autowired
	CalcularParidadNumeroExpedienteService calcularParidadNumeroExpedienteService;
	@Autowired
	AudienciaDAO audienciaDAO;
	
	private static final int NUMERO_PAGINA_INICIAL = 1; 
	
	@Override
	public List<FuncionarioDTO> consultarJuecesDisponiblesParaFechaYHoraAudiencia(
			Date fecha, Integer duracionEstimada, UsuarioDTO usuarioDto) throws NSJPNegocioException{
		
		if(usuarioDto == null ||usuarioDto.getFuncionario() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(usuarioDto.getFuncionario());
		
		List<FuncionarioDTO> resultado = new ArrayList<FuncionarioDTO>();
		
		List<Funcionario> jueces = funcionarioDAO
				.consultarFuncionariosPorEspecialidadYPuestoDisponiblesParaFechaYHoraAudiencia(
						fecha, duracionEstimada, null, funcionario);
		
		if (usuarioDto.getRolACtivo() != null
				&& usuarioDto.getRolACtivo().getRol() != null
				&& usuarioDto.getRolACtivo().getRol().getRolId() != null
				&& usuarioDto.getRolACtivo().getRol().getRolId()
						.equals(Roles.JUEZPJ.getValorId())) {

			/*
			 * Funcion para forzar que el juez loggeado, se encuentre en la
			 * lista de jueces disponibles para la audiencia
			 */
			Boolean existeFuncionario = false;

			for (Funcionario juez : jueces) {
				if (usuarioDto.getFuncionario().getClaveFuncionario() != null
						&& usuarioDto.getFuncionario().getClaveFuncionario()
								.equals(juez.getClaveFuncionario())) {
					existeFuncionario = true;
					break;
				}
			}
			if (!existeFuncionario) {
				if (jueces != null && jueces.size() > 0) {
					jueces.add(funcionario);
				}
			}
		}
		
		List<Funcionario> juecesPaginado = null;
		juecesPaginado = jueces;
		juecesPaginado = paginacionManualJueces(juecesPaginado);

		for (Funcionario juez : juecesPaginado) {
			resultado.add(FuncionarioTransformer.transformarFuncionario(juez));
		}
		return resultado;
	}
	
	
	/**
	 * Metodo para realizar la paginacion, manual para el gird de jueces disponibles para la audiencia
	 * @param listaRespuesta
	 * @return
	 */
	private List<Funcionario> paginacionManualJueces(
			List<Funcionario> listaRespuesta) {
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		if((pag.getPage()*pag.getRows())-pag.getRows()>listaRespuesta.size()){
			pag.setPage(NUMERO_PAGINA_INICIAL);
		}
		int inicio=0;
		if(pag.getPage()<= NUMERO_PAGINA_INICIAL){
			inicio=0;
		}else{
			inicio=((pag.getPage()- NUMERO_PAGINA_INICIAL)*pag.getRows());
		}
		int indiceFinal=inicio+pag.getRows();
		List<Funcionario> listaAlterna=listaRespuesta;
		listaRespuesta=new ArrayList<Funcionario>();
		if(indiceFinal>=listaAlterna.size()){
			for (int i = inicio; i < listaAlterna.size(); i++) {
				listaRespuesta.add(listaAlterna.get(i));
			}
		}else{
			for (int i = inicio; i < indiceFinal; i++) {
				listaRespuesta.add(listaAlterna.get(i));
			}
		}
		pag.setTotalRegistros((long)listaAlterna.size());
		pag.setPaginacionHecha(true);
		PaginacionThreadHolder.set(pag);
		return listaRespuesta;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionariosJuecesDisponiblesParaAudienciaService#consultarJuezAutomaticoADesignar(java.util.Date, java.lang.Integer)
	 */
	@Override
	public List<FuncionarioDTO> consultarJuezAutomaticoADesignar(Date fecha,
			Integer duracionEstimada,AudienciaDTO audiencia,boolean juezSustituto,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		int juecesNecesarios = ConstantesGenerales.JUECES_AUTOMATICOS_PREDETERMINADO;
		Boolean paridadJuezRequerida = null;
		Audiencia audienciaDB = audienciaDAO.read(audiencia.getId());
		if(TipoAudiencia.JUICIO_ORAL.getValorId().equals(audiencia.getTipoAudiencia().getIdCampo())){
			juecesNecesarios = juezSustituto?ConstantesGenerales.JUECES_AUTOMATICOS_JUICIO_ORAL+
					ConstantesGenerales.JUECES_AUTOMATICOS_SUSTITUTOS:ConstantesGenerales.JUECES_AUTOMATICOS_JUICIO_ORAL;
		}
		//Consultar paridad requerida de juez
		if(audienciaDB != null && audienciaDB.getNumeroExpediente() != null){
			//Consultar la paridad de la audiencia
			paridadJuezRequerida = calcularParidadNumeroExpedienteService.consultarParidadDeNumeroExpediente(audienciaDB.getNumeroExpediente().getNumeroExpedienteId());
			if(TipoAudiencia.JUICIO_ORAL.getValorId().equals(audiencia.getTipoAudiencia().getIdCampo())){
				
				paridadJuezRequerida = paridadJuezRequerida!=null?!paridadJuezRequerida.booleanValue():null;
			}else{
				paridadJuezRequerida = null;
			}
			
		}
		
		
		Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(funcionarioDTO);
		
		List<FuncionarioDTO> juecesElegidos = new ArrayList<FuncionarioDTO>();
		List<Funcionario> jueces = funcionarioDAO.
		consultarFuncionariosPorEspecialidadYPuestoDisponiblesParaFechaYHoraAudiencia
		(fecha, duracionEstimada, paridadJuezRequerida, funcionario);
		
		for(Funcionario unJuez:jueces){
			juecesElegidos.add(FuncionarioTransformer.transformarFuncionario(unJuez));
		}
		
		while(juecesElegidos.size()>juecesNecesarios){
			juecesElegidos.remove(juecesElegidos.size()-1);
		}
		
		//FIXME Hacer que baje la clave del funcionario hasta el DAO, en el caso de que sea Juez
		
		return juecesElegidos;
	}

}
