package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class Doctor implements hospitalFuntions{
	
	private Connection connection ;
	
	public  Doctor(Connection connection, Scanner scanner) {
		this.connection  = connection;
	}
	
	
	public void viewDoctors() {
		String quary = "select * from Doctors";
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(quary);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("Doctors");
			System.out.println("+-------------+--------------------+---------------------+");
			System.out.println("| Doctor Id   |   Name             |   Specialization    |");
			System.out.println("+-------------+--------------------+---------------------+");
			
			while(resultset.next()) {
				
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				String specialization = resultset.getString("specialization");
				System.out.printf("| %-11s | %-19s | %-18s |\n",id,name,specialization);
				System.out.println("+-------------+--------------------+---------------------+");
			}
			
		} catch (SQLException e) {
			
			e.getStackTrace();
		}
	}
	
	public boolean getDoctorById(int id) {
		String quary = "select * from Doctors WHERE id = ?";
		
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
