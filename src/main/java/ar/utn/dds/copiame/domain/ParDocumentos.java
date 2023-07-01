package ar.utn.dds.copiame.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ParDocumentos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Documento documento1;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Documento documento2;
	@OneToMany(mappedBy = "par",cascade = CascadeType.ALL)
	private List<RevisionDocumento> revisiones;

	protected ParDocumentos() {
		super();
	}

	public ParDocumentos(Documento documento1, Documento documento2) {
		super();
		this.documento1 = documento1;
		this.documento2 = documento2;
		this.revisiones = new ArrayList<RevisionDocumento>();
	}

	public Documento getDocumento1() {
		return documento1;
	}

	public Documento getDocumento2() {
		return documento2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<RevisionDocumento> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(List<RevisionDocumento> revisiones) {
		this.revisiones = revisiones;
	}

	public void setDocumento1(Documento documento1) {
		this.documento1 = documento1;
	}

	public void setDocumento2(Documento documento2) {
		this.documento2 = documento2;
	}

	public float distancia() {
		return this.documento1.distancia(documento2);
	}

	public float puntaje() {
		Double sum = this.revisiones.stream().mapToDouble(x -> x.getValorCopia()).sum();
		return sum.floatValue() / this.revisiones.size();
	}

	public Boolean esCopia(float umbral) {
		return this.puntaje() < umbral;
	}

	public void addRevision(RevisionDocumento rev) {
		this.revisiones.add(rev);
	}

	public boolean finalizado() {
		return this.revisiones.stream().allMatch(x -> x.finalizado());
	}

}
