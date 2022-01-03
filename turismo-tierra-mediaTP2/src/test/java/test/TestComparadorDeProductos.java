package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.ComparadorDeProductos;
import model.TipoAtraccion;

public class TestComparadorDeProductos {

	Atraccion atraccion1;
	Atraccion atraccion2;
	Atraccion atraccion3;
	ComparadorDeProductos comparador;
	
	@Before
	public void setUp() {
		atraccion1 = new Atraccion("Arenas", 6, 5d, 9, TipoAtraccion.AVENTURA);
		atraccion2 = new Atraccion("Playa", 10, 6d, 11, TipoAtraccion.PAISAJE);
		atraccion3 = new Atraccion("Cielo", 4, 6d, 8, TipoAtraccion.PAISAJE);
	}
	
	@Test
	public void testComparadorPorPreferencia() {
		comparador = new ComparadorDeProductos(TipoAtraccion.AVENTURA);
		int comparacion=comparador.compare(atraccion1, atraccion2);
		assertEquals(-1, comparacion,0);
		comparador = new ComparadorDeProductos(TipoAtraccion.PAISAJE);
		comparacion=comparador.compare(atraccion1, atraccion2);
		assertEquals(1, comparacion,0);
	}
	
	@Test
	public void testComparadorPorCosto() {
		comparador = new ComparadorDeProductos(TipoAtraccion.PAISAJE);
		int comparacion=comparador.compare(atraccion2, atraccion3);
		assertEquals(-1, comparacion,0);
		comparacion=comparador.compare(atraccion3, atraccion2);
		assertEquals(1, comparacion,0);
	}
	
	@Test
	public void testComparadorPorTiempo() {
		comparador = new ComparadorDeProductos(TipoAtraccion.PAISAJE);
		atraccion3.setPrecio(10);
		atraccion3.setTiempo(3.5);
		int comparacion=comparador.compare(atraccion2, atraccion3);
		assertEquals(-1, comparacion,0);
		comparacion=comparador.compare(atraccion3, atraccion2);
		assertEquals(1, comparacion,0);
	}

}
