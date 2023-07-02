package ar.utn.dds.copiame.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.paukov.combinatorics3.Generator;

import ar.utn.dds.copiame.persist.Lote;

@Entity
public class AnalisisDeCopia {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long pk;
	
	private String id;
	private float umbral;
	@Transient
	private Lote lote;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "analisis_id")
	private List<ParDocumentos> pares;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "analisis_id")
	private List<EvaluadorDeCopia> evaluadores;
	@OneToOne(cascade = CascadeType.ALL)
	private ResultadoLote rl;
	
	
	
	protected AnalisisDeCopia() {
		super();
	}



	public AnalisisDeCopia(float umbral, Lote lote) {
		super();
		this.umbral = umbral;
		this.lote = lote;
		
		this.evaluadores = new ArrayList<EvaluadorDeCopia>();
		
	}
	
	
	
	public long getPk() {
		return pk;
	}



	public void setPk(long pk) {
		this.pk = pk;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public float getUmbral() {
		return umbral;
	}
	
	public void setUmbral(float umbral) {
		this.umbral = umbral;
	}
	
	public Lote getLote() {
		return lote;
	}
	
	public void addEvaluador(EvaluadorDeCopia eval) {
		this.evaluadores.add(eval);
	}

	public void crearPares() {
// Genero todos los pares de documentos Posibles
		this.pares = Generator.combination(
						this.lote.getDocumentos())
				.simple(2)
				.stream()
				.map(
						t-> new ParDocumentos(
								t.get(0),t.get(1)) )
				.collect(Collectors.toList());
	}
	//---------------------------
	public void procesar() {
// Armo el resultado procesando cada par
		this.rl = new ResultadoLote();
		rl.setFechaInicio(LocalDateTime.now());
		for (EvaluadorDeCopia evaluador :
				this.evaluadores) {
			evaluador.procesar(pares);
		}
	}


	public ResultadoLote resultado() {
		for (ParDocumentos parDocumentos : pares) {
			if(parDocumentos.esCopia(this.umbral)) {
				rl.agregarPar(parDocumentos);	
			}			
		}
		rl.setFechaFin(LocalDateTime.now());
		return rl;
	}



	public Boolean finalizado() {
		
		return this.pares.stream().allMatch( p-> p.finalizado());
	}
	

}
