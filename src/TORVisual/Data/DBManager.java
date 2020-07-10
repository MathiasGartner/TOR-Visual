package TORVisual.Data;

import TORVisual.Settings.Private;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private String url = Private.DB_URL;
    private String username = Private.DB_USERNAME;
    private String password = Private.DB_PASSWORD;

    public DBManager() {

    }

    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    private ArrayList<DiceResult> getDiceResults(ResultSet rs) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        while (rs.next()) {
            var dr = new DiceResult(rs.getInt("Id"), rs.getInt("ClientId"), rs.getString("Name"), rs.getInt("Result"), rs.getTime("Time"));
            diceResults.add(dr);
        }
        return diceResults;
    }

    public ArrayList<DiceResult> getDiceResultAboveId(int resultId) throws SQLException {
        var diceResults = new ArrayList<DiceResult>();
        String sql = "SELECT d.Id, d.ClientId, c.Name, d.Result, d.Time FROM tor.diceresult d LEFT JOIN tor.Client c ON d.ClientId = c.Id WHERE d.Id > ?";
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
        String sql = "SELECT d.Id, d.ClientId, c.Name, d.Result, d.Time FROM tor.diceresult d LEFT JOIN tor.Client c ON d.ClientId = c.Id WHERE d.Id > ? AND d.ClientId = ?";
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
        String sql = "SELECT d.Id, d.ClientId, c.Name, d.Result, d.Time FROM tor.diceresult d LEFT JOIN tor.Client c ON d.ClientId = c.Id WHERE d.ClientId = ?";
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
}
