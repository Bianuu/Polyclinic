package administrare;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.util.StringUtils;

import db.Interogari;
import db.MySQL_Connect;
import modele.Utilizator;
import net.proteanit.sql.DbUtils;
import pagini.PaginaPrincipala;

public class Meniu_Administrare extends JPanel {
	private final Image img_back = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/admin_back.jpg"))
			.getImage().getScaledInstance(790, 630, Image.SCALE_SMOOTH);
	private final JTable table;
	// Conexiune Baza de date
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null, stmt = null, selectStatement1 = null, stmt2 = null;
	private CallableStatement selectStatement2 = null;
	ResultSet rs = null, rs1 = null, rs2 = null;
	private JTextField nume;
	private JTextField prenume;
	private JTextField CNP;
	private JTextField adresa;
	private JTextField nrTelefon;
	private JTextField email;
	private JTextField IBAN;
	private JTextField nrContract;
	private JTextField dataAngajarii;
	private JTextField salariuNegociat;
	private JTextField nrOreContract;
	private JTextField username;
	private JPasswordField parola;

	public Meniu_Administrare(Utilizator utilizatorCurent) {
		setSize(790, 630);
		setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("DialogInput", Font.BOLD, 18));
		tabbedPane.setBackground(new Color(0, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(new Color(0, 102, 255));
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panel_utilizatori = new JPanel();
		panel_utilizatori.setFont(new Font("DialogInput", Font.BOLD, 18));
		panel_utilizatori.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Utilizatori", null, panel_utilizatori, null);
		panel_utilizatori.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 170, 770, 301);
		panel_utilizatori.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		Button butonAngajati = new Button("Afiseaza Angajati");
		butonAngajati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					selectStatement.execute(Interogari.FIND_UTILIZATORI_FARA_ROL_ADMIN);
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonAngajati.setBounds(255, 91, 238, 48);
		panel_utilizatori.add(butonAngajati);

		Button butonModificare = new Button("Modifica Angajat");
		butonModificare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
//					int nrColoane = table.getColumnCount();
					int linie_edit = table.getSelectedRow();
					if (linie_edit != -1) {
						int id = (int) table.getValueAt(linie_edit, 0);
						String nume = (String) table.getValueAt(linie_edit, 1);
						String prenume = (String) table.getValueAt(linie_edit, 2);
						String cnp = (String) table.getValueAt(linie_edit, 3);
						String adresa = (String) table.getValueAt(linie_edit, 4);
						String n_tel = (String) table.getValueAt(linie_edit, 5);
						String email = (String) table.getValueAt(linie_edit, 6);
						String iban = (String) table.getValueAt(linie_edit, 7);
						int contract = (int) table.getValueAt(linie_edit, 8);
						Date data_angajarii = (Date) table.getValueAt(linie_edit, 9);
						String functie = (String) table.getValueAt(linie_edit, 10);
						double sal = (double) table.getValueAt(linie_edit, 11);
						int ore = (int) table.getValueAt(linie_edit, 12);
						System.out.println(functie);
						if (functie.equals("Admin") || functie.equals("Super Admin")) {
							JOptionPane.showMessageDialog(null,
									"Nu se poate modifica functia in Admin sau Super Admin!");
							return;
						}

						selectStatement2 = (CallableStatement) connection
								.prepareCall("{call modifica_utilizator(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						selectStatement2.setInt(1, id);
						selectStatement2.setString(2, nume);
						selectStatement2.setString(3, prenume);
						selectStatement2.setString(4, cnp);
						selectStatement2.setString(5, adresa);
						selectStatement2.setString(6, n_tel);
						selectStatement2.setString(7, email);
						selectStatement2.setString(8, iban);
						selectStatement2.setInt(9, contract);
						selectStatement2.setDate(10, data_angajarii);
						selectStatement2.setString(11, functie);
						selectStatement2.setDouble(12, sal);
						selectStatement2.setInt(13, ore);
						selectStatement2.executeUpdate();
						selectStatement2.close();
						stmt2 = connection.createStatement();
						stmt2.execute(Interogari.FIND_UTILIZATORI_FARA_ROL_ADMIN);
						rs2 = stmt2.getResultSet();
						table.setModel(DbUtils.resultSetToTableModel(rs2));
						connection.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonModificare.setBounds(137, 511, 170, 48);
		panel_utilizatori.add(butonModificare);

		Button butonSterge = new Button("Sterge Angajat");
		butonSterge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement1 = connection.createStatement();
					int linie_sters = table.getSelectedRow();
					int idUtilizator = (int) table.getValueAt(linie_sters, 0);
					selectStatement1.execute("DELETE FROM utilizator WHERE id_utilizator='" + idUtilizator + "';");
					stmt = connection.createStatement();
					stmt.execute(Interogari.FIND_UTILIZATORI_FARA_ROL_ADMIN);
					rs1 = stmt.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs1));
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonSterge.setBounds(463, 511, 170, 48);
		panel_utilizatori.add(butonSterge);

		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 790, 630);
		lbBackground.setIcon(new ImageIcon(img_back));
		add(lbBackground);

