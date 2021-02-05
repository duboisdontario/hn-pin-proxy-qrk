package ca.ontario.moh.hsc.prsb.reg.hnpinproxy.entity;

//import io.quarkus.mongodb.panache.MongoEntity; 
//import io.quarkus.mongodb.panache.PanacheMongoEntity;

//@MongoEntity
public class Proxy {  //extends PanacheMongoEntity {

    public long healthNumber;
    public String pin;

    public Proxy(){

    }

    public Proxy(long hn, String pin){
        this.healthNumber = hn;
        this.pin = pin;
    }


}