package application;

public class NetPay {
	
	public PersonalInfo person;
	public GrossPay gross_pay;
	public Tax tax;
	private double net_pay;
	private long bank_account;
	public String pay_date;
	public String label;
	
	
	public Double netpay;
	public Double gross;
	public Double taxAmount;
	
	NetPay(PersonalInfo p, GrossPay g, Tax tax){
	//connect with other classes.
		person = p;
		gross_pay = g;
		//tax = t;
		net_pay=0;
		bank_account = 0;
		pay_date = null;
	}
	
	NetPay(GrossPay g, Tax t) {
		
		gross_pay = g;
		tax = t;
		
	}
	
	public void SetGross(Double g) {
		this.gross = g;
	}
	
	public Double GetGross() {
		return gross;
	}

	public void SetTaxAmount(Double t) {
		this.taxAmount = t;
	}
	
	public Double GetTaxAmount() {
		return taxAmount;
	}
	
	public void SetNetPay(Double net) {
		this.netpay = net;
	}
	
	public Double GetNetPay() {
		return netpay;
	}
	
	public void CalculatedNetPay() {
		SetNetPay(GetGross() - GetTaxAmount());
	}
	
	//everything below this line can be modified whenever
	//I included the necessary functions above
	
	
	public long getbank_account() {
		return bank_account;
	}

	public void setbank_account(long account) {
		bank_account = account;
	}

	public String getpay_date() {
		return pay_date;
	}

	public void setpay_date(String date) {
		pay_date = date;
	}

	public PersonalInfo getPerson() {
		return person;
	}

	public GrossPay getGross_pay() {
	//based on the name, can change the name.
		return gross_pay;
	}

	//public Tax getTax() {
	// can change the name.
	//	return tax;
	//}

	public void setnet_pay(double pay) {
	//set net_pay.
		net_pay = pay;
	}

	public double getnet_pay() {
	//return netpay.
		return net_pay;
	}

	public String getlabel() {
	//label
		return label;
	}

//	calculate net_pay and set it.
	public void calnet_pay() {
		setnet_pay(gross_pay.getGrossPay()- tax.get_taxdeduct());
	}

	public void addnet_pay(double pay) {
	//if we have this person info, we can add a new net pay.
		if(checkPersonInfo())
			setnet_pay(pay);
	}

	public void modifynet_pay(double pay) {
	//if we have this person info, we can modify it.
		if(checkPersonInfo())
			setnet_pay(pay);
	}

	public void deletenet_pay() {
	// if we have this person info, we can delete which set it to 0.(I guess that is what she wanted).
		if(checkPersonInfo())
			setnet_pay(0);
	}

	public boolean checkPersonInfo() {
	//check if the info is all set.
		if(person.getFirstName().isEmpty()||person.getLastName().isEmpty()||person.getAddress().isEmpty()||person.getSocialSecurity().isEmpty()) {
			return false;
		}		
		return true;
	}
}