/****** Object:  View [dbo].[usuariosigi]    Script Date: 10/02/16 12:40:05 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE view [dbo].[usuariosigi] as 
Select f.cNombreFuncionario, 
f.cApellidoPaternoFuncionario,
f.cApellidoMaternoFuncionario,
f.cSexo,
uve.Valor_id as especialidad_id,
uve.cValor as especilidad_valor,
uvp.Valor_id as puesto_id,
uvp.cValor as puesto_valor,
jo.JerarquiaOrganizacional_id,
jo.cNombre as nombre_jerarquia,
ute.Valor_id as tipo_especilidad_id,
ute.cValor as tipo_especilidad_valor,
f.cNumeroEmpleado,
cd.catDiscriminante_id,
cd.cNombre as cNombreDiscriminante,
uie.catUIE_id,
uie.cNombreUIE,
ca.CatAreasNegocio_id,
ca.cNombre as cNombreAreaNeg,
u.cClaveUsuario,
jur.Rol_id, 
jur.cNombreRol,
jur.cDescripcionRol
From Funcionario f left outer join Usuario u
on f.iClaveFuncionario =u.iClaveFuncionario
left outer join JerarquiaOrganizacional jo 
on f.JerarquiaOrganizacional_id= jo.JerarquiaOrganizacional_id
left outer join CatAreasNegocio ca 
on f.CatAreasNegocio_id=ca.CatAreasNegocio_id
left outer join CatDiscriminante cd
on f.catDiscriminante_id=cd.catDiscriminante_id
left outer join Region r
on f.region_id=r.region_id
left outer join CatUIEspecializada uie
on f.catUIE_id=uie.catUIE_id
left outer join valor uve 
on f.Especialidad_val= uve.Valor_id --Campo catalogo_id 118
left outer join valor uvp 
on f.Especialidad_val= uvp.Valor_id --Campo catalogo_id 67
left outer join valor ute 
on f.Especialidad_val= ute.Valor_id --Campo catalogo_id 76
left outer join (
				select  ur.Rol_id, ur.Usuario_id ,rol.cNombreRol,rol.cDescripcionRol 
				From UsuarioRol ur join Rol rol 
				on ur.Rol_id=rol.Rol_id
				where ur.Usuario_id= Usuario_id and esPrincipal=1)jur
on u.Usuario_id=jur.Usuario_id
GO

