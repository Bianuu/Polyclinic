package modele;

import java.util.HashSet;
import java.util.Set;

public class ServiciuMedicalSpecial extends ServiciuMedical {
	private Set<Echipament> echipamenteNecesare = new HashSet<>();

	public ServiciuMedicalSpecial(int idServiciu, String denumire, String specialitate, double pret, int durata,
			int idMedic) {
		super(idServiciu, denumire, specialitate, pret, durata, idMedic);
		this.echipamenteNecesare = new HashSet<>();
	}

	public ServiciuMedicalSpecial(int idServiciu, String denumire, String specialitate, double pret, int durata,
			int idMedic, Set<Echipament> echipamenteNecesare) {
		super(idServiciu, denumire, specialitate, pret, durata, idMedic);
		this.echipamenteNecesare.addAll(echipamenteNecesare);
	}

	public Set<Echipament> getEchipamenteNecesare() {
		return echipamenteNecesare;
	}

}
