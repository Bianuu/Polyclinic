package modul_1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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

import db.Interogari;
import db.MySQL_Connect;
import modele.Roluri;
import modele.Utilizator;
import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;
import pagini.PaginaPrincipala;

public class Meniu_DepartamentMedical_M1 extends JPanel {
	private final Image img_back = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/M1_Back.png"))
			.getImage().getScaledInstance(790, 630, Image.SCALE_SMOOTH);
	private final Image img_back2 = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/M1_Back.png"))
			.getImage().getScaledInstance(785, 591, Image.SCALE_SMOOTH);
	private final JTable table;
	private final JTable table3;
	// Conexiune Baza de date
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null, selectStatement2 = null;
	ResultSet rs = null, rs2 = null;
	private final JTable table1;

	public Meniu_DepartamentMedical_M1(Utilizator utilizatorCurent) {
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
		tabbedPane.addTab("Angajat", null, panel_cauta, null);
		panel_cauta.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(32, 34, 700, 326);
		panel_cauta.add(scrollPane_2);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(32, 380, 700, 326);
		panel_cauta.add(scrollPane_3);

		table = new JTable();
		scrollPane_2.setViewportView(table);

		table3 = new JTable();
		scrollPane_3.setViewportView(table3);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			if (utilizatorCurent.getFunctie().equals(Roluri.ROL_MEDIC)
					|| utilizatorCurent.getFunctie().equals(Roluri.ROL_ASISTENT)
					|| utilizatorCurent.getFunctie().equals(Roluri.ROL_RECEPTIONER)) {
				selectStatement.execute(
						"SELECT DISTINCT nume,prenume,functie , os.data AS 'Zi',os.ora_incepere AS 'Ora start', os.ora_incheiere AS 'Ora final', um.denumire AS 'Denumire unitate' FROM utilizator, orar_specific os, unitate_medicala um WHERE id_utilizator='"
								+ utilizatorCurent.getIdUtilizator() + "' AND um.id_unitate_medicala = os.id_unitate"
								+ " and id_utilizator=os.id_angajat ORDER BY nume ASC");
				rs = selectStatement.getResultSet();
				table3.setModel(DbUtils.resultSetToTableModel(rs));

			}
			selectStatement.execute(
					"SELECT DISTINCT nume,prenume,functie , og.ziua AS 'Zi',og.ora_incepere AS 'Ora start', og.ora_incheiere AS 'Ora final', um.denumire AS 'Denumire unitate' FROM utilizator, orar_generic og, unitate_medicala um WHERE id_utilizator='"
							+ utilizatorCurent.getIdUtilizator() + "' AND um.id_unitate_medicala = og.id_unitate"
							+ " and id_utilizator=og.id_angajat ORDER BY nume ASC");
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
		scrollPane_1.setBounds(32, 34, 700, 326);
		panel_concediu.add(scrollPane_1);

		table1 = new JTable();
		scrollPane_1.setViewportView(table1);

		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute(
					Interogari.FIND_CONCEDII + " WHERE U.id_utilizator='" + utilizatorCurent.getIdUtilizator() + "'");
			rs = selectStatement.getResultSet();
			table1.setModel(DbUtils.resultSetToTableModel(rs));
			connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lblback2 = new JLabel("");
		lblback2.setBounds(0, 0, 785, 591);
		panel_concediu.add(lblback2);
		lblback2.setIcon(new ImageIcon(img_back2));

		JLabel lbBackground = new JLabel("");
		lbBackground.setBounds(0, 0, 790, 630);
		lbBackground.setIcon(new ImageIcon(img_back));
		add(lbBackground);
	}
}
