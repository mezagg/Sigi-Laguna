<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.TipoTurno"%>
<script type="text/javascript"> 
 
$(document).ready(function() {

	//cambia los elementos de la pantalla dependiendo si viene de defensoria 
	tipoAtencion(defensoria);
});


function turno(){
	if(parseInt(defensoria)!= 1){
		if($('#checpenal').is(':checked')){
			$("#tiem").val('');
			$("#textemer").css("display", "");
			$("#checemer").css("display", "");
			$("#checemer").attr('disabled', "");
		} else{
			$('#checemer').attr('checked', false); 
			$("#tiem").val('');
			$("#textemer").css("display", "none");
			$("#checemer").css("display", "none");
			$("#checemer").attr('disabled', "disabled");
		}
	}
}

//genera turno 
function cargaTurno(){
	
	var params;
	if(defensoria==1)
	{
		if($(':radio[name=kevinTipoTurno]:checked').val()==1)
		{
			//asesoria legal 3
			params = 'tipoTurno='+  <%=TipoTurno.ASESORIA_LEGAL.ordinal()%>;
		}
		else
		{
			//solicitud ciudadana 4
			params = 'tipoTurno='+  <%=TipoTurno.SOLICITUD_CIUDADANA.ordinal()%>;
		}
	}
	else
	{
		params ='tipoTurno=' + $(':radio[name=kevinTipoTurno]:checked').val();
	}
	params+='&esUrgente=' + $('#checemer').is(':checked');
	
	//var tipoTurno  = $(':radio[name=RadioGroup1]:checked').val();	
	//var esUrgente = true;
	//var rAdmin = $(':radio[name=checadmin]:checked').val();	//nombre
	
    $.ajax({
    	  	url: '<%= request.getContextPath()%>/GenerarTurno.do',
    	  	dataType: 'xml',
    	  	Type: 'POST',
    	  	data:params,
    	  	async: false,
    	  	success: function(xml){ 
        	      		  
    		  	$('#xml').val(xml);
    		  	$('#tiem').val($(xml).find('turnoDTO').find('numeroTurno').text());
    		  	$('#tiem2').val($(xml).find('turnoDTO').find('numeroTurno').text());
    			try {
    				if (typeof refrescarArbol == 'function' ){
    					refrescarArbol();
        			}
    			} catch(error) {
    				console.log(error);
				}
		  	}
    	});

    document.frmDoc.submit();
      
	}
	
function muestradatospersona(){
	$("#divDatos").css("display", "block");
	$("#divTurno").css("display", "none");
	
	
}

function tipoAtencion(defensoria){

	if(parseInt(defensoria)== 1){
		$("#atencionPenal").empty();
		$("#atencionPenal").append('Solicitud ciudadana de defensor');
		$("#atencionNoPenal").empty();
		//Si la variable no esta definida o si tiene el valor de 0 se esconde el componente
		if( habilitarAsesoriaLegal == null || parseInt(habilitarAsesoriaLegal) == 0){
			$('#checadmin').hide();
		}
		else{
			$("#atencionNoPenal").append('Asesor&iacute;a legal');
		}
		$("#checemer").css("display", "none");
		$("#textemer").empty();
		$('#tablaFondo').removeClass("back_pantalla_bienvenido");
		$('#tablaFondo').addClass("back_pantalla_bienvenido_def");
	}
}


function cerrarVentanaQueja(){
	var pantalla ="iframewindowQuejaCiudadana";
	$.closeWindow(pantalla);
}

</script> 

 <br/>
 <br/>
 <br/>
 <br/>
 <br/>
 
 <br/>
 <br/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td align="center" valign="top">
    		<table width="537" border="0" align="center" cellpadding="0" cellspacing="0" class="back_pantalla_bienvenido" id="tablaFondo">
      			<tr>
        			<td height="277" align="center" valign="top">
        				<table width="490" border="0" cellspacing="0" cellpadding="0">
          					<tr>
								<td height="31" align="center" valign="bottom" class="txt_cuad_bienvenido">Bienvenido a atenci&oacute;n temprana.</td> 
          					</tr>
        				</table>
          				<TABLE width=490 border=0 align=center>
          					<TBODY>
            					<TR align=middle>
              						<TD height="35" colSpan=3 align="center" valign="middle">&nbsp;</TD>
            					</TR>
            					<TR align=middle>
				    				<td width="245" align="left" >
				    					<input type="radio" name="kevinTipoTurno" value="1" id="checadmin"  onclick="turno()"/>
				    					<span id="atencionNoPenal" class="txt_cuad_bienvenido">Atenci&oacute;n no Penal</span>
				    				</td>
				    				<TD width=235 rowSpan=2 align="left" valign="top">
				    					<span class="txt_cuad_bienvenido">N&uacute;mero de turno:</span>
				     					<input type="text" id="tiem" maxLength=30 style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-TOP: 0px; BORDER-RIGHT: 0px; font-size: 1cm; " size="3"  > 
				     				</td>
								</tr>				 
			 					<TR align=middle>
              						<TD align="left">
				      					<input type="radio" name="kevinTipoTurno" value="0" id="checpenal" checked="checked" onClick="turno()"/> 
				      					<span id="atencionPenal" class="txt_cuad_bienvenido">Unidad  de Atenci&oacute;n Integral</span>
				      					<dir>
					  						<input id="checemer" name="checemer" type="checkbox"   />&nbsp;
					  						<span id="textemer" class="txt_cuad_bienvenido"  >Urgente</span> 
					  					</dir>
					  				</TD>
								</tr>
            					<TR align=middle>
              						<TD height="40" colSpan=3>
                                                        <input name="Generar Turno de atenci&oacute;n penal" type="button"  id="botpenal" value="Generar Turno" class="ui-button ui-corner-all ui-widget" onclick="cargaTurno();" /></td></tr>
                					
                                                        </td>
            					</tr>
            				</TBODY>
        				</table>
          				<TABLE border=0 width="50%" align=center>				 
            				<TBODY>
              					<TR valign="top">
                                                    
                                                        <td  >
                                                            
             					</TR>
            				</TBODY>
            			</TABLE>
           				<table width="490" border="0" cellspacing="0" cellpadding="0">
            				<tr>
              					<td height="25">&nbsp;</td>
            				</tr>
        				</table>
        			</td>
      			</tr>
    		</table>
    	</td>
    </tr>
</table>

<form name="frmDoc" action="<%= request.getContextPath() %>/ImprimirTurno.do" method="post">
<!-- input type="hidden" name="documentoId" /-->
</form>

				 
			
<script type="text/javascript">
//$("#botpenal").click(cargaTurno);
</script>
