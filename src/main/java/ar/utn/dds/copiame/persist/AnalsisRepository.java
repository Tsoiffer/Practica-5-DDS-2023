package ar.utn.dds.copiame.persist;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ar.utn.dds.copiame.domain.AnalisisDeCopia;

public interface AnalsisRepository {
	
	public void save(AnalisisDeCopia analisis) ;
	
	public AnalisisDeCopia findById(String id) ;
	
	public Collection<AnalisisDeCopia> all() ;
	
}
