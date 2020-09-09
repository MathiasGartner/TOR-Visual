package TORVisual.Database;

import java.util.Date;

public class DiceResult {

    public int Id;
    public int ClientId;
    public String Material;
    public int Result;
    public Date Time;
    public Boolean UserGenerated;

    public DiceResult() {
    }

    public DiceResult(int id, int clientId, String material, int result, Date time, Boolean userGenerated) {
        this.Id = id;
        this.ClientId = clientId;
        this.Material = material;
        this.Result = result;
        this.Time = time;
        this.UserGenerated = userGenerated;
    }
}
