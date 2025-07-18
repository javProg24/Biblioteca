CREATE TABLE [Usuario] (
  [ID] int PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Nombre] varchar(25) NOT NULL,
  [Apellido] varchar(25) NOT NULL,
  [Direccion] varchar(50) NOT NULL,
  [Telefono] int(10) NOT NULL,
  [Fecha_Nacimiento] date NOT NULL
)
GO

CREATE TABLE [Libro] (
  [ID] int PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [ISBN] int NOT NULL,
  [Titulo] varchar(25) NOT NULL,
  [Anio_Publicacion] int NOT NULL,
  [Autor] varchar(25) NOT NULL,
  [Categoria] varchar(25) NOT NULL
)
GO

CREATE TABLE [Bibliotecario] (
  [ID] int PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Usuario] varchar(25),
  [Clave] varchar(8)
)
GO

CREATE TABLE [Ejemplar] (
  [ID] int PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Codigo_Interno] nvarchar(255) NOT NULL,
  [Estado] bit NOT NULL,
  [ID_Libro] int NOT NULL
)
GO

CREATE TABLE [Prestamo] (
  [ID] int PRIMARY KEY NOT NULL IDENTITY(1, 1),
  [Fecha_Prestamo] date NOT NULL,
  [Fecha_Devolucion] date NOT NULL,
  [Estado] bit NOT NULL,
  [ID_Bibliotecario] int NOT NULL,
  [ID_Usuario] int NOT NULL,
  [ID_Ejemplar] int NOT NULL
)
GO

ALTER TABLE [Ejemplar]
ADD CONSTRAINT FK_Ejemplar_Libro
FOREIGN KEY ([ID_Libro])
REFERENCES [Libro]([ID])
ON DELETE CASCADE;
GO

ALTER TABLE [Prestamo] ADD FOREIGN KEY ([ID_Ejemplar]) REFERENCES [Ejemplar] ([ID])
GO

ALTER TABLE [Prestamo] ADD FOREIGN KEY ([ID_Usuario]) REFERENCES [Usuario] ([ID])
GO

ALTER TABLE [Prestamo] ADD FOREIGN KEY ([ID_Bibliotecario]) REFERENCES [Bibliotecario] ([ID])
GO
