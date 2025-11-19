package common;

public class PropertyValue {
    private final String zipCode;
    private final double marketValue;
    private final Double salePrice;
    private final String saleDate;
    private final String owner1;
    private final String streetName;
    private final String houseNumber;
    private final double livableArea;

    public PropertyValue(String zipCode, double marketValue, Double salePrice, 
                        String saleDate, String owner1, String streetName, String houseNumber, double livableArea) {
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new IllegalArgumentException("ZIP code cannot be null or empty");
        }
        if (marketValue < 0) {
            throw new IllegalArgumentException("Market value cannot be negative");
        }
        if (salePrice != null && salePrice < 0) {
            throw new IllegalArgumentException("Sale price cannot be negative");
        }
        if (livableArea < 0) {
            throw new IllegalArgumentException("Livable area cannot be negative");
        }

        this.zipCode = zipCode.trim();
        this.marketValue = marketValue;
        this.salePrice = salePrice;
        if (saleDate == null || saleDate.trim().isEmpty()) {
            this.saleDate = null;
        } else {
            this.saleDate = saleDate.trim();
        }
        if (owner1 == null || owner1.trim().isEmpty()) {
            this.owner1 = null;
        } else {
            this.owner1 = owner1.trim();
        }
        if (streetName == null || streetName.trim().isEmpty()) {
            this.streetName = null;
        } else {
            this.streetName = streetName.trim();
        }
        if (houseNumber == null || houseNumber.trim().isEmpty()) {
            this.houseNumber = null;
        } else {
            this.houseNumber = houseNumber.trim();
        }
        this.livableArea = livableArea;
    }

    public String getZipCode() {
        return zipCode;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public String getOwner1() {
        return owner1;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public double getLivableArea() {
        return livableArea;
    }
}


