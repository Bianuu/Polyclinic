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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db.MySQL_Connect;
import modele.Utilizator;
import net.proteanit.sql.DbUtils;

public class Meniu_Asistent extends JPanel {
	private final JTable table;
	private final JTextField idraport;
	private final JTextField nume_p;
	private final JTextField prenume_p;
	private final JTextField simptome;
	private final JTextField diagnostic;
	private final JTextField recomandari;
	private final JTextField parafat;
	// Conexiune Baza de date
	private MySQL_Connect conexiuneDB;
	private Connection con;
	Statement selectStatement = null, selectStatement2 = null, stmt = null;
	ResultSet rs = null, rs2 = null, rs3 = null;
	private final JTextField idmedic;
	private JTextField investigatii;

	public Meniu_Asistent(Utilizator utilizatorCurent) {
		setBackground(new Color(135, 206, 250));
		setSize(790, 630);
		setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 191, 255));
		tabbedPane.addTab("Programari", null, panel, null);
		panel.setLayout(null);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(108, 47, 542, 272);
		panel.add(scrollPane2);

		JTable table2 = new JTable();
		scrollPane2.setViewportView(table2);

		Button butonP = new Button("Vezi programari");
		butonP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					String sqlQuery = "SELECT P.id_programare, P.nume_pacient As 'Nume Pacient', P.prenume_pacient as 'Prenume Pacient', P.ora, P.zi, U.denumire FROM programare P JOIN unitate_medicala U ON P.id_unitate=U.id_unitate_medicala WHERE P.zi=CURDATE() AND incheiata!='1' ORDER BY P.ora ASC";
					selectStatement.execute(sqlQuery);
					rs = selectStatement.getResultSet();
					table2.setModel(DbUtils.resultSetToTableModel(rs));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonP.setBounds(250, 348, 234, 82);
		panel.add(butonP);

		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(0, 191, 255));
		tabbedPane.addTab("Rapoarte", null, panel2, null);
		panel2.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(313, 69, 452, 332);
		panel2.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		Button butonRapoarte = new Button("Vezi Rapoarte");
		butonRapoarte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement = con.createStatement();
					selectStatement.execute(
							"SELECT r.id_raport, r.nume_pacient, r.prenume_pacient, r.parafat, r.id_medic FROM raport_medical r , asistent a , utilizator u WHERE r.id_asistent = a.id_asistent AND a.id_utilizator = u.id_utilizator AND u.id_utilizator = '"
									+ utilizatorCurent.getIdUtilizator() + "';");
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonRapoarte.setBounds(342, 437, 146, 58);
		panel2.add(butonRapoarte);

		JLabel lblNewLabel = new JLabel("Id Raport");
		lblNewLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblNewLabel.setForeground(new Color(255, 250, 250));
		lblNewLabel.setBounds(6, 50, 103, 36);
		panel2.add(lblNewLabel);

		idraport = new JTextField();
		idraport.setEditable(false);
		idraport.setBounds(134, 50, 133, 41);
		panel2.add(idraport);
		idraport.setColumns(10);

		JLabel lblNumePacient = new JLabel("Nume Pacient");
		lblNumePacient.setForeground(new Color(255, 250, 250));
		lblNumePacient.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblNumePacient.setBounds(6, 175, 103, 36);
		panel2.add(lblNumePacient);

		JLabel lblPrenumePacient = new JLabel("Prenume Pacient");
		lblPrenumePacient.setForeground(new Color(255, 250, 250));
		lblPrenumePacient.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblPrenumePacient.setBounds(6, 235, 118, 36);
		panel2.add(lblPrenumePacient);

		JLabel lblSimptome = new JLabel("Simptome");
		lblSimptome.setForeground(new Color(255, 250, 250));
		lblSimptome.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblSimptome.setBounds(6, 296, 103, 36);
		panel2.add(lblSimptome);

		JLabel lblDiagnostic = new JLabel("Diagnostic");
		lblDiagnostic.setForeground(new Color(255, 250, 250));
		lblDiagnostic.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblDiagnostic.setBounds(6, 363, 103, 36);
		panel2.add(lblDiagnostic);

		nume_p = new JTextField();
		nume_p.setColumns(10);
		nume_p.setBounds(121, 174, 165, 41);
		panel2.add(nume_p);

		prenume_p = new JTextField();
		prenume_p.setColumns(10);
		prenume_p.setBounds(121, 234, 165, 41);
		panel2.add(prenume_p);

		simptome = new JTextField();
		simptome.setColumns(10);
		simptome.setBounds(103, 295, 183, 41);
		panel2.add(simptome);

		diagnostic = new JTextField();
		diagnostic.setColumns(10);
		diagnostic.setBounds(103, 362, 183, 41);
		panel2.add(diagnostic);

		JLabel lblRecomandari = new JLabel("Recomandari");
		lblRecomandari.setForeground(new Color(255, 250, 250));
		lblRecomandari.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblRecomandari.setBounds(6, 440, 103, 36);
		panel2.add(lblRecomandari);

		JLabel lblParafat = new JLabel("Parafat");
		lblParafat.setForeground(new Color(255, 250, 250));
		lblParafat.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblParafat.setBounds(6, 542, 103, 36);
		panel2.add(lblParafat);

		recomandari = new JTextField();
		recomandari.setColumns(10);
		recomandari.setBounds(103, 435, 183, 41);
		panel2.add(recomandari);

		parafat = new JTextField();
		parafat.setEditable(false);
		parafat.setColumns(10);
		parafat.setBounds(134, 537, 133, 41);
		panel2.add(parafat);

		JLabel lblNewLabel_1 = new JLabel("Rapoarte medicale");
		lblNewLabel_1.setForeground(new Color(106, 90, 205));
		lblNewLabel_1.setFont(new Font("DialogInput", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(228, 0, 299, 58);
		panel2.add(lblNewLabel_1);

		Button butonAdauga = new Button("Modifica Raport");
		butonAdauga.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRow = table.getSelectedRow();
					if (selectedRow == -1) {
						return;
					}
					int idRaportP = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
					selectStatement2 = con.createStatement();
					selectStatement2.execute("SELECT * FROM raport_medical WHERE id_raport='" + idRaportP + "'");
					rs2 = selectStatement2.getResultSet();
					rs2.next();
					int idMedicP = rs2.getInt("id_medic");
					String numeP = rs2.getString("nume_pacient");
					String prenumeP = rs2.getString("prenume_pacient");
					String simptomeP = rs2.getString("simptome");
					String diagnosticP = rs2.getString("diagnostic");
					String recomandariP = rs2.getString("recomandari");
					String investigatiiP = rs2.getString("investigatii");
					int parafatP = rs2.getInt("parafat");
					idraport.setText(String.valueOf(idRaportP));
					idmedic.setText(String.valueOf(idMedicP));
					nume_p.setText(numeP);
					prenume_p.setText(prenumeP);
					simptome.setText(simptomeP);
					diagnostic.setText(diagnosticP);
					recomandari.setText(recomandariP);
					parafat.setText(String.valueOf(parafatP));
					investigatii.setText(investigatiiP);
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonAdauga.setBounds(566, 437, 154, 58);
		panel2.add(butonAdauga);

		JLabel lblIdMedic = new JLabel("Id Medic");
		lblIdMedic.setForeground(new Color(255, 250, 250));
		lblIdMedic.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblIdMedic.setBounds(6, 117, 103, 36);
		panel2.add(lblIdMedic);

		Button butonModifica = new Button("Salveaza Modificarile");
		butonModifica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (idraport.getText().isEmpty()) {
						JOptionPane.showMessageDialog(butonModifica, "Alege un raport mai intai!", "Eroare", 0);
						return;
					}
					conexiuneDB = new MySQL_Connect();
					con = conexiuneDB.getConnection();
//					stmt = con.createStatement();
					int isParafat = Integer.parseInt(parafat.getText());
					if (isParafat == 0) {
						selectStatement = con.createStatement();
						selectStatement.execute("UPDATE raport_medical SET nume_pacient='" + nume_p.getText()
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
		butonModifica.setBounds(412, 524, 243, 58);
		panel2.add(butonModifica);

		idmedic = new JTextField();
		idmedic.setEditable(false);
		idmedic.setColumns(10);
		idmedic.setBounds(134, 112, 133, 41);
		panel2.add(idmedic);

		JLabel lblinvestigatii = new JLabel("investigatii");
		lblinvestigatii.setForeground(new Color(255, 250, 250));
		lblinvestigatii.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblinvestigatii.setBounds(6, 493, 103, 36);
		panel2.add(lblinvestigatii);

		investigatii = new JTextField();
		investigatii.setColumns(10);
		investigatii.setBounds(103, 488, 183, 41);
		panel2.add(investigatii);

	}
}
