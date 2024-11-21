import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderID;
    private List<FoodItem> items; //list of items the customer has ordered for
    private Status status;
    private String specialRequests;
    private boolean isVIP;
    private static int idCount=0;
    private String deliveryDetails;

    public Order(String specialRequests, String deliveryDetails, boolean isVIP) {
        this.orderID = "Order" + (idCount++);
        this.status = Status.Received;
        this.specialRequests = specialRequests;
        this.isVIP = isVIP;
        items=new ArrayList<>();
        this.deliveryDetails=deliveryDetails;
    }

    public enum Status{
        Received,Preparing,Out_For_Delivery,Delivered,Denied,Refunded
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(String deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }



    public List<FoodItem> getItems(){
        return items;
    }

    public void orderItem(FoodItem item){
        items.add(item);
    }

    public void removeItem(FoodItem item){
        items.remove(item);
    }

    public int totalPrice(){
        int total=0;
        for(FoodItem item: items){
            total+=item.getPrice();
        }
        return total;
    }

    public String toString(){
        return "Order: {" + "Order ID= " + orderID + "| Items= " + items + "| Status= " + status + "| Total Price= " + totalPrice() + "}";
    }
}
