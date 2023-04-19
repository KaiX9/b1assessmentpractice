package sg.edu.nus.iss.b1assessmentpractice.repository;

public class DBQueries {
    
    public static final String GET_DETAILS = "select * from accounts";

    public static final String GET_BALANCE_BY_ACCOUNT_ID = "select balance from accounts where account_id = ?";

    public static final String UPDATE_ACCOUNT_BALANCE = "update accounts set balance = ? where account_id = ?";

    public static final String GET_NAME_BY_ACCOUNT_ID = "select name from accounts where account_id = ?";

}
