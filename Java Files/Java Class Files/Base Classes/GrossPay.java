package application;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class GrossPay {

	PersonalInfo person;
	private Integer userId;
	
	private Double salary; //per hour for now, could have a drop down menu or something
	private Double bonuses;//so that they could choose if its per year or month, w.e
	protected Integer fulltime;						//private String salaryType;
	
	private Double raisedSalary;
	private Double grossPay;
	
	private Double hours; 
	private Double overtimeHours;
	
	private String raiseReason;
	private String raiseEffectiveDate;
	
	private Date lastPayDay;
	
	protected Double totalWages;
	protected Double overtimeWages;
	protected Double raiseTotal;
	protected Double otherWages;
	protected Double raiseOvertimeWages;
	
	//added variables
	private Integer wage = 15;
	
	public GrossPay() {

//		getRaisedSalary().isNaN() || getRaisedSalary() == 0.0 || getSalary() > getRaisedSalary()
		setRaisedSalary(0.0);
		setSalary(0.0);
		setHours(0.0);
		setOvertimeHours(0.0);
		setBonuses(0.0);
	}

	//added from Kim's
	public GrossPay(double salary, double hours, double overtimeHours, double bonuses, double raisesalary /*double equity,*/)
	{
		setSalary(salary);
		setHours(hours);
		setOvertimeHours(overtimeHours);
		setBonuses(bonuses);
		setRaisedSalary(raisesalary);
	}
	
	public GrossPay(Double salary, Double hours, Double overtimeHours, Double bonuses, Double raisedSalary, String raiseReason, String raiseEffectiveDate) throws ParseException {
		setSalary(salary);
		setHours(hours);
		setOvertimeHours(overtimeHours);
		setBonuses(bonuses);
		setRaisedSalary(raisedSalary);
		setRaiseReason(raiseReason);
		setRaiseEffectiveDate(raiseEffectiveDate);
	}
	
	public GrossPay(Integer id, Double salary, Double hours, Double overtimeHours, Double bonuses, Double raisedSalary, String raiseReason, String raiseEffectiveDate) throws ParseException {
		setUserId(id);
		setSalary(salary);
		setHours(hours);
		setOvertimeHours(overtimeHours);
		setBonuses(bonuses);
		setRaisedSalary(raisedSalary);
		setRaiseReason(raiseReason);
		setRaiseEffectiveDate(raiseEffectiveDate);
	}
	
	//getters and setters
	protected Integer getUserId() {
		return userId;
	}
	
	protected void setUserId(Integer id) {
		this.userId = id;
	}
	
	protected Double getSalary() {
		return salary;
	}
	
	protected void setSalary(Double salary) {
		this.salary = salary;
	}
	
	protected Double getHours() {
		return hours;
	}
	
	protected void setHours(Double hours) {
		this.hours = hours;
	}
	
	protected Double getOvertimeHours() {
		return overtimeHours;
	}

	protected void setOvertimeHours(Double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	protected Double getBonuses() {
		return bonuses;
	}

	protected void setBonuses(Double bonuses) {
		this.bonuses = bonuses;
	}

	protected Double getRaisedSalary() {
		return raisedSalary;
	}

	protected void setRaisedSalary(Double raisedSalary) {
		this.raisedSalary = raisedSalary;
	}
	
	protected String getRaiseEffectiveDate() {
		return raiseEffectiveDate;
	}
	
	protected void setRaiseEffectiveDate(String raiseDate) {
		this.raiseEffectiveDate = raiseDate;
	}

	protected String getRaiseReason() {
		return raiseReason;
	}

	protected void setRaiseReason(String raiseReason) {
		this.raiseReason = raiseReason;
	}
	
	protected void setFullTime(Integer full) {
		this.fulltime = full;
	}

	protected Integer getFullTime() {
		return fulltime;
	}
	
	protected Double getGrossPay() {
		return grossPay;
	}
	
	protected void setGrossPay(Double i) {
		this.grossPay = i;
	}
	
	protected void setWage(int wage)
	{
		this.wage = wage;
	}
	
	protected int getWage()
	{
		return wage;
	}

	protected void setLastPayDay(Date day) {
		this.lastPayDay = day;
	}

	protected Date getLastPayDay() {
		return lastPayDay;
	}
	
	//add, modify and delete functions
	public void addGrossPay() {
		if(checkPersonInfo()) {
			setRaiseEffectiveDate(raiseEffectiveDate);
			setSalary(salary);
			setOvertimeHours(overtimeHours);
			setBonuses(bonuses);
			setRaisedSalary(raisedSalary);
			setRaiseReason(raiseReason);
			setGrossPay(grossPay);
		}
	}
	
	public void modifyGrossPay() {
		if(checkPersonInfo()) {
			setRaiseEffectiveDate(raiseEffectiveDate);
			setSalary(salary);
			setOvertimeHours(overtimeHours);
			setBonuses(bonuses);
			setRaisedSalary(raisedSalary);
			setRaiseReason(raiseReason);
			setGrossPay(grossPay);
		}
	}
	
	public void deleteGrossPay() {
		if(checkPersonInfo()) {
			setRaiseEffectiveDate(null);
			setSalary(0.0);
			setOvertimeHours(0.0);
			setBonuses(0.0);
			setRaisedSalary(0.0);
			setRaiseReason(null);
			setGrossPay(0.0);
		}
	}
	
	//logic and calculation functions (will possibly add more depending on time and future features
	public Double calcTotalWages() {
		totalWages = getSalary() * getHours();
		return totalWages;
	}
	
	public Double calcOvertimeWages() {
		overtimeWages = getOvertimeHours() * (getSalary() * 1.5);
		return overtimeWages;
	}
	
	public Double calcOtherWages() {
		otherWages = getBonuses(); //can add other forms of income here like equity and such
		return otherWages;
	}
	
	public Double calcRaiseTotal() {
		raiseTotal = getRaisedSalary() * getHours();
		return raiseTotal;
	}
	
	public Double calcRaiseOvertimeWages() {
		raiseOvertimeWages = getOvertimeHours() * (getRaisedSalary() * 1.5);
		return raiseOvertimeWages;
	}
	
	//from Kim's version, will use for when employee view check
	
	public void isFullTime() {
		
		if(getHours() < 30.0)
			setFullTime(0);
		else
			setFullTime(1);
		
	}
	
	public void calcGrossPay() {
		//this is where I would add the condition so that if the raise date is today or a past date,
		// the raised salary is used instead, there'll be another time
		if(!getRaisedSalary().isNaN() || getRaisedSalary() == 0.0 || getSalary() > getRaisedSalary())
			grossPay = (calcTotalWages() * 52.0) + calcOvertimeWages() + calcOtherWages();
		else																
			grossPay = calcRaiseTotal() + calcRaiseOvertimeWages() + calcOtherWages();
		
		setGrossPay(grossPay);
	}
	
	public boolean checkPersonInfo() {
		if(person.getFirstName().isEmpty()||person.getLastName().isEmpty()||person.getAddress().isEmpty()||person.getSocialSecurity().isEmpty()) {
			return false;
		}		
		return true;
	}
	
}
