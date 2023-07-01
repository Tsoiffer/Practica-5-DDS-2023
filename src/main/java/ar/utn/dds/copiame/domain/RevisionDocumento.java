package ar.utn.dds.copiame.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RevisionDocumento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private ParDocumentos par;
	private LocalDateTime fecha;
	
	@Enumerated(EnumType.STRING)
	private RevisionEstado estado;
	private Float valorCopia;

	protected RevisionDocumento() {
		super();
	}

	public RevisionDocumento(ParDocumentos parDocumentos) {
		super();
		this.estado = RevisionEstado.Pendiente;
		this.fecha = LocalDateTime.now();
		this.par = parDocumentos;
	}

	public ParDocumentos getPar() {
		return par;
	}

	public void setPar(ParDocumentos par) {
		this.par = par;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public RevisionEstado getEstado() {
		return estado;
	}

	public void setEstado(RevisionEstado estado) {
		this.estado = estado;
	}

	public float getValorCopia() {
		return valorCopia;
	}

	public void setValorCopia(float valorCopia) {
		this.valorCopia = valorCopia;
	}

	public boolean finalizado() {
		return this.valorCopia != null;
	}

	protected long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	protected void setValorCopia(Float valorCopia) {
		this.valorCopia = valorCopia;
	}
	
	

}
