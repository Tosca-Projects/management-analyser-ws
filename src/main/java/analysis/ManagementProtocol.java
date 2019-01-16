package analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagementProtocol {
    
    private List<String> states;
    // rho - function associating states with the requirements they need
    private Map<String,List<String>> rho;
    // gamma - function associating states with the capabilities they provide
    private Map<String,List<String>> gamma;
    // tau - transition relation
    private Map<String,List<Transition>> tau;
    // phi - fault handling transition relation
    private Map<String,List<String>> phi;
    
    // Constructor for obtaining default (Brooklyn) management protocol
    public ManagementProtocol(List<String> reqs, List<String> caps) {
        states = new ArrayList<String>();
        rho = new HashMap<String,List<String>>();
        gamma = new HashMap<String,List<String>>();
        tau = new HashMap<String,List<Transition>>();
        phi = new HashMap<String,List<String>>();
                
        // States
        createState("unavailable");
        createState("started");
        createState("stopped");
        createState("failed");
        
        // rho
        for(String r : reqs) {
            rho.get("started").add(r);
        }
        
        // gamma
        for(String c : caps) {
            gamma.get("started").add(c);
        }
        
        // tau
        List<String> none = new ArrayList<String>();
        tau.get("unavailable").add(new Transition("start",reqs,none,"started"));
        tau.get("started").add(new Transition("release",none,none,"unavailable"));
        tau.get("started").add(new Transition("stop",none,none,"stopped"));
        tau.get("stopped").add(new Transition("release",none,none,"unavailable"));
        tau.get("stopped").add(new Transition("start",reqs,none,"started"));
        tau.get("failed").add(new Transition("release",none,none,"unavailable"));
                
        // phi
        phi.get("started").add("failed");
    }
    
    public ManagementProtocol(List<String> states,
            Map<String,List<String>> rho,
            Map<String,List<String>> gamma,
            Map<String,List<Transition>> tau,
            Map<String,List<String>> phi) {
        this.states = states;
        this.rho = rho;
        this.gamma = gamma;
        this.tau = tau;
        this.phi = phi;
    }
    
    private void createState(String stateName) {
        this.states.add(stateName);
        rho.put(stateName, new ArrayList());
        gamma.put(stateName, new ArrayList());
        tau.put(stateName, new ArrayList());
        phi.put(stateName, new ArrayList());
    }
    
    @Override
    public String toString() {
        return "{ states: " + this.states +
                ", rho: " + this.rho +
                ", gamma: " + this.gamma +
                ", tau: " + this.tau +
                ", phi: " + this.phi + "}";
    }
}