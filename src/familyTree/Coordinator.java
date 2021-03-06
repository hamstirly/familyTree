package familyTree;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
@SuppressWarnings("serial")
public class Coordinator extends JPanel implements ActionListener{
	private static JFrame frame, addFrame;
	private static JFrame determineFamilyMemberFrame;
	private static Coordinator addPanel, determinePanel;
	private static JTextField nameField;
	private static JLabel addLabel;
	private static JLabel determineLabel;
	private static JButton normalCloseButton, nextButton, determineCloseButton;
	private static FamilyMember newFamilyMember;
	private static String tempGivenName, tempFamilyName;
	private static int addStep;
	private static boolean onGivenName;
	private static int familyNumber;
	private static ArrayList<FamilyMember> familyList = new ArrayList<FamilyMember>();
	private static final String MASTER_PATH = "C:\\Users\\sawye\\github-repos\\familyTree\\src\\familyTreeResources\\";
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = (int) (screenSize.getWidth());
	public static final int SCREEN_HEIGHT = (int) (screenSize.getHeight());
	public static void main(String[] args) {
		File folder = new File(MASTER_PATH + "testFamily");
		File[] data = folder.listFiles();
		String family = "testFamily";
		try {data.toString();}
		catch(Exception nullPointerException) {
			System.err.println("Master path wrong, please check file structure for proper path and update file.");
			System.exit(0);
		}
		for(int i = 0; i < data.length; i++) {
			if(data[i].isDirectory()){continue;}
			Scanner fileReader = null;
			try {fileReader = new Scanner(new File(MASTER_PATH + family + "\\" + data[i].getName()));}
			catch (FileNotFoundException e) {}
			familyList.add(new FamilyMember(fileReader.nextLine(), fileReader.nextLine(), fileReader.nextLine(),
					fileReader.nextLine(), Integer.valueOf(fileReader.nextLine()), fileReader.nextLine(), fileReader.nextLine(),
					fileReader.nextInt(),
					fileReader.nextInt()));
			String[] parentNames = null, significantOtherNames = null, childNames = null;
			if(fileReader.hasNext()) parentNames = fileReader.next().split(";");
			if(fileReader.hasNext()) significantOtherNames = fileReader.next().split(";");
			if(fileReader.hasNext()) childNames = fileReader.next().split(";");
			for(int j = 0; parentNames != null && j < parentNames.length; j++) {
				String[] parent = parentNames[j].split(",");
				familyList.get(i).addParentNameSet(parent);
			}
			for(int j = 0; significantOtherNames != null && j < significantOtherNames.length; j++) {
				String[] significantOther = significantOtherNames[j].split(",", 2);
				familyList.get(i).addSignificantOtherNameSet(significantOther);
			}
			for(int j = 0; childNames != null && j < childNames.length; j++) {
				String[] child = childNames[j].split(",", 2);
				familyList.get(i).addChildNameSet(child);
			}
			fileReader.close();
		}
		for(int i = 0; i < familyList.size(); i++) {
			ArrayList<String[]> parentNameSets = familyList.get(i).getParentNameSets();
			ArrayList<String[]> significantOtherNameSets = familyList.get(i).getSignificantOtherNameSets();
			ArrayList<String[]> childNameSets = familyList.get(i).getChildNameSets();
			for(int j = 0; j < parentNameSets.size(); j++) {
				for(int k = 0; k < familyList.size(); k++) {
					//System.out.println(familyList.get(k).getGivenName());
					//System.out.println(parentNameSets.get(j)[0]);
					if(familyList.get(k).getGivenName().equals(parentNameSets.get(j)[0]) && familyList.get(k).getFamilyName().equals(parentNameSets.get(j)[1])){
						familyList.get(i).addParent(familyList.get(k));
					}
				}
			}
			for(int j = 0; j < significantOtherNameSets.size(); j++) {
				for(int k = 0; k < familyList.size(); k++) {
					if(familyList.get(k).getGivenName().equals(significantOtherNameSets.get(j)[0]) && familyList.get(k).getFamilyName().equals(significantOtherNameSets.get(j)[1])){
						familyList.get(i).addSignificantOther(familyList.get(k));
					}
				}
			}
			for(int j = 0; j < childNameSets.size(); j++) {
				for(int k = 0; k < familyList.size(); k++) {
					if(familyList.get(k).getGivenName().equals(childNameSets.get(j)[0]) && familyList.get(k).getFamilyName().equals(childNameSets.get(j)[1])){
						familyList.get(i).addChild(familyList.get(k));
					}
				}
			}
		}

		//for(int i = 0; i < familyList.size(); i++) {familyList.get(i).printBirthDate();}
		frame = new JFrame("Family Tree");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Coordinator panel = new Coordinator();
		//panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		panel.setFrame(frame);
		panel.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		normalCloseButton = new JButton("Close");
		normalCloseButton.addActionListener(panel);
		panel.add(normalCloseButton, "North");
		JButton newFamilyMemberButton = new JButton("Add New Family Member");
		newFamilyMemberButton.addActionListener(panel);
		panel.add(newFamilyMemberButton, "South");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
		panel.repaint();
		//determineFromMultipleNames(new String[]{"Sawyer", "Miedema"});
		do {} while (true);
	}
	public static void determineFromMultipleNames(String[] name) {
		determineFamilyMemberFrame = new JFrame("Determine From Multiple Names");
		determinePanel = new Coordinator();
		determinePanel.setFrame(determineFamilyMemberFrame);
		determinePanel.setLayout(new BorderLayout());
		determineFamilyMemberFrame.getContentPane().add(determinePanel);
		determineCloseButton = new JButton("Close");
		determineCloseButton.addActionListener(determinePanel);
		determinePanel.add(determineCloseButton, "North");
		String labelString = "<html>Were you looking for ";
		int successes = 0;
		for(int i = 0; i < familyList.size(); i++) {
			if(familyList.get(i).getGivenName().equals(name[0]) && familyList.get(i).getFamilyName().equals(name[1])) {
				if(successes >= 1) { labelString += "<br>&emsp or ";}
				successes++;
				labelString = labelString + "(" + successes + ") " + familyList.get(i).getGivenName();
				if(!familyList.get(i).getMiddleName().equals(null)) {
					labelString += " " + familyList.get(i).getMiddleName();
					if(familyList.get(i).getNeeName().equals(null) && !familyList.get(i).getNeeName().equals(familyList.get(i).getFamilyName())) labelString += " " + familyList.get(i).getNeeName();
				}
				labelString += " " + familyList.get(i).getFamilyName();
			}
		}
		labelString += "?</html>";
		determineLabel = new JLabel(labelString);
		determinePanel.add(determineLabel);
		determineFamilyMemberFrame.pack();
		determineFamilyMemberFrame.setSize(600, 200);
		determineFamilyMemberFrame.setVisible(true);
	}
	public JFrame getFrame() {return frame;}
	public void setFrame(JFrame frame) {Coordinator.frame = frame;}
	public void actionPerformed(ActionEvent e) {
		String optionalIndicator = " or Press 'Next'";
		if(e.getSource().equals(normalCloseButton)) {System.exit(0);}
		else if(e.getSource().equals(determineCloseButton)) {determineFamilyMemberFrame.dispose(); determineFamilyMemberFrame = null;}
		else if(e.getSource().equals(nextButton)) {
			System.out.println("On addstep " + addStep);
			onGivenName = false;
			nameField.setText("");
			familyNumber = 1;
			switch(addStep) {
				case 1: {newFamilyMember.setMiddleName(null); addLabel.setText("Enter Nee Name" + optionalIndicator); break;}
				case 2: {newFamilyMember.setNeeName(null); addLabel.setText("Enter Family Name"); addPanel.remove(nextButton); break;}
				case 3: {newFamilyMember.setFamilyName(null); addLabel.setText("Enter Numeral Suffix (e.g. 2 )" + optionalIndicator); break;}
				case 4: {newFamilyMember.setNumeralSuffix(-1); addLabel.setText("Enter Professional Suffix (e.g. PhD)" + optionalIndicator); break;}
				case 5: {newFamilyMember.setProfessionalSuffix(null); addLabel.setText("Enter Gender" + optionalIndicator); break;}
				case 6: {newFamilyMember.setGender(null); addLabel.setText("Enter Birth Date: MMDDYYYY" + optionalIndicator); break;}
				case 7: {newFamilyMember.setBirthDate(-1); addLabel.setText("Enter Death Date: MMDDYYYY" + optionalIndicator); break;}
				case 8: {newFamilyMember.setDeathDate(-1);
					if(addLabel.getText().equals("Enter Death Date: MMDDYYYY" + optionalIndicator) || addLabel.getText().equals("Please Enter Death Date in this format: MMDDYYYY" + optionalIndicator)) {
						//addStep--;
						addLabel.setText("Enter Parent #1 Given Name" + optionalIndicator); break;
					}
				}
				case 9: { if(newFamilyMember.getParentNameSets().size() == 0) { newFamilyMember.addParentNameSet(new String[2]); }
					addLabel.setText("Enter Significant Other Given Name" + optionalIndicator); break;
				}
				case 10: { if(newFamilyMember.getSignificantOtherNameSets().size() == 0) { newFamilyMember.addSignificantOtherNameSet(new String[2]); }
					addLabel.setText("Enter Child #1 Given Name" + optionalIndicator); break;}
				case 11: {
					try {
						File newFamilyMemberFile = new File(MASTER_PATH + "\\testFamily\\" + newFamilyMember.getGivenName().toLowerCase() + newFamilyMember.getFamilyName() + ".fm");
						PrintWriter out;
						if(newFamilyMemberFile.createNewFile()) {
							out = new PrintWriter(MASTER_PATH + "\\testFamily\\" + newFamilyMember.getGivenName().toLowerCase() + newFamilyMember.getFamilyName() + ".fm");
						}
						else {
							int i = 1;
							do {
								newFamilyMemberFile = new File(MASTER_PATH + "\\testFamily\\" + newFamilyMember.getGivenName().toLowerCase() + newFamilyMember.getFamilyName() + i + ".fm");
								i++;
							} while(!newFamilyMemberFile.createNewFile());
							i--;
							out = new PrintWriter(MASTER_PATH + "\\testFamily\\" + newFamilyMember.getGivenName().toLowerCase() + newFamilyMember.getFamilyName() + i + ".fm");
						}
						out.println(newFamilyMember.getGivenName());
						out.println(newFamilyMember.getMiddleName());
						out.println(newFamilyMember.getNeeName());
						out.println(newFamilyMember.getFamilyName());
						out.println(newFamilyMember.getNumeralSuffix());
						out.println(newFamilyMember.getProfessionalSuffix());
						out.println(newFamilyMember.getGender());
						out.println(newFamilyMember.getBirthDate());
						out.println(newFamilyMember.getDeathDate());
						ArrayList<String[]> parentNameSets = newFamilyMember.getParentNameSets();
						ArrayList<String[]> significantOtherNameSets = newFamilyMember.getSignificantOtherNameSets();
						ArrayList<String[]> childNameSets = newFamilyMember.getChildNameSets();
						for(int j = 0; j < parentNameSets.size(); j++) {
							out.print(parentNameSets.get(j)[0] + "," + parentNameSets.get(j)[1] + ";");
							for(int k = 0; k < familyList.size(); k++) {
								if(familyList.get(k).getGivenName().equals(parentNameSets.get(j)[0]) && familyList.get(k).getFamilyName().equals(parentNameSets.get(j)[1])){
									newFamilyMember.addParent(familyList.get(k));
								}
							}
						}
						out.println();
						for(int j = 0; j < significantOtherNameSets.size(); j++) {
							out.print(significantOtherNameSets.get(j)[0] + "," + significantOtherNameSets.get(j)[1] + ";");
							for(int k = 0; k < familyList.size(); k++) {
								if(familyList.get(k).getGivenName().equals(significantOtherNameSets.get(j)[0]) && familyList.get(k).getFamilyName().equals(significantOtherNameSets.get(j)[1])){
									newFamilyMember.addSignificantOther(familyList.get(k));
								}
							}
						}
						out.println();
						for(int j = 0; j < childNameSets.size(); j++) {
							out.print(childNameSets.get(j)[0] + "," + childNameSets.get(j)[1] + ";");
							for(int k = 0; k < familyList.size(); k++) {
								if(familyList.get(k).getGivenName().equals(childNameSets.get(j)[0]) && familyList.get(k).getFamilyName().equals(childNameSets.get(j)[1])){
									newFamilyMember.addChild(familyList.get(k));
								}
							}
						}
						out.close();
					}
					catch(IOException ioe) {}
					addFrame.dispose();
					addFrame = null;
					familyList.add(newFamilyMember);
					newFamilyMember = null;
					return;
				}
			}
			nameField.setText("");
			addPanel.repaint();
			addStep++;
			System.out.println("exited switch from next, on addstep " + addStep);
		}
		else if(e.getSource().equals(nameField)) {
			System.out.println("On addStep " + addStep);
			switch(addStep) {
				case 0: {
					newFamilyMember = new FamilyMember(e.getActionCommand());
					addLabel.setText("Enter Middle Name" + optionalIndicator);
					nextButton = new JButton("Next");
					nextButton.addActionListener(addPanel);
					addPanel.add(nextButton, "East");
					break;
				}
				case 1: {
					newFamilyMember.setMiddleName(e.getActionCommand());
					addLabel.setText("Enter Nee Name" + optionalIndicator);
					break;
				}
				case 2: {
					newFamilyMember.setNeeName(e.getActionCommand());
					addLabel.setText("Enter Family Name");
					addPanel.remove(nextButton);
					break;
				}
				case 3: {
					newFamilyMember.setFamilyName(e.getActionCommand());
					addLabel.setText("Enter Numeral Suffix (e.g. 2 )" + optionalIndicator);
					nextButton = new JButton("Next");
					nextButton.addActionListener(addPanel);
					addPanel.add(nextButton, "East");
					break;
				}
				case 4: {
					try{newFamilyMember.setNumeralSuffix(Integer.parseInt(e.getActionCommand()));}
					catch(NumberFormatException nfe) {
						System.out.println("In catch block");
						addLabel.setText("Please Enter Numeral Suffix as an integer (e.g. 2)" + optionalIndicator);
						addPanel.repaint();
						return;
					}
					addLabel.setText("Enter Professional Suffix (e.g. PhD)" + optionalIndicator);
					break;
				}
				case 5: {
					newFamilyMember.setProfessionalSuffix(e.getActionCommand());
					addLabel.setText("Enter Gender" + optionalIndicator);
					break;
				}
				case 6: {
					newFamilyMember.setGender(e.getActionCommand());
					addLabel.setText("Enter Birth Date: MMDDYYYY");
					break;
				}
				case 7: {
					try {newFamilyMember.setBirthDate(Integer.parseInt(e.getActionCommand()));}
					catch(NumberFormatException nfe) {
						addLabel.setText("Please Enter Birth Date in this format: MMDDYYYY" + optionalIndicator);
						addPanel.repaint();
						return;
					}
					addLabel.setText("Enter Death Date: MMDDYYYY" + optionalIndicator);
					break;
				}
				case 8: {
					if(addLabel.getText().equals("Enter Death Date: MMDDYYYY" + optionalIndicator) || addLabel.getText().equals("Please Enter Death Date in this format: MMDDYYYY" + optionalIndicator)){
						try {newFamilyMember.setDeathDate(Integer.parseInt(e.getActionCommand()));}
						catch(NumberFormatException nfe) {
							System.out.println("In catch block");
							addLabel.setText("Please Enter Death Date in this format: MMDDYYYY" + optionalIndicator);
							addPanel.repaint();
							return;
						}
						familyNumber = 1;					
						addLabel.setText("Enter Parent #" + familyNumber + " Given Name" + optionalIndicator);
					}
					else if(onGivenName) {
						newFamilyMember.addParentNameSet(new String[]{tempGivenName, e.getActionCommand()});
						addLabel.setText("Enter Parent #" + familyNumber + " Given Name" + optionalIndicator);
						onGivenName = false;
					}
					else {
						tempGivenName = e.getActionCommand();
						addLabel.setText("Enter Parent #" + familyNumber + " Family Name" + optionalIndicator);
						onGivenName = true;
						familyNumber++;
						System.out.println("In parent family name block");
					}
					nameField.setText("");
					return;
				}
				case 9: {
					if(onGivenName) {
						newFamilyMember.addSignificantOtherNameSet(new String[]{tempGivenName, e.getActionCommand()});
						if(familyNumber == 1) {addLabel.setText("Enter Significant Other Given Name" + optionalIndicator);}
						else {addLabel.setText("Enter Former Significant Other #" + (familyNumber - 1)+ " Given Name" + optionalIndicator);}
						onGivenName = false;
					}
					else {
						tempGivenName = e.getActionCommand();
						if(familyNumber == 1) {addLabel.setText("Enter Significant Other Family Name or Press 'Next");}
						else {addLabel.setText("Enter Former Significant Other #" + (familyNumber - 1) + " Family Name" + optionalIndicator);}
						onGivenName = true;
						familyNumber++;
					}
					nameField.setText("");
					return;
				}
				case 10: {
					System.out.println("I'm falling??? Should have returned" + addStep);
					if(onGivenName) {
						newFamilyMember.addChildNameSet(new String[]{tempGivenName, e.getActionCommand()});
						addLabel.setText("Enter Child #" + familyNumber + " Given Name" + optionalIndicator);
						onGivenName = false;
					}
					else {
						tempGivenName = e.getActionCommand();
						addLabel.setText("Enter Child #" + familyNumber + " Family Name" + optionalIndicator);
						onGivenName = true;
						familyNumber++;
					}
					nameField.setText("");
					return;
				}
			}
			nameField.setText("");
			addPanel.repaint();
			addStep++;
			System.out.println("Exited switch from nameField, on addStep " + addStep);
		}
		else if(e.getActionCommand().equals("Close Add Panel")) {addFrame.dispose(); addFrame = null;}
		else if(e.getActionCommand().equals("Add New Family Member") && addFrame == null) {
			addStep = 0;
			newFamilyMember = null;
			addFrame = new JFrame("Add New Family Member");
			addFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			addPanel = new Coordinator();
			addPanel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
			addPanel.setFrame(frame);
			addPanel.setLayout(new BorderLayout());
			JButton closeButton = new JButton("Close Add Panel");
			closeButton.addActionListener(addPanel);
			addPanel.add(closeButton, "North");
			addLabel = new JLabel("Enter Given Name");
			addPanel.add(addLabel, "Center");
			nameField = new JTextField();
			nameField.addActionListener(addPanel);
			addPanel.add(nameField, "South");
			addFrame.getContentPane().add(addPanel);
			//addFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			addFrame.pack();
			addFrame.setSize(600, 200);
			addFrame.setVisible(true);
		}

	}
}