package ar.utn.dds.copiame.persist;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ar.utn.dds.copiame.domain.Revisor;

public class RevisorRepository {

	private EntityManager entityManager ;

	public RevisorRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	

	public void save(Revisor revisor) {
		this.entityManager.persist(revisor);
		
	}

	public Revisor findById(Long id) {		
		return this.entityManager.find(Revisor.class, id);
	}
	
	
	public List<Revisor> all() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Revisor> criteriaQuery = criteriaBuilder.createQuery(Revisor.class);
		Root<Revisor> root = criteriaQuery.from(Revisor.class);

		criteriaQuery.select(root);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
}
