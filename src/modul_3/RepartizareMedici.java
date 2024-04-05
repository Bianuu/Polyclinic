package modul_3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import db.Interogari;
import db.MySQL_Connect;
import modele.CabinetMedical;
import modele.Echipament;
import modele.Programare;
import modele.ServiciuMedicalSpecial;

public class RepartizareMedici {

	private MySQL_Connect conexiuneDB;
	private Connection con;
	private Statement selectStatement = null;
	private ResultSet rs = null;
	private List<Programare> programari;
	private List<CabinetMedical> cabinete;

	public void getServiciiMedicaleForProgramari() {
		programari = getProgramari();
		Map<Integer, Set<ServiciuMedicalSpecial>> mapServicii = new HashMap<>();
		try {
			conexiuneDB = new MySQL_Connect();
			con = conexiuneDB.getConnection();
			selectStatement = con.createStatement();
			selectStatement.execute(Interogari.SERVICII_MEDICALE_ZIUA_CURENTA);
			rs = selectStatement.getResultSet();
			while (rs.next()) {
				int idProgramare = rs.getInt("id_programare");
				int idServiciuMedical = rs.getInt("id_serviciu_medical");
				String denumireServiciu = rs.getString("denumire");
				String specializarea = rs.getString("specializarea");
				double pret = rs.getDouble("pret");
				int durata = rs.getInt("durata");
				int idMedic = rs.getInt("id_medic");
				int idEchipament = rs.getInt("id_echipament");
				ServiciuMedicalSpecial sm = new ServiciuMedicalSpecial(idServiciuMedical, denumireServiciu,
						specializarea, pret, durata, idMedic);
				String numeEchipament = rs.getString("nume");
				Echipament echipament = new Echipament(idEchipament, numeEchipament);
				sm.getEchipamenteNecesare().add(echipament);
				if (!mapServicii.containsKey(idProgramare)) {
					Set<ServiciuMedicalSpecial> serviciiMedicale = new HashSet<>();
					serviciiMedicale.add(sm);
					mapServicii.put(idProgramare, serviciiMedicale);
				} else {
					Set<ServiciuMedicalSpecial> serviciiMedicaleSet = mapServicii.get(idProgramare);
					serviciiMedicaleSet.add(sm);
					mapServicii.put(idProgramare, serviciiMedicaleSet);
				}
			}
			con.close();
			for (Programare programare : programari) {
				Set<ServiciuMedicalSpecial> servicii = mapServicii.get(programare.getIdProgramare());
				programare.setServiciiMedicale(servicii);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public List<Programare> getProgramari() {
		programari = new ArrayList<>();
		try {
			conexiuneDB = new MySQL_Connect();
			con = conexiuneDB.getConnection();
			selectStatement = con.createStatement();
			selectStatement.execute(Interogari.PROGRAMARI_ZIUA_CURENTA);
			rs = selectStatement.getResultSet();

			while (rs.next()) {
				int idProgramare = rs.getInt("id_programare");
				int idMedic = rs.getInt("id_medic");
				Programare programare = new Programare(idProgramare, idMedic);
				programari.add(programare);
			}
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return programari;
	}

	public void getCabinete() {
		cabinete = new ArrayList<>();
		Map<Integer, CabinetMedical> mapCabinete = new HashMap<>();
		try {
			conexiuneDB = new MySQL_Connect();
			con = conexiuneDB.getConnection();
			selectStatement = con.createStatement();
			selectStatement.execute(Interogari.ECHIPAMENTE_CABINETE);
			rs = selectStatement.getResultSet();

			while (rs.next()) {
				int idCabinet = rs.getInt("id_cabinet");
				int idEchipament = rs.getInt("id_echipament");
				String numeEchipament = rs.getString("nume");
				Echipament echipament = new Echipament(idEchipament, numeEchipament);
				if (mapCabinete.containsKey(idCabinet)) {
					CabinetMedical cm = mapCabinete.get(idCabinet);
					cm.getEchipamenteDisponibile().add(echipament);
					mapCabinete.put(idCabinet, cm);
				} else {
					Set<Echipament> echipamente = new HashSet<>();
					echipamente.add(echipament);
					CabinetMedical cm = new CabinetMedical(idCabinet, echipamente);
					mapCabinete.put(idCabinet, cm);
				}
			}
			cabinete.addAll(mapCabinete.values());
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void repartizare() {
		getServiciiMedicaleForProgramari();
		getCabinete();

		for (Programare programare : programari) {
			Set<ServiciuMedicalSpecial> serviciiProgramari = programare.getServiciiMedicale();
			for (ServiciuMedicalSpecial sm : serviciiProgramari) {
				Set<Echipament> echipamenteNecesare = sm.getEchipamenteNecesare();
				if (echipamenteNecesare.isEmpty()) {
					programare.setIdCabinet(cabinete.get(0).getIdCabinet());
				} else {
					Echipament echipament = echipamenteNecesare.stream().findFirst().get();
					for (CabinetMedical cabinet : cabinete) {
						Set<Echipament> echipamenteDisponibile = cabinet.getEchipamenteDisponibile();
						if (echipamenteDisponibile.stream().filter((echipamentD) -> echipamentD.equals(echipament))
								.findAny().isPresent()) {
							programare.setIdCabinet(cabinet.getIdCabinet());
							break;
						}
					}
					break;
				}
			}
		}

		for (Programare programare : programari) {
			int idUnitate = getUnitateForCabinet(programare.getIdCabinet());
			programare.setIdUnitate(idUnitate);
		}

		for (Programare programare : programari) {
			updateCabinetForProgramare(programare);
		}
	}

	public int getUnitateForCabinet(int idCabinet) {
		int idUnitate = 0;
		try {
			conexiuneDB = new MySQL_Connect();
			con = conexiuneDB.getConnection();
			selectStatement = con.createStatement();
			String sql = "SELECT id_unitate FROM cabinet_medical WHERE id_cabinet='" + idCabinet + "'";
			selectStatement.execute(sql);
			rs = selectStatement.getResultSet();
			if (rs.next()) {
				idUnitate = rs.getInt("id_unitate");
			}
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return idUnitate;
	}

	public void updateCabinetForProgramare(Programare programare) {
		try {
			conexiuneDB = new MySQL_Connect();
			con = conexiuneDB.getConnection();
			selectStatement = con.createStatement();
			System.out.println("IdCabinet  " + programare.getIdCabinet());
			System.out.println("IdUnitate  " + programare.getIdUnitate());
			String sql = "UPDATE programare SET id_cabinet='" + programare.getIdCabinet() + "', id_unitate='"
					+ programare.getIdUnitate() + "' WHERE id_programare='" + programare.getIdProgramare() + "'";
			selectStatement.execute(sql);
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
