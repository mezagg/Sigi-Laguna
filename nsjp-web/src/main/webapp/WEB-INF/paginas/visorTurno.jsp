<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
<script type="text/javascript"> 


	

		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionTurnos()%>';

$(document).ready(
		
		setInterval(function cargaTurnos(){
			
			$('#visorTurnos').text('');
			var cont = 0;
			
		    $.ajax({
			      
		    	  url: '<%= request.getContextPath()%>/ConsultarTurnoReciente.do',
		    	  dataType: 'xml',
		    	  Type: 'POST',
		    	  data:'',
		    	  async: false,
		    	  success: function(xml){
		    		  $('#visorTurnos').append('<table width="700"  border="1"   align="center">');
		    		  $('#visorTurnos').append('<tr align="center"><td colspan="2" >');
		    		  $('#visorTurnos').append('<h1 style="font-size: 200%;">Visor Turnos</h1>');
		    		  $('#visorTurnos').append('</td></tr>');
		    		  
		    		  $(xml).find('turnoDTO').each(function(){
		    			  
		    			if(cont == 0 || cont == 2){
		    				$('#visorTurnos').append('<tr align="center">');
		    			}
		  				
		    			$('#visorTurnos').append('<td width="1000" height= "190px" align="center" >' + 
		    										'<h1 style="font-size: 300%; font-weight: bold;">' +
		    										$(this).find('numeroTurno').text() + '</h1>' +
		    										'<h1 style="font-size: 200%;">' +
		    										$(this).find('tipoTurno').text() + '</h1>' +
		    										'<h1 style="font-size: 150%;">' +
		  	    				                    $(this).find('usuario').find('funcionario').find('nombreFuncionario').text() + ' ' +
		  	    				                  	$(this).find('usuario').find('funcionario').find('apellidoPaternoFuncionario').text() + ' ' +
		  	    				                	$(this).find('usuario').find('funcionario').find('apellidoMaternoFuncionario').text() + ' ' +
		  	    				                 	'</h1></td>');
		  				
		  				if(cont == 1 || cont == 3){
		  					$('#visorTurnos').append('</tr>');
		    			}
		  				
		  				cont++;
		  				
		  			});
		    		 
		    		  $('#visorTurnos').append('</table>');
		    		  
		    		  	    		
				  }
		    	});
		      
			}, tiempo)
	
	);
</script> 

 
<br />

<div id="visorTurnos" align="center" style="text-align: center;"></div>

