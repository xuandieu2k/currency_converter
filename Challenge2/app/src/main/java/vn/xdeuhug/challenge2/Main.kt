package vn.xdeuhug.challenge2

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 28 / 10 / 2024
 */

// ############### Support Question 2.1 ##############//
data class Product(val name: String, val price: Double, val quantity: Int)

class Inventory(private val products: List<Product>) {

    // Calculate total inventory value
    fun totalInventoryValue(): Double {
        return products.filter { it.quantity > 0 }.sumOf { it.price * it.quantity }
    }

    // Find the most expensive product
    fun mostExpensiveProduct(): String? {
        return products.maxByOrNull { it.price }?.name
    }

    // Check if the product named "Headphones" is in stock
    fun isProductInStock(productName: String): Boolean {
        return products.any { it.name == productName }
    }

    // Sort products by price or quantity
    fun sortProducts(by: String = "price", descending: Boolean = true): List<Product> {
        return when (by) {
            "price" -> if (descending) products.sortedByDescending { it.price } else products.sortedBy { it.price }
            "quantity" -> if (descending) products.sortedByDescending { it.quantity } else products.sortedBy { it.quantity }
            else -> products
        }
    }
}
// ############### Support Question 2.1 ##############//


// ############### Support Question 2.2 ##############//

// Find missing numbers in array
fun findMissingNumber(arr: List<Int>): Int {
    val n = arr.size
    val totalSum = (n + 1) * (n + 2) / 2
    val actualSum = arr.sum()
    return totalSum - actualSum
}
// ############### Support Question 2.2 ##############//

fun main() {
    // Create a list of sample products
    val products = listOf(
        Product("Laptop", 999.99, 5),
        Product("Smartphone", 499.99, 10),
        Product("Tablet", 299.99, 0),
        Product("Smartwatch", 199.99, 3)
    )

    val inventory = Inventory(products)

    // Display initial product list
    println("Question 2.1. Product Inventory Management\n")
    println("Initial product list:")
    products.forEach { println("Product: ${it.name}, Price: ${it.price}, Quantity: ${it.quantity}") }

    // Perform tasks with the inventory
    println("\nResults:")
    println("Total inventory value: ${inventory.totalInventoryValue()}")
    println("Most expensive product: ${inventory.mostExpensiveProduct()}")
    println("The product 'Headphones' is in stock: ${inventory.isProductInStock("Headphones")}")
    val sortedByPrice = inventory.sortProducts(by = "price", descending = true)
    println("Products sorted by price in descending order: ${sortedByPrice.map { it.name to it.price }}\n")

    // Array for missing number check
    val arr = listOf(3, 7, 1, 2, 6, 4)
    println("Question 2.2. Array Manipulation and Missing Number Problem\n")
    println("Initial array: $arr")
    println("Missing number: ${findMissingNumber(arr)}")

}