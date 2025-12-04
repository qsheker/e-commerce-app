import { useContext } from "react";
import { CartContext } from "../context/CartContext";

const ProductCard = ({ product }) => {
    const { addToCart } = useContext(CartContext);

    if (!product) return null;

    return (
        <div style={{ border: "1px solid gray", margin: "10px", padding: "10px" }}>
            <h3>{product.name}</h3>
            <p>Price: {product.price}$</p>
            <button onClick={() => addToCart(product)}>Add to cart</button>
        </div>
    );
};

export default ProductCard;
