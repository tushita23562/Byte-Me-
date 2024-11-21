import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Admin admin= new Admin();
        Map<String,Customer> customers=new HashMap<>();

        FoodItem pizza=new FoodItem("Pizza","Snacks",100,true);
        FoodItem cola=new FoodItem("Cola","Beverage",50,true);
        FoodItem donut=new FoodItem("Donut","Dessert",120,true);
        FoodItem pasta=new FoodItem("Pasta","Snacks",180,true);

        admin.addItem(pizza);
        admin.addItem(cola);
        admin.addItem(donut);
        admin.addItem(pasta);

        boolean isRunning=true;
        while(isRunning){
            System.out.println("Welcome to Byte Me!");
            System.out.println("Enter 1 to login as admin, 2 if you are a customer, 0 if you want to exit: ");
            int choice=sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1: //Admin
                    System.out.println("Enter admin password: ");
                    String password=sc.nextLine();
                    boolean adminSession=true;
                    if("123".equals(password)) { //admin password=123
                        while (adminSession) {
                            System.out.println("Admin functionalities: ");
                            System.out.println("1) Add new items");
                            System.out.println("2) Update existing items");
                            System.out.println("3) Remove items");
                            System.out.println("4) View pending orders");
                            System.out.println("5) Update order status");
                            System.out.println("6) Process refunds");
                            System.out.println("7) Generate daily sales report");
                            System.out.println("0) Exit");
                            System.out.println("Enter option: ");
                            int adminChoice = sc.nextInt();
                            sc.nextLine();

                            switch (adminChoice) {
                                case 0: //Exit
                                    adminSession = false;
                                    System.out.println("Exiting admin session, Thank You for visiting!");
                                    break;
                                case 1: //add new food item to menu
                                    System.out.println("Enter name: ");
                                    String name = sc.nextLine();
                                    System.out.println("Enter category: ");
                                    String category = sc.nextLine();
                                    System.out.println("Enter availability(true/false): ");
                                    boolean availability = sc.nextBoolean();
                                    System.out.println("Enter price: ");
                                    int price = sc.nextInt();
                                    sc.nextLine();
                                    admin.addItem(new FoodItem(name, category, price, availability));
                                    System.out.println(name + " added successfully!");
                                    break;
                                case 2: //update existing items
                                    System.out.println("Enter name of item you want to update details of: ");
                                    String n = sc.nextLine();
                                    admin.update(n);
                                    break;
                                case 3: //remove items
                                    System.out.println("Enter name of item you want to update details of: ");
                                    String na = sc.nextLine();
                                    admin.remove(na);
                                    break;
                                case 4: //view pending orders
                                    admin.viewPendingOrders();
                                    break;
                                case 5: //update order status
                                    System.out.println("Enter the Order ID for which you want to update the status: ");
                                    String orderID = sc.nextLine();
                                    System.out.println("Choose the new status: ");
                                    System.out.println("1) Received\n2) Preparing\n3) Out_For_Delivery\n4) Delivered\n5) Denied\n6) Refunded");
                                    System.out.println("Enter choice number(1/2/3/4/5/6): ");
                                    int statusChoice = sc.nextInt();
                                    sc.nextLine();
                                    Order.Status status = null;
                                    switch (statusChoice) {
                                        case 1 -> status = Order.Status.Received;
                                        case 2 -> status = Order.Status.Preparing;
                                        case 3 -> status = Order.Status.Out_For_Delivery;
                                        case 4 -> status = Order.Status.Delivered;
                                        case 5 -> status = Order.Status.Denied;
                                        case 6 -> status = Order.Status.Refunded;
                                        default -> System.out.println("Invalid choice, please select a valid status.");
                                    }
                                    if (status != null) {
                                        admin.updateOrderStatus(orderID, status);
                                    }
                                    break;
                                case 6: //process refunds
                                    System.out.println("Enter the Order ID for which you want to process the refund: ");
                                    String id= sc.nextLine();
                                    admin.processRefund(id);
                                    break;
                                case 7: //generate daily sales report
                                    admin.generateReport();
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                    }
                    else{
                        System.out.println("Incorrect password, access denied!");
                    }
                    break;

                case 2: //Customer
                    System.out.println("Enter your name: ");
                    String customerName=sc.nextLine();
                    System.out.println("Enter VIP code if you are a VIP customer(or press enter to skip): ");
                    String code=sc.nextLine();
                    boolean isVIP="vip123".equals(code); //VIP code=vip123

                    Customer customer;
                    if(customers.containsKey(customerName)){
                        customer=customers.get(customerName);
                        System.out.println("Welcome back, " + customerName + "!");
                    }
                    else{
                        customer=new Customer(customerName,isVIP,admin);
                        customers.put(customerName,customer);
                        System.out.println("New customer created. Welcome, " + customerName + "!");
                    }

//                    Customer customer=new Customer(customerName,isVIP,admin);
                    boolean customerSession=true;

                    while(customerSession){
                        System.out.println("Customer functionalities: ");
                        System.out.println("1) View all items");
                        System.out.println("2) Search");
                        System.out.println("3) Filter by category");
                        System.out.println("4) Sort by price");
                        System.out.println("5) Add items to cart");
                        System.out.println("6) Modify/Update quantities of items added to cart");
                        System.out.println("7) Remove items from cart");
                        System.out.println("8) View total ");
                        System.out.println("9) Checkout");
                        System.out.println("10) View order status");
                        System.out.println("11) Cancel order");
                        System.out.println("12) Check order history & re-order");
                        System.out.println("13) Provide review for food items");
                        System.out.println("14) View reviews of a particular item left by other customers");
                        System.out.println("0) Exit");
                        System.out.println("Enter option: ");
                        int customerChoice=sc.nextInt();
                        sc.nextLine();

                        switch(customerChoice){
                            case 0: //exit
                                customerSession=false;
                                System.out.println("Exiting customer session, Thank You for visiting!");
                                break;

                            case 1: //view menu
                                customer.viewMenu();
                                break;
                            case 2: //search item by keyword
                                System.out.println("Enter keyword of item you want to search for: ");
                                String key=sc.nextLine();
                                customer.search(key);
                                break;
                            case 3: //filter by category
                                System.out.println("Enter category for which you want to filter items: ");
                                String category=sc.nextLine();
                                customer.filter(category);
                                break;
                            case 4: //sort by price
                                System.out.println("Enter true if you want to sort items in ascending order of prices or false if you want to sort items in descending order of prices: ");
                                boolean c=sc.nextBoolean();
                                customer.sort(c);
                                break;
                            case 5: //add items to cart
                                System.out.println("Enter name of item you want to add to cart: ");
                                String name=sc.nextLine();
                                System.out.println("Enter quantity of the item you want to add in your cart: ");
                                int q=sc.nextInt();
                                sc.nextLine();
                                FoodItem item=null; //item to add
                                for(FoodItem i:admin.getMenu().values()){
                                    if(i.getName().equalsIgnoreCase(name)){
                                        item=i;
                                        break;
                                    }
                                }
                                if(item!=null){
                                    customer.addToCart(item,q);
                                    System.out.println(item.getName() + " added to cart!");
                                }
                                else{
                                    System.out.println("Item not found in menu!");
                                }
                                break;
                            case 6: // modify quantities
                                System.out.println("Enter name of item whose quantity you want to update: ");
                                String n=sc.nextLine();
//                                System.out.println("Enter new/updated quantity: ");
//                                int qu=sc.nextInt();
//                                sc.nextLine();
                                FoodItem it=null; //item to add
                                for(FoodItem i:admin.getMenu().values()){
                                    if(i.getName().equalsIgnoreCase(n)){
                                        it=i;
                                        break;
                                    }
                                }
                                if(it!=null && customer.getCart().containsKey(it)){
                                    System.out.println("Enter new/updated quantity: ");
                                    int qu=sc.nextInt();
                                    sc.nextLine();
                                    customer.modifyQuantities(it,qu);
                                    System.out.println("Quantity updated for " + it.getName() + "!");
                                }
                                else{
                                    System.out.println("Item not found in cart!");
                                }
                                break;
                            case 7: //remove items
                                System.out.println("Enter name of item you want to remove: ");
                                String name1=sc.nextLine();
                                FoodItem item1=null; //item to add
                                for(FoodItem i:admin.getMenu().values()){
                                    if(i.getName().equalsIgnoreCase(name1)){
                                        item1=i;
                                        break;
                                    }
                                }
                                if(item1!=null){
                                    customer.remove(item1);
                                    System.out.println(item1.getName() + " removed from cart!");
                                }
                                else{
                                    System.out.println("Item not found in cart!");
                                }
                                break;
                            case 8: //view cart total
                                customer.viewTotal();
                                break;
                            case 9: //checkout process
                                System.out.println("Enter payment details(cash on delivery/UPI/card): ");
                                String payment=sc.nextLine();
                                System.out.println("Enter delivery address: ");
                                String address=sc.nextLine();
                                System.out.println("Would you like to add any special request?(yes/no): ");
                                String option=sc.nextLine();
                                String special="";
                                if(option.equalsIgnoreCase("yes")){
                                    System.out.println("Enter special request: ");
                                    special=sc.nextLine();
                                }
                                customer.checkout(payment,address,special);
                                break;
                            case 10: //view order status
                                System.out.println("Enter order ID which was shown when checkout process was completed: ");
                                String id=sc.nextLine();
                                customer.viewOrderStatus(id);
                                break;
                            case 11: //cancel order
                                System.out.println("Enter order ID which was shown when checkout process was completed: ");
                                String i=sc.nextLine();
                                customer.cancelOrder(i);
                                break;
                            case 12: //view order history & re-order
                                customer.viewHistory();
                                break;
                            case 13: //provide review
                                System.out.println("Enter name of food item for which you want to add review: ");
                                String food=sc.nextLine();
                                FoodItem item2=null;
                                for(FoodItem menuItem: admin.getMenu().values()){
                                    if(menuItem.getName().equalsIgnoreCase(food)){
                                        item2=menuItem;
                                        break;
                                    }
                                }
                                if(item2!=null){
                                    customer.provideReview(item2);
                                }
                                break;
                            case 14: // view reviews
                                System.out.println("Enter name of food item for which you want to view reviews: ");
                                String food1=sc.nextLine();
                                FoodItem item3=null;
                                for(FoodItem menuItem: admin.getMenu().values()){
                                    if(menuItem.getName().equalsIgnoreCase(food1)){
                                        item3=menuItem;
                                        break;
                                    }
                                }
                                if(item3!=null){
                                    customer.viewReviews(item3);
                                }
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                    }
            }
        }
    }
}