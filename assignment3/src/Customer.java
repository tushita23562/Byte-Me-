import java.util.*;

public class Customer {
    private String name;
    private boolean isVIP;
    private List<Order> history;
    private Map<FoodItem,Integer> cart; //mapping food item(with its attributes) to its qunatites
    private Admin admin;

    public Customer(String name, boolean isVIP, Admin admin) {
        this.name = name;
        this.isVIP = isVIP;
        this.admin = admin;
        this.history=new ArrayList<>();
        this.cart=new HashMap<>();
    }

    public void viewMenu(){
        for(FoodItem item: admin.getMenu().values()){
            System.out.println(item.getName() + "-" + item.getPrice() + "-" + (item.isAvailability()? "Available" : "Currently unavailable"));
        }
    }

    public void search(String keyword){ //customer can search for the item by entering a keyword
        for(FoodItem item:admin.getMenu().values()){
            if(item.getName().toLowerCase().contains(keyword.toLowerCase())){
                System.out.println(item.getName() + "-" + item.getPrice() + "-" + (item.isAvailability()? "Available" : "Currently unavailable"));
            }
        }
    }

    public void filter(String category){ //customer can filter the items based on categories
        boolean found=false;
        for(FoodItem item:admin.getMenu().values()){
            if(item.getCategory().equalsIgnoreCase(category)){
                System.out.println(item.getName() + "-" + item.getPrice());
            }
        }
        if(!found){
            System.out.println("Category not found, try again!");
        }
    }

    public void sort(boolean ascendingOrder){ //sort by price in ascending order by default
        List<FoodItem> menu=new ArrayList<>(admin.getMenu().values());
        menu.sort(Comparator.comparing(FoodItem::getPrice));
        if(!ascendingOrder){
            Collections.reverse(menu);
        }
        for(FoodItem item: menu){
            System.out.println(item.getName() + "-" + item.getPrice());
        }
    }

    public void addToCart(FoodItem item,int quantity){
        cart.put(item,cart.getOrDefault(item,0)+quantity);
    }

    public void modifyQuantities(FoodItem item,int updatedQuantity){ //to modify/update quantity of item added in cart
        if(cart.containsKey(item)){
            cart.put(item,updatedQuantity);
            System.out.println("Qunatity updated successfully!");
        }
//        else if(!cart.containsKey(item)){
//            System.out.println("Item not found in cart!");
//        }
    }

    public void remove(FoodItem item){ //to remove an added item from the cart
        if(cart.containsKey(item)){
            cart.remove(item);
        }
    }

    public void viewTotal(){
        int total=0;
        for(Map.Entry<FoodItem,Integer> entry:cart.entrySet()){
            total+=entry.getKey().getPrice()*entry.getValue();
        }
        System.out.println("Total price of the items in cart= "+ total);
    }

    public void checkout(String paymentDetails,String deliveryDetails,String request){
        if(cart.isEmpty()){
            System.out.println("Cart is empty!");
            return;
        }
        Order order=new Order(UUID.randomUUID().toString(),deliveryDetails,isVIP); //UUID: Universally Unique Identifier
        for(Map.Entry<FoodItem,Integer> entry:cart.entrySet()){
            for(int i=0;i<entry.getValue();i++){
                order.orderItem(entry.getKey());
            }
        }
        admin.handleSpecialRequests(order,request);
        admin.addOrder(order);
        history.add(order);
        cart.clear();
        System.out.println(order.getOrderID() + " placed successfully!");
    }

    public void viewOrderStatus(String orderID){
        for(Order order: history){
            if(order.getOrderID().equals(orderID)){
                System.out.println("Order status: " +order.getStatus());
                return;
            }
        }
        System.out.println("Order not found!");
    }

    public void cancelOrder(String orderID){
        Iterator<Order> iterator=history.iterator();
        while(iterator.hasNext()){
            Order order=iterator.next();
            if(order.getOrderID().equals(orderID) && order.getStatus()==Order.Status.Received){
                admin.processRefund(orderID); //assumption: only those orders can be cancelled which were paid for
                iterator.remove();
                System.out.println("Order cancelled successfully!");
                return;
            }
        }
        System.out.println("Order cannot be cancelled now!");
    }

    public void viewHistory(){
        Scanner sc=new Scanner(System.in);
        if(history.isEmpty()){
            System.out.println("No previous orders found, this is your first order!");
            return;
        }
        for(Order order:history){
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Items: ");
            for(FoodItem item: order.getItems()){
                System.out.println("Name: "+ item.getName() + " | Price: " + item.getPrice());
            }
        }
        System.out.println("Enter order number to reorder or 0 to exit: ");
        String choice=sc.nextLine();
        if(choice.equals("0")){
            System.out.println("Exiting without reordering!");
            return;
        }
        Order prevOrder=null;
        for(Order order:history){
            if(order.getOrderID().equalsIgnoreCase(choice)){
                prevOrder=order;
                break;
            }
        }
        if(prevOrder!=null){
            Order newOrder=new Order(prevOrder.getSpecialRequests(),prevOrder.getDeliveryDetails(),prevOrder.isVIP());
            for(FoodItem item: prevOrder.getItems()){
                newOrder.orderItem(item);
            }
            admin.addOrder(newOrder);
            history.add(newOrder);
            System.out.println("Reorder placed successfully with order ID: " + newOrder.getOrderID());
        }
        else{
            System.out.println("Order ID not found!");
        }
    }

    Scanner sc = new Scanner(System.in);
    public void provideReview(FoodItem item){
        if(history.stream().anyMatch(order -> order.getItems().contains(item))){
            System.out.println("Enter review: ");
            String review=sc.nextLine();
            item.addReview(review);
            System.out.println("Review added successfully!");
        }
        else{
            System.out.println("You need to order something to add reviews!");
        }
    }

    public void viewReviews(FoodItem item){
        System.out.println("Reviews for " + item.getName() + ":");
        for(String review: item.getReviews()){
            System.out.println(review);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public Map<FoodItem, Integer> getCart() {
        return cart;
    }
}
