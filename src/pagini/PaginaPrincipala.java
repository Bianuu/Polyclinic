package pagini;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import administrare.Meniu_Administrare;
import administrare.Meniu_SuperAdministrare;
import modele.Roluri;
import modele.Utilizator;
import modul_1.Meniu_DepartamentMedical_M1;
import modul_1.Meniu_ExpertFinanciar_M1;
import modul_1.Meniu_Inspector_M1;
import modul_2.Meniu_Angajat_M2;
import modul_2.Meniu_ExpertFinanciar_M2;
import modul_3.Meniu_Asistent;
import modul_3.Meniu_Medic;
import modul_3.Meniu_Receptioner;

public class PaginaPrincipala {

    JFrame frameLogare;
    private Utilizator utilizatorCurent = null;
    //	private String utilizator = new String("");
//	private String nume = new String("");
//	private String prenume = new String("");
//	private String functie = new String("");
    private PaginaLogare windowMain;
    private JPanel panelMain;
    private final List<JPanel> meniuri = new ArrayList<>();
    // Background si Logo
    private static final Image img_logo = new ImageIcon(
            PaginaPrincipala.class.getResource("../resurse/logo_logare.png")).getImage()
            .getScaledInstance(140, 130, Image.SCALE_SMOOTH);
    private static final Image img_back2 = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/backMenu.jpg"))
            .getImage().getScaledInstance(340, 700, Image.SCALE_SMOOTH);
    private static final Image img_back3 = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/backMenu2.jpg"))
            .getImage().getScaledInstance(850, 685, Image.SCALE_SMOOTH);
    private static final Image img_home = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/home.png"))
            .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    private static final Image img_signout = new ImageIcon(PaginaPrincipala.class.getResource("../resurse/exit.png"))
            .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    private static final Image img_administrare = new ImageIcon(
            PaginaPrincipala.class.getResource("../resurse/administrare_logo.png")).getImage()
            .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    private static final Image img_medical = new ImageIcon(
            PaginaPrincipala.class.getResource("../resurse/medical_logo.png")).getImage()
            .getScaledInstance(55, 53, Image.SCALE_SMOOTH);
    private static final Image img_resurse = new ImageIcon(
            PaginaPrincipala.class.getResource("../resurse/resurse_umane.png")).getImage()
            .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    private static final Image img_financiar = new ImageIcon(
            PaginaPrincipala.class.getResource("../resurse/financiar.png")).getImage()
            .getScaledInstance(60, 40, Image.SCALE_SMOOTH);
    /// Meniuri ~ VIEW-urile din MySQL
    private Meniu_Acasa meniu_Acasa;
    private Meniu_ExpertFinanciar_M2 meniu_ExpertFinanciar_M2;
    private Meniu_Medic meniu_Medic;
    //	private Meniu_SignOut meniu_SignOut;
    private Meniu_Administrare meniu_Administrare;
    private Meniu_Angajat_M2 meniu_Angajat_M2;
    private Meniu_SuperAdministrare meniu_SuperAdministrare;
    private Meniu_DepartamentMedical_M1 meniu_DepartamentMedical_M1;
    private Meniu_Inspector_M1 meniu_Inspector_M1;
    private Meniu_ExpertFinanciar_M1 meniu_ExpertFinanciar_M1;
    //	private Meniu_Medic_M3 meniu_Medic_M3;
    private Meniu_Asistent meniu_Asistent;
    private Meniu_Receptioner meniu_Receptioner;
    ///

    public PaginaPrincipala() {
        initialize();
    }

    public PaginaPrincipala(Utilizator utilizatorCurent) {
        this.utilizatorCurent = utilizatorCurent;
        initialize();
    }

