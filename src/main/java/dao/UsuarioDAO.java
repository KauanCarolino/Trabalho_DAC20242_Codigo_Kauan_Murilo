package dao;

import java.util.List;

import javax.persistence.*;

import entidade.*;
import util.*;

public class UsuarioDAO {
	public static void salvar(Usuario usuario) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Usuario> Listar(){
		EntityManager em = JPAUtil.criarEntityManager();
		Query q = em.createQuery("select u from Usuario u");
		List<Usuario> lista = q.getResultList();
		em.close();
		return lista;
	}
	
    public Usuario buscarPorLogin(String login) {
		EntityManager em = JPAUtil.criarEntityManager();
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
        query.setParameter("login", login);
        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }
}
