package pagini;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.util.StringUtils;

import db.MySQL_Connect;
import modele.Roluri;

public class CreareCont {
	private final Image img_background = new ImageIcon(CreareCont.class.getResource("../resurse/creare_back.jpg"))
			.getImage().getScaledInstance(888, 780, Image.SCALE_SMOOTH);
	PaginaLogare windowStart;
	JFrame frameCreareCont;
	private MySQL_Connect db;
	private Connection con;
	private Statement selectStatement = null;
	private CallableStatement stmt = null;
	private ResultSet rs = null;
	private JTextField CNP;
	private JTextField Nume;
	private JTextField Prenume;
	private JTextField Nr_telefon;
	private JTextField Email;
	private JTextField IBAN;
	private JTextField nrContract;
	private JTextField DataAngajarii;
	private JTextField user;
	private JPasswordField parola;
	private int ok = 0;
	private JTextField adresa;

	public CreareCont() {
		initialize();
	}

	private void initialize() {
		frameCreareCont = new JFrame();
		frameCreareCont.setBounds(100, 100, 894, 804);
		frameCreareCont.setResizable(false);
		frameCreareCont.setTitle("POLICLINICI by CIOBAN FABIAN-REMUS & POJAR ANDREI-GABRIEL");
		frameCreareCont.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameCreareCont.setIconImage(
				Toolkit.getDefaultToolkit().getImage(CreareCont.class.getResource("../resurse/logo_app.jpg")));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frameCreareCont.setLocation(dim.width / 2 - frameCreareCont.getSize().width / 2,
				dim.height / 2 - frameCreareCont.getSize().height / 2);
		frameCreareCont.getContentPane().setLayout(null);

		JLabel lblreg = new JLabel("Formular de \u00EEnregistrare");
		lblreg.setBounds(261, 11, 342, 68);
		lblreg.setForeground(new Color(0, 0, 0));
		lblreg.setFont(new Font("DialogInput", Font.BOLD, 24));
		lblreg.setHorizontalAlignment(SwingConstants.CENTER);
		frameCreareCont.getContentPane().add(lblreg);

		JButton btnLogare = new JButton("Logare");
		btnLogare.setBounds(37, 702, 211, 49);
		btnLogare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameCreareCont.dispose();
				windowStart = new PaginaLogare();
				windowStart.getFrame().setVisible(true);
			}
		});
		btnLogare.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogare.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frameCreareCont.getContentPane().add(btnLogare);

		CNP = new JTextField();
		CNP.setFont(new Font("DialogInput", Font.BOLD, 14));
		CNP.setBounds(224, 312, 170, 50);
		frameCreareCont.getContentPane().add(CNP);
		CNP.setColumns(10);

		JLabel lblCNP = new JLabel("CNP");
		lblCNP.setForeground(new Color(255, 215, 0));
		lblCNP.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCNP.setHorizontalAlignment(SwingConstants.CENTER);
		lblCNP.setBounds(108, 310, 106, 50);
		frameCreareCont.getContentPane().add(lblCNP);

		Nume = new JTextField();
		Nume.setFont(new Font("DialogInput", Font.BOLD, 14));
		Nume.setColumns(10);
		Nume.setBounds(224, 215, 170, 50);
		frameCreareCont.getContentPane().add(Nume);

		Prenume = new JTextField();
		Prenume.setFont(new Font("DialogInput", Font.BOLD, 14));
		Prenume.setColumns(10);
		Prenume.setBounds(637, 215, 170, 50);
		frameCreareCont.getContentPane().add(Prenume);

		Nr_telefon = new JTextField();
		Nr_telefon.setFont(new Font("DialogInput", Font.BOLD, 14));
		Nr_telefon.setColumns(10);
		Nr_telefon.setBounds(637, 312, 170, 50);
		frameCreareCont.getContentPane().add(Nr_telefon);

		JLabel lblNume = new JLabel("Nume");
		lblNume.setHorizontalAlignment(SwingConstants.CENTER);
		lblNume.setForeground(new Color(255, 215, 0));
		lblNume.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNume.setBounds(108, 215, 106, 50);
		frameCreareCont.getContentPane().add(lblNume);

		JLabel lblPrenume = new JLabel("Prenume");
		lblPrenume.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrenume.setForeground(new Color(255, 215, 0));
		lblPrenume.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPrenume.setBounds(505, 215, 106, 50);
		frameCreareCont.getContentPane().add(lblPrenume);

		JLabel lblNrtelefon = new JLabel("Nr.Telefon");
		lblNrtelefon.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrtelefon.setForeground(new Color(255, 215, 0));
		lblNrtelefon.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNrtelefon.setBounds(497, 310, 106, 50);
		frameCreareCont.getContentPane().add(lblNrtelefon);

		Email = new JTextField();
		Email.setFont(new Font("DialogInput", Font.BOLD, 14));
		Email.setColumns(10);
		Email.setBounds(224, 413, 170, 50);
		frameCreareCont.getContentPane().add(Email);

		IBAN = new JTextField();
		IBAN.setFont(new Font("DialogInput", Font.BOLD, 14));
		IBAN.setColumns(10);
		IBAN.setBounds(637, 413, 170, 50);
		frameCreareCont.getContentPane().add(IBAN);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(new Color(255, 215, 0));
		lblEmail.setFont(new Font("Dialog", Font.BOLD, 18));
		lblEmail.setBounds(108, 413, 106, 50);
		frameCreareCont.getContentPane().add(lblEmail);

		JLabel lblIban = new JLabel("IBAN");
		lblIban.setHorizontalAlignment(SwingConstants.CENTER);
		lblIban.setForeground(new Color(255, 215, 0));
		lblIban.setFont(new Font("Dialog", Font.BOLD, 18));
		lblIban.setBounds(505, 411, 106, 50);
		frameCreareCont.getContentPane().add(lblIban);

		nrContract = new JTextField();
		nrContract.setFont(new Font("DialogInput", Font.BOLD, 14));
		nrContract.setColumns(10);
		nrContract.setBounds(224, 510, 170, 50);
		frameCreareCont.getContentPane().add(nrContract);

		JLabel lblNrcontract = new JLabel("Nr.Contract");
		lblNrcontract.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrcontract.setForeground(new Color(255, 215, 0));
		lblNrcontract.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNrcontract.setBounds(108, 510, 106, 50);
		frameCreareCont.getContentPane().add(lblNrcontract);

		DataAngajarii = new JTextField();
		DataAngajarii.setFont(new Font("DialogInput", Font.BOLD, 14));
		DataAngajarii.setColumns(10);
		DataAngajarii.setBounds(637, 510, 170, 50);
		frameCreareCont.getContentPane().add(DataAngajarii);

		JLabel lblDataAngajarii = new JLabel("Data Angajarii");
		lblDataAngajarii.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataAngajarii.setForeground(new Color(255, 215, 0));
		lblDataAngajarii.setFont(new Font("Dialog", Font.BOLD, 16));
		lblDataAngajarii.setBounds(452, 510, 140, 50);
		frameCreareCont.getContentPane().add(lblDataAngajarii);

		JComboBox functie = new JComboBox();
		functie.setBounds(570, 596, 263, 49);
		functie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		functie.setBorder(null);
		functie.setOpaque(false);
		functie.setModel(new DefaultComboBoxModel(new String[] { "-Alege-", "Super Admin", "Admin", "Asistent medical",
				"Medic", "Inspector resurse umane", "Expert financiar contabil", "Receptioner" }));
		frameCreareCont.getContentPane().add(functie);

		JLabel lblFunctie = new JLabel("Functie");
		lblFunctie.setHorizontalAlignment(SwingConstants.CENTER);
		lblFunctie.setForeground(new Color(255, 215, 0));
		lblFunctie.setFont(new Font("Dialog", Font.BOLD, 18));
		lblFunctie.setBounds(434, 595, 106, 50);
		frameCreareCont.getContentPane().add(lblFunctie);

		user = new JTextField();
		user.setFont(new Font("DialogInput", Font.BOLD, 14));
		user.setColumns(10);
		user.setBounds(224, 127, 170, 50);
		frameCreareCont.getContentPane().add(user);

		JLabel lblUser = new JLabel("User");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setForeground(new Color(255, 215, 0));
		lblUser.setFont(new Font("Dialog", Font.BOLD, 18));
		lblUser.setBounds(108, 127, 106, 50);
		frameCreareCont.getContentPane().add(lblUser);

		parola = new JPasswordField();
		parola.setBounds(638, 127, 169, 49);
		frameCreareCont.getContentPane().add(parola);

		JLabel lblParola = new JLabel("Parola");
		lblParola.setHorizontalAlignment(SwingConstants.CENTER);
		lblParola.setForeground(new Color(255, 215, 0));
		lblParola.setFont(new Font("Dialog", Font.BOLD, 18));
		lblParola.setBounds(505, 127, 106, 50);
		frameCreareCont.getContentPane().add(lblParola);

		JButton btnInregistrare = new JButton("Inregistrare");
		btnInregistrare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int verificare = 1;
				String fct = functie.getSelectedItem().toString();
				if (parola.getText().equals("") || user.getText().equals("") || Email.getText().equals("")
						|| fct == "-Alege-" || Nume.getText().equals("") || Prenume.getText().equals("")
						|| CNP.getText().equals("") || IBAN.getText().equals("") || nrContract.getText().equals("")
						|| DataAngajarii.getText().equals("") || Nr_telefon.getText().equals("")) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Nu ai introdus una dintre date!\nVerifica din nou!",
							"Eroare", JOptionPane.ERROR_MESSAGE);
				} else if (parola.getText().length() < 4) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Parola trebuie sa contina minim 4 caractere",
							"Eroare", JOptionPane.ERROR_MESSAGE);
				} else if (StringUtils.isStrictlyNumeric(CNP.getText()) != true || CNP.getText().length() != 13) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "CNP-ul trebuie sa contina exact 13 cifre!",
							"Eroare", JOptionPane.ERROR_MESSAGE);
				} else if (StringUtils.isStrictlyNumeric(Nr_telefon.getText()) != true
						|| Nr_telefon.getText().length() != 10) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont,
							"Numarul de telefon trebuie sa contina exact 10 cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
				} else if (StringUtils.isStrictlyNumeric(nrContract.getText()) != true) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Nr. de contract trebuie sa contina doar cifre!",
							"Eroare", JOptionPane.ERROR_MESSAGE);
				} else if (!validPattern(DataAngajarii.getText())) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Formatul pentru data este incorect!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
				} else if (!validEmail(Email.getText())) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Ai introdus o adresa de email incorecta!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
				} else if (Nume.getText().matches(".*\\d.*")) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Numele nu poate contine cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
				} else if (Prenume.getText().matches(".*\\d.*")) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Prenumele nu poate contine cifre!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
				} else if (adresa.getText().length() < 1) {
					verificare = 0;
					JOptionPane.showMessageDialog(frameCreareCont, "Adresa nu poate sa fie goala!", "Eroare",
							JOptionPane.ERROR_MESSAGE);
				}
				int aux_iesire = -1;
				if (verificare == 0) {
					return;
				}
				if (verificare == 1) {
					try {
						db = new MySQL_Connect();
						con = db.getConnection();
						selectStatement = con.createStatement();
						selectStatement.execute("SELECT * FROM utilizator");
						rs = selectStatement.getResultSet();
						ok = 1;
						while (rs.next()) {
							if (user.getText().equals(rs.getString("user"))) {
								ok = 0;
								break;
							}
						}
						if (ok == 1) {
							if (functie.getSelectedItem().toString().equals(Roluri.ROL_SUPERADMIN)) {
								String cod = JOptionPane.showInputDialog(frameCreareCont,
										"Introduceti codul unic dedicat Super Administratorilor");
								int c1 = Integer.parseInt(cod);
								if (c1 != 1111)
									JOptionPane.showMessageDialog(frameCreareCont,
											"Ai introdus gresit codul unic Super Adminilor!", "Eroare", 0);
								else {
									java.sql.Date date1 = java.sql.Date.valueOf(DataAngajarii.getText());
									stmt = (CallableStatement) con
											.prepareCall("{call adauga_utilizator(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
									stmt.setString(1, Nume.getText());
									stmt.setString(2, Prenume.getText());
									stmt.setString(3, CNP.getText());
									stmt.setString(4, adresa.getText());
									stmt.setString(5, Nr_telefon.getText());
									stmt.setString(6, Email.getText());
									stmt.setString(7, IBAN.getText());
									stmt.setInt(8, Integer.parseInt(nrContract.getText()));
									stmt.setDate(9, date1);
									stmt.setString(10, functie.getSelectedItem().toString());
									stmt.setDouble(11, 2500.0);
									stmt.setInt(12, 160);
									stmt.setString(13, user.getText());
									stmt.setString(14, parola.getText());

									stmt.executeUpdate();
									stmt.close();
									JOptionPane.showMessageDialog(frameCreareCont, "Contul a fost creat cu succes!");
									aux_iesire = 0;
								}
							} else if (functie.getSelectedItem().toString().equals(Roluri.ROL_ADMIN)) {
								String cod2 = JOptionPane.showInputDialog(frameCreareCont,
										"Introduceti codul unic dedicat Adminilor");
								int c2 = Integer.parseInt(cod2);

								if (c2 != 0000)
									JOptionPane.showMessageDialog(frameCreareCont,
											"Ai introdus gresit codul unic Adminilor!");

								else {
									java.sql.Date date1 = java.sql.Date.valueOf(DataAngajarii.getText());
									stmt = (CallableStatement) con
											.prepareCall("{call adauga_utilizator(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
									stmt.setString(1, Nume.getText());
									stmt.setString(2, Prenume.getText());
									stmt.setString(3, CNP.getText());
									stmt.setString(4, adresa.getText());
									stmt.setString(5, Nr_telefon.getText());
									stmt.setString(6, Email.getText());
									stmt.setString(7, IBAN.getText());
									stmt.setInt(8, Integer.parseInt(nrContract.getText()));
									stmt.setDate(9, date1);
									stmt.setString(10, functie.getSelectedItem().toString());
									stmt.setDouble(11, 2500.0);
									stmt.setInt(12, 160);
									stmt.setString(13, user.getText());
									stmt.setString(14, parola.getText());
									stmt.executeUpdate();
									stmt.close();
									JOptionPane.showMessageDialog(frameCreareCont, "Contul a fost creat cu succes!",
											"Succes", 3);
									aux_iesire = 0;
								}
							} else {
								java.sql.Date date1 = java.sql.Date.valueOf(DataAngajarii.getText());
								System.out.println(functie.getSelectedItem().toString());
								stmt = (CallableStatement) con
										.prepareCall("{call adauga_utilizator(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
								stmt.setString(1, Nume.getText());
								stmt.setString(2, Prenume.getText());
								stmt.setString(3, CNP.getText());
								stmt.setString(4, adresa.getText());
								stmt.setString(5, Nr_telefon.getText());
								stmt.setString(6, Email.getText());
								stmt.setString(7, IBAN.getText());
								stmt.setInt(8, Integer.parseInt(nrContract.getText()));
								stmt.setDate(9, date1);
								stmt.setString(10, functie.getSelectedItem().toString());
								stmt.setDouble(11, 2500.0);
								stmt.setInt(12, 160);
								stmt.setString(13, user.getText());
								stmt.setString(14, parola.getText());

								stmt.executeUpdate();
								stmt.close();
								JOptionPane.showMessageDialog(frameCreareCont, "Contul a fost creat cu succes!",
										"Succes", JOptionPane.INFORMATION_MESSAGE);
								aux_iesire = 0;
							}
						}
						if (ok == 0)
							JOptionPane.showMessageDialog(frameCreareCont, "User-ul exista deja!", "Eroare",
									JOptionPane.ERROR_MESSAGE);
						if (aux_iesire == 0) {
							frameCreareCont.dispose();
							windowStart = new PaginaLogare();
							windowStart.getFrame().setVisible(true);
						}
					} catch (SQLException | NumberFormatException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnInregistrare.setHorizontalTextPosition(SwingConstants.CENTER);
		btnInregistrare.setFont(new Font("DialogInput", Font.PLAIN, 20));
		btnInregistrare.setBounds(369, 686, 211, 49);
		frameCreareCont.getContentPane().add(btnInregistrare);

		JLabel lblAdresa = new JLabel("Adresa");
		lblAdresa.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdresa.setForeground(new Color(255, 215, 0));
		lblAdresa.setFont(new Font("Dialog", Font.BOLD, 18));
		lblAdresa.setBounds(108, 596, 106, 50);
		frameCreareCont.getContentPane().add(lblAdresa);

		adresa = new JTextField();
		adresa.setFont(new Font("DialogInput", Font.BOLD, 14));
		adresa.setColumns(10);
		adresa.setBounds(224, 596, 170, 50);
		frameCreareCont.getContentPane().add(adresa);

		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 888, 776);
		lbBackground.setFont(new Font("DialogInput", Font.BOLD, 30));
		lbBackground.setIcon(new ImageIcon(img_background));
		frameCreareCont.getContentPane().add(lbBackground);
	}

	public static boolean validPattern(String s) {
		return s.matches("\\d{4}-\\d{2}-\\d{2}");
	}

	public static boolean validEmail(String s) {
		return s.matches(".*@.*\\..*");
	}
}
