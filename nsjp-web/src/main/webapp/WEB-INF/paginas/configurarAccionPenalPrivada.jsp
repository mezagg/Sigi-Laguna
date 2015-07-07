<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>


<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	
	Long rolId = 0L;
	
	if (usuario != null && usuario.getRolACtivo() != null
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null) {
		//Obtiene el rol activo del usuario loggeado
		rolId = usuario.getRolACtivo().getRol().getRolId();
	}
%>   	   

<script type="text/javascript">

	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var contextoPagina = "${pageContext.request.contextPath}";

	$(document).ready(function(){
		configurarAccionPenalPrivada();
	});


	/*
	*Consulta la entidad federativa en la que se encuentra desplegada la
	*la aplicacion
	*/
	function consultarParametroEntidadFederativa(){

		var parametros = "";
		var valorParametro="";
		
		parametros += 'idParametro=' + '<%=Parametros.ENTIDAD_FEDERATIVA_DESPLIEGUE.ordinal()%>';
		
		ejecutaAction("/consultarParametro", function(respuesta){
			if(parseInt($(respuesta).find('code').text())==0){
				if($(respuesta).find('body').text() != null 
						&& $(respuesta).find('body').text() != "null"){
					valorParametro = $(respuesta).find('body').find('respuesta').text();
				}
			}
		} , parametros);

		//Devuelve el valor obtenido
		return valorParametro;
	}

	/*
	*Muestra los elementos necesarios, para la accion penal privada
	*dependiendo del rol activo del usuario Loggeado
	*/
	function agregarAccionPenalPrivada(idRolActivo){

		if(idRolActivo == '<%=Roles.ATENCIONPUBLICO.getValorId()%>'){
			//Muestra boton de la barra de herramientas
			$('#btnNuevaSolAccPenalPrivadaPJATP').attr('style','display:block');
			//Muestra la ceja de acc. penal privada del acordeon izquierdo
			$('#encabezadoAccPenalPriv').attr('style','display:block');
			$('#menuAcordeonAccPenalPriv').attr('style','display:block');
		}
		if(idRolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
			//Muestra la ceja de acc. penal privada del acordeon izquierdo	
			$('#encabezadoAccPenalPriv').attr('style','display:block');
			$('#menuAcordeonAccPenalPriv').attr('style','display:block');
		}
	}

	/*
	*Consulta la entidad de despliegue y si es diferente de Zacatecas
	*llama a la funcion para mostrar los elementos de Acc. Penal. Priv.
	*dependiendo del rol de usuario loggeado, y al final construye el acordeon 
	*de las dos jsp's.
	*atencion al publico o encargado de causa respectivamente
	*/
	function configurarAccionPenalPrivada(){

		//Obteniene entidad Fed. del despliegue
		var entidadFederativa = consultarParametroEntidadFederativa();

		if(entidadFederativa != '<%=EntidadFederativa.ZACATECAS.getValorId()%>'){
			//Muestra los elementos
			agregarAccionPenalPrivada(<%=rolId%>);
		}
		//Construye el acordeon izquierdo		 
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
	}
</script>