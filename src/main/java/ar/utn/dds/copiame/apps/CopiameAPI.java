package ar.utn.dds.copiame.apps;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ar.utn.dds.copiame.controllers.AnalisisAddController;
import ar.utn.dds.copiame.controllers.AnalisisListController;
import ar.utn.dds.copiame.controllers.RevisorGetController;
import ar.utn.dds.copiame.controllers.RevisorPostController;
import ar.utn.dds.copiame.persist.AnalisisInMemoryRepository;
import ar.utn.dds.copiame.persist.AnalsisRepository;
import io.javalin.Javalin;

public class CopiameAPI {

	public static EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		startEntityManagerFactory();
		Integer port = Integer.parseInt(System.getProperty("PORT", "8080"));

		Javalin app = Javalin.create().start(port);
		AnalsisRepository repo = new AnalisisInMemoryRepository();

		app.get("/analisis", new AnalisisListController(repo));
		app.post("/analisis", new AnalisisAddController(repo));

		app.get("/revisor/{id}", new RevisorGetController(entityManagerFactory));
		app.post("/revisor/", new RevisorPostController(entityManagerFactory));
		
		//app.get("/revisor/{id}/revision", new RevisorRevisionesListController(entityManagerFactory));
		//app.post("/revisor/{id}/revision", new RevisorAddRevisionController(entityManagerFactory));
		// app.get("/revisor/{id}/revision/{rev}/file/{fileid}", new
		// RevisorRevisionFilesController(repo));

	}

	public static void startEntityManagerFactory() {
		// https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();

		String[] keys = new String[] { "javax.persistence.jdbc.url", "javax.persistence.jdbc.user",
				"javax.persistence.jdbc.password", "javax.persistence.jdbc.driver", "hibernate.hbm2ddl.auto",
				"hibernate.connection.pool_size", "hibernate.show_sql" };

		for (String key : keys) {
			if (env.containsKey(key)) {

				String value = env.get(key);
				configOverrides.put(key, value);

			}
		}

		entityManagerFactory = Persistence.createEntityManagerFactory("db", configOverrides);

	}

}
