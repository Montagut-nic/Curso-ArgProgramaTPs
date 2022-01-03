package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import model.TipoAtraccion;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO{

	@Override
	public List<Usuario> findAll() {
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM USUARIO";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> usuarios = new LinkedList<Usuario>();
			while (resultados.next()) {
				usuarios.add(toUsuario(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Usuario toUsuario(ResultSet resultados) throws SQLException {
		TipoAtraccion tipoFavorito = null;
		switch(resultados.getString(2)) {
			case "AVENTURA":
				tipoFavorito=TipoAtraccion.AVENTURA;
				break;
			case "PAISAJE":
				tipoFavorito=TipoAtraccion.PAISAJE;
				break;
			case "DEGUSTACION":
				tipoFavorito=TipoAtraccion.DEGUSTACION;
				break;
		}
		return new Usuario(resultados.getString(1), tipoFavorito, resultados.getInt(3),resultados.getDouble(4));
	}

	@Override
	public int insert(Usuario t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "INSERT INTO USUARIO (NOMBRE, TIPO_FAV, PRESUPUESTO, TIEMPO_DISP) VALUES (?, ?, ?, ?)";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			statement.setString(2, t.getAtraccionPreferida().name());
			statement.setInt(3, t.getMonedas());
			statement.setDouble(4, t.getTiempoDisponible());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			conn.rollback();
			throw new MissingDataException(e);
		}finally {
			conn.commit();
		}
	}

	@Override
	public int update(Usuario t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "UPDATE USUARIO SET TIPO_FAV = ?, PRESUPUESTO = ?, TIEMPO_DISP = ? WHERE NOMBRE = ?";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getAtraccionPreferida().name());
			statement.setInt(2, t.getMonedas());
			statement.setDouble(3, t.getTiempoDisponible());
			statement.setString(4, t.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			conn.rollback();
			throw new MissingDataException(e);
		}finally {
			conn.commit();
		}
	}
	
	@Override
	public int delete(Usuario t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "DELETE FROM USUARIO WHERE NOMBRE = ?";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			conn.rollback();
			throw new MissingDataException(e);
		}finally {
			conn.commit();
		}
	}

	@Override
	public Usuario findByNombre(String nombre) {
		Usuario user = null;
		try {
			String sql = "SELECT * FROM USUARIO WHERE NOMBRE = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();
			
			if (resultados.next()) {
				user = toUsuario(resultados);
			}
		}catch(Exception e){
			throw new MissingDataException(e);
		}
		return user;
	}

	@Override
	public int saveItinerario(Usuario t) throws SQLException {
		Connection conn = null;
		int rows=0;
		try {
			conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO ITINERARIO (U_NOMBRE, COMPRAS, GASTO_TOTAL, TIEMPO_TOTAL) VALUES (?, ?, ?, ?)";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			statement.setString(2, t.getStringCompras());
			statement.setInt(3, t.getGasto());
			statement.setDouble(4, t.getHsAConsumir());
			rows = statement.executeUpdate();
		} catch (Exception e) {
			conn.rollback();
			throw new MissingDataException(e);
		}finally {
			conn.commit();
		}
		return rows;
	}

	@Override
	public String getComprasRealizadas(Usuario u){
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT COMPRAS FROM ITINERARIO WHERE U_NOMBRE=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u.getNombre());
			ResultSet resultados = statement.executeQuery();

			return resultados.toString();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
