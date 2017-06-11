package it.polito.tdp.formula1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.time.*;
import java.util.*;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.formula1.model.*;
import it.polito.tdp.formula1.model.Driver;


public class F1DAO {
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getCircuitiPerAnno(Year y, Map<Integer, Circuit> mappa){
		String sql="SELECT * FROM circuits WHERE circuits.circuitId IN "+
					"(select races.circuitId from races where year=?)";
		List<Circuit> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, y.getValue());
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				if(!mappa.containsKey(rs.getInt("circuitId"))){
					Circuit c=new Circuit(rs.getInt("circuitId"),rs.getString("circuitRef"),rs.getString("name"),
							rs.getString("location"),rs.getString("country"),new LatLng(rs.getDouble("lat"),rs.getDouble("lng")));
					list.add(c);
					mappa.put(c.getCircuitId(), c);
				}else{
					list.add(mappa.get(rs.getInt("circuitId")));
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}	
	}

	public Race getGara(Season s, Circuit c, Map<Integer, Circuit> mappa) {
		String sql="SELECT * FROM races,circuits "+
				"WHERE year=? and circuits.circuitId=? and races.circuitId=circuits.circuitId";
		Race r=null;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getYear().getValue());
			st.setInt(2, c.getCircuitId());
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				if(!mappa.containsKey(rs.getInt("circuitId"))){
					System.out.println("Errore. Circuito non presente.\n");
				}else{
					int id=rs.getInt("raceId");
					Year y=Year.of(rs.getInt("year"));
					int round=rs.getInt("round");
					Circuit circuito=mappa.get(rs.getInt("circuitId"));
					String name=rs.getString("name");
					LocalDate ld=rs.getDate("date").toLocalDate();
					Time lt=rs.getTime("time");
					String url=rs.getString("url");
					if(lt!=null){
						r=new Race(id,y,round,circuito,name,ld,lt.toLocalTime(),url);
					}else{
						r=new Race(id,y,round,circuito,name,ld,url);
					}
				}
			}
			conn.close();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}	
	}

	public List<Driver> getPiloti(Race r) {
		String sql="SELECT d.driverId,driverRef,number,code,forename,surname,dob,nationality,d.url "+
				"from races,driverstandings as app, drivers as d "+
				"where races.raceId=? and app.raceId=races.raceId and d.driverId=app.driverId "+
				"order by d.driverId";
		List<Driver> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, r.getRaceId());
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				list.add(new Driver(rs.getInt("driverId"),rs.getString("driverRef"),
						rs.getInt("number"),rs.getString("code"),rs.getString("forename"),
						rs.getString("surname"),rs.getDate("dob").toLocalDate(),rs.getString("nationality"),rs.getString("url")));
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<DriverAndTempi> getTempiPilota(Driver d, Race r) {
		String sql="SELECT r.year,l.raceId,l.driverId,l.lap,l.position,l.time,l.milliseconds,d.surname "+
					"FROM drivers as d,laptimes as l,races as r "+
					"where d.driverId=l.driverId and l.raceId=r.raceId  and d.driverId=? and r.circuitId=? "+
					"order by l.lap,year";
		
		List<DriverAndTempi> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, d.getDriverId());
			st.setInt(2, r.getCircuit().getCircuitId());
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				list.add(new DriverAndTempi(Year.of(rs.getInt("year")),new LapTime(rs.getInt("raceId"),rs.getInt("driverId"),
						rs.getInt("lap"),rs.getInt("position"),rs.getString("time"),rs.getInt("milliseconds"))));
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}	
}
