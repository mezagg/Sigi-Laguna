<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<!--css de aplicacion-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />

<!--css para jQuery-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />

<!--css para jqGrid-->
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

<!--css para ventanas windows engine-->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />



<!--js para jQuery-->
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>

<!--js para jqGrid-->	
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>

<!--para controlar las ventanas windows engine-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>

<!--js de la aplicacion-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>

<!-- Script de mandamientos judicial -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>

</head>
<script type="text/javascript">

	var contextoPagina 			=	"${pageContext.request.contextPath}";
	var probRespId				=	<%=request.getParameter("probRespId")%>;
	var idExpediente			=	<%=request.getParameter("idExpediente")%>;
	var mandamientoJudicialId	=	<%=request.getParameter("mandamientoJudicialId")%>;
	var nombreProbResp			=	'<%=request.getParameter("nombreProbResp")%>';
	var audienciaId				=	<%=request.getParameter("audienciaId")%>;
	var esAudiencia				=	<%=request.getParameter("esAudiencia")%>;
	
	var cargaProbableRespBandera = true;

	$(document).ready(function(){
		//configurarPantallaConsulta();
	});

	function configurarPantallaConsulta(){
		$("#adminGenManJudExpTable").hide();
		$("#consultarManJudExpTable").show();
		$("#proRespManJudTxtExp").val(nombreProbResp);
		
		cargarGridMandJudAsocProbRespGenManJud(probRespId);
	}

</script>

<body>
	<jsp:include page="/WEB-INF/paginas/consultarMandamientosJudicialesExp.jsp" flush="true"></jsp:include>
	
	<!--Form para la consulta del archivo digital-->
	<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	</form>
</body>

</html>