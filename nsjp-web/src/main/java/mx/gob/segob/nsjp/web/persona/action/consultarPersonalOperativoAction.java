/**
* Nombre del Programa : consultarPersonalOperativoAction.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20/07/2011
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
package mx.gob.segob.nsjp.web.persona.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudPericialDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.institucion.InstitucionDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Componente de Action permite consultar personal operativo por varios filtro:
 * id, nombre, amaterno, apaterno, rfc, curp, cedula profesional, email, Institucion, area,
 * especialidad y puesto
 * @version 1.0
 * @author ricardog
 *
 */
public class consultarPersonalOperativoAction extends GenericAction {
	@Autowired
	SolicitudPericialDelegate solicitudPericialDelegate;
	
	@Autowired
	FuncionarioDelegate funcionarioDelegate;
	
	
		
	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(consultarPersonalOperativoAction.class);
	
	/**
	 * M�todo para realizar al b�squeda de personal operativo por filtro:	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarPersonalOperativo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		//criterios
		FuncionarioDTO filtro = new FuncionarioDTO();		
		List<FuncionarioDTO> resultados = new ArrayList<FuncionarioDTO>();
		InstitucionDTO institucionDTO = new InstitucionDTO(null,null);
		AreaDTO loAreaDTO = new AreaDTO();
		loAreaDTO.setInstitucion(institucionDTO);
		DepartamentoDTO loDepartamentoDTO= new DepartamentoDTO();
		loDepartamentoDTO.setArea(loAreaDTO);		
		filtro.setDepartamento(loDepartamentoDTO);
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();


			if( StringUtils.isNotBlank(request.getParameter("idPO"))){
				Long idPersonalOperativo = NumberUtils.toLong(request.getParameter("idPO"),0);
				if(idPersonalOperativo > 0)
					filtro.setClaveFuncionario(idPersonalOperativo);
				log.info("idPO:" + request.getParameter("idPO"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("nombre"))){
				filtro.setNombreFuncionario(request.getParameter("nombre"));
				log.info("Nombre:" + request.getParameter("nombre"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("apellidoPaterno"))){
				filtro.setApellidoPaternoFuncionario(request.getParameter("apellidoPaterno"));
				log.info("apellidoPaterno:" + request.getParameter("apellidoPaterno"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("apellidoMaterno"))){
				filtro.setApellidoMaternoFuncionario(request.getParameter("apellidoMaterno"));
				log.info("apellidoMaterno:" + request.getParameter("apellidoMaterno"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("especialidad"))){
				Long idEspecialidad = NumberUtils.toLong(request.getParameter("especialidad"),0);
				if(idEspecialidad > 0)
					filtro.setEspecialidad(new ValorDTO(idEspecialidad));
				log.info("especialidad:" + request.getParameter("especialidad"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("rfc"))){
				filtro.setRfc(request.getParameter("rfc"));
				log.info("rfc:" + request.getParameter("rfc"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("curp"))){
				filtro.setCurp(request.getParameter("curp"));
				log.info("CURP recibido:" + request.getParameter("curp"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("puesto"))){
				Long idPuesto = NumberUtils.toLong(request.getParameter("puesto"),0);
				if(idPuesto > 0)
					filtro.setPuesto(new ValorDTO(idPuesto));
				log.info("puesto:" + request.getParameter("puesto"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("correo"))){
				filtro.setEmail(request.getParameter("correo"));
				log.info("correo:" + request.getParameter("correo"));				
			}
			if( StringUtils.isNotBlank(request.getParameter("cedula"))){
				filtro.setCedula(request.getParameter("cedula"));
				log.info("cedula:" + request.getParameter("cedula"));				
			}
			// Checar si se utilizar�
/**			if( StringUtils.isNotBlank(request.getParameter("organizacion"))){
				Long idOrganizacion = NumberUtils.toLong(request.getParameter("organizacion"),0);
			}**/

			if( StringUtils.isNotBlank(request.getParameter("area"))){
				Long idArea = NumberUtils.toLong(request.getParameter("area"),0);
				if(idArea>0){
					filtro.getDepartamento().getArea().setAreaId(idArea);
					log.info("area:" + request.getParameter("area"));					
				}
			}
			if( StringUtils.isNotBlank(request.getParameter("institucion"))){
				Long idInstitucion = NumberUtils.toLong(request.getParameter("institucion"),0);
				if(idInstitucion>0){
					filtro.getDepartamento().getArea().getInstitucion().setInstitucionId(idInstitucion);		
					log.info("institucion:" + request.getParameter("institucion"));					
				}				
			}
			if( StringUtils.isNotBlank(request.getParameter("departamento"))){
				Long idDepartamento = NumberUtils.toLong(request.getParameter("departamento"),0);
				if(idDepartamento>0){
					filtro.getDepartamento().setDepartamentoId(idDepartamento);
					log.info("idDepartamento:" + request.getParameter("idDepartamento"));					
				}				
			}

