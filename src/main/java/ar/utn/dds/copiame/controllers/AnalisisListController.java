package ar.utn.dds.copiame.controllers;

import ar.utn.dds.copiame.persist.AnalisisJPARepository;
import ar.utn.dds.copiame.persist.AnalsisRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManagerFactory;


public class AnalisisListController implements Handler {
	private EntityManagerFactory entityManagerFactory;

	public AnalisisListController(
			EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		AnalisisJPARepository repo = new AnalisisJPARepository(
				entityManagerFactory.createEntityManager());
		ctx.json(repo.all());
	}
}

