package modul_3;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.cj.jdbc.CallableStatement;

import db.MySQL_Connect;
import modele.ServiciuMedical;
import net.proteanit.sql.DbUtils;

public class Meniu_Receptioner extends JPanel {
	private final JTextField textOra;
	private final JTextField textData;
	private final JTextField numeText;
	private final JTextField prenumeText;
	private final List<ServiciuMedical> servicii;
	private final Map<Integer, String> serviciiMedicale;
	private final Map<Integer, String> medici;
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null;
	private CallableStatement selectStatement2 = null;
	ResultSet rs = null, rs2 = null;

	public Meniu_Receptioner() {

		servicii = new ArrayList<>();
		serviciiMedicale = new HashMap<>();
		medici = new HashMap<>();

		setBackground(new Color(135, 206, 235));
		setSize(790, 630);
		setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("DialogInput", Font.BOLD, 18));
		tabbedPane.setBackground(new Color(0, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(new Color(0, 102, 255));
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panelProgramare = new JPanel();
		panelProgramare.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelProgramare.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Programare", null, panelProgramare, null);
		panelProgramare.setLayout(null);

		JLabel lblProgramare = new JLabel("Inregistrare Programare");
		lblProgramare.setFont(new Font("DialogInput", Font.BOLD, 18));
		lblProgramare.setBounds(269, 20, 280, 44);
		panelProgramare.add(lblProgramare);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(411, 123, 320, 323);
		panelProgramare.add(scrollPane_2);

		JTable table = new JTable();
		scrollPane_2.setViewportView(table);

		JComboBox comboMedici = new JComboBox();
		List<String> names = new ArrayList<>();
		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			String sql = "SELECT * FROM utilizator U JOIN medic M on M.id_utilizator = U.id_utilizator";
			selectStatement.execute(sql);
			rs = selectStatement.getResultSet();
			names.add("-Lista medici-");
			while (rs.next()) {
				int id_medic = rs.getInt("id_medic");
				String nume = rs.getString("nume");
				String prenume = rs.getString("prenume");
				String numeComplet = nume + " " + prenume;
				medici.clear();
				medici.put(id_medic, numeComplet);
				names.add(numeComplet);
			}
			Object[] arrNames = names.toArray();
			comboMedici.setModel(new DefaultComboBoxModel(arrNames));

			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(411, 488, 320, 49);
		panelProgramare.add(scrollPane);

		JList listaOre = new JList();
		listaOre.setVisibleRowCount(20);
		listaOre.setEnabled(false);
		scrollPane.setViewportView(listaOre);

		comboMedici.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					String[] separatedName = comboMedici.getSelectedItem().toString().split(" ");
					String sql = "SELECT S.id_serviciu_medical, S.denumire, S.pret, S.specializarea, S.durata , S.id_medic FROM serviciu_medical S "
							+ "JOIN medic M ON M.id_medic = S.id_medic "
							+ "JOIN utilizator U on U.id_utilizator = M.id_utilizator WHERE nume='" + separatedName[0]
							+ "' AND prenume='" + separatedName[1] + "'";
//							+ medici.get(comboMedici.getSelectedItem().toString()) + "'";
					selectStatement.execute(sql);
					rs = selectStatement.getResultSet();
					while (rs.next()) {
						int idServiciu = rs.getInt("id_serviciu_medical");
						String numeServiciu = rs.getString("denumire");
						String specializare = rs.getString("specializarea");
						double pret = rs.getDouble("pret");
						int durata = rs.getInt("durata");
						int idMedic = rs.getInt("id_medic");
						ServiciuMedical serviciuMedical = new ServiciuMedical(idServiciu, numeServiciu, specializare,
								pret, durata, idMedic);
						servicii.add(serviciuMedical);
						serviciiMedicale.put(idServiciu, numeServiciu);
					}
					selectStatement.execute(sql);
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					selectStatement.close();
					selectStatement = connection.createStatement();
					String query = "SELECT * FROM orar_generic OG "
							+ "JOIN utilizator U ON U.id_utilizator = OG.id_angajat "
							+ "JOIN medic M ON M.id_utilizator = U.id_utilizator" + " WHERE U.nume = '"
							+ separatedName[0] + "' AND U.prenume='" + separatedName[1] + "'";
					selectStatement.execute(query);
					rs = selectStatement.getResultSet();
					DefaultListModel<String> listModel = new DefaultListModel<>();
					while (rs.next()) {
						String ziua = rs.getString("ziua");
						String oraIncepere = rs.getTime("ora_incepere").toString();
						String oraIncheiere = rs.getTime("ora_incheiere").toString();
						String evOrar = ziua + "-" + oraIncepere + "-" + oraIncheiere;
						listModel.addElement(evOrar);
					}
					selectStatement.close();
					selectStatement = connection.createStatement();
					query = "SELECT * FROM orar_specific OS " + "JOIN utilizator U ON U.id_utilizator = OS.id_angajat "
							+ "JOIN medic M ON M.id_utilizator = U.id_utilizator" + " WHERE U.nume = '"
							+ separatedName[0] + "' AND U.prenume='" + separatedName[1] + "'";
					selectStatement.execute(query);
					rs = selectStatement.getResultSet();
					while (rs.next()) {
						String ziua = rs.getDate("data").toString();
						String oraIncepere = rs.getTime("ora_incepere").toString();
						String oraIncheiere = rs.getTime("ora_incheiere").toString();
						String evOrar = ziua + ":" + oraIncepere + "-" + oraIncheiere;
						listModel.addElement(evOrar);
					}
					listaOre.setModel(listModel);

					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
//		comboMedici.setModel(new DefaultComboBoxModel(new String[] { "-Lista medici-" }));
		comboMedici.setBounds(76, 381, 239, 36);
		panelProgramare.add(comboMedici);

		textOra = new JTextField();
		textOra.setBounds(190, 182, 166, 44);
		panelProgramare.add(textOra);
		textOra.setColumns(10);

		JLabel lblOra = new JLabel("Ora");
		lblOra.setHorizontalAlignment(SwingConstants.CENTER);
		lblOra.setBounds(76, 182, 95, 44);
		panelProgramare.add(lblOra);

		textData = new JTextField();
		textData.setColumns(10);
		textData.setBounds(190, 123, 166, 44);
		panelProgramare.add(textData);

		JLabel lblData = new JLabel("Data");
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setBounds(76, 123, 95, 44);
		panelProgramare.add(lblData);

		JLabel lblServicii = new JLabel("Lista Servicii");
		lblServicii.setBounds(411, 82, 120, 35);
		panelProgramare.add(lblServicii);

		numeText = new JTextField();
		numeText.setColumns(10);
		numeText.setBounds(190, 238, 166, 44);
		panelProgramare.add(numeText);

		prenumeText = new JTextField();
		prenumeText.setColumns(10);
		prenumeText.setBounds(190, 294, 166, 44);
		panelProgramare.add(prenumeText);

		JLabel lblNume = new JLabel("Nume Pacient");
		lblNume.setHorizontalAlignment(SwingConstants.CENTER);
		lblNume.setBounds(62, 238, 95, 44);
		panelProgramare.add(lblNume);

		JLabel lblPrenume = new JLabel("Prenume Pacient");
		lblPrenume.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrenume.setBounds(42, 294, 136, 44);
		panelProgramare.add(lblPrenume);

		JButton btnCreare = new JButton("Programeaza");
		btnCreare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numeText.getText() == null || numeText.getText().equals("") || prenumeText.getText() == null
						|| prenumeText.getText().equals("") || textData.getText() == null
						|| textData.getText().equals("") || textOra.getText() == null || textOra.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Nu ai introdus unul dintre campuri sau acesta nu este valid.");
					return;
				}
				String numePacient = numeText.getText();
				String prenumePacient = prenumeText.getText();
				Date dataProgramarii = Date.valueOf(textData.getText());
				Time ora = Time.valueOf(textOra.getText());
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					int linie_edit = table.getSelectedRow();
					int[] rows = table.getSelectedRows();
					if (rows.length > 0) {
						String specializareInit = (String) table.getValueAt(rows[0], 3);
						for (int row : rows) {
							String specializareRow = (String) table.getValueAt(row, 3);
							if (!specializareRow.equals(specializareInit)) {
								JOptionPane.showMessageDialog(null,
										"Programarea trebuie sa contina doar servicii medicale de la aceeasi specializare.");
								return;
							}
						}

						int idMedic = (int) table.getValueAt(rows[0], 5);

						selectStatement = connection.createStatement();
						String queryMedici = "SELECT * FROM programare P WHERE id_medic='" + idMedic + "'";
						selectStatement.execute(queryMedici);
						rs = selectStatement.getResultSet();
						while (rs.next()) {
							Date dataConsultatie = rs.getDate("zi");
							Time timp = rs.getTime("ora");
							if (dataConsultatie.equals(dataProgramarii) && timp.equals(ora)) {
								JOptionPane.showMessageDialog(null, "Ora introdusa se suprapune cu o alta programare.");
								return;
							}
						}

						selectStatement2 = (CallableStatement) connection
								.prepareCall("{call adauga_programare(?,?,?,?,?)}");
						selectStatement2.setDate(1, dataProgramarii);
						selectStatement2.setTime(2, ora);
						selectStatement2.setString(3, numePacient);
						selectStatement2.setString(4, prenumePacient);
						selectStatement2.setInt(5, idMedic);
						selectStatement2.executeUpdate();

						String query = "SELECT LAST_INSERT_ID()";
						selectStatement = connection.createStatement();
						selectStatement.execute(query);
						rs = selectStatement.getResultSet();
						rs.next();
						int lastId = rs.getInt(1);
						selectStatement2.close();
						for (int row : rows) {
							int idSpecializare = (int) table.getValueAt(row, 0);
							idMedic = (int) table.getValueAt(row, 5);
							selectStatement = connection.createStatement();

							String sqlQuery = "INSERT INTO programare_servicii_medicale VALUES(" + lastId + ","
									+ idSpecializare + ")";
							selectStatement.execute(sqlQuery);

						}
						selectStatement = connection.createStatement();
						String sqlQuery = "SELECT id_pacient FROM pacient WHERE nume='" + numePacient
								+ "' AND prenume='" + prenumePacient + "'";
						selectStatement.execute(sqlQuery);
						rs = selectStatement.getResultSet();
						int idPacient = 0;
						while (rs.next()) {
							idPacient = rs.getInt("id_pacient");
						}

						selectStatement = connection.createStatement();
						sqlQuery = "UPDATE programare SET id_pacient = '" + idPacient + "' WHERE id_programare='"
								+ lastId + "'";
						selectStatement.executeUpdate(sqlQuery);

						RepartizareMedici repartizare = new RepartizareMedici();
						repartizare.repartizare();
						selectStatement = connection.createStatement();
						query = "SELECT UM.denumire FROM unitate_medicala UM "
								+ "JOIN programare P ON P.id_unitate = UM.id_unitate_medicala "
								+ "WHERE P.id_programare = " + lastId;
						String unitateP = "";
						if (rs.next()) {
							unitateP = rs.getString("denumire");
						}
						JOptionPane.showMessageDialog(null,
								"Programarea a fost creata cu succes la unitatea medicala " + unitateP + " !");
						textOra.setText("");
						textData.setText("");
						numeText.setText("");
						prenumeText.setText("");
						selectStatement.close();
						connection.close();

					} else {
						JOptionPane.showMessageDialog(null, "Selecteaza un serviciu medical.");
						return;
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCreare.setBounds(125, 488, 142, 44);
		panelProgramare.add(btnCreare);

		JPanel panelBon = new JPanel();
		panelBon.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelBon.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Bon Fiscal", null, panelBon, null);
		panelBon.setLayout(null);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(108, 47, 542, 272);
		panelBon.add(scrollPane2);

		JTable table2 = new JTable();
		scrollPane2.setViewportView(table2);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			String sql = "SELECT id_programare, ora, zi ,nume_pacient AS 'Nume pacient', prenume_pacient AS 'Prenume pacient' FROM programare WHERE incheiata=1 AND id_programare "
					+ "NOT IN (select id_programare from bon_fiscal)";
			selectStatement.execute(sql);
			rs = selectStatement.getResultSet();
			table2.setModel(DbUtils.resultSetToTableModel(rs));

			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JButton btnEmitere = new JButton("Emite Bon Fiscal");
		btnEmitere.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table2.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Selecteaza o programare pentru emiterea bonului fiscal.");
					return;
				}
				int idProgramare = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());
				double pretTotal = 0;
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					String sqlQuery = "SELECT * FROM serviciu_medical SM "
							+ "JOIN programare_servicii_medicale PSM ON PSM.id_servicii=SM.id_serviciu_medical "
							+ "JOIN programare P ON P.id_programare=PSM.id_programare WHERE P.id_programare='"
							+ idProgramare + "'";
					selectStatement.execute(sqlQuery);
					rs = selectStatement.getResultSet();
					while (rs.next()) {
						double pretServiciu = rs.getDouble("pret");
						pretTotal += pretServiciu;
					}
					selectStatement = connection.createStatement();
					sqlQuery = "INSERT INTO bon_fiscal(id_bon,total,id_programare) VALUES (0,'" + pretTotal + "','"
							+ idProgramare + "')";
					selectStatement.execute(sqlQuery);
					JOptionPane.showMessageDialog(null, "Bonul fiscal a fost eliberat cu succes!.");

