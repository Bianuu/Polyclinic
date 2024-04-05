package pagini;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modele.Utilizator;

public class Meniu_Acasa extends JPanel {

	private final Image img_back = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/home_back.png"))
			.getImage().getScaledInstance(790, 630, Image.SCALE_SMOOTH);

	public Meniu_Acasa(Utilizator utilizatorCurent) {
		setSize(790, 630);
		setLayout(null);

		JLabel lblmesaj = new JLabel(
				"Bine ai venit " + utilizatorCurent.getNume() + " " + utilizatorCurent.getPrenume() + "!");
		lblmesaj.setForeground(new Color(255, 0, 0));
		lblmesaj.setFont(new Font("DialogInput", Font.BOLD, 24));
		lblmesaj.setHorizontalAlignment(SwingConstants.CENTER);
		lblmesaj.setBounds(27, 19, 700, 83);
		add(lblmesaj);

		JLabel descriere = new JLabel("Meniurile se afla in partea stanga.");
		descriere.setForeground(new Color(0, 0, 0));
		descriere.setFont(new Font("DialogInput", Font.BOLD, 18));
		descriere.setBounds(27, 419, 740, 93);
		add(descriere);

		JLabel descriere2 = new JLabel("Acestea pot fi accesate in functie de rolul pe care il detineti.");
		descriere2.setForeground(new Color(0, 0, 0));
		descriere2.setFont(new Font("DialogInput", Font.BOLD, 18));
		descriere2.setBounds(27, 470, 740, 93);
		add(descriere2);

		JLabel lbltxt2 = new JLabel("Pentru a accesa unul dintre acestea apasati click pe meniul dorit!");
		lbltxt2.setForeground(new Color(0, 0, 0));
		lbltxt2.setFont(new Font("DialogInput", Font.BOLD, 17));
		lbltxt2.setBounds(39, 520, 740, 93);
		add(lbltxt2);

		JLabel lblFunctie = new JLabel("Functie");
		lblFunctie.setForeground(new Color(255, 255, 255));
		lblFunctie.setHorizontalAlignment(SwingConstants.CENTER);
		lblFunctie.setBounds(52, 128, 103, 34);
		add(lblFunctie);

		JLabel lblValoareFunctie = new JLabel(utilizatorCurent.getFunctie());
		lblValoareFunctie.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareFunctie.setForeground(Color.WHITE);
		lblValoareFunctie.setBounds(167, 128, 300, 34);
		add(lblValoareFunctie);

		JLabel lblValoareIban = new JLabel(utilizatorCurent.getIBAN());
		lblValoareIban.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareIban.setForeground(Color.WHITE);
		lblValoareIban.setBounds(167, 227, 300, 34);
		add(lblValoareIban);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(52, 174, 103, 34);
		add(lblEmail);

		JLabel lblValoareEmail = new JLabel(utilizatorCurent.getEmail());
		lblValoareEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareEmail.setForeground(Color.WHITE);
		lblValoareEmail.setBounds(167, 174, 300, 34);
		add(lblValoareEmail);

		JLabel IbanLbl = new JLabel("IBAN");
		IbanLbl.setHorizontalAlignment(SwingConstants.CENTER);
		IbanLbl.setForeground(Color.WHITE);
		IbanLbl.setBounds(52, 227, 103, 34);
		add(IbanLbl);

		JLabel lblValoareNrTelefon = new JLabel(utilizatorCurent.getNrTelefon());
		lblValoareNrTelefon.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareNrTelefon.setForeground(Color.WHITE);
		lblValoareNrTelefon.setBounds(167, 273, 300, 34);
		add(lblValoareNrTelefon);

		JLabel lblNrTelefon = new JLabel("Nr.Telefon");
		lblNrTelefon.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrTelefon.setForeground(Color.WHITE);
		lblNrTelefon.setBounds(52, 273, 103, 34);
		add(lblNrTelefon);

		JLabel lblValoareCNP = new JLabel(utilizatorCurent.getCNP());
		lblValoareCNP.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareCNP.setForeground(Color.WHITE);
		lblValoareCNP.setBounds(167, 319, 300, 34);
		add(lblValoareCNP);

		JLabel lblCnp = new JLabel("CNP");
		lblCnp.setHorizontalAlignment(SwingConstants.CENTER);
		lblCnp.setForeground(Color.WHITE);
		lblCnp.setBounds(52, 319, 103, 34);
		add(lblCnp);

		JLabel lblDataAngajarii = new JLabel("Data Angajarii");
		lblDataAngajarii.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataAngajarii.setForeground(Color.WHITE);
		lblDataAngajarii.setBounds(52, 373, 103, 34);
		add(lblDataAngajarii);

		JLabel lblValoareDataAngajarii = new JLabel(utilizatorCurent.getDataAngajarii().toString());
		lblValoareDataAngajarii.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareDataAngajarii.setForeground(Color.WHITE);
		lblValoareDataAngajarii.setBounds(167, 373, 300, 34);
		add(lblValoareDataAngajarii);

		JLabel lblNrContract = new JLabel("Nr.Contract");
		lblNrContract.setHorizontalAlignment(SwingConstants.CENTER);
		lblNrContract.setForeground(Color.WHITE);
		lblNrContract.setBounds(52, 419, 103, 34);
		add(lblNrContract);

		JLabel lblValoareNrContract = new JLabel(String.valueOf(utilizatorCurent.getNrContract()));
		lblValoareNrContract.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareNrContract.setForeground(Color.WHITE);
		lblValoareNrContract.setBounds(167, 419, 300, 34);
		add(lblValoareNrContract);

		JLabel lblUser = new JLabel("Username");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setForeground(Color.WHITE);
		lblUser.setBounds(52, 82, 103, 34);
		add(lblUser);

		JLabel lblValoareUser = new JLabel(utilizatorCurent.getUsername());
		lblValoareUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoareUser.setForeground(Color.WHITE);
		lblValoareUser.setBounds(167, 82, 300, 34);
		add(lblValoareUser);

		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 790, 630);
		lbBackground.setIcon(new ImageIcon(img_back));
		add(lbBackground);
	}
}
