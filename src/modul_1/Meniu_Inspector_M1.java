package modul_1;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.util.StringUtils;

import db.Interogari;
import db.MySQL_Connect;
import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;
import pagini.PaginaPrincipala;

public class Meniu_Inspector_M1 extends JPanel {
	private final Image img_back = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/M1_Back.png"))
			.getImage().getScaledInstance(790, 630, Image.SCALE_SMOOTH);
	private final Image img_back2 = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/M1_Back.png"))
			.getImage().getScaledInstance(785, 591, Image.SCALE_SMOOTH);
	private final JTable table;
	private final JTable table3;
	private final JTextField nume;
	private final JTextField prenume;
	private final JTextField functie;
	private final JTable table1;
	private final JTextField nume1;
	private final JTextField prenume1;
	private final JTextField functie1;
	// Conexiune Baza de date
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null, selectStatement2 = null;
	ResultSet rs = null, rs2 = null;
	private final JTextField primaZi;
	private final JTextField aDouaZi;
	private JTextField data;
	private JTextField ora_inceput;
	private JTextField ora_sfarsit;
	private final JTable table_1;
	private final JTextField unitate;

	public Meniu_Inspector_M1() {
		setSize(790, 630);
		setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("DialogInput", Font.BOLD, 18));
		tabbedPane.setBackground(new Color(0, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(new Color(0, 102, 255));
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panel_cauta = new JPanel();
		panel_cauta.setFont(new Font("DialogInput", Font.BOLD, 18));
		panel_cauta.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Cauta Angajat", null, panel_cauta, null);
		panel_cauta.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(32, 34, 399, 300);
		panel_cauta.add(scrollPane_2);

		table = new JTable();
		scrollPane_2.setViewportView(table);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(32, 350, 399, 300);
		panel_cauta.add(scrollPane_3);

		table3 = new JTable();
		scrollPane_3.setViewportView(table3);

		JLabel lblNume = new JLabel("Nume");
		lblNume.setForeground(new Color(102, 0, 255));
		lblNume.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblNume.setHorizontalAlignment(SwingConstants.CENTER);
		lblNume.setBounds(423, 34, 158, 50);
		panel_cauta.add(lblNume);

		JLabel lblPrenume = new JLabel("Prenume");
		lblPrenume.setForeground(new Color(102, 0, 255));
		lblPrenume.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblPrenume.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrenume.setBounds(423, 117, 158, 50);
		panel_cauta.add(lblPrenume);

		JLabel lblFunctie = new JLabel("Functie");
		lblFunctie.setForeground(new Color(102, 0, 255));
		lblFunctie.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblFunctie.setHorizontalAlignment(SwingConstants.CENTER);
		lblFunctie.setBounds(423, 206, 158, 50);
		panel_cauta.add(lblFunctie);

		nume = new JTextField();
		nume.setBounds(606, 34, 158, 50);
		panel_cauta.add(nume);
		nume.setColumns(10);

		prenume = new JTextField();
		prenume.setColumns(10);
		prenume.setBounds(606, 121, 158, 50);
		panel_cauta.add(prenume);

		functie = new JTextField();
		functie.setColumns(10);
		functie.setBounds(606, 210, 158, 50);
		panel_cauta.add(functie);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute("SELECT nume,prenume,functie AS 'Functie' FROM utilizator");
			rs = selectStatement.getResultSet();
			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Button butonCauta = new Button("Cauta Angajat");
		butonCauta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean hasParameters = false;
					String numeCautat = nume.getText();
					String queryParam = "WHERE";
					if (!StringUtils.isNullOrEmpty(numeCautat)) {
						queryParam += " nume='" + numeCautat + "' ";
						hasParameters = true;
					}
					String prenumeCautat = prenume.getText();
					if (!StringUtils.isNullOrEmpty(prenumeCautat)) {
						queryParam += " prenume='" + prenumeCautat + "' ";
						hasParameters = true;
					}
					String functieCautata = functie.getText();
					if (!StringUtils.isNullOrEmpty(functieCautata)) {
						queryParam += " functie='" + functieCautata + "' ";
						hasParameters = true;
					}
					if (hasParameters == false) {
						queryParam = "";
					}
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();

					selectStatement.execute(
							"SELECT DISTINCT nume,prenume,functie , os.data AS 'Zi',os.ora_incepere AS 'Ora start', os.ora_incheiere AS 'Ora final' FROM utilizator U  "
									+ "join orar_specific os on U.id_utilizator=os.id_angajat " + queryParam
									+ " ORDER BY nume ASC");
					rs = selectStatement.getResultSet();
					table3.setModel(DbUtils.resultSetToTableModel(rs));

					selectStatement.execute(
							"SELECT DISTINCT nume,prenume,functie , og.ziua AS 'Zi',og.ora_incepere AS 'Ora start', og.ora_incheiere AS 'Ora final' FROM utilizator U "
									+ "join orar_generic og on U.id_utilizator=og.id_angajat " + queryParam
									+ " ORDER BY nume ASC");
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					nume.setText("");
					prenume.setText("");
					functie.setText("");
					connection.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonCauta.setBounds(524, 298, 158, 50);
		panel_cauta.add(butonCauta);

		JLabel lblback1 = new JLabel("");
		lblback1.setBounds(0, 0, 785, 591);
		panel_cauta.add(lblback1);
		lblback1.setIcon(new ImageIcon(img_back2));

		JPanel panel_concediu = new JPanel();
		panel_concediu.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Concediu", null, panel_concediu, null);
		panel_concediu.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 34, 356, 326);
		panel_concediu.add(scrollPane_1);

		table1 = new JTable();
		scrollPane_1.setViewportView(table1);

		JLabel lblNume1 = new JLabel("Nume");
		lblNume1.setForeground(new Color(102, 0, 255));
		lblNume1.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblNume1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNume1.setBounds(423, 34, 158, 50);
		panel_concediu.add(lblNume1);

		JLabel lblPrenume1 = new JLabel("Prenume");
		lblPrenume1.setForeground(new Color(102, 0, 255));
		lblPrenume1.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblPrenume1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrenume1.setBounds(423, 117, 158, 50);
		panel_concediu.add(lblPrenume1);

		JLabel lblFunctie1 = new JLabel("Functie");
		lblFunctie1.setForeground(new Color(102, 0, 255));
		lblFunctie1.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblFunctie1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFunctie1.setBounds(423, 206, 158, 50);
		panel_concediu.add(lblFunctie1);

		nume1 = new JTextField();
		nume1.setBounds(606, 34, 158, 50);
		panel_concediu.add(nume1);
		nume1.setColumns(10);

		prenume1 = new JTextField();
		prenume1.setColumns(10);
		prenume1.setBounds(606, 121, 158, 50);
		panel_concediu.add(prenume1);

		functie1 = new JTextField();
		functie1.setColumns(10);
		functie1.setBounds(606, 210, 158, 50);
		panel_concediu.add(functie1);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute(Interogari.FIND_UTILIZATOR_NPF);
			rs = selectStatement.getResultSet();
			table1.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		Button butonConcedii = new Button("Arata concedii");
		butonConcedii.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean hasParameters = false;
					String numeCautat = nume1.getText();
					String queryParam = "AND ";
					if (!StringUtils.isNullOrEmpty(numeCautat)) {
						queryParam += " nume='" + numeCautat + "' ";
						hasParameters = true;
					}
					String prenumeCautat = prenume1.getText();
					if (!StringUtils.isNullOrEmpty(prenumeCautat)) {
						queryParam += " prenume='" + prenumeCautat + "' ";
						hasParameters = true;
					}
					String functieCautata = functie1.getText();
					if (!StringUtils.isNullOrEmpty(functieCautata)) {
						queryParam += " functie='" + functieCautata + "' ";
						hasParameters = true;
					}
					if (hasParameters == false) {
						queryParam = "";
					}
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					selectStatement.execute(
							"SELECT U.nume, U.prenume, U.functie, C.data_incepere AS 'Inceput Concediu', C.data_incheiere AS 'Sfarsit concediu' from concediu C, utilizator U WHERE C.id_utilizator = U.id_utilizator "
									+ queryParam + " ORDER BY nume ASC");
					rs = selectStatement.getResultSet();
					table1.setModel(DbUtils.resultSetToTableModel(rs));

					nume1.setText("");
					prenume1.setText("");
					functie1.setText("");
					connection.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonConcedii.setFont(new Font("DialogInput", Font.BOLD, 17));
		butonConcedii.setBounds(547, 305, 152, 57);
		panel_concediu.add(butonConcedii);

		primaZi = new JTextField();
		primaZi.setBounds(103, 469, 152, 50);
		panel_concediu.add(primaZi);
		primaZi.setColumns(10);

		aDouaZi = new JTextField();
		aDouaZi.setBounds(322, 466, 158, 57);
		panel_concediu.add(aDouaZi);
		aDouaZi.setColumns(10);

		JLabel lblDateConcediu = new JLabel(
				"In casutele de mai jos introduceti prima zi de concediu si respectiv ultima");
		lblDateConcediu.setForeground(new Color(255, 255, 255));
		lblDateConcediu.setFont(new Font("DialogInput", Font.BOLD, 14));
		lblDateConcediu.setBounds(53, 398, 620, 41);
		panel_concediu.add(lblDateConcediu);

		Button butonAdaugaConcediu = new Button("Adauga Concediu");
		butonAdaugaConcediu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean hasParameters = false;
					String numeCautat = nume1.getText();
					String queryParam = "WHERE ";
					String queryParamV2 = "AND ";
					if (!StringUtils.isNullOrEmpty(numeCautat)) {
						queryParam += " nume='" + numeCautat + "' ";
						queryParamV2 += " nume='" + numeCautat + "' ";
						hasParameters = true;
					}
					String prenumeCautat = prenume1.getText();
					if (!StringUtils.isNullOrEmpty(prenumeCautat)) {
						queryParam += "  AND prenume='" + prenumeCautat + "' ";
						queryParamV2 += " AND prenume='" + prenumeCautat + "' ";
						hasParameters = true;
					}
					String functieCautata = functie1.getText();
					if (!StringUtils.isNullOrEmpty(functieCautata)) {
						queryParam += " AND functie='" + functieCautata + "' ";
						queryParamV2 += " AND functie='" + functieCautata + "' ";
						hasParameters = true;
					}
					if (hasParameters == false) {
						queryParam = "";
						queryParamV2 = "";
					}
					Date date1 = Date.valueOf(primaZi.getText());
					Date date2 = Date.valueOf(aDouaZi.getText());
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					selectStatement.execute("SELECT id_utilizator FROM utilizator " + queryParam);
					rs = selectStatement.getResultSet();
					rs.next();
					int id = rs.getInt("id_utilizator");
					CallableStatement stmt = (CallableStatement) connection
							.prepareCall("{CALL adauga_concediu(?,?,?)}");
					stmt.setDate(1, date1);
					stmt.setDate(2, date2);
					stmt.setInt(3, id);
					rs2 = stmt.executeQuery();
					selectStatement2 = connection.createStatement();
					selectStatement2.execute(
							"SELECT u.nume, u.prenume, u.functie, c.data_incepere AS 'Inceput Concediu', c.data_incheiere AS 'Sfarsit concediu' from concediu c, utilizator u WHERE c.id_utilizator = u.id_utilizator "
									+ queryParamV2);

					nume1.setText("");
					prenume1.setText("");
					functie1.setText("");
					primaZi.setText("");
					aDouaZi.setText("");

					ResultSet rs3 = selectStatement2.getResultSet();
					table1.setModel(DbUtils.resultSetToTableModel(rs3));
					connection.close();

				} catch (SQLException | NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonAdaugaConcediu.setFont(new Font("DialogInput", Font.BOLD, 14));
		butonAdaugaConcediu.setBounds(571, 469, 128, 50);
		panel_concediu.add(butonAdaugaConcediu);

		JLabel lbllinie = new JLabel("-");
		lbllinie.setForeground(new Color(255, 255, 255));
		lbllinie.setHorizontalAlignment(SwingConstants.CENTER);
		lbllinie.setFont(new Font("DialogInput", Font.BOLD, 30));
		lbllinie.setBounds(266, 487, 46, 14);
		panel_concediu.add(lbllinie);

		JLabel lblback2 = new JLabel("");
		lblback2.setBounds(0, 0, 785, 591);
		panel_concediu.add(lblback2);
		lblback2.setIcon(new ImageIcon(img_back2));

		JPanel panel_orar = new JPanel();
		panel_orar.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Orar Saptamanal", null, panel_orar, null);
		panel_orar.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 98, 700, 164);
		panel_orar.add(scrollPane);

