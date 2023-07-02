package ar.utn.dds.copiame.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.utn.dds.copiame.domain.AnalisisDeCopia;
import ar.utn.dds.copiame.domain.EvaluadorDeCopiaAutomatico;
import ar.utn.dds.copiame.domain.EvaluadorDeCopiaManual;
import ar.utn.dds.copiame.domain.Revisor;
import ar.utn.dds.copiame.persist.AnalisisJPARepository;
import ar.utn.dds.copiame.persist.AnalsisRepository;
import ar.utn.dds.copiame.persist.Lote;
import ar.utn.dds.copiame.persist.UnzipUtility;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AnalisisAddController implements Handler {

	private EntityManagerFactory entityManagerFactory;
	public AnalisisAddController(
			EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public void handle(Context ctx) throws Exception {
		EntityManager entityManager =
				entityManagerFactory.createEntityManager();
		AnalisisJPARepository repo = new AnalisisJPARepository(
				entityManager);
// Proceso de parámetros de entrada
		String destDirectory = "/tmp/unlugar";

		UnzipUtility.unzip(ctx.uploadedFile("file").content()
				,destDirectory);

// Armado del Análisis
		Lote lote = new Lote(destDirectory);
		lote.validar();
		lote.cargar();
		float umbral = 0.5f;
		AnalisisDeCopia analisis = new AnalisisDeCopia(umbral, lote);
		analisis.addEvaluador(new EvaluadorDeCopiaAutomatico());

// genero los pares de documentos del lote
// YA NO LOS PROCESO
		analisis.crearPares();

// Guardado
		entityManager.getTransaction().begin();
		repo.save(analisis);
		entityManager.getTransaction().commit();
		entityManager.close();
// Armado de la respuesta
		Map<String,String> rta = new HashMap<String, String>();
		rta.put("analisis", analisis.getId());
		rta.put("terminado", analisis.finalizado().toString());
		ctx.json(rta);
	}
}
