package test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.PromoPorcentual;
import model.TipoAtraccion;
import model.Producto;

public class TestProducto {

	Atraccion atraccion1;
	Atraccion atraccion2;
	Atraccion atraccion3;
	PromoPorcentual promocion1;
	List<Producto> listaProductos;
	
	@Before
	public void setUp() {
		atraccion1 = new Atraccion("Arenas", 6, 7d, 9, TipoAtraccion.AVENTURA);
		atraccion2 = new Atraccion("Playa", 10, 6d, 11, TipoAtraccion.PAISAJE);
		atraccion3 = new Atraccion("Cielo", 4, 5d, 8, TipoAtraccion.PAISAJE);
		promocion1 = new PromoPorcentual(TipoAtraccion.PAISAJE,"Promo porcentual paisaje","Playa-Cielo", 10);
		listaProductos = new LinkedList<Producto>();
	}
	
	@Test
	public void testContieneProducto() {
		listaProductos.add(promocion1);
		assertTrue(atraccion3.contieneAtraccion(listaProductos));
		assertTrue(promocion1.contieneAtraccion(listaProductos));
		assertFalse(atraccion1.contieneAtraccion(listaProductos));
		listaProductos.add(atraccion1);
		assertTrue(atraccion1.contieneAtraccion(listaProductos));
	}

}
