package modele;

import java.util.Objects;

public class Echipament {
	private final int idEchipament;
	private final String numeEchipament;

	public Echipament(int idEchipament, String numeEchipament) {
		super();
		this.idEchipament = idEchipament;
		this.numeEchipament = numeEchipament;
	}

	public int getIdEchipament() {
		return idEchipament;
	}

	public String getNumeEchipament() {
		return numeEchipament;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEchipament, numeEchipament);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Echipament other = (Echipament) obj;
		return idEchipament == other.idEchipament && Objects.equals(numeEchipament, other.numeEchipament);
	}

}
