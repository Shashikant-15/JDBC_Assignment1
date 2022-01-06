        package com.knoldus.JDBC_Connection;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.logging.Logger;
        import org.slf4j.LoggerFactory;

        /**
         * Class JdbcConnection for Implementing JDBC Connection
         */
        public class JdbcConnection {

            /**
             * create a final static logger
             */
            private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JdbcConnection.class);

            static Connection connection ;
            PreparedStatement statement ;
            ResultSet result;

            /**
             * main method called from here
             * @param args arguments
             * @throws SQLException it's throw an Exception
             */
            public static void main(String[] args) throws SQLException {

                // here providing DataBase url with userName  & Password
                try {
                    final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
                    final String dbUrl = "jdbc:mysql://localhost:3306/database1";
                    final String user = "root";
                    final String password = "Shashi@15*";

                    Class.forName(jdbcDriver);
                    connection = DriverManager.getConnection(dbUrl,user,password);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                JdbcConnection jdbcConn = new JdbcConnection();  // jdbcConn is defined as an object
                jdbcConn. DisplayTableOfProduct();
                jdbcConn.TotalCalculatedAmount();
                jdbcConn.OverallTotal();
                jdbcConn.NotProductSold();
                jdbcConn.MaxProductSold();
            }

            /**
             * To Display Product Table
             */
            public void  DisplayTableOfProduct(){
                try {
                    String query=("select * from product");
                    statement = connection.prepareStatement(query);
                    ResultSet result = statement.executeQuery();
                    LOGGER.info(" ************* Product Table ***********");
                    LOGGER.info("Product ID\t\tPrice\t \t\t Product Name\n");
                    while (result.next()) {
                        LOGGER.info("\t" + result.getString(1) + "\t\t " + result.getString(3) + "\t\t\t\t\t\t" +
                                result.getString(2));
                    }
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            /**
             * TotalCalculatedAmount = Quantity * price
             */
            public void TotalCalculatedAmount() {
                try {
                    String query=("select product.pid, product.pname,cart.Qty,cart.Qty*product.price as Total from product,cart " +
                            "where product.pid= cart.pid ");
                    statement = connection.prepareStatement(query);
                    ResultSet result = statement.executeQuery();
                    System.out.println("================ Cart Items ===================");
                    System.out.print("Product ID\t\tQuantity\t\tTotalPrice\t\t Product Name\n");
                    while (result.next()) {
                        LOGGER.info("\t" + result.getString(1) + "\t\t\t" + result.getString(3) + "\t\t\t\t\t\t" +
                                result.getString(4) + "\t\t\t" + result.getString(2));
                    }
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
             /**
             * OverallTotal  at the time of checkout
              */
            public void  OverallTotal() throws SQLException {
                String query=("select (OverallTotal) from(select cart.Qty*product.price as OverallTotal from product,cart where product.pid=cart.pid) as total");
                statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    System.out.println("\t" + "   Grand total = " + rs.getString(1));
                }
                System.out.println();

            }

            // which product not sold
            public void NotProductSold(){
                try {
                    String query=("select * from product where pid not in " +
                            "(select pid from cart);");
                    statement = connection.prepareStatement(query);
                    result= statement.executeQuery();
                    System.out.println("=================Not Product Sold==================");
                    System.out.print("Product ID\t\tPrice\t\tProduct_Name\n");
                    while(result.next()){
                        System.out.println("\t"+result.getString(1)+"\t\t\t"+result.getString(3)+"\t\t" +
                                result.getString(2));
                    }
                    System.out.println();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            /**
             * // for MaxProductSold  product
             */
            public void MaxProductSold(){
                try{
                    String query=("select product.pid, product.pname, cart.Qty " +
                            "from product,cart where product.pid= cart.pid order by Qty desc limit 3;");
                    statement = connection.prepareStatement(query);
                    result=statement.executeQuery();
                    System.out.println("============= Maximum Product Sold ==============");
                    System.out.print("Product ID\t\tQuantity\t\tProduct Name\n");
                    while(result.next()){
                        System.out.println("\t"+result.getString(1)+"\t\t\t\t"+result.getString(3)+"\t\t\t" +
                                result.getString(2)+"\n");
                        break;
                    }

                }catch(SQLException e){
                    System.out.println(e);}
            }
        }