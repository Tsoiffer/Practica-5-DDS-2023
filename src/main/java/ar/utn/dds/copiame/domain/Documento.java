package ar.utn.dds.copiame.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.text.similarity.LevenshteinDistance;

@Entity
public class Documento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String autor;
	private String contenido;
		
	
	
	protected Documento() {
		super();
	}

	public Documento(String autor, String contenido) {
		super();
		this.autor = autor;
		this.contenido = contenido;
	}

	
	
	
	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	protected void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAutor() {
		return autor;
	}
	
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public Float distancia(Documento otroDoc) {
		/* La distancia de Levenshtein mide la cantidad de operaciones 
		 * (inserción, eliminación o sustitución de un carácter) necesarias para transformar un texto en otro
		 */
		
		// Mientras más distintos los textos, más alta es la distancia
		Integer rawDist = LevenshteinDistance.getDefaultInstance().apply(this.contenido, otroDoc.getContenido());
				
		// Normalizamos por el tamaño de la distancia por el tamaño del texto mas largo 
		Integer contentSize = Math.max(this.contenido.length(), otroDoc.getContenido().length());
		
		
		return  rawDist * 1.0f / contentSize;
		
	}
	
}
