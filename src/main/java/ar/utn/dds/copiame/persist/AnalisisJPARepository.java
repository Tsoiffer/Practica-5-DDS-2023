package ar.utn.dds.copiame.persist;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ar.utn.dds.copiame.domain.AnalisisDeCopia;

public class AnalisisJPARepository implements AnalsisRepository {

	
	private EntityManager entityManager ;

	public AnalisisJPARepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	
	@Override
	public void save(AnalisisDeCopia analisis) {
		String key = UUID.randomUUID().toString().substring(0, 5);
		analisis.setId(key);
		this.entityManager.persist(analisis);
		
	}

	@Override
	public AnalisisDeCopia findById(String id) {		
		// Notar que esto no es la PK, queda poco consistente respecto al resto de las clases
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AnalisisDeCopia> criteriaQuery = criteriaBuilder.createQuery(AnalisisDeCopia.class);
		Root<AnalisisDeCopia> root = criteriaQuery.from(AnalisisDeCopia.class);

		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));

		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	

	@Override
	public Collection<AnalisisDeCopia> all() {
		return null;
	}

}
