class Electronics extends Item {
    private String brand;

    public Electronics(int id, String name, double price, String brand) {
        super(id, name, price);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String getDescription() {
        return "Electronics: " + getName() + " (Brand: " + brand + ")";
    }
}