    private void initialize() {
        frameLogare = new JFrame();
        frameLogare.setBounds(100, 100, 1178, 708);
        frameLogare.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogare.setResizable(false);
        frameLogare.setTitle("POLICLINICI by CIOBAN FABIAN-REMUS & POJAR ANDREI-GABRIEL");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frameLogare.setLocation(dim.width / 2 - frameLogare.getSize().width / 2,
                dim.height / 2 - frameLogare.getSize().height / 2);
        frameLogare.getContentPane().setLayout(null);
        frameLogare.setIconImage(
                Toolkit.getDefaultToolkit().getImage(PaginaPrincipala.class.getResource("../resurse/logo_app.jpg")));

        meniu_Acasa = new Meniu_Acasa(utilizatorCurent);
        meniu_ExpertFinanciar_M2 = new Meniu_ExpertFinanciar_M2();
        meniu_Administrare = new Meniu_Administrare(utilizatorCurent);
        meniu_SuperAdministrare = new Meniu_SuperAdministrare(utilizatorCurent);
        meniu_DepartamentMedical_M1 = new Meniu_DepartamentMedical_M1(utilizatorCurent);
        meniu_Inspector_M1 = new Meniu_Inspector_M1();
        meniu_ExpertFinanciar_M1 = new Meniu_ExpertFinanciar_M1(utilizatorCurent);
        meniu_Angajat_M2 = new Meniu_Angajat_M2(utilizatorCurent);
        meniu_Medic = new Meniu_Medic(utilizatorCurent);
        meniu_Asistent = new Meniu_Asistent(utilizatorCurent);
        meniu_Receptioner = new Meniu_Receptioner();

        meniuri.add(meniu_Acasa);
        meniuri.add(meniu_ExpertFinanciar_M2);
        meniuri.add(meniu_Administrare);
        meniuri.add(meniu_SuperAdministrare);
        meniuri.add(meniu_Inspector_M1);
        meniuri.add(meniu_ExpertFinanciar_M1);
        meniuri.add(meniu_DepartamentMedical_M1);
        meniuri.add(meniu_Angajat_M2);
        meniuri.add(meniu_Medic);
        meniuri.add(meniu_Asistent);
        meniuri.add(meniu_Receptioner);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 336, 691);
        panel.setBorder(null);
        frameLogare.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lbLogo = new JLabel("");
        lbLogo.setBounds(92, 55, 138, 127);
        lbLogo.setIcon(new ImageIcon(img_logo));
        panel.add(lbLogo);

        JPanel panel_Acasa = new JPanel();
        panel_Acasa.addMouseListener(new PanelButtonMouseAdapter(panel_Acasa) {
            @Override
            public void mouseClicked(MouseEvent e) {
                meniuCurent(meniu_Acasa);
            }
        });
        panel_Acasa.setBackground(new Color(135, 206, 250));
        panel_Acasa.setBorder(new MatteBorder(2, 2, 1, 2, new Color(0, 191, 255)));
        panel_Acasa.setBounds(43, 215, 258, 75);
        panel.add(panel_Acasa);
        panel_Acasa.setLayout(null);

        JLabel lbHomeIcon = new JLabel("");
        lbHomeIcon.setBounds(29, 11, 40, 40);
        lbHomeIcon.setIcon(new ImageIcon(img_home));
        panel_Acasa.add(lbHomeIcon);

        JLabel lbHome = new JLabel("Pagina Principala");
        lbHome.setBounds(63, 11, 185, 53);
        lbHome.setForeground(new Color(106, 90, 205));
        lbHome.setFont(new Font("DialogInput", Font.BOLD, 14));
        lbHome.setHorizontalAlignment(SwingConstants.CENTER);
        panel_Acasa.add(lbHome);

