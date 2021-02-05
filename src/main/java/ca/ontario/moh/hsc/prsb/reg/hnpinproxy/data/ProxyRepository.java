package ca.ontario.moh.hsc.prsb.reg.hnpinproxy.data;

import javax.enterprise.context.ApplicationScoped;
//import io.quarkus.mongodb.panache.PanacheMongoRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import ca.ontario.moh.hsc.prsb.reg.hnpinproxy.entity.Proxy;

@ApplicationScoped
public class ProxyRepository {   //implements PanacheMongoRepository<Proxy> {

    private static Long[] hns = {1078870621L, 1080169723L, 1080697103L, 1081089326L, 1083284180L, 1088831472L, 1091052348L, 1091527869L};
    private static Map<Long, Proxy> records; //key is hn
    private static Map<String, Proxy> pins; //key is PIN

    static{
        //init
        records = new HashMap<Long, Proxy>();
        pins = new HashMap<String, Proxy>();

        for(Long hn : hns){
            String pin = generateAuditId();
            Proxy p =  new Proxy(hn, pin);
            records.put(hn, p);
            pins.put(pin, p);
        }

    }

    public Proxy findByHealthNumber(long hn){
        Proxy p = records.get(hn);
        if(p == null){
            //add
            p = add(hn);
        }
        return p;
        //return find("healthNumber", hn).firstResult();
    }

    public Proxy findByPIN(String pin){
        return pins.get(pin);
        //return find("pin", pin).firstResult();
    }

    private Proxy add(long hn){
        Proxy p = new Proxy(hn, generateAuditId());
        records.put(hn, p);
        pins.put(p.pin, p);
        return p;
        //return persist(new Proxy(hn, generateAuditId()));
    }

    private static String generateAuditId(){
        return UUID.randomUUID().toString();
    }
}