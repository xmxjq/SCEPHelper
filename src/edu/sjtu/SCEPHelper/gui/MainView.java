package edu.sjtu.SCEPHelper.gui;

import javax.swing.SwingUtilities;


public class MainView {
	private ClientFrame clientView = null;
	private ServerFrame serverView = null;
	
	public ClientFrame getClientView() {
		if (clientView == null) {
			clientView = new ClientFrame(this);
		}
		return clientView;
	}
	
	public ServerFrame getServerView() {
		if (serverView == null) {
			serverView = new ServerFrame(this);
		}
		return serverView;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.setProperty("swing.plaf.metal.controlFont","宋体");
				MainView application = new MainView();
				application.getClientView().setVisible(true);
			}
		});
	}

}
