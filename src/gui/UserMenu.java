package gui;

import gui.MainMenu.GameButtonAction;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import users.NameTakenException;
import users.User;
import users.UserManagementService;

import java.util.List;

public class UserMenu extends mainGUI {
	private JLabel frame_title;

	private JButton createNewUserButton;
	private JButton selectUserButton;
	private JList<User> users;
	
	

	public UserMenu() {
		// setup GUI styles/frame
		setup();
		setLayout(new GridLayout(3, 1));
		populateUsersList();
		makeButtons();
		setVisible(true);
	}
	
	public void populateUsersList() {
		List<User> userslist = UserManagementService.getInstance().getUsers();
		users = new JList<User>(userslist.toArray(new User[userslist.size()]));
		add(users);
	}

	private class NewUserDialog extends JFrame {
		private void createAndShowNewUserDialog() {
			setName("Add new user");
			String nameLabel = "Enter Name: ";
			JPanel p = new JPanel();
			JLabel l = new JLabel(nameLabel);
			p.add(l);
			
			final JTextField inputField = new JTextField(20);
			l.setLabelFor(inputField);
			p.add(inputField);
			JButton addUserButton = new JButton("Add User");
			
			addUserButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = inputField.getText();
					if (name == null){
						JOptionPane.showMessageDialog(NewUserDialog.this, "You didn't enter your name!");
					}
					else{
						try {
							User newUser = UserManagementService.getInstance().createUser(name);
							UserManagementService.getInstance().setMainUser(newUser);
							goToMainMenu();
							
						} catch (NameTakenException e1) {
							JOptionPane.showMessageDialog(NewUserDialog.this, e1.getError());
						}
					}
				}
			});
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					dispose();
				}
			});
			p.add(addUserButton);
			p.add(cancelButton);
			setContentPane(p);
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
			setVisible(true);
		}
	}

	private class CreateNewUserDialogHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			NewUserDialog dlg = new NewUserDialog();
			dlg.createAndShowNewUserDialog();
		}

	}
	
	private class selectedUserHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {			
			User selectedUser = users.getSelectedValue(); 
			UserManagementService.getInstance().setMainUser(selectedUser);
			System.out.println("You selected user: " + selectedUser.getName() + "\n with preferences: " + selectedUser.getPreferences());
			goToMainMenu();
		}

	}
	
	private void goToMainMenu(){
		MainMenu s = new MainMenu();
	}
	
	@Override
	public void makeButtons() {
		// make buttons 
		createNewUserButton = new JButton("Create New User");
		createNewUserButton.setText("<html><font color=\"#FF6600\">C</font>" + "reate New User</html>");
		selectUserButton = new JButton("Select User");
		createNewUserButton.addActionListener(new CreateNewUserDialogHandler());
		selectUserButton.addActionListener(new selectedUserHandler());
		
		/*createNewUserButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('c'), "gameButtonPressed");
		createNewUserButton.getActionMap().put("gameButtonPressed", new GameButtonAction());*/
		
		if(users.getModel().getSize() == 0)
		{
			System.out.println("No users");
			selectUserButton.setEnabled(false);
		}
		add(createNewUserButton);
		add(selectUserButton);
	}

}

