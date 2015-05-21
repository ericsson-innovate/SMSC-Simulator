/* 
 * ConnectH2DB.java 
 */
package com.e3.smsqueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.e3.smsqueue.entity.SMS;

/**
 * @author www.javaworkspace.com
 * 
 */
public class H2DAO {
	static public String getCurrentStatus(String msisdn) {
		Connection connection = null;
		ResultSet resultSet = null;
		String sql;
		PreparedStatement pstmt;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(
					"jdbc:h2:tcp://localhost/h2db/smssender", "smssender",
					"smssender");
			sql = "SELECT STATUS  FROM  DEVICE_STATUS where MSISDN=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, msisdn);
			resultSet = pstmt.executeQuery();
			if (resultSet.first()) {
				return resultSet.getString("STATUS");
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				resultSet.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static public void updateStatus(String msisdn, String status, String requestId) {
		Connection connection = null;
		ResultSet resultSet = null;
		String sql;
		PreparedStatement pstmt;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(
					"jdbc:h2:tcp://localhost/h2db/smssender", "smssender",
					"smssender");
			sql = "SELECT STATUS  FROM  DEVICE_STATUS where MSISDN=? and REQUESTID=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, msisdn);
			pstmt.setString(2, requestId);
			resultSet = pstmt.executeQuery();
			// statement
			// .executeQuery("SELECT STATUS  FROM  DEVICE_STATUS where MSISDN="+msisdn);
			if (!resultSet.first()) {
				sql = "insert into DEVICE_STATUS(MSISDN,STATUS,REQUESTID) values(?,?,?)";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, msisdn);
				pstmt.setString(2, status);
				pstmt.setString(3, requestId);
				pstmt.execute();
			} else {
				sql = "UPDATE device_status SET status=? WHERE msisdn=? and REQUESTID=?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, status);
				pstmt.setString(2, msisdn);
				pstmt.setString(3, requestId);
				pstmt.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	static public List<SMS> getSMSStatus(String requestId){
		Connection connection = null;
		ResultSet resultSet = null;
		List<SMS> sms = new ArrayList<SMS>();
		String sql;
		PreparedStatement pstmt;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(
					"jdbc:h2:tcp://localhost/h2db/smssender", "smssender",
					"smssender");
			sql = "SELECT MSISDN, STATUS, REQUESTID FROM  DEVICE_STATUS where REQUESTID=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, requestId);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()){
				SMS tempSMS= new SMS(resultSet.getString("MSISDN"), "", "", resultSet.getString("REQUESTID"));
				tempSMS.setDeliveryStatus(resultSet.getString("STATUS"));
				sms.add(tempSMS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sms;
	}

}