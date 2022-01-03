package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.TipoAtraccion;


public class AtraccionDAOImpl implements AtraccionDAO {

	@Override
	public List<Atraccion> findAll() {
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM ATRACCION";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Atraccion> atracciones = new LinkedList<Atraccion>();
			while (resultados.next()) {
				atracciones.add(toAtraccion(resultados));
			}

			return atracciones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}


	@Override
	public int insert(Atraccion t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "INSERT INTO ATRACCION (NOMBRE, TIPO, COSTO, TIEMPO, CUPO) VALUES (?, ?, ?, ?, ?)";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			statement.setString(2, t.getTipo().name());
			statement.setInt(3, t.getPrecio());
			statement.setDouble(4, t.getTiempo());
			statement.setInt(5, t.getCupo());
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
	public int update(Atraccion t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "UPDATE ATRACCION SET TIPO = ?, COSTO = ?, TIEMPO = ?, CUPO = ? WHERE NOMBRE = ?";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getTipo().name());
			statement.setInt(2, t.getPrecio());
			statement.setDouble(3, t.getTiempo());
			statement.setInt(4, t.getCupo());
			statement.setString(5, t.getNombre());
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
	public int delete(Atraccion t) throws SQLException
	{
		Connection conn = ConnectionProvider.getConnection();
	try {
		String sql = "DELETE FROM ATRACCION WHERE NOMBRE = ?";
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
	public Atraccion findByNombreAtraccion(String nombre) {
		Atraccion atraccion = null;
		try {
			String sql = "SELECT * FROM ATRACCION WHERE NOMBRE = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();
			
			if (resultados.next()) {
				atraccion = toAtraccion(resultados);
			}
		}catch(Exception e){
			throw new MissingDataException(e);
		}
		return atraccion;
	}

	private Atraccion toAtraccion(ResultSet resultados) throws SQLException {
		TipoAtraccion tipoAtraccion = null;
		switch(resultados.getString(2)) {
			case "AVENTURA":
				tipoAtraccion=TipoAtraccion.AVENTURA;
				break;
			case "PAISAJE":
				tipoAtraccion=TipoAtraccion.PAISAJE;
				break;
			case "DEGUSTACION":
				tipoAtraccion=TipoAtraccion.DEGUSTACION;
				break;
		}
		return new Atraccion(resultados.getString(1), resultados.getInt(3),resultados.getDouble(4), resultados.getInt(5),tipoAtraccion);
	}
	
}
