import { Link } from "react-router-dom";
import { useContext } from "react";
import { CartContext } from "../context/CartContext";

const Navbar = () => {
    const { cartItems } = useContext(CartContext);

    return (
        <nav>
            <Link to="/">Main</Link> |
            <Link to="/cart">Cart: ({cartItems.length})</Link>
        </nav>
    );
};

export default Navbar;
