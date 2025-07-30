USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ACTUALIZAR_ESTADO_EJEMPLAR]    Script Date: 17/7/2025 23:33:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ACTUALIZAR_ESTADO_EJEMPLAR]
	@ID int,
	@Estado bit
AS
	UPDATE Ejemplar SET
	Estado=@Estado
	WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ACTUALIZAR_LIBRO]    Script Date: 17/7/2025 23:33:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ACTUALIZAR_LIBRO]
	@ID int,
	@ISBN int,
	@Titulo varchar(50),
	@Anio_Publicacion int,
	@Autor varchar(25),
	@Categoria varchar(25)
AS
	UPDATE Libro SET
		ISBN=@ISBN,
		Titulo=@Titulo,
		Anio_Publicacion=@Anio_Publicacion,
		Autor=@Autor,
		Categoria=@Categoria
	WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ACTUALIZAR_PRESTAMO]    Script Date: 17/7/2025 23:34:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ACTUALIZAR_PRESTAMO]
	@ID int,
	@Fecha_Prestamo date,
	@Fecha_Devolucion date,
	@Estado bit,
	@ID_Bibliotecario int,
	@ID_Usuario int,
	@ID_Ejemplar int
AS
	UPDATE Prestamo SET
	Fecha_Prestamo=Fecha_Prestamo,
	Fecha_Devolucion=@Fecha_Devolucion,
	Estado=@Estado,
	ID_Bibliotecario=@ID_Bibliotecario,
	ID_Usuario=@ID_Usuario,
	ID_Ejemplar=@ID_Ejemplar;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ACTUALIZAR_USUARIO]    Script Date: 17/7/2025 23:34:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ACTUALIZAR_USUARIO]
	@ID int,
	@Nombre varchar(25),
	@Apellido varchar(25),
	@Direccion varchar(50),
	@Telefono int,
	@Fecha_Nacimiento date
AS
	UPDATE Usuario SET
		Nombre=@Nombre,
		Apellido=@Apellido,
		Direccion=@Direccion,
		Telefono=@Telefono,
		Fecha_Nacimiento=@Fecha_Nacimiento
	WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ELIMINAR_EJEMPLAR]    Script Date: 17/7/2025 23:34:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ELIMINAR_EJEMPLAR]
	@ID int
AS
	DELETE FROM Ejemplar WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ELIMINAR_LIBRO]    Script Date: 17/7/2025 23:35:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ELIMINAR_LIBRO]
	@ID int
AS
	DELETE FROM Libro WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ELIMINAR_PRESTAMO]    Script Date: 17/7/2025 23:35:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ELIMINAR_PRESTAMO]
	@ID int
AS
	DELETE FROM Prestamo WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_ELIMINAR_USUARIO]    Script Date: 17/7/2025 23:35:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_ELIMINAR_USUARIO]
	@ID int
AS
	DELETE FROM Usuario WHERE ID = @ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_INSERTAR_EJEMPLAR]    Script Date: 17/7/2025 23:35:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_INSERTAR_EJEMPLAR]
	@Codigo_Interno nvarchar(255),
	@Estado bit,
	@ID_Libro int
AS
	INSERT INTO Ejemplar(Codigo_Interno,Estado,ID_Libro)
		VALUES(@Codigo_Interno,@Estado,@ID_Libro);

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_INSERTAR_LIBRO]    Script Date: 17/7/2025 23:36:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_INSERTAR_LIBRO]
	@ISBN int,
	@Titulo varchar(50),
	@Anio_Publicacion int,
	@Autor varchar(25),
	@Categoria varchar(25),
	@ID_Libro int OUTPUT -- parámetro de salida
AS
BEGIN
	INSERT INTO Libro(ISBN, Titulo, Anio_Publicacion, Autor, Categoria)
	VALUES(@ISBN, @Titulo, @Anio_Publicacion, @Autor, @Categoria);

	-- Obtiene el último IDENTITY del mismo scope
	SET @ID_Libro = SCOPE_IDENTITY();
END

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_INSERTAR_LIBRO_EJEMPLAR]    Script Date: 17/7/2025 23:36:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_INSERTAR_LIBRO_EJEMPLAR]
	@ISBN int,
	@Titulo varchar(25),
	@Anio_Publicacion int,
	@Autor varchar(25),
	@Categoria varchar(25),
	@ID_Libro int OUTPUT -- parámetro de salida
AS
BEGIN
	INSERT INTO Libro(ISBN, Titulo, Anio_Publicacion, Autor, Categoria)
	VALUES(@ISBN, @Titulo, @Anio_Publicacion, @Autor, @Categoria);

	-- Obtiene el último IDENTITY del mismo scope
	SET @ID_Libro = SCOPE_IDENTITY();
