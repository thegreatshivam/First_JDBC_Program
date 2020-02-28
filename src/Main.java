import java.sql.*;
import java.util.Scanner;

public class Main {
    // Class Level Variables
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static Scanner userInput = new Scanner(System.in);
    // Main Function
    public static void main(String[] args) throws Exception {
        connectDb();
        boolean terminate = false;
        while(!terminate){
            System.out.println("1. Insert Student");
            System.out.println("2. Edit Student Details");
            System.out.println("3. Remove Student");
            System.out.println("4. List Students");
            System.out.println("5. Search Student");
            System.out.println("0. Exit");
            System.out.println("########################################");
            int choice = 0;
            try{
                choice = userInput.nextInt();
            }catch(Exception e){
                System.out.println("Error Occurred.");
            }
            switch (choice){
                case 1:
                    insert_student();
                    break;
                case 2:
                    edit_student();
                    break;
                case 3:
                    remove_student();
                    break;
                case 4:
                    print_student_list();
                    break;
                case 5:
                    print_student_detail();
                    break;
                case 0:
                    terminate = true;
                    break;
                default:
                    System.out.println("Wrong Input");

            }
        }
        disconnectDb();
    }
    // Database Connector Function
    private static void connectDb() {
        try {
            String url = "jdbc:mysql://thegreatshivam.co/thegreat_first_jdbc_app";
            String user = "thegreat_demo";
            String password = "demopassword";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }
    // Database Disconnect Function
    private static void disconnectDb() throws SQLException {
        connection.close();
    }
    // Student Insert Function
    private static void insert_student() throws SQLException {
        int id;
        String name;
        long mobNo;
        System.out.print("Student's ID: " );
        id = userInput.nextInt();
        userInput.nextLine();
        System.out.print("Student's Name: ");
        name = userInput.nextLine();
        System.out.print("Student's Mob No: ");
        mobNo = userInput.nextLong();
        String insertQuery = "insert into student (id, name, mob_num) values (?, ?, ?);";
        preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setLong(3, mobNo);
        int status = preparedStatement.executeUpdate();
        if(status == 1){
            System.out.println("Student inserted successfully");
        }else{
            System.out.println("Student didn't inserted");
        }
    }
    // Student Edit Function
    private static void edit_student() throws SQLException {
        int id;
        String name;
        long mobNo;
        System.out.print("Student's ID: " );
        id = userInput.nextInt();
        userInput.nextLine();
        System.out.print("Student's Name: ");
        name = userInput.nextLine();
        System.out.print("Student's Mob No: ");
        mobNo = userInput.nextLong();
        String query = "update student set name = ?, mob_num = ? where id = ?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setLong(2, mobNo);
        preparedStatement.setInt(3, id);
        int status = preparedStatement.executeUpdate();
        if(status == 1){
            System.out.println("Student details updated successfully");
        }else{
            System.out.println("Student details didn't updated");
        }
    }
    // Student Remove Function
    private static void remove_student() throws SQLException {
        System.out.print("Enter ID: ");
        int id = userInput.nextInt();
        String query = "delete from student where id = " + id + ";";
        int status = statement.executeUpdate(query);
        if(status == 1){
            System.out.println("Student removed successfully");
        } else {
            System.out.println("Student didn't removed");
        }

    }
    // Student List Print Function
    private static void print_student_list() throws SQLException {
        String query = "select * from  student;";
        resultSet = statement.executeQuery(query);
        System.out.printf("%-13s%-16s%s\n", "ID", "Name", "Mobile No");
        while(resultSet.next()){
            System.out.printf("%-13d%-16s%d\n", resultSet.getInt("id"), resultSet.getString("name"), resultSet.getLong("mob_num"));
        }
    }
    // Student Detail Print Function
    private static void print_student_detail() throws SQLException {
        System.out.print("Enter ID: ");
        int id = userInput.nextInt();
        String query = "select * from student where id = " + id + ";";
        resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            System.out.printf("%-13s%-16s%s\n", "ID", "Name", "Mobile No");
            System.out.printf("%-13d%-16s%d\n", resultSet.getInt("id"), resultSet.getString("name"), resultSet.getLong("mob_num"));
        }else{
            System.out.println("Student didn't found");
        }
    }
}