package modele;

import java.util.HashSet;
import java.util.Set;

public class CabinetMedical {
	private final int idCabinet;
//	private final int idUnitate;
	private final Set<Echipament> echipamenteDisponibile = new HashSet<>();

	public CabinetMedical(int idCabinet, Set<Echipament> echipamenteDisponibile) {
		super();
		this.idCabinet = idCabinet;
//		this.idUnitate = idUnitate;
		this.echipamenteDisponibile.addAll(echipamenteDisponibile);
	}

	public int getIdCabinet() {
		return idCabinet;
	}

//	public int getIdUnitate() {
//		return idUnitate;
//	}

	public Set<Echipament> getEchipamenteDisponibile() {
		return echipamenteDisponibile;
	}

}