END

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_INSERTAR_PRESTAMO]    Script Date: 17/7/2025 23:36:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_INSERTAR_PRESTAMO]
	@Fecha_Prestamo date,
	@Fecha_Devolucion date,
	@Estado bit,
	@ID_Usuario int,
	@ID_Ejemplar int
AS
	INSERT INTO Prestamo(Fecha_Prestamo,Fecha_Devolucion,Estado,ID_Usuario,ID_Ejemplar)
	VALUES(@Fecha_Prestamo,@Fecha_Devolucion,@Estado,@ID_Usuario,@ID_Ejemplar);

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_INSERTAR_USUARIO]    Script Date: 17/7/2025 23:36:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_INSERTAR_USUARIO]
	@Nombre varchar(25),
	@Apellido varchar(25),
	@Direccion varchar(50),
	@Telefono int,
	@Fecha_Nacimiento date
AS
	INSERT INTO Usuario(Nombre,Apellido,Direccion,Telefono,Fecha_Nacimiento)
	VALUES(@Nombre,@Apellido,@Direccion,@Telefono,@Fecha_Nacimiento);

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_BIBLIOTECARIO]    Script Date: 17/7/2025 23:36:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_OBTENER_BIBLIOTECARIO]
    @Usuario NVARCHAR(50),
    @Contrasena NVARCHAR(50)
AS
BEGIN
    IF EXISTS (
        SELECT 1 FROM Bibliotecario WHERE Usuario = @Usuario AND Clave = @Contrasena
    )
        SELECT CAST(1 AS BIT) AS EsValido;
    ELSE
        SELECT CAST(0 AS BIT) AS EsValido;
END

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_EJEMPLAR]    Script Date: 17/7/2025 23:37:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_OBTENER_EJEMPLAR]
AS
BEGIN
	SELECT
		E.ID,
		E.Codigo_Interno,
		E.Estado,
		E.ID_Libro,
		L.Titulo AS Nombre_Libro
	FROM Ejemplar E
	INNER JOIN Libro L ON E.ID_Libro = L.ID;
END;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_ID_LIBRO]    Script Date: 17/7/2025 23:37:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[SP_OBTENER_ID_LIBRO]
	@ID int
as
	SELECT ISBN,Titulo,Anio_Publicacion,Autor,Categoria FROM Libro WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_ID_USUARIO]    Script Date: 17/7/2025 23:37:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_OBTENER_ID_USUARIO]
	@ID int
AS
	SELECT Nombre,Apellido,Direccion,Telefono,Fecha_Nacimiento FROM Usuario WHERE ID=@ID;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_LIBRO]    Script Date: 17/7/2025 23:37:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_OBTENER_LIBRO]
AS
	SELECT ID,ISBN,Titulo,Anio_Publicacion,Autor,Categoria FROM Libro;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_NOMBRE_USUARIO]    Script Date: 17/7/2025 23:38:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_OBTENER_NOMBRE_USUARIO]
	@Nombre varchar(50)
as
	select ID,Nombre,Apellido,Direccion,Telefono,Fecha_Nacimiento FROM Usuario WHERE Nombre=@Nombre;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_PRESTAMO]    Script Date: 17/7/2025 23:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[SP_OBTENER_PRESTAMO]
AS
BEGIN
	SELECT
		p.ID,
		U.ID AS ID_Usuario,
		U.Nombre + ' ' + U.Apellido AS Usuario,
		E.ID AS ID_Ejemplar,
		E.Codigo_Interno AS Codigo_Ejemplar,
		L.ID as ID_Libro,
		L.Titulo AS Libro,
		P.Fecha_Prestamo,
		P.Fecha_Devolucion,
		P.Estado
	FROM Prestamo P
	INNER JOIN Usuario U ON P.ID_Usuario = U.ID
	INNER JOIN Ejemplar E ON P.ID_Ejemplar = E.ID
	INNER JOIN Libro L ON E.ID_Libro = L.ID;
END;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_TITULO_LIBRO]    Script Date: 17/7/2025 23:38:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[SP_OBTENER_TITULO_LIBRO]
	@Titulo varchar(50)
as
	select ID,ISBN,Titulo,Anio_Publicacion,Autor,Categoria FROM Libro WHERE Titulo=@Titulo;

USE [Biblioteca]
GO
/****** Object:  StoredProcedure [dbo].[SP_OBTENER_USUARIO]    Script Date: 17/7/2025 23:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_OBTENER_USUARIO]
AS
	SELECT ID,Nombre,Apellido,Direccion,Telefono,Fecha_Nacimiento FROM Usuario;