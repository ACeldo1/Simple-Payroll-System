package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;

public class Benefits {
	
	FileWriter OverWriter;
	public String [] PackageCode;
	public double MedicalDeductable;
	public double DentalDeductable;
	private PersonalInfo personinfo;
	HashMap<String, HashMap<String, Packages>> AllMedicalBenefits = new HashMap<String, HashMap<String, Packages>>();
	

	

	
	Benefits() throws IOException{
		File file = new File("C:/Users/drtrt/eclipse-workspace/MyJavaFX/src/application/BenefitsList"); 
		HashMap<String, Packages> MedicalBenefits = new HashMap<String, Packages>();
		double [] deductionCarrier = new double[25];
		double [] transfer = new double [5]; 
		
		int count = 0;
		Packages onePackage;
		String locationName = new String ();
		String name = new String();
		
		
		
		Scanner sc = new Scanner(file); 
		String [] line;
		line = sc.nextLine().split("[,]", 0);
		 
		name = line[0];
		
		
		while(sc.hasNextLine())
		{
			onePackage = new Packages();
			line = sc.nextLine().split("[,]", 0);
			
			if (line.length == 1)
			{
				AllMedicalBenefits.put(name, MedicalBenefits);
				MedicalBenefits = new HashMap<String, Packages>();
				name = line[0];
				
			}
			else {
			
			for(int i = 0; i < line.length; i++)
			{
				line[i] = line[i].trim();
			
			
			
			if(i == 0)
					locationName = line[0];
			
			else {
				if (line[i].compareTo("N/A") == 0)
				deductionCarrier[i-1] = Double.MAX_VALUE;
					else 
				deductionCarrier[i-1] = Double.parseDouble(line[i]);
			}
			
			}
			
			for(int i = 0; i<5; i++)
			{
				for(int j = 0; j<5; j++)
				{
					transfer[j]= deductionCarrier[count];
					count++;
				}
				
				if (i==0)
				onePackage.setBasics(transfer);
			
				else if (i == 1)
					onePackage.setBronze(transfer);
				else if(i == 2)
					onePackage.setSilver(transfer);
				else if (i == 3)
					onePackage.setGold(transfer);
				else if(i == 4)
					onePackage.setPlatinum(transfer);
			}
				MedicalBenefits.put(locationName, onePackage);
			count = 0;
			
			}
			
			}
			AllMedicalBenefits.put(name, MedicalBenefits);
	}

	/*AO3 = over 30; AU3 = under 30; BCBS = bluecross blueshild
	
	**DS = Downstate; LI = long Island; A = Albany
	**MH = Mid-Hudson; S = Syracuse; UW = Utica/Watertown
	**ALL = All
	
	**BSK = basic; BRZ= Bronze; SLV = Silver; GLD = Gold
	**PTM = Platinum
	
	**I = Individual; IAS = Individual & Spouse; PAC = Parent & Child
	**F = Family; C = Child
	
	*/
				
	private void setDeductions()
	{
		Packages packages = new Packages();
		WhoseBenefits whoseBenefits = new WhoseBenefits();
		String first = "", second = ""; 
		double result = 0;
		Supplier third = null, fourth = null;
		
	
		//AO3 = over 30; AU3 = under 30; BCBS = bluecross blueshild
		if (PackageCode[0].contentEquals("AO3"))
		first = "Emblem Health > 30";	
		
		else if (PackageCode[0].contentEquals("AU3"))
		first = "Emblem Health < 30";
		
		else if (PackageCode[0].contentEquals("BCBS"))
			first = "BlueCross BlueShield";
	
		
		
		//DS = Downstate; LI = long Island; A = Albany
		//MH = Mid-Hudson; S = Syracuse; UW = Utica/Watertown
		//ALL = All
		if (PackageCode[1].contentEquals("DS"))
			second = "DownState";
		
		else if (PackageCode[1].contentEquals("LI"))
			second = "Long Island";
		
		else if (PackageCode[1].contentEquals("A"))
			second = "Albany";
		
		else if (PackageCode[1].contentEquals("MH"))
			second = "Mid-Hudson";
		
		else if (PackageCode[1].contentEquals("S"))
			second = "Syracuse";
		
		else if (PackageCode[1].contentEquals("UW"))
			second = "Utica/WaterTown";
		
		else if (PackageCode[1].contentEquals("ALL"))
			second = "All";
		
		
		if (first != "" && second != "")
		{
		
		packages=AllMedicalBenefits.get(first).get(second);
		
		
		//BSK = basic; BRZ= Bronze; SLV = Silver; GLD = Gold
		//PTM = Platinum
		if (PackageCode[2].contentEquals("BSK"))
			third = packages::getBasics;
		
		else if (PackageCode[2].contentEquals("BRZ"))
			third = packages::getBronze;
			
		else if (PackageCode[2].contentEquals("SLV"))
			third = packages::getSilver;
			
		else if (PackageCode[2].contentEquals("GLD"))
			third = packages::getGold;
			
		else if (PackageCode[2].contentEquals("PTM"))
			third = packages::getPlatinum;
	
		
		whoseBenefits=AllMedicalBenefits.get(first).get(second).execute(third);
		
		//I = Individual; IAS = Individual & Spouse; PAC = Parent & Child
		//F = Family; C = Child
		
		 if (PackageCode[3].contentEquals("I"))
			fourth = whoseBenefits::getIndividual;
			
		else if (PackageCode[3].contentEquals("IAS"))
			fourth = whoseBenefits::getIndividual_and_Spouse;
			
		else if (PackageCode[3].contentEquals("PAC"))
			fourth = whoseBenefits::getParentAndChildren;
			
		else if (PackageCode[3].contentEquals("F"))
			fourth = whoseBenefits::getFamily;
		
		else if (PackageCode[3].contentEquals("C"))
			fourth = whoseBenefits::getChild;
		 
		 result = AllMedicalBenefits.get(first).get(second).execute(third).execute(fourth);
		
		
		}
	
		
		else 
			System.out.println("It was not found");
		
	
		if(result == Double.MAX_VALUE)
			System.out.println("That package or option isn't available");
		
		else 
			MedicalDeductable = result;
			
			 System.out.println(result);
	}

