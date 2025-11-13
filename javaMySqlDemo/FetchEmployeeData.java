import java.sql.*;

public class FetchEmployeeData {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/company_db"; 
        String username = "root";  
        String password = "";       

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                System.out.println("✅ Connection successful!");

                // 1. Create Table
                String createTable = "CREATE TABLE IF NOT EXISTS Employee ("
                                   + "id INT PRIMARY KEY AUTO_INCREMENT, "
                                   + "name VARCHAR(50), "
                                   + "sal DOUBLE)";
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTable);
                    System.out.println("✔ Table ready (if not exists).");
                }

                // 2. Insert using PreparedStatement
                String insertQuery = "INSERT INTO Employee (name, sal) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertQuery)) {

                    // Insert 1
                    ps.setString(1, "Nikita");
                    ps.setDouble(2, 50000);
                    ps.executeUpdate();

                    // Insert 2
                    ps.setString(1, "Preeti");
                    ps.setDouble(2, 55000);
                    ps.executeUpdate();

                    System.out.println("✔ Data inserted using PreparedStatement.");
                }

                // 3. Fetch & Display All Records
                String fetchQuery = "SELECT * FROM Employee";

                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(fetchQuery)) {

                    System.out.println("\nID | Name    | Salary");
                    System.out.println("------------------------");

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        double sal = rs.getDouble("sal");

                        System.out.println(id + " | " + name + " | " + sal);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//javac -cp ".;lib\mysql-connector-j-9.5.0.jar" FetchEmployeeData.java
//java -cp ".;lib\mysql-connector-j-9.5.0.jar" FetchEmployeeData