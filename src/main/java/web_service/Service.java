package web_service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String serviceName;
    private boolean isActive;
    private double serviceMonthPrice;
    private long customerId;
    @ManyToMany(mappedBy = "services")
    private List<User> users = new ArrayList<>();

    public Service() {
    }

    public Service(String serviceName, boolean isActive, double serviceMonthPrice, long customerId) {
        this.serviceName = serviceName;
        this.isActive = isActive;
        this.serviceMonthPrice = serviceMonthPrice;
        this.customerId = customerId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getServiceMonthPrice() {
        return serviceMonthPrice;
    }

    public void setServiceMonthPrice(double serviceMonthPrice) {
        this.serviceMonthPrice = serviceMonthPrice;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void addUser(User user){
        users.add(user);
    }

    @Override
    public String toString() {
        return "Service{" + id + " " + serviceName + " " + isActive + " " + serviceMonthPrice +
                " " + customerId + "}";
    }
}
