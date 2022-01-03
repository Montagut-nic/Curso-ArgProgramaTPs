package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.TipoAtraccion;

public class TestAtraccion {

	Atraccion atraccion1;
	Atraccion atraccion2;
	Atraccion atraccion3;
	
	@Before
	public void setUp() {
		atraccion1 = new Atraccion("Arenas", 6, 7d, 9, TipoAtraccion.AVENTURA);
		atraccion2 = new Atraccion("Playa", 10, 6d, 11, TipoAtraccion.PAISAJE);
		atraccion3 = new Atraccion("Cielo", 4, 5d, 8, TipoAtraccion.PAISAJE);
	}
	
	@Test
	public void testConstructor() {
		Atraccion atraccion4 = new Atraccion ("Lluvia",12,0.8d,15,TipoAtraccion.PAISAJE);
		assertNotNull(atraccion4);
	}
	
	@Test
	public void obtenerCostoAtraccion() {
		atraccion3.setPrecio(20);
		assertEquals(20,atraccion3.getPrecio(),0);
	}
	
	@Test
	public void obtenerTiempoAtraccion() {
		atraccion3.setTiempo(8d);
		assertEquals(8d,atraccion3.getTiempo(),0);
	}
	
	@Test
	public void obtenerCupoAtraccion() {
		atraccion2.setCupo(3);
		assertEquals(3,atraccion2.getCupo(),0);
	}
	
	@Test
	public void hayCupoAtraccion() {
		atraccion2.setCupo(3);
		assertTrue(atraccion2.hayCupo());
		atraccion2.setCupo(0);
		assertFalse(atraccion2.hayCupo());
	}
	
	
	@Test
	public void obtenerNombreAtraccion() {
		atraccion1.setNombre("Nuevo Cielo");
		assertEquals("Nuevo Cielo",atraccion1.getNombre());
	}
	
	@Test
	public void obtenerTipoAtraccion() {
		assertEquals(TipoAtraccion.AVENTURA,atraccion1.getTipo());
	}
	
	
}
