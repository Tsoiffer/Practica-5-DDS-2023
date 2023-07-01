package ar.utn.dds.copiame.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ar.utn.dds.copiame.domain.Revisor;
import ar.utn.dds.copiame.persist.RevisorRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RevisorPostController implements Handler {

	private EntityManagerFactory entityManagerFactory;
	
	
	public RevisorPostController(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		EntityManager em = entityManagerFactory.createEntityManager();
		RevisorRepository repo = new RevisorRepository(em);
		Revisor revisor = ctx.bodyAsClass(Revisor.class);
		
		em.getTransaction().begin();
		repo.save(revisor);
		em.getTransaction().commit();
		em.close();
		ctx.json(revisor);

	}

}
