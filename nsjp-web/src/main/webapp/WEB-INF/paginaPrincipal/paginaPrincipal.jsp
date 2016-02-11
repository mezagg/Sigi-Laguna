<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	String institucion = usuarioDTO.getInstitucion().getConfInstitucionId().toString();

%>
<html>
	<head>

			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<meta http-equiv="pragma" content="no-cache">
			<meta http-equiv="cache-control" content="no-cache">
			<meta http-equiv="expires" content="0">
			<title>
				<% 
					ConfInstitucionDTO confInstitucionDTO = usuarioDTO.getInstitucion();
					if(confInstitucionDTO != null && ! confInstitucionDTO.getNombreInst().isEmpty()) {
						out.println(confInstitucionDTO.getNombreInst());
					} else {
				%>
					<tiles:getAsString name="title" />
				<%
					}
				%>
			</title>
		
			<script type="text/javascript">
				var contextoPagina = "${pageContext.request.contextPath}";
				var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
				//tiempo sesion
				var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
				//tiempo alarmas
				var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
				var institucionActual = '<%= institucion %>';
				var idInstitucionPG = '<%=Instituciones.PGJ.getValorId()%>';
			</script>
			<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
			<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" /-->
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
			<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
			<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
			<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
			<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
			<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->
			
				
			<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.layout-1.3.0.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/layout_complex.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jsTree/jquery.jstree.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
			<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
			<!--script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script-->
			<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/chat.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/paneles.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/alarmas.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesMenuSuperior.js?n=1"></script>
			
			<script type="text/javascript">
				estaSesionActiva();
				
				$(document).ready(function() {
					jQuery(document).ajaxStop(jQuery.unblockUI);
					muestraGadgets();		
				});
				
				/*
					INICIA FUNCIONES DEL MENU SUPERIOR
				*/
				
				
				
				
				
				/*
					TERMINA FUNCIONES DEL MENU SUPERIOR
				*/

			</script>			
	</head>
	<body>
		<tiles:insert attribute="encabezado" />
        <tiles:insert attribute="menuIzquierdo" />
        <tiles:insert attribute="centro" />
        <tiles:insert attribute="menuDerecho" />
        <tiles:insert attribute="alertasGenericas" />
        <tiles:insert attribute="pie" />
	</body>
</html>