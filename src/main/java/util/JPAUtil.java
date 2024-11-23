package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("DACAV3");

	public static EntityManager criarEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void main(String[] args) {
	    EntityManager entityManager = null;
	    try {
	        entityManager = criarEntityManager();
	        System.out.println("Conexão bem-sucedida!");
	    } catch (Exception e) {
	        System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
	    } finally {
	        if (entityManager != null && entityManager.isOpen()) {
	            entityManager.close();
	        }
	    }
	}
}
