package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import jdk.internal.org.objectweb.asm.Type;
import model.Promo;
import model.PromoAbsoluta;
import model.PromoAxB;
import model.PromoPorcentual;
import model.TipoAtraccion;

public class PromoDAOImpl implements PromoDAO {

	@Override
	public List<Promo> findAll() {
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM PROMOCION";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Promo> promos = new LinkedList<Promo>();
			while (resultados.next()){
				Promo promoTest =toPromo(resultados);
				promos.add(promoTest);
			}
			return promos;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}


	private Promo toPromo(ResultSet resultados) {
		TipoAtraccion tipoAtraccion = null;
		Promo promocion = null;
		try {
			switch(resultados.getString(3)) {
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
			switch(resultados.getString(2)) {
				case "Absoluta":
					promocion = new PromoAbsoluta(tipoAtraccion, resultados.getString(1), resultados.getString(4), resultados.getInt(7));
					break;
				case "AxB":
					promocion = new PromoAxB(tipoAtraccion, resultados.getString(1), resultados.getString(4), resultados.getString(6));
					break;
				case "Porcentual":
					promocion = new PromoPorcentual(tipoAtraccion, resultados.getString(1), resultados.getString(4), resultados.getInt(5));
					break;
			}
		}catch(Exception e) {
			throw new MissingDataException(e);	
		}
		return promocion;
	}


	@Override
	public int insert(Promo t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "INSERT INTO PROMOCION (NOMBRE_PACK, TIPO_PROMO, TIPO_ATRACCION, ATRACCIONES, PORC_DESC, AXB_GRATIS, ABS_COSTO) VALUES (?, ?, ?, ?, ?, ?, ?)";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			statement.setString(2, t.getTipoPromo());
			statement.setString(3, t.getTipo().name());
			statement.setString(4, t.nombresAtracciones());
			switch(t.getTipoPromo()) {
			case "Absoluta":
				statement.setNull(5,Type.INT);
				statement.setString(6,null);
				statement.setInt(7,t.getPrecio());
				break;
			case "AxB":
				statement.setNull(5,Type.INT);
				statement.setString(6,((PromoAxB)t).getAtraccionGratuita());
				statement.setInt(7,Type.INT);
				break;
			case "Porcentual":
				statement.setNull(5,t.getPrecio());
				statement.setString(6,null);
				statement.setInt(7,Type.INT);
				break;
			}
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
	public int update(Promo t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "UPDATE PROMOCION SET TIPO_PROMO = ?, TIPO_ATRACCION = ?, ATRACCIONES = ?, PORC_DESC = ?, AXB_GRATIS = ?, ABS_COSTO = ? WHERE NOMBRE_PACK = ?";
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(7, t.getNombre());
			statement.setString(1, t.getTipoPromo());
			statement.setString(2, t.getTipo().name());
			statement.setString(3, t.nombresAtracciones());
			switch(t.getTipoPromo()) {
			case "Absoluta":
				statement.setNull(4,Type.INT);
				statement.setString(5,null);
				statement.setInt(6,t.getPrecio());
				break;
			case "AxB":
				statement.setNull(4,Type.INT);
				statement.setString(5,((PromoAxB)t).getAtraccionGratuita());
				statement.setInt(6,Type.INT);
				break;
			case "Porcentual":
				statement.setNull(4,t.getPrecio());
				statement.setString(5,null);
				statement.setInt(6,Type.INT);
				break;
			}
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
	public int delete(Promo t) throws SQLException {
		Connection conn = ConnectionProvider.getConnection();
		try {
			String sql = "DELETE FROM PROMOCION WHERE NOMBRE_PACK = ?";
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
	public Promo findByNombre(String nombre) {
		Promo promocion = null;
		try {
			String sql = "SELECT * FROM PROMOCION WHERE NOMBRE_PACK = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();
			
			if (resultados.next()) {
				promocion = toPromo(resultados);
			}
		}catch(Exception e){
			throw new MissingDataException(e);
		}
		return promocion;
	}

}