		table_1 = new JTable();
		scrollPane.setViewportView(table_1);

		JLabel lblUnitate = new JLabel("Unitate Medicala");
		lblUnitate.setForeground(new Color(255, 255, 255));
		lblUnitate.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblUnitate.setBounds(98, 313, 172, 49);
		panel_orar.add(lblUnitate);

		unitate = new JTextField();
		unitate.setBounds(299, 313, 224, 49);
		panel_orar.add(unitate);
		unitate.setColumns(10);

		Button arataProgram = new Button("Arata Program");
		arataProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String umed = unitate.getText();
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement2 = connection.createStatement();

					selectStatement2.execute(Interogari.FIND_ORAR_UNITATE_MEDICALA
							+ " JOIN unitate_medicala U on U.id_program = P.id_program WHERE U.denumire='" + umed
							+ "'");
					rs = selectStatement2.getResultSet();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
					unitate.setText("");
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		arataProgram.setBounds(560, 313, 151, 49);
		panel_orar.add(arataProgram);

		JPanel panelModificaServicii = new JPanel();
		panelModificaServicii.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelModificaServicii.setBackground(new Color(0, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.addTab("Modifica Servicii", null, panelModificaServicii, null);
		panelModificaServicii.setLayout(null);

		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(50, 62, 700, 302);
		panelModificaServicii.add(scrollPane3);

		JTable table3 = new JTable();
		scrollPane3.setViewportView(table3);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			String queryServicii = "SELECT SM.*, M.titlu_stiintific, U.nume, U.prenume FROM serviciu_medical SM "
					+ "JOIN medic M ON M.id_medic = SM.id_medic "
					+ "JOIN utilizator U ON U.id_utilizator = M.id_utilizator";
			selectStatement.execute(queryServicii);
			rs = selectStatement.getResultSet();
			table3.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JButton btnNewButton = new JButton("Modifica");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table3.getSelectedRow();
				if (selectedRow != -1) {
					int id = Integer.parseInt(table3.getValueAt(selectedRow, 0).toString());
					String denumire = (String) table3.getValueAt(selectedRow, 1);
					String specializare = (String) table3.getValueAt(selectedRow, 2);
					double pret = Double.parseDouble(table3.getValueAt(selectedRow, 3).toString());
					int durata = Integer.parseInt(table3.getValueAt(selectedRow, 4).toString());
					int idMedic = Integer.parseInt(table3.getValueAt(selectedRow, 5).toString());

					try {
						db = new MySQL_Connect();
						connection = db.getConnection();
						selectStatement = connection.createStatement();
						selectStatement.execute("UPDATE serviciu_medical SET pret='" + pret + "' , durata='" + durata
								+ "' WHERE id_medic='" + idMedic + "' AND id_serviciu_medical='" + id + "'");
						connection.close();
						JOptionPane.showMessageDialog(null, "Serviciu modificat cu succes!");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else
					return;
			}
		});
		btnNewButton.setBounds(279, 423, 170, 59);
		panelModificaServicii.add(btnNewButton);

		JLabel lblback3 = new JLabel("");
		lblback3.setBounds(0, 0, 785, 591);
		panel_orar.add(lblback3);
		lblback3.setIcon(new ImageIcon(img_back2));

		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 790, 630);
		lbBackground.setIcon(new ImageIcon(img_back));
		add(lbBackground);
	}
}
