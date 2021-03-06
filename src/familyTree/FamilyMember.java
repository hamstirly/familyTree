package familyTree;

import java.util.ArrayList;

public class FamilyMember {
	public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};;
	public static final String[] DAY_ENDINGS = {"st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th"};
	private String givenName;
	private String middleName;
	private String neeName;
	private String familyName;
	private String professionalSuffix;
	private String gender;
	private int numeralSuffix;
	private int birthDate;
	private int deathDate;
	private ArrayList<String[]> parentNameSets = new ArrayList<String[]>();
	private ArrayList<String[]> significantOtherNameSets = new ArrayList<String[]>();
	private ArrayList<String[]> childNameSets = new ArrayList<String[]>();
	private ArrayList<FamilyMember> parents = new ArrayList<FamilyMember>();
	private ArrayList<FamilyMember> significantOthers = new ArrayList<FamilyMember>();
	private ArrayList<FamilyMember> children = new ArrayList<FamilyMember>();
	public FamilyMember(String givenName) {this.givenName = givenName;}
	public FamilyMember(String givenName, String middleName, String neeName, String familyName, int numeralSuffix, String professionalSuffix,
			String gender, int birthDate, int deathDate) {
		this.givenName = givenName;
		this.middleName = middleName;
		this.neeName = neeName;
		this.familyName = familyName;
		this.numeralSuffix = numeralSuffix;
		this.professionalSuffix = professionalSuffix;
		this.gender = gender;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
	}
	public void printBirthDate() {
		int birthDay = (birthDate % 1000000)/10000;
		int birthYear = (birthDate % 10000);
		int birthDayMath = birthDay;
		if(birthDay > 10) {birthDayMath = birthDayMath%10; if(birthDayMath == 0) {birthDayMath += 10;}}
		System.out.println(givenName + " was born on " + MONTHS[(birthDate / 1000000 - 1)] + " " + birthDay + DAY_ENDINGS[birthDayMath - 1] + ", " + birthYear + ".");
	}
	public void printDeathDate() {
		if(deathDate == 0) {return;}
		int deathDay = (deathDate % 1000000)/10000;
		int deathYear = (deathDate % 10000);
		int deathDayMath = deathDay;
		if(deathDay > 10) {deathDayMath = deathDayMath%10; if(deathDayMath == 0) {deathDayMath += 10;}}
		System.out.println(givenName + " died on " + MONTHS[(deathDate / 1000000 - 1)] + " " + deathDay + DAY_ENDINGS[deathDayMath - 1] + ", " + deathYear + ".");
	}
	public void printParents() {
		System.out.print(givenName + "'s parents are ");
		for(int i = 0; i < parents.size() - 1; i++) {
			System.out.print(parents.get(i).getGivenName() + ", ");
		}
		if(parents.size() > 0) {System.out.println(" and " + parents.get(parents.size() - 1).getGivenName());}
	}
	public String getGivenName() {return givenName;}
	public String getMiddleName() {return middleName;}
	public String getNeeName() {return neeName;}
	public String getFamilyName() {return familyName;}
	public String getProfessionalSuffix() {return professionalSuffix;}
	public String getGender() {return gender;}
	public int getNumeralSuffix() {return numeralSuffix;}
	/**Returns FamilyMember gender as an integer.
	 * 1 is Male, 2 is Female, 0 is other**/
	public int getAbsoluteGender() {
		String gender = this.getGender().toLowerCase();
		if(gender.equals("male")) {return 1;}
		else if (gender.equals("female")){return 2;}
		else {return 0;}
	}
	public int getBirthDate() {return birthDate;}
	public int getDeathDate() {return deathDate;}
	public ArrayList<String[]> getParentNameSets() {return parentNameSets;}
	public ArrayList<String[]> getSignificantOtherNameSets() {return significantOtherNameSets;}
	public ArrayList<String[]> getChildNameSets() {return childNameSets;}
	public void addParent(FamilyMember fm) {parents.add(fm);}
	public void addSignificantOther(FamilyMember fm) {significantOthers.add(fm);}
	public void addChild(FamilyMember fm) {children.add(fm);}
	public void addParentNameSet(String[] parentNames) {parentNameSets.add(parentNames);}
	public void addSignificantOtherNameSet(String[] significantOtherNames) {significantOtherNameSets.add(significantOtherNames);}
	public void addChildNameSet(String[] childNames) {childNameSets.add(childNames);}
	public void setGivenName(String givenName) {this.givenName = givenName;}
	public void setMiddleName(String middleName) {this.middleName = middleName;}
	public void setNeeName(String neeName) {this.neeName = neeName;}
	public void setFamilyName(String familyName) {this.familyName = familyName;}
	public void setGender(String gender) {this.gender = gender;}
	public void setProfessionalSuffix(String professionalSuffix) {this.professionalSuffix = professionalSuffix;}
	public void setNumeralSuffix(int numeralSuffix) {this.numeralSuffix = numeralSuffix;}
	public void setBirthDate(int birthDate) {this.birthDate = birthDate;}
	public void setDeathDate(int deathDate) {this.deathDate = deathDate;}
}