		JPanel panel_adauga_utilizator = new JPanel();
		panel_adauga_utilizator.setFont(new Font("DialogInput", Font.BOLD, 18));
		panel_adauga_utilizator.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Adauga utilizator", null, panel_adauga_utilizator, null);
		panel_adauga_utilizator.setLayout(null);

		JLabel lblNume = new JLabel("Nume");
		lblNume.setForeground(Color.WHITE);
		lblNume.setHorizontalAlignment(SwingConstants.CENTER);
		lblNume.setBounds(237, 24, 101, 28);
		panel_adauga_utilizator.add(lblNume);

		nume = new JTextField();
		nume.setBounds(350, 24, 161, 28);
		panel_adauga_utilizator.add(nume);
		nume.setColumns(10);

		JLabel lblPrenume = new JLabel("Prenume");
		lblPrenume.setForeground(Color.WHITE);
		lblPrenume.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrenume.setBounds(237, 64, 101, 28);
		panel_adauga_utilizator.add(lblPrenume);

		prenume = new JTextField();
		prenume.setColumns(10);
		prenume.setBounds(350, 64, 161, 28);
		panel_adauga_utilizator.add(prenume);

		JLabel lblCNP = new JLabel("CNP");
		lblCNP.setForeground(Color.WHITE);
		lblCNP.setHorizontalAlignment(SwingConstants.CENTER);
		lblCNP.setBounds(237, 104, 101, 28);
		panel_adauga_utilizator.add(lblCNP);

		CNP = new JTextField();
		CNP.setColumns(10);
		CNP.setBounds(350, 104, 161, 28);
		panel_adauga_utilizator.add(CNP);

		adresa = new JTextField();
		adresa.setColumns(10);
		adresa.setBounds(350, 144, 161, 28);
		panel_adauga_utilizator.add(adresa);

		JLabel lblAdresa = new JLabel("Adresa");
		lblAdresa.setForeground(Color.WHITE);
		lblAdresa.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdresa.setBounds(237, 144, 101, 28);
		panel_adauga_utilizator.add(lblAdresa);

		JLabel lblNrTelefon = new JLabel("Nr.Telefon");
		lblNrTelefon.setForeground(Color.WHITE);
		lblNrTelefon.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrTelefon.setBounds(237, 184, 101, 28);
		panel_adauga_utilizator.add(lblNrTelefon);

		nrTelefon = new JTextField();
		nrTelefon.setColumns(10);
		nrTelefon.setBounds(350, 184, 161, 28);
		panel_adauga_utilizator.add(nrTelefon);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(237, 224, 101, 28);
		panel_adauga_utilizator.add(lblEmail);

		email = new JTextField();
		email.setColumns(10);
		email.setBounds(350, 224, 161, 28);
		panel_adauga_utilizator.add(email);

		JLabel ibanLabel = new JLabel("IBAN");
		ibanLabel.setForeground(Color.WHITE);
		ibanLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ibanLabel.setBounds(237, 264, 101, 28);
		panel_adauga_utilizator.add(ibanLabel);

		IBAN = new JTextField();
		IBAN.setColumns(10);
		IBAN.setBounds(350, 264, 161, 28);
		panel_adauga_utilizator.add(IBAN);

