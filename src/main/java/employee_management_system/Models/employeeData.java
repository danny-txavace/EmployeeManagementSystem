package employee_management_system.Models;

public class employeeData {
    private String employeeID;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String position;
    private String image;
    private String date;
    private Double salary;

    public employeeData(String employeeID, String firstName, String lastName, String gender, String phone, String position, String image, String date) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.position = position;
        this.image = image;
        this.date = date;
    }

    public employeeData(String employeeID, String firstName, String lastName, String position, Double salary) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
    }

    public employeeData(){}

    public String getEmployeeID() {
        return this.employeeID;
    }
        public String getFirstName() {
            return this.firstName;
        }
            public String getLastName() {
                return this.lastName;
            }
                public String getGender() {
                    return this.gender;
                }
                    public String getPhone() {
                        return this.phone;
                    }
                        public String getPosition() {
                            return this.position;
                        }
                            public String getImage() {
                                return this.image;
                            }
                                public String getDate() {
                                    return this.date;
                                }
                                    public Double getSalary() {
                                        return this.salary;
                                    }
                                public void setDate(String date) {
                                    this.date = date;
                                }
                            public void setImage(String image) {
                                this.image = image;
                            }
                        public void setPosition(String position) {
                            this.position = position;
                        }
                    public void setPhone(String phone) {
                        this.phone = phone;
                    }
                public void setGender(String gender) {
                    this.gender = gender;
                }
            public void setLastName(String lastName) {
                this.lastName = lastName;
            }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    public void setEmployeeID(String empoloyeeID) {
        this.employeeID =empoloyeeID;
    }
}
