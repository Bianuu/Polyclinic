package modul_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import db.Interogari;
import db.MySQL_Connect;
import modele.Roluri;
import modele.Utilizator;
import net.proteanit.sql.DbUtils;

public class Meniu_Angajat_M2 extends JPanel {
	private MySQL_Connect db;
	private Connection connection;
	Statement selectStatement = null;
	private ResultSet rs = null;

	public Meniu_Angajat_M2(Utilizator utilizatorCurent) {
		setSize(790, 630);
		setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("DialogInput", Font.BOLD, 18));
		tabbedPane.setBackground(new Color(0, 204, 255));
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(new Color(0, 102, 255));
		tabbedPane.setBounds(0, 0, 790, 630);
		add(tabbedPane);

		JPanel panelSalariu = new JPanel();
		panelSalariu.setFont(new Font("DialogInput", Font.BOLD, 18));
		panelSalariu.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Salarii", null, panelSalariu, null);
		panelSalariu.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(32, 34, 700, 326);
		panelSalariu.add(scrollPane_2);

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
					selectStatement.execute("SELECT suma,luna,an from salariu WHERE id_utilizator='"
							+ utilizatorCurent.getIdUtilizator() + "'");
					rs = selectStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSalarii.setBounds(292, 409, 172, 47);
		panelSalariu.add(btnSalarii);

		if (utilizatorCurent.getFunctie().equals(Roluri.ROL_MEDIC)) {
			JPanel panelMedic = new JPanel();
			panelMedic.setFont(new Font("DialogInput", Font.BOLD, 18));
			panelMedic.setBackground(new Color(135, 206, 235));
			tabbedPane.addTab("Profit medic", null, panelMedic, null);
			panelMedic.setLayout(null);

			double sumaCastiguri = 0;
			double sumaCastiguriMedic = 0;

			try {
				db = new MySQL_Connect();
				connection = db.getConnection();
				selectStatement = connection.createStatement();
				selectStatement.execute(Interogari.CASTIGURI_LUNA_CURENTA);
				rs = selectStatement.getResultSet();
				while (rs.next()) {
					double castig = rs.getDouble("total");
					sumaCastiguri += castig;
				}

				selectStatement.execute("SELECT suma,luna,an from salariu WHERE id_utilizator='"
						+ utilizatorCurent.getIdUtilizator() + "' AND YEAR(NOW())=an AND MONTH(NOW())=luna");
				rs = selectStatement.getResultSet();
				while (rs.next()) {
					double castig = rs.getDouble("suma");
					sumaCastiguriMedic += castig;
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			double valoareProfit = sumaCastiguri - sumaCastiguriMedic;
			JLabel lblNewLabel = new JLabel("Profitul realizat de medic in aceasta luna este");
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(92, 241, 573, 71);
			panelMedic.add(lblNewLabel);

			JLabel lblProfit = new JLabel(String.valueOf(valoareProfit));
			lblProfit.setHorizontalAlignment(SwingConstants.CENTER);
			lblProfit.setBounds(236, 324, 286, 71);
			panelMedic.add(lblProfit);
		}
	}

}
