package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//import jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput;

public abstract class Patient implements hospitalFuntions{
			
	
	private Connection connection ;
	private Scanner scanner;
	
	
	public Patient(Connection connection, Scanner scanner) {
		this.connection  = connection;
		this.scanner = scanner;
	}
	
	public void addPatient() {
		
		System.out.println("Enter Patient Name");
		String name = scanner.next();
		System.out.println("Enter Patient age: ");
		int age = scanner.nextInt();
		System.out.println("Enter Patient Gender: ");
		String gender = scanner.next();
		
		
		try {
			
			String quary = "INSERT INTO patients( name, age, gender) VALUE (?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(quary);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2,age);
			preparedStatement.setString(3, gender);
			
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows > 0)
			{
				System.out.println("Patient Added Successfully");
			}
			else {
				
				System.out.println("Failed to add");
			}
			
			
			
			
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	public void viewPatients() {
		String quary = "select * from patients";
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(quary);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("Patients");
			System.out.println("+------------+--------------+--------+----------------+");
			System.out.println("| Patient Id | Name         | Age    | Gender         |");
			System.out.println("+------------+--------------+--------+----------------+");
			
			while(resultset.next()) {
				
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				int age = resultset.getInt("age");
				String gender = resultset.getString("gender");
				System.out.printf("|%-12s|%-14s|%-8s|%-16s|\n",id,name,age,gender);
				System.out.println("+------------+--------------+--------+----------------+");
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.getStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String quary = "select * from patients WHERE id = ?";
		
		try {
				PreparedStatement preparedStatement = connection.prepareStatement(quary);
				preparedStatement.setInt(1,id);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next())
				{	return true;
				}
				else {
					return false;
				}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
