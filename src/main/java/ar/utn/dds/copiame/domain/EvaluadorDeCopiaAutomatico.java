package ar.utn.dds.copiame.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class EvaluadorDeCopiaAutomatico extends EvaluadorDeCopia {

	@Override
	public void procesar(List<ParDocumentos> pares) {
		
		for (ParDocumentos parDocumentos : pares) {
			RevisionDocumento rd = new RevisionDocumento(parDocumentos);
			rd.setValorCopia(parDocumentos.distancia());
			parDocumentos.addRevision(rd);
		}

	}

}
