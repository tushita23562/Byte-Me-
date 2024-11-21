import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    private String name;
    private String category;
    private int price;
    private boolean availability;
    private List<String> reviews;

    public FoodItem(String name, String category, int price, boolean availability) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.availability = availability;
        reviews=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void addReview(String review){
        reviews.add(review);
    }

    public List<String> getReviews(){
        return reviews;
    }

    public String toString(){
        return name + " (" + category + ") |" + price + (availability ? "[Available]" : " [Unavailable]");
    }
}
