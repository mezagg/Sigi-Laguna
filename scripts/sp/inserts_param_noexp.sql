-- INSERTS PARA PARAMETROS DE GENERACION DE No. EXPEDIENTE SABINAS

INSERT INTO [dbo].[Parametro] (cClave,cValor,cDescripcion,cTipoValor)
		VALUES ('DIGITOS_NUM_EXPEDIENTE','4',
				'N�mero de digitos para la generaci�n de numero de expediente','1')

INSERT INTO [dbo].[Parametro] (cClave,cValor,cDescripcion,cTipoValor)
		VALUES ('CONF_UNIDADES_NUM_EXPEDIENTE','AGENCIA',
				'Especifica el tipo de filtrado de unidades  que se consultan para la generaci�n de n�mero de expediente','1')


-- INSERTS PARA PARAMETROS DE GENERACION DE No. EXPEDIENTE PIEDRAS NEGRAS

INSERT INTO [dbo].[Parametro] (cClave,cValor,cDescripcion,cTipoValor)
		VALUES ('DIGITOS_NUM_EXPEDIENTE','5',
				'N�mero de digitos para la generaci�n de numero de expediente','1')

INSERT INTO [dbo].[Parametro] (cClave,cValor,cDescripcion,cTipoValor)
		VALUES ('CONF_UNIDADES_NUM_EXPEDIENTE','UIE',
				'Especifica el tipo de filtrado de unidades  que se consultan para la generaci�n de n�mero de expediente','1')


