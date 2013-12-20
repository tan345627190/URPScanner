package com.gs.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
class Userinfo{
	public Userinfo(String username, String password) {
		this.username = username;
		this.password = password;
	}
	String username;
	String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
public class Main {

	private JFrame frmV;
	private static JTextField textField;
	private static JPasswordField textField_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		String json = null;
		try {
			json = FileUtils.readFileToString(new File("user.json"), "utf8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Userinfo u = new Gson().fromJson(json, Userinfo.class);
		textField.setText(u.getUsername());
		textField_1.setText(u.getPassword());
		
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmV = new JFrame();
		frmV.setResizable(false);
		frmV.setTitle("\u6210\u7EE9\u67E5\u770B\u5668 v2.0");
		frmV.setBounds(100, 100, 783, 484);
		frmV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnNewButton = new JButton("\u597D");
		final JScrollPane scrollPane = new JScrollPane();
		final JProgressBar progressBar = new JProgressBar();
		table = new JTable(new Object[1][3],new Object[]{"名称","学分","成绩"});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"\u540D\u79F0", "\u5B66\u5206", "\u6210\u7EE9"
			}
		));
		table.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		final JLabel lblNewLabel = new JLabel("");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						progressBar.setMinimum(0);
						progressBar.setMaximum(100);
						URPScanner u = new URPScanner();
						String html = "";
						try {
							html = u.scan(textField.getText(), textField_1.getText());
							progressBar.setValue(30);
						} catch (HttpException e) {
							JOptionPane.showMessageDialog(null, "网络错误");
							e.printStackTrace();
							return;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "IO错误");
							e.printStackTrace();
							return;
						}
						if(html.length() == 0) {JOptionPane.showMessageDialog(null, "密码错误");return;}
						List<ClassPOJO> list = u.revert(html);
						Object[][] data = new Object[list.size()][3];
						progressBar.setValue(60);
						int i = 0;
						for(ClassPOJO p : list){
							data[i] = new Object[] {p.getName(),p.getCredit(),p.getScore()};
							i++;
						}
						progressBar.setValue(80);
						table = new JTable(data,new Object[]{"名称","学分","成绩"});
						table.setFont(new Font("微软雅黑", Font.PLAIN, 15));
						table.setAutoResizeMode(JTable.HEIGHT);
						table.setFillsViewportHeight(true);
						table.setEnabled(false);
						lblNewLabel.setText(u.getName());
						scrollPane.setViewportView(table);
						progressBar.setValue(100);
						try {
							FileUtils.writeStringToFile(new File("user.json"), new Gson().toJson(new Userinfo(textField.getText(),textField_1.getText())), "utf8");
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "存入用户信息时出现未知错误"+e.getMessage());
							e.printStackTrace();
						}
					}
				});
				t.run();
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u5B66\u53F7");
		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		
		textField_1 = new JPasswordField();
		
		JLabel lblNewLabel_1 = new JLabel("https://github.com/gsh199449/URPScanner");
		
		GroupLayout groupLayout = new GroupLayout(frmV.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addComponent(label)
					.addGap(27)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(55)
					.addComponent(label_1)
					.addGap(18)
					.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(77)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
					.addGap(156))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(276)
					.addComponent(lblNewLabel_1)
					.addContainerGap(323, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
					.addGap(9)
					.addComponent(lblNewLabel_1))
		);
		
		
		scrollPane.setViewportView(table);
		
		
		frmV.getContentPane().setLayout(groupLayout);
	}
}
