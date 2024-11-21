import java.util.*;

public class Admin {
    private Map<String,FoodItem> menu;
    private PriorityQueue<Order> pendingOrders; //for handling VIP orders first
    private List<Order> history; //for generating daily sales report

    public Admin(){
        menu=new HashMap<>();
        pendingOrders=new PriorityQueue<>(Comparator.comparing(Order::isVIP).reversed());
        history=new ArrayList<>();
    }

    public void addItem(FoodItem item){
        menu.put(item.getName(),item);
    }

    public void update(String name){
        FoodItem item=menu.get(name);
        if(item!=null){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter 1 to update price, 2 to update availability, 3 to update both: ");
            int choice=sc.nextInt();

            switch(choice){
                case 1:
                    System.out.println("Enter new price: ");
                    int newPrice=sc.nextInt();
                    item.setPrice(newPrice);
                    System.out.println("Price updated successfully!");
                    break;
                case 2:
                    System.out.println("Enter updated availability, true for available and false for unavailable: ");
                    boolean newAvailability=sc.nextBoolean();
                    item.setAvailability(newAvailability);
                    System.out.println("Availability updated successfully!");
                    break;
                case 3:
                    System.out.println("Enter new price: ");
                    int updatedPrice=sc.nextInt();
                    item.setPrice(updatedPrice);
                    System.out.println("Enter updated availability, true for available and false for unavailable: ");
                    boolean updatedAvailability=sc.nextBoolean();
                    item.setAvailability(updatedAvailability);
                    System.out.println("Price and availability updated successfully!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        else{
            System.out.println("Item not found in the menu!");
        }
    }

    public void remove(String name){
        if(menu.containsKey(name)){
            menu.remove(name);
            for(Order order: pendingOrders){
                order.getItems().removeIf(item->item.getName().equals(name));
                order.setStatus(Order.Status.Denied);
            }
            System.out.println(name+ " removed from the menu successfully!");
        }
        else{
            System.out.println("Item not found in the menu!");
        }
    }

    public void viewPendingOrders(){
        System.out.println("Pending orders: ");
        for(Order order: pendingOrders){
            System.out.println(order);
        }
    }

    public void updateOrderStatus(String orderID,Order.Status status){
        for(Order order: pendingOrders){
            if(order.getOrderID().equals(orderID)){
                order.setStatus(status);
                System.out.println(orderID + " status updated to " + status);
                if(status==Order.Status.Delivered){
                    history.add(order);
                }
                return;
            }
        }
        System.out.println("Order not found!");
    }

    public void processRefund(String orderID){
        for(Order order:pendingOrders){
            if(order.getOrderID().equals(orderID)){
                order.setStatus(Order.Status.Refunded);
                history.add(order);
                pendingOrders.remove(order);
                System.out.println("Refund processed for " + orderID);
                return;
            }
        }
        System.out.println("Order not found!");
    }

    public void generateReport(){ //to generate daily sales report
        int totalSales=0;
        Map<String,Integer> itemCount=new HashMap<>();

        for(Order order:history){
            totalSales+=order.totalPrice();
            for(FoodItem item:order.getItems()){
                itemCount.put(item.getName(),itemCount.getOrDefault(item.getName(),0)+1);
            }
        }
        System.out.println("Daily Sales Report: ");
        System.out.println("Total Sales: " + totalSales);
        System.out.println("Total orders processed: " + history.size());
    }

    public void addOrder(Order order){
        pendingOrders.offer(order);
    }

    public void completeOrder(Order order){
        pendingOrders.remove(order);
        history.add(order);
    }

    public Map<String, FoodItem> getMenu() {
        return menu;
    }

    public void handleSpecialRequests(Order order,String request){
        if(order!=null && request!=null && !request.isEmpty()){
            order.setSpecialRequests(request);
            System.out.println("Special request added to order: " + order.getOrderID());
        }
//        else{
//            System.out.println("No valid request provided, try again!");
//        }
    }
}
