package com.isp.seeds.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.Exceptions.InstanceNotFoundException;
import com.isp.seeds.dao.spi.CategoriaDAO;
import com.isp.seeds.dao.spi.ContenidoDAO;
import com.isp.seeds.dao.spi.EtiquetaDAO;
import com.isp.seeds.dao.spi.VideoDAO;
import com.isp.seeds.dao.utils.JDBCUtils;
import com.isp.seeds.model.Categoria;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Etiqueta;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.criteria.ContenidoCriteria;
import com.isp.seeds.service.util.Results;

public class ContenidoDAOImpl implements ContenidoDAO {

	private CategoriaDAO categoriaDao = null;
	private EtiquetaDAO etiquetaDao = null;
	private VideoDAO videoDao = null;         // ESTO AQUI DA STACK OVERFLOW ERROR (EXCEPTION)

	private static Logger logger = LogManager.getLogger(ContenidoDAOImpl.class);


	public ContenidoDAOImpl () {
		categoriaDao = new CategoriaDAOImpl();
		etiquetaDao = new EtiquetaDAOImpl();
		//videoDao = new VideoDAOImpl();

	}

	/**
	 * Comprueba si Existe alg�n contenido con el ID que recibe.
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @return Boolean : True si existe el contenido. False si no existe le contenido
	 * 
	 */
	public Boolean exists(Connection connection, Long idContenido) 
			throws DataException {
		boolean exist = false;

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", idContenido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT ID_CONTENIDO " + 
							" FROM CONTENIDO " +
							" WHERE ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return exist;
	}


