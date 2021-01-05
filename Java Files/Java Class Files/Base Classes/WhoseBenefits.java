package application;

import java.util.function.Supplier;

public class WhoseBenefits {
	double Individual_and_Spouse;
	double Individual;
	double ParentAndChildren;
	double Family;
	double Child;
	
	public double getChild() {
		return Child;
	}
	public void setChild(double child) {
		Child = child;
	}
	WhoseBenefits()
	{}
	public double getIndividual_and_Spouse() {
		return Individual_and_Spouse;
	}
	public void setIndividual_and_Spouse(double individual_and_Spouse) {
		Individual_and_Spouse = individual_and_Spouse;
	}
	public double getIndividual() {
		return Individual;
	}
	public void setIndividual(double individual) {
		Individual = individual;
	}
	public double getParentAndChildren() {
		return ParentAndChildren;
	}
	public void setParentAndChildren(double benefits) {
		ParentAndChildren = benefits;
	}
	public double getFamily() {
		return Family;
	}
	public void setFamily(double family) {
		Family = family;
	}
	
	public String toString()
	{
		return 	Individual+", "+Individual_and_Spouse+", "+ParentAndChildren+", "+Family+", "+Child;
	}

	public double execute(Supplier t)
	{
		return (double) t.get();
	}
	
}


