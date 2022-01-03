package dao;

import java.sql.SQLException;

import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario>{

	public abstract Usuario findByNombre(String nombre);
	public abstract int saveItinerario(Usuario u) throws SQLException;
	public abstract String getComprasRealizadas(Usuario u);
}
