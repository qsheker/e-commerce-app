import { useContext } from "react";
import { CartContext } from "../context/CartContext";

const CartPage = () => {
    const { cartItems, removeFromCart } = useContext(CartContext);

    if (cartItems.length === 0) return <p>Cart is empty</p>;

    return (
        <div>
            <h2>Корзина</h2>
            {cartItems.map(item => (
                <div key={item.id} style={{ border: "1px solid gray", margin: "10px" }}>
                    <p>{item.name} - {item.price}$</p>
                    <button onClick={() => removeFromCart(item.id)}>Delete</button>
                </div>
            ))}
        </div>
    );
};

export default CartPage;
