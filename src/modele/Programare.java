package modele;

import java.util.HashSet;
import java.util.Set;

public class Programare {
	private final int idProgramare;
	private final int idMedic;
	private Set<ServiciuMedicalSpecial> serviciiMedicale;
	private int idCabinet;
	private int idUnitate;

	public Programare(int idProgramare, int idMedic) {
		super();
		this.idProgramare = idProgramare;
		this.idMedic = idMedic;
		this.serviciiMedicale = new HashSet<>();
	}

	public Programare(int idProgramare, int idMedic, Set<ServiciuMedicalSpecial> serviciiMedicale) {
		super();
		this.idProgramare = idProgramare;
		this.idMedic = idMedic;
		this.serviciiMedicale = serviciiMedicale;
	}

	public int getIdProgramare() {
		return idProgramare;
	}

	public int getIdMedic() {
		return idMedic;
	}

	public Set<ServiciuMedicalSpecial> getServiciiMedicale() {
		return serviciiMedicale;
	}

	public void setServiciiMedicale(Set<ServiciuMedicalSpecial> serviciiMedicale) {
		if (serviciiMedicale != null) {
			this.serviciiMedicale.addAll(serviciiMedicale);
		} else
			this.serviciiMedicale = new HashSet<>();
	}

	public void setIdCabinet(int idCabinet) {
		this.idCabinet = idCabinet;
	}

	public void setIdUnitate(int idUnitate) {
		this.idUnitate = idUnitate;
	}

	public int getIdCabinet() {
		return idCabinet;
	}

	public int getIdUnitate() {
		return idUnitate;
	}

}