        JPanel panel_Resurse = new JPanel();
        panel_Resurse.addMouseListener(new PanelButtonMouseAdapter(panel_Resurse) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_MEDIC)
                        || utilizatorCurent.getFunctie().equals(Roluri.ROL_ASISTENT)
                        || utilizatorCurent.getFunctie().equals(Roluri.ROL_RECEPTIONER))
                    meniuCurent(meniu_DepartamentMedical_M1);
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_INSPECTOR)
                        || utilizatorCurent.getFunctie().equals(Roluri.ROL_SUPERADMIN)
                        || utilizatorCurent.getFunctie().equals(Roluri.ROL_ADMIN))
                    meniuCurent(meniu_Inspector_M1);
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_EXPERT))
                    meniuCurent(meniu_ExpertFinanciar_M1);
            }
        });
        panel_Resurse.setBackground(new Color(135, 206, 250));
        panel_Resurse.setBorder(new MatteBorder(1, 2, 1, 2, new Color(0, 191, 255)));
        panel_Resurse.setBounds(43, 290, 258, 75);
        panel.add(panel_Resurse);
        panel_Resurse.setLayout(null);

        JLabel lblResurse = new JLabel("Gestiune Resurse Umane");
        lblResurse.setBounds(30, 11, 228, 53);
        lblResurse.setForeground(new Color(106, 90, 205));
        lblResurse.setHorizontalAlignment(SwingConstants.CENTER);
        lblResurse.setFont(new Font("DialogInput", Font.BOLD, 14));
        panel_Resurse.add(lblResurse);

        JLabel lbResurseUmane_Logo = new JLabel("");
        lbResurseUmane_Logo.setBounds(10, 15, 40, 40);
        lbResurseUmane_Logo.setIcon(new ImageIcon(img_resurse));
        panel_Resurse.add(lbResurseUmane_Logo);

        JPanel panel_Financiar = new JPanel();
        panel_Financiar.addMouseListener(new PanelButtonMouseAdapter(panel_Financiar) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_EXPERT))
                    meniuCurent(meniu_ExpertFinanciar_M2);
                else
                    meniuCurent(meniu_Angajat_M2);
            }
        });
        panel_Financiar.setBackground(new Color(135, 206, 250));
        panel_Financiar.setBorder(new MatteBorder(1, 2, 1, 2, new Color(0, 191, 255)));
        panel_Financiar.setBounds(43, 365, 258, 75);
        panel.add(panel_Financiar);
        panel_Financiar.setLayout(null);

        JLabel lblFinanciar = new JLabel("Op. Financiar-contabile");
        lblFinanciar.setBounds(53, 11, 205, 53);
        lblFinanciar.setForeground(new Color(106, 90, 205));
        lblFinanciar.setHorizontalAlignment(SwingConstants.CENTER);
        lblFinanciar.setFont(new Font("DialogInput", Font.BOLD, 14));
        panel_Financiar.add(lblFinanciar);

        JLabel lbFinanciar = new JLabel("");
        lbFinanciar.setBounds(5, 15, 60, 40);
        lbFinanciar.setIcon(new ImageIcon(img_financiar));
        panel_Financiar.add(lbFinanciar);

        JPanel panel_Medical = new JPanel();
        panel_Medical.addMouseListener(new PanelButtonMouseAdapter(panel_Medical) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_INSPECTOR)
                        || utilizatorCurent.getFunctie().equals(Roluri.ROL_EXPERT))
                    JOptionPane.showMessageDialog(null, "Nu aveti drept de acces !", "Acces interzis", 0);
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_MEDIC))
                    meniuCurent(meniu_Medic);
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_ASISTENT))
                    meniuCurent(meniu_Asistent);
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_RECEPTIONER))
                    meniuCurent(meniu_Receptioner);
            }
        });
        panel_Medical.setBackground(new Color(135, 206, 250));
        panel_Medical.setBorder(new MatteBorder(1, 2, 2, 2, new Color(0, 191, 255)));
        panel_Medical.setBounds(43, 440, 258, 75);
        panel.add(panel_Medical);
        panel_Medical.setLayout(null);

        JLabel lblMedical = new JLabel("Modulul Medical");
        lblMedical.setBounds(53, 11, 195, 53);
        lblMedical.setBackground(new Color(147, 112, 219));
        lblMedical.setForeground(new Color(106, 90, 205));
        lblMedical.setHorizontalAlignment(SwingConstants.CENTER);
        lblMedical.setFont(new Font("DialogInput", Font.BOLD, 14));
        panel_Medical.add(lblMedical);

        JLabel lbMedicalLogo = new JLabel("");
        lbMedicalLogo.setBounds(21, 11, 55, 53);
        lbMedicalLogo.setIcon(new ImageIcon(img_medical));
        panel_Medical.add(lbMedicalLogo);

        JPanel panel_SignOut = new JPanel();
        panel_SignOut.addMouseListener(new PanelButtonMouseAdapter(panel_SignOut) {
            @Override
            public void mouseClicked(MouseEvent e) {
                int aux = JOptionPane.showConfirmDialog(panelMain, "Sunteti sigur ca doriti sa va delogati?",
                        "Delogare", 0);
                if (aux == 0) {
                    windowMain = new PaginaLogare();
                    windowMain.getFrame().setVisible(true);
                    frameLogare.dispose();
                }
            }
        });
        panel_SignOut.setBackground(new Color(135, 206, 250));
        panel_SignOut.setBorder(new MatteBorder(1, 2, 2, 2, new Color(0, 191, 255)));
        panel_SignOut.setBounds(43, 590, 258, 62);
        panel.add(panel_SignOut);
        panel_SignOut.setLayout(null);

        JLabel lbSignOutIcon = new JLabel("");
        lbSignOutIcon.setBounds(25, 13, 40, 40);
        lbSignOutIcon.setIcon(new ImageIcon(img_signout));
        panel_SignOut.add(lbSignOutIcon);

        JLabel lblSignOut = new JLabel("Delogare");
        lblSignOut.setBounds(75, 5, 162, 53);
        lblSignOut.setForeground(new Color(106, 90, 205));
        lblSignOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignOut.setFont(new Font("DialogInput", Font.BOLD, 14));
        panel_SignOut.add(lblSignOut);

        JPanel panel_Administrare = new JPanel();
        panel_Administrare.addMouseListener(new PanelButtonMouseAdapter(panel_Administrare) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (utilizatorCurent.getFunctie().equals(Roluri.ROL_ADMIN))
                    meniuCurent(meniu_Administrare);
                else if (utilizatorCurent.getFunctie().equals(Roluri.ROL_SUPERADMIN))
                    meniuCurent(meniu_SuperAdministrare);
                else
                    JOptionPane.showMessageDialog(meniu_Administrare, "Nu ai acces in aceasta sectiune!",
                            "Acces interzis!", 0);
            }
        });
        panel_Administrare.setBorder(new MatteBorder(1, 2, 2, 2, new Color(0, 191, 255)));
        panel_Administrare.setBackground(new Color(135, 206, 250));
        panel_Administrare.setBounds(43, 515, 258, 75);
        panel.add(panel_Administrare);
        panel_Administrare.setLayout(null);

        JLabel lblAdministrare = new JLabel("Administrare");
        lblAdministrare.setBounds(53, 11, 195, 53);
        lblAdministrare.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdministrare.setForeground(new Color(106, 90, 205));
        lblAdministrare.setFont(new Font("DialogInput", Font.BOLD, 14));
        lblAdministrare.setBackground(new Color(147, 112, 219));
        panel_Administrare.add(lblAdministrare);

        JLabel lbAdministrareIcon = new JLabel("");
        lbAdministrareIcon.setBounds(25, 15, 40, 40);
        lbAdministrareIcon.setIcon(new ImageIcon(img_administrare));
        panel_Administrare.add(lbAdministrareIcon);

        JLabel lbBackgroundLeft = new JLabel("");
        lbBackgroundLeft.setBounds(0, 0, 336, 681);
        lbBackgroundLeft.setIcon(new ImageIcon(img_back2));
        panel.add(lbBackgroundLeft);

        panelMain = new JPanel();
        panelMain.setBounds(360, 25, 790, 630);
        frameLogare.getContentPane().add(panelMain);
        panelMain.setLayout(null);
        panelMain.add(meniu_Acasa);
        panelMain.add(meniu_ExpertFinanciar_M2);
        panelMain.add(meniu_Angajat_M2);
        panelMain.add(meniu_Medic);
        panelMain.add(meniu_Administrare);
        panelMain.add(meniu_SuperAdministrare);
        panelMain.add(meniu_DepartamentMedical_M1);
        panelMain.add(meniu_Inspector_M1);
        panelMain.add(meniu_ExpertFinanciar_M1);
        panelMain.add(meniu_Receptioner);
        panelMain.add(meniu_Asistent);

        meniuCurent(meniu_Acasa);

        JLabel lbAux = new JLabel("");
        lbAux.setBounds(330, 0, 844, 681);
        lbAux.setIcon(new ImageIcon(img_back3));
        frameLogare.getContentPane().add(lbAux);
    }

    public void meniuCurent(JPanel panel) {
        for (JPanel meniu : meniuri) {
            meniu.setVisible(false);
        }

        panel.setVisible(true);
    }

    private class PanelButtonMouseAdapter extends MouseAdapter {
        JPanel panel;

        public PanelButtonMouseAdapter(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            panel.setBackground(new Color(190, 250, 252));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            panel.setBackground(new Color(135, 206, 255));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            panel.setBackground(new Color(40, 234, 255));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            panel.setBackground(new Color(190, 250, 252));
        }
    }
}