	/**
	 * Comprueba si Existen relaciones entre Este contenido y Cualquier otro contenido.
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @return Boolean : True si existen relaciones. False en caso contrario.
	 */
	protected Boolean hasRelations (Connection connection, Long idContenido)
			throws DataException {
		boolean exist = false;

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", idContenido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT USUARIO_ID_CONTENIDO " + 
							" FROM USUARIO_CONTENIDO " +
							" WHERE CONTENIDO_ID_CONTENIDO = ? OR USUARIO_ID_CONTENIDO = ?";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idContenido);
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}



	/**
	 * Devuelve el contenido identificado id recibido como parametro.
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @return Contenido : Contenido que corresponde con el ID recibido
	 */
	@Override
	public Contenido findById(Connection connection, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", idContenido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try { 
			String queryString = 
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
							+" FROM  CONTENIDO C LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO=UC.CONTENIDO_ID_CONTENIDO) "
							+" WHERE C.ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			Contenido contenido = null;

			if (resultSet.next()) {
				contenido = loadNext(connection, resultSet);				
			} else {
				throw new DataException("\nContenido with id " +idContenido+ "not found\n");
			}
			return contenido;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}


	/**
	 * Devuelve el contenido que tiene EXACTEMENTE el nombre recibido como parametro.
	 * @param connection - Connection
	 * @param nombreContenido - String
	 * @return Contenido : Contenido que corresponde con el nombre recibido
	 */
	@Override
	public Contenido findByNombre(Connection connection, String nombreContenido, int startIndex, int count, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Nombre= {} ", nombreContenido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT c.id_contenido, c.nombre, c.fecha_alta, c.fecha_mod, c.autor_id_contenido, c.tipo"+
							" FROM Contenido c " +
							" WHERE c.nombre = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, nombreContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			Contenido contenido = null;

			if (resultSet.next()) {
				contenido = loadNext(connection, resultSet);				
			} else {
				throw new DataException("\nContenido with id " +nombreContenido+ "not found\n");
			}
			return contenido;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}


	@Override
	public Results<Contenido> findByCriteria (Connection connection, ContenidoCriteria contenido, int startIndex, int count, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Criteria= {} startIndex={} count={} lang={}", contenido, startIndex, count, idioma);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) " + 
					" FROM CONTENIDO C LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO) ");
			//Al anexionar las tablas solo si es uno de los tipos buscados se soluciona el filtrado por tipo:
			if (contenido.getAceptarUsuario()!=null && contenido.getAceptarUsuario()) { queryString.append(" INNER JOIN USUARIO U ON (C.ID_CONTENIDO = U.ID_CONTENIDO) ");}
			if (contenido.getAceptarLista()!=null && contenido.getAceptarLista()) { queryString.append(" INNER JOIN LISTA L ON (C.ID_CONTENIDO = L.ID_CONTENIDO) ");}
			if (contenido.getAceptarVideo()!=null && contenido.getAceptarVideo()) { queryString.append(" INNER JOIN VIDEO V ON (C.ID_CONTENIDO = V.ID_CONTENIDO) ");}
			
			if (contenido.getCategoria()!=null) { queryString.append(" INNER JOIN CATEGORIA_CONTENIDO CC ON (C.ID_CONTENIDO = CC.ID_CONTENIDO) AND (CC.ID_CATEGORIA = ? )");}			

			boolean first = true;

			if (contenido.getId()!=null) {
				addClause(queryString, first, " C.ID_CONTENIDO = ? ");
				first = false;
			}
			
			if (contenido.getNombre()!=null) {
				addClause(queryString, first, " C.NOMBRE LIKE ? ");
				first = false;
			}

			if (contenido.getFechaAlta()!=null) {
				addClause(queryString, first, " C.FECHA_ALTA >= ?  ");
				first = false;
			}

			if (contenido.getFechaAltaHasta()!=null) {
				addClause(queryString, first, " C.FECHA_ALTA <= ?  ");
				first = false;
			}

			if (contenido.getFechaMod()!=null) {
				addClause(queryString, first, " C.FECHA_MOD >= ?  ");
				first = false;
			}

			if (contenido.getFechaModHasta()!=null) {
				addClause(queryString, first, " C.FECHA_MOD <= ?  ");
				first = false;
			}

			if (contenido.getAutor()!=null) {
				addClause(queryString, first, " C.AUTOR_ID_CONTENIDO = ? ");
				first = false;
			}

			if (contenido.getTipo()!=null) {
				addClause(queryString, first, " C.TIPO = ? ");
				first = false;
			}
			
			if (contenido.getCategoria()!=null) {
				addClause(queryString, first, " CC.ID_CATEGORIA = ? ");
				first = false;
			}
			
			queryString.append(" GROUP BY C.ID_CONTENIDO ");
			
			boolean firstHaving = true;
			
			if(contenido.getValoracionMin()!=null) {
				addHaving(queryString, firstHaving, "  AVG(UC.VALORACION) >= ? ");
				firstHaving=false;
			}
			
			if(contenido.getValoracionMax()!=null) {
				addHaving(queryString, firstHaving, "  AVG(UC.VALORACION) <= ? ");
				firstHaving=false;
			}
			
			if(contenido.getReproduccionesMin()!=null) {
				addHaving(queryString, firstHaving, "  C.REPRODUCCIONES >= ? ");
				firstHaving=false;
			}
			
			if(contenido.getReproduccionesMax()!=null) {
				addHaving(queryString, firstHaving, "  C.REPRODUCCIONES <= ? ");
				firstHaving=false;
			}
			
			queryString.append(" ORDER BY C.FECHA_ALTA ");
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if (contenido.getId()!=null)
				preparedStatement.setLong(i++, contenido.getId());
			if (contenido.getNombre()!=null)
				preparedStatement.setString(i++, "%" + contenido.getNombre() + "%");

			// Se utiliza como "Fecha Desde" la fecha de la clase contenido:
			if (contenido.getFechaAlta()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(contenido.getFechaAlta().getTime()));
			if (contenido.getFechaAltaHasta()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(contenido.getFechaAltaHasta().getTime()));
			if (contenido.getFechaMod()!=null)
				preparedStatement.setDate(i++,  new java.sql.Date(contenido.getFechaMod().getTime()));
			if (contenido.getFechaModHasta()!=null)
				preparedStatement.setDate(i++,  new java.sql.Date(contenido.getFechaModHasta().getTime()));


			if (contenido.getAutor()!=null) 
				preparedStatement.setLong(i++,  contenido.getAutor() );
			if (contenido.getTipo()!=null) 
				preparedStatement.setInt(i++, contenido.getTipo());
			
			if(contenido.getValoracionMin()!=null) {
				preparedStatement.setDouble(i++, contenido.getValoracionMin());
			}
			if(contenido.getValoracionMax()!=null) {
				preparedStatement.setDouble(i++, contenido.getValoracionMax());
			}
			if(contenido.getReproduccionesMin()!=null) {
				preparedStatement.setInt(i++, contenido.getReproduccionesMin());
			}
			if(contenido.getReproduccionesMax()!=null) {
				preparedStatement.setInt(i++, contenido.getReproduccionesMax());
			}
			
			if(contenido.getCategoria()!=null) {
				preparedStatement.setLong(i++, contenido.getCategoria());
				preparedStatement.setLong(i++, contenido.getCategoria());
			}

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			List<Contenido> page = new ArrayList<Contenido>();    
			Contenido e = null;

			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext(connection, resultSet);
					page.add(e);
					currentCount++;

				} while ((currentCount < count) &&  resultSet.next()) ;
			}

			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<Contenido> results = new Results<Contenido>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		}  finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public Double getValoracion (Connection connection, Long idContenido)  
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("IdContenido={}", idContenido);
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Double valor = null;
		
		try {
			String queryString = " SELECT AVG(VALORACION) FROM USUARIO_CONTENIDO WHERE CONTENIDO_ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if(resultSet.getObject(1) != null) {
					valor = resultSet.getDouble(1);
				}
			} else {
				throw new DataException("\nValoracion del contenido " +idContenido+ " no encontrada \n");
			}
			return valor;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} catch (DataException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		}  finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	/**
	 * Devuelve todos los contenidos.
	 * @param connection - Connection
	 * @return  Lista con todos los contenidos
	 */
	@Override
	public Results<Contenido> findAll(Connection connection, int startIndex, int count, String idioma) throws DataException {
		ContenidoDAO dao= new ContenidoDAOImpl();
		ContenidoCriteria contenido= new ContenidoCriteria();
		contenido.setAceptarLista(true);
		contenido.setAceptarVideo(true);
		contenido.setAceptarUsuario(true);
		return dao.findByCriteria(connection, contenido, startIndex, count, idioma);
	}


	/**
	 * Crea el contenido que recibe como par�metro y lo devuelve con el campo ID cubierto.
	 * @param connection - Connection
	 * @param c - Contenido
	 * @return Contenido : Contenido creado (con ID)
	 */
	@Override
	public Contenido create (Connection connection, Contenido c) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Contenido= {} ", c);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Creamos el preparedstatement
			String queryString = "INSERT INTO contenido (nombre, fecha_alta, fecha_mod, autor_id_contenido, tipo, reproducciones) "
					+ " VALUES (?, ?, ?, ?, ?, ?) ";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);

			// Rellenamos el "preparedStatement"
			int i = 1;
			preparedStatement.setString(i++, c.getNombre());
			preparedStatement.setDate(i++, new java.sql.Date(c.getFechaAlta().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(c.getFechaMod().getTime()));
			if(c.getAutor() == null) { // INSERTAR USUARIOS: AUTOR=NULL
				preparedStatement.setNull(i++, Types.NULL);
			}
			else {  // INSERTAR VIDEOS Y LISTAS
				preparedStatement.setLong(i++, c.getAutor());
			}
			preparedStatement.setInt(i++, c.getTipo());
			preparedStatement.setInt(i++,0); //Al crear un contenido las reproducciones siempre ser�n 0
			//preparedStatement.setInt(i++, c.getReproducciones());

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Contenido'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Long pk = resultSet.getLong(1); 
				c.setId(pk);
			} else {
				logger.debug("No se ha recogido la clave del contenido {}", c );
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			return c;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public void update(Connection connection, Contenido contenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Contenido= {} ", contenido);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE Contenido " 
					);

			boolean first = true;

			if (contenido.getNombre()!=null) {
				addUpdate(queryString, first, " nombre = ? ");
				first = false;
			}

			if (contenido.getFechaMod()!=null) {
				addUpdate(queryString, first, " fecha_mod = ? ");
				first = false;
			}

			if (contenido.getAutor()!=null) {
				addUpdate(queryString, first, " autor_id_contenido = ? ");
				first = false;
			}

			if (contenido.getTipo()!=null) {
				addUpdate(queryString, first, " tipo = ? ");
				first = false;
			}
			
			if (contenido.getReproducciones()!=null) {
				addUpdate(queryString, first, " reproducciones = ? ");
				first = false;
			}			

			queryString.append("WHERE id_contenido = ?");
			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			if (contenido.getNombre()!=null)
				preparedStatement.setString(i++,contenido.getNombre());

			if (contenido.getFechaMod()!=null)
				preparedStatement.setDate(i++, new java.sql.Date(contenido.getFechaMod().getTime()));

			if (contenido.getAutor()!=null)
				preparedStatement.setLong(i++,contenido.getAutor());

			if (contenido.getTipo()!=null)
				preparedStatement.setLong(i++,contenido.getTipo());
			
			if (contenido.getReproducciones()!=null)
				preparedStatement.setInt(i++,contenido.getReproducciones());


			preparedStatement.setLong(i++, contenido.getId());

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(contenido.getId(), Contenido.class.getName()); //Esto ultimo pa mostrar o nome da clase??
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						contenido.getId() + "' in table 'Contenido'");
			}     

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}    
	}


	@Override
	public void delete(Connection connection, Long id)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", id);
		}

		deleteRelaciones(connection, id);
		deleteCategorias(connection, id);
		deleteEtiquetas(connection, id);
		deleteContenido(connection, id);
	}


	public void deleteContenido(Connection connection, Long id)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", id);
		}

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM CONTENIDO " 
							+ "WHERE id_contenido = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new DataException("Exception: No removed rows (Tabla CONTENIDO) Id:"+id);
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	/**
	 * Desvincula un Contenido de todas sus Etiquetas
	 * @param connection - Connection
	 * @param idContenido - Long
	 */
	public void deleteEtiquetas(Connection connection, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", idContenido);
		}

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM ETIQUETA_CONTENIDO " 
							+ "WHERE id_contenido = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	/**
	 * Desvincula un Contenido de su Categoria
	 * @param connection - Connection
	 * @param idContenido - Long
	 */
	public void deleteCategorias (Connection connection, Long id) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", id);
		}

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM Categoria_CONTENIDO " 
							+ "WHERE id_contenido = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	/**
	 * Desvincula un Contenido del resto de Contenidos
	 * @param connection - Connection
	 * @param idContenido - Long
	 */
	public void deleteRelaciones (Connection connection, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("Id= {} ", idContenido);
		}

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM Usuario_CONTENIDO " 
							+ "WHERE USUARIO_ID_CONTENIDO = ? OR CONTENIDO_ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idContenido);
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	/**
	 * Vincula un contenido a una Categoria
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @param idCategoria - Long
	 */
	@Override
	public void agignarCategoria(Connection connection, Long idContenido, Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("IdContenido= {} IdCategoria= {} ", idContenido, idCategoria);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " INSERT INTO categoria_contenido (id_categoria, id_contenido) "
					+ " VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idCategoria);
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("No se ha podido asignar la categor�a");
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	/**
	 * Desvincula un Contenido de una Categoria y lo vincula con otra.
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @param idCategoria - Long
	 */
	@Override
	public void modificarCategoria(Connection connection, Long idContenido, Long idCategoria) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("IdContenido= {} IdCategoria= {} ", idContenido, idCategoria);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE categoria_Contenido " 
					);

			boolean first = true;

			if (idContenido != null && idCategoria != null ) {
				addUpdate(queryString, first, " id_categoria = ? ");
				first = false;

			}

			queryString.append("WHERE id_contenido= ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;
			if (idContenido != null && idCategoria != null ) {
				preparedStatement.setLong(i++, idCategoria );
				preparedStatement.setLong(i++, idContenido );
			}

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la categor�a");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idContenido +" - "+idCategoria+ "' in table 'Categoria_Contenido'");
			}     

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	/**
	 * Vincula un contenido a una Etiqueta
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @param idEtiqueta - Long
	 */
	@Override
	public void asignarEtiqueta(Connection connection, Long idContenido, Long idEtiqueta) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("IdContenido= {} idEtiqueta= {} ", idContenido, idEtiqueta);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " INSERT INTO etiqueta_contenido (id_Etiqueta, id_contenido) "
					+ " VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idEtiqueta);
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("No se ha podido asignar la etiqueta");
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	/**
	 * Desvincula un contenido de una Etiqueta
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @param idEtiqueta - Long
	 */
	@Override
	public void eliminarEtiqueta(Connection connection, Long idContenido, Long idEtiqueta) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("IdContenido= {} idEtiqueta= {} ", idContenido, idEtiqueta);
		}

		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM ETIQUETA_CONTENIDO " 
							+ "WHERE id_contenido = ? and id_etiqueta = ?";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idContenido);
			preparedStatement.setLong(i++, idEtiqueta);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new DataException("Exception: No se ha eliminado la etiqueta");
			}

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}





	/**
	 * Comprueba si Existe una relacion entre un usuario y un contenido determinados.
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 * @return Boolean : True si existe la relacion. False en caso contrario.
	 */
	@Override
	public Boolean comprobarExistenciaRelacion(Connection connection, Long idUsuario, Long idContenido)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} ", idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT USUARIO_ID_CONTENIDO, CONTENIDO_ID_CONTENIDO " +
							"FROM USUARIO_CONTENIDO " +
							"WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idUsuario);
			preparedStatement.setLong(i++, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;			
			}
			return false;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}



	/**
	 * Inicializa una relacion entre dos contenidos (Usuario, Conteindo).
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 */
	@Override
	public void crearRelacion(Connection connection, Long idUsuario, Long idContenido) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} ", idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = " INSERT INTO USUARIO_CONTENIDO " +
					" (USUARIO_ID_CONTENIDO, CONTENIDO_ID_CONTENIDO, SIGUIENDO, DENUNCIADO, VALORACION, GUARDADO, COMENTARIO) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setLong(i++, idUsuario);
			preparedStatement.setLong(i++, idContenido);

			preparedStatement.setBoolean(i++, false);
			preparedStatement.setBoolean(i++, false);
			preparedStatement.setInt(i++, 0);
			preparedStatement.setBoolean(i++, false);
			preparedStatement.setNull(i++, Types.NULL);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("No se ha podido crear la relacion Usuario_Contenido");
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	/**
	 * Modifica la relacion SEGUIR entre un Usuario y un Contenido (Usuario o Lista).
	 * False para dejar de seguir. True para empezar a seguir.
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 * @param siguiendo - Boolean
	 */
	@Override
	public void seguirContenido(Connection connection, Long idUsuario, Long idContenido, Boolean siguiendo) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} Siguiendo{} ", idUsuario, idContenido, siguiendo);
		}

		if(!comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			crearRelacion(connection, idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);

			boolean first = true;
			addUpdate(queryString, first, " SIGUIENDO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;

			preparedStatement.setBoolean(i++, siguiendo );
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (seguir)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (seguir)'");
			}     
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}


	/**
	 * Modifica la relacion DENUNCIAR entre un Usuario y un Contenido (Usuario, Video, o Lista).
	 * Recibe un String con el motivo de la denuncia para CREAR la denuncia.
	 * Recibe null para CANCELAR la denuncia.
	 * 
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 * @param denunciado - String
	 */
	@Override
	public void denunciarContenido(Connection connection, Long idUsuario, Long idContenido, Boolean denunciado)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} Denunciado{} ", idUsuario, idContenido, denunciado);
		}

		if(!comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			crearRelacion(connection, idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);

			boolean first = true;
			addUpdate(queryString, first, " DENUNCIADO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;

			preparedStatement.setBoolean(i++, denunciado);

			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (denunciar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (denunciar)'");
			}     
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}



	/**
	 * Modifica la valoraci�n realizada por un Usuario a un Contenido.
	 * 
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 * @param Integer - valoracion
	 */
	@Override
	public void valorarContenido(Connection connection, Long idUsuario, Long idContenido, Integer valoracion)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} Valoracion{} ", idUsuario, idContenido, valoracion);
		}

		if(!comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			crearRelacion(connection, idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);

			boolean first = true;
			addUpdate(queryString, first, " VALORACION = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;

			preparedStatement.setInt(i++, valoracion );
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (valorar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (valorar)' ");
			}     
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	/**
	 * Modifica la relacion GUARDAR entre un Usuario y un Contenido (Video o Lista).
	 * False para Borrar. True para Guardar.
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 * @param guardado - Boolean
	 */
	@Override
	public void guardarContenido(Connection connection, Long idUsuario, Long idContenido, Boolean guardado)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} Guardado{} ", idUsuario, idContenido, guardado);
		}

		if(!comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			crearRelacion(connection, idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);

			boolean first = true;
			addUpdate(queryString, first, " GUARDADO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;

			preparedStatement.setBoolean(i++, guardado );
			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO  (guardar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (guardar)'");
			}     
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}


	}


	/**
	 * Modifica/Crea un comentario de un Usuario en un Contenido (Video, o Lista).
	 * Recibe un String con el COMENTARIO.
	 * Recibe null para Borrar el comentario.
	 * 
	 * @param connection - Connection
	 * @param idUsuario - Long
	 * @param idContenido - Long
	 * @param comentario - String
	 */

	@Override
	public void comentarContenido(Connection connection, Long idUsuario, Long idContenido, String comentario)
			throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idUsuario= {} idContenido= {} Comentario{} ", idUsuario, idContenido, comentario);
		}

		if(!comprobarExistenciaRelacion(connection, idUsuario, idContenido)) {
			crearRelacion(connection, idUsuario, idContenido);
		}

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE USUARIO_CONTENIDO " 
					);

			boolean first = true;
			addUpdate(queryString, first, " COMENTARIO = ? ");
			first = false;

			queryString.append("WHERE USUARIO_ID_CONTENIDO = ? and CONTENIDO_ID_CONTENIDO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());

			int i = 1;

			if(comentario == null) {
				preparedStatement.setNull(i++, Types.NULL);
			} else {
				preparedStatement.setString(i++, comentario );
			}

			preparedStatement.setLong(i++, idUsuario );
			preparedStatement.setLong(i++, idContenido );

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("No se ha podido modificar la tabla USUARIO_CONTENIDO (comentar)");
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						idUsuario +" - "+idContenido+ "' in table 'USUARIO_CONTENIDO (comentar)'");
			}     
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}



	/**
	 * Recupera la Categoria a la que pertenece un Contenido
	 * 
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @param idioma - String ( ESP: Castellano ; ENG: Ingl�s)
	 * @return Categoria
	 */
	@Override
	public Categoria verCategoria(Connection connection, Long idContenido, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idContenido= {} idioma= {} ", idContenido, idioma);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					" SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA " + 
							" FROM CATEGORIA C INNER JOIN CATEGORIA_CONTENIDO CC ON (C.ID_CATEGORIA = CC.ID_CATEGORIA) " +
							" INNER JOIN CATEGORIA_IDIOMA CI ON (C.ID_CATEGORIA = CI.ID_CATEGORIA ) " +
							" WHERE CC.ID_CONTENIDO = ? AND CI.ID_IDIOMA = ?";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setLong(i++, idContenido);
			preparedStatement.setString(i++, idioma);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			Categoria c = new Categoria();
			if (resultSet.next()) {
				int j=1;
				c.setIdCategoria(resultSet.getLong(j++));
				c.setNombreCategoria(resultSet.getString(j++));			
			} else {
				throw new DataException("\nContenido with id " +idContenido+ "not found\n");     ///////////////////////////////////////////
			}
			return c;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	
	}

	/**
	 * Recupera las Etiquetas asignadas a un Contenido
	 * 
	 * @param connection - Connection
	 * @param idContenido - Long
	 * @param idioma - String ( ESP: Castellano ; ENG: Ingl�s)
	 * @return Lista de Etiquetas
	 */
	@Override
	public List<Etiqueta> verEtiquetas(Connection connection, Long idEtiqueta, String idioma) throws DataException {

		if(logger.isDebugEnabled()) {
			logger.debug ("idEtiqueta= {} idioma= {} ", idEtiqueta, idioma);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT E.ID_ETIQUETA, EI.NOMBRE_ETIQUETA " + 
							" FROM ETIQUETA E INNER JOIN ETIQUETA_CONTENIDO EC ON (E.ID_ETIQUETA = EC.ID_ETIQUETA) " +
							" INNER JOIN ETIQUETA_IDIOMA EI ON (E.ID_ETIQUETA = EI.ID_ETIQUETA ) " +
					" WHERE EC.ID_CONTENIDO = ? AND EI.ID_IDIOMA = ?");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setLong(i++, idEtiqueta );
			preparedStatement.setString(i++, idioma );

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}

			resultSet = preparedStatement.executeQuery();

			List<Etiqueta> results = new ArrayList<Etiqueta>();    

			Etiqueta e = null;
			//int currentCount = 0;

			//if ((startIndex >=1) && resultSet.absolute(startIndex)) {
			if(resultSet.next()) {
				do {
					e = etiquetaDao.loadNext(resultSet);
					results.add(e);               	
					//currentCount++;                	
				} while (/*(currentCount < count) && */ resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	/*
	public Results<String> cargarComentarios(Connection connection, Long idContenido, int startIndex, int count) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					"SELECT COMENTARIO FROM USUARIO_CONTENIDO "
					+" WHERE ID_CONTENIDO = ? ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			List<String> page = new ArrayList<String>();
			int currentCount = 0;
			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					page.add(resultSet.getString(1));
					currentCount++;
				} while ((currentCount < count) &&  resultSet.next()) ;
			}
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<String> results = new Results<String>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}
	*/
	

	@Override
	public Results<Contenido> cargarSeguidos(Connection connection, Long idContenido, int startIndex, int count)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+" FROM CONTENIDO C LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO) "
					/*		+" AND (UC.USUARIO_ID_CONTENIDO= ? ) "*/
					+" WHERE UC.USUARIO_ID_CONTENIDO = ? AND UC.SIGUIENDO= 'TRUE' ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			Contenido contenido = new Contenido();
			List<Contenido> page = new ArrayList<Contenido>();
			int currentCount = 0;
			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					contenido = loadNext(connection, resultSet);
					page.add(contenido);
					currentCount++;
				} while ((currentCount < count) &&  resultSet.next()) ;
			}
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<Contenido> results = new Results<Contenido>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}

	@Override
	public Results<Contenido> cargarGuardados(Connection connection, Long idContenido, int startIndex, int count)
			throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT C.ID_CONTENIDO, C.NOMBRE, C.FECHA_ALTA, C.FECHA_MOD, C.AUTOR_ID_CONTENIDO, C.TIPO, C.REPRODUCCIONES, AVG(UC.VALORACION) "
					+" FROM CONTENIDO C LEFT OUTER JOIN USUARIO_CONTENIDO UC ON (C.ID_CONTENIDO = UC.CONTENIDO_ID_CONTENIDO) "
					/*		+" AND (UC.USUARIO_ID_CONTENIDO= ? ) "*/
					+" WHERE UC.USUARIO_ID_CONTENIDO = ? AND UC.GUARDADO= 'TRUE' ");

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			preparedStatement.setLong(1, idContenido);

			if(logger.isDebugEnabled()) {
				logger.debug("QUERY= {}",preparedStatement);
			}
			resultSet = preparedStatement.executeQuery();
			
			Contenido contenido = new Contenido();
			List<Contenido> page = new ArrayList<Contenido>();
			int currentCount = 0;
			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					contenido = loadNext(connection, resultSet);
					page.add(contenido);
					currentCount++;
				} while ((currentCount < count) &&  resultSet.next()) ;
			}
			int totalRows = JDBCUtils.getTotalRows(resultSet);
			Results<Contenido> results = new Results<Contenido>(page, startIndex, totalRows);
			return results;

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
		return null;
	}
	

	protected static Contenido loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

		int i = 1;
		Long idContenido = resultSet.getLong(i++);
		String nombre = resultSet.getString(i++);
		Date fechaAlta =  resultSet.getDate(i++);
		Date fechaMod =  resultSet.getDate(i++);
		Long autor = null;
		if(resultSet.getObject(i)==null) {
			autor = (Long) resultSet.getObject(i++);

		} else {
			autor = resultSet.getLong(i++);
		}
		Integer tipo = resultSet.getInt(i++);
		Integer reproducciones = resultSet.getInt(i++);
		Double valoracion = resultSet.getDouble(i++);

		Contenido c = new Contenido();
		c.setId(idContenido);
		c.setNombre(nombre);
		c.setFechaAlta(fechaAlta);
		c.setFechaMod(fechaMod);
		c.setAutor(autor);
		c.setTipo(tipo);
		c.setReproducciones(reproducciones);
		c.setValoracion(valoracion);

		return c;
	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}

	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}
	
	private void addHaving(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" HAVING ": " AND ").append(clause);
	}

}