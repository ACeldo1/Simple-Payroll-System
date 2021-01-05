package application;

import java.util.function.Supplier;

public class Packages {
	
	WhoseBenefits Basics;
	WhoseBenefits Bronze;
	WhoseBenefits Silver;
	WhoseBenefits Gold;
	WhoseBenefits Platinum;

	
	Packages()
	{
		Basics = new WhoseBenefits();
		Bronze = new WhoseBenefits();
		Silver = new WhoseBenefits();
		Gold = new WhoseBenefits();
		Platinum = new WhoseBenefits();
	}


	public WhoseBenefits getBasics() {
		return Basics;
	}


	public void setBasics(double[]benefits) {
		Basics.setIndividual(benefits[0]);
		Basics.setIndividual_and_Spouse(benefits[1]);
		Basics.setParentAndChildren(benefits[2]);
		Basics.setFamily(benefits[3]);
		Basics.setChild(benefits[4]);
	}


	public WhoseBenefits getBronze() {
		return Bronze;
	}


	public void setBronze(double[]benefits) {
		Bronze.setIndividual(benefits[0]);
		Bronze.setIndividual_and_Spouse(benefits[1]);
		Bronze.setParentAndChildren(benefits[2]);
		Bronze.setFamily(benefits[3]);
		Bronze.setChild(benefits[4]);
	}


	public WhoseBenefits getSilver() {
		return Silver;
	}


	public void setSilver(double[]benefits) {
		Silver.setIndividual(benefits[0]);
		Silver.setIndividual_and_Spouse(benefits[1]);
		Silver.setParentAndChildren(benefits[2]);
		Silver.setFamily(benefits[3]);
		Silver.setChild(benefits[4]);
	}


	public WhoseBenefits getGold() {
		return Gold;
	}


	public void setGold(double[]benefits) {
		Gold.setIndividual(benefits[0]);
		Gold.setIndividual_and_Spouse(benefits[1]);
		Gold.setParentAndChildren(benefits[2]);
		Gold.setFamily(benefits[3]);
		Gold.setChild(benefits[4]);
	}


	public WhoseBenefits getPlatinum() {
		return Platinum;
	}


	public void setPlatinum(double[]benefits) {
		Platinum.setIndividual(benefits[0]);
		Platinum.setIndividual_and_Spouse(benefits[1]);
		Platinum.setParentAndChildren(benefits[2]);
		Platinum.setFamily(benefits[3]);
		Platinum.setChild(benefits[4]);
	}
	
	public String toString()
	{
		return 	"Basic: "+Basics +" Bronze: "
				+Bronze+" Silver: "+Silver+" Gold: "
				+Gold+" Platinum: "+Platinum;
	}
	
	public WhoseBenefits execute(Supplier t)
	{
		
		return (WhoseBenefits) t.get();
	}
	
}
