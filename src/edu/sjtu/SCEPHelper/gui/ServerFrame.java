package edu.sjtu.SCEPHelper.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class ServerFrame extends JFrame {
	
	public MainView parent = null;

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar MainMenuBar = null;
	private JMenu ModeMenu = null;
	private JMenuItem TurnToClientMode = null;

	private JButton OpenServer = null;

	/**
	 * This is the default constructor
	 */
	public ServerFrame( MainView par) {
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
		this.setTitle("SCEPHelper - 服务器模式");
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
			jContentPane.add(getOpenServer(), BorderLayout.CENTER);
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
			ModeMenu.add(getTurnToClientMode());
		}
		return ModeMenu;
	}

	/**
	 * This method initializes TurnToClientMode	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getTurnToClientMode() {
		if (TurnToClientMode == null) {
			TurnToClientMode = new JMenuItem();
			TurnToClientMode.setText("切换到客户端模式");
			TurnToClientMode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.getClientView().setLocation(getLocation());
					parent.getServerView().setVisible(false);
					parent.getClientView().setVisible(true);
				}
			});
		}
		return TurnToClientMode;
	}

	/**
	 * This method initializes OpenServer	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenServer() {
		if (OpenServer == null) {
			OpenServer = new JButton();
			OpenServer.setText("打开服务器");
		}
		return OpenServer;
	}

}
