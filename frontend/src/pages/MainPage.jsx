import { useEffect, useState } from "react";
import ProductCard from "../components/ProductCard";

const MainPage = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetch("http://localhost:9090/api/v1/products")
            .then(res => res.json())
            .then(data => setProducts(data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div className="main-page">
            {products.length === 0 ? (
                <p>Loading products...</p>
            ) : (
                products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))
            )}
        </div>
    );
};

export default MainPage;
