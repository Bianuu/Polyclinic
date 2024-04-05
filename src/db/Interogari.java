package db;

public final class Interogari {
	public static final String FIND_UTILIZATORI = "SELECT * FROM utilizator";
	public static final String FIND_UTILIZATOR_BY_USERNAME = "SELECT * FROM utilizator WHERE user=?";
	public static final String FIND_ANGAJAT_BY_ID_UTILIZATOR = "SELECT * FROM angajat A JOIN utilizator U ON A.id_utilizator =  U.id_utilizator WHERE U.id_utilizator=?";
	public static final String DELETE_UTILIZATOR = "DELETE FROM utilizator WHERE id_utilizator=?";
	public static final String FIND_CONCEDII = "SELECT U.nume, U.prenume, C.data_incepere, C.data_incheiere FROM concediu C JOIN utilizator U ON C.id_utilizator = U.id_utilizator";
	public static final String FIND_CONCEDII_UTILIZATOR_NPF = "SELECT U.nume, U.prenume, U.functie, C.data_incepere as 'Data inceput', C.data_incheiere as 'Data incheiere' "
			+ "FROM utilizator U JOIN concediu C on C.id_utilizator = U.id_utilizator WHERE U.nume=? AND U.prenume=? and U.functie=?";
	public static final String FIND_UTILIZATOR_NPF = "SELECT nume,prenume,functie FROM utilizator";
	public static final String FIND_UTILIZATORI_FARA_ROL_ADMIN = "SELECT id_utilizator,nume,prenume,CNP,adresa,nr_telefon,email,IBAN,nr_contract,data_angajarii,functie,salariu_negociat,nr_ore from utilizator WHERE functie!='Admin' AND functie!='Super Admin'";
	public static final String FIND_UTILIZATORI_FARA_USER_SI_PASS = "SELECT id_utilizator,nume,prenume,CNP,adresa,nr_telefon,email,IBAN,nr_contract,data_angajarii,functie,salariu_negociat,nr_ore from utilizator WHERE functie!='Super Admin'";
	public static final String FIND_ORAR_UNITATE_MEDICALA = "SELECT * from program_functionare P";
	public static final String SALARII_LUNA_CURENTA = "SELECT * FROM salariu WHERE an=YEAR(NOW()) AND luna=MONTH(NOW())";
	public static final String SALARII = "SELECT * FROM salariu";
	public static final String CASTIGURI_LUNA_CURENTA = "SELECT * FROM bon_fiscal B JOIN programare P on P.id_programare = B.id_programare WHERE YEAR(P.zi)=YEAR(NOW()) AND MONTH(P.zi)=MONTH(NOW())";
	public static final String PROGRAMARI_ZIUA_CURENTA = "SELECT * FROM programare WHERE zi=CURDATE()";
	public static final String SERVICII_MEDICALE_ZIUA_CURENTA = "SELECT P.id_programare, SM.*, E.* FROM serviciu_medical SM \n"
			+ "JOIN programare_servicii_medicale PSM ON PSM.id_servicii = SM.id_serviciu_medical\n"
			+ "JOIN programare P ON P.id_programare = PSM.id_programare \n"
			+ "JOIN serviciu_echipament SE ON SE.id_serviciu = SM.id_serviciu_medical\n"
			+ "JOIN echipament E ON SE.id_echipament = E.id_echipament\n" + "WHERE P.zi = CURDATE();";
	public static final String ECHIPAMENTE_CABINETE = "SELECT E.* , C.id_cabinet FROM echipament E\n"
			+ "LEFT JOIN cabinet_echipament CE ON CE.id_echipament = E.id_echipament\n"
			+ "LEFT JOIN cabinet_medical C ON C.id_cabinet = CE.id_cabinet";
}
