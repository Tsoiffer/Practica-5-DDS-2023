package ar.utn.dds.copiame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.utn.dds.copiame.domain.AnalisisDeCopia;
import ar.utn.dds.copiame.domain.Documento;
import ar.utn.dds.copiame.domain.EvaluadorDeCopiaAutomatico;
import ar.utn.dds.copiame.domain.EvaluadorDeCopiaManual;
import ar.utn.dds.copiame.domain.ResultadoLote;
import ar.utn.dds.copiame.domain.RevisionDocumento;
import ar.utn.dds.copiame.domain.Revisor;
import ar.utn.dds.copiame.persist.AnalisisJPARepository;
import ar.utn.dds.copiame.persist.DocumentoRepository;
import ar.utn.dds.copiame.persist.Lote;
import ar.utn.dds.copiame.persist.RevisorRepository;

public class PersistenceIT {

	static EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	DocumentoRepository docRepo;
	RevisorRepository revisorRepo;

	@BeforeAll
	public static void setUpClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("compiamedb");
	}

	@BeforeEach
	public void setup() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
		docRepo = new DocumentoRepository(entityManager);
		revisorRepo = new RevisorRepository(entityManager);
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		entityManager.close();
	}

	@Test
	public void testConectar() {
		// vacío, para ver que levante el ORM
	}

	@Test
	public void testGuardarYRecuperarDoc() throws Exception {
		Documento doc1 = new Documento("pepe", "cosas");
		entityManager.getTransaction().begin();
		entityManager.persist(doc1);
		entityManager.getTransaction().commit();
		entityManager.close();

		entityManager = entityManagerFactory.createEntityManager();

		Documento doc2 = entityManager.find(Documento.class, doc1.getId());

		assertEquals(doc1.getContenido(), doc2.getContenido()); // también puede redefinir el equals

	}

	@Test
	public void testGuardarYRecuperarAnalisis() throws Exception {
		
		// Pre condiciones: se supone que el revisor esta dado de alta ANTES de cargar el lote
		Revisor revisor = new Revisor();

		// Guardamos los documentos
		entityManager.getTransaction().begin();		
		revisorRepo.save(revisor);
		entityManager.getTransaction().commit();
		entityManager.close();

		// Notar que volvemos a inicializar la persistencia

		entityManager = entityManagerFactory.createEntityManager();
		docRepo = new DocumentoRepository(entityManager);
		revisorRepo = new RevisorRepository(entityManager);
		
		List<Revisor> revisores = revisorRepo.all();
		
		Lote lote = new Lote("src/test/resources/lote1");
		lote.validar();
		lote.cargar();

		float umbral = 0.4f;
		AnalisisDeCopia analisis = new AnalisisDeCopia(umbral, lote);
		analisis.setId(UUID.randomUUID().toString());
		analisis.addEvaluador(new EvaluadorDeCopiaAutomatico());
		
		EvaluadorDeCopiaManual eval = new EvaluadorDeCopiaManual(revisores, 1.0);
		analisis.addEvaluador(eval);

		// Ejecución
		analisis.procesar();

		entityManager.getTransaction().begin();
		entityManager.persist(analisis);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		
		entityManager = entityManagerFactory.createEntityManager();		
		revisorRepo = new RevisorRepository(entityManager);		
		entityManager.getTransaction().begin();
		
		
		Revisor revisorX = revisorRepo.findById(revisor.getId());
		// Marco manualmente el valor de copia en un valor alto
		// 1 --> no se copiaron  |  0 se copiaron y los docs son identicos
		for (RevisionDocumento revision : revisorX.getRevisar()) {
			revision.setValorCopia(0.9f);
		} 
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		
		entityManager = entityManagerFactory.createEntityManager();	
		AnalisisJPARepository analisisRepo = new AnalisisJPARepository(entityManager);
		entityManager.getTransaction().begin();
		
		// Notar que VOLVEMOS a traer el análisis
		analisis =  analisisRepo.findById(analisis.getId());		
		ResultadoLote resultado = analisis.resultado();
		entityManager.getTransaction().commit();
		
		
		// Chequeo
		assertTrue(analisis.finalizado());
		assertEquals(0, resultado.getPosiblesCopias().size(),
				"La revisión manual cambio el resultado de la posible copia, con lo que no se tiene que detectar copia alguna"); 
				
		
	}

}
