package modul_3;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mysql.cj.jdbc.CallableStatement;

import db.MySQL_Connect;
import modele.Utilizator;
import net.proteanit.sql.DbUtils;

public class Meniu_Medic extends JPanel {
	private final JTable table;
	// Conexiune Baza de date
	private MySQL_Connect conexiuneDB;
	private Connection con;
	Statement selectStatement = null, selectStatement2 = null;
	private CallableStatement stmt = null;
	ResultSet rs = null, rs2 = null, rs3 = null;
	private final JTextField idraport;
	private final JTextField nume_p;
	private final JTextField prenume_p;
	private final JTextField simptome;
	private final JTextField recomandari;
	private final JTextField parafat;
	private final JTextField diagnostic;
	private final JTable table_1;
	private final JTextField investigatii;
	private final JTextField asistent;

	public Meniu_Medic(Utilizator utilizatorCurent) {
		setSize(790, 630);
		setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 191, 255));
		tabbedPane.addTab("Programari", null, panel, null);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(108, 47, 542, 272);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		Button butonP = new Button("Vezi programari");
		butonP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					String sqlQuery = "SELECT P.id_programare, P.nume_pacient As 'Nume Pacient', P.prenume_pacient as 'Prenume Pacient', P.ora, P.zi, U.denumire FROM programare P "
							+ "JOIN unitate_medicala U ON P.id_unitate=U.id_unitate_medicala "
							+ "JOIN medic M on M.id_medic=P.id_medic "
							+ "JOIN utilizator UT ON UT.id_utilizator=M.id_utilizator"
							+ " WHERE P.zi=CURDATE() AND incheiata!='1' AND UT.id_utilizator='"
							+ utilizatorCurent.getIdUtilizator() + "' ORDER BY P.ora ASC";
					selectStatement.execute(sqlQuery);
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonP.setBounds(250, 348, 234, 82);
		panel.add(butonP);

		Button butonIncepe = new Button("Incepe programarea");
		butonIncepe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Alege o programare mai intai!", "Eroare", 0);
					return;
				}
				int idProgramare = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					String sqlQuery = "SELECT * from programare WHERE id_programare='" + idProgramare + "'";
					selectStatement.execute(sqlQuery);
					rs = selectStatement.getResultSet();
					rs.next();
					String numePacient = rs.getString("nume_pacient");
					String prenumePacient = rs.getString("prenume_pacient");
					String zi = rs.getString("zi");
					java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
					int idMedicPr = rs.getInt("id_medic");
					sqlQuery = "INSERT INTO raport_medical(id_raport, nume_pacient, prenume_pacient, nume_medic_realizator, prenume_medic_realizator,data_consultatiei, istoric, id_medic) "
							+ "VALUES(0,'" + numePacient + "','" + prenumePacient + "','" + utilizatorCurent.getNume()
							+ "','" + utilizatorCurent.getPrenume() + "','" + sqlDate + "','Nu are','" + idMedicPr
							+ "');";
					selectStatement = con.createStatement();
					selectStatement.execute(sqlQuery);
					JOptionPane.showMessageDialog(null, "Consultatia a inceput!");

					String query = "SELECT LAST_INSERT_ID()";
					selectStatement = con.createStatement();
					selectStatement.execute(query);
					rs = selectStatement.getResultSet();
					rs.next();
					int lastId = rs.getInt(1);
					selectStatement.close();

					selectStatement = con.createStatement();
					sqlQuery = "INSERT INTO istoric_medical(id_istoric_medical, data_consultarii, id_raport, id_pacient) VALUES (0,'"
							+ sqlDate + "','" + lastId + ",'" + numePacient + "','" + prenumePacient + "')";
					selectStatement.executeUpdate(sqlQuery);
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonIncepe.setBounds(250, 467, 234, 82);
		panel.add(butonIncepe);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(135, 206, 250));
		tabbedPane.addTab("Rapoarte Medicale", null, panel_1, null);
		panel_1.setLayout(null);

		Button butonRapoarte = new Button("Vezi Rapoarte");
		butonRapoarte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					selectStatement.execute(
							"SELECT r.id_raport, r.nume_pacient, r.prenume_pacient, r.parafat, r.id_medic FROM raport_medical r , medic m , utilizator u WHERE r.data_consultatiei=CURDATE() AND r.id_medic = m.id_medic AND m.id_utilizator = u.id_utilizator AND u.id_utilizator = '"
									+ utilizatorCurent.getIdUtilizator() + "';");
					rs = selectStatement.getResultSet();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonRapoarte.setBounds(371, 375, 146, 58);
		panel_1.add(butonRapoarte);

		Button butonAdauga = new Button("Adauga Raport");
		butonAdauga.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					String SqlQuery = "SELECT M.id_medic FROM medic M , utilizator U WHERE U.id_utilizator=M.id_utilizator AND U.id_utilizator='"
							+ utilizatorCurent.getIdUtilizator() + "'";
					selectStatement.execute(SqlQuery);
					rs = selectStatement.getResultSet();
					rs.next();
					int idMedic = rs.getInt("id_medic");
					stmt = (CallableStatement) con.prepareCall("{call adauga_raport_medical(?,?,?,?,?,?,?,?,?,?,?)}");
					stmt.setString(1, nume_p.getText());
					stmt.setString(2, prenume_p.getText());
					stmt.setString(3, utilizatorCurent.getNume());
					stmt.setString(4, utilizatorCurent.getPrenume());
					java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
					stmt.setDate(5, sqlDate);
					stmt.setString(6, simptome.getText());
					stmt.setString(7, diagnostic.getText());
					stmt.setString(8, recomandari.getText());
					stmt.setString(9, investigatii.getText());
					stmt.setInt(10, idMedic);
					stmt.setInt(11, Integer.parseInt(asistent.getText()));

					stmt.executeUpdate();
					stmt.close();
					JOptionPane.showMessageDialog(butonAdauga, "Raportul a fost adaugat cu succes");
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonAdauga.setBounds(559, 375, 139, 58);
		panel_1.add(butonAdauga);

		Button butonParafa = new Button("Pune parafa");
		butonParafa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					selectStatement.execute("UPDATE raport_medical SET parafat=1 WHERE id_raport='"
							+ Integer.parseInt(idraport.getText()) + "'");
					JOptionPane.showMessageDialog(butonParafa, "Parafa a fost pusa!");
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonParafa.setBounds(559, 439, 143, 58);
		panel_1.add(butonParafa);

		JLabel lblNewLabel = new JLabel("Id Raport");
		lblNewLabel.setForeground(new Color(255, 250, 250));
		lblNewLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 21, 103, 36);
		panel_1.add(lblNewLabel);

		JLabel lblNumePacient = new JLabel("Nume Pacient");
		lblNumePacient.setForeground(new Color(255, 250, 250));
		lblNumePacient.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblNumePacient.setBounds(10, 125, 103, 36);
		panel_1.add(lblNumePacient);

		JLabel lblPrenumePacient = new JLabel("Prenume Pacient");
		lblPrenumePacient.setForeground(new Color(255, 250, 250));
		lblPrenumePacient.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblPrenumePacient.setBounds(10, 178, 120, 36);
		panel_1.add(lblPrenumePacient);

		JLabel lblSimptome = new JLabel("Simptome");
		lblSimptome.setForeground(new Color(255, 250, 250));
		lblSimptome.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblSimptome.setBounds(10, 250, 103, 36);
		panel_1.add(lblSimptome);

		JLabel lblRecomandari = new JLabel("Recomandari");
		lblRecomandari.setForeground(new Color(255, 250, 250));
		lblRecomandari.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblRecomandari.setBounds(10, 375, 103, 36);
		panel_1.add(lblRecomandari);

		JLabel lblParafat = new JLabel("Parafat");
		lblParafat.setForeground(new Color(255, 250, 250));
		lblParafat.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblParafat.setBounds(10, 515, 103, 36);
		panel_1.add(lblParafat);

		idraport = new JTextField();
		idraport.setEditable(false);
		idraport.setColumns(10);
		idraport.setBounds(126, 21, 133, 41);
		panel_1.add(idraport);

		nume_p = new JTextField();
		nume_p.setColumns(10);
		nume_p.setBounds(123, 125, 184, 41);
		panel_1.add(nume_p);

		prenume_p = new JTextField();
		prenume_p.setColumns(10);
		prenume_p.setBounds(140, 178, 169, 41);
		panel_1.add(prenume_p);

		simptome = new JTextField();
		simptome.setColumns(10);
		simptome.setBounds(126, 245, 181, 41);
		panel_1.add(simptome);

		recomandari = new JTextField();
		recomandari.setColumns(10);
		recomandari.setBounds(126, 374, 181, 41);
		panel_1.add(recomandari);

		parafat = new JTextField();
		parafat.setEditable(false);
		parafat.setColumns(10);
		parafat.setBounds(126, 514, 133, 41);
		panel_1.add(parafat);

		JLabel lblDiagnostic = new JLabel("Diagnostic");
		lblDiagnostic.setForeground(new Color(255, 250, 250));
		lblDiagnostic.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblDiagnostic.setBounds(10, 309, 103, 36);
		panel_1.add(lblDiagnostic);

		diagnostic = new JTextField();
		diagnostic.setColumns(10);
		diagnostic.setBounds(126, 309, 181, 41);
		panel_1.add(diagnostic);

		JLabel lblinvestigatii = new JLabel("investigatii");
		lblinvestigatii.setForeground(new Color(255, 250, 250));
		lblinvestigatii.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblinvestigatii.setBounds(10, 440, 103, 36);
		panel_1.add(lblinvestigatii);

		investigatii = new JTextField();
		investigatii.setColumns(10);
		investigatii.setBounds(126, 439, 181, 41);
		panel_1.add(investigatii);

		JLabel lblAsistent = new JLabel("Id Asistent");
		lblAsistent.setForeground(new Color(255, 250, 250));
		lblAsistent.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblAsistent.setBounds(10, 69, 103, 36);
		panel_1.add(lblAsistent);

		asistent = new JTextField();
		asistent.setColumns(10);
		asistent.setBounds(126, 69, 133, 41);
		panel_1.add(asistent);

		JComboBox listaAsistenti = new JComboBox();
		listaAsistenti.setMaximumRowCount(100);
		listaAsistenti.setBounds(331, 500, 244, 71);

		try {
			List<String> asistenti = new ArrayList<>();
			conexiuneDB = new MySQL_Connect();
			con = conexiuneDB.getConnection();
			selectStatement = con.createStatement();
			// Iau id-ul unitatii medicale pentru doctorul curent folosindu-ma de orarul
			// specific
			String query = "SELECT UM.id_unitate_medicala FROM unitate_medicala UM "
					+ "JOIN orar_specific OS ON OS.id_unitate = UM.id_unitate_medicala "
					+ "JOIN utilizator U ON U.id_utilizator = OS.id_angajat "
					+ "WHERE OS.data = curdate() AND OS.ora_incepere < time(now()) AND OS.ora_incheiere > time(now()) AND U.id_utilizator = "
					+ utilizatorCurent.getIdUtilizator();
			selectStatement.execute(query);
			rs = selectStatement.getResultSet();
			int idUnitateCurenta = 0;
			if (rs.next()) {
				idUnitateCurenta = rs.getInt("id_unitate_medicala");
			} else {
				// Iau id-ul unitatii medicale pentru doctorul curent folosindu-ma de orarul
				// generic
				query = "SET lc_time_names = 'ro_RO';";
				selectStatement = con.createStatement();
				selectStatement.execute(query);
				query = "SELECT UM.id_unitate_medicala FROM unitate_medicala UM"
						+ "	JOIN orar_generic OG ON OG.id_unitate = UM.id_unitate_medicala"
						+ "	JOIN utilizator U ON U.id_utilizator = OG.id_angajat"
						+ " WHERE OG.ziua = DAYNAME(curdate())" + "	AND OG.ora_incepere < time(now())"
						+ "	AND OG.ora_incheiere > time(now())" + " AND U.id_utilizator = "
						+ utilizatorCurent.getIdUtilizator();
				selectStatement.close();
				selectStatement = con.createStatement();
				selectStatement.execute(query);
				rs = selectStatement.getResultSet();
				if (rs.next()) {
					idUnitateCurenta = rs.getInt("id_unitate_medicala");
				}
				selectStatement.close();
			}
			selectStatement = con.createStatement();
			query = "SELECT A.id_asistent , U.nume, U.prenume FROM asistent A "
					+ "JOIN utilizator U ON U.id_utilizator = A.id_utilizator "
					+ "JOIN orar_specific OS ON OS.id_angajat = U.id_utilizator "
					+ "JOIN unitate_medicala UM ON UM.id_unitate_medicala = OS.id_unitate "
					+ "WHERE OS.data = curdate()" + "	AND OS.ora_incepere < time(now())"
					+ "	AND OS.ora_incheiere > time(now())" + "	AND UM.id_unitate_medicala = " + idUnitateCurenta;
			selectStatement.execute(query);
			rs = selectStatement.getResultSet();
			while (rs.next()) {
				int idA = rs.getInt("id_asistent");
				String nume = rs.getString("nume");
				String prenume = rs.getString("prenume");
				String asistent = idA + "." + nume + " " + prenume;
				asistenti.add(asistent);
			}
			selectStatement.close();
			selectStatement = con.createStatement();
			query = "SET lc_time_names = 'ro_RO';";
			selectStatement.execute(query);
			selectStatement = con.createStatement();
			query = "SELECT A.id_asistent , U.nume, U.prenume FROM asistent A "
					+ "JOIN utilizator U ON U.id_utilizator = A.id_utilizator "
					+ "JOIN orar_generic OG ON OG.id_angajat = U.id_utilizator "
					+ "JOIN unitate_medicala UM ON UM.id_unitate_medicala = OG.id_unitate "
					+ "WHERE OG.ziua = DAYNAME(curdate())" + "	AND OG.ora_incepere < time(now())"
					+ "	AND OG.ora_incheiere > time(now())" + " AND UM.id_unitate_medicala = " + idUnitateCurenta;
			selectStatement.execute(query);
			rs = selectStatement.getResultSet();
			while (rs.next()) {
				int idA = rs.getInt("id_asistent");
				String nume = rs.getString("nume");
				String prenume = rs.getString("prenume");
				String asistent = idA + "." + nume + " " + prenume;
				asistenti.add(asistent);
			}
			Object[] arrNames = asistenti.toArray();
			listaAsistenti.setModel(new DefaultComboBoxModel(arrNames));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		panel_1.add(listaAsistenti);

		Button butonModifica = new Button("Modifica Raport");
		butonModifica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRow = table_1.getSelectedRow();
					if (selectedRow == -1) {
						return;
					}
					int idRaportP = Integer.parseInt(table_1.getValueAt(selectedRow, 0).toString());
					System.out.println(idRaportP);
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement2 = con.createStatement();
					selectStatement2.execute("SELECT * FROM raport_medical WHERE id_raport='" + idRaportP + "'");
					rs2 = selectStatement2.getResultSet();
					rs2.next();
					String numeP = rs2.getString("nume_pacient");
					String prenumeP = rs2.getString("prenume_pacient");
					String simptomeP = rs2.getString("simptome");
					String diagnosticP = rs2.getString("diagnostic");
					String recomandariP = rs2.getString("recomandari");
					String investigatiiP = rs2.getString("investigatii");
					int idAsistent = rs2.getInt("id_asistent");
					int parafatP = rs2.getInt("parafat");
					idraport.setText(String.valueOf(idRaportP));
					nume_p.setText(numeP);
					prenume_p.setText(prenumeP);
					simptome.setText(simptomeP);
					diagnostic.setText(diagnosticP);
					recomandari.setText(recomandariP);
					parafat.setText(String.valueOf(parafatP));
					investigatii.setText(investigatiiP);
					asistent.setText(String.valueOf(idAsistent));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonModifica.setBounds(371, 439, 146, 58);
		panel_1.add(butonModifica);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(332, 43, 423, 315);
		panel_1.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		Button butonSalveaza = new Button("Salveaza modificari");
		butonSalveaza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (idraport.getText().isEmpty()) {
						JOptionPane.showMessageDialog(butonModifica, "Alege un raport mai intai!", "Eroare", 0);
						return;
					}
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					int isParafat = Integer.parseInt(parafat.getText());
					if (isParafat == 0) {
						selectStatement = con.createStatement();
						selectStatement.execute("UPDATE raport_medical SET id_asistent='"
								+ Integer.parseInt(asistent.getText()) + "', nume_pacient='" + nume_p.getText()
								+ "' ,prenume_pacient='" + prenume_p.getText() + "' ,simptome = '" + simptome.getText()
								+ "', diagnostic='" + diagnostic.getText() + "', recomandari='" + recomandari.getText()
								+ "' " + ",investigatii='" + investigatii.getText() + "' WHERE id_raport='"
								+ Integer.parseInt(idraport.getText()) + "'");
						JOptionPane.showMessageDialog(butonModifica, "Raportul a fost modificat cu succes!");
					} else
						JOptionPane.showMessageDialog(butonModifica,
								"Raportul nu poate fi modificat deoarece a fost pusa parafa", "Eroare", 0);
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonSalveaza.setBounds(581, 503, 146, 58);
		panel_1.add(butonSalveaza);

		JPanel panelIstoric = new JPanel();
		panelIstoric.setBackground(new Color(135, 206, 250));
		tabbedPane.addTab("Istoric Medical", null, panelIstoric, null);
		panelIstoric.setLayout(null);

		JScrollPane scrollPane4 = new JScrollPane();
		scrollPane4.setBounds(19, 43, 723, 315);
		panelIstoric.add(scrollPane4);

		JTable tableIstoric = new JTable();
		scrollPane4.setViewportView(tableIstoric);

		Button butonIstoric = new Button("Vezi istoric");
		butonIstoric.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRow = tableIstoric.getSelectedRow();
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(null,
								"Selecteaza un pacient pentru care vrei sa vezi istoricul!");
						return;
					}

					String numePacient = tableIstoric.getValueAt(selectedRow, 1).toString();
					String prenumePacient = tableIstoric.getValueAt(selectedRow, 2).toString();
					System.out.println(numePacient);
					System.out.println(prenumePacient);

					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					String sql = "SELECT IM.*, RM.nume_medic_realizator, RM.prenume_medic_realizator, RM.simptome, RM.diagnostic, RM.recomandari, RM.investigatii FROM istoric_medical IM "
							+ "JOIN raport_medical RM ON RM.id_raport = IM.id_raport WHERE IM.nume_pacient='"
							+ numePacient + "' AND IM.prenume_pacient='" + prenumePacient + "'";
					selectStatement.execute(sql);
					rs = selectStatement.getResultSet();
					tableIstoric.setModel(DbUtils.resultSetToTableModel(rs));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonIstoric.setBounds(449, 396, 174, 78);
		panelIstoric.add(butonIstoric);

		Button butonRap = new Button("Rapoarte");
		butonRap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					selectStatement.execute(
							"SELECT r.id_raport, r.nume_pacient, r.prenume_pacient, r.parafat, r.id_medic FROM raport_medical r , medic m , utilizator u WHERE r.data_consultatiei=CURDATE() AND r.id_medic = m.id_medic AND m.id_utilizator = u.id_utilizator AND u.id_utilizator = '"
									+ utilizatorCurent.getIdUtilizator() + "';");
					rs = selectStatement.getResultSet();
					tableIstoric.setModel(DbUtils.resultSetToTableModel(rs));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonRap.setBounds(147, 396, 174, 78);
		panelIstoric.add(butonRap);

	}
}
