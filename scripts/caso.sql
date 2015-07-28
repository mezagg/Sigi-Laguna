ALTER TABLE [dbo].[Caso]
ALTER COLUMN [cNumeroGeneralCaso] [cadena 32];
GO

ALTER TABLE [dbo].[CatDiscriminante]
ADD COLUMN [region_id] [numeric](18, 0) NULL;
GO


CREATE TABLE [dbo].[Region](
	[region_id] [numeric](18, 0) NOT NULL,
	[cClaveRegion] [nvarchar](3) NOT NULL,
	[cNombre] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Region] PRIMARY KEY CLUSTERED
(
	[region_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


