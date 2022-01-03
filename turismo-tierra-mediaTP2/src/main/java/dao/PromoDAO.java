package dao;

import model.Promo;


public interface PromoDAO extends GenericDAO<Promo>{

	public abstract Promo findByNombre(String nombre);
}
