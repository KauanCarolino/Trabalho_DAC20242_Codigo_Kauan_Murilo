package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidade.Campeonato;
import util.JPAUtil;

public class CampeonatoDAO {
	public static void salvar(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(campeonato);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void editar(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(campeonato);
		em.getTransaction().commit();
		em.close();
	}
	
	public static Campeonato achar(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		Campeonato campeonato = em.find(Campeonato.class, id);
		em.close();
		return campeonato;
	}
	
	public static void Excluir(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		campeonato = em.find(Campeonato.class, campeonato.getId());
		em.remove(campeonato);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Campeonato> Listar(){
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select c from Campeonato c");
		List<Campeonato> lista = q.getResultList();
		em.close();
		return lista;
	}
}
