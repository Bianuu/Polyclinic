package pagini;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db.Interogari;
import db.MySQL_Connect;
import modele.Utilizator;

public class PaginaLogare {
	private JFrame frame;
	// Conexiune Baza de date
	private MySQL_Connect db;
	private Connection connection;
	private Statement selectStatement = null;
	private ResultSet rs = null;

	// Imagini background , logo etc.
	private final Image img_background = new ImageIcon(PaginaLogare.class.getResource("../resurse/background.jpg"))
			.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
	private final Image img_user = new ImageIcon(PaginaLogare.class.getResource("../resurse/user.png")).getImage()
			.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private final Image img_password = new ImageIcon(PaginaLogare.class.getResource("../resurse/password.png"))
			.getImage().getScaledInstance(70, 50, Image.SCALE_SMOOTH);
	//
	private JTextField user_text;
	private JPasswordField password;
	private PaginaPrincipala windowLogare;

	public PaginaLogare() {
		initialize();
	}


	private void initialize() {
		
	
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setTitle("POLICLINICA by CIOBAN FABIAN-REMUS & POJAR ANDREI-GABRIEL");
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(PaginaLogare.class.getResource("../resurse/logo_app.jpg")));
		frame.getContentPane().setLayout(null);

		JLabel descriere = new JLabel("RESPECT PENTRU VIATA");
		descriere.setFont(new Font("DialogInput", Font.BOLD, 38));
		descriere.setForeground(new Color(255, 0, 0));
		descriere.setHorizontalAlignment(SwingConstants.LEFT);
		descriere.setBounds(20, 85, 655, 103);
		frame.getContentPane().add(descriere);
		
		
		user_text = new JTextField();
		user_text.setForeground(new Color(30, 144, 255));
		user_text.setFont(new Font("DialogInput", Font.BOLD, 20));
		user_text.setBounds(274, 284, 182, 43);
		frame.getContentPane().add(user_text);
		user_text.setColumns(10);

		password = new JPasswordField();
		password.setFont(new Font("DialogInput", Font.BOLD, 16));
		password.setBounds(274, 378, 182, 43);
		frame.getContentPane().add(password);

		JLabel lblUser = new JLabel("Utilizator");
		lblUser.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblUser.setForeground(new Color(240, 255, 255));
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(97, 285, 155, 43);
		frame.getContentPane().add(lblUser);

		JLabel lblPass = new JLabel("Parol\u0103");
		lblPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblPass.setForeground(new Color(240, 255, 255));
		lblPass.setFont(new Font("DialogInput", Font.BOLD, 22));
		lblPass.setBounds(97, 378, 155, 43);
		frame.getContentPane().add(lblPass);

		JLabel lbIconUser = new JLabel("");
		lbIconUser.setHorizontalAlignment(SwingConstants.CENTER);
		lbIconUser.setBounds(45, 275, 50, 52);
		lbIconUser.setIcon(new ImageIcon(img_user));
		frame.getContentPane().add(lbIconUser);

		JLabel lbIconPassword = new JLabel("");
		lbIconPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lbIconPassword.setBounds(45, 363, 60, 73);
		lbIconPassword.setIcon(new ImageIcon(img_password));
		frame.getContentPane().add(lbIconPassword);

		Button buttonLogare = new Button("Logare");
		buttonLogare.setBackground(new Color(106, 90, 205));
		buttonLogare.setForeground(new Color(0, 191, 255));
		buttonLogare.setFont(new Font("DialogInput", Font.BOLD, 24));
		buttonLogare.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					selectStatement.execute(Interogari.FIND_UTILIZATORI);
					rs = selectStatement.getResultSet();
					int ok = 0;
					while (rs.next()) {
						if (user_text.getText().equals(rs.getString("user"))
								&& password.getText().equals(rs.getString("parola"))) {
							int id = rs.getInt("id_utilizator");
							String user = user_text.getText();
							String nume = rs.getString("nume");
							String prenume = rs.getString("prenume");
							String CNP = rs.getString("CNP");
							String adresa = rs.getString("adresa");
							String nrTelefon = rs.getString("nr_telefon");
							String email = rs.getString("email");
							String IBAN = rs.getString("IBAN");
							int nrContract = rs.getInt("nr_contract");
							Date dataAngajarii = rs.getDate("data_angajarii");
							String functie = rs.getString("functie");
							double salariuNegociat = rs.getDouble("salariu_negociat");
							int nrOre = rs.getInt("nr_ore");
							String username = rs.getString("user");

							Utilizator utilizator = new Utilizator(id, nume, prenume, CNP, adresa, nrTelefon, email,
									IBAN, nrContract, dataAngajarii, functie, salariuNegociat, nrOre, username);
							windowLogare = new PaginaPrincipala(utilizator);
							windowLogare.frameLogare.setVisible(true);
							frame.dispose();
							ok = 1;
							break;
						}
					}
					connection.close();
					if (ok == 0)
						JOptionPane.showMessageDialog(frame, "Datele introduse sunt incorecte!", "Eroare", 0);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonLogare.setBounds(184, 454, 155, 43);
		frame.getContentPane().add(buttonLogare);

		Button buttonInregistrare = new Button("Creare cont");
		buttonInregistrare.setForeground(new Color(0, 191, 255));
		buttonInregistrare.setFont(new Font("DialogInput", Font.BOLD, 24));
		buttonInregistrare.setBackground(new Color(106, 90, 205));
		buttonInregistrare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				CreareCont windowCreareCont = new CreareCont();
				windowCreareCont.frameCreareCont.setVisible(true);
			}
		});
		buttonInregistrare.setBounds(177, 527, 175, 43);
		frame.getContentPane().add(buttonInregistrare);

		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 994, 671);
		lblBackground.setIcon(new ImageIcon(img_background));
		frame.getContentPane().add(lblBackground);
	}

	/*
	 * private void add(JLabel descriere) { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	public JFrame getFrame() {
		return frame;
	}
}
