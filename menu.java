public class menu {
    public String m_id;
    public String m_name;
    public int m_price;
    public int menuQty;

    
    public menu(){
        
    }

    public void setMenuId(String m_id){
        this.m_id = m_id;
    }

    public String getMenuId(){
        return m_id;
    }

    public void setMenuName(String m_name){
        this.m_name = m_name;
    }

    public String getMenuName(){
        return m_name;
    }

    public void setPrice(int m_price){
        this.m_price = m_price;
    }

    public int getPrice(){
        return m_price;
    }

    public void setMenuQty(int menuQty){
        this.menuQty = menuQty;
    }

    public int getMenuQty(){
        return menuQty;
    }
}
