package presentation.rest.customer;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import presentation.rest.CollectionMeta;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerList {

    @SuppressWarnings("unused") private CollectionMeta meta;

    @XmlElement(name = "objects")
    private List<CustomerGet> customers;

    public CustomerList() {
        this.customers = new LinkedList<>();
    }

    public void addUser(CustomerGet user) {
        customers.add(user);
    }

    public void setMeta(CollectionMeta meta) {
        this.meta = meta;
    }

    public int size() {
        return customers.size();
    }
}


