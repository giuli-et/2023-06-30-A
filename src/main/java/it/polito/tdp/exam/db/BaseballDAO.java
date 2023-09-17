package it.polito.tdp.exam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.exam.model.People;
import it.polito.tdp.exam.model.Team;

public class BaseballDAO {

	public List<People> readAllPlayers() {
		String sql = "SELECT * " + "FROM people";
		List<People> result = new ArrayList<People>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new People(rs.getString("playerID"), rs.getString("birthCountry"), rs.getString("birthCity"),
						rs.getString("deathCountry"), rs.getString("deathCity"), rs.getString("nameFirst"),
						rs.getString("nameLast"), rs.getInt("weight"), rs.getInt("height"), rs.getString("bats"),
						rs.getString("throws"), getBirthDate(rs), getDebutDate(rs), getFinalGameDate(rs),
						getDeathDate(rs)));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Team> readAllTeams() {
		String sql = "SELECT * " + "FROM  teams";
		List<Team> result = new ArrayList<Team>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Team(rs.getInt("iD"), rs.getInt("year"), rs.getString("teamCode"), rs.getString("divID"),
						rs.getInt("div_ID"), rs.getInt("teamRank"), rs.getInt("games"), rs.getInt("gamesHome"),
						rs.getInt("wins"), rs.getInt("losses"), rs.getString("divisionWinnner"),
						rs.getString("leagueWinner"), rs.getString("worldSeriesWinnner"), rs.getInt("runs"),
						rs.getInt("hits"), rs.getInt("homeruns"), rs.getInt("stolenBases"), rs.getInt("hitsAllowed"),
						rs.getInt("homerunsAllowed"), rs.getString("name"), rs.getString("park")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> getAllTeamsNames(){
		String sql = "SELECT DISTINCt t.name "
				+ "FROM teams t "
				+ "ORDER BY t.name ";
		List<String> result = new ArrayList<String>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("name"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Integer> getVertici(String squadra){
		String sql = "SELECT DISTINCT t.year "
				+ "FROM teams t "
				+ "WHERE t.name = ? ";
		List<Integer> result = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, squadra);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getInt("year"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<People> getPeopleFromTeamAndYear(int anno, String squadra){
		String sql = "SELECT DISTINCT p.* "
				+ "FROM appearances a, people p, teams t "
				+ "WHERE t.year = ? AND a.teamID = t.ID AND t.name = ? AND a.playerID = p.playerID ";
		List<People> result = new ArrayList<People>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, squadra);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new People(res.getString("playerID"), res.getString("birthCountry"), res.getString("birthCity"),
						res.getString("deathCountry"), res.getString("deathCity"), res.getString("nameFirst"),
						res.getString("nameLast"), res.getInt("weight"), res.getInt("height"), res.getString("bats"),
						res.getString("throws"), getBirthDate(res), getDebutDate(res), getFinalGameDate(res),
						getDeathDate(res)));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	
	
	

	// =================================================================
	// ==================== HELPER FUNCTIONS =========================
	// =================================================================

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getBirthDate(ResultSet rs) {
		try {
			if (rs.getDate("birth_date") != null) {
				return rs.getDate("birth_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDebutDate(ResultSet rs) {
		try {
			if (rs.getDate("debut_date") != null) {
				return rs.getDate("debut_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getFinalGameDate(ResultSet rs) {
		try {
			if (rs.getDate("finalgame_date") != null) {
				return rs.getDate("finalgame_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDeathDate(ResultSet rs) {
		try {
			if (rs.getDate("death_date") != null) {
				return rs.getDate("death_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