					selectStatement = connection.createStatement();
					String sql = "SELECT id_programare, ora, zi ,nume_pacient AS 'Nume pacient', prenume_pacient AS 'Prenume pacient' FROM programare WHERE incheiata=1 AND id_programare "
							+ "NOT IN (select id_programare from bon_fiscal)";
					selectStatement.execute(sql);
					rs = selectStatement.getResultSet();
					table2.setModel(DbUtils.resultSetToTableModel(rs));

					sql = "SELECT U.id_utilizator, S.suma, M.procent_negociat , B.total FROM bon_fiscal B "
							+ "JOIN programare P ON P.id_programare = B.id_programare "
							+ "JOIN medic M ON P.id_medic = M.id_medic "
							+ "JOIN utilizator U ON U.id_utilizator = M.id_utilizator "
							+ "JOIN salariu S ON S.id_utilizator = U.id_utilizator WHERE S.luna=MONTH(NOW()) AND S.an=YEAR(NOW()) AND B.id_programare='"
							+ idProgramare + "'";
					selectStatement.execute(sql);
					rs = selectStatement.getResultSet();
					rs.next();
					double sumaVeche = rs.getDouble("suma");
					double costTotal = rs.getDouble("total");
					int procent = rs.getInt("procent_negociat");
					int idUtilizator = rs.getInt("id_utilizator");
					double sumaNoua = sumaVeche + (costTotal * procent / 100);
					sql = "UPDATE salariu SET suma='" + sumaNoua
							+ "' WHERE luna=MONTH(NOW()) AND an=YEAR(NOW()) AND id_utilizator='" + idUtilizator + "'";
					selectStatement.execute(sql);
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEmitere.setBounds(309, 429, 147, 57);
		panelBon.add(btnEmitere);
	}
}
