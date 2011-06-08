package edu.sjtu.SCEPHelper.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class ClientFrame extends JFrame {

	public MainView parent = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar MainMenuBar = null;
	private JMenu ModeMenu = null;
	private JMenuItem TurnToServerMode = null;

	private JButton OpenClient = null;
	/**
	 * This is the default constructor
	 */
	public ClientFrame( MainView par ) {
		super();
		initialize();
		parent = par;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setJMenuBar(getMainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("SCEPHelper - 客户端模式");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getOpenClient(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes MainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMainMenuBar() {
		if (MainMenuBar == null) {
			MainMenuBar = new JMenuBar();
			MainMenuBar.add(getModeMenu());
		}
		return MainMenuBar;
	}

	/**
	 * This method initializes ModeMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getModeMenu() {
		if (ModeMenu == null) {
			ModeMenu = new JMenu();
			ModeMenu.setText("模式");
			ModeMenu.add(getTurnToServerMode());
		}
		return ModeMenu;
	}

	/**
	 * This method initializes TurnToServerMode	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getTurnToServerMode() {
		if (TurnToServerMode == null) {
			TurnToServerMode = new JMenuItem();
			TurnToServerMode.setText("切换到服务器模式");
			TurnToServerMode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.getServerView().setLocation(getLocation());
					parent.getClientView().setVisible(false);
					parent.getServerView().setVisible(true);
				}
			});
		}
		
		return TurnToServerMode;
	}

	/**
	 * This method initializes OpenClient	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenClient() {
		if (OpenClient == null) {
			OpenClient = new JButton();
			OpenClient.setText("打开客户端");
		}
		return OpenClient;
	}

}