		JLabel lblNrContract = new JLabel("Nr.Contract");
		lblNrContract.setForeground(Color.WHITE);
		lblNrContract.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrContract.setBounds(237, 304, 101, 28);
		panel_adauga_utilizator.add(lblNrContract);

		nrContract = new JTextField();
		nrContract.setColumns(10);
		nrContract.setBounds(350, 304, 161, 28);
		panel_adauga_utilizator.add(nrContract);

		JLabel lblDataAngajarii = new JLabel("Data angajarii");
		lblDataAngajarii.setForeground(Color.WHITE);
		lblDataAngajarii.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataAngajarii.setBounds(237, 340, 101, 28);
		panel_adauga_utilizator.add(lblDataAngajarii);

		dataAngajarii = new JTextField();
		dataAngajarii.setColumns(10);
		dataAngajarii.setBounds(350, 340, 161, 28);
		panel_adauga_utilizator.add(dataAngajarii);

		JLabel lblSalariuNegociat = new JLabel("Salariu negociat");
		lblSalariuNegociat.setForeground(Color.WHITE);
		lblSalariuNegociat.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalariuNegociat.setBounds(237, 380, 101, 28);
		panel_adauga_utilizator.add(lblSalariuNegociat);

		salariuNegociat = new JTextField();
		salariuNegociat.setColumns(10);
		salariuNegociat.setBounds(350, 380, 161, 28);
		panel_adauga_utilizator.add(salariuNegociat);

		JLabel lblNrOreContract = new JLabel("Nr. ore contract");
		lblNrOreContract.setForeground(Color.WHITE);
		lblNrOreContract.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrOreContract.setBounds(237, 420, 101, 28);
		panel_adauga_utilizator.add(lblNrOreContract);

		nrOreContract = new JTextField();
		nrOreContract.setColumns(10);
		nrOreContract.setBounds(350, 420, 161, 28);
		panel_adauga_utilizator.add(nrOreContract);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(237, 460, 101, 28);
		panel_adauga_utilizator.add(lblUsername);

		username = new JTextField();
		username.setColumns(10);
		username.setBounds(350, 460, 161, 28);
		panel_adauga_utilizator.add(username);

		JLabel lblParola = new JLabel("Parola");
		lblParola.setForeground(Color.WHITE);
		lblParola.setHorizontalAlignment(SwingConstants.CENTER);
		lblParola.setBounds(237, 500, 101, 28);
		panel_adauga_utilizator.add(lblParola);

		parola = new JPasswordField();
		parola.setBounds(350, 500, 161, 28);
		panel_adauga_utilizator.add(parola);

		JLabel lbBackgroundPanel = new JLabel("");
		lbBackgroundPanel.setBounds(0, 0, 790, 630);
		panel_utilizatori.add(lbBackgroundPanel);
		lbBackgroundPanel.setIcon(new ImageIcon(img_back));

		JLabel lblFunctie = new JLabel("Functie");
		lblFunctie.setHorizontalAlignment(SwingConstants.CENTER);
		lblFunctie.setForeground(Color.WHITE);
		lblFunctie.setBounds(237, 540, 101, 28);
		panel_adauga_utilizator.add(lblFunctie);

		JComboBox comboFunctie = new JComboBox();
		comboFunctie.setBounds(350, 540, 161, 28);
		comboFunctie.setBorder(null);
		comboFunctie.setOpaque(false);
		comboFunctie.setModel(new DefaultComboBoxModel(new String[] { "-Alege-", "Asistent medical", "Medic",
				"Inspector resurse umane", "Expert financiar contabil", "Receptioner" }));
		panel_adauga_utilizator.add(comboFunctie);

