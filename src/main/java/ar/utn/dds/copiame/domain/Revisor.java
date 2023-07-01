package ar.utn.dds.copiame.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Revisor {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nombre;
	
	
	@OneToMany(mappedBy = "revisor")	
	private List<RevisionDocumentoManual> revisar;
	
	
	

	public Revisor() {
		super();
		this.revisar = new ArrayList<RevisionDocumentoManual>();
	}

	
	
	public long getId() {
		return id;
	}



	protected void setId(long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RevisionDocumentoManual> getRevisar() {
		return revisar;
	}

	public void setRevisar(List<RevisionDocumentoManual> revisar) {
		this.revisar = revisar;
	}

	public void addRevision(RevisionDocumentoManual rev) {
		this.revisar.add(rev);
	}

}
