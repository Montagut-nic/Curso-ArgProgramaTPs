package test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.PromoPorcentual;
import model.TipoAtraccion;

public class TestPromo {

	Atraccion atraccion1;
	Atraccion atraccion2;
	Atraccion atraccion3;
	List<Atraccion> atracciones;
	@Before
	public void setUp() {
		atraccion1 = new Atraccion("Arenas", 6, 7d, 9, TipoAtraccion.AVENTURA);
		atraccion2 = new Atraccion("Playa", 10, 6d, 11, TipoAtraccion.PAISAJE);
		atraccion3 = new Atraccion("Cielo", 4, 5d, 8, TipoAtraccion.PAISAJE);
		
		atracciones = new LinkedList<Atraccion>();
	}
	
	
	@Test
	public void obtenerTipoAtraccion() {
		
		atracciones.add(atraccion1);
		atracciones.add(atraccion2);
		atracciones.add(atraccion3);
		PromoPorcentual promo = new PromoPorcentual(TipoAtraccion.PAISAJE, "Porcentual","Playa-Cielo",20);
		
		promo.establecerHsPromo(promo, atracciones);
		promo.establecerPrecioPromo(promo, atracciones);
		
		assertEquals(TipoAtraccion.PAISAJE,promo.getTipo());
		
	}
}