			try {
				resultados = solicitudPericialDelegate.consultarFuncionarioPorFiltro(filtro,null);
				writer.print("<rows>");
				writer.print("<records>" + resultados.size() + "</records>");
				
				for (FuncionarioDTO funcionario : resultados) {						   
					writer.print("<row id='"+ funcionario.getClaveFuncionario() + "'>");
					writer.print("<nombre>"+ funcionario.getNombreCompleto() +  "</nombre>");
					writer.print("<especialidad>"+ (funcionario.getEspecialidad()!=null?funcionario.getEspecialidad().getValor():"-") +  "</especialidad>");												
					writer.print("<curp>"+ (funcionario.getCurp()!=null?funcionario.getCurp():"-") +  "</curp>");
					writer.print("<rfc>"+ (funcionario.getRfc()!=null?funcionario.getRfc():"-") +  "</rfc>");
					writer.print("<email>"+ (funcionario.getEmail()!=null?funcionario.getEmail():"-") +  "</email>");
					//writer.print("<organizacion>"+ "-" +  "</organizacion>");//Nombre de la organizaci�n a la que pertenece.
					writer.print("<cedula>"+ (funcionario.getCedula()!=null?funcionario.getCedula():"-") +  "</cedula>");
					writer.print("<puesto>"+ (funcionario.getPuesto()!=null?funcionario.getPuesto().getValor():"-") +  "</puesto>");
					writer.print("<departamento>"+ (funcionario.getDepartamento()!=null && funcionario.getDepartamento().getNombreDepto()!= null?funcionario.getDepartamento().getNombreDepto():"-") +  "</departamento>");					
					writer.print("<institucion>"+ (funcionario.getDepartamento()!=null && funcionario.getDepartamento().getArea()!= null&&
							funcionario.getDepartamento().getArea().getInstitucion()!= null?
									funcionario.getDepartamento().getArea().getInstitucion().getNombre():"-") +  "</institucion>");//Institucion
					writer.print("</row>");
				}
			} catch (NSJPNegocioException e) {
				e.printStackTrace();
			}
		writer.print("</rows>");
		writer.flush();
		writer.close();
		return null;
	}
	
	
	 /*
	 * M�todo utilizado para realizar la consulta de personal operativo
	 * por N�mero de Empleado
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarPersonalOperativoIPH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		
		try {
			log.info("ejecutando Action ConsultarPersonalOperativoIPH");
			//criterios
			FuncionarioDTO funcionario = new FuncionarioDTO();
			List<FuncionarioDTO> resultados = new ArrayList<FuncionarioDTO>();
			funcionario.setNumeroEmpleado(request.getParameter("numeroEmpleado"));		
			resultados = funcionarioDelegate.consultarFuncionarioXNumeroEmpleado(funcionario);		
			funcionario = new FuncionarioDTO();
			if(!resultados.isEmpty())
			{
				funcionario = resultados.get(0);
			}
			converter.alias("funcionarioDTO", FuncionarioDTO.class);
			String xml = converter.toXML(funcionario);
			log.info("respuesta consulta funcionario IPH ------- "+xml);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
		return null;
	}		
	
	
	 /*
	 * M�todo utilizado para realizar la consulta de especialidades
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoEspecialidad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar especialidad");
			String tipoEspecialidad = request.getParameter("tipoEspecialidadId");
			log.info("ejecutando Action consultar especialidad tipoEspecialidad - "+tipoEspecialidad);
			
			List<CatalogoDTO> listaCatalogoEspecialidades = catDelegate
					.recuperarCatalogo(Catalogos.ESPECIALIDAD_FUNCIONARIO);							
			converter.alias("listaCatalogoEspecialidades", java.util.List.class);
			converter.alias("especialidad", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogoEspecialidades);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public ActionForward consultarFuncionarioPorDepartamento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			Long idArea = NumberUtils.toLong(request.getParameter("idArea"),0);
			Long idCatUIE = NumberUtils.toLong(request.getParameter("idCatUIE"),0);
			Long idCatDiscriminante = NumberUtils.toLong(request.getParameter("idCatDiscriminante"),0);
			Long idDistrito = NumberUtils.toLong(request.getParameter("idDistrito"),0);

			FuncionarioDTO filtro = new FuncionarioDTO();
			
			Areas area = Areas.getByValor(idArea);
			
			List<Long> idsJerarquiaOrganizacional = new ArrayList<Long>();
			
			if(area != null && idArea >0){
				filtro.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(idArea));
				idsJerarquiaOrganizacional.add(idArea);
				switch (area){
					case UNIDAD_INVESTIGACION:
						idsJerarquiaOrganizacional.add(Areas.COORDINACION_UNIDAD_INVESTIGACION.parseLong());					
					break;
					case FISCAL_FACILITADOR:
						idsJerarquiaOrganizacional.add(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
					break;
				}
			}
			
			CatDiscriminanteDTO loCatDiscriminanteDTO = new CatDiscriminanteDTO(idCatDiscriminante);
			CatDistritoDTO loCatDistritoDTO= new CatDistritoDTO(idDistrito);
			loCatDiscriminanteDTO.setDistrito(loCatDistritoDTO);
			
			// Se agrega el filtro de agencia y distrito 
			filtro.setDiscriminante(loCatDiscriminanteDTO);
			
			CatUIEspecializadaDTO loCatUIEspecializadaDTO = new CatUIEspecializadaDTO(idCatUIE); 
			// Se agrega el filtro de UIE
			filtro.setUnidadIEspecializada(loCatUIEspecializadaDTO);
			
			List<FuncionarioDTO> listaDeFuncionarios = funcionarioDelegate.consultarFuncionarioXFiltroYAreas(filtro,idsJerarquiaOrganizacional);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
		
			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }
			
            for (FuncionarioDTO funcionarioDTO : listaDeFuncionarios) {					
				writer.print("<row id='" + funcionarioDTO.getClaveFuncionario()+ "'>");
				writer.print("<cell>" + funcionarioDTO.getNombreFuncionario()+ "</cell>");						
				writer.print("<cell>" + funcionarioDTO.getApellidoPaternoFuncionario()+ "</cell>");						
				writer.print("<cell>" + funcionarioDTO.getApellidoMaternoFuncionario()+ "</cell>");						
				writer.print("</row>");
            }			
            
			writer.print("</rows>");
			writer.flush();
			writer.close();
       
        } catch (NSJPNegocioException ex) {
            java.util.logging.Logger.getLogger(consultarPersonalOperativoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
	}

}
