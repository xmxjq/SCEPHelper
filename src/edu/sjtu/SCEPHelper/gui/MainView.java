package edu.sjtu.SCEPHelper.gui;

import edu.sjtu.SCEPHelper.net.server.BackendServer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: gsj987
 * Date: 11-5-28
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public class MainView {
    private static boolean isServerStarted = false;
    private static BackendServer backendServer = null;
    private static MainView mainView = null;
    private JButton startServerButton = null;
    private JButton stopServerButton = null;
    private JFrame mainFrame = null;

    public MainView() {
        startServerButton = new JButton("开启服务器");
        stopServerButton = new JButton("关闭服务器");
        mainFrame = new JFrame();
        mainFrame.setTitle("英语试卷管理系统");
        mainFrame.setSize(new Dimension(200, 180));
        mainFrame.add(startServerButton);
        mainFrame.pack();
    }

    public static MainView getInstance() {
        // 用这种方法可以防止窗口关闭进程结束，可以用来作为后台运行
        if (mainView==null){
            mainView = new MainView();
        }
        return mainView;
    }

    public void show(){
        mainFrame.setVisible(true);
    }

    public static void main(String args[]) {
        MainView mainView = getInstance();
        mainView.show();
    }
}
