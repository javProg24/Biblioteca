USE Biblioteca;

GO
CREATE PROCEDURE SP_INSERTAR_USUARIO
	@Nombre varchar(25),
	@Apellido varchar(25),
	@Direccion varchar(50),
	@Telefono int,
	@Fecha_Nacimiento date
AS
	INSERT INTO Usuario(Nombre,Apellido,Direccion,Telefono,Fecha_Nacimiento) 
	VALUES(@Nombre,@Apellido,@Direccion,@Telefono,@Fecha_Nacimiento);
GO

CREATE PROCEDURE SP_ACTUALIZAR_USUARIO
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
GO

GO
CREATE PROCEDURE SP_ELIMINAR_USUARIO
	@ID int
AS
	DELETE FROM Usuario WHERE ID = @ID;
GO

CREATE PROCEDURE SP_OBTENER_USUARIO
AS
	SELECT ID,Nombre,Apellido,Direccion,Telefono,Fecha_Nacimiento FROM Usuario;
GO

CREATE PROCEDURE SP_INSERTAR_LIBRO
	@ISBN int,
	@Titulo varchar(25),
	@Anio_Publicacion int,
	@Autor varchar(25),
	@Categoria varchar(25)
AS
	INSERT INTO Libro(ISBN,Titulo,Anio_Publicacion,Autor,Categoria)
		VALUES(@ISBN,@Titulo,@Anio_Publicacion,@Autor,@Categoria);
GO

CREATE PROCEDURE SP_ACTUALIZAR_LIBRO
	@ID int,
	@ISBN int,
	@Titulo varchar(25),
	@Anio_Publicacion int,
	@Autor varchar(25),
	@Categoria varchar(25)
AS
	UPDATE Libro SET
		ISBN=@ISBN,
		Titulo=@Titulo,
		Anio_Publicacion=@Anio_Publicacion,
		Autor=@Autor,
		Categoria=@Categoria;
GO

CREATE PROCEDURE SP_ELIMINAR_LIBRO
	@ID int
AS
	DELETE FROM Libro WHERE ID=@ID;
GO

SELECT
    fk.name AS ForeignKeyName,
    tp.name AS ParentTable,
    tr.name AS ReferencedTable
FROM sys.foreign_keys fk
JOIN sys.tables tp ON fk.parent_object_id = tp.object_id
JOIN sys.tables tr ON fk.referenced_object_id = tr.object_id
WHERE tp.name = 'Ejemplar' AND tr.name = 'Libro';

GO
CREATE PROCEDURE SP_OBTENER_LIBRO
AS
	SELECT ID,ISBN,Titulo,Anio_Publicacion,Autor,Categoria FROM Libro;
GO

CREATE PROCEDURE SP_INSERTAR_EJEMPLAR
	@Codigo_Interno nvarchar(255),
	@Estado bit,
	@ID_Libro int
AS
	INSERT INTO Ejemplar(Codigo_Interno,Estado,ID_Libro)
		VALUES(@Codigo_Interno,@Estado,@ID_Libro);
GO

CREATE PROCEDURE SP_ELIMINAR_EJEMPLAR
	@ID int
AS
	DELETE FROM Ejemplar WHERE ID=@ID;
GO

CREATE PROCEDURE SP_OBTENER_EJEMPLAR
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
GO

CREATE PROCEDURE SP_INSERTAR_PRESTAMO
	@Fecha_Prestamo date,
	@Fecha_Devolucion date,
	@Estado bit,
	@ID_Bibliotecario int,
	@ID_Usuario int,
	@ID_Ejemplar int
AS
	INSERT INTO Prestamo(Fecha_Prestamo,Fecha_Devolucion,Estado,ID_Bibliotecario,ID_Usuario,ID_Ejemplar)
	VALUES(@Fecha_Prestamo,@Fecha_Devolucion,@Estado,@ID_Bibliotecario,@ID_Usuario,@ID_Ejemplar);
GO

CREATE PROCEDURE SP_ACTUALIZAR_PRESTAMO
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
GO

CREATE PROCEDURE SP_ELIMINAR_PRESTAMO
	@ID int
AS
	DELETE FROM Prestamo WHERE ID=@ID;
GO

CREATE OR ALTER PROCEDURE SP_OBTENER_PRESTAMO
AS
BEGIN
	SELECT 
		U.Nombre + ' ' + U.Apellido AS Usuario,
		E.Codigo_Interno AS Codigo_Ejemplar,
		L.Titulo AS Libro,
		P.Fecha_Prestamo,
		P.Fecha_Devolucion,
		P.Estado,
		B.Usuario AS Bibliotecario
	FROM Prestamo P
	INNER JOIN Bibliotecario B ON P.ID_Bibliotecario = B.ID
	INNER JOIN Usuario U ON P.ID_Usuario = U.ID
	INNER JOIN Ejemplar E ON P.ID_Ejemplar = E.ID
	INNER JOIN Libro L ON E.ID_Libro = L.ID;
END;
GO

execute SP_OBTENER_PRESTAMO;