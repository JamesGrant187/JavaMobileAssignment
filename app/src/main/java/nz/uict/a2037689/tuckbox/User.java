package nz.uict.a2037689.tuckbox;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//User storage for database.
public class User {

        private String email;
        private String userID;
        private String orderTime;
        private String orderDate;
        private String orderLocationTown;
        private String orderLocationStreet;
        private String orderCreditCard;
        private String orderCreditExp;
        private String orderCreditCVV;
        private String orderMeals;
        private List<OrderInformation> orderList = new ArrayList<>();
        private List<List<OrderInformation>> history = new ArrayList<>();

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
        public User(){
        }



        public User(String userID){
            this.setUserID(userID);
        }





        //Allow use of email variable for updating database
        public String getEmail(){
            return email;
        }

        //Update user record for email.
        public void setEmail(String email){
            this.email = email;
            databaseUpdate();
        }

        public String getUserID() {
            return userID;

        }

        public void setUserID(String userID) {
            this.userID = userID;
            databaseUpdate();
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
            databaseUpdate();
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
            databaseUpdate();
        }

        public String getOrderLocationTown() {
            return orderLocationTown;
        }

        public void setOrderLocationTown(String orderLocationTown) {
            this.orderLocationTown = orderLocationTown;
            databaseUpdate();
        }

        public String getOrderLocationStreet() {
            return orderLocationStreet;
        }

        public void setOrderLocationStreet(String orderLocationStreet) {
            this.orderLocationStreet = orderLocationStreet;
            databaseUpdate();
        }

        public String getOrderCreditCard() {
            return orderCreditCard;
        }

        public void setOrderCreditCard(String orderCreditCard) {
            this.orderCreditCard = orderCreditCard;
            databaseUpdate();
        }

        public String getOrderCreditExp() {
            return orderCreditExp;
        }

        public void setOrderCreditExp(String orderCreditExp) {
            this.orderCreditExp = orderCreditExp;
            databaseUpdate();
        }

        public String getOrderCreditCVV() {
            return orderCreditCVV;

        }

        public void setOrderCreditCVV(String orderCreditCVV) {
            this.orderCreditCVV = orderCreditCVV;
            databaseUpdate();
        }

        public String getOrderMeals() {
            return orderMeals;

        }

        public void setOrderMeals(String orderMeals) {
            this.orderMeals = orderMeals;
            databaseUpdate();
        }

        public void databaseUpdate(){
        //FIREBASE AUTHENTICATION IS HAVING ISSUES, sometimes returns null as the userID
            Log.i("TUCKBOX", "getUID"+getUserID());
            FirebaseDatabase.getInstance().getReference("users").child(getUserID()).setValue(this);
        }

        public List<OrderInformation> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderInformation> orderList) {
            this.orderList = orderList;
        }

    public List<List<OrderInformation>> getHistory() {
        return history;
    }

    public void setHistory(List<List<OrderInformation>> history) {
        this.history = history;
    }

    public void addToHistory(){
        history.add(orderList);
        databaseUpdate();
    }
}


