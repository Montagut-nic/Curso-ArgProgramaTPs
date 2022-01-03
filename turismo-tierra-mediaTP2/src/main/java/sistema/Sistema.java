package sistema;

import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import dao.*;
import model.*;
import jdbc.*;

public class Sistema {
	
	public static void leerBaseDeDatos(UsuarioDAO usuarioDao, PromoDAO promoDao, AtraccionDAO atraccionDao) {
		
		atracciones = atraccionDao.findAll();
		promociones = promoDao.findAll();
		usuarios = usuarioDao.findAll();
		
		for(Usuario user : usuarios) {
			try {
				user.setComprasRealizadas(usuarioDao.getComprasRealizadas(user),atracciones,promociones);
			}catch(Exception e) {
				throw new MissingDataException(e);
			}
		}

	}
	
	public static void escribirBaseDeDatos(UsuarioDAO usuarioDao, AtraccionDAO atraccionDao) throws SQLException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			for(Usuario usuario : usuarios) {
				usuarioDao.update(usuario);
				usuarioDao.saveItinerario(usuario);
			}
			for(Atraccion atraccion : atracciones) {
				atraccionDao.update(atraccion);
			}
		}catch(SQLException e) {
			conn.rollback();
		} finally {
			conn.commit();
		}
	}
	
	
	private static List<Usuario> usuarios;
	private static List<Atraccion> atracciones;
	private static List<Promo> promociones;
	private static List<Producto> productos;

	private static int dato;
	
	public static void main(String[] args) throws IOException{
		UsuarioDAO usuarioDao= DAOFactory.getUsuarioDAO();
		AtraccionDAO atraccionDao = DAOFactory.getAtraccionDAO();
		PromoDAO promoDao = DAOFactory.getPromoDAO();
		 Scanner sc = new Scanner(System.in);
		 
		 usuarios = new LinkedList<Usuario>();
		 atracciones = new LinkedList<Atraccion>();
		 promociones = new LinkedList<Promo>();
		 leerBaseDeDatos(usuarioDao, promoDao, atraccionDao);
		 
		 for(Promo promo : promociones) {
			//Establece las horas totales de una promo (suma tiempos de cada atraccion)
			 promo.establecerHsPromo(promo, atracciones);
			//Establece el precio total de una promo (axb,absoluta o porcentual) (suma precios de cada atraccion)
			 promo.establecerPrecioPromo(promo, atracciones);
		 }
		 //Interaccion con cada usuario
		 for (Usuario usuario : usuarios) 
		 {
			 //ordeno segun la preferencia del user
			 Collections.sort(promociones,new ComparadorDeProductos(usuario.getAtraccionPreferida()));
			 Collections.sort(atracciones,new ComparadorDeProductos(usuario.getAtraccionPreferida()));
			 
			 productos = new LinkedList<Producto>(promociones);//lista de promos
			 productos.addAll(atracciones);//lista de promos + lista de atracciones --> lista de productos(ordenada)
			 
			 usuario.datosUsuario(); //muestro al user con sus datos iniciales
			 usuario.sugerencia(productos); //genero sugeencia para usuario (itinerario)
			 
			 for (Producto producto : productos) 
			 {
				 //si el user tiene monedas y tiempo y si hay cupos discponibles
				 if ( usuario.tieneMonedas() && usuario.tieneTiempo() && 
				     ((producto instanceof Promo)? ((Promo)producto).hayCupo(atracciones, ((Promo)producto).getNombres_atracciones()) : ((Atraccion) producto).hayCupo())) {
					 //si las monedas y el tiempo que tiene el user alcanza para comprar y que el user no haya comprado el prod anteriormente
					 if ( usuario.puedeComprar(producto) ) {
						 System.out.println( (producto instanceof Promo) ? usuario.getNombre() + " Desea comprar el " + producto.getNombre()+ " ( " + (((Promo) producto).nombresAtracciones()) + " ) de " + producto.getTipo() + ", Promo "+((Promo)producto).getTipoPromo()+"?": 
							 											   usuario.getNombre() + " Desea comprar la atraccion " + producto.getNombre() + " de tipo " + producto.getTipo() + "?");
						 System.out.println("1 - SI              2 - NO");
						 dato = sc.nextInt();
						 if ( dato == 1) {
							 if (producto instanceof Promo) {
								 usuario.comprar(atracciones, (Promo) producto); 
							 } else {
								 usuario.comprar((Atraccion) producto);
							 }
						 }
						 usuario.datosUsuario();
					 }
				 }
			 }
			 System.out.println("###############################################################################");
			 System.out.println(usuario.getNombre() + " total de hs a consumir " + usuario.getHsAConsumir() + " total de dinero a gastar " + usuario.getGasto());
			 System.out.println("###############################################################################\n");
			 System.out.println("\n");
		 }
		 //escribo el itinerario final de todos los user, modifico el cupo de las atracciones en la base de datos
		 try {
			 escribirBaseDeDatos(usuarioDao, atraccionDao);
		 }catch(Exception e) {
			 throw new MissingDataException (e);
		 }finally {
			 sc.close();
		 }
	}
}
