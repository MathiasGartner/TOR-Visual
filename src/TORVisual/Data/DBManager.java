package TORVisual.Data;

import TORVisual.Settings.SettingsPrivate;

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
            var dr = new DiceResult(rs.getInt("Id"), rs.getInt("ClientId"), rs.getString("Material"), rs.getInt("Result"), rs.getTime("Time"));
            diceResults.add(dr);
        }
        return diceResults;
    }

    public ArrayList<DiceResult> getDiceResultAboveId(int resultId) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.Id > ? LIMIT 100";
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
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.Id > ? AND d.ClientId = ?";
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
        String sql = "SELECT d.Id, d.ClientId, c.Material, d.Result, d.Time FROM diceresult d LEFT JOIN client c ON d.ClientId = c.Id WHERE d.ClientId = ?";
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
