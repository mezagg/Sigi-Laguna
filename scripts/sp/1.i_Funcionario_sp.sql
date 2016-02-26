USE [coa-sab-DEF]
GO

/****** Object:  StoredProcedure [dbo].[i_Funcionario_sp]    Script Date: 12/02/2016 12:28:23 ******/
--DROP PROCEDURE [dbo].[i_Funcionario_sp]
GO

/****** Object:  StoredProcedure [dbo].[i_Funcionario_sp]    Script Date: 12/02/2016 12:28:23 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[i_Funcionario_sp]


--TABLA FUNCIONARIO--
	--iClaveFuncionario
	@cNombreFuncionario varchar(50),
	@cApellidoPaternoFuncionario varchar(50),	
	@cApellidoMaternoFuncionario varchar(50),	
	@cSexo varchar(1),
	--@cRFC	
	--@cCURP	
	--@dFechaNacimiento	
	--@cEmail	
	--@cCedula	
	--@iClaveFuncionarioJefe	
	@Especialidad_val decimal,
	@Puesto_val	decimal,
	@JerarquiaOrganizacional_id decimal,	
	@TipoEspecialidad_val decimal,	
	--@bEsPar	
	@cNumeroEmpleado varchar(20),	
	@catDiscriminante_id decimal,	
	--@dFechaIngreso	
	--@ArchivoDigital_id	
	@catUIE_id decimal,	
	@CatAreasNegocio_id decimal,

--TABLA USUARIO--
--@Usuario_id	
@cClaveUsuario varchar(30),
--@cPalabraSecreta	
--@iClaveFuncionario	
--@password	
--@cllave	
--@iSesion	
--@iIntentos	
--@cIP	
--@bEsActivo	
--@idSesionServer

--TABLA USUARIOROL--
--@Usuario_id	
@Rol_id int
--@dFechaInicio	
--@dFechaFin	
--@esPrincipal


AS

Declare @iclaveFuncionario int
Declare @usuarioId int

INSERT INTO Funcionario VALUES (
						@cNombreFuncionario,
						@cApellidoPaternoFuncionario,	
						@cApellidoMaternoFuncionario,	
						@cSexo,
						'',	
						'',	
						getdate(),--Fecha	
						NULL,	
						NULL,	
						NULL,	
						@Especialidad_val, --2002
						@Puesto_val, --2110
						@JerarquiaOrganizacional_id, --10
						0.00,
						@TipoEspecialidad_val, --1728
						NULL,	
						@cNumeroEmpleado,	
						@catDiscriminante_id, --3	
						getdate(),--Fecha		
						NULL,	
						@catUIE_id,	--3
						@CatAreasNegocio_id,--3
						5, --EntidadFederativa
						2) --Region
SET @iclaveFuncionario = (select max(iClaveFuncionario) from Funcionario)


INSERT INTO Usuario VALUES (	
						@cClaveUsuario,
						NULL,	
						@iclaveFuncionario,	
						0x35E0C0A71803B29F3896A5DB8807B602, --sigi00	
						0x0A5D8C1B801AD5AB7E44A7C5D67E9329,	
						0,	
						0,
						'0.0.0.0',
						1,	
						'')

SET @usuarioId = (select max(Usuario_id) from Usuario)


INSERT INTO UsuarioRol VALUES (
						@usuarioId,	
						@Rol_id, --agentemp 7
						getdate(),--Fecha		
						getdate(),--Fecha	
						1)

GO

