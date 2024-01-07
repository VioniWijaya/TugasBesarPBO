class Furniture extends Item {
    private String material;

    public Furniture(int id, String name, double price, String material) {
        super(id, name, price);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String getDescription() {
        return "Furniture: " + getName() + " (Material: " + material + ")";
    }
}