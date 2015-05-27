# Shopping-Store-App

Shopping Retail Store:

Details of Android Retail Store Application:

Comprises of 4 Activities,
CategoryList: Displays Items grouped by categories
ProductList: Displays lists of products for a particular category
ProductDetails: Displays details for a product. Product(class) is a retail store item. 
Each product has a name, id, picture, details, price.

CartDetails: Displays the current cart status and list of products. 
Multiple products of the same type can be added/removed. 
Cart persistence is implemented via Sqlite. Cart design uses a singleton pattern.
CartDetails Activity uses a custom Adapter(CartAdapter) to provide various list item actions on a cart item.
Cart can be navigated to from any screen via action bar.

Inventory is maintained as xml under assets.
Inventory class handles the conversion of xml to appropriate data structures.
