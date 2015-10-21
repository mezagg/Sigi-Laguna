<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<script type="text/javascript">	


function consultaDelitoPersonaPorImputadosAudiencia(){
		    	
	jQuery("#gridRelacionesPersonaPJ").jqGrid({ 
		url:'<%= request.getContextPath()%>/consultarRelacionesDelitoPersonaPorAudiencia.do?idAudiencia='+idAudiencia +'&idExpediente='+idExpediente+'', 
		datatype: "xml",
		colNames:['<bean:message key="probableResponsable"/>','Delito','V&iacute;ctima'],
		colModel:[ 	{name:'probableResponsable',index:'probableResponsable', width:250,sortable:false},
					{name:'Delito',index:'delito', width:250,sortable:false},
					{name:'Victima',index:'victima',width:250,sortable:false},
				],
		pager: jQuery('#pagerGridRelacionesPersonaPJ'),
		rowNum:10,
		rowList:[10,20,30],
		autowidth: true,
		caption:"RELACIONES DELITO(S) PERSONA(S)",
		sortname: 'probableResponsable',
		viewrecords: true,
		sortorder: "desc",
		loadComplete: function(){		        		
	
	     	}
	}).navGrid('#pagerGridRelacionesPersonaPJ',{edit:false,add:false,del:false});
}
 </script>
 
 
 
 <div id="seccionRelacionesDelitoPersona">
 	<table align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table id="gridRelacionesPersonaPJ" >
				</table>
				<div id="pagerGridRelacionesPersonaPJ"></div>
			</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>