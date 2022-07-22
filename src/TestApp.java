import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class TestApp {
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static HashMap<User,ArrayList<Product>> list = new HashMap<>();
    public static Random random = new Random();

    public static User getUserById(int id){
        for (User usr:users){
            if (usr.getId() == id){
                return usr;
            }
        }
        return null;
    }

    public static Product getProductById(int id){
        for (Product prd:products){
            if (prd.getId() == id){
                return prd;
            }
        }
        return null;
    }

    public static void  AddUser() throws Exception {
        int id = 0;
        boolean b = false;
        while (true) {
            id = random.nextInt(1000);
            for (User usr : users) {
                if (usr.getId() == id) {
                    b = true;
                    break;
                }
            }
            if(!b){
                break;
            }
        }
        System.out.println("Enter the First name: ");
        String first_name = sc.nextLine();
        first_name = sc.nextLine();


        System.out.println("Enter the Last name: ");
        String last_name = sc.nextLine();


        System.out.println("Enter the amount of money:");
        if(!sc.hasNextFloat()){
            throw new Exception("The amount of money isn't float! Try again!");
        }else {
            float money = sc.nextFloat();
            if(money != 0) {
                User user = new User(id, first_name, last_name, money);
                users.add(user);
                list.put(user, new ArrayList<>());
            }else{
                throw new Exception("The amount of money is 0! Try again!");
            }
        }
    }

    public static void  AddProduct() throws Exception {
        int id = 0;
        boolean b = false;
        while (true) {
            id = random.nextInt(1000);
            for (Product prd : products) {
                if (prd.getId() == id) {
                    b = true;
                    break;
                }
            }
            if(!b){
                break;
            }
        }
        System.out.println("Enter the name: ");
        String name = sc.nextLine();
        name = sc.nextLine();


        System.out.println("Enter the price:");
        if(!sc.hasNextFloat()){
            throw new Exception("The price isn't float! Try again!");
        }else {
            float price = sc.nextFloat();
            if(price != 0) {
                Product product = new Product(id, name, price);
                products.add(product);
            }else{
                throw new Exception("The price is 0! Try again!");
            }
        }
    }

    public static void  RemoveUser() throws Exception {
        int id = 0;
        System.out.println("Insert id of user to delete:");
        id = sc.nextInt();
        boolean b = false;
        for(User usr : users){
            if(usr.equals(getUserById(id))){
                b = true;
                break;
            }
        }
        if(!b) throw new Exception("There is no user with id " + id + "in a users list!");

        users.remove(getUserById(id));

        list.remove(getUserById(id));
    }

    public static void  RemoveProduct() throws Exception{
        int id = 0;
        System.out.println("Insert id of product to delete:");
        id = sc.nextInt();
        boolean b = false;
        for(Product prd : products){
            if(prd.equals(getProductById(id))){
                b = true;
                break;
            }
        }
        if(!b) throw new Exception("There is no product with id " + id + "in a products list!");

        products.remove(getProductById(id));

        for(User usr : users){
            for(Product prd : list.get(usr)){
                if(prd.getId() == id){
                    list.get(usr).remove(prd);
                }
            }
        }
    }

    public static void Buy() throws Exception{
        int id_u = 0;
        int id_p = 0;
        boolean b;
        while(true) {
            System.out.println("Enter the user's id: ");
            id_u = sc.nextInt();
            b = false;
            for (User usr : users) {
                if (id_u == usr.getId()) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                System.out.println("There is no such user in the list of users! Try again!");
            } else break;
        }

        while(true) {
            System.out.println("Enter the product's id: ");
            id_p = sc.nextInt();
            b = false;
            for (Product prd : products) {
                if (id_p == prd.getId()) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                System.out.println("There is no such product in the list of products! Try again!");
            } else break;
        }
        Product prod = getProductById(id_p);
        User user= getUserById(id_u);

        if(prod.getPrice()>user.getMoney())
            throw new Exception(user.getMoney() + " is not enough for product " + prod.getName() +
                    " because it's price is "+ prod.getPrice() + "\n");

        System.out.println(user + " bought " + prod + " successfully!");
        user.setMoney(user.getMoney()-prod.getPrice());
        list.get(user).add(prod);
    }

    public static void main(String[] args) throws Exception {
        TestApp app = new TestApp();
        while(true) {
                app.startApp();
        }
    }
    private void startApp(){
        System.out.println("\nChoose:\n"
                + "1. Display list of all users\n"
                + "2. Display list of all products\n"
                + "3. Buy a product\n"
                + "4.  Display list of user products by user id\n"
                + "5. Display list of users that bought product by product id\n"
                + "6. Add user\n"
                + "7. Remove user\n"
                + "8. Add product\n"
                + "9. Remove product\n");

        int choice = 0;
        while(!sc.hasNextInt()){
            sc.next();
        }
choice = sc.nextInt();
        switch (choice) {
            case 1: {
                System.out.println(users);
                break;
            }
            case 2: {
                System.out.println(products);
                break;
            }
            case 3: {
                try {
                    Buy();
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 4: {
                for(User usr:users){
                    if(!list.get(usr).isEmpty()){
                        System.out.println(usr + ":\n" + list.get(usr));
                    }
                }
                break;
            }
            case 5: {
                String str = "";
                for(Product prd:products){
                    str+=prd.getName()+":"+"\n";
                    for (User usr: users){
                        if(list.get(usr).contains(prd)){
                            str+=usr.getFirstName() + " " + usr.getLastName() + ", ";
                        }
                    }
                    if(!str.equals(prd.getName()+":"+"\n")){
                        System.out.println(str);
                    }
                    str = "";
                }
                break;
            } case 6:{
                try {
                    AddUser();
                    System.out.println(users);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            } case 7:{
                try {
                    RemoveUser();
                    System.out.println(users);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            } case 8:{
                try {
                    AddProduct();
                    System.out.println(products);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            } case 9:{
                try {
                    RemoveProduct();
                    System.out.println();
                    System.out.println(products);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }
}
