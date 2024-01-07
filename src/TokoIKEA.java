import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TokoIKEA {
    static final String DB_URL = "jdbc:mysql://localhost:3306/Ikea_db";
    static final String USER = "root";
    static final String PASS = "PBOSMT3";

    private List<Item> inventory;
    private Connection connection;

    public TokoIKEA(Connection connection) {
        this.connection = connection;
        this.inventory = new ArrayList<>();
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            createTableIfNotExists(connection);

            // Contoh penggunaan TokoIKEA
            TokoIKEA ikeaStore = new TokoIKEA(connection);

            // Menambahkan beberapa item ke inventory
            Item furniture = new Furniture(1, "Meja", 100.0, "Kayu");
            Item electronics = new Electronics(2, "TV", 500.0, "Samsung");

            ikeaStore.addItem(furniture);
            ikeaStore.addItem(electronics);

            // Menampilkan inventory
            ikeaStore.displayInventory();

            // Menampilkan total harga
            double total = ikeaStore.calculateTotalPrice();
            System.out.println("Total Harga di Toko IKEA: $" + total);

            // Contoh input langsung
            Item newFurniture = new Furniture(3, "Rak", 50.0, "Plastik");
            ikeaStore.addItem(newFurniture);

            // Menampilkan inventory setelah menambahkan item baru
            ikeaStore.displayInventory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS items (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "price DOUBLE NOT NULL)";
            statement.executeUpdate(createTableSQL);
        }
    }


    // Menambahkan item ke inventory
    public void addItem(Item item) {
        inventory.add(item);
        insertItemToDatabase(item);
    }

    // Menampilkan semua item di inventory
    public void displayInventory() {
        System.out.println("\n=== Daftar Item di Toko IKEA ===");
        for (Item item : inventory) {
            System.out.println(item.getDescription());
        }
    }

    // Menghitung total harga barang di inventory
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Item item : inventory) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    // Menambahkan item ke database
    private void insertItemToDatabase(Item item) {
        String insertSQL = "INSERT INTO items (name, price) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


