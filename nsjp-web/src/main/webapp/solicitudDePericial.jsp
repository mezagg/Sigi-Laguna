<%@page import="mx.gob.segob.nsjp.comun.enums.funcionario.Puestos"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Solicitar Servicio Pericial</title>
	
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	<script type="text/javascript">
	var solicitudId = '<%= request.getParameter("rowid")%>';
	
	$(document).ready(function() {
		$("#tabsPrincipal").tabs();
		/*$("#solServPericialFechaVencimiento").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});*/
		$("#guardarNarraTiva").css("display", "none");
		$("#btnEnviarSolicitud").click(enviarSolicitudPeritoCoordPerDef);
		cargaDatosDelUsuario();
		cargaFechaCaptura();
		cargaDatosSolicitud(solicitudId);
		consultaFuncionario();
		cargaGridEvidencias(solicitudId);
	});

	/*Funcion que dispara el Action para consultar los datos de usuario*/
    function cargaDatosDelUsuario(){
    	
    	$('#solServPericialAreaAdmin').val('');
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarDatosUsuario.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){

    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					$('#solDePericialAreaAdmin').val( $(xml).find('usuarioDTO').find('areaActual').find('nombre').first().text());
					$('#solDePericialNombre').val(
							$(xml).find('usuarioDTO').find('funcionario').find('nombreFuncionario').first().text() + " " +
							$(xml).find('usuarioDTO').find('funcionario').find('apellidoPaternoFuncionario').first().text() + " "+
							$(xml).find('usuarioDTO').find('funcionario').find('apellidoMaternoFuncionario').first().text());
					$('#solDePericialPuesto').val($(xml).find('usuarioDTO').find('funcionario').find('puesto').find('valor').first().text() );
    			}
				else{
					//Mostrar mensaje de error
				}
    		}
    	});
    }

	//Envia la solicitud bas&aacute;ndose en la solicitudId y el id del funcionario destinatario
	function enviarSolicitudPeritoCoordPerDef(){
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/enviarSolicitudPeritoCoordPerDef.do?solicitudPericialId='+solicitudId+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				alert("Asignaci&oacute;n enviada con &eacute;xito");
				parent.cerrarVentanaPericial();	
				parent.cargaGridSolicitudesPericialesNoAtendidas();			
			}
		});
	}
	
	function cargaFechaCaptura(){
    	$.ajax({
    		type: 'POST',
    	    url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
    	    data: '',
    	    dataType: 'xml',
    	    async: false,
    	    success: function(xml){
    			$('#solDePericialFecha').val($(xml).find('fechaActual').text());
    		}
		});
    }

	function cargaDatosSolicitud(solicitudId){
		$.ajax({
	    	  type: 'POST',
	    	  url:  '<%= request.getContextPath()%>/consultaDetalleSolicitudPericial.do',
	    	  data: 'solicitudPericialId='+solicitudId,
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
	    		  pintaDatosSolicitud(xml);
			  }
	    });
    }

    function pintaDatosSolicitud(xml){

        if($(xml).find('fechaLimiteStr') != null){
 		   $('#solServPericialFechaVencimiento').val($(xml).find('fechaLimiteStr').text());
        }
        if($(xml).find('expedienteDTO') != null){
 		   $('#solServPericialNumExpediente').val($(xml).find('expedienteDTO').find('numeroExpediente').text());
        }
        if($(xml).find('observaciones') != null){
 		   $('#areaDescripcion').val($(xml).find('observaciones').text());
     	}
    }
	
    function cargaGridEvidencias(solicitudId){
    	jQuery("#gridDetalleCadenaCustodia").jqGrid({ 
    		url:'<%= request.getContextPath()%>/consultarEvidenciasSolicitud.do?solicitudId='+solicitudId+'',
    		data:'',
    		datatype: "xml", 
    		colNames:['N&uacute;mero de Evidencia','Cadena de Custodia','Objeto','C&oacute;digo de Barras'], 
    		colModel:[ 	{name:'NumeroEvidencia',index:'numeroEvidencia', width:150},
    		           	{name:'CadenaCustodia',index:'cadenaCustodia', width:150},
    		           	{name:'Objeto',index:'objeto', width:150},
    		           	{name:'CodigoBarras',index:'codigoBarras', width:150}
    				],
    		pager: jQuery('#pagerCadenaCustodia'),
    		rowNum:10,
    		rowList:[10,20,30],
    		autowidth: true,
    		sortname: 'CadenaCustodia',
    		viewrecords: true,
    		sortorder: "desc",
    		multiselect: false
    		}).navGrid('#pagerCadenaCustodia',{edit:false,add:false,del:false});
    }

	/**
	* Carga el funcionario a mostrar en la tab de Avisar a Funcionario seg&uacute;n puesto del destinatario
	*/
	function consultaFuncionario(){		
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarFuncionarioANotificar.do',
    		data: 'puestoId='+<%= Puestos.COORDINADOR_PERICIAL_DE_DEFENSORIA.getValorId() %>,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){

    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					$('#solServPericialNombreFuncionario').val($(xml).find('nombreFuncionario').first().text() + " " +
							$(xml).find('apellidoPaternoFuncionario').first().text() + " "+
							$(xml).find('apellidoMaternoFuncionario').first().text());
    			}
				else{
					//Mostrar mensaje de error
				}
    		}
    	});
	}

	</script>
