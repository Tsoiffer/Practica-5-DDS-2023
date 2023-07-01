package ar.utn.dds.copiame.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ResultadoLote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "resultado_id")
	private List<ParDocumentos> posiblesCopias;

	public ResultadoLote() {
		super();
		this.posiblesCopias = new ArrayList<ParDocumentos>();
	}

	protected long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	protected void setPosiblesCopias(List<ParDocumentos> posiblesCopias) {
		this.posiblesCopias = posiblesCopias;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<ParDocumentos> getPosiblesCopias() {
		// encapsulamos la coleccion para que no puedan manipularla sin usar agregarPar
		return new ArrayList<ParDocumentos>(posiblesCopias);
	}

	public void agregarPar(ParDocumentos par) {
		this.posiblesCopias.add(par);
	}

}
