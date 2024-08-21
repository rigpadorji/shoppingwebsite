package bean;

public class CartItem {
    private int productId;
    private int cartPrice;
    private int cartQuantity;
    private String color;
    private String size;
    private String image;
    
    // Constructor
    public CartItem(int productId, int cartPrice, int cartQuantity, String color, String size, String image) {
        this.productId = productId;
        this.cartPrice = cartPrice;
        this.cartQuantity = cartQuantity;
        this.color = color;
        this.size = size;
        this.image = image;
    }
    
    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(int cartPrice) {
        this.cartPrice = cartPrice;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
    public String getImage() {
    	
    	return image;
    }
    
    public void setImage(String image) {
    	this.image = image;
    	
    }
}
