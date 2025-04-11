package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class HospitalManagementSystem implements hospitalFuntions {

	
	
		private static final String url = "jdbc:mysql://localhost:3306/hospital";
		private static final String username = "root";
		private static final String password = "Surya@4090#";
		
		public static void main(String[] args) {
			
			
			try {
				
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			
			Scanner scanner = new Scanner(System.in);
			try {
				
				Connection connection = DriverManager.getConnection(url,username,password);
				
				Patient patient = new Patient(connection, scanner);
				Doctor doctor = new Doctor(connection, scanner);
				
				while (true) {
					
					System.out.println("HOSPTAL MANAGEMENT SYSTEM ");
					System.out.println("1. Add Patient");
					System.out.println("2. View Patient");
					System.out.println("3. View Doctors");
					System.out.println("4. Book Appointment");
					System.out.println("5. Exit ");
					
					System.out.println("Enter your choice");
					
					int choice  = scanner.nextInt();
					
					switch (choice) {
						
					case 1:
						// add patient
						patient.addPatient();
						break;
					case 2:
						patient.viewPatients();
						System.out.println();
						break;
					case 3:
						// View Doctors
						doctor.viewDoctors();
						System.out.println();
						break;
					case 4:
						// Book Appointments
						bookAppointment(patient, doctor, connection, scanner);
						System.out.println();
						break;
						
					case 5:
						System.out.println("Thank you for using Hospital Management System !!!!");
						return;
					
					
					
						default:
								throw new IllegalArgumentException("Unexpected value: " + choice);
					}
					
					
					
				}
				
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
		public static void bookAppointment (Patient patient, Doctor doctor, Connection connection, Scanner scanner)
		{
			System.out.println("Enter patient Id : ");
			
			int patientId = scanner.nextInt();
			
			System.out.println("Enter Doctor ID: ");
			
			int doctorId = scanner.nextInt();
			
			System.out.println("Enter appointment date (YYYY-MM-DD): ");
			
			String appointmentDate = scanner.next();
			
			if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
			{
				if(checkDoctorAvailability(doctorId,appointmentDate,connection)) {
					
					String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?,?,?)";
					
					try {
						
						
						PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
						preparedStatement.setInt(1, doctorId);
						preparedStatement.setInt(2, doctorId);
						preparedStatement.setString(3, appointmentDate);
						
						int rowsAffected = preparedStatement.executeUpdate();
						
						if (rowsAffected>0) {
							
							System.out.println("Appoinment Booked !!");
							
						}
						else {
							
							System.out.println("Failed to book appointment !");
						}
					}
					catch (SQLException e) {
					
						e.printStackTrace();
					}
				}
				else
				{
					System.out.println("Doctor not available on this date !!");
				}
			}
			else 
			{
				System.out.println("Either doctor or patient doesn't exit !!");
			}
			
		}
		
		public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection)
		{
			String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";


			try{
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, doctorId);
				preparedStatement.setString(2, appointmentDate);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					
					int count  = resultSet.getInt(1);
					
					if (count ==0) {
						return true;
					}
					else {
						return false;
					}
					}
				}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
		}
		
}