		JButton btnCreare = new JButton("Creare utilizator");
		btnCreare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (parola.getText().equals("") || username.getText().equals("") || email.getText().equals("")
						|| comboFunctie.getSelectedItem().toString() == "-Alege-" || nume.getText().equals("")
						|| prenume.getText().equals("") || CNP.getText().equals("") || IBAN.getText().equals("")
						|| nrContract.getText().equals("") || dataAngajarii.getText().equals("")
						|| nrTelefon.getText().equals("") || nrOreContract.getText().equals("")
						|| salariuNegociat.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Nu ai introdus una dintre date!\nVerifica din nou!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (parola.getText().length() < 4) {
					JOptionPane.showMessageDialog(null, "Parola trebuie sa contina minim 4 caractere", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (StringUtils.isStrictlyNumeric(CNP.getText()) != true || CNP.getText().length() != 13) {
					JOptionPane.showMessageDialog(null, "CNP-ul trebuie sa contina exact 13 cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (StringUtils.isStrictlyNumeric(nrTelefon.getText()) != true || nrTelefon.getText().length() != 10) {
					JOptionPane.showMessageDialog(null, "Numarul de telefon trebuie sa contina exact 10 cifre!",
							"Eroare", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (StringUtils.isStrictlyNumeric(nrContract.getText()) != true) {
					JOptionPane.showMessageDialog(null, "Nr. de contract trebuie sa contina doar cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!validPattern(dataAngajarii.getText())) {
					JOptionPane.showMessageDialog(null, "Formatul pentru data este incorect!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (!validEmail(email.getText())) {
					JOptionPane.showMessageDialog(null, "Ai introdus o adresa de email incorecta!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (nume.getText().matches(".*\\d.*")) {
					JOptionPane.showMessageDialog(null, "Numele nu poate contine cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (prenume.getText().matches(".*\\d.*")) {
					JOptionPane.showMessageDialog(null, "Prenumele nu poate contine cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (adresa.getText().length() < 1) {
					JOptionPane.showMessageDialog(null, "Adresa nu poate sa fie goala!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (StringUtils.isStrictlyNumeric(salariuNegociat.getText()) != true) {
					JOptionPane.showMessageDialog(null, "Salariu negociat trebuie sa contina doar cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (StringUtils.isStrictlyNumeric(nrOreContract.getText()) != true) {
					JOptionPane.showMessageDialog(null, "Nr. de ore din contract trebuie sa contina doar cifre!",
							"Eroare", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement1 = connection.createStatement();
					java.sql.Date date1 = java.sql.Date.valueOf(dataAngajarii.getText());
					String query = "INSERT INTO utilizator (nume, prenume, CNP, adresa, nr_telefon, email, IBAN, nr_contract, data_angajarii, functie, salariu_negociat, nr_ore, user, parola)"
							+ " VALUES (?, ?, ?, ?, ?, ? , ? ,? ,? ,? ,? ,? ,? ,?)";
					String functie = comboFunctie.getSelectedItem().toString();
					PreparedStatement preparedStmt = connection.prepareStatement(query);
					preparedStmt.setString(1, nume.getText());
					preparedStmt.setString(2, prenume.getText());
					preparedStmt.setString(3, CNP.getText());
					preparedStmt.setString(4, adresa.getText());
					preparedStmt.setString(5, nrTelefon.getText());
					preparedStmt.setString(6, email.getText());
					preparedStmt.setString(7, IBAN.getText());
					preparedStmt.setInt(8, Integer.parseInt(nrContract.getText()));
					preparedStmt.setDate(9, date1);
					preparedStmt.setString(10, functie);
					preparedStmt.setDouble(11, Double.parseDouble(salariuNegociat.getText()));
					preparedStmt.setInt(12, Integer.parseInt(nrOreContract.getText()));
					preparedStmt.setString(13, username.getText());
					preparedStmt.setString(14, parola.getText());

					preparedStmt.execute();
					preparedStmt.close();
					JOptionPane.showMessageDialog(null, "Utilizatorul a fost creat cu succes!", "Succes",
							JOptionPane.INFORMATION_MESSAGE);
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCreare.setBounds(562, 501, 161, 48);
		panel_adauga_utilizator.add(btnCreare);

		JLabel lbBackgroundPanel2 = new JLabel("");
		lbBackgroundPanel2.setBounds(0, 0, 790, 630);
		lbBackgroundPanel2.setIcon(new ImageIcon(img_back));
		panel_adauga_utilizator.add(lbBackgroundPanel2);

	}

	public static boolean validPattern(String s) {
		return s.matches("\\d{4}-\\d{2}-\\d{2}");
	}

	public static boolean validEmail(String s) {
		return s.matches(".*@.*\\..*");
	}
}