	protected double getMedicalDeductable() {
		return MedicalDeductable;
	}

	private void DeleteBenefits(String name) throws IOException
	{
		
		HashMap<String, Packages> medicalBenefits = new HashMap<String, Packages>();
		OverWriter = new FileWriter("C:\\Users\\Owner\\eclipse-workspace\\Kimberly\\src\\CSC430\\BenefitsList.txt", false);
		AllMedicalBenefits.remove(name);
		Packages aPackage;
		WhoseBenefits something = new WhoseBenefits();
		String theString = new String ();
		
		 for (String i : AllMedicalBenefits.keySet()) {
			 
			 medicalBenefits = AllMedicalBenefits.get(i);
			 theString += i+"\n";
			 for (String j : medicalBenefits.keySet()) {
				
				 aPackage= medicalBenefits.get(j);
				 
				 theString += j+", ";
				 for(int k = 0; k< 5; k++)
				 {
					 if (k==0)
					 something = aPackage.getBasics();
					 else if(k==1)
						 something = aPackage.getBronze();
					 else if(k==2)
						 something = aPackage.getSilver();
					 else if(k==3)
						 something = aPackage.getGold();
					 else if(k==4)
						 something = aPackage.getPlatinum();
					 
					 
				 if (something.getIndividual() == Double.MAX_VALUE)
					theString += "N/A, ";
				 else
					 theString += something.getIndividual()+", ";
				
				 if (something.getIndividual_and_Spouse() == Double.MAX_VALUE)
					 theString += "N/A, ";
				 else
					 theString += something.getIndividual_and_Spouse() +", ";
				
				 if (something.getParentAndChildren() == Double.MAX_VALUE)
					 theString += "N/A, ";
				 else
					 theString += something.getParentAndChildren() +", ";
				 
				 if (something.getFamily() == Double.MAX_VALUE)
					 theString += "N/A, ";
				 else
					 theString += something.getFamily() +", ";
				 
				 if (something.getChild() == Double.MAX_VALUE)
					 theString += "N/A, ";
				 else
				 {	if(k==4)
					 theString += something.getChild();
				 
				 else
					 theString += something.getChild()+", ";
				 }
				 }
				 theString += "\n";
			 }
		    }
		
		 
		 String completeString = new String ();
		 for(int i = 0; i <theString.toCharArray().length-3; i++)
			 completeString += theString.charAt(i);
			 
		 
		OverWriter.write(completeString);
		
	
		OverWriter.close();
	}
	
//Now we need to populate each thing individually,
	
	public String[] getPackageCode() {
		return PackageCode;
	}
	


	
	public String generateCode(String first, String second, String third, String fourth)
	{
		String code = "";
		
		if (first.contentEquals("Emblem Health > 30"))
			code ="AO3-";
			
		else if(first.contentEquals("Emblem Health < 30"))
			code ="AU3-";
		
		else if(first.contentEquals("BlueCross BlueShield"))
			code ="BCBS-";
		
		if (second.contentEquals("DownState"))
			code += "DS-";
		
		else if (second.contentEquals("Long Island"))
			code += "LI-";
		
		else if (second.contentEquals("Albany"))
			code += "A-";
		
		else if (second.contentEquals("Mid-Hudson"))
			code += "MH-";
		
		else if (second.contentEquals("Syracuse"))
			code += "S-";
		
		else if (second.contentEquals("Utica/WaterTown"))
			code += "UW-";
		
		else if (second.contentEquals("All"))
			code += "ALL-";
		
		if (third.contentEquals("Basic"))
		code += "BSK-";
		
		else if (third.contentEquals("Bronze"))
			code += "BRZ-";
		
		else if (third.contentEquals("Silver"))
			code += "SLV-";
		
		else if (third.contentEquals("Gold"))
			code += "GLD-";
		
		else if (third.contentEquals("Platinum"))
			code += "PTM-";

		if (fourth.contentEquals("Individual")) 
		code += "I";
		
		else if (fourth.contentEquals("Individual and Spouse")) 
			code += "IAS";
		
		else if (fourth.contentEquals("Parent and Child")) 
			code += "PAC";
		
		else if (fourth.contentEquals("Family")) 
			code += "F";
		
		else if (fourth.contentEquals("Child")) 
			code += "C";
		
		return code;
		
	}
	
	
	
	
	
	public void setPackageCode(String packageCode) {
		PackageCode = packageCode.split("-");
	}
	
	public double getDentalDeductable() {
		return DentalDeductable;
	}

	public void setDentalDeductable(int numOfFamily) {
		if (numOfFamily < 3)
			DentalDeductable = numOfFamily * 2.08;
		
		else 
			DentalDeductable = 6.25;
		
	}
	
//	public static void main(String[] args) throws IOException {
//		
//		
//	}
	
}


