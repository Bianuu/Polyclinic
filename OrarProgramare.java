package modele;

import java.sql.Date;
import java.sql.Time;

public class OrarProgramare {
	private int idProgramare;
	private Date dataProgramarii;
	private Time timpProgramare;
	private int durata;

	public OrarProgramare(int idProgramare, Date dataProgramarii, Time timpProgramare, int durata) {
		super();
		this.idProgramare = idProgramare;
		this.dataProgramarii = dataProgramarii;
		this.timpProgramare = timpProgramare;
		this.durata = durata;
	}

	public int getIdProgramare() {
		return idProgramare;
	}

	public void setIdProgramare(int idProgramare) {
		this.idProgramare = idProgramare;
	}

	public Date getDataProgramarii() {
		return dataProgramarii;
	}

	public void setDataProgramarii(Date dataProgramarii) {
		this.dataProgramarii = dataProgramarii;
	}

	public Time getTimpProgramare() {
		return timpProgramare;
	}

	public void setTimpProgramare(Time timpProgramare) {
		this.timpProgramare = timpProgramare;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

}
