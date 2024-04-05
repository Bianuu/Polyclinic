import java.awt.EventQueue;

import pagini.PaginaLogare;

public class Demo {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PaginaLogare window = new PaginaLogare();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
