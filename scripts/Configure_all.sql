select * from ConfInstitucion

update ConfInstitucion set cUrlInst='http://localhost:8080/nsjp-web'

select * from Parametro

update ConfInstitucion 
set cValor='http://172.25.48.8:8080/imagenes/imagen'
where cClave='URL_SERVIDOR_IMAGENES';

Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'http://172.25.48.8:8080/imagenes/'
,'http://gitlab.lucasianmexico.com:9080/imagenes/') AS NText) where  CAST(cCuerpo as NVarchar(MAX)) like '%172.25.48.8%';



Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'http://172.25.48.8:8080/imagenes/'
,'http://gitlab.lucasianmexico.com:9080/imagenes/') AS NText) where  CAST(cCuerpo as NVarchar(MAX)) like '%172.25.48.8%';

http://10.10.30.11:8080/imagenes/imagen/sistema/Logo%20Oficial.jpg

Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'http://10.10.30.11:8080/imagenes/'
,'http://gitlab.lucasianmexico.com:9080/imagenes/') AS NText) where  CAST(cCuerpo as NVarchar(MAX)) like '%10.10.30.11%';


select * from Usuario
update  [Usuario] set cIP='0.0.0.0', idSesionServer=null,
password=0x5D4F20F64233BB0A8F902693F5294D22, cllave=0xFDE8F0AA3479D0A10E2F01EC2D5BD668;
