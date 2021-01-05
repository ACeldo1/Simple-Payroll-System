package application;

public class Tax {
private double tax_deduction;
private GrossPay grosspay;
private PersonalInfo personinfo;
//1. single 2 married filing jointly 3. married filing separately 4. head of household
public int status;
private double state_tax;
private double federal_tax;
final double Social_Security_Tax = 0.062;
final double Medicare_tax = 0.0145;
Tax()
{
	 tax_deduction = 0;
	 status = 0;
	 state_tax = 0;
	 federal_tax = 0;
}
Tax(GrossPay g)
{
	grosspay = g;
}
Tax(GrossPay g, PersonalInfo p)
{
	grosspay = g;
	personinfo = p;
}
public double getfederaltax()
{
	return federal_tax;
}
public double getstatetax()
{
	return state_tax;
}
public void setfederaltax(double fedtax) {
	this.federal_tax = fedtax;
}
public void setstatetax(double statetax){
	this.state_tax = statetax;
}

public double getMedicaretax()
{
	return Medicare_tax;
}
public double getSSN_tax()
{
	return Social_Security_Tax;
}
protected GrossPay getgrosspay()
{
	return grosspay;
}
protected PersonalInfo getpersoninfo()
{
	return personinfo;
}
protected void cal_taxdeduct()
{
	find_federaltax();
	find_statetax();
	tax_deduction = (grosspay.getGrossPay()*(federal_tax+state_tax+Social_Security_Tax+Medicare_tax));
}
protected double get_taxdeduct()
{
	return tax_deduction;
}
protected void set_taxdeduct(double t)
{
	tax_deduction = t;
}
protected void add_taxdeduct(double t)
{
	//may need to add more.
	if(checkPersonInfo())
	{
	set_taxdeduct(t);
	
	}
}
protected void delete_taxdeduct()
{
	if(checkPersonInfo())
	{
	set_taxdeduct(0);
	
	}
}
protected void modify_taxdeduct(double t)
{
	if(checkPersonInfo())
	{
	set_taxdeduct(t);
	
	}
}
protected void setstatus(int s)
{
	status = s;
}
protected int getstatus()
{
	return status;
}
protected void find_federaltax()
{
	System.out.println("status:" + status);
	if((status == 1) || (status == 3))
	{
		if(grosspay.getGrossPay()>=0 && grosspay.getGrossPay()<9701)
		{
			this.federal_tax = 0.1;
		}
		else if(grosspay.getGrossPay()>=9701 && grosspay.getGrossPay()<39476)
		{
			this.federal_tax = 0.12;
		}
		else if(grosspay.getGrossPay()>=39476 && grosspay.getGrossPay()<84201)
		{
			this.federal_tax = 0.22;
		}
		else if(grosspay.getGrossPay()>=84201 && grosspay.getGrossPay()<160726)
		{
			this.federal_tax = 0.24;
		}
		else if(grosspay.getGrossPay()>=160726 && grosspay.getGrossPay()<204101)
		{
			this.federal_tax = 0.32;
		}
		else if(grosspay.getGrossPay()>=204101 && grosspay.getGrossPay()<510301)
		{
			this.federal_tax = 0.35;
		}
		else
			this.federal_tax = 0.37;
	}
	//2 married filing jointly
	else if(status == 2)
	{
		if(grosspay.getGrossPay()>=0 && grosspay.getGrossPay()<19401)
		{
			this.federal_tax = 0.1;
		}
		else if(grosspay.getGrossPay()>=19401 && grosspay.getGrossPay()<78951)
		{
			this.federal_tax = 0.12;
		}
		else if(grosspay.getGrossPay()>=78951 && grosspay.getGrossPay()<168401)
		{
			this.federal_tax = 0.22;
		}
		else if(grosspay.getGrossPay()>=168401 && grosspay.getGrossPay()<321451)
		{
			this.federal_tax = 0.24;
		}
		else if(grosspay.getGrossPay()>=321451 && grosspay.getGrossPay()<408201)
		{
			this.federal_tax = 0.32;
		}
		else if(grosspay.getGrossPay()>=408201 && grosspay.getGrossPay()<612351)
		{
			this.federal_tax = 0.35;
		}
		else 
			this.federal_tax = 0.37;
	}
	//4.head of household
	else if(status == 4)
	{
		if(grosspay.getGrossPay()>=0 && grosspay.getGrossPay()<13851)
		{
			this.federal_tax = 0.1;
		}
		else if(grosspay.getGrossPay()>=13851 && grosspay.getGrossPay()<52851)
		{
			this.federal_tax = 0.12;
		}
		else if(grosspay.getGrossPay()>=52851 && grosspay.getGrossPay()<84201)
		{
			this.federal_tax = 0.22;
		}
		else if(grosspay.getGrossPay()>=84201 && grosspay.getGrossPay()<160701)
		{
			this.federal_tax = 0.24;
		}
		else if(grosspay.getGrossPay()>=160701 && grosspay.getGrossPay()<204101)
		{
			this.federal_tax = 0.32;
		}
		else if(grosspay.getGrossPay()>=204101 && grosspay.getGrossPay()<510301)
		{
			this.federal_tax = 0.35;
		}
		else
			this.federal_tax = 0.37;
	}
}
protected void find_statetax()
{
	//single and married filing separately
	if((status == 1)||(status == 3))
	{
		if(grosspay.getGrossPay()>=0 && grosspay.getGrossPay()<8501)
		{
			this.state_tax = 0.04;
		}
		else if(grosspay.getGrossPay()>=8501 && grosspay.getGrossPay()<11701)
		{
			this.state_tax = 0.045;
		}
		else if(grosspay.getGrossPay()>=11701 && grosspay.getGrossPay()<13901)
		{
			this.state_tax = 0.0525;
		}
		else if(grosspay.getGrossPay()>=13901 && grosspay.getGrossPay()<21401)
		{
			this.state_tax = 0.059;
		}
		else if(grosspay.getGrossPay()>=21401 && grosspay.getGrossPay()<80651)
		{
			this.state_tax = 0.0621;
		}
		else if(grosspay.getGrossPay()>=80651 && grosspay.getGrossPay()<215401)
		{
			this.state_tax = 0.0649;
		}
		else if(grosspay.getGrossPay()>=215401 && grosspay.getGrossPay()<1077551)
		{
			this.state_tax = 0.0685;
		}
		else
			this.state_tax = 0.0882;
	}
	//2 married filing jointly
	else if(status == 2)
	{
		if(grosspay.getGrossPay()>=0 && grosspay.getGrossPay()<17151)
		{
			this.state_tax= 0.04;
		}
		else if(grosspay.getGrossPay()>=17151 && grosspay.getGrossPay()<23601)
		{
			this.state_tax = 0.045;
		}
		else if(grosspay.getGrossPay()>=23601 && grosspay.getGrossPay()<27901)
		{
			this.state_tax = 0.0525;
		}
		else if(grosspay.getGrossPay()>=27901 && grosspay.getGrossPay()<43001)
		{
			this.state_tax = 0.059;
		}
		else if(grosspay.getGrossPay()>=43001 && grosspay.getGrossPay()<161551)
		{
			this.state_tax = 0.0621;
		}
		else if(grosspay.getGrossPay()>=161551 && grosspay.getGrossPay()<323201)
		{
			this.state_tax = 0.0649;
		}
		else if(grosspay.getGrossPay()>=323201 && grosspay.getGrossPay()<2155351)
		{
			this.state_tax = 0.0685;
		}
		else
			this.state_tax = 0.0882;
	}
	else if(status == 4)
	{
		if(grosspay.getGrossPay()>=0 && grosspay.getGrossPay()<12801)
		{
			this.state_tax = 0.04;
		}
		else if(grosspay.getGrossPay()>=12801 && grosspay.getGrossPay()<17651)
		{
			this.state_tax = 0.045;
		}
		else if(grosspay.getGrossPay()>=17651 && grosspay.getGrossPay()<20901)
		{
			this.state_tax = 0.0525;
		}
		else if(grosspay.getGrossPay()>=20901 && grosspay.getGrossPay()<32201)
		{
			this.state_tax = 0.059;
		}
		else if(grosspay.getGrossPay()>=32201 && grosspay.getGrossPay()<107651)
		{
			this.state_tax = 0.0621;
		}
		else if(grosspay.getGrossPay()>=107651 && grosspay.getGrossPay()<269301)
		{
			this.state_tax= 0.0649;
		}
		else if(grosspay.getGrossPay()>=269301 && grosspay.getGrossPay()<1616450)
		{
			this.state_tax = 0.0685;
		}
		else
			this.state_tax = 0.0882;
	}
	
}
protected boolean checkPersonInfo()
{
	//check if the info is all set.
	if(personinfo.getFirstName().isEmpty()||personinfo.getLastName().isEmpty()||personinfo.getAddress().isEmpty()||personinfo.getSocialSecurity().isEmpty())
	{
		return false;
	}
	return true;
}
}

