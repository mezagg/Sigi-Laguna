
Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'http://172.25.48.8:8080/imagenes/'
,'http://gitlab.lucasianmexico.com:9080/imagenes/') AS NText) where  CAST(cCuerpo as NVarchar(MAX)) like '%172.25.48.8%';

--http://10.10.30.11:8080/imagenes/imagen/sistema/Logo%20Oficial.jpg

Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'http://10.10.30.11:8080/imagenes/'
,'http://gitlab.lucasianmexico.com:9080/imagenes/') AS NText) where  CAST(cCuerpo as NVarchar(MAX)) like '%10.10.30.11%';
