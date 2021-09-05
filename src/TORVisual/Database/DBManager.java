package TORVisual.Database;

import TORVisual.Settings.SettingsPrivate;
import TORVisual.Settings.SettingsVisual;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DBManager {

    private String url = SettingsPrivate.DB_URL;
    private String username = SettingsPrivate.DB_USERNAME;
    private String password = SettingsPrivate.DB_PASSWORD;

    public DBManager() {

    }

    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    private ArrayList<DiceResult> getDiceResults(ResultSet rs) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        while (rs.next()) {
            var dr = new DiceResult(rs.getInt("Id"), rs.getInt("ClientId"), rs.getString("Material"), rs.getInt("Result"), rs.getTime("Time"), rs.getBoolean("UserGenerated"));
            diceResults.add(dr);
        }
        return diceResults;
    }

    public ArrayList<DiceResult> getDiceResultByEventSource(String eventSource) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time, d.UserGenerated FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.Source = ?";
        try (
                Connection conn = this.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, eventSource);
            try (ResultSet rs = ps.executeQuery()) {
                diceResults = getDiceResults(rs);
            }
        }
        return diceResults;
    }

    public ArrayList<DiceResult> getDiceResultAboveId(int resultId) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time, d.UserGenerated FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.Id > ? AND d.Source = '" + SettingsVisual.DiceResultEventSource + "' LIMIT 30000";
        try (
            Connection conn = this.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, resultId);
            try (ResultSet rs = ps.executeQuery()) {
                diceResults = getDiceResults(rs);
            }
        }
        return diceResults;
    }

    public ArrayList<DiceResult> getDiceResultAboveIdByClientId(int resultId, int clientId) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time, d.UserGenerated FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.Id > ? AND d.ClientId = ? AND d.Source = '" + SettingsVisual.DiceResultEventSource + "'";
        try (
                Connection conn = this.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, resultId);
            ps.setInt(2, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                diceResults = getDiceResults(rs);
            }
        }
        return diceResults;
    }

    public ArrayList<DiceResult> getDiceResultByClientId(int clientId) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time, d.UserGenerated FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.ClientId = ? AND d.Source = '" + SettingsVisual.DiceResultEventSource + "'";
        try (
                Connection conn = this.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                diceResults = getDiceResults(rs);
            }
        }
        return diceResults;
    }

    public void CreateDummyResults(int count) throws SQLException {
        String sql = "INSERT INTO diceresult (ClientId, Result, X, Y) VALUES (?, ?, 0, 0)";
        try (
                Connection conn = this.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            for (int i = 0; i < count; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 6+1);
                ps.setInt(2, randomNum);
                randomNum = ThreadLocalRandom.current().nextInt(1, 25);
                ps.setInt(1, randomNum);
                ps.executeUpdate();
            }
        }
    }
}
