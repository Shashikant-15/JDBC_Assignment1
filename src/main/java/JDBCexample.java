


        import java.sql.*;
        public class JDBCexample {
            public static void main(String[] args) {
        try
        {
            final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            final String DB_URL = "jdbc:mysql://localhost:3306/database1";
            final String USER = "root";
            final String PASS = "Shashi@15*";

            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);



            System.out.println("1: The total amount to be paid at the time of checkout for a particular cart. As shown in above table. e.g. Query should return a single integer as total amount.");
            Statement s = conn.createStatement();
            ResultSet res = s.executeQuery("select sum(amount) as total from cart");
            while(res.next())
                System.out.println("Total Amount = "+res.getString(1));

            System.out.println("\n\n");
            System.out.println("2: The product name which has been sold the most.");
            Statement stmt2=conn.createStatement();
            ResultSet result =stmt2.executeQuery("select pname from product where pid=(select pid from cart where qty=(select Max(qty) from cart));");
            while(result.next())
                System.out.println("Name with maximum quantity = "+result.getString(1));


            System.out.println("\n\n");
            System.out.println("3: All the products which are not yet sold.");
            Statement stmt3=conn.createStatement();
            ResultSet r3=stmt3.executeQuery("select pname from product where pid NOT IN(Select pid from cart)");
            while(r3.next())
                System.out.println(r3.getString(1));

            System.out.println("\n\n");
            conn.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}