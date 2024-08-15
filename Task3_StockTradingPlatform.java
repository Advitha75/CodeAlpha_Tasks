import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Market {
    private Map<String, Stock> stocks;

    public Market() {
        stocks = new HashMap<>();
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void updatePrices() {
        Random random = new Random();
        for (Stock stock : stocks.values()) {
            double change = (random.nextDouble() - 0.5) * 10; // Random change in price
            stock.setPrice(stock.getPrice() + change);
        }
    }

    public void displayMarketData() {
        System.out.println("Current Market Data:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
        }
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
        System.out.println("Stock " + stock.getSymbol() + " added with initial price $" + stock.getPrice());
    }

    public void initializeMarket(Scanner sc) {
        System.out.println("Enter the number of stocks to add to the market: ");
        int numStocks = sc.nextInt();
        sc.nextLine(); // Consume newline

        for (int i = 0; i < numStocks; i++) {
            System.out.print("Enter stock symbol: ");
            String symbol = sc.nextLine();
            System.out.print("Enter initial price: ");
            double price = sc.nextDouble();
            sc.nextLine(); // Consume newline
            addStock(new Stock(symbol, price));
        }
    }
}

class Transaction {
    private String stockSymbol;
    private int quantity;
    private double price;
    private boolean isBuy;

    public Transaction(String stockSymbol, int quantity, double price, boolean isBuy) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.isBuy = isBuy;
    }

    @Override
    public String toString() {
        return (isBuy ? "Bought" : "Sold") + " " + quantity + " shares of " + stockSymbol + " at $" + price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings;
    private List<Transaction> transactions;

    public Portfolio() {
        holdings = new HashMap<>();
        transactions = new ArrayList<>();
    }

    public void buyStock(String symbol, int quantity, double price) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        transactions.add(new Transaction(symbol, quantity, price, true));
    }

    public void sellStock(String symbol, int quantity, double price) {
        if (holdings.getOrDefault(symbol, 0) >= quantity) {
            holdings.put(symbol, holdings.get(symbol) - quantity);
            transactions.add(new Transaction(symbol, quantity, price, false));
        } else {
            System.out.println("Not enough shares to sell");
        }
    }

    public void displayPortfolio(Market market) {
        System.out.println("Current Portfolio:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            double currentPrice = market.getStock(symbol).getPrice();
            System.out.println(symbol + ": " + quantity + " shares, Current Price: $" + currentPrice + ", Total Value: $" + (quantity * currentPrice));
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Market market = new Market();
        Portfolio portfolio = new Portfolio();
        Scanner sc = new Scanner(System.in);

        // Initialize market with user-provided data
        market.initializeMarket(sc);

        while (true) {
            market.updatePrices();
            market.displayMarketData();
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. View Transaction History");
            System.out.println("5. Add New Stock to Market");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter stock symbol: ");
                String symbol = sc.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = sc.nextInt();
                Stock stock = market.getStock(symbol);
                if (stock != null) {
                    portfolio.buyStock(symbol, quantity, stock.getPrice());
                } else {
                    System.out.println("Stock not found");
                }
            } else if (choice == 2) {
                System.out.print("Enter stock symbol: ");
                String symbol = sc.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = sc.nextInt();
                Stock stock = market.getStock(symbol);
                if (stock != null) {
                    portfolio.sellStock(symbol, quantity, stock.getPrice());
                } else {
                    System.out.println("Stock not found");
                }
            } else if (choice == 3) {
                portfolio.displayPortfolio(market);
            } else if (choice == 4) {
                portfolio.displayTransactionHistory();
            } else if (choice == 5) {
                System.out.print("Enter new stock symbol: ");
                String symbol = sc.nextLine();
                System.out.print("Enter initial price: ");
                double price = sc.nextDouble();
                market.addStock(new Stock(symbol, price));
            } else if (choice == 6) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }

        sc.close();
    }
}