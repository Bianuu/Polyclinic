package modele;

import java.sql.Date;

public class Utilizator {
	private final int idUtilizator;
	private final String nume;
	private final String prenume;
	private final String CNP;
	private final String adresa;
	private final String nrTelefon;
	private final String email;
	private final String IBAN;
	private final int nrContract;
	private final Date dataAngajarii;
	private final String functie;
	private final Double salariuNegociat;
	private final int nrOre;
	private final String username;

	public Utilizator(int idUtilizator, String nume, String prenume, String CNP, String adresa, String nrTelefon,
			String email, String IBAN, int nrContract, Date dataAngajarii, String functie, Double salariuNegociat,
			int nrOre, String username) {
		super();
		this.idUtilizator = idUtilizator;
		this.nume = nume;
		this.prenume = prenume;
		this.CNP = CNP;
		this.adresa = adresa;
		this.nrTelefon = nrTelefon;
		this.email = email;
		this.IBAN = IBAN;
		this.nrContract = nrContract;
		this.dataAngajarii = dataAngajarii;
		this.functie = functie;
		this.salariuNegociat = salariuNegociat;
		this.nrOre = nrOre;
		this.username = username;
	}

	public int getIdUtilizator() {
		return idUtilizator;
	}

	public String getNume() {
		return nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public String getCNP() {
		return CNP;
	}

	public String getAdresa() {
		return adresa;
	}

	public String getNrTelefon() {
		return nrTelefon;
	}

	public String getEmail() {
		return email;
	}

	public String getIBAN() {
		return IBAN;
	}

	public int getNrContract() {
		return nrContract;
	}

	public Date getDataAngajarii() {
		return dataAngajarii;
	}

	public String getFunctie() {
		return functie;
	}

	public Double getSalariuNegociat() {
		return salariuNegociat;
	}

	public int getNrOre() {
		return nrOre;
	}

	public String getUsername() {
		return username;
	}

}
