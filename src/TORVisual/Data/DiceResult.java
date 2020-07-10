package TORVisual.Data;

import java.util.Date;

public class DiceResult {

    public int Id;
    public int ClientId;
    public String ClientName;
    public int Result;
    public Date Time;

    public DiceResult() {
    }

    public DiceResult(int id, int clientId, String clientName, int result, Date time) {
        this.Id = id;
        this.ClientId = clientId;
        this.ClientName = clientName;
        this.Result = result;
        this.Time = time;
    }
}
