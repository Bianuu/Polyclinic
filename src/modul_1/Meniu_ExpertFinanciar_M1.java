package modul_1;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db.Interogari;
import db.MySQL_Connect;
import modele.Utilizator;
import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;
import pagini.PaginaPrincipala;

public class Meniu_ExpertFinanciar_M1 extends JPanel {
	private final Image img_back = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/M1_Back.png"))
			.getImage().getScaledInstance(790, 630, Image.SCALE_SMOOTH);
	private final Image img_back2 = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/M1_Back.png"))
			.getImage().getScaledInstance(785, 591, Image.SCALE_SMOOTH);
	private final JTable table;
	private final JTable table1;
	// Conexiune Baza de date
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null, selectStatement2 = null;
	ResultSet rs = null, rs2 = null;
	private JTextField data;
	private final JTable table_1;
	private final JTextField unitate;

	public Meniu_ExpertFinanciar_M1(Utilizator utilizatorCurent) {
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
		tabbedPane.addTab("Info", null, panel_cauta, null);
		panel_cauta.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(32, 34, 600, 500);
		panel_cauta.add(scrollPane_2);

		table = new JTable();
		scrollPane_2.setViewportView(table);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			String q = "SELECT DISTINCT u.nume,u.prenume,u.functie,og.ziua as 'Zi', og.ora_incepere AS 'Ora start', og.ora_incheiere AS 'Ora final' FROM utilizator u, orar_generic og WHERE og.id_angajat = u.id_utilizator AND u.id_utilizator="
					+ utilizatorCurent.getIdUtilizator();
			selectStatement.execute(q);
			rs = selectStatement.getResultSet();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lblback1 = new JLabel("");
		lblback1.setBounds(0, 0, 785, 591);
		panel_cauta.add(lblback1);
		lblback1.setIcon(new ImageIcon(img_back2));

		JPanel panel_concediu = new JPanel();
		panel_concediu.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Concediu", null, panel_concediu, null);
		panel_concediu.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 34, 500, 326);
		panel_concediu.add(scrollPane_1);

		table1 = new JTable();
		scrollPane_1.setViewportView(table1);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute("SELECT nume,prenume,functie from utilizator WHERE id_utilizator=" + "'"
					+ utilizatorCurent.getIdUtilizator() + "'");
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
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					selectStatement.execute(
							"SELECT U.nume, U.prenume, U.functie, C.data_incepere AS 'Inceput Concediu', C.data_incheiere AS 'Sfarsit concediu' from concediu C, utilizator U WHERE C.id_utilizator = U.id_utilizator ORDER BY nume ASC");
					rs = selectStatement.getResultSet();
					table1.setModel(DbUtils.resultSetToTableModel(rs));
					connection.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		butonConcedii.setFont(new Font("DialogInput", Font.BOLD, 17));
		butonConcedii.setBounds(547, 305, 152, 57);
		panel_concediu.add(butonConcedii);

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
