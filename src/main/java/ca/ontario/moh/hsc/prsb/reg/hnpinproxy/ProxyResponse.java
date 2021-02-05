package ca.ontario.moh.hsc.prsb.reg.hnpinproxy;

public class ProxyResponse {
    
    public int responseCode;
    public String responseCodeDescription;
    public String auditId;
    public String externalAuditId;  //turn-around attrib
    public String pin;  //HN surrogate
    public long healthNumber;

    public ProxyResponse(){

    }    
}
