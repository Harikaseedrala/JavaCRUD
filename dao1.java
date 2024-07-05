import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Scanner;

class Dao {
    Connection con; // instance variable becomes reference variable

    Dao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // static method
        System.out.println("Loaded drive");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gecb2", "root", "harika@123");
    }

    void insert(int eno, String ename) throws SQLException {
        Statement stmt = con.createStatement();
        int r = stmt.executeUpdate("insert into emp values(" + eno + ", '" + ename + "')");
        System.out.printf("Inserted...%d\n", r);
    }

    void updateName(int eno, String ename) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("update emp set ename=? where emp=?");
        pstmt.setString(1, ename);
        pstmt.setInt(2, eno);
        int r = pstmt.executeUpdate();
        System.out.printf("Updated...%d\n", r);
    }

    void deletemp(int eno) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("delete from emp where emp=?");
        pstmt.setInt(1, eno);
        int r = pstmt.executeUpdate();
        System.out.printf("Deleted...%d\n", r);
    }  

    void view() throws SQLException {
        Statement stmt = con.createStatement();
        boolean b = stmt.execute("select * from emp");
        if (b) {
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                System.out.printf("%-5d %-20s %n", rs.getInt(1), rs.getString(2));
            }
        }
        System.out.println("done:" + b);
    }
}

public class dao1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Dao dao = new Dao();
            while (true) {
                System.out.println("\nSelect an operation:");
                System.out.println("1. Insert");
                System.out.println("2. Update");
                System.out.println("3. Delete");
                System.out.println("4. View");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter employee number: ");
                        int enoInsert = scanner.nextInt();
                        System.out.print("Enter employee name: ");
                        String enameInsert = scanner.next();
                        dao.insert(enoInsert, enameInsert);
                        break;
                    case 2:
                        System.out.print("Enter employee number: ");
                        int enoUpdate = scanner.nextInt();
                        System.out.print("Enter new employee name: ");
                        String enameUpdate = scanner.next();
                        dao.updateName(enoUpdate, enameUpdate);
                        break;
                    case 3:
                        System.out.print("Enter employee number to delete: ");
                        int enoDelete = scanner.nextInt();
                        dao.deletemp(enoDelete);
                        break;
                    case 4:
                        dao.view();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
