package model;

import java.util.LinkedList;
import java.util.List;

public class Usuario {
	private String nombre;
	private TipoAtraccion atraccionPreferida;//preferencia
	private Integer monedas;//presupuesto
	private Double tiempoDisponible;
	private List<Producto> compras;
	private Integer gasto;
	private Double hsAConsumir;
	
	public Usuario(String nombre, TipoAtraccion atraccionPreferida, Integer monedas,Double tiempoDisponible) {
		this.nombre = nombre;
		this.monedas = monedas;
		this.tiempoDisponible = tiempoDisponible;
		this.atraccionPreferida = atraccionPreferida;
		compras = new LinkedList<Producto>();
		gasto = 0;
		hsAConsumir = 0d;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoAtraccion getAtraccionPreferida() {
		return atraccionPreferida;
	}

	public Integer getMonedas() {
		return monedas;
	}

	public Double getTiempoDisponible() {
		return tiempoDisponible;
	}

	public Double getHsAConsumir() {
		return hsAConsumir;
	}
	
	public Integer getGasto() {
		return gasto;
	}

	public List<Producto> getCompras() {
		return compras;
	}
	
	public void setComprasRealizadas(String comprasStr, List<Atraccion> atracciones, List<Promo> promociones) {
		for (String compra:comprasStr.split("/")) {
			for(Atraccion atraccion:atracciones) {
				if(compra.equals(atraccion.getNombre())) {
					this.compras.add(atraccion);
				}else 
				{
					for(String elemento:compra.split(":")) {
						for(Promo promo:promociones) {
							if(elemento.equals(promo.getNombre())) 
							{
								this.compras.add(promo);
							}
						}
					}
				}
			}
		}
	}
	
	public String getStringCompras() {
		String comprasStr = new String();
		for(Producto compra : compras) {
			if(compra instanceof Promo) {
				comprasStr+=(compra.getNombre()+":"+((Promo)compra).nombresAtracciones()+"/");
			}
			if(compra instanceof Atraccion) {
				comprasStr+=(compra.getNombre()+"/");
			}
		}
		return comprasStr;
	}
	
	public Boolean puedeComprar(Producto producto) {
		return ((this.getMonedas() >= producto.getPrecio()) && (this.getTiempoDisponible() >= producto.getTiempo())) && (!producto.contieneAtraccion(compras));
	}

	public Boolean tieneTiempo() {
		return tiempoDisponible > 0;
	}
	
	public Boolean tieneMonedas() {
		return monedas > 0;
	}
	//compra las atracciones de una promo
	public void comprar(List<Atraccion> atracciones, Promo promo) {
		this.monedas -= promo.precio(atracciones);
		this.tiempoDisponible -= promo.getTiempo();
		this.gasto +=  promo.precio(atracciones); 
		this.hsAConsumir += promo.getTiempo();
		promo.setCupos(atracciones,promo.getNombres_atracciones());
		compras.add(promo);
	}
	//compra una sola atraccion
	public void comprar(Atraccion atraccion) {
		this.monedas -= atraccion.getPrecio();
		this.tiempoDisponible -= atraccion.getTiempo();
		this.gasto +=  atraccion.getPrecio();
		this.hsAConsumir += atraccion.getTiempo();
		atraccion.setCupo(atraccion.getCupo() - 1);
		compras.add(atraccion);
	}
	
	public void sugerencia(List<Producto> productos) {
		System.out.println("------------------------------ LISTA SUGERIDA PARA "+nombre+"---------------------------------------------");
		for( Producto producto : productos) {
			System.out.println(producto);
		}
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}

	public void datosUsuario() {
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("Nombre : " + nombre + ", Atraccion Preferida : " + atraccionPreferida + ", Monedas : " + monedas
				+ ", Tiempo : " + tiempoDisponible);
		System.out.println("----------------------------------------------------------------------------");
	}

	@Override
	public String toString() {
		return "Nombre : " + nombre + ", Atraccion Preferida : " + atraccionPreferida + ", Monedas Disponibles : " + monedas
				+ ", Tiempo Disponible : " + tiempoDisponible + "\nCompras Realizadas : " + compras.toString() + "\nGasto Total : " + gasto
				+ ", Horas a Consumir : " + hsAConsumir;
	}
}
