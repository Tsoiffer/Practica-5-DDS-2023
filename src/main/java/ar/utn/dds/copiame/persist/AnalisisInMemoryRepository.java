package ar.utn.dds.copiame.persist;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ar.utn.dds.copiame.domain.AnalisisDeCopia;

public class AnalisisInMemoryRepository implements AnalsisRepository {

private Map<String,AnalisisDeCopia> lista;
	
	public AnalisisInMemoryRepository() {
		super();
		this.lista = new HashMap<String, AnalisisDeCopia>();
	}

	public void save(AnalisisDeCopia analisis) {
		String key = UUID.randomUUID().toString().substring(0, 5);
		analisis.setId(key);
		this.lista.put(key, analisis);
	}
	
	public AnalisisDeCopia findById(String id) {
		return this.lista.get(id);
	}
	
	public Collection<AnalisisDeCopia> all() {
		return this.lista.values();
	}
	
}
