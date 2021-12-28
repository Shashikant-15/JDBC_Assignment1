

        import org.slf4j.LoggerFactory;
        import java.sql.*;
        import java.util.logging.Logger;

        // Created a Class as name of JDBExample
        public class JDBExample {

            // create a final static logger
            private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JDBExample.class);
            // main method  within JDBExample Class called here
            public static void main(String[] args) {
        try
        {
            // here providing DataBase url with USER  name  & Password
            final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            final String DB_URL = "jdbc:mysql://localhost:3306/database1";
            final String USER = "root";
            final String PASS = "Shashi@15*";

            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // output block started from here
            LOGGER.info("1: The total amount to be paid at the time of checkout for a particular cart. As shown in above table. e.g. Query should return a single integer as total amount.");
            Statement s = conn.createStatement();    // providing connection to db as query
            ResultSet res = s.executeQuery("select sum(amount) as total from cart");
            while(res.next())
                LOGGER.info("Total Amount = "+res.getString(1));

            LOGGER.info("\n\n");
            LOGGER.info("2: The product name which has been sold the most.");
            Statement stmt2=conn.createStatement();
            ResultSet result =stmt2.executeQuery("select pname from product where pid=(select pid from cart where qty=(select Max(qty) from cart));");
            while(result.next())
                LOGGER.info("Name with maximum quantity = "+result.getString(1));


            LOGGER.info("\n\n");
            LOGGER.info("3: All the products which are not yet sold.");
            Statement stmt3=conn.createStatement();
            ResultSet r3=stmt3.executeQuery("select pname from product where pid NOT IN(Select pid from cart)");
            while(r3.next())
                LOGGER.info(r3.getString(1));

            LOGGER.info("\n\n");
            conn.close();
        }
        // catch blog start from here for through Exception
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}