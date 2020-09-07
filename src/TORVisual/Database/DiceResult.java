package TORVisual.Database;

import java.util.Date;

public class DiceResult {

    public int Id;
    public int ClientId;
    public String Material;
    public int Result;
    public Date Time;

    public DiceResult() {
    }

    public DiceResult(int id, int clientId, String material, int result, Date time) {
        this.Id = id;
        this.ClientId = clientId;
        this.Material = material;
        this.Result = result;
        this.Time = time;
    }
}
