package modul_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import db.Interogari;
import db.MySQL_Connect;
import net.proteanit.sql.DbUtils;

public class Meniu_ExpertFinanciar_M2 extends JPanel {

	private final List<Double> salarii = new ArrayList<>();
	private final List<Double> castiguri = new ArrayList<>();
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null;
	private ResultSet rs = null;
	private int luna;
	private int an;
	private final boolean searchSpecializare = false;
	double sumaSalarii = 0;
	double sumaCastiguri = 0;
	private int selectedCombo = -1;
	private JComboBox comboMedic;
	private JComboBox comboUnitate;
	private JComboBox comboSpecializare;

	public Meniu_ExpertFinanciar_M2() {
		comboMedic = new JComboBox();
		comboUnitate = new JComboBox();
		comboSpecializare = new JComboBox();

		setSize(790, 630);
		setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("DialogInput", Font.BOLD, 18));
		tabbedPane.setBackground(new Color(0, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(new Color(0, 102, 255));
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panelProfil = new JPanel();
		panelProfil.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelProfil.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Profit", null, panelProfil, null);
		panelProfil.setLayout(null);

		JLabel lblNewLabel = new JLabel("Profitul realizat in luna curenta de lantul de policlinici este");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(92, 241, 573, 71);
		panelProfil.add(lblNewLabel);

		double profit = calculProfit();

		JLabel lblProfit = new JLabel(String.valueOf(profit));
		lblProfit.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfit.setBounds(236, 324, 286, 71);
		panelProfil.add(lblProfit);

		JLabel lblVenituri = new JLabel("Venituri");
		lblVenituri.setHorizontalAlignment(SwingConstants.CENTER);
		lblVenituri.setBounds(86, 70, 161, 44);
		panelProfil.add(lblVenituri);

		JLabel lblCheltuieli = new JLabel("Cheltuieli");
		lblCheltuieli.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheltuieli.setBounds(504, 70, 161, 44);
		panelProfil.add(lblCheltuieli);

		JLabel lblVenit = new JLabel(String.valueOf(sumaCastiguri));
		lblVenit.setHorizontalAlignment(SwingConstants.CENTER);
		lblVenit.setBounds(22, 126, 286, 71);
		panelProfil.add(lblVenit);

		JLabel lblCheltuiala = new JLabel(String.valueOf(sumaSalarii));
		lblCheltuiala.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheltuiala.setBounds(446, 126, 286, 71);
		panelProfil.add(lblCheltuiala);

		JPanel panelSalarii = new JPanel();
		panelSalarii.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelSalarii.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Salarii", null, panelSalarii, null);
		panelSalarii.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(32, 34, 700, 326);
		panelSalarii.add(scrollPane_2);

		JTable table = new JTable();
		scrollPane_2.setViewportView(table);

		JButton btnSalarii = new JButton("Vezi salariile");
		btnSalarii.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					selectStatement.execute(Interogari.SALARII);
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSalarii.setBounds(292, 409, 172, 47);
		panelSalarii.add(btnSalarii);

		JPanel panelSalariiMedic = new JPanel();
		panelSalariiMedic.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelSalariiMedic.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Profit medic", null, panelSalariiMedic, null);
		panelSalariiMedic.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 34, 700, 326);
		panelSalariiMedic.add(scrollPane);

		JTable table2 = new JTable();
		scrollPane.setViewportView(table2);

		Map<Integer, String> mapMedici = new HashMap<>();
		mapMedici.put(0, "-Medic-");
		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute("SELECT * from medic M JOIN utilizator U ON U.id_utilizator = M.id_utilizator");
			rs = selectStatement.getResultSet();
			while (rs.next()) {
				int idMedic = rs.getInt("id_medic");
				String nume = rs.getString("nume");
				String prenume = rs.getString("prenume");
				String numeComplet = nume + " " + prenume;
				mapMedici.put(idMedic, numeComplet);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		Map<Integer, String> mapSpecializari = new HashMap<>();
		mapSpecializari.put(0, "-Specializare-");
		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute("SELECT * from specialitate_medic");
			rs = selectStatement.getResultSet();

			while (rs.next()) {
				int idSpecializare = rs.getInt("id_specialitate");
				String numeSpecializare = rs.getString("nume");
				mapSpecializari.put(idSpecializare, numeSpecializare);
			}
			comboSpecializare.setModel(new DefaultComboBoxModel(mapSpecializari.values().toArray()));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		comboSpecializare.setModel(new DefaultComboBoxModel(mapSpecializari.values().toArray()));

		comboSpecializare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					String selectedSpecializare = comboSpecializare.getSelectedItem().toString();
					if (selectedSpecializare.equals("-Specializare-")) {
						return;
					}
					selectedCombo = 1;
					resetComboBox();
					selectStatement.execute(
							"SELECT SM.specializarea, SUM(SM.pret) AS 'Total' , MONTH(P.zi) AS 'Luna', YEAR(P.zi) as 'An' \n"
									+ "FROM serviciu_medical SM\n"
									+ "JOIN programare_servicii_medicale PSM ON PSM.id_servicii=SM.id_serviciu_medical\n"
									+ "JOIN programare P ON P.id_programare=PSM.id_programare\n"
									+ "JOIN bon_fiscal B ON P.id_programare=B.id_programare\n"
									+ "WHERE SM.specializarea='" + selectedSpecializare
									+ "' GROUP by  SM.specializarea ,MONTH(P.zi) ,YEAR(P.zi);");
					rs = selectStatement.getResultSet();
					table2.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		comboSpecializare.setBounds(436, 372, 241, 69);
		panelSalariiMedic.add(comboSpecializare);

		Map<Integer, String> mapUnitate = new HashMap<>();
		mapUnitate.put(0, "-Unitate medicala-");
		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute("SELECT * from unitate_medicala");
			rs = selectStatement.getResultSet();

			while (rs.next()) {
				int idUnitate = rs.getInt("id_unitate_medicala");
				String numeUnitate = rs.getString("denumire");
				mapUnitate.put(idUnitate, numeUnitate);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		comboUnitate.setModel(new DefaultComboBoxModel(mapUnitate.values().toArray()));

		comboUnitate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					String selectedUnitate = comboUnitate.getSelectedItem().toString();
					if (selectedUnitate.equals("-Unitate medicala-")) {
						return;
					}
					selectedCombo = 2;
					resetComboBox();
					int idUnitate = mapUnitate.entrySet().stream()
							.filter(entry -> selectedUnitate.equals(entry.getValue())).map(Map.Entry::getKey)
							.findFirst().get();
					selectStatement.execute(
							"SELECT SUM(B.total) AS 'Total', MONTH(P.zi) AS 'Luna', YEAR(P.zi) AS 'An' from bon_fiscal B JOIN programare P ON B.id_programare=P.id_programare WHERE id_unitate='"
									+ idUnitate + "' GROUP BY MONTH(P.zi),YEAR(P.zi)");
					rs = selectStatement.getResultSet();
					table2.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		comboUnitate.setBounds(436, 449, 241, 69);
		panelSalariiMedic.add(comboUnitate);

		comboMedic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					db = new MySQL_Connect();
					connection = db.getConnection();
					selectStatement = connection.createStatement();
					String selectedMedic = comboMedic.getSelectedItem().toString();
					if (selectedMedic.equals("-Medic")) {
						return;
					}
					selectedCombo = 3;
					resetComboBox();
					int idMedic = mapMedici.entrySet().stream().filter(entry -> selectedMedic.equals(entry.getValue()))
							.map(Map.Entry::getKey).findFirst().get();
					selectStatement.execute("SELECT S.id_salariu, S.suma, S.luna, S.an,S.id_utilizator from salariu S "
							+ "JOIN utilizator U ON U.id_utilizator=S.id_utilizator "
							+ "JOIN medic M ON M.id_utilizator = U.id_utilizator WHERE M.id_medic='" + idMedic + "'");
					rs = selectStatement.getResultSet();
					table2.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		comboMedic.setModel(new DefaultComboBoxModel(mapMedici.values().toArray()));
		comboMedic.setBounds(81, 396, 241, 69);
		panelSalariiMedic.add(comboMedic);

		JButton btnProfitCustom = new JButton("Resetare campuri");
		btnProfitCustom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedCombo = -1;
				comboSpecializare.setSelectedIndex(0);
				comboUnitate.setSelectedIndex(0);
				comboMedic.setSelectedIndex(0);
			}
		});
		btnProfitCustom.setBounds(91, 496, 165, 52);
		panelSalariiMedic.add(btnProfitCustom);

	}

	double calculProfit() {
		try {
			db = new MySQL_Connect();
			connection = db.getConnection();
			selectStatement = connection.createStatement();
			selectStatement.execute(Interogari.SALARII_LUNA_CURENTA);
			rs = selectStatement.getResultSet();
			while (rs.next()) {
				double salariu = rs.getDouble("suma");
				luna = rs.getInt("luna");
				an = rs.getInt("an");
				sumaSalarii += salariu;
				salarii.add(salariu);
			}

			selectStatement = connection.createStatement();
			selectStatement.execute(Interogari.CASTIGURI_LUNA_CURENTA);
			rs = selectStatement.getResultSet();
			while (rs.next()) {
				double castig = rs.getDouble("total");
				sumaCastiguri += castig;
				castiguri.add(castig);
			}

			return sumaCastiguri - sumaSalarii;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0.0;
	}

	void resetComboBox() {
		switch (selectedCombo) {
		case 1:
			comboUnitate.setSelectedIndex(0);
			comboMedic.setSelectedIndex(0);
			break;
		case 2:
			comboSpecializare.setSelectedIndex(0);
			comboMedic.setSelectedIndex(0);
			break;
		case 3:
			comboSpecializare.setSelectedIndex(0);
			comboUnitate.setSelectedIndex(0);
			break;
		default:
			selectedCombo = -1;
		}
	}
}
