package modele;

public class ServiciuMedical {
	private final int idServiciu;
	private final String denumire;
	private final String specialitate;
	private final double pret;
	private final int durata;
	private final int idMedic;

	public ServiciuMedical(int idServiciu, String denumire, String specialitate, double pret, int durata, int idMedic) {
		super();
		this.idServiciu = idServiciu;
		this.denumire = denumire;
		this.specialitate = specialitate;
		this.pret = pret;
		this.durata = durata;
		this.idMedic = idMedic;
	}

	public int getIdServiciu() {
		return idServiciu;
	}

	public String getDenumire() {
		return denumire;
	}

	public String getSpecialitate() {
		return specialitate;
	}

	public double getPret() {
		return pret;
	}

	public int getDurata() {
		return durata;
	}

	public int getIdMedic() {
		return idMedic;
	}

}