</head>
<body>
<table width="100%">
	<tr>
		<td>
			<div id="tabsPrincipal">
				<ul>
					<li><a href="#tabsconsultaprincipal-1">Solicitud</a></li>
					<li><a href="#tabsconsultaprincipal-2">Evidencia</a></li>
					<li><a href="#tabsconsultaprincipal-3">Enviar Solicitud</a></li>
				</ul>
				<div id="tabsconsultaprincipal-1">
					<fieldset style="width: 700px;">
					<legend>Solicitante</legend>
					<table width="100%" border="0" height="90%">
						<tr>
							<td>
								Nombre Servidor P&uacute;blico:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solDePericialNombre"  disabled="disabled" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
							</td>
						</tr>
						<tr>
							<td>
								Cargo:
							</td>
							<td>
								<input type="text" size="50" maxlength="50"	id="solDePericialPuesto"  disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td>
								&Aacute;rea Administrativa:
							</td>
							<td>
								<input type="text" size="50" maxlength="50" id="solDePericialAreaAdmin"  disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td>
								Fecha Elaboraci&oacute;n:
							</td>
							<td>
								<input type="text" size="50" maxlength="13"	id="solDePericialFecha" disabled="disabled"/>
							</td>
						</tr>
					</table>
					</fieldset>
					<fieldset style="width: 700px;">
					<legend>Datos de la Solicitud</legend>
					<table width="100%" border="0" height="90%">
						<tr>
							<td>
								N&uacute;mero de Expediente:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solServPericialNumExpediente"  disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td>
								Fecha L&iacute;mite:
							</td>
							<td>
								<input type="text" id="solServPericialFechaVencimiento" width="50px" disabled="disabled"/>
							</td>
						</tr>
					</table>
					</fieldset>
				</div>
				<div id="tabsconsultaprincipal-2">
					<fieldset style="width: 700px;">
					<legend>Evidencias Solicitadas</legend>
					<table>
						<tr>
							<td width="100%">
								<table id="gridDetalleCadenaCustodia"></table>
								<div id="pagerCadenaCustodia"></div>
							</td>
						</tr>
					</table>
					</fieldset>
					<fieldset style="width: 700px;">
					<legend>Recomendaciones</legend>
					<table width="100%" border="0" height="90%">
						<tr>
				          <td>
				            <textarea id="areaDescripcion" cols="45" rows="5" style="width: 500px; height:200px;" disabled="disabled"></textarea>
			              </td>
				        </tr>
					</table>
					</fieldset>
				</div>
				<div id="tabsconsultaprincipal-3" style="height: 400">
				  	<table width="100%">
				  		<tr>
				  			<td>
				  				Nombre Funcionario:
				                <input type="text" class="" size="50" maxlength="50" id="solServPericialNombreFuncionario" disabled="disabled" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
				  			</td>
				  		</tr>
				  		<tr>
				        	<td>&nbsp;</td>
				        </tr>
				  		<tr>
							<td align="left">
								<input type="button" id="btnEnviarSolicitud" value="Enviar Solicitud" class="ui-button ui-corner-all ui-widget"/>
							</td>
						</tr>
				  	</table>
			  </div>
			</div>
		</td>
	</tr>
</table>
</body>
</html>