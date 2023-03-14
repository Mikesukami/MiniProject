public class order {
    //attribute
    public String orderId;
    public String orderDate;
    public String orderTime;
    public String menuId;
    public int quantity;
    public double total;
    public String menuName;

    
    

    //defult constructor
    public order() {
    }

    //setter
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }


    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }



    //getter
    public String getOrderId() {
        return orderId;
    }

    public String getMenuId() {
        return menuId;
    }

    public int getQty() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

}
