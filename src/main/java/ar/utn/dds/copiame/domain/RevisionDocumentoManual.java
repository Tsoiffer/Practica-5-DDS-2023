package ar.utn.dds.copiame.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RevisionDocumentoManual extends RevisionDocumento {

	
	
	public RevisionDocumentoManual() {
		super();
	}

	@ManyToOne
	private Revisor revisor;
	
	public RevisionDocumentoManual(ParDocumentos par,Revisor revisor) {
		super(par);
		this.revisor = revisor;
	}

	public Revisor getRevisor() {
		return revisor;
	}

	public void setRevisor(Revisor revisor) {
		this.revisor = revisor;
	}
	
	
	
}
