<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>	
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	Boolean esCoordinadorAmpGeneral = false;
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
	ConfInstitucionDTO institucion = usuario.getInstitucion();
	long valorInstitucion = institucion.getConfInstitucionId();
%>
	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
           // $.jgrid.useJSON = true;
        
		//cargamos el combo con los delitos del expediente
		function cargaGridConsultaDelitosTodos()
		{
			var probableResponsableProp = '<bean:message key="probableResponsableTitulo"/>';
			//cargamos el grid con los delitos del PR
	    	jQuery("#gridCatDelitosRDPTodos").jqGrid({ 
				url:'<%= request.getContextPath()%>/ConsultarRelacionDelitosPorTodos.do?idExpediente='+idExpedienteop+'', 
				datatype: "xml",
				colNames:[probableResponsableProp,'Delito','Forma de Participación','Víctima'], 
				colModel:[ 	{name:'Probable',index:'probable', width:250},
				           	{name:'Delito',index:'delito', width:250}, 
							{name:'FormaParticipacion',index:'formaParticipacion',width:250},
							{name:'Victima',index:'victima',width:250},
						],
				pager: jQuery('#pagerGridCatDelitosRDPTodos'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				autowidth: true,
				caption:"LISTA DE RELACIONES",
				sortname: 'Clave',
				viewrecords: true,
				id: 'divgridIzq',
				ondblClickRow: function(rowid) {
					var idsRelacionDelito = rowid.split("_");
					rowid = idsRelacionDelito[0];//id DelitoPersona
				    $.newWindow({id:"consultaDelitoPersona", statusBar: true, posx:20,posy:20,width:500,height:350,title:"Consulta delito persona", type:"iframe"});
             		$.updateWindowContent("consultaDelitoPersona",'<iframe src="<%= request.getContextPath() %>/abrePantallaConsultaDelitoPersona.do?idDelitoPersona='+rowid+'&idNumeroExpediente='+idNumeroExpedienteOp+'" width="500" height="350" />');
				},
				onSelectRow: function(id){
					idRelacionDelito=id;
				},
				loadComplete: function(){
					ocultarFormasParticipacionGridRDPTodos();					
				},
				sortorder: "desc"
			}).navGrid('#pagerGridCatDelitosRDPTodos',{edit:false,add:false,del:false});
	    	$("#gridCatDelitosRDPTodos").trigger("reloadGrid");

		}


		function ocultarFormasParticipacionGridRDPTodos(){
	        if (<%=valorInstitucion%> == '<%=Instituciones.PJ.getValorId()%>'){
	        	jQuery("#gridCatDelitosRDPTodos").jqGrid('hideCol',"FormaParticipacion");
			}
		}
		
        </script>
</head>
<body>
	<table border="0"  width="900px" id="tblRelacionarDelitoPorTodos">
		<tr>
			<td>
				<table id="gridCatDelitosRDPTodos" width="400px"></table>
				<div id="pagerGridCatDelitosRDPTodos"></div>
			</td>
		</tr>
	</table>
	

