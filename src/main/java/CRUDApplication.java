

import java.sql.*;
import java.util.Scanner;

public class CRUDApplication {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String user = "root";
    private static final String password = "456784";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            // Infinite loop to take input from the user continuously
            while (true) {
                System.out.println("\nStudent CRUD Application");
                System.out.println("1. Create Student");
                System.out.println("2. Read Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Create student
                        System.out.print("Enter student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String insertQuery = "INSERT INTO student (id, name, age) VALUES (?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                            preparedStatement.setInt(1, id);
                            preparedStatement.setString(2, name);
                            preparedStatement.setInt(3, age);
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Student added successfully!");
                            }
                        }
                        break;

                    case 2:
                        // Read students
                        String selectQuery = "SELECT * FROM student";
                        ResultSet resultSet = statement.executeQuery(selectQuery);
                        while (resultSet.next()) {
                            int resultId = resultSet.getInt("id");
                            String resultName = resultSet.getString("name");
                            int resultAge = resultSet.getInt("age");
                            System.out.println("ID: " + resultId + ", Name: " + resultName + ", Age: " + resultAge);
                        }
                        break;

                    case 3:
                        // Update student
                        System.out.print("Enter student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new student name: ");
                        String updateName = scanner.nextLine();
                        System.out.print("Enter new student age: ");
                        int updateAge = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String updateQuery = "UPDATE student SET name = ?, age = ? WHERE id = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                            preparedStatement.setString(1, updateName);
                            preparedStatement.setInt(2, updateAge);
                            preparedStatement.setInt(3, updateId);
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Student updated successfully!");
                            } else {
                                System.out.println("No student found with ID: " + updateId);
                            }
                        }
                        break;

                    case 4:
                        // Delete student
                        System.out.print("Enter student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String deleteQuery = "DELETE FROM student WHERE id = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                            preparedStatement.setInt(1, deleteId);
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Student deleted successfully!");
                            } else {
                                System.out.println("No student found with ID: " + deleteId);
                            }
                        }
                        break;

                    case 5:
                        // Exit the application
                        System.out.println("Exiting application...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to connect or execute query: " + e.getMessage());
        }
    }
}